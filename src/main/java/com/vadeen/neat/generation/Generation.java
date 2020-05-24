package com.vadeen.neat.generation;

import com.vadeen.neat.genome.Genome;
import com.vadeen.neat.species.Species;

import java.util.Collections;
import java.util.List;

/**
 * Contains all info about a generation.
 */
public class Generation {

    private final List<Species> species;
    private int generationNumber;

    public Generation(int generationNumber, List<Species> species) {
        this(species);
        this.generationNumber = generationNumber;
    }

    public Generation(List<Species> species) {
        this.species = species;
    }

    public List<Species> getSpecies() {
        return Collections.unmodifiableList(species);
    }

    public int getGenerationNumber() {
        return generationNumber;
    }

    public void setGenerationNumber(int generationNumber) {
        this.generationNumber = generationNumber;
    }

    public float getTotalFitness() {
        float fitness = 0.0f;
        for (Species s : species) {
            for (Genome genome : s.getGenomes()) {
                fitness += genome.getFitness();
            }
        }
        return fitness;
    }

    /**
     * @return The best genome of this generation.
     */
    public Genome getBestGenome() {
        Genome best = null;
        for (Species s : species) {
            Genome genome = s.getBestGenome();
            if (best == null || genome.getFitness() > best.getFitness())
                best = genome;
        }
        return best;
    }

    @Override
    public String toString() {
        return "Generation{species=" + species.size() + "}";
    }
}
