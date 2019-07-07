package com.vadeen.neat.io.json.gene;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vadeen.neat.gene.GeneFactory;

public class GeneFactoryJson {
    @JsonProperty
    private int connectionCounter;

    @JsonProperty
    private int nodeCounter;

    public static GeneFactoryJson of(GeneFactory factory) {
        GeneFactoryJson json = new GeneFactoryJson();
        json.connectionCounter = factory.getConnectionCounter();
        json.nodeCounter = factory.getNodeCounter();
        return json;
    }

    public GeneFactory toGeneFactory() {
        GeneFactory factory = new GeneFactory();
        factory.setConnectionCounter(connectionCounter);
        factory.setNodeCounter(nodeCounter);
        return factory;
    }
}
