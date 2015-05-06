/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.psp.relative.ga;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.ga.Individue;
import org.ufpr.cbio.poc.utils.Controller;
import org.ufpr.cbio.poc.utils.ResidueUtils;
import org.ufpr.cbio.poc.writer.OutputCSVWriter;

/**
 *
 *
 * @author vfontoura
 */
public class RelativeGeneticAlgorithm {

    private static final String CROSSOVER_OPERATION_NOT_SUPPORTED = "The specified %s crossover method isn't supported";
    private static final String MUTATION_OPERATION_NOT_SUPPORTED = "The specified %s mutation method isn't supported";

    private int INDIVIDUE_LENGHT;
    private int POPULATION_SIZE;
    private int GENERATIONS;
    private int MUTATION;
    private int CROSSOVER;
    private int SELECTION;
    private double CROSSOVER_RATE;
    private double MUTATION_RATE;
    private double ELITISM_PERCENTAGE;
    private int[][] POPULATION;
    private int[] FITNESS;
    private Random RANDOM;
    private String PROTEIN_CHAIN;
    private int TOURNAMENT_SIZE = 2;

    public RelativeGeneticAlgorithm(int POPULATION_SIZE, int GENERATIONS, int MUTATION, int CROSSOVER, int SELECTION,
        double CROSSOVER_RATE, double MUTATION_RATE, double ELITISM_PERCENTAGE, String PROTEIN_CHAIN, int SEED) {

        this.POPULATION_SIZE = POPULATION_SIZE;
        this.GENERATIONS = GENERATIONS;
        this.MUTATION = MUTATION;
        this.CROSSOVER = CROSSOVER;
        this.SELECTION = SELECTION;
        this.CROSSOVER_RATE = CROSSOVER_RATE;
        this.MUTATION_RATE = MUTATION_RATE;
        this.ELITISM_PERCENTAGE = ELITISM_PERCENTAGE;
        this.RANDOM = new Random(SEED);
        this.PROTEIN_CHAIN = PROTEIN_CHAIN;
        this.INDIVIDUE_LENGHT = PROTEIN_CHAIN.length() - 2;
    }

    public int[][] executeAlgorithm() {

        // Inicialization
        generateInitialPopulation();

        // Initial evalutation
        calculatePopulationFitness();

        for (int i = 0; i < GENERATIONS; i++) {
            System.out.print("Generation: " + i);
            POPULATION = evolvePopulation();
            System.out.print(" Best Fitness: " + getBestFitness());
            System.out.println(" Individuo: " + Arrays.toString(getBestIndividue()).replace(" ", ""));
        }
        return POPULATION;
    }

    private int[] getBestIndividue() {

        int bestIndex = 0;
        int[] best = POPULATION[0];

        for (int i = 1; i < POPULATION_SIZE; i++) {

            if (FITNESS[i] > FITNESS[bestIndex]) {
                best = POPULATION[i];
                bestIndex = i;
            }
        }
        return best;
    }

    private int getBestFitness() {

        int fitness = 0;
        for (int i = 1; i < POPULATION_SIZE; i++) {
            if (FITNESS[i] > fitness) {
                fitness = FITNESS[i];
            }
        }
        return fitness;
    }

