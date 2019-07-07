package com.vadeen.neat.io.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vadeen.neat.Neat;
import com.vadeen.neat.gene.GeneFactory;
import com.vadeen.neat.generation.GenerationEvaluator;
import com.vadeen.neat.generation.GenerationFactory;
import com.vadeen.neat.genome.Genome;
import com.vadeen.neat.genome.GenomeComparator;
import com.vadeen.neat.genome.GenomeFactory;
import com.vadeen.neat.genome.GenomeMutator;
import com.vadeen.neat.species.SpeciesFactory;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class NeatJsonTest {

    @Test
    public void testGenerateJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Neat neat = Neat.create(null, 2, 1);

        // Set values
        // Mutator
        GenomeMutator mutator = neat.getMutator();
        mutator.setNodeMutationProbability(1001.0f);
        mutator.setConnectionMutationProbability(1002.0f);
        mutator.setWeightMutationProbability(1003.0f);
        mutator.setWeightMutationRenewProbability(1004.0f);
        mutator.setWeightPerturbingFactor(1005.0f);

        // Genome factory
        GenomeFactory genomeFactory = neat.getGenomeFactory();
        genomeFactory.setBreedMutationProbability(2001.0f);
        genomeFactory.setReexpressProbability(2002.0f);

        // Genome comparator
        GenomeComparator comparator = neat.getGenomeComparator();
        comparator.setExcessFactor(3001.0f);
        comparator.setDisjointFactor(3002.0f);
        comparator.setWeightDiffFactor(3003.0f);
        comparator.setNormalizeThreshold(3004);

        // Species factory
        SpeciesFactory speciesFactory = neat.getSpeciesFactory();
        speciesFactory.setDistanceThreshold(4001);

        // Generation factory
        GenerationFactory generationFactory = neat.getGenerationFactory();
        generationFactory.setPopulationSize(5004);
        generationFactory.setRefocusSpeciesCount(5005);
        generationFactory.setOffspringByMutation(5005.0f);
        generationFactory.setMaxSpeciesProportion(5005.0f);

        // Generation evaluator
        GenerationEvaluator generationEvaluator = neat.getGenerationEvaluator();
        generationEvaluator.setUnimprovedCount(6001);
        generationEvaluator.setRefocusThreshold(6002);
        generationEvaluator.setGenerationCounter(6003);

        // Gene factory
        GeneFactory geneFactory = neat.getGeneFactory();
        geneFactory.setConnectionCounter(7001);
        geneFactory.setNodeCounter(7002);

        // Genome in generation
        neat.getGenerationEvaluator().getGeneration().getBestGenome().setFitness(100);


        // Write json
        NeatJson neatJson = NeatJson.of(neat);
        String json = mapper.writeValueAsString(neatJson);

        // Read json
        NeatJson readJson = mapper.readValue(json, NeatJson.class);
        Neat readNeat = NeatJson.toNeat(null, readJson);



        // Check all values are the same
        // Mutator
        GenomeMutator readMutator = readNeat.getMutator();
        assertEquals(readMutator.getNodeMutationProbability(), 1001.0f, 0.0f);
        assertEquals(readMutator.getConnectionMutationProbability(), 1002.0f, 0.0f);
        assertEquals(readMutator.getWeightMutationProbability(), 1003.0f, 0.0f);
        assertEquals(readMutator.getWeightMutationRenewProbability(), 1004.0f, 0.0f);
        assertEquals(readMutator.getWeightPerturbingFactor(), 1005.0f, 0.0f);

        // Genome factory
        GenomeFactory readGenomeFactory = readNeat.getGenomeFactory();
        assertEquals(readGenomeFactory.getBreedMutationProbability(), 2001.0f, 0.0f);
        assertEquals(readGenomeFactory.getReexpressProbability(), 2002.0f, 0.0f);

        // Genome comparator
        GenomeComparator readComparator = readNeat.getGenomeComparator();
        assertEquals(readComparator.getExcessFactor(), 3001.0f, 0.0f);
        assertEquals(readComparator.getDisjointFactor(), 3002.0f, 0.0f);
        assertEquals(readComparator.getWeightDiffFactor(), 3003.0f, 0.0f);
        assertEquals(readComparator.getNormalizeThreshold(), 3004);

        // Species factory
        SpeciesFactory readSpeciesFactory = readNeat.getSpeciesFactory();
        assertEquals(readSpeciesFactory.getDistanceThreshold(), 4001);

        // Generation factory
        GenerationFactory readGenerationFactory = readNeat.getGenerationFactory();
        assertEquals(readGenerationFactory.getPopulationSize(), 5004);
        assertEquals(readGenerationFactory.getRefocusSpeciesCount(), 5005);
        assertEquals(readGenerationFactory.getOffspringByMutation(), 5005.0f, 0.0f);
        assertEquals(readGenerationFactory.getMaxSpeciesProportion(), 5005.0f, 0.0f);

        // Generation evaluator
        GenerationEvaluator readGenerationEvaluator = readNeat.getGenerationEvaluator();
        assertEquals(readGenerationEvaluator.getUnimprovedCount(), 6001);
        assertEquals(readGenerationEvaluator.getRefocusThreshold(), 6002);
        assertEquals(readGenerationEvaluator.getGenerationCounter(), 6003);

        // Gene factory
        GeneFactory readGeneFactory = readNeat.getGeneFactory();
        assertEquals(readGeneFactory.getConnectionCounter(), 7001);
        assertEquals(readGeneFactory.getNodeCounter(), 7002);

        // Species
        assertEquals(readGenerationEvaluator.getGeneration().getSpecies().size(), 1);
        assertEquals(readGenerationEvaluator.getGeneration().getSpecies().get(0).getGenomes().size(), 1);

        // Number of out and in nodes in a genome
        Genome readGenome = readGenerationEvaluator.getGeneration().getSpecies().get(0).getGenomes().get(0);
        assertEquals(readGenome.getInputNodes().size(), 2);
        assertEquals(readGenome.getOutputNodes().size(), 1);
        assertEquals(readGenome.getFitness(), 100.0f, 0.0f);
    }
}