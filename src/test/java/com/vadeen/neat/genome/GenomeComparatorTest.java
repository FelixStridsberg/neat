package com.vadeen.neat.genome;

import com.vadeen.neat.io.NeatIO;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GenomeComparatorTest {

    @Test
    public void testDistanceCompareSame() throws IOException {
        Genome g1 = NeatIO.genomeFromResource("genomes/simple.json");
        Genome g2 = NeatIO.genomeFromResource("genomes/simple.json");

        GenomeComparator comparator = new GenomeComparator();
        comparator.setExcessFactor(1);
        comparator.setDisjointFactor(1);
        comparator.setWeightDiffFactor(1);

        assertThat(comparator.calculateDistance(g1, g2), is(0.0f));
    }

    @Test
    public void testDistanceCompareSimpleFig2() throws IOException {
        Genome g1 = NeatIO.genomeFromResource("genomes/simple.json");
        Genome g2 = NeatIO.genomeFromResource("genomes/stanley_fig2.json");

        GenomeComparator comparator = new GenomeComparator();
        comparator.setExcessFactor(10);
        comparator.setDisjointFactor(1);
        comparator.setWeightDiffFactor(1);

        assertThat(comparator.calculateDistance(g1, g2), is(30.0f));
    }

    @Test
    public void testDistanceCompareFig4() throws IOException {
        Genome g1 = NeatIO.genomeFromResource("genomes/stanley_fig4_p1.json");
        Genome g2 = NeatIO.genomeFromResource("genomes/stanley_fig4_p2.json");

        GenomeComparator comparator = new GenomeComparator();
        comparator.setExcessFactor(1);
        comparator.setDisjointFactor(2);
        comparator.setWeightDiffFactor(3);

        assertThat(comparator.calculateDistance(g1, g2), is(11.0f));
    }
}