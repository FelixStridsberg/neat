package com.vadeen.neat.genome;

import java.util.List;

/**
 * The evaluator is the magic that determines how good a genome is in comparison to all its buddies.
 */
public interface GenomeEvaluator {

    /**
     * Evaluates all genomes and sets the fitness.
     * @param genomes The genomes to be evaluated.
     */
    void evaluateAll(List<Genome> genomes);
}
