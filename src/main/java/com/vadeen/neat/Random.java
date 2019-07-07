package com.vadeen.neat;

/**
 * We need some extra random calculations so lets put them here.
 */
public class Random extends java.util.Random {

    // Increase this factor to make the biased values even more biased.
    private final static float BIAS_FACTOR = 1.3f;

    public Random() {}

    public Random(long seed) {
        super(seed);
    }

    /**
     * Returns an integer with a higher probability of getting a low value.
     */
    public int nextLowBiasedInt(int bound) {
        return (int)((Math.pow(nextFloat(),BIAS_FACTOR))*bound);
    }
}
