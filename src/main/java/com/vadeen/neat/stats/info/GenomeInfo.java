package com.vadeen.neat.stats.info;

import com.vadeen.neat.genome.Genome;

public class GenomeInfo {

    private final float fitness;
    private final int nodeCount;
    private final int connectionCount;

    public static GenomeInfo of(Genome g) {
        return new GenomeInfo(g.getFitness(), g.getNodes().size(), g.getConnections().size());
    }

    private GenomeInfo(float fitness, int nodeCount, int connectionCount) {
        this.fitness = fitness;
        this.nodeCount = nodeCount;
        this.connectionCount = connectionCount;
    }

    public float getFitness() {
        return fitness;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public int getConnectionCount() {
        return connectionCount;
    }
}
