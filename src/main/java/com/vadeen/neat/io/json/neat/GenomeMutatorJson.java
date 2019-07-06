package com.vadeen.neat.io.json.neat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vadeen.neat.Random;
import com.vadeen.neat.gene.GeneFactory;
import com.vadeen.neat.genome.GenomeMutator;

public class GenomeMutatorJson {
    @JsonProperty
    private float nodeMutationProbability;

    @JsonProperty
    private float connectionMutationProbability;

    @JsonProperty
    private float weightMutationProbability;

    @JsonProperty
    private float weightMutationRenewProbability;

    @JsonProperty
    private float weightPerturbingFactor;

    public static GenomeMutatorJson of(GenomeMutator mutator) {
        GenomeMutatorJson json = new GenomeMutatorJson();
        json.nodeMutationProbability = mutator.getNodeMutationProbability();
        json.connectionMutationProbability = mutator.getConnectionMutationProbability();
        json.weightMutationProbability = mutator.getWeightMutationProbability();
        json.weightMutationRenewProbability = mutator.getWeightMutationRenewProbability();
        json.weightPerturbingFactor = mutator.getWeightPerturbingFactor();
        return json;
    }

    public GenomeMutator toGenomeMutator(Random random, GeneFactory geneFactory) {
        GenomeMutator genomeMutator = new GenomeMutator(random, geneFactory);
        genomeMutator.setNodeMutationProbability(nodeMutationProbability);
        genomeMutator.setConnectionMutationProbability(connectionMutationProbability);
        genomeMutator.setWeightMutationProbability(weightMutationProbability);
        genomeMutator.setWeightMutationRenewProbability(weightMutationRenewProbability);
        genomeMutator.setWeightPerturbingFactor(weightPerturbingFactor);
        return genomeMutator;
    }
}
