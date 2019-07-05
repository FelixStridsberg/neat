package com.vadeen.neat.io.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vadeen.neat.generation.Generation;
import com.vadeen.neat.genome.Genome;
import com.vadeen.neat.species.Species;

import java.util.ArrayList;
import java.util.List;

public class GenerationJson {

    @JsonProperty
    List<GenomeJson> genomes;

    public static GenerationJson fromGeneration(Generation generation) {
        List<GenomeJson> genomes = new ArrayList<>();

        for (Species s : generation.getSpecies()) {
            for (Genome g : s.getGenomes()) {
                genomes.add(GenomeJson.fromGenome(g));
            }
        }

        return new GenerationJson(genomes);
    }

    public GenerationJson() {}

    private GenerationJson(List<GenomeJson> genomes) {
        this.genomes = genomes;
    }

    public List<GenomeJson> getGenomes() {
        return genomes;
    }
}
