package com.vadeen.neat.tree;

/**
 * @see LevelTree, it explains all you need explained.
 */
public class LevelConnection {

    protected final int id;
    protected final int in;
    protected final int out;

    public LevelConnection(int id, int in, int out) {
        this.id = id;
        this.in = in;
        this.out = out;
    }

    public int getId() {
        return id;
    }

    public int getIn() {
        return in;
    }

    public int getOut() {
        return out;
    }

    @Override
    public String toString() {
        return String.format("LevelConnection{id=%d,in=%d,out=%d}", id, in, out);
    }
}
