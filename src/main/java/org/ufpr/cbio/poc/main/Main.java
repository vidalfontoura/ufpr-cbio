package org.ufpr.cbio.poc.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ufpr.cbio.poc.ga.GeneticAlgorithm;
import org.ufpr.cbio.poc.ga.Individue;
import org.ufpr.cbio.poc.writer.OutputCSVWriter;

/**
 *
 *
 * @author user
 */
public class Main {

    private static final int INDIVIDUE_LENGHT = 100;
    private static final int POPULATION_SIZE = 500;
    private static final int GENERATIONS = 1000;
    private static final int MUTATION = 1;
    private static final int CROSSOVER = 3;
    private static final int SELECTION = 1;
    // private static final String PROTEIN_CHAIN =
    // "PPPPPPHPHHPPPPPHHHPHHHHHPHHPPPPHHPPHHPHHHHHPHHHHHHHHHHPHHPHHHHHHHPPPPPPPPPPPHHHHHHHPPHPHHHPPPPPPHPHH";
    private static final String PROTEIN_CHAIN =
        "HHPHHHPPPHHHHPHPHPHHHPPPPPHHHHHHHHPPPPPPHHHHHPPPHHHHHHPPPHHHPPPHHHHHPPPHHHHHPPPPHHHHPPPHHHHHPPPHHHHH";

    private static final boolean DYNAMIC_CHANGE_REFERENCE = true;
    private static final int MAX_REFERENCE_CHANGE = 30;
    private static final int MAX_REPEATED_FITNEES = 100;

    private static final double CROSSOVER_RATE = 0.9;
    private static final double MUTATION_RATE = 0.05;
    private static final int ELITISM_PERCENTAGE = 10;

    private static final String FILE_NAME =
        "C:\\Users\\user\\Desktop\\Mestrado\\UFPR\\Oficina\\PFP - AG\\Relatorio\\residues_%s_pop_%s_generations_%s_crossover_%s_crossrate_%s_mutation_%s_mutationrate_%s_referencechange_%s_.ods";

    public static void main(String[] args) throws IOException {

        OutputCSVWriter csvWriter = new OutputCSVWriter();
        List<Map<Integer, List<Individue>>> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            GeneticAlgorithm geneticAlgorithm =
                new GeneticAlgorithm(INDIVIDUE_LENGHT, POPULATION_SIZE, GENERATIONS, MUTATION, CROSSOVER, SELECTION,
                    PROTEIN_CHAIN, CROSSOVER_RATE, MUTATION_RATE, ELITISM_PERCENTAGE, i, DYNAMIC_CHANGE_REFERENCE,
                    MAX_REPEATED_FITNEES, MAX_REFERENCE_CHANGE);
            Map<Integer, List<Individue>> execute = geneticAlgorithm.execute();
            data.add(execute);
            System.out.println();
        }
        csvWriter.writeOutputCSV(String.format(FILE_NAME, PROTEIN_CHAIN.length(), POPULATION_SIZE, GENERATIONS,
            CROSSOVER, CROSSOVER_RATE, MUTATION, MUTATION_RATE, DYNAMIC_CHANGE_REFERENCE), data);
        System.out.println("Finished");

    }
}
