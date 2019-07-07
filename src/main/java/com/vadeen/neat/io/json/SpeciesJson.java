package com.vadeen.neat.io.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vadeen.neat.gene.GeneFactory;
import com.vadeen.neat.genome.Genome;
import com.vadeen.neat.species.Species;

import java.util.List;

public class SpeciesJson {
    @JsonProperty
    private int id;

    @JsonProperty
    private GenomeJson reference;

    @JsonProperty
    private List<GenomeJson> genomes;

    public static SpeciesJson of(Species species) {
        SpeciesJson json = new SpeciesJson();
        json.id = species.getId();
        json.reference = GenomeJson.of(species.getReference());
        json.genomes = GenomeJson.of(species.getGenomes());
        return json;
    }

    public Species toSpecies(GeneFactory geneFactory) {
        Genome reference = this.reference.toGenome(geneFactory);
        Species species = new Species(id, reference);

        genomes.stream()
                .map(g -> g.toGenome(geneFactory))
                .filter(e -> e.equals(reference))
                .forEach(species::addGenome);

        return species;
    }
}
