package com.vadeen.neat.io.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vadeen.neat.Neat;
import com.vadeen.neat.Random;
import com.vadeen.neat.gene.GeneFactory;
import com.vadeen.neat.generation.Generation;
import com.vadeen.neat.generation.GenerationEvaluator;
import com.vadeen.neat.generation.GenerationFactory;
import com.vadeen.neat.genome.*;
import com.vadeen.neat.io.json.gene.GeneFactoryJson;
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
    private GeneFactoryJson geneFactory;

    @JsonProperty
    private GenerationJson generation;

    public static NeatJson of(Neat neat) {
        GenomeMutatorJson genomeMutatorJson = GenomeMutatorJson.of(neat.getMutator());
        GenomeFactoryJson genomeFactoryJson = GenomeFactoryJson.of(neat.getGenomeFactory());
        GenomeComparatorJson genomeComparatorJson = GenomeComparatorJson.of(neat.getGenomeComparator());
        SpeciesFactoryJson speciesFactoryJson = SpeciesFactoryJson.of(neat.getSpeciesFactory());
        GenerationFactoryJson generationFactoryJson = GenerationFactoryJson.of(neat.getGenerationFactory());
        GenerationEvaluatorJson generationEvaluatorJson = GenerationEvaluatorJson.of(neat.getGenerationEvaluator());
        GeneFactoryJson geneFactoryJson = GeneFactoryJson.of(neat.getGeneFactory());
        GenerationJson generationJson = GenerationJson.of(neat.getGenerationEvaluator().getGeneration());


        NeatJson neatJson = new NeatJson();
        neatJson.genomeMutator = genomeMutatorJson;
        neatJson.genomeFactory = genomeFactoryJson;
        neatJson.genomeComparator = genomeComparatorJson;
        neatJson.speciesFactory = speciesFactoryJson;
        neatJson.generationFactory = generationFactoryJson;
        neatJson.generationEvaluator = generationEvaluatorJson;
        neatJson.geneFactory = geneFactoryJson;
        neatJson.generation = generationJson;
        return neatJson;
    }

    public Neat toNeat(GenomeEvaluator evaluator) {
        Random random = new Random();

        GenomeValidator validator = new GenomeValidator();
        GeneFactory geneFactory = this.geneFactory.toGeneFactory();
        GenomeMutator mutator = this.genomeMutator.toGenomeMutator(random, geneFactory);
        GenomeFactory genomeFactory = this.genomeFactory.toGenomeFactory(mutator, validator, random);
        GenomeComparator genomeComparator = this.genomeComparator.toGenomeComparator();
        SpeciesFactory speciesFactory = this.speciesFactory.toSpeciesFactory(genomeComparator);
        GenerationFactory generationFactory = this.generationFactory.toGenerationFactor(genomeFactory, speciesFactory);
        Generation generation = this.generation.toGeneration(geneFactory);


        Neat neat = Neat.create(evaluator, generation);

        GenerationEvaluator generationEvaluator = this.generationEvaluator.toGenerationEvaluator(
                evaluator, generationFactory, neat.getGenerationEvaluator().getGeneration());

        neat.setMutator(mutator);
        neat.setGenomeFactory(genomeFactory);
        neat.setGenomeComparator(genomeComparator);
        neat.setSpeciesFactory(speciesFactory);
        neat.setGenerationFactory(generationFactory);
        neat.setGenerationEvaluator(generationEvaluator);
        neat.setGeneFactory(geneFactory);
        return neat;
    }
}
