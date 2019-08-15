package com.vadeen.neat;

import com.vadeen.neat.gene.GeneFactory;
import com.vadeen.neat.generation.Generation;
import com.vadeen.neat.generation.GenerationEvaluator;
import com.vadeen.neat.generation.GenerationFactory;
import com.vadeen.neat.genome.*;
import com.vadeen.neat.species.SpeciesFactory;

import java.util.Collections;

/**
 * This is an abstraction around the generation of a neat network, so the network can be loaded and saved to files and
 * to avoid having to create every damn object required every time.
 *
 * This can create all objects with default configs and allows you to get them and reconfigure at any time.
 *
 * Or even swap em out.
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

    private Generation generation;

    public static Neat create(GenomeEvaluator evaluator, int inputs, int outputs) {
        Random random = new Random(System.currentTimeMillis());
        GeneFactory geneFactory = new GeneFactory();
        GenomeMutator mutator = new GenomeMutator(random, geneFactory);
        GenomeValidator validator = new GenomeValidator();
        GenomeFactory genomeFactory = new GenomeFactory(mutator, validator, random);
        GenomeComparator genomeComparator = new GenomeComparator();
        SpeciesFactory speciesFactory = new SpeciesFactory(genomeComparator);

        Genome starter = Genome.create(geneFactory, inputs, outputs);
        Generation firstGeneration = new Generation(Collections.singletonList(speciesFactory.createSpecies(starter)));
        GenerationFactory generationFactory = new GenerationFactory(genomeFactory, speciesFactory);
        GenerationEvaluator generationEvaluator = new GenerationEvaluator(evaluator, generationFactory, firstGeneration);

        return new Neat(random, geneFactory, mutator, validator, genomeFactory,
                genomeComparator, speciesFactory, generationFactory, generationEvaluator);
    }

    public static Neat create(GenomeEvaluator evaluator, Generation generation) {
        Random random = new Random(System.currentTimeMillis());
        GeneFactory geneFactory = new GeneFactory();
        GenomeMutator mutator = new GenomeMutator(random, geneFactory);
        GenomeValidator validator = new GenomeValidator();
        GenomeFactory genomeFactory = new GenomeFactory(mutator, validator, random);
        GenomeComparator genomeComparator = new GenomeComparator();
        SpeciesFactory speciesFactory = new SpeciesFactory(genomeComparator);

        GenerationFactory generationFactory = new GenerationFactory(genomeFactory, speciesFactory);
        GenerationEvaluator generationEvaluator = new GenerationEvaluator(evaluator, generationFactory, generation);

        return new Neat(random, geneFactory, mutator, validator, genomeFactory,
                genomeComparator, speciesFactory, generationFactory, generationEvaluator);
    }

    // Ridiculous constructor I know, but this is just a convenience class so you don't have to create all of these.
    private Neat(Random random, GeneFactory geneFactory, GenomeMutator mutator, GenomeValidator validator,
                GenomeFactory genomeFactory, GenomeComparator genomeComparator, SpeciesFactory speciesFactory,
                GenerationFactory generationFactory, GenerationEvaluator generationEvaluator) {
        this.random = random;
        this.geneFactory = geneFactory;
        this.mutator = mutator;
        this.validator = validator;
        this.genomeFactory = genomeFactory;
        this.genomeComparator = genomeComparator;
        this.speciesFactory = speciesFactory;
        this.generationFactory = generationFactory;
        this.generationEvaluator = generationEvaluator;
    }

    public Generation evolve() {
        return generationEvaluator.nextGeneration();
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
