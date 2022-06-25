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
import java.io.IOException;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
        Neat readNeat = readJson.toNeat(null);



        // Check all values are the same
        // Mutator
        GenomeMutator readMutator = readNeat.getMutator();
        assertThat(readMutator.getNodeMutationProbability(), is(1001.0f));
        assertThat(readMutator.getConnectionMutationProbability(), is(1002.0f));
        assertThat(readMutator.getWeightMutationProbability(), is(1003.0f));
        assertThat(readMutator.getWeightMutationRenewProbability(), is(1004.0f));
        assertThat(readMutator.getWeightPerturbingFactor(), is(1005.0f));

        // Genome factory
        GenomeFactory readGenomeFactory = readNeat.getGenomeFactory();
        assertThat(readGenomeFactory.getBreedMutationProbability(), is(2001.0f));
        assertThat(readGenomeFactory.getReexpressProbability(), is(2002.0f));

        // Genome comparator
        GenomeComparator readComparator = readNeat.getGenomeComparator();
        assertThat(readComparator.getExcessFactor(), is(3001.0f));
        assertThat(readComparator.getDisjointFactor(), is(3002.0f));
        assertThat(readComparator.getWeightDiffFactor(), is(3003.0f));
        assertThat(readComparator.getNormalizeThreshold(), is(3004));

        // Species factory
        SpeciesFactory readSpeciesFactory = readNeat.getSpeciesFactory();
        assertThat(readSpeciesFactory.getDistanceThreshold(), is(4001));

        // Generation factory
        GenerationFactory readGenerationFactory = readNeat.getGenerationFactory();
        assertThat(readGenerationFactory.getPopulationSize(), is(5004));
        assertThat(readGenerationFactory.getRefocusSpeciesCount(), is(5005));
        assertThat(readGenerationFactory.getOffspringByMutation(), is(5005.0f));
        assertThat(readGenerationFactory.getMaxSpeciesProportion(), is(5005.0f));

        // Generation evaluator
        GenerationEvaluator readGenerationEvaluator = readNeat.getGenerationEvaluator();
        assertThat(readGenerationEvaluator.getUnimprovedCount(), is(6001));
        assertThat(readGenerationEvaluator.getRefocusThreshold(), is(6002));
        assertThat(readGenerationEvaluator.getGenerationCounter(), is(6003));

        // Gene factory
        GeneFactory readGeneFactory = readNeat.getGeneFactory();
        assertThat(readGeneFactory.getConnectionCounter(), is(7001));
        assertThat(readGeneFactory.getNodeCounter(), is(7002));

        // Species
        assertThat(readGenerationEvaluator.getGeneration().getSpecies().size(), is(1));
        assertThat(readGenerationEvaluator.getGeneration().getSpecies().get(0).getGenomes().size(), is(1));

        // Number of out and in nodes in a genome
        Genome readGenome = readGenerationEvaluator.getGeneration().getSpecies().get(0).getGenomes().get(0);
        assertThat(readGenome.getInputNodes().size(), is(2));
        assertThat(readGenome.getOutputNodes().size(), is(1));
        assertThat(readGenome.getFitness(), is(100.0f));
    }
}