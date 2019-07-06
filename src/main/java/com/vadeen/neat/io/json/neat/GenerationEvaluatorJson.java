package com.vadeen.neat.io.json.neat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vadeen.neat.generation.Generation;
import com.vadeen.neat.generation.GenerationEvaluator;
import com.vadeen.neat.generation.GenerationFactory;
import com.vadeen.neat.genome.GenomeEvaluator;

public class GenerationEvaluatorJson {
    @JsonProperty
    private int unimprovedCount;

    @JsonProperty
    private int refocusThreshold;

    public static GenerationEvaluatorJson of(GenerationEvaluator evaluator) {
        GenerationEvaluatorJson json = new GenerationEvaluatorJson();
        json.unimprovedCount = evaluator.getUnimprovedCount();
        json.refocusThreshold = evaluator.getRefocusThreshold();
        return json;
    }

    public GenerationEvaluator toGenerationEvaluator(GenomeEvaluator genomeEvaluator,
                                                     GenerationFactory generationFactory, Generation generation) {
        GenerationEvaluator evaluator = new GenerationEvaluator(genomeEvaluator, generationFactory, generation);
        evaluator.setUnimprovedCount(unimprovedCount);
        evaluator.setRefocusThreshold(refocusThreshold);
        return evaluator;
    }
}
