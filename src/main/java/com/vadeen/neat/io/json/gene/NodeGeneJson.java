package com.vadeen.neat.io.json.gene;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vadeen.neat.gene.GeneFactory;
import com.vadeen.neat.gene.NodeGene;

/**
 * JSON representation of a NodeGene.
 *
 * This is to abstract the actual implementation from the json.
 */
@JsonSerialize
public class NodeGeneJson {
    @JsonProperty
    private int id;

    @JsonProperty
    private NodeGene.Type type;

    public static NodeGeneJson of(NodeGene n) {
        NodeGeneJson json = new NodeGeneJson();
        json.id = n.getId();
        json.type = n.getType();
        return json;
    }

    public NodeGene toNode(GeneFactory geneFactory) {
        return geneFactory.createNode(type, id);
    }
}
