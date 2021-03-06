package com.vadeen.neat.io.json.genome;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vadeen.neat.gene.ConnectionGene;
import com.vadeen.neat.gene.GeneFactory;
import com.vadeen.neat.gene.NodeGene;
import com.vadeen.neat.genome.Genome;
import com.vadeen.neat.io.json.gene.ConnectionGeneJson;
import com.vadeen.neat.io.json.gene.NodeGeneJson;

import java.util.ArrayList;
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
    private int id;

    @JsonProperty
    private float fitness;

    @JsonProperty
    private List<NodeGeneJson> nodes;

    @JsonProperty
    private List<ConnectionGeneJson> connections;

    public static GenomeJson of(Genome genome) {
        GenomeJson json = new GenomeJson();
        json.id = genome.getId();
        json.fitness = genome.getFitness();
        json.nodes = genome.getNodes().values().stream()
                .map(NodeGeneJson::of)
                .collect(Collectors.toList());

        json.connections = genome.getConnections().values().stream()
                .sorted(Comparator.comparing(ConnectionGene::getInnovation))
                .map(ConnectionGeneJson::of)
                .collect(Collectors.toList());

        return json;
    }

    public static List<GenomeJson> of(List<Genome> genome) {
        List<GenomeJson> json = new ArrayList<>();
        for (Genome g : genome) {
            json.add(of(g));
        }
        return json;
    }

    public Genome toGenome(GeneFactory geneFactory) {
        List<NodeGene> nodes = this.nodes.stream()
                .map(j -> j.toNode(geneFactory))
                .collect(Collectors.toList());

        List<ConnectionGene> connections = this.connections.stream()
                .map(j -> j.toConnection(geneFactory))
                .collect(Collectors.toList());

        Genome genome = Genome.create(id, connections, nodes);
        genome.setFitness(fitness);
        return genome;
    }
}
