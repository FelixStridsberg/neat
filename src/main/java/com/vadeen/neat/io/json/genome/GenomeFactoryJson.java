package com.vadeen.neat.io.json.genome;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vadeen.neat.BiasedRandom;
import com.vadeen.neat.genome.GenomeFactory;
import com.vadeen.neat.genome.GenomeMutator;
import com.vadeen.neat.genome.GenomeValidator;

public class GenomeFactoryJson {
    @JsonProperty
    private float reexpressProbability;

    @JsonProperty
    private float breedMutationProbability;


    public static GenomeFactoryJson of(GenomeFactory factory) {
        GenomeFactoryJson json = new GenomeFactoryJson();
        json.breedMutationProbability = factory.getBreedMutationProbability();
        json.reexpressProbability = factory.getReexpressProbability();
        return json;
    }

    public GenomeFactory toGenomeFactory(GenomeMutator mutator, GenomeValidator validator, BiasedRandom random) {
        GenomeFactory factory = new GenomeFactory(mutator, validator, random);
        factory.setReexpressProbability(reexpressProbability);
        factory.setBreedMutationProbability(breedMutationProbability);
        return factory;
    }
}
