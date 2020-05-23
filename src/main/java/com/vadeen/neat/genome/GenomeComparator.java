package com.vadeen.neat.genome;

import com.vadeen.neat.gene.ConnectionGene;

import java.util.Iterator;

/**
 * Can calculateDistance genomes.
 */
public class GenomeComparator {

    /**
     * How heavy the excess will weigh in the comparison.
     */
    private float excessFactor = 1.0f;

    /**
     * How heavy the disjoint connections weigh in the comparison.
     */
    private float disjointFactor = 1.0f;

    /**
     * How heavy the weight diff factor weigh in the comparison.
     */
    private float weightDiffFactor = 0.2f;


    /**
     * If the number of nodes is more than this number on any side, the disjoint count and excess count is divided
     * by the number of nodes.
     *
     * TODO why? To make the comparison more fair comparing big and small? How does this make it fair?
     */
    private int normalizeThreshold = 20;

    /**
     * Calculates the distance between two genomes.
     * The distance represents how far from each other the genomes are in an evolutionary stand point.
     * This can be used to determine if the genomes belong to the same species.
     *
     * @return The distance between the genomes.
     */
    public float calculateDistance(Genome left, Genome right) {
        GenomeDiff diff = GenomeDiff.diff(left, right);

        int excess = diff.getExcessLeft().size() + diff.getExcessRight().size();
        int disjoint = diff.getDisjointLeft().size() + diff.getDisjointRight().size();
        float avgWeightDiff = averageWeightDiff(diff);

        // Normalize for genome size
        int leftGeneCount = left.getConnections().size();
        int rightGeneCount = right.getConnections().size();
        int geneCount = 1;

        if (Math.max(leftGeneCount, rightGeneCount) > normalizeThreshold)
            geneCount = Math.max(leftGeneCount, rightGeneCount);

        return (excessFactor*excess + disjointFactor*disjoint)/geneCount + weightDiffFactor *avgWeightDiff;
    }

    public float getExcessFactor() {
        return excessFactor;
    }

    public void setExcessFactor(float excessFactor) {
        this.excessFactor = excessFactor;
    }

    public float getDisjointFactor() {
        return disjointFactor;
    }

    public void setDisjointFactor(float disjointFactor) {
        this.disjointFactor = disjointFactor;
    }

    public float getWeightDiffFactor() {
        return weightDiffFactor;
    }

    public void setWeightDiffFactor(float weightDiffFactor) {
        this.weightDiffFactor = weightDiffFactor;
    }

    public int getNormalizeThreshold() {
        return normalizeThreshold;
    }

    public void setNormalizeThreshold(int normalizeThreshold) {
        this.normalizeThreshold = normalizeThreshold;
    }

    /**
     * @return Average weight difference on the matching connections.
     */
    private float averageWeightDiff(GenomeDiff diff) {
        Iterator<ConnectionGene> leftIt = diff.getMatchingLeft().iterator();
        Iterator<ConnectionGene> rightIt = diff.getMatchingRight().iterator();

        if (diff.getMatchingLeft().isEmpty())
            return 0;

        float total = 0;
        while (leftIt.hasNext()) {
            ConnectionGene left = leftIt.next();
            ConnectionGene right = rightIt.next();

            total += Math.abs(left.getWeight() - right.getWeight());
        }

        return total/diff.getMatchingLeft().size();
    }
}
