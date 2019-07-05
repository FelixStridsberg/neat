package com.vadeen.neat;

import com.vadeen.neat.gene.GeneFactory;
import com.vadeen.neat.generation.Generation;
import com.vadeen.neat.generation.GenerationEvaluator;
import com.vadeen.neat.generation.GenerationFactory;
import com.vadeen.neat.genome.*;
import com.vadeen.neat.species.SpeciesFactory;

import java.io.File;
import java.util.Collections;

/**
 * This is an abstraction around the generation of a neat network, so the network can be loaded and saved to files and
 * to avoid having to create every damn object required every time.
 *
 * This can create all objects with default configs and allows you to get them and reconfigure at any time.
 */
public class Neat {

    private final Random random;
    private final GeneFactory geneFactory;
    private final GenomeMutator mutator;
    private final GenomeValidator validator;
    private final GenomeFactory genomeFactory;
    private final GenomeComparator genomeComparator;
    private final SpeciesFactory speciesFactory;
    private final GenerationFactory generationFactory;
    private final GenerationEvaluator generationEvaluator;

    // TODO json file with all the settings and such.
    // Possibility to save evolved networks as well.
    public static Neat load(File file) {
        return null;
    }

    public static Neat create(GenomeEvaluator evaluator, int inputs, int outputs) {
        Random random = new Random(System.currentTimeMillis());
        GeneFactory geneFactory = new GeneFactory();
        GenomeMutator mutator = new GenomeMutator(random, geneFactory);
        GenomeValidator validator = new GenomeValidator();
        GenomeFactory genomeFactory = new GenomeFactory(mutator, validator, random);
        GenomeComparator genomeComparator = new GenomeComparator();
        SpeciesFactory speciesFactory = new SpeciesFactory(genomeComparator);

        Genome starter = Genome.create(geneFactory,inputs, outputs);
        Generation firstGeneration = new Generation(Collections.singletonList(speciesFactory.createSpecies(starter)));
        GenerationFactory generationFactory = new GenerationFactory(genomeFactory, speciesFactory);
        GenerationEvaluator generationEvaluator = new GenerationEvaluator(evaluator, generationFactory, firstGeneration);

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

    public Random getRandom() {
        return random;
    }

    public GeneFactory getGeneFactory() {
        return geneFactory;
    }

    public GenomeMutator getMutator() {
        return mutator;
    }

    public GenomeValidator getValidator() {
        return validator;
    }

    public GenomeFactory getGenomeFactory() {
        return genomeFactory;
    }

    public GenomeComparator getGenomeComparator() {
        return genomeComparator;
    }

    public SpeciesFactory getSpeciesFactory() {
        return speciesFactory;
    }

    public GenerationFactory getGenerationFactory() {
        return generationFactory;
    }

    public GenerationEvaluator getGenerationEvaluator() {
        return generationEvaluator;
    }

    public Generation evolve() {
        return generationEvaluator.nextGeneration();
    }
}
