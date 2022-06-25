package com.vadeen.neat.genome;

import com.vadeen.neat.gene.ConnectionGene;
import com.vadeen.neat.gene.NodeGene;
import com.vadeen.neat.io.NeatIO;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class GenomeTest {

    @Test
    public void testGetConnectionByInnovation() throws IOException {
        Genome genome = NeatIO.genomeFromResource("genomes/simple.json");
        assertThat(genome.getConnection(1), is(new ConnectionGene(1, 4, 1.0f, true, 1)));
        assertThat(genome.getConnection(2), is(new ConnectionGene(2, 4, 1.0f, true, 2)));
        assertThat(genome.getConnection(3), is(new ConnectionGene(3, 4, 1.0f, true, 3)));
    }

    @Test
    public void testGetNodeById() throws IOException {
        Genome genome = NeatIO.genomeFromResource("genomes/simple.json");
        assertThat(genome.getNode(1), is(new NodeGene(NodeGene.Type.INPUT, 1)));
        assertThat(genome.getNode(2), is(new NodeGene(NodeGene.Type.INPUT, 2)));
        assertThat(genome.getNode(3), is(new NodeGene(NodeGene.Type.INPUT, 3)));
        assertThat(genome.getNode(4), is(new NodeGene(NodeGene.Type.OUTPUT, 4)));
    }

    @Test
    public void testGetLevelsSimple() throws IOException {
        Genome genome = NeatIO.genomeFromResource("genomes/simple.json");
        List<List<NodeGene>> levels = genome.getLevels();

        assertThat(levels.size(), is(3));
        assertThat(levels.get(0).size(), is(0));    // Level 0
        assertThat(levels.get(1).size(), is(3));    // Level 1
        assertThat(levels.get(2).size(), is(1));    // Level 2
    }

    @Test
    public void testDeepCopy() throws IOException {
        Genome genome = NeatIO.genomeFromResource("genomes/simple.json");
        Genome copy = Genome.copy(genome);

        // Assert that they equals...
        assertThat(genome.getNode(1), is(copy.getNode(1)));
        assertThat(genome.getNode(2), is(copy.getNode(2)));
        assertThat(genome.getNode(3), is(copy.getNode(3)));
        assertThat(genome.getConnection(1), is(copy.getConnection(1)));
        assertThat(genome.getConnection(2), is(copy.getConnection(2)));

        // ... but are not the same objects.
        assertNotSame(genome.getNode(1), copy.getNode(1));
        assertNotSame(genome.getNode(2), copy.getNode(2));
        assertNotSame(genome.getNode(3), copy.getNode(3));
        assertNotSame(genome.getConnection(1), copy.getConnection(1));
        assertNotSame(genome.getConnection(2), copy.getConnection(2));
    }
}