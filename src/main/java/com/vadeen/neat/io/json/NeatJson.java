package com.vadeen.neat.io.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vadeen.neat.Neat;
import com.vadeen.neat.Random;
import com.vadeen.neat.gene.GeneFactory;
import com.vadeen.neat.generation.Generation;
import com.vadeen.neat.generation.GenerationEvaluator;
import com.vadeen.neat.generation.GenerationFactory;
import com.vadeen.neat.genome.*;
import com.vadeen.neat.io.json.generation.GenerationEvaluatorJson;
import com.vadeen.neat.io.json.generation.GenerationFactoryJson;
import com.vadeen.neat.io.json.generation.GenerationJson;
import com.vadeen.neat.io.json.genome.GenomeComparatorJson;
import com.vadeen.neat.io.json.genome.GenomeFactoryJson;
import com.vadeen.neat.io.json.genome.GenomeMutatorJson;
import com.vadeen.neat.io.json.species.SpeciesFactoryJson;
import com.vadeen.neat.species.SpeciesFactory;

public class NeatJson {
    @JsonProperty
    private GenomeMutatorJson genomeMutator;

    @JsonProperty
    private GenomeFactoryJson genomeFactory;

    @JsonProperty
    private GenomeComparatorJson genomeComparator;

    @JsonProperty
    private GenerationFactoryJson generationFactory;

    @JsonProperty
    private SpeciesFactoryJson speciesFactory;

    @JsonProperty
    private GenerationEvaluatorJson generationEvaluator;

    @JsonProperty
    private GenerationJson generation;

    public static NeatJson of(Neat neat) {
        GenomeMutatorJson genomeMutatorJson = GenomeMutatorJson.of(neat.getMutator());
        GenomeFactoryJson genomeFactoryJson = GenomeFactoryJson.of(neat.getGenomeFactory());
        GenomeComparatorJson genomeComparatorJson = GenomeComparatorJson.of(neat.getGenomeComparator());
        SpeciesFactoryJson speciesFactoryJson = SpeciesFactoryJson.of(neat.getSpeciesFactory());
        GenerationFactoryJson generationFactoryJson = GenerationFactoryJson.of(neat.getGenerationFactory());
        GenerationEvaluatorJson generationEvaluatorJson = GenerationEvaluatorJson.of(neat.getGenerationEvaluator());

        GenerationJson generationJson = GenerationJson.of(neat.getGenerationEvaluator().getGeneration());


        NeatJson neatJson = new NeatJson();
        neatJson.genomeMutator = genomeMutatorJson;
        neatJson.genomeFactory = genomeFactoryJson;
        neatJson.genomeComparator = genomeComparatorJson;
        neatJson.speciesFactory = speciesFactoryJson;
        neatJson.generationFactory = generationFactoryJson;
        neatJson.generationEvaluator = generationEvaluatorJson;
        neatJson.generation = generationJson;
        return neatJson;
    }

    public static Neat toNeat(GenomeEvaluator evaluator, NeatJson json) {
        Random random = new Random();
        GeneFactory geneFactory = new GeneFactory();

        GenomeValidator validator = new GenomeValidator();
        GenomeMutator mutator = json.genomeMutator.toGenomeMutator(random, geneFactory);
        GenomeFactory genomeFactory = json.genomeFactory.toGenomeFactory(mutator, validator, random);
        GenomeComparator genomeComparator = json.genomeComparator.toGenomeComparator();
        SpeciesFactory speciesFactory = json.speciesFactory.toSpeciesFactory(genomeComparator);
        GenerationFactory generationFactory = json.generationFactory.toGenerationFactor(genomeFactory, speciesFactory);
        Generation generation = json.generation.toGeneration(geneFactory);


        Neat neat = Neat.create(evaluator, generation);

        GenerationEvaluator generationEvaluator = json.generationEvaluator.toGenerationEvaluator(
                evaluator, generationFactory, neat.getGenerationEvaluator().getGeneration());

        neat.setMutator(mutator);
        neat.setGenomeFactory(genomeFactory);
        neat.setGenomeComparator(genomeComparator);
        neat.setSpeciesFactory(speciesFactory);
        neat.setGenerationFactory(generationFactory);
        neat.setGenerationEvaluator(generationEvaluator);
        return neat;
    }
}
