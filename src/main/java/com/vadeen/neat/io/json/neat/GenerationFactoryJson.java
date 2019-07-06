package com.vadeen.neat.io.json.neat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vadeen.neat.generation.GenerationFactory;
import com.vadeen.neat.genome.GenomeFactory;
import com.vadeen.neat.species.SpeciesFactory;

public class GenerationFactoryJson {

    @JsonProperty
    private int populationSize;

    @JsonProperty
    private int refocusSpeciesCount;

    @JsonProperty
    private float offspringByMutation;

    @JsonProperty
    private float maxSpeciesProportion;

    public static GenerationFactoryJson of(GenerationFactory factory) {
        GenerationFactoryJson json = new GenerationFactoryJson();
        json.populationSize = factory.getPopulationSize();
        json.refocusSpeciesCount = factory.getRefocusSpeciesCount();
        json.offspringByMutation = factory.getOffspringByMutation();
        json.maxSpeciesProportion = factory.getMaxSpeciesProportion();
        return json;
    }

    public GenerationFactory toGenerationFactor(GenomeFactory genomeFactory, SpeciesFactory speciesFactory) {
        GenerationFactory factory = new GenerationFactory(genomeFactory, speciesFactory);
        factory.setPopulationSize(populationSize);
        factory.setRefocusSpeciesCount(refocusSpeciesCount);
        factory.setOffspringByMutation(offspringByMutation);
        factory.setMaxSpeciesProportion(maxSpeciesProportion);
        return factory;
    }
}
