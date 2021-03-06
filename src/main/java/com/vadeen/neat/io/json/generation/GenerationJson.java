package com.vadeen.neat.io.json.generation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vadeen.neat.gene.GeneFactory;
import com.vadeen.neat.generation.Generation;
import com.vadeen.neat.io.json.species.SpeciesJson;
import com.vadeen.neat.species.Species;

import java.util.List;
import java.util.stream.Collectors;

public class GenerationJson {

    @JsonProperty
    private int generationNumber;

    @JsonProperty
    private List<SpeciesJson> species;

    public static GenerationJson of(Generation generation) {
        List<SpeciesJson> speciesJson = generation.getSpecies().stream()
                .map(SpeciesJson::of)
                .collect(Collectors.toList());

        GenerationJson json = new GenerationJson();
        json.generationNumber = generation.getGenerationNumber();
        json.species = speciesJson;
        return json;
    }

    public Generation toGeneration(GeneFactory geneFactory) {
        List<Species> species = this.species.stream()
                .map(s -> s.toSpecies(geneFactory))
                .collect(Collectors.toList());

        return new Generation(generationNumber, species);
    }
}
