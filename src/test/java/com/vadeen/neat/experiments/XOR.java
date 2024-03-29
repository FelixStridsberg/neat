package com.vadeen.neat.experiments;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import com.vadeen.neat.Neat;
import com.vadeen.neat.evaluator.SimpleEvaluator;
import com.vadeen.neat.generation.Generation;
import com.vadeen.neat.genome.Genome;
import com.vadeen.neat.genome.GenomeEvaluator;
import com.vadeen.neat.genome.GenomePropagator;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * This class sets up a complete network and teaches it to calculate xor.
 */
public class XOR {

    private final static int MAX_GENERATIONS = 300;

    private final GenomeEvaluator evaluator = new SimpleEvaluator() {
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
        for (int i = 1; i < MAX_GENERATIONS; i++) {
            generation = neat.evolve();

            // Assert generation number.
            assertThat(generation.getGenerationNumber(), is(i));

            Genome bestGenome = generation.getBestGenome();

            if (bestGenome.getFitness() < maxFitness)
                fail("Fitness decreased...");

            maxFitness = bestGenome.getFitness();
            if (bestGenome.getFitness() == 4.0f)
                break;

            if (generation.getGenerationNumber() == MAX_GENERATIONS - 1)
                fail("Did not finish XOR in " + MAX_GENERATIONS + " generations. WTF, over?");
        }

        Genome bestGenome = generation.getBestGenome();
        GenomePropagator p = new GenomePropagator(bestGenome);

        assertThat(round(p.propagate(Arrays.asList(1.0f, 1.0f)).get(0)), is(0.0f));
        assertThat(round(p.propagate(Arrays.asList(1.0f, 0.0f)).get(0)), is(1.0f));
        assertThat(round(p.propagate(Arrays.asList(0.0f, 1.0f)).get(0)), is(1.0f));
        assertThat(round(p.propagate(Arrays.asList(0.0f, 0.0f)).get(0)), is(0.0f));
    }

    /**
     * Round to 3 decimals.
     */
    private float round(float v) {
        return (float)Math.round(v * 1000.0f) / 1000.0f;
    }
}
