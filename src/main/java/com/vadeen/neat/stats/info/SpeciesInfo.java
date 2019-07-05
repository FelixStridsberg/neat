package com.vadeen.neat.stats.info;

import com.vadeen.neat.species.Species;

public class SpeciesInfo {

    private final int id;
    private final int size;

    public static SpeciesInfo of(Species s) {
        return new SpeciesInfo(s.getId(), s.getSize());
    }

    private SpeciesInfo(int id, int size) {
        this.id = id;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }
}
