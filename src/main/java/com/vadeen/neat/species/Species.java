package com.vadeen.neat.species;

import com.vadeen.neat.genome.Genome;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A Species is a bunch of genomes that are close enough in `distance` (@see GenomeComparator) to be bunched together.
 */
public class Species implements Comparable<Species> {
    private final int id;
    private final Genome reference;
    private final List<Genome> genomes = new LinkedList<>();

    // Cache
    private List<Genome> sortedGenomes = null;

    // Cache
    private Float fitness = null;

    Species(int id, Genome reference) {
        this.id = id;
        this.reference = reference;
        genomes.add(reference);
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return genomes.size();
    }

    public Genome getReference() {
        return reference;
    }

    public void addGenome(Genome genome) {
        genomes.add(genome);
        sortedGenomes = null;
    }

    public List<Genome> getGenomes() {
        return Collections.unmodifiableList(genomes);
    }

    public Genome getBestGenome() {
        List<Genome> sorted = getSortedGenomes();
        return sorted.get(0);
    }

    public List<Genome> getSortedGenomes() {
        if (sortedGenomes == null) {
            sortedGenomes = genomes.stream()
                    .sorted(Comparator.comparing(g -> -g.getFitness()))
                    .collect(Collectors.toList());
        }
        return sortedGenomes;
    }

    /**
     * @return The sum of all genome fitness divided by population size.
     */
    public float getFitness() {
        if (fitness == null) {
            fitness = 0.0f;
            for (Genome g : genomes) {
                fitness += g.getFitness();
            }
            fitness = fitness / genomes.size();
        }

        return fitness;
    }

    @Override
    public int compareTo(Species o) {
        return Float.compare(getFitness(), o.getFitness());
    }

    @Override
    public String toString() {
        return "Species{id=" + id + ",size=" + genomes.size() + ",ref=" + reference+ ",genomes=" + genomes + "}";
    }
}
