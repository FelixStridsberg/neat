package com.vadeen.neat;

import com.vadeen.neat.gene.GeneFactory;
import com.vadeen.neat.generation.Generation;
import com.vadeen.neat.generation.GenerationEvaluator;
import com.vadeen.neat.generation.GenerationFactory;
import com.vadeen.neat.genome.*;
import com.vadeen.neat.species.SpeciesFactory;

import java.util.Collections;

/**
 * High level interface to the Neat network.
 */
public class Neat {
    private Random random;
    private GeneFactory geneFactory;
    private GenomeMutator mutator;
    private GenomeValidator validator;
    private GenomeFactory genomeFactory;
    private GenomeComparator genomeComparator;
    private SpeciesFactory speciesFactory;
    private GenerationFactory generationFactory;
    private GenerationEvaluator generationEvaluator;

    public static Neat create(GenomeEvaluator evaluator, int inputs, int outputs) {
        Neat neat = createBase();
        Genome starter = Genome.create(neat.geneFactory, inputs, outputs);
        Generation firstGeneration = new Generation(Collections.singletonList(neat.speciesFactory.createSpecies(starter)));

        neat.generationFactory = new GenerationFactory(neat.genomeFactory, neat.speciesFactory);
        neat.generationEvaluator = new GenerationEvaluator(evaluator, neat.generationFactory, firstGeneration);
        return neat;
    }

    public static Neat create(GenomeEvaluator evaluator, Generation generation) {
        Neat neat = createBase();
        neat.generationFactory = new GenerationFactory(neat.genomeFactory, neat.speciesFactory);
        neat.generationEvaluator = new GenerationEvaluator(evaluator, neat.generationFactory, generation);
        return neat;
    }

    private static Neat createBase() {
        Neat neat = new Neat();
        neat.random = new Random(System.currentTimeMillis());
        neat.geneFactory = new GeneFactory();
        neat.mutator = new GenomeMutator(neat.random, neat.geneFactory);
        neat.validator = new GenomeValidator();
        neat.genomeFactory = new GenomeFactory(neat.mutator, neat.validator, neat.random);
        neat.genomeComparator = new GenomeComparator();
        neat.speciesFactory = new SpeciesFactory(neat.genomeComparator);

        return neat;
    }

    public Generation evolve() {
        return generationEvaluator.nextGeneration();
    }

    public Generation getGeneration() {
        return generationEvaluator.getGeneration();
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public GeneFactory getGeneFactory() {
        return geneFactory;
    }

    public void setGeneFactory(GeneFactory geneFactory) {
        this.geneFactory = geneFactory;
    }

    public GenomeMutator getMutator() {
        return mutator;
    }

    public void setMutator(GenomeMutator mutator) {
        this.mutator = mutator;
    }

    public GenomeValidator getValidator() {
        return validator;
    }

    public void setValidator(GenomeValidator validator) {
        this.validator = validator;
    }

    public GenomeFactory getGenomeFactory() {
        return genomeFactory;
    }

    public void setGenomeFactory(GenomeFactory genomeFactory) {
        this.genomeFactory = genomeFactory;
    }

    public GenomeComparator getGenomeComparator() {
        return genomeComparator;
    }

    public void setGenomeComparator(GenomeComparator genomeComparator) {
        this.genomeComparator = genomeComparator;
    }

    public SpeciesFactory getSpeciesFactory() {
        return speciesFactory;
    }

    public void setSpeciesFactory(SpeciesFactory speciesFactory) {
        this.speciesFactory = speciesFactory;
    }

    public GenerationFactory getGenerationFactory() {
        return generationFactory;
    }

    public void setGenerationFactory(GenerationFactory generationFactory) {
        this.generationFactory = generationFactory;
    }

    public GenerationEvaluator getGenerationEvaluator() {
        return generationEvaluator;
    }

    public void setGenerationEvaluator(GenerationEvaluator generationEvaluator) {
        this.generationEvaluator = generationEvaluator;
    }
}