    private void generateInitialPopulation() {

        POPULATION = new int[POPULATION_SIZE][INDIVIDUE_LENGHT];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            for (int j = 0; j < INDIVIDUE_LENGHT; j++) {
                POPULATION[i][j] = RANDOM.nextInt(3);
            }
        }
    }

    public int[][] evolvePopulation() {

        int[][] newPopulation = new int[POPULATION_SIZE][];
        int startIndex = 0;
        if (ELITISM_PERCENTAGE != 0) {
            startIndex = (int) Math.round((ELITISM_PERCENTAGE / 100) * POPULATION_SIZE);
            if (startIndex % 2 != 0) {
                startIndex = startIndex - 1;
            }
            int[] bestsIndexes = getBestsIndexes();
            int[] cloneFitnessArray = cloneArray(FITNESS);
            for (int j = 0; j < bestsIndexes.length; j++) {
                newPopulation[j] = POPULATION[bestsIndexes[j]];
                FITNESS[j] = cloneFitnessArray[bestsIndexes[j]];
            }
        }

        for (int j = 0; j < ((POPULATION_SIZE - startIndex) / 2); j++) {

            int[][] newIndividues = null;
            int fitness0 = 0;
            int fitness1 = 0;

            int[] parent1 = selection();
            int[] parent2 = selection();

            if (RANDOM.nextDouble() <= CROSSOVER_RATE) {
                // Applying crossover using selected parent1 and selected
                // parent2
                newIndividues = crossover(parent1, parent2);
            } else {
                // Returning the select parent because of the CROSSOVER_RATE
                newIndividues = new int[][] { parent1, parent2 };
            }

            // Mutation
            newIndividues[0] = mutation(newIndividues[0]);
            newIndividues[1] = mutation(newIndividues[1]);

            // Evaluating the new individues
            fitness0 = calculateIndividueFitness(newIndividues[0]);
            fitness1 = calculateIndividueFitness(newIndividues[1]);

            newPopulation[j * 2 + startIndex] = newIndividues[0];
            newPopulation[j * 2 + startIndex + 1] = newIndividues[1];

            FITNESS[j * 2 + startIndex] = fitness0;
            FITNESS[j * 2 + startIndex + 1] = fitness1;
        }
        return newPopulation;

    }

    public int[] getBestsIndexes() {

        return findTopNValues(FITNESS, (int) Math.round((ELITISM_PERCENTAGE / 100) * POPULATION_SIZE));
    }

    public int[] findTopNValues(int[] fitness, int n) {

        int[] values = cloneArray(fitness);
        int length = values.length;
        int[] indexes = new int[values.length];
        for (int i = 1; i < length; i++) {
            int curPos = i;
            while ((curPos > 0) && (values[i] >= values[curPos - 1])) {
                curPos--;
            }

            if (curPos != i) {
                int element = values[i];
                System.arraycopy(values, curPos, values, curPos + 1, (i - curPos));
                values[curPos] = element;
                indexes[curPos] = i;
            }
        }
        return Arrays.copyOf(indexes, n);
    }

    public int[] selection() {

        switch (SELECTION) {
        // Tournament Selection
            case 1:
                Set<Integer> tournamentSet = new HashSet<>();
                for (int i = 0; i < TOURNAMENT_SIZE; i++) {
                    boolean added = tournamentSet.add(RANDOM.nextInt(POPULATION_SIZE));
                    while (!added) {
                        added = tournamentSet.add(RANDOM.nextInt(POPULATION_SIZE));
                    }
                }

                int maxFitness = Integer.MIN_VALUE;
                int[] fittest = null;
                for (int index : tournamentSet) {
                    int fitness = FITNESS[index];
                    if (fitness >= maxFitness) {
                        maxFitness = fitness;
                        fittest = POPULATION[index];
                    }
                }
                return fittest;
            default:
                throw new UnsupportedOperationException("This operation isn't supported yet");
        }
    }

    public int[][] crossover(int[] parent1, int[] parent2) {

        // Apply the crossover by the given parameter
        switch (CROSSOVER) {
            case 1:
                return onePointCrossover(parent1, parent2);
            case 2:
                return twoPointCrossover(parent1, parent2);
            case 3:
                return uniformCrossover(parent1, parent2);
            default:
                throw new UnsupportedOperationException(String.format(CROSSOVER_OPERATION_NOT_SUPPORTED, CROSSOVER));
        }
    }

    private int[][] onePointCrossover(int[] individue1, int[] individue2) {

        int[] newIndividue1 = new int[INDIVIDUE_LENGHT];
        int[] newIndividue2 = new int[INDIVIDUE_LENGHT];

        for (int i = 0; i < INDIVIDUE_LENGHT; i++) {
            newIndividue1[i] = (i < (INDIVIDUE_LENGHT / 2)) ? individue1[i] : individue2[i];
            newIndividue2[i] = (i < (INDIVIDUE_LENGHT / 2)) ? individue2[i] : individue1[i];
        }

        return new int[][] { newIndividue1, newIndividue2 };
    }

    private int[][] twoPointCrossover(int[] individue1, int[] individue2) {

        int point1, point2;

        // Ensure that the two points are different p1 < p2
        point1 = RANDOM.nextInt(INDIVIDUE_LENGHT - 1);

        do {
            point2 = RANDOM.nextInt(INDIVIDUE_LENGHT);
        } while (point1 > point2);

        int[] newIndividue1 = new int[INDIVIDUE_LENGHT];
        int[] newIndividue2 = new int[INDIVIDUE_LENGHT];

        for (int i = 0; i < newIndividue1.length; i++) {
            newIndividue1[i] = (i < point1 || i > point2) ? individue1[i] : individue2[i];
            newIndividue2[i] = (i < point1 || i > point2) ? individue2[i] : individue1[i];
        }

        return new int[][] { newIndividue1, newIndividue2 };
    }

    private int[][] uniformCrossover(int[] individue1, int[] individue2) {

        int[] newIndividue1 = new int[INDIVIDUE_LENGHT];
        int[] newIndividue2 = new int[INDIVIDUE_LENGHT];

        for (int i = 0; i < newIndividue1.length; i++) {
            int rand = RANDOM.nextInt(2);
            newIndividue1[i] = (rand == 0) ? individue1[i] : individue2[i];
            newIndividue2[i] = (rand == 0) ? individue2[i] : individue1[i];

        }
        return new int[][] { newIndividue1, newIndividue2 };
    }

    private int[] mutation(int[] individue) {

        // Apply the mutation by the given parameter
        switch (MUTATION) {
            case 1:
                return bitFlipMutation(individue);
            case 2:
                return swapMutation(individue);
            default:
                throw new UnsupportedOperationException(String.format(MUTATION_OPERATION_NOT_SUPPORTED, MUTATION));
        }
    }

    private int[] bitFlipMutation(int[] individue) {

        int[] newIndividue = cloneArray(individue);
        for (int i = 0; i < newIndividue.length; i++) {
            if (RANDOM.nextDouble() <= MUTATION_RATE) {
                newIndividue[i] = (newIndividue[i] == 0) ? 1 : 0;
            }
        }
        return newIndividue;
    }

    private int[] swapMutation(int[] individue) {

        int[] newIndividue = cloneArray(individue);
        int pos1, pos2;
        pos1 = RANDOM.nextInt(INDIVIDUE_LENGHT);

        do {
            pos2 = RANDOM.nextInt(INDIVIDUE_LENGHT);
        } while (pos2 == pos1);

        int aux = newIndividue[pos1];
        newIndividue[pos1] = newIndividue[pos2];
        newIndividue[pos2] = aux;

        return newIndividue;
    }

    private void calculatePopulationFitness() {

        FITNESS = new int[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            FITNESS[i] = calculateIndividueFitness(POPULATION[i]);
        }
    }

    public int calculateIndividueFitness(int[] individue) {

        Controller controller = new Controller();
        List<Residue> residues = controller.parseInput(PROTEIN_CHAIN, individue);
        Grid grid = controller.generateGrid(residues);
        return ResidueUtils.getTopologyContacts(residues, grid).size() - ResidueUtils.getCollisionsCount(residues);
    }

    public int[] cloneArray(int[] fitness) {

        int[] clone = new int[fitness.length];
        for (int i = 0; i < fitness.length; i++) {
            clone[i] = fitness[i];
        }
        return clone;
    }

    public static void main(String[] args) throws IOException {

        // String PROTEIN_CHAIN = "HPHPPHHPHPPHPHHPPHPH";
        // 85
        // String PROTEIN_CHAIN =
        // "HHHHPPPPHHHHHHHHHHHHPPPPPPHHHHHHHHHHHHPPPHHHHHHHHHHHHPPPHHHHHHHHHHHHPPPHPPHHPPHHPPHPH";
        String PROTEIN_CHAIN =
            "PPPPPPHPHHPPPPPHHHPHHHHHPHHPPPPHHPPHHPHHHHHPHHHHHHHHHHPHHPHHHHHHHPPPPPPPPPPPHHHHHHHPPHPHHHPPPPPPHPHH";
        int POPULATION_SIZE = 500;
        int GENERATIONS = 300;
        int CROSSOVER = 3;
        int MUTATION = 1;
        int SELECTION = 1;
        double CROSSOVER_RATE = 0.9;
        double MUTATION_RATE = 0.02;
        double ELITISM = 10;
        String FILE_NAME =
            "residues_%s_pop_%s_generations_%s_crossover_%s_crossrate_%s_mutation_%s_mutationrate_%s_eletism_%s.ods";

        OutputCSVWriter csvWriter = new OutputCSVWriter();
        List<Map<Integer, List<Individue>>> data = new ArrayList<>();
        for (int t = 3; t < 4; t++) {
            RelativeGeneticAlgorithm relativeGeneticAlgorithm =
                new RelativeGeneticAlgorithm(POPULATION_SIZE, GENERATIONS, MUTATION, CROSSOVER, SELECTION,
                    CROSSOVER_RATE, MUTATION_RATE, ELITISM, PROTEIN_CHAIN, t);
            int[][] finalPopulation = relativeGeneticAlgorithm.executeAlgorithm();
            Controller controller = new Controller();
            List<Individue> individues = new ArrayList<>();
            for (int i = 0; i < finalPopulation.length; i++) {
                List<Residue> residues = controller.parseInput(PROTEIN_CHAIN, finalPopulation[i]);
                int collisionsCount = ResidueUtils.getCollisionsCount(residues);
                if (collisionsCount == 0 && residues.size() == PROTEIN_CHAIN.length()) {
                    Individue individue = new Individue();
                    individue.setMoves(finalPopulation[i]);
                    individue.setFitness(relativeGeneticAlgorithm.calculateIndividueFitness(finalPopulation[i]));
                    individues.add(individue);
                }
            }
            Map<Integer, List<Individue>> collect =
                individues.stream().collect(Collectors.groupingBy(Individue::getFitness));
            Integer maxFitness = Integer.MIN_VALUE;
            for (Integer integer : collect.keySet()) {
                if (integer > maxFitness) {
                    maxFitness = integer;
                }
            }
            System.out.println("Max Fitness without collisions: " + maxFitness);
            List<Individue> list = collect.get(maxFitness);
            if (list != null) {
                System.out.println("Size: " + list.size());
                for (int i = 0; i < list.size(); i++) {
                    Individue individue = list.get(i);
                    int[] moves = individue.getMoves();
                    List<Residue> residues = controller.parseInput(PROTEIN_CHAIN, moves);
                    System.out.println(Arrays.toString(moves).replace(" ", ""));
                    // for (int j = 0; j < residues.size(); j++) {
                    // System.out.println("residues.add(new Residue(new Point("
                    // + residues.get(j).getPoint().getX()
                    // + ", " + residues.get(j).getPoint().getY()
                    // +
                    // "), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt("
                    // + j + ")))));");
                    // }
                    System.out.println("-------------------------------------------");

                }
                data.add(collect);
            }

        }
        csvWriter.writeOutputCSV(String.format(FILE_NAME, PROTEIN_CHAIN.length(), POPULATION_SIZE, GENERATIONS,
            CROSSOVER, CROSSOVER_RATE, MUTATION, MUTATION_RATE, ELITISM), data);
        System.out.println("Finished");

    }
}
