package com.vadeen.neat.evaluator;

import com.vadeen.neat.genome.Genome;
import com.vadeen.neat.genome.GenomeEvaluator;
import com.vadeen.neat.species.Species;

import java.util.List;

/**
 * The simple evaluator iterates all genomes and calls the evaluate method.
 */
public abstract class SimpleEvaluator implements GenomeEvaluator {

    @Override
    public void evaluateAll(List<Species> species) {
        for (Species s : species) {
            for (Genome g : s.getGenomes()) {
                g.setFitness(evaluate(g));
            }
        }
    }

    /**
     * @param genome Genome to evaluate.
     * @return A float value. The bigger, the better, the Huskaroo sweater.
     */
    public abstract float evaluate(Genome genome);
}
