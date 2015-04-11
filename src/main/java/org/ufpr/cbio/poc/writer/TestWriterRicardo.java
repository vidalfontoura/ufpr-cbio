/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.writer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ufpr.cbio.poc.ga.GeneticAlgorithmRicardo;
import org.ufpr.cbio.poc.ga.Individue;

/**
 *
 *
 * @author user
 */
public class TestWriterRicardo {

    private static final int INDIVIDUE_LENGHT = 100;
    private static final int POPULATION_SIZE = 100;
    private static final int GENERATIONS = 3000;
    private static final int MUTATION = 3;
    private static final int CROSSOVER = 1;
    private static final String PROTEIN_CHAIN =
        "HHPHHHPPPHHHHPHPHPHHHPPPPPPHHHHHHHHPPPPPPHHHHHPPPHHHHHHPPPHHHPPPHHHHHHPPPHHHHHPPPPHHHHPPPHHHHHPPPHHH";

    private static final String FILE_NAME =
        "C:\\Users\\user\\Desktop\\Mestrado\\UFPR\\Oficina\\PFP - AG\\Relatorio\\residues_%s_pop_%s_generations_%s_crossover_%s_mutation_%s.ods";

    public static void main(String[] args) throws IOException {

        GeneticAlgorithmRicardo geneticAlgorithm =
            new GeneticAlgorithmRicardo(INDIVIDUE_LENGHT, POPULATION_SIZE, GENERATIONS, MUTATION, CROSSOVER,
                PROTEIN_CHAIN);
        OutputCSVWriter csvWriter = new OutputCSVWriter();
        List<Map<Integer, List<Individue>>> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add(geneticAlgorithm.execute());
            System.out.println();
        }
        csvWriter.writeOutputCSV(
            String.format(FILE_NAME, PROTEIN_CHAIN.length(), POPULATION_SIZE, GENERATIONS, CROSSOVER, MUTATION), data);
        System.out.println("Finished");

    }
}
