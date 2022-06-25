package com.vadeen.neat.generation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import com.vadeen.neat.genome.Genome;
import com.vadeen.neat.genome.GenomeEvaluator;
import com.vadeen.neat.species.Species;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

public class GenerationEvaluatorTest {

    @Captor
    ArgumentCaptor<List<Species>> speciesCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testNextGeneration() {
        // Mock
        GenerationFactory generationFactory = mock(GenerationFactory.class);
        GenomeEvaluator evaluator = mock(GenomeEvaluator.class);

        // Generation factory next turns generation1 into generation2.
        Generation generation1 = new Generation(Collections.singletonList(new Species(1, new Genome(1))));
        Generation generation2 = new Generation(Collections.singletonList(new Species(2, new Genome(2))));
        generation2.getBestGenome().setFitness(1.0f);
        given(generationFactory.next(eq(generation1))).willReturn(generation2);

        // Run
        GenerationEvaluator generationEvaluator = new GenerationEvaluator(evaluator, generationFactory, generation1);
        Generation nextGeneration = generationEvaluator.nextGeneration();

        // Verify
        assertThat(nextGeneration, is(generation2));
        assertThat(generationEvaluator.getUnimprovedCount(), is(0));

        verify(evaluator, times(1)).evaluateAll(speciesCaptor.capture());
        assertThat(speciesCaptor.getValue(), is(generation2.getSpecies()));
    }

    @Test
    public void testRefocus() {
        // Mock
        GenerationFactory generationFactory = mock(GenerationFactory.class);
        GenomeEvaluator evaluator = mock(GenomeEvaluator.class);

        // Generation factory next makes no changes.
        Generation generation1 = new Generation(Collections.singletonList(new Species(1, new Genome(1))));
        given(generationFactory.next(any())).willAnswer(a -> a.getArgument(0));

        // Generation factory refocus makes improvement.
        Generation generation2 = new Generation(Collections.singletonList(new Species(2, new Genome(2))));
        generation2.getBestGenome().setFitness(1.0f);
        given(generationFactory.refocus(eq(generation1))).willReturn(generation2);

        // Run
        GenerationEvaluator generationEvaluator = new GenerationEvaluator(evaluator, generationFactory, generation1);
        generationEvaluator.setRefocusThreshold(3);

        generationEvaluator.nextGeneration();
        generationEvaluator.nextGeneration();
        generationEvaluator.nextGeneration();
        generationEvaluator.nextGeneration(); // Refocus should occur here

        // Verify
        assertThat(generationEvaluator.getUnimprovedCount(), is(0));

        verify(evaluator, times(4)).evaluateAll(speciesCaptor.capture());
        assertThat(speciesCaptor.getAllValues(), is(List.of(
                generation1.getSpecies(), generation1.getSpecies(), generation1.getSpecies(), generation2.getSpecies())));
    }
}