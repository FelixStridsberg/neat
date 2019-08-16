package com.vadeen.neat.generation;

import com.vadeen.neat.gene.ConnectionGene;
import com.vadeen.neat.gene.NodeGene;
import com.vadeen.neat.genome.Genome;
import com.vadeen.neat.genome.GenomeComparator;
import com.vadeen.neat.genome.GenomeFactory;
import com.vadeen.neat.species.Species;
import com.vadeen.neat.species.SpeciesFactory;
import com.vadeen.neat.tree.LevelTree;
import com.vadeen.neat.tree.LevelTreeConnectionIndex;
import org.junit.Test;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;

public class GenerationFactoryTest {

    private static final Answer<Genome> copyBestGenome = invocationOnMock -> {
        Species s = invocationOnMock.getArgument(0);
        return Genome.copy(s.getBestGenome());
    };

    private static final Answer<Genome> copyBestGenomeFromList = invocationOnMock -> {
        List<Species> s = invocationOnMock.getArgument(0);
        return Genome.copy(s.get(0).getBestGenome());
    };


    @Test
    public void testNextGeneration() throws NoSuchFieldException {
        GenomeComparator comparator = new GenomeComparator();
        SpeciesFactory speciesFactory = new SpeciesFactory(comparator);
        GenomeFactory genomeFactory = mock(GenomeFactory.class);
        GenerationFactory generationFactory = new GenerationFactory(genomeFactory, speciesFactory);

        Species s1 = generateSpecies(speciesFactory, 2, 60.0f);
        Species s2 = generateSpecies(speciesFactory, 10, 30.0f);
        Species s3 = generateSpecies(speciesFactory, 100, 10.0f);

        when(genomeFactory.breed(any(Species.class))).thenAnswer(copyBestGenome);
        when(genomeFactory.breed(anyList())).thenAnswer(copyBestGenomeFromList);
        when(genomeFactory.copyMutate(any(Species.class))).thenAnswer(copyBestGenome);

        List<Species> species = Arrays.asList(s1, s2, s3);
        Generation generation = new Generation(species);
        generationFactory.next(generation);

        // BREEDING
        verify(genomeFactory, times(66)).breed(eq(s1));
        verify(genomeFactory, times(32)).breed(eq(s2));
        verify(genomeFactory, times(10)).breed(eq(s3));

        // MUTATION
        verify(genomeFactory, times(22)).copyMutate(eq(s1));
        verify(genomeFactory, times(11)).copyMutate(eq(s2));
        verify(genomeFactory, times(3)).copyMutate(eq(s3));

        // Population is 150.
        verify(genomeFactory, times(3)).breed(anyList());
    }

    /**
     * Generate a species with specific population and adjusted fitness.
     */
    private Species generateSpecies(SpeciesFactory speciesFactory, int population, float fitness) throws NoSuchFieldException {
        Genome refGenome = mockGenome();
        when(refGenome.getFitness()).thenReturn(fitness);

        HashMap<Integer, NodeGene> nodes = new HashMap<>();
        nodes.put(1, new NodeGene(NodeGene.Type.INPUT, 1));

        HashMap<Integer, ConnectionGene> connections = new HashMap<>();
        connections.put(1, new ConnectionGene(1, 2, 0.0f, true, 1));

        Species species = speciesFactory.createSpecies(refGenome);
        for (int i = 0; i < population; i++) {
            Genome genome = mockGenome();
            when(genome.getFitness()).thenReturn(fitness);
            when(genome.getNodes()).thenReturn(nodes);
            when(genome.getConnections()).thenReturn(connections);
        }

        return species;
    }

    private Genome mockGenome() throws NoSuchFieldException {
        Genome genome = mock(Genome.class);
        FieldSetter.setField(genome, LevelTree.class.getDeclaredField("connectionIndex"), new LevelTreeConnectionIndex());
        return genome;
    }
}