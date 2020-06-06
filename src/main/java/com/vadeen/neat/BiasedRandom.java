package com.vadeen.neat;

import java.util.Random;

/**
 * Adapter around a Random object to provide biased values.
 *
 * Provide a <em>low bias factor</em> in the constructor to bias values returned by {@link #nextLowBiasedInt(int)}.
 */
public final class BiasedRandom {

    private static final float DEFAULT_BIAS = 1.3f;

    private final Random random;
    private final float lowBiasFactor;

    /**
     * Create a biased random with default bias.
     *
     * @param random source of random numbers
     */
    public BiasedRandom(Random random) {
        this(random, DEFAULT_BIAS);
    }

    /**
     * Create a biased random with specific bias.
     *
     * @param random source of random numbers
     * @param lowBiasFactor the factor to provide low bias; must not be less than 1.0f
     * @throws IllegalArgumentException if lowBiasFactor is less than 1.0f
     */
    public BiasedRandom(Random random, float lowBiasFactor) {
        if (lowBiasFactor < 1.0f)
            throw new IllegalArgumentException("lowBiasFactor cannot be less than 1.0f. Actual value: " + lowBiasFactor);

        this.random = random;
        this.lowBiasFactor = lowBiasFactor;
    }

    /**
     * @return the source of random numbers provided to constructor
     */
    public Random getRandomSource() {
        return random;
    }

    /**
     * Gets next random float from the random source and biases towards the lower spectrum using the
     * <em>lowBiasFactor</em>.
     *
     * @param bound upper bound (exclusive); must be positive
     * @return next random number biased towards the lower spectrum
     * @throws IllegalArgumentException if bound is less than 1
     */
    public int nextLowBiasedInt(int bound) {
        if (bound < 1)
            throw new IllegalArgumentException("bound must be positive. Actual value: " + bound);

        return (int)((Math.pow(random.nextFloat(), lowBiasFactor))*bound);
    }

    /**
     * @see Random#nextFloat()
     */
    public float nextFloat() {
        return random.nextFloat();
    }

    /**
     * @see Random#nextInt()
     */
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    /**
     * @see Random#nextBoolean()
     */
    public boolean nextBoolean() {
        return random.nextBoolean();
    }
}
