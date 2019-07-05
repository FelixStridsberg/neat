package com.vadeen.neat.species;

import com.vadeen.neat.genome.Genome;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SpeciesTest {

    @Test
    public void testFitness() {
        Genome g1 = mock(Genome.class);
        Genome g2 = mock(Genome.class);
        Genome g3 = mock(Genome.class);

        when(g1.getFitness()).thenReturn(100.0f);
        when(g2.getFitness()).thenReturn(200.0f);
        when(g3.getFitness()).thenReturn(600.0f);

        Species species = new Species(1, g1);
        species.addGenome(g2);
        species.addGenome(g3);

        assertThat(species.getFitness(), is(300.0f));
    }
}