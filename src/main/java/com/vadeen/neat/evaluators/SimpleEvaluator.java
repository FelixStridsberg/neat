package com.vadeen.neat.evaluators;

import com.vadeen.neat.genome.Genome;
import com.vadeen.neat.genome.GenomeEvaluator;

import java.util.List;

/**
 * The simple evaluator iterates all genomes and calls the evaluate method.
 */
public abstract class SimpleEvaluator implements GenomeEvaluator {

    @Override
    public void evaluateAll(List<Genome> genomes) {
        for (Genome g : genomes) {
            g.setFitness(evaluate(g));
        }
    }

    /**
     * @param genome Genome to evaluate.
     * @return A float value. The bigger, the better, the Huskaroo sweater.
     */
    public abstract float evaluate(Genome genome);
}
