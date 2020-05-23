package com.vadeen.neat.tree;

import java.util.*;

/**
 * A level tree is a tree with nodes divided into levels like this:
 *       0    Level 4
 *      / \
 *     0  |   Level 3
 *    / \ |
 *   /  0 |   Level 2
 *  /    \|
 * 0      0   Level 1
 *
 * 0,0,0,...  Level 0 (Not connected nodes)
 *
 * Any number of inputs and outputs are allowed for each node.
 * All nodes and connections have an id and is directly accessible.
 *
 * The levels are calculated so that the connections always moves upwards. Nodes on the same level is never connected.
 * If a connection is added from a higher node to a lower node, or nodes on the same level, the lower node is upgrade to
 * a higher level if possible.
 *
 * If it is not possible to upgrade the lower node and all it's superiors without introducing a cycle (connections not
 * pointing upwards), the connection is denied.
 */
public class LevelTree<C extends LevelConnection, N extends LevelNode> {

    protected int levelCount = 0;

    protected Map<Integer, N> nodes = new HashMap<>();
    protected Map<Integer, C> connections = new HashMap<>();

    protected LevelTreeConnectionIndex connectionIndex = new LevelTreeConnectionIndex();

    /**
     * Add node to the tree.
     * Nodes are always added to level 0. When connected they are upgraded to the suitable level.
     */
    public void addNode(N node) {
        nodes.put(node.getId(), node);
        node.setLevel(0);

        // If we have nodes, we have at least 1 level.
        if (levelCount == 0)
            levelCount = 1;
    }

    /**
     * Get node by id.
     */
    public N getNode(int i) {
        return nodes.get(i);
    }

    /**
     * Get number of levels in the current tree.
     * - 0 levels means no nodes at all.
     * - 1 levels means there are nodes but non of them are connected. (All in level 0)
     * - 2< levels there is connected nodes in the tree.
     */
    public int getLevelCount() {
        return levelCount;
    }

    /**
     * Return the tree split into levels.
     * Example:
     *
     *          3       // Level 2
     *         / \
     *        1   2     // Level 1
     *
     *  4 (unconnected) // Level 0
     */
    public List<List<N>> getLevels() {
        ArrayList<List<N>> levels = new ArrayList<>(levelCount);
        for (int i = 0; i < levelCount; i++) {
            levels.add(new ArrayList<>());
        }

        for (N node : nodes.values()) {
            levels.get(node.getLevel()).add(node);
        }
        return levels;
    }

    /**
     * Check if there is a connection from inNode to outNode.
     */
    public boolean hasConnection(int inNode, int outNode) {
        return connectionIndex.getOutputNodes(inNode).contains(outNode);
    }

    public Map<Integer, C> getConnections() {
        return Collections.unmodifiableMap(connections);
    }

    /**
     * Get connection by id.
     */
    public C getConnection (int id) {
        return connections.get(id);
    }

    /**
     * Get all connections leading in to node with nodeId.
     */
    public List<C> getInConnections(int nodeId) {
        List<C> res = new ArrayList<>();
        for (int id : connectionIndex.getInputConnections(nodeId)) {
            res.add(connections.get(id));
        }
        return res;
    }

    /**
     * Get all connections leading out from node with nodeId.
     */
    public List<C> getOutConnections(int nodeId) {
        List<C> res = new ArrayList<>();
        for (int id : connectionIndex.getOutputConnections(nodeId)) {
            res.add(connections.get(id));
        }
        return res;
    }

    /**
     * Connect two existing nodes.
     *
     * @throws IllegalArgumentException If the connection cannot be added because the nodes do not exists, it is a
     * duplicate or it is illegal.
     */
    public void addConnection(C connection) {
        N in = nodes.get(connection.getIn());
        N out = nodes.get(connection.getOut());

        if (in == null || out == null)
            throw new IllegalArgumentException("Invalid connection in:" + in + " out:" + out);

        // We cannot connect the same node to it self.
        if (in.getId() == out.getId())
            throw new IllegalArgumentException("Invalid connection in:" + in + " out:" + out);

        // We cannot connect the same node to it self in reverse. (Cyclic)
        if (hasConnection(in.getId(), out.getId()))
            throw new IllegalArgumentException("Duplicate connection in:" + in + " out:" + out);

        // Make sure this connection do not introduce a cycle.
        if (!isCyclic(in, out))
            throw new IllegalArgumentException(String.format("%s cannot be connected to %s, will introduce cycle", in, out));

        // If the in node is a zero level node, it is upgraded to level 1 when connected.
        if (in.getLevel() == 0)
            upgradeLevel(in, 1);

        // Upgrade levels upwards the tree.
        upgradeLevelsUpwards(out, in.getLevel() + 1);

        // Add the connection to connection map.
        connections.put(connection.getId(), connection);

        // Update index with the new connection.
        connectionIndex.addConnection(connection);
    }

    public String toString() {
        return String.format("LevelTree{nodes=%s,connections=%s}", nodes.values(), connections.values());
    }

    /**
     * Checks if there would be a cycle if a connection was introduced from the in node to the out node.
     */
    private boolean isCyclic(LevelNode in, LevelNode out) {
        // Level 0 can never be cyclic since they have no connections.
        if (out.getLevel() == 0)
            return true;

        // Always safe to connect to higher levels.
        if (in.getLevel() <= out.getLevel())
            return true;

        // If we cannot find the input node from the output node, we are good.
        return !isAccessibleFrom(in, out);
    }

    /**
     * Checks if node can be access from the from node in the current tree.
     */
    private boolean isAccessibleFrom(LevelNode node, LevelNode from) {
        // If it's the same node, it is accessible.
        if (from.getId() == node.getId())
            return true;

        // If any of our outputs can access it then it is accessible.
        for (int nodeId : connectionIndex.getOutputNodes(from.getId())) {
            LevelNode out = nodes.get(nodeId);
            if (isAccessibleFrom(node, out))
                return true;
        }

        return false;
    }

    /**
     * Upgrades the node to level, propagates the level upgrade upwards the tree.
     *
     * Example:
     *      upgradeLevelsUpwards(Node(2), 3)
     *
     * Would result in the following change:
     *      3          4
     *      |          |
     *      2    ->    3
     *      |          |
     *      1          1
     */
    private void upgradeLevelsUpwards(N node, int level) {
        // Update current level.
        upgradeLevel(node, level);

        // Update levels that has current nodes as input.
        for (Integer nodeId : connectionIndex.getOutputNodes(node.getId())) {
            upgradeLevelsUpwards(nodes.get(nodeId), level + 1);
        }
    }

    /**
     * Upgrade a single node to a higher level.
     * If a lower level is provided, there is no effect.
     */
    private void upgradeLevel(N node, int level) {
        if (node.getLevel() >= level)
            return;

        node.setLevel(level);
        levelCount = Math.max(levelCount, level + 1);
    }
}
