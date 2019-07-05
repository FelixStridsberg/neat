package com.vadeen.neat.experiments;

import com.vadeen.neat.Neat;
import com.vadeen.neat.evaluators.SimpleEvaluator;
import com.vadeen.neat.generation.Generation;
import com.vadeen.neat.genome.Genome;
import com.vadeen.neat.genome.GenomeEvaluator;
import com.vadeen.neat.genome.GenomePropagator;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * This class sets up a complete network and teaches it to calculate xor.
 */
public class XOR {

    private final int MAX_GENERATIONS = 300;

    private GenomeEvaluator evaluator = new SimpleEvaluator() {
        private final List<List<Float>> input = Arrays.asList(
                Arrays.asList(1.0f, 1.0f),
                Arrays.asList(1.0f, 0.0f),
                Arrays.asList(0.0f, 1.0f),
                Arrays.asList(0.0f, 0.0f)
        );

        private final List<Float> expectedOutput = Arrays.asList(
                0.0f,
                1.0f,
                1.0f,
                0.0f
        );

        @Override
        public float evaluate(Genome genome) {
            GenomePropagator propagator = new GenomePropagator(genome);

            float fitness = 4;
            for (int i = 0; i < input.size(); i++) {
                Float result = propagator.propagate(input.get(i)).get(0);
                fitness -= Math.pow((result - expectedOutput.get(i)), 2);
            }

            return fitness;
        }
    };

    @Test
    public void runXOR() {
        Neat neat = Neat.create(evaluator, 2, 1);
        neat.getSpeciesFactory().setDistanceThreshold(10);
        neat.getGenerationFactory().setPopulationSize(150);

        float maxFitness = 0;

        // Loop through generations until we have a result, or give up.
        Generation generation = null;
        for (int i = 0; i < MAX_GENERATIONS; i++) {
            generation = neat.evolve();

            Genome bestGenome = generation.getBestGenome();

            if (bestGenome.getFitness() < maxFitness)
                fail("Fitness decreased...");

            maxFitness = bestGenome.getFitness();
            if (bestGenome.getFitness() == 4.0f)
                break;

            if (i == MAX_GENERATIONS - 1)
                fail("Did not finish XOR in " + MAX_GENERATIONS + " generations. WTF, over?");
        }

        Genome bestGenome = generation.getBestGenome();
        GenomePropagator p = new GenomePropagator(bestGenome);

        assertThat(found(p.propagate(Arrays.asList(1.0f, 1.0f)).get(0)), is(0.0f));
        assertThat(found(p.propagate(Arrays.asList(1.0f, 0.0f)).get(0)), is(1.0f));
        assertThat(found(p.propagate(Arrays.asList(0.0f, 1.0f)).get(0)), is(1.0f));
        assertThat(found(p.propagate(Arrays.asList(0.0f, 0.0f)).get(0)), is(0.0f));
    }

    /**
     * Round to 3 decimals.
     */
    private float found(float v) {
        return (float)Math.round(v * 1000.0f) / 1000.0f;
    }
}
