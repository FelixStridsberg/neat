package com.vadeen.neat.evaluator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.vadeen.neat.genome.Genome;
import com.vadeen.neat.species.Species;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SimpleEvaluatorTest {

    @Test
    public void testSimpleEvaluator() {
        // Simple evaluator that sets the genome id as fitness.
        SimpleEvaluator simpleEvaluator = new SimpleEvaluator() {
            @Override
            public float evaluate(Genome genome) {
                return genome.getId();
            }
        };

        // Setup species.
        Genome g1 = new Genome(1);
        Genome g2 = new Genome(2);

        Species species = new Species(1, g1);
        species.addGenome(g2);

        // Evaluate.
        simpleEvaluator.evaluateAll(List.of(species));

        // Verify.
        assertThat(g1.getFitness(), is(1.0f));
        assertThat(g2.getFitness(), is(2.0f));
    }
}