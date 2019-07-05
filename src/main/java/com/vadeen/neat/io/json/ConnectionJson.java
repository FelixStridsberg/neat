package com.vadeen.neat.io.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vadeen.neat.gene.ConnectionGene;

/**
 * JSON representation of a ConnectionGene.
 *
 * This is to abstract the actual implementation from the json.
 */
@JsonSerialize
class ConnectionJson {
    @JsonProperty
    int innovation;

    @JsonProperty
    int in;

    @JsonProperty
    int out;

    @JsonProperty
    float weight;

    @JsonProperty
    boolean expressed;

    public ConnectionJson() {}

    public ConnectionJson(ConnectionGene c) {
        this.innovation = c.getInnovation();
        this.in = c.getIn();
        this.out = c.getOut();
        this.weight = c.getWeight();
        this.expressed = c.isExpressed();
    }
}
