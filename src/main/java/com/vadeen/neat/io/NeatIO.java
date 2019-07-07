package com.vadeen.neat.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vadeen.neat.Neat;
import com.vadeen.neat.gene.GeneFactory;
import com.vadeen.neat.generation.Generation;
import com.vadeen.neat.genome.Genome;
import com.vadeen.neat.io.json.GenerationJson;
import com.vadeen.neat.io.json.GenomeJson;
import com.vadeen.neat.io.json.PrettyPrinter;

import java.io.File;
import java.io.IOException;

/**
 * Reads and writes genomes to json files.
 */
public class NeatIO {

    public static Genome genomeFromResource(String name) throws IOException {
        String filename = NeatIO.class.getClassLoader().getResource(name).getFile();
        File file = new File(filename);
        return readGenome(file);
    }

    /**
     * Load from resource and update factory innovation counter.
     */
    public static Genome genomeFromResource(String name, GeneFactory geneFactory) throws IOException {
        String filename = NeatIO.class.getClassLoader().getResource(name).getFile();
        File file = new File(filename);
        return readGenome(file, geneFactory);
    }

    public static Genome readGenome(File file) throws IOException {
        return readGenome(file, new GeneFactory());
    }

    /**
     * Load from file and update factory innovation counter.
     */
    public static Genome readGenome(File file, GeneFactory geneFactory) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        GenomeJson json = mapper.readValue(file, GenomeJson.class);
        return json.toGenome(geneFactory);
    }

    public static Generation readGeneration(File file, GeneFactory geneFactory) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        GenerationJson json = mapper.readValue(file, GenerationJson.class);
        return json.toGeneration(geneFactory);
    }

    public static void write(File file, Genome genome) throws IOException {
        GenomeJson json = GenomeJson.of(genome);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDefaultPrettyPrinter(new PrettyPrinter(1));
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, json);
    }

    public static void write(File file, Generation generation) throws IOException {
        GenerationJson json = GenerationJson.of(generation);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDefaultPrettyPrinter(new PrettyPrinter(3));
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, json);
    }

    public static void write(File file, Neat neat) {
//        NeatJson json =
    }
}
