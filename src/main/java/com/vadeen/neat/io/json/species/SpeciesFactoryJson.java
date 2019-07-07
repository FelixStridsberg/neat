package com.vadeen.neat.io.json.species;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vadeen.neat.genome.GenomeComparator;
import com.vadeen.neat.species.SpeciesFactory;

public class SpeciesFactoryJson {

    @JsonProperty
    private int distanceThreshold;

    public static SpeciesFactoryJson of(SpeciesFactory factory) {
        SpeciesFactoryJson json = new SpeciesFactoryJson();
        json.distanceThreshold = factory.getDistanceThreshold();
        return json;
    }

    public SpeciesFactory toSpeciesFactory(GenomeComparator comparator) {
        SpeciesFactory speciesFactory = new SpeciesFactory(comparator);
        speciesFactory.setDistanceThreshold(distanceThreshold);
        return speciesFactory;
    }
}
