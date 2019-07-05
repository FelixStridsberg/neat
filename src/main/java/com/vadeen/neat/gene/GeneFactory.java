package com.vadeen.neat.gene;

/**
 * The gene factory keeps track of innovation numbers.
 */
public class GeneFactory {

    /**
     * Keeps track of the connection innovation. Increased by 1 at every connection creation.
     */
    private int connectionCounter = 0;

    /**
     * Keeps track of the node id. Increased by 1 at every node creation.
     */
    private int nodeCounter = 0;

    /**
     * Creates connection gene with innovation number starting from 1 and increasing for every creation.
     * Expressed is always true.
     */
    public ConnectionGene createConnection(int in, int out, float weight) {
        return new ConnectionGene(in, out, weight, true, ++connectionCounter);
    }

    /**
     * Creates new connection with provided innovation number.
     * Internal innovation counter is updated with the provided innovation if it is higher than current.
     *
     * Used to recreate genomes from file.
     */
    public ConnectionGene createConnection(int in, int out, float weight, boolean expressed, int innovation) {
        if (connectionCounter < innovation)
            connectionCounter = innovation;

        return new ConnectionGene(in, out, weight, expressed, innovation);
    }

    /**
     * Create a node, id is assigned by a counter.
     */
    public NodeGene createNode(NodeGene.Type type) {
        return new NodeGene(type, ++nodeCounter);
    }

    /**
     * Create a node and update the id counter of this factory.
     *
     * Used to recreate genomes from file.
     */
    public NodeGene createNode(NodeGene.Type type, int id) {
        if (nodeCounter < id)
            nodeCounter = id;

        return new NodeGene(type, id);
    }

    public int getConnectionCounter() {
        return connectionCounter;
    }

    public void setConnectionCounter(int connectionCounter) {
        this.connectionCounter = connectionCounter;
    }

    public int getNodeCounter() {
        return nodeCounter;
    }

    public void setNodeCounter(int nodeCounter) {
        this.nodeCounter = nodeCounter;
    }
}

