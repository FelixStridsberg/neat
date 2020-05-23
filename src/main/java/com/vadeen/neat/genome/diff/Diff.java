package com.vadeen.neat.genome.diff;

public class Diff<T> {
    private final T left;
    private final T right;

    public Diff(T left, T right) {
        this.left = left;
        this.right = right;
    }

    public T getLeft() {
        return left;
    }

    public T getRight() {
        return right;
    }
}
