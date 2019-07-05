package com.vadeen.neat.io.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vadeen.neat.gene.ConnectionGene;
import com.vadeen.neat.gene.GeneFactory;
import com.vadeen.neat.gene.NodeGene;
import com.vadeen.neat.genome.Genome;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JSON representation of a Genome.
 *
 * This is to abstract the actual implementation from the json.
 */
@JsonSerialize
public class GenomeJson {
    @JsonProperty
    int id;

    @JsonProperty
    List<NodeJson> nodes;

    @JsonProperty
    List<ConnectionJson> connections;

    public static GenomeJson fromGenome(Genome genome) {
        GenomeJson json = new GenomeJson();
        json.id = genome.id;
        json.nodes = genome.getNodes().values().stream()
                .map(NodeJson::new)
                .collect(Collectors.toList());

        json.connections = genome.getConnections().values().stream()
                .map(ConnectionJson::new)
                .sorted(Comparator.comparing(g -> g.innovation))
                .collect(Collectors.toList());

        return json;
    }

    public static Genome toGenome(GeneFactory geneFactory, GenomeJson json) {
        List<NodeGene> nodes = json.nodes.stream()
                .map(j -> geneFactory.createNode(j.type, j.id))
                .collect(Collectors.toList());

        List<ConnectionGene> connections = json.connections.stream()
                .map(j -> geneFactory.createConnection(j.in, j.out, j.weight, j.expressed, j.innovation))
                .collect(Collectors.toList());

        return Genome.create(connections, nodes);
    }
}
