/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.ga;

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
import org.ufpr.cbio.poc.utils.Controller;
import org.ufpr.cbio.poc.utils.EnumMovements;
import org.ufpr.cbio.poc.utils.Movements;
import org.ufpr.cbio.poc.utils.ResidueUtils;

/**
 *
 *
 * @author vfontoura
 */
public class GeneticAlgorithm {

    private static final String CROSSOVER_OPERATION_NOT_SUPPORTED = "The specified %s crossover method isn't supported";
    private static final String MUTATION_OPERATION_NOT_SUPPORTED = "The specified %s mutation method isn't supported";

    // Parameters
    private int INDIVIDUE_LENGHT;
    private int POPULATION_SIZE;
    private int GENERATIONS;
    private int MUTATION;
    private int CROSSOVER;
    private int SELECTION;
    private double CROSSOVER_RATE;
    private double MUTATION_RATE;
    private double ELITISM_PERCENTAGE;
    private List<Residue> residues;

    private int TOURNAMENT_SIZE = 2;

    private int[][] POPULATION;
    private int[] FITNESS;
    private Random RANDOM;

    private boolean DINAMICALLY_CHANGE_REFERENCE;
    private int MAX_STABLE_FITNESS;
    private int MAX_REFERENCE_CHANGES;

    public GeneticAlgorithm() {

        super();
    }

    public GeneticAlgorithm(int INDIVIDUE_LENGHT, int POPULATION_SIZE, int GENERATIONS, int MUTATION, int CROSSOVER,
        int SELECTION, String PROTEIN_CHAIN, double CROSSOVER_RATE, double MUTATION_RATE, double ELITISM_PERCENTAGE,
        int SEED, boolean DINAMICALLY_CHANGE_REFERENCE, int MAX_STABLE_FITNESS, int MAX_REFERENCE_CHANGES) {

        this(INDIVIDUE_LENGHT, POPULATION_SIZE, GENERATIONS, MUTATION, CROSSOVER, SELECTION, PROTEIN_CHAIN,
            CROSSOVER_RATE, MUTATION_RATE, ELITISM_PERCENTAGE, SEED, null, DINAMICALLY_CHANGE_REFERENCE,
            MAX_STABLE_FITNESS, MAX_REFERENCE_CHANGES);

    }

    public GeneticAlgorithm(int INDIVIDUE_LENGHT, int POPULATION_SIZE, int GENERATIONS, int MUTATION, int CROSSOVER,
        int SELECTION, String PROTEIN_CHAIN, double CROSSOVER_RATE, double MUTATION_RATE, double ELITISM_PERCENTAGE,
        int SEED, List<Residue> residues) {

        this(INDIVIDUE_LENGHT, POPULATION_SIZE, GENERATIONS, MUTATION, CROSSOVER, SELECTION, PROTEIN_CHAIN,
            CROSSOVER_RATE, MUTATION_RATE, ELITISM_PERCENTAGE, SEED, residues, false, 0, 0);
    }

    public GeneticAlgorithm(int INDIVIDUE_LENGHT, int POPULATION_SIZE, int GENERATIONS, int MUTATION, int CROSSOVER,
        int SELECTION, String PROTEIN_CHAIN, double CROSSOVER_RATE, double MUTATION_RATE, double ELITISM_PERCENTAGE,
        int SEED, List<Residue> residues, boolean DINAMICALLY_CHANGE_REFERENCE, int MAX_STABLE_FITNESS,
        int MAX_REFERENCE_CHANGES) {

        this.INDIVIDUE_LENGHT = INDIVIDUE_LENGHT;
        this.POPULATION_SIZE = POPULATION_SIZE;
        this.GENERATIONS = GENERATIONS;
        this.MUTATION = MUTATION;
        this.CROSSOVER = CROSSOVER;
        this.SELECTION = SELECTION;
        this.CROSSOVER_RATE = CROSSOVER_RATE;
        this.MUTATION_RATE = MUTATION_RATE;
        this.ELITISM_PERCENTAGE = ELITISM_PERCENTAGE;
        this.RANDOM = new Random(SEED);
        this.DINAMICALLY_CHANGE_REFERENCE = DINAMICALLY_CHANGE_REFERENCE;
        this.MAX_STABLE_FITNESS = MAX_STABLE_FITNESS;
        this.MAX_REFERENCE_CHANGES = MAX_REFERENCE_CHANGES;

        if (residues == null) {
            // Temporary if setup just to be possible to calculate the fitness
            // function Should be replace with the residue reference
            if (PROTEIN_CHAIN.length() == 10) {
                this.residues = ResidueUtils.createDefaultReference10(PROTEIN_CHAIN);
            } else if (PROTEIN_CHAIN.length() == 20) {
                this.residues = ResidueUtils.createDefaultReference20(PROTEIN_CHAIN);
            } else if (PROTEIN_CHAIN.length() == 100) {
                this.residues = ResidueUtils.createDefaultReference100(PROTEIN_CHAIN);
            } else {
                throw new UnsupportedOperationException("The only supported chain sizes are 20 and 100 at the moment");
            }
        } else {
            this.residues = residues;
        }
    }

