package com.vadeen.neat.species;

import com.vadeen.neat.genome.Genome;
import com.vadeen.neat.genome.GenomeComparator;

import java.util.LinkedList;
import java.util.List;

/**
 * The species factory is used to dived genomes into species.
 */
public class SpeciesFactory {

    /**
     * We use a GenomeComparator to calculate the distance between genomes.
     */
    private GenomeComparator comparator;

    /**
     * If the distance between the genome is at or bellow this threshold, the genomes are considered the same species.
     */
    private int distanceThreshold = 4;

    /**
     * We give every new species an id. This is the counter that id comes from.
     */
    private int idCounter = 0;

    public SpeciesFactory(GenomeComparator comparator) {
        this.comparator = comparator;
    }

    public Species copy(Species s, Genome reference) {
        return new Species(s.getId(), reference);
    }

    public Species createSpecies(Genome reference) {
        return new Species(++idCounter, reference);
    }

    public List<Species> generate(List<Genome> genomes) {
        List<Species> species = new LinkedList<>();
        return generate(species, genomes);
    }

    public List<Species> generate(List<Species> species, List<Genome> genomes) {
        for (Genome g : genomes) {
            Species s = findSpecies(species, g);
            if (s != null)
                s.addGenome(g);
            else
                species.add(createSpecies(g));
        }

        return species;
    }

    public GenomeComparator getComparator() {
        return comparator;
    }

    public void setComparator(GenomeComparator comparator) {
        this.comparator = comparator;
    }

    public int getDistanceThreshold() {
        return distanceThreshold;
    }

    public void setDistanceThreshold(int distanceThreshold) {
        this.distanceThreshold = distanceThreshold;
    }

    private Species findSpecies(List<Species> species, Genome g) {
        for (Species s : species) {
            if (comparator.calculateDistance(s.getReference(), g) < distanceThreshold)
                return s;
        }

        return null;
    }
}
