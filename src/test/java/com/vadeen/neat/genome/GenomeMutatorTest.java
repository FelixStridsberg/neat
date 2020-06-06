package com.vadeen.neat.genome;

import com.vadeen.neat.BiasedRandom;
import com.vadeen.neat.gene.GeneFactory;
import com.vadeen.neat.gene.NodeGene;
import com.vadeen.neat.io.NeatIO;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenomeMutatorTest {

    private Random mockedRandom;
    private BiasedRandom biasedRandom;

    @Before
    public void setUp() throws Exception {
        mockedRandom = mock(Random.class);
        biasedRandom = new BiasedRandom(mockedRandom, 1.0f);
    }

    @Test
    public void testNodeMutation() throws IOException {
        GeneFactory geneFactory = new GeneFactory();
        GenomeValidator validator = new GenomeValidator();
        GenomeMutator mutator = new GenomeMutator(biasedRandom, geneFactory);

        mutator.setNodeMutationProbability(1.0f);
        mutator.setConnectionMutationProbability(0.0f);
        mutator.setWeightMutationProbability(0.0f);

        Genome genome = NeatIO.genomeFromResource("genomes/simple.json", geneFactory);

        assertThat(genome.getNodes().size(), is(4));
        assertThat(genome.getConnections().size(), is(3));

        // Add node on connection 1 (position 0 in array)
        when(mockedRandom.nextInt(anyInt())).thenReturn(0);

        // Perform
        mutator.addNodeMutation(genome);

        // Verify
        assertThat(genome.getNodes().size(), is(5));
        assertThat(genome.getConnections().size(), is(5));

        assertThat(genome.getConnection(4).getIn(), is(1));
        assertThat(genome.getConnection(4).getOut(), is(5));
        assertThat(genome.getConnection(5).getIn(), is(5));
        assertThat(genome.getConnection(5).getOut(), is(4));

        assertThat(validator.validate(genome), is(true));
    }

    @Test
    public void testConnectionMutation() throws IOException {
        GeneFactory geneFactory = new GeneFactory();
        GenomeValidator validator = new GenomeValidator();
        GenomeMutator mutator = new GenomeMutator(biasedRandom, geneFactory);

        mutator.setConnectionMutationProbability(1.0f);
        mutator.setNodeMutationProbability(0.0f);
        mutator.setWeightMutationProbability(0.0f);

        Genome genome = NeatIO.genomeFromResource("genomes/stanley_fig2.json", geneFactory);

        assertThat(genome.getNodes().size(), is(5));
        assertThat(genome.getConnections().size(), is(6));

        // Add connection mutation between node 3 and 5
        int node3Index = genome.getNodeIds().indexOf(3);
        int node5Index = genome.getFilteredNodeIds(NodeGene.Type.INPUT).indexOf(5);
        when(mockedRandom.nextInt(anyInt())).thenReturn(node3Index, node5Index);

        // Perform
        mutator.addConnectionMutation(genome);

        // Verify
        assertThat(genome.getNodes().size(), is(5));
        assertThat(genome.getConnections().size(), is(7));

        assertThat(genome.getConnection(7).getIn(), is(3));
        assertThat(genome.getConnection(7).getOut(), is(5));

        assertThat(validator.validate(genome), is(true));
    }

    @Test
    public void testConnectionMutationReversed() throws IOException {
        GeneFactory geneFactory = new GeneFactory();
        GenomeValidator validator = new GenomeValidator();
        GenomeMutator mutator = new GenomeMutator(biasedRandom, geneFactory);

        mutator.setWeightMutationProbability(1.0f);
        mutator.setConnectionMutationProbability(0.0f);
        mutator.setNodeMutationProbability(0.0f);

        Genome genome = NeatIO.genomeFromResource("genomes/stanley_fig2.json", geneFactory);

        assertThat(genome.getNodes().size(), is(5));
        assertThat(genome.getConnections().size(), is(6));

        // Add connection mutation between node 5 and 3 (position 4 and 2 in array)
        when(mockedRandom.nextInt(anyInt())).thenReturn(4, 2);

        // Perform
        mutator.addConnectionMutation(genome);

        // Verify that connection was added in correct direction
        assertThat(genome.getNodes().size(), is(5));
        assertThat(genome.getConnections().size(), is(7));

        assertThat(genome.getConnection(7).getIn(), is(3));
        assertThat(genome.getConnection(7).getOut(), is(5));

        assertThat(validator.validate(genome), is(true));
    }

    @Test
    public void testConnectionMutationExisting() throws IOException {
        GenomeValidator validator = new GenomeValidator();
        GenomeMutator mutator = new GenomeMutator(biasedRandom, new GeneFactory());

        mutator.setConnectionMutationProbability(1.0f);
        mutator.setNodeMutationProbability(0.0f);
        mutator.setWeightMutationProbability(0.0f);

        Genome genome = NeatIO.genomeFromResource("genomes/stanley_fig2.json");

        assertThat(genome.getNodes().size(), is(5));
        assertThat(genome.getConnections().size(), is(6));

        // Add connection mutation between node 1 and 4 (position 0 and 3 in array)
        int node1Index = genome.getNodeIds().indexOf(1);
        int node4Index = genome.getFilteredNodeIds(NodeGene.Type.INPUT).indexOf(4);
        when(mockedRandom.nextInt(anyInt())).thenReturn(node1Index, node4Index);

        // Perform
        mutator.addConnectionMutation(genome);

        // Verify that no connection was added
        assertThat(genome.getNodes().size(), is(5));
        assertThat(genome.getConnections().size(), is(6));

        assertThat(validator.validate(genome), is(true));
    }
}