package com.vadeen.neat.tree;

import java.util.*;

/**
 * Index of node input and output connections for fast access.
 *
 * Example:
 *      3
*     7/ \8
 *    1  2
 *
 *      getInputNodes(3) -> [1, 2]
 *      getOutputNodes(1) -> [3]
 *
 *      getOutputConnections(1) -> [7]
 *      getInputConnections(3) -> [7, 8]
 *
 */
public class LevelTreeConnectionIndex {

    // Map<NodeId, List<NodeId>>
    private final HashMap<Integer, List<Integer>> inNodeIndex = new HashMap<>();
    private final HashMap<Integer, List<Integer>> outNodeIndex = new HashMap<>();

    // Map<NodeId, List<ConnectionId>>
    private final HashMap<Integer, List<Integer>> inConIndex = new HashMap<>();
    private final HashMap<Integer, List<Integer>> outConIndex = new HashMap<>();

    public static LevelTreeConnectionIndex copy(LevelTreeConnectionIndex o) {
        LevelTreeConnectionIndex index = new LevelTreeConnectionIndex();
        copyIndex(o.inNodeIndex, index.inNodeIndex);
        copyIndex(o.outNodeIndex, index.outNodeIndex);
        copyIndex(o.inConIndex, index.inConIndex);
        copyIndex(o.outConIndex, index.outConIndex);
        return index;
    }

    private static void copyIndex(HashMap<Integer, List<Integer>> from, HashMap<Integer, List<Integer>> to) {
        for (Map.Entry<Integer, List<Integer>> e : from.entrySet()) {
            to.put(e.getKey(), new ArrayList<>(e.getValue()));
        }
    }

    public void addConnection(LevelConnection con) {
        List<Integer> nodeInputs = inNodeIndex.computeIfAbsent(con.getOut(), key -> new ArrayList<>());
        List<Integer> nodeOutputs = outNodeIndex.computeIfAbsent(con.getIn(), key -> new ArrayList<>());
        List<Integer> conInputs = inConIndex.computeIfAbsent(con.getOut(), key -> new ArrayList<>());
        List<Integer> conOutputs = outConIndex.computeIfAbsent(con.getIn(), key -> new ArrayList<>());

        nodeInputs.add(con.getIn());
        nodeOutputs.add(con.getOut());
        conInputs.add(con.getId());
        conOutputs.add(con.getId());
    }

    public List<Integer> getInputNodes(int nodeId) {
        List<Integer> inputs = inNodeIndex.get(nodeId);
        if (inputs == null)
            return new ArrayList<>();

        return Collections.unmodifiableList(inputs);
    }

    public List<Integer> getOutputNodes(int nodeId) {
        List<Integer> outputs = outNodeIndex.get(nodeId);
        if (outputs == null)
            return new ArrayList<>();

        return Collections.unmodifiableList(outputs);
    }

    public List<Integer> getInputConnections(int nodeId) {
        List<Integer> inputs = inConIndex.get(nodeId);
        if (inputs == null)
            return new ArrayList<>();

        return Collections.unmodifiableList(inputs);
    }

    public List<Integer> getOutputConnections(int nodeId) {
        List<Integer> outputs = outConIndex.get(nodeId);
        if (outputs == null)
            return new ArrayList<>();

        return Collections.unmodifiableList(outputs);
    }
}