    public Map<Integer, List<Individue>> execute() {

        int[][] result = executeAlgorithm();
        List<Individue> individues = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            Individue individue = new Individue();
            individue.setMoves(result[i]);
            individue.setFitness(FITNESS[i]);
            System.out.print("Fitness: " + FITNESS[i] + " ");
            System.out.println(Arrays.toString(result[i]));
            individues.add(individue);
        }

        for (int i = 0; i < result.length; i++) {
            System.out.print("Fitness do vetor: " + FITNESS[i]);
            System.out.print(" Calculado Fitness: " + calculateIndividueFitness(result[i]));
            System.out.println(" Individue: " + Arrays.toString(result[i]));
        }
        return individues.stream().collect(Collectors.groupingBy(Individue::getFitness));

    }

    public int[][] executeAlgorithm() {

        // Inicialização
        generateInitialPopulation();

        // Avaliação inicial
        calculatePopulationFitness();

        // Gerações
        if (DINAMICALLY_CHANGE_REFERENCE) {
            int repeatedMaxFitness = 0;
            int countReferenceChange = 0;
            int previousBestFitness = getBestFitness();
            int generations = 0;
            int currentFitness = 0;
            while (countReferenceChange <= MAX_REFERENCE_CHANGES) {
                if (repeatedMaxFitness > MAX_STABLE_FITNESS /**
                 * &&
                 * getBestFitness() >= currentFitness
                 */
                ) {
                    int[][] backupPopulation = POPULATION;
                    System.out.println("Changing from " + currentFitness + " to " + getBestFitness());
                    repeatedMaxFitness = 0;
                    int[] bestIndividue = getBestIndividue();
                    EnumMovements[] movements = ResidueUtils.toMovementsArray(bestIndividue);
                    Grid grid = new Controller().generateGrid(residues);
                    for (int i = 0; i < residues.size(); i++) {
                        Movements.doMovement(residues.get(i), residues, grid, movements[i]);
                    }
                    currentFitness = ResidueUtils.getTopologyContacts(residues, grid).size();
                    System.out.println("Reference changed using solution with fitness: " + currentFitness);

                    generateInitialPopulation();

                    calculatePopulationFitness();
                    // TODO: Check this with aurora
                    if (getBestFitness() < currentFitness) {
                        POPULATION = backupPopulation;
                        calculatePopulationFitness();

                    }
                    countReferenceChange++;

                    generations = 0;

                }
                POPULATION = evolvePopulation();
                generations++;
                System.out.println("Generations: " + generations + " Best Fitness " + getBestFitness());
                int newPopulationBestFitness = getBestFitness();
                if (newPopulationBestFitness <= previousBestFitness) {
                    repeatedMaxFitness++;
                } else {
                    previousBestFitness = newPopulationBestFitness;
                    repeatedMaxFitness = 0;

                }

            }
            return POPULATION;
        }
        for (int i = 0; i < GENERATIONS; i++) {
            System.out.print("Generation: " + i);
            POPULATION = evolvePopulation();
            System.out.println(" Best Fitness: " + getBestFitness());
        }
        return POPULATION;

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

    private void generateInitialPopulation() {

        POPULATION = new int[POPULATION_SIZE][INDIVIDUE_LENGHT];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            for (int j = 0; j < INDIVIDUE_LENGHT; j++) {
                POPULATION[i][j] = RANDOM.nextInt(2);
            }
        }
    }

    public int calculateIndividueFitness(int[] individue) {

        Controller controller = new Controller();
        List<Residue> cloneResidues = ResidueUtils.cloneResidueList(residues);
        Grid grid = controller.generateGrid(cloneResidues);
        EnumMovements[] movements = ResidueUtils.toMovementsArray(individue);
        for (int i = 0; i < cloneResidues.size(); i++) {
            Movements.doMovement(cloneResidues.get(i), cloneResidues, grid, movements[i]);
        }
        return ResidueUtils.getTopologyContacts(cloneResidues, grid).size();

    }

    private void calculatePopulationFitness() {

        FITNESS = new int[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            FITNESS[i] = calculateIndividueFitness(POPULATION[i]);
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

    public void printIndividue(int[] individue) {

        for (int i = 0; i < individue.length; i++) {
            System.out.print(individue[i] + " ");
        }
        System.out.println();
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

    public int[] cloneArray(int[] fitness) {

        int[] clone = new int[fitness.length];
        for (int i = 0; i < fitness.length; i++) {
            clone[i] = fitness[i];
        }
        return clone;
    }
}
