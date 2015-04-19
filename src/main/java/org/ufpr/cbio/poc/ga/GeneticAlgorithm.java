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

    // Parâmetros
    private int INDIVIDUE_LENGHT;
    private int POPULATION_SIZE;
    private int GENERATIONS;
    private int MUTATION;
    private int CROSSOVER;
    private int SELECTION;
    private String PROTEIN_CHAIN;
    private double CROSSOVER_RATE;
    private double MUTATION_RATE;
    private double ELITISM_PERCENTAGE;
    private List<Residue> residues;

    private int K = 0;
    private int TOURNAMENT_SIZE = 2;

    private int[][] POPULATION;
    private int[] FITNESS;
    private Random random;

    public GeneticAlgorithm(int INDIVIDUE_LENGHT, int POPULATION_SIZE, int GENERATIONS, int MUTATION, int CROSSOVER,
        int SELECTION, String PROTEIN_CHAIN, double CROSSOVER_RATE, double MUTATION_RATE, double ELITISM_PERCENTAGE,
        int SEED) {

        super();
        this.INDIVIDUE_LENGHT = INDIVIDUE_LENGHT;
        this.POPULATION_SIZE = POPULATION_SIZE;
        this.GENERATIONS = GENERATIONS;
        this.MUTATION = MUTATION;
        this.CROSSOVER = CROSSOVER;
        this.SELECTION = SELECTION;
        this.PROTEIN_CHAIN = PROTEIN_CHAIN;
        this.CROSSOVER_RATE = CROSSOVER_RATE;
        this.MUTATION_RATE = MUTATION_RATE;
        this.ELITISM_PERCENTAGE = ELITISM_PERCENTAGE;
        this.random = new Random(SEED);

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
    }

    public GeneticAlgorithm() {

    }

    public int getK() {

        return K;
    }

    public void setK(int k) {

        K = k;
    }

    public int getINDIVIDUE_LENGHT() {

        return INDIVIDUE_LENGHT;
    }

    public int getPOPULATION_SIZE() {

        return POPULATION_SIZE;
    }

    public int getGENERATIONS() {

        return GENERATIONS;
    }

    public int getMUTATION() {

        return MUTATION;
    }

    public int getCROSSOVER() {

        return CROSSOVER;
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
        int[] bestIndividue = getBestIndividue();

        return individues.stream().collect(Collectors.groupingBy(Individue::getFitness));

    }

    public int[][] executeAlgorithm() {

        // Inicialização
        generateInitialPopulation();

        // Avaliação inicial
        calculatePopulationFitness();

        // Gerações
        for (int i = 0; i < GENERATIONS; i++) {
            System.out.print("Generation: " + i);

            int[][] newPopulation = new int[POPULATION_SIZE][];
            int startIndex = 0;
            if (ELITISM_PERCENTAGE != 0) {
                startIndex = (int) (ELITISM_PERCENTAGE / 100) * POPULATION_SIZE;
                int[] bestsIndexes = getBestsIndexes();
                for (int j = 0; j < bestsIndexes.length; j++) {
                    newPopulation[j] = POPULATION[j];
                }
            }

            for (int j = startIndex; j < POPULATION_SIZE / 2; j++) {

                int[][] newIndividue = null;
                int fitness0 = 0;
                int fitness1 = 0;

                int[] parent1 = selections();
                int[] parent2 = selections();
                if (parent1 == null || parent2 == null) {
                }

                if (random.nextDouble() <= CROSSOVER_RATE) {
                    // Applying crossover using selected parent1 and selected
                    // parent2
                    newIndividue = crossover(parent1, parent2);
                } else {
                    // Returning the select parent because of the CROSSOVER_RATE
                    newIndividue = new int[][] { parent1, parent2 };
                }

                // Mutação
                newIndividue[0] = mutation(newIndividue[0]);
                newIndividue[1] = mutation(newIndividue[1]);

                // Avaliação do novo indivíduo
                fitness0 = calculateIndividueFitness(newIndividue[0]);
                fitness1 = calculateIndividueFitness(newIndividue[1]);

                newPopulation[j * 2] = newIndividue[0];
                newPopulation[j * 2 + 1] = newIndividue[1];

                FITNESS[j * 2] = fitness0;
                FITNESS[j * 2 + 1] = fitness1;

                // Comparação
                // POPULATION[j] = selection(j, POPULATION[j], newIndividue);
            }
            POPULATION = newPopulation;
            int bestFitness = getBestFitness();
            System.out.println(" Best Fitness: " + bestFitness);
            System.out.println("Individue:  " + Arrays.toString(getBestIndividue()));
            System.out.println();
        }

        return POPULATION;
    }

    /**
     * 
     */
    public int[] getBestsIndexes() {

        return findTopNValues(FITNESS, (int) ELITISM_PERCENTAGE);
    }

    public int[] findTopNValues(int[] values, int n) {

        int length = values.length;
        int[] indexes = new int[values.length];
        for (int i = 1; i < length; i++) {
            int curPos = i;
            while ((curPos > 0) && (values[i] > values[curPos - 1])) {
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
                POPULATION[i][j] = random.nextInt(2);
            }
        }
    }

    private int calculateIndividueFitness(int[] individue) {

        Controller controller = new Controller();
        Grid grid = controller.generateGrid(residues);
        EnumMovements[] movements = ResidueUtils.toMovementsArray(individue);

        for (int i = 0; i < individue.length; i++) {
            Movements.doMovement(residues.get(i), residues, grid, movements[i]);
        }
        return ResidueUtils.getTopologyContacts(residues, grid).size();

    }

    private void calculatePopulationFitness() {

        FITNESS = new int[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            FITNESS[i] = calculateIndividueFitness(POPULATION[i]);
        }
    }

    public int[][] crossover(int[] parent1, int[] parent2) {

        // TODO Faz o crossover passado como parâmetro
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

        // garante que pelo menos haja 2 pontos diferentes sendo p1 < p2
        point1 = random.nextInt(INDIVIDUE_LENGHT - 1);

        do {
            point2 = random.nextInt(INDIVIDUE_LENGHT);
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
            int rand = random.nextInt(2);
            newIndividue1[i] = (rand == 0) ? individue1[i] : individue2[i];
            newIndividue2[i] = (rand == 0) ? individue2[i] : individue1[i];

        }
        return new int[][] { newIndividue1, newIndividue2 };
    }

    private int[] mutation(int[] individue) {

        // TODO Faz a mutação passada como parâmetro
        switch (MUTATION) {
            case 1:
                return bitStringMutation(individue);
            case 2:
                return flipBitMutation(individue);
            case 3:
                return orderChangingMutation(individue);
            default:
                // TODO exception valor inválido
                return null;
        }
    }

    private int[] bitStringMutation(int[] individue) {

        for (int i = 0; i < individue.length; i++) {
            if (random.nextDouble() <= MUTATION_RATE) {
                individue[i] = (individue[i] == 0) ? 1 : 0;
            }
        }
        return individue;
    }

    private int[] flipBitMutation(int[] individue) {

        for (int i = 0; i < individue.length; i++) {
            individue[i] = (individue[i] == 0) ? 1 : 0;
        }
        return individue;
    }

    private int[] orderChangingMutation(int[] individue) {

        int pos1, pos2;
        pos1 = random.nextInt(INDIVIDUE_LENGHT);

        do {
            pos2 = random.nextInt(INDIVIDUE_LENGHT);
        } while (pos2 == pos1);

        int aux = individue[pos1];
        individue[pos1] = individue[pos2];
        individue[pos2] = aux;

        return individue;
    }

    // TODO: THis is being done at the end of the algorithm
    private int[] selection(int index, int[] oldIndividue, int[] newIndividue) {

        // TODO Compara o antigo e o novo individuo e retorna o melhor

        int oldFit = calculateIndividueFitness(oldIndividue);
        int newFit = calculateIndividueFitness(newIndividue);

        if (newFit > oldFit) {
            FITNESS[index] = newFit;
            return newIndividue;
        }

        return oldIndividue;
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

    public int[] selections() {

        switch (SELECTION) {
        // Tournament Selection
            case 1:
                Set<Integer> tournamentSet = new HashSet<>();
                for (int i = 0; i < TOURNAMENT_SIZE; i++) {
                    boolean added = tournamentSet.add(random.nextInt(POPULATION_SIZE));
                    while (!added) {
                        added = tournamentSet.add(random.nextInt(POPULATION_SIZE));
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
}
