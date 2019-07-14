package com.vadeen.neat.genome;

import com.vadeen.neat.Random;
import com.vadeen.neat.gene.ConnectionGene;
import com.vadeen.neat.gene.GeneFactory;
import com.vadeen.neat.gene.NodeGene;

import java.util.List;
import java.util.Set;

/**
 * The genome mutator mutate genomes to change their network. There are multiple ways a genome can be mutated, which way
 * is selected is randomized according to the probability suffixes settings. How severe the mutation is are configured
 * with the factor suffixed settings.
 *
 * The default settings are maybe suitable for some situations, but you most likely want to tweak them for good progress.
 * The more node mutations, the faster the network gets complex.
 */
public class GenomeMutator {

    /**
     * Used for generating random numbers. (Surprised?)
     */
    private Random random;

    /**
     * We use the gene factory to create genes, it keeps track of the ids and innovations.
     */
    private GeneFactory geneFactory;

    /**
     * The probability of a node is added during mutation.
     */
    private float nodeMutationProbability = 0.03f;

    /**
     * The probability of a connection being added during mutation.
     */
    private float connectionMutationProbability = 0.3f;

    /**
     * The probability of the weights being mutated during mutation.
     */
    private float weightMutationProbability = 0.9f;

    /**
     * The probability of a weight being replaced with a random value during weight mutation.
     */
    private float weightMutationRenewProbability = 0.1f;

    /**
     * The factor of which weights are perturbed during weight mutation.
     */
    private float weightPerturbingFactor = 3.0f;

    public GenomeMutator(Random random, GeneFactory geneFactory) {
        this.random = random;
        this.geneFactory = geneFactory;
    }

    /**
     * @return true if genome was mutated.
     */
    public boolean mutate(Genome genome) {
        boolean mutated = false;

        if (random.nextFloat() < weightMutationProbability) {
            weightMutation(genome);
            mutated = true;
        }

        if (random.nextFloat() < nodeMutationProbability) {
            addNodeMutation(genome);
            mutated = true;
        }

        if (random.nextFloat() < connectionMutationProbability) {
            addConnectionMutation(genome);
            mutated = true;
        }

        return mutated;
    }

    /**
     * Randomly mutates a weight of a random connection.
     */
    public void weightMutation(Genome genome) {
        genome.getConnections().values().forEach(c -> {
            if (random.nextFloat() < weightMutationRenewProbability) {
                // Randomize weight
                c.setWeight(random.nextFloat()*200.0f - 100.0f);
            } else {
                // Perturb weight
                c.setWeight(c.getWeight() + (random.nextFloat() * (weightPerturbingFactor * 2.0f) - weightPerturbingFactor));
            }
        });
    }

    /**
     * Adds a new connection between two previously not connected nodes.
     * This mutation might not do anything in case the selected random nodes already have a connection or are the same.
     */
    public void addConnectionMutation(Genome genome) {
        List<Integer> nodeIds = genome.getNodeIds();
        int id1 = nodeIds.remove(random.nextInt(nodeIds.size()));
        NodeGene node1 = genome.getNode(id1);

        // If node1 is not HIDDEN, node2 cannot be of the same type. (in -> in and out -> out connections are forbidden)
        if (node1.getType() != NodeGene.Type.HIDDEN)
            nodeIds = genome.getFilteredNodeIds(node1.getType());

        int id2 = nodeIds.get(random.nextInt(nodeIds.size()));
        NodeGene node2 = genome.getNode(id2);

        // Make sure we add connection in the right direction.
        if (node1.getType().getOrder() > node2.getType().getOrder()) {
            NodeGene tmpNode = node1;
            node1 = node2;
            node2 = tmpNode;
        }

        // If connection already exists.
        if (genome.hasConnection(node1.getId(), node2.getId()))
            return;

        try {
            genome.addConnection(geneFactory.createConnection(node1.getId(), node2.getId(), 1.0f));
        } catch (IllegalArgumentException e) {
            //System.out.println("MUTATION: " + e.getMessage()); // TODO
        }
    }

    /**
     * Picks a random connection and ads a new node to it. The existing connection is kept but disabled.
     * Input connection to the new node get weight 1.0f. Output connection gets weight of the disabled connection.
     */
    public void addNodeMutation(Genome genome) {
        Integer[] connectionIds = genome.getConnectionIds();

        // There is no connections on this genome.
        if (connectionIds.length == 0)
            return;

        int id = connectionIds[random.nextInt(connectionIds.length)];

        ConnectionGene con = genome.getConnection(id);
        con.setExpressed(false);


        NodeGene node = geneFactory.createNode(NodeGene.Type.HIDDEN);
        genome.addNode(node);
        ConnectionGene inCon = geneFactory.createConnection(con.getIn(), node.getId(), 1.0f);
        ConnectionGene outCon = geneFactory.createConnection(node.getId(), con.getOut(), con.getWeight());

        genome.addConnection(inCon);
        genome.addConnection(outCon);
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public GeneFactory getGeneFactory() {
        return geneFactory;
    }

    public void setGeneFactory(GeneFactory geneFactory) {
        this.geneFactory = geneFactory;
    }

    public float getNodeMutationProbability() {
        return nodeMutationProbability;
    }

    public void setNodeMutationProbability(float nodeMutationProbability) {
        this.nodeMutationProbability = nodeMutationProbability;
    }

    public float getConnectionMutationProbability() {
        return connectionMutationProbability;
    }

    public void setConnectionMutationProbability(float connectionMutationProbability) {
        this.connectionMutationProbability = connectionMutationProbability;
    }

    public float getWeightMutationProbability() {
        return weightMutationProbability;
    }

    public void setWeightMutationProbability(float weightMutationProbability) {
        this.weightMutationProbability = weightMutationProbability;
    }

    public float getWeightMutationRenewProbability() {
        return weightMutationRenewProbability;
    }

    public void setWeightMutationRenewProbability(float weightMutationRenewProbability) {
        this.weightMutationRenewProbability = weightMutationRenewProbability;
    }

    public float getWeightPerturbingFactor() {
        return weightPerturbingFactor;
    }

    public void setWeightPerturbingFactor(float weightPerturbingFactor) {
        this.weightPerturbingFactor = weightPerturbingFactor;
    }
}
