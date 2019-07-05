package com.vadeen.neat.genome;

import com.vadeen.neat.gene.ConnectionGene;
import com.vadeen.neat.io.NeatIO;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GenomeDiffTest {

    @Test
    public void testStanleyFig4() throws IOException {
        Genome g1 = NeatIO.genomeFromResource("genomes/stanley_fig4_p1.json");
        Genome g2 = NeatIO.genomeFromResource("genomes/stanley_fig4_p2.json");

        // Diff in both directions
        GenomeDiff diffLeft = GenomeDiff.diff(g1, g2);
        GenomeDiff diffRight = GenomeDiff.diff(g2, g1);


        // Assert the same result in both directions
        assertThat(diffLeft.getDisjointLeft(), is(diffRight.getDisjointRight()));
        assertThat(diffLeft.getDisjointRight(), is(diffRight.getDisjointLeft()));

        assertThat(diffLeft.getExcessLeft(), is(diffRight.getExcessRight()));
        assertThat(diffLeft.getExcessRight(), is(diffRight.getExcessLeft()));

        assertThat(diffLeft.getMatchingLeft(), is(diffRight.getMatchingRight()));
        assertThat(diffLeft.getMatchingRight(), is(diffRight.getMatchingLeft()));


        // Assert the diff contains the correct elements
        GenomeDiff diff = diffLeft;


        // Disjoint
        assertThat(diff.getDisjointLeft(), is(Arrays.asList(
                new ConnectionGene(1, 5, 1.0f, true, 8)
        )));
        assertThat(diff.getDisjointRight(), is(Arrays.asList(
                new ConnectionGene(5, 6, 2.0f, true, 6),
                new ConnectionGene(6, 4, 2.0f, true, 7)
        )));


        // Excess
        assertThat(diff.getExcessLeft(), is(new LinkedList<>()));
        assertThat(diff.getExcessRight(), is(Arrays.asList(
                new ConnectionGene(3, 5, 2.0f, true, 9),
                new ConnectionGene(1, 6, 2.0f, true, 10)
        )));


        // Matching
        assertThat(diff.getMatchingLeft(), is(Arrays.asList(
                new ConnectionGene(1, 4, 1.0f, true, 1),
                new ConnectionGene(2, 4, 1.0f, false, 2),
                new ConnectionGene(3, 4, 1.0f, true, 3),
                new ConnectionGene(2, 5, 1.0f, true, 4),
                new ConnectionGene(5, 4, 1.0f, true, 5)
        )));
        assertThat(diff.getMatchingRight(), is(Arrays.asList(
                new ConnectionGene(1, 4, 2.0f, true, 1),
                new ConnectionGene(2, 4, 2.0f, false, 2),
                new ConnectionGene(3, 4, 2.0f, true, 3),
                new ConnectionGene(2, 5, 2.0f, true, 4),
                new ConnectionGene(5, 4, 2.0f, false, 5)
        )));
    }
}