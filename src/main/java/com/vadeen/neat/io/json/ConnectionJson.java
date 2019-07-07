package com.vadeen.neat.io.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vadeen.neat.gene.ConnectionGene;
import com.vadeen.neat.gene.GeneFactory;

/**
 * JSON representation of a ConnectionGene.
 *
 * This is to abstract the actual implementation from the json.
 */
@JsonSerialize
class ConnectionJson {
    @JsonProperty
    private int innovation;

    @JsonProperty
    private int in;

    @JsonProperty
    private int out;

    @JsonProperty
    private float weight;

    @JsonProperty
    private boolean expressed;

    public static ConnectionJson of(ConnectionGene c) {
        ConnectionJson json = new ConnectionJson();
        json.innovation = c.getInnovation();
        json.in = c.getIn();
        json.out = c.getOut();
        json.weight = c.getWeight();
        json.expressed = c.isExpressed();
        return json;
    }

    public ConnectionGene toConnection(GeneFactory geneFactory) {
        return geneFactory.createConnection(in, out, weight, expressed, innovation);
    }
}
