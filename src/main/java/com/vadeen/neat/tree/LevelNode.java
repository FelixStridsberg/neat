package com.vadeen.neat.tree;

import java.util.Objects;

/**
 * @see LevelTree, it explains all you need explained.
 */
public class LevelNode {

    protected final int id;

    private int level = 0;

    public LevelNode(int id) {
        this.id = id;
    }

    public LevelNode(LevelNode o) {
        this.id = o.id;
        this.level = o.level;
    }

    public int getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LevelNode))
            return false;

        LevelNode other = (LevelNode)obj;
        return other.id == this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("LevelNode{id=%d,level=%d}", id, level);
    }
}
