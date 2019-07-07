package com.vadeen.neat.genome;

import com.vadeen.neat.Random;
import com.vadeen.neat.gene.ConnectionGene;
import com.vadeen.neat.gene.NodeGene;
import com.vadeen.neat.species.Species;

import java.util.*;


/**
 * Can create genomes in a few different ways.
 */
public class GenomeFactory {
    /**
     * Mutator for mutating genomes.
     */
    private GenomeMutator mutator;

    /**
     * Validator for validating crossed genomes. (Possible to build away?)
     */
    private GenomeValidator validator;

    /**
     * When a non expressed connection gene is inherited. This is the probability of it being expressed again.
     */
    private float reexpressProbability = 0.25f;

    /**
     * Probability of a genome being mutated after it's created by crossover.
     */
    private float breedMutationProbability = 0.8f;

    /**
     * For random numbers.
     */
    private final Random random;

    public GenomeFactory(GenomeMutator mutator, GenomeValidator validator, Random random) {
        this.mutator = mutator;
        this.validator = validator;
        this.random = random;
    }

    /**
     * Breed a random species.
     */
    public Genome breed(List<Species> species) {
        Species s = species.get(random.nextLowBiasedInt(species.size()));
        return breed(s);
    }

    /**
     * Breed a species to make a new Genome.
     */
    public Genome breed(Species species) {
        List<Genome> genomes = species.getSortedGenomes();

        int p1 = random.nextLowBiasedInt(genomes.size());
        int p2 = random.nextLowBiasedInt(genomes.size());

        Genome parent1 = genomes.get(p1);
        Genome parent2 = genomes.get(p2);
        Genome child = cross(parent1, parent2);

        // Invalid child
        if (child == null)
            return null;

        mutator.mutate(child);

        return child;
    }

    /**
     * Cross two genomes to make another.
     */
    public Genome cross(Genome parent1, Genome parent2) {
        GenomeDiff diff = GenomeDiff.diff(parent1, parent2);

        Map<Integer, ConnectionGene> connections = new HashMap<>();
        Map<Integer, NodeGene> nodes = new HashMap<>();

        // Matching genes, inherited randomly.
        Iterator<ConnectionGene> leftMatchIt = diff.getMatchingLeft().iterator();
        Iterator<ConnectionGene> rightMatchIt = diff.getMatchingRight().iterator();
        while (leftMatchIt.hasNext()) {
            ConnectionGene left = leftMatchIt.next();
            ConnectionGene right = rightMatchIt.next();

            if (random.nextBoolean())
                connections.put(left.getInnovation(), new ConnectionGene(left));
            else
                connections.put(right.getInnovation(), new ConnectionGene(right));
        }


        // Disjoint genes, inherited from most fit parent. Randomly if equal fitness.
        for (ConnectionGene con : getDisjointGenes(diff))
            connections.put(con.getInnovation(), new ConnectionGene(con));

        // Excess genes, inherited from most fit parent. Randomly if equal fitness.
        for (ConnectionGene con : getExcessGenes(diff))
            connections.put(con.getInnovation(), new ConnectionGene(con));


        // Add nodes, takes from the most fit.
        Genome mostFit = parent1;
        Genome leastFit = parent2;
        if (parent1.getFitness() < parent2.getFitness()) {
            mostFit = parent2;
            leastFit = parent1;
        } else if (parent1.getFitness() == parent2.getFitness() && random.nextBoolean()) {
            mostFit = parent2;
            leastFit = parent1;
        }

        // Always transfer input and output nodes
        for (NodeGene node : mostFit.getNodes().values()) {
            if (node.getType() != NodeGene.Type.HIDDEN)
                nodes.put(node.getId(), new NodeGene(node));
        }

        // Transfer all nodes that are referred to.
        for (ConnectionGene con : connections.values()) {
            if (!nodes.containsKey(con.getIn())) {
                NodeGene node = mostFit.getNodes().get(con.getIn());
                if (node == null)
                    node = leastFit.getNodes().get(con.getIn());
                nodes.put(node.getId(), new NodeGene(node));
            }
            if (!nodes.containsKey(con.getOut())) {
                NodeGene node = mostFit.getNodes().get(con.getOut());
                if (node == null)
                    node = leastFit.getNodes().get(con.getOut());
                nodes.put(node.getId(), new NodeGene(node));
            }
        }

        // Express none expressed genes by probability.
        for (ConnectionGene con : connections.values()) {
            if (!con.isExpressed())
                continue;

            if (random.nextFloat() < reexpressProbability)
                con.setExpressed(true);
        }

        try {
            Genome child = Genome.create(connections.values(), nodes.values());
            if (!validator.validate(child))
                return null;

            // Mutate child if probability.
            if (random.nextFloat() < breedMutationProbability)
                mutator.mutate(child);

            return child;
        } catch (IllegalArgumentException e) {
            //e.printStackTrace(); // TODO log
        }

        return null;
    }

    /**
     * Returns a mutated copy of a random genome.
     */
    public Genome copyMutate(Species species) {
        // Choose a best biased genome.
        List<Genome> sortedGenomes = species.getSortedGenomes();
        Genome genome = sortedGenomes.get(random.nextLowBiasedInt(sortedGenomes.size()));

        return copyMutate(genome);
    }

    public Genome copyMutate(Genome genome) {
        Genome newGenome = Genome.copy(genome);

        // Mutate until mutated.
        while (!mutator.mutate(newGenome));

        return newGenome;
    }

    public float getReexpressProbability() {
        return reexpressProbability;
    }

    public void setReexpressProbability(float reexpressProbability) {
        this.reexpressProbability = reexpressProbability;
    }

    public float getBreedMutationProbability() {
        return breedMutationProbability;
    }

    public void setBreedMutationProbability(float breedMutationProbability) {
        this.breedMutationProbability = breedMutationProbability;
    }

    public GenomeMutator getMutator() {
        return mutator;
    }

    public void setMutator(GenomeMutator mutator) {
        this.mutator = mutator;
    }

    public GenomeValidator getValidator() {
        return validator;
    }

    public void setValidator(GenomeValidator validator) {
        this.validator = validator;
    }

    public Random getRandom() {
        return random;
    }

    private List<ConnectionGene> getDisjointGenes(GenomeDiff diff) {
        Genome left = diff.getLeft();
        Genome right = diff.getRight();

        if (left.getFitness() > right.getFitness())
            return diff.getDisjointLeft();

        if (left.getFitness() < right.getFitness())
            return diff.getDisjointRight();

        return random(diff.getDisjointLeft(), diff.getDisjointRight());
    }

    private List<ConnectionGene> getExcessGenes(GenomeDiff diff) {
        Genome left = diff.getLeft();
        Genome right = diff.getRight();

        if (left.getFitness() > right.getFitness())
            return diff.getExcessLeft();

        if (left.getFitness() < right.getFitness())
            return diff.getExcessRight();

        return random(diff.getExcessLeft(), diff.getExcessRight());
    }

    /**
     * Returns a random number of random selected elements from left and right.
     */
    private List<ConnectionGene> random(List<ConnectionGene> left, List<ConnectionGene> right) {
        List<ConnectionGene> all = new LinkedList<>(left);
        all.addAll(right);

        if (all.size() == 0)
            return all;

        Collections.shuffle(all, random);
        return all.subList(0, random.nextInt(all.size()));
    }
}
