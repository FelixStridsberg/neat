package com.vadeen.neat.genome;

import com.vadeen.neat.gene.ConnectionGene;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Diff two genomes. Only diffs the connections since the nodes are just placeholders between two connections so they
 * are irrelevant in this context. (At least in my mind).
 */
public class GenomeDiff {

    /**
     * The genomes diffed.
     */
    private Genome left;
    private Genome right;

    /**
     * Disjoint genes are within the range of the other genome, but missing.
     */
    private List<ConnectionGene> disjointLeft = new LinkedList<>();
    private List<ConnectionGene> disjointRight = new LinkedList<>();

    /**
     * Excess genes are outside the range of the other genome.
     */
    private List<ConnectionGene> excessLeft = new LinkedList<>();
    private List<ConnectionGene> excessRight = new LinkedList<>();

    /**
     * Matching genes are present in both genomes. Weight might differ.
     */
    private List<ConnectionGene> matchingLeft = new LinkedList<>();
    private List<ConnectionGene> matchingRight = new LinkedList<>();

    public static GenomeDiff diff(Genome left, Genome right) {
        GenomeDiff result = new GenomeDiff();
        result.left = left;
        result.right = right;

        Map<Integer, ConnectionGene> leftConnections = left.getConnections();
        Map<Integer, ConnectionGene> rightConnections = right.getConnections();

        int maxInnovationLeft = 0;
        if (leftConnections.size() > 0)
            maxInnovationLeft = Collections.max(leftConnections.keySet());

        int maxInnovationRight = 0;
        if (rightConnections.size() > 0)
            maxInnovationRight = Collections.max(rightConnections.keySet());

        // Find excess
        if (maxInnovationLeft > maxInnovationRight)
            result.excessLeft = getExcess(left, maxInnovationRight);
        else if (maxInnovationLeft < maxInnovationRight)
            result.excessRight = getExcess(right, maxInnovationLeft);

        // Find disjoint and matching
        for (int i = 1; i <= Math.min(maxInnovationLeft, maxInnovationRight); i++) {
            ConnectionGene leftCon = leftConnections.get(i);
            ConnectionGene rightCon = rightConnections.get(i);

            if (leftCon != null && rightCon != null) {
                result.matchingLeft.add(leftCon);
                result.matchingRight.add(rightCon);
            }
            else if (leftCon != null) {
                result.disjointLeft.add(leftCon);
            }
            else if (rightCon != null) {
                result.disjointRight.add(rightCon);
            }
        }

        return result;
    }

    private static List<ConnectionGene> getExcess(Genome g, int above) {
        return g.getConnections().values().stream()
                .filter(x -> x.getInnovation() > above)
                .sorted(Comparator.comparing(ConnectionGene::getInnovation))
                .collect(Collectors.toList());
    }

    private GenomeDiff() {}

    public Genome getLeft() {
        return left;
    }

    public Genome getRight() {
        return right;
    }

    public List<ConnectionGene> getDisjointLeft() {
        return disjointLeft;
    }

    public List<ConnectionGene> getDisjointRight() {
        return disjointRight;
    }

    public List<ConnectionGene> getExcessLeft() {
        return excessLeft;
    }

    public List<ConnectionGene> getExcessRight() {
        return excessRight;
    }

    public List<ConnectionGene> getMatchingLeft() {
        return matchingLeft;
    }

    public List<ConnectionGene> getMatchingRight() {
        return matchingRight;
    }
}
