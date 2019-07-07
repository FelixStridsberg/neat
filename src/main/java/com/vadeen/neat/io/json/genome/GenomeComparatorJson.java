package com.vadeen.neat.io.json.genome;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vadeen.neat.genome.GenomeComparator;

public class GenomeComparatorJson {
    @JsonProperty
    private float excessFactor;

    @JsonProperty
    private float disjointFactor;

    @JsonProperty
    private float weightDiffFactor;

    @JsonProperty
    private int normalizeThreshold;

    public static GenomeComparatorJson of(GenomeComparator comparator) {
        GenomeComparatorJson comparatorJson = new GenomeComparatorJson();
        comparatorJson.excessFactor = comparator.getExcessFactor();
        comparatorJson.disjointFactor = comparator.getDisjointFactor();
        comparatorJson.weightDiffFactor = comparator.getWeightDiffFactor();
        comparatorJson.normalizeThreshold = comparator.getNormalizeThreshold();
        return comparatorJson;
    }

    public GenomeComparator toGenomeComparator() {
        GenomeComparator comparator = new GenomeComparator();
        comparator.setExcessFactor(excessFactor);
        comparator.setDisjointFactor(disjointFactor);
        comparator.setWeightDiffFactor(weightDiffFactor);
        comparator.setNormalizeThreshold(normalizeThreshold);
        return comparator;
    }
}
