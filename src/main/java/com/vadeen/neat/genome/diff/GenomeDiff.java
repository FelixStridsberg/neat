package com.vadeen.neat.genome.diff;

import com.vadeen.neat.gene.ConnectionGene;
import com.vadeen.neat.genome.Genome;

import java.util.*;

/**
 * Diff two genomes. Only diffs the connections since the nodes are just placeholders between two connections so they
 * are irrelevant in this context. (At least in my mind).
 */
public class GenomeDiff {

    /**
     * The genomes diffed.
     */
    private final Diff<Genome> genomes;

    /**
     * Disjoint genes are within the range of the other genome, but missing.
     */
    private final Diff<List<ConnectionGene>> disjoint;

    /**
     * Excess genes are outside the range of the other genome.
     */
    private final Diff<List<ConnectionGene>> excess;

    /**
     * Matching genes are present in both genomes. Weight might differ.
     */
    private final Diff<List<ConnectionGene>> matching;

    public GenomeDiff(
            Diff<Genome> genomes,
            Diff<List<ConnectionGene>> disjoint,
            Diff<List<ConnectionGene>> excess,
            Diff<List<ConnectionGene>> matching
    ) {
        this.genomes = genomes;
        this.disjoint = disjoint;
        this.excess = excess;
        this.matching = matching;
    }

    public static GenomeDiff diff(Genome left, Genome right) {
        GenomeDiffFactory diffFactory = new GenomeDiffFactory(left, right);
        return diffFactory.getDiff();
    }

    public Genome getLeft() {
        return genomes.getLeft();
    }

    public Genome getRight() {
        return genomes.getRight();
    }

    public List<ConnectionGene> getDisjointLeft() {
        return disjoint.getLeft();
    }

    public List<ConnectionGene> getDisjointRight() {
        return disjoint.getRight();
    }

    public List<ConnectionGene> getExcessLeft() {
        return excess.getLeft();
    }

    public List<ConnectionGene> getExcessRight() {
        return excess.getRight();
    }

    public List<ConnectionGene> getMatchingLeft() {
        return matching.getLeft();
    }

    public List<ConnectionGene> getMatchingRight() {
        return matching.getRight();
    }
}
