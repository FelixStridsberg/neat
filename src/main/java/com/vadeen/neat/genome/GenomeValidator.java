package com.vadeen.neat.genome;

import com.vadeen.neat.gene.NodeGene;

/**
 * The validator is used during crossover to see if the child is retarded or not. If it is we make a quick abortion and
 * get back to work making child genomes.
 *
 * This class should probably not be required if the crossover function is working properly. But it do not so we need
 * this until it does.
 */
public class GenomeValidator {

    public boolean validate(Genome genome) {
        for (NodeGene node : genome.getNodes().values()) {
            if (!validateNode(genome, node))
                return false;
        }

        return true;
    }

    private boolean validateNode(Genome genome, NodeGene node) {
        switch (node.getType()) {
            case HIDDEN:
                return validateHiddenNode(genome, node.getId());

            case OUTPUT:
                return validateOutputNode(genome, node.getId());

            case INPUT:
                return validateInputNode(genome, node.getId());
        }

        throw new RuntimeException("Could not validate node of unknown type: " + node.getType());
    }

    private boolean validateHiddenNode(Genome genome, int nodeId) {
        return hasNodeIncoming(genome, nodeId) && hasNodeOutgoing(genome, nodeId);
    }

    private boolean validateOutputNode(Genome genome, int nodeId) {
        return !hasNodeOutgoing(genome, nodeId);
    }

    private boolean validateInputNode(Genome genome, int nodeId) {
        return !hasNodeIncoming(genome, nodeId);
    }

    private boolean hasNodeOutgoing(Genome genome, int nodeId) {
        return !genome.getOutConnections(nodeId).isEmpty();
    }

    private boolean hasNodeIncoming(Genome genome, int nodeId) {
        return !genome.getInConnections(nodeId).isEmpty();
    }
}
