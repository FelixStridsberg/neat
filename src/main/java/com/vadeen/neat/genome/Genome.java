package com.vadeen.neat.genome;

import com.vadeen.neat.gene.ConnectionGene;
import com.vadeen.neat.gene.GeneFactory;
import com.vadeen.neat.gene.NodeGene;
import com.vadeen.neat.tree.LevelTree;
import com.vadeen.neat.tree.LevelTreeConnectionIndex;

import java.util.*;

/**
 * A genome is a network tree which can be propagated, crossed with other genomes, mutated and so on.
 */
public class Genome extends LevelTree<ConnectionGene, NodeGene> {

    /**
     * Every genome has an id, just for debugging purposes.
     */
    public int id;

    /**
     * This is the counter that generates the debug id.
     */
    private static int idCounter = 0;

    /**
     * Fitness of this genome.
     */
    private float fitness = 0.0f;

    /**
     * Creates a minimal possible tree of a genome. Which is just the nodes and no connections.
     *
     * @param inputs Number of input nodes.
     * @param outputs Number of output nodes.
     * @return The new genome.
     */
    public static Genome create(GeneFactory factory, int inputs, int outputs) {
        Genome genome = new Genome(++idCounter);

        for (int i = 0; i < inputs; i++)
            genome.addNode(factory.createNode(NodeGene.Type.INPUT));

        for (int i = 0; i < outputs; i++)
            genome.addNode(factory.createNode(NodeGene.Type.OUTPUT));

        return genome;
    }

    /**
     * Creates a new genome from connections and nodes.
     *
     * @param connections Connections to add to the genome.
     * @param nodes Node to add to the genome.
     */
    public static Genome create(Collection<ConnectionGene> connections, Collection<NodeGene> nodes) {
        return create(++idCounter, connections, nodes);
    }

    /**
     * Creates a new genome from connections and nodes.
     *
     * @param connections Connections to add to the genome.
     * @param nodes Node to add to the genome.
     */
    public static Genome create(int id, Collection<ConnectionGene> connections, Collection<NodeGene> nodes) {
        Genome genome = new Genome(id);

        if (id >= idCounter)
            idCounter = id;

        for (NodeGene node : nodes)
            genome.addNode(node);

        for (ConnectionGene con : connections)
            genome.addConnection(con);

        return genome;
    }

    /**
     * Deep copy of genome and level three.
     */
    public static Genome copy(Genome old) {
        Genome genome = new Genome(old.id);

        for (Map.Entry<Integer, NodeGene> e : old.getNodes().entrySet()) {
            genome.nodes.put(e.getKey(), new NodeGene(e.getValue()));
        }

        for (Map.Entry<Integer, ConnectionGene> e : old.getConnections().entrySet()) {
            genome.connections.put(e.getKey(), new ConnectionGene(e.getValue()));
        }

        genome.levelCount = old.levelCount;
        genome.connectionIndex = LevelTreeConnectionIndex.copy(old.connectionIndex);

        return genome;
    }

    public Genome(int id) {this.id = id;}

    /**
     * @return All input nodes.
     */
    public List<NodeGene> getInputNodes() {
        List<NodeGene> nodes = new ArrayList<>();
        for (NodeGene n : this.nodes.values()) {
            if (n.getType() == NodeGene.Type.INPUT)
                nodes.add(n);
        }
        return nodes;
    }

    /**
     * @return All output nodes.
     */
    public List<NodeGene> getOutputNodes() {
        List<NodeGene> nodes = new ArrayList<>();
        for (NodeGene n : this.nodes.values()) {
            if (n.getType() == NodeGene.Type.OUTPUT)
                nodes.add(n);
        }
        return nodes;
    }

    /**
     * Can be used to get ids to randomly select a node.
     *
     * @return All node ids.
     */
    public Integer[] getNodeIds() {
        return nodes.keySet().toArray(Integer[]::new);
    }

    public NodeGene getNode(int id) {
        return nodes.get(id);
    }

    public Map<Integer, NodeGene> getNodes() {
        return Collections.unmodifiableMap(nodes);
    }

    /**
     * Can be used to get ids to randomly select a connection.
     *
     * @return All connection ids.
     */
    public Integer[] getConnectionIds() {
        return connections.keySet().toArray(Integer[]::new);
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public float getFitness() {
        return fitness;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genome genome = (Genome) o;
        return id == genome.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Genome(%d,cons=%d,nodes=%d,fit=%f)", id, connections.size(), nodes.size(), fitness);
    }
}
