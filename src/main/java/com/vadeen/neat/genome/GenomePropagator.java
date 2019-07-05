package com.vadeen.neat.genome;

import com.vadeen.neat.gene.ConnectionGene;
import com.vadeen.neat.gene.NodeGene;

import java.util.*;

/**
 * The propagator is the logic that turns a neat network into math. It may sound boring but it is very easy math and
 * is required to make use of the network.
 *
 * Basically it works like this:
 *  Input values are put into the input nodes.
 *  The value is transferred to the next node level and multiplied by connection weights of the connections used.
 *  When all node levels has been processed, the value of the output nodes are returned.
 */
public class GenomePropagator {

    private final Genome genome;
    private final Map<Integer, Float> nodeValues = new HashMap<>();

    public GenomePropagator(Genome genome) {
        this.genome = genome;
    }

    /**
     * Loads the input nodes with the input values.
     * Moves them up the levels and multiplies them with the connection weights.
     * Return values of output level when propagation is done.
     */
    public List<Float> propagate(List<Float> inputs) {
        List<List<NodeGene>> levels = genome.getLevels();
        Iterator<List<NodeGene>> levelsIterator = levels.iterator();

        // Skip level 0 (unconnected nodes).
        levelsIterator.next();

        // Get output nodes.
        List<NodeGene> outputNodes = genome.getOutputNodes();
        List<NodeGene> inputNodes = genome.getInputNodes();

        // If non of the nodes are connected.
        if (!levelsIterator.hasNext())
            return new ArrayList<>(Collections.nCopies(outputNodes.size(), 0.0f));

        // Skip input layer (it might not contain all inputs so it cannot be used to get them)
        levelsIterator.next();

        // Make sure network matches inputs.
        if (inputNodes.size() != inputs.size())
            throw new IllegalArgumentException("Number of inputs and input nodes do not match.");

        // Load input values.
        for (int i = 0; i < inputs.size(); i++) {
            nodeValues.put(inputNodes.get(i).getId(), inputs.get(i));
        }

        // Walk through the rest of the layers and propagate up the levels.
        while (levelsIterator.hasNext()) {
            propagateLevel(levelsIterator.next());
        }

        // Get output values.
        List<Float> result = new ArrayList<>();
        for (NodeGene node : outputNodes) {
            result.add(nodeValues.getOrDefault(node.getId(), 0.0f));
        }

        return result;
    }

    /**
     * Propagates one single level. Moves values from previous level into this one.
     */
    private void propagateLevel(List<NodeGene> nodes) {
        // Iterate over nodes in this layer.
        for (NodeGene n : nodes) {
            // Get sum value from all incoming nodes.
            float inputValue = calculateIncomingValue(n.getId());

            // Activation
            nodeValues.put(n.getId(), (float)sigmoid(inputValue));
        }
    }

    /**
     * Calculates the incoming value to a node by multiplying their values with the connection weight and sums them up.
     */
    private float calculateIncomingValue(int nodeId) {
        float inputValue = 0.0f;

        // Get all incoming connections.
        List<ConnectionGene> inCons = genome.getInConnections(nodeId);
        for (ConnectionGene con : inCons) {

            // Ignore non expressed genes
            if (!con.isExpressed())
                continue;

            // Value of incoming node.
            float value = nodeValues.get(con.getIn()) * con.getWeight();
            inputValue += value;
        }

        return inputValue;
    }

    /**
     * Sigmoid activation function.
     */
    private static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
}
