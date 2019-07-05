package com.vadeen.neat.generation;

import com.vadeen.neat.genome.Genome;
import com.vadeen.neat.genome.GenomeEvaluator;
import com.vadeen.neat.species.Species;

import java.util.ArrayList;
import java.util.List;

/**
 * The generation evaluator keeps track of a stream of generations.
 * Give it one and you can get the future generations.
 */
public class GenerationEvaluator {

    /**
     * Evaluator to evaluate the fitness of each genome.
     */
    private GenomeEvaluator evaluator;

    /**
     * The current generation.
     */
    private Generation generation;

    /**
     * Factory for creating new generations.
     */
    private GenerationFactory generationFactory;

    /**
     * Counts how many generations has not improved in fitness.
     */
    private int unimprovedCount = 0;

    /**
     * If the unimproved count reaches this value, the next generation will be refocused.
     */
    private int refocusThreshold = 20;

    /**
     * @param evaluator Evaluator to evaluate fitness of each genome.
     * @param generation First generation.
     */
    public GenerationEvaluator(GenomeEvaluator evaluator, GenerationFactory generationFactory, Generation generation) {
        this.evaluator = evaluator;
        this.generation = generation;
        this.generationFactory = generationFactory;
    }

    /**
     * Progress to the next generation.
     */
    public Generation nextGeneration() {
        Generation oldGeneration = generation;

        // If we haven't improved for long enough. It's time to refocus.
        if (unimprovedCount >= refocusThreshold) {
            oldGeneration = generationFactory.refocus(oldGeneration);
            unimprovedCount = 0;
        }

        // Woho we have the next generation! Lets hope they don't let us down.
        generation = generationFactory.next(oldGeneration);

        // Evaluate all genomes.
        evaluate(generation);

        // If generation did not improve in fitness. Increase counter.
        if (oldGeneration.getBestGenome().getFitness() == generation.getBestGenome().getFitness())
            unimprovedCount++;

        return generation;
    }

    public GenomeEvaluator getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(GenomeEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    public Generation getGeneration() {
        return generation;
    }

    public GenerationFactory getGenerationFactory() {
        return generationFactory;
    }

    public void setGenerationFactory(GenerationFactory generationFactory) {
        this.generationFactory = generationFactory;
    }

    public int getUnimprovedCount() {
        return unimprovedCount;
    }

    public int getRefocusThreshold() {
        return refocusThreshold;
    }

    public void setRefocusThreshold(int refocusThreshold) {
        this.refocusThreshold = refocusThreshold;
    }

    /**
     * Evaluate the genomes.
     */
    private void evaluate(Generation generation) {
        List<Genome> genomes = new ArrayList<>();
        for (Species s : generation.getSpecies())
            genomes.addAll(s.getGenomes());

        evaluator.evaluateAll(genomes);
    }
}
