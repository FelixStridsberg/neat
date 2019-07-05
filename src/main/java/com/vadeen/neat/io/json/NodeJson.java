package com.vadeen.neat.io.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vadeen.neat.gene.NodeGene;

/**
 * JSON representation of a NodeGene.
 *
 * This is to abstract the actual implementation from the json.
 */
@JsonSerialize
class NodeJson {
    @JsonProperty
    int id;

    @JsonProperty
    NodeGene.Type type;

    NodeJson() {}

    NodeJson(NodeGene n) {
        this.id = n.getId();
        this.type = n.getType();
    }
}
