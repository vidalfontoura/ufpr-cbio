/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
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

    // Parâmetros
    private int INDIVIDUE_LENGHT;
    private int POPULATION_SIZE;
    private int GENERATIONS;
    private int MUTATION;
    private int CROSSOVER;
    private String PROTEIN_CHAIN;

    private int K = 0;

    private int[][] POPULATION;
    private int[] FITNESS;

    public GeneticAlgorithm(int INDIVIDUE_LENGHT, int POPULATION_SIZE, int GENERATIONS, int MUTATION,
        int CROSSOVER, String PROTEIN_CHAIN) {

        super();
        this.INDIVIDUE_LENGHT = INDIVIDUE_LENGHT;
        this.POPULATION_SIZE = POPULATION_SIZE;
        this.GENERATIONS = GENERATIONS;
        this.MUTATION = MUTATION;
        this.CROSSOVER = CROSSOVER;
        this.PROTEIN_CHAIN = PROTEIN_CHAIN;
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
            individue.setFitness(calculateIndividueFitness(result[i]));
            individues.add(individue);
        }
        return individues.stream().collect(Collectors.groupingBy(Individue::getFitness));

    }

    public int[][] executeAlgorithm() {

        // Inicialização
        generateInitialPopulation();

        // Avaliação inicial
        calculatePopulationFitness();

        // Gerações
        for (int i = 0; i < GENERATIONS; i++) {

            // Seleção
            for (int j = 0; j < 1; j++) {

                int[] newIndividue = null;
                int fitness = 0;

                // Cruzamento
                newIndividue = crossover(POPULATION[j]);

                // Mutação
                newIndividue = mutation(newIndividue);

                // Avaliação do novo indivíduo
                fitness = calculateIndividueFitness(newIndividue);

                // Comparação
                POPULATION[j] = selection(j, POPULATION[j], newIndividue);
            }
        }

        return POPULATION;
    }

    private void generateInitialPopulation() {

        POPULATION = new int[POPULATION_SIZE][INDIVIDUE_LENGHT];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            for (int j = 0; j < INDIVIDUE_LENGHT; j++) {
                POPULATION[i][j] = new Random().nextInt(2);
            }
        }
    }

    private int calculateIndividueFitness(int[] individue) {

        // Temporary if setup just to be possible to calculate the fitness
        // function Should be replace with the residue reference
        List<Residue> residues = null;
        if (PROTEIN_CHAIN.length() == 20) {
            residues = ResidueUtils.createDefaultReference20(PROTEIN_CHAIN);
        } else if (PROTEIN_CHAIN.length() == 100) {
            residues = ResidueUtils.createDefaultReference100(PROTEIN_CHAIN);
        } else {
            throw new UnsupportedOperationException("The only supported chain sizes are 20 and 100 at the moment");
        }

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

    private int[] crossover(int[] individue) {

        // TODO Faz o crossover passado como parâmetro
        switch (CROSSOVER) {
            case 1:
                return onePointCrossover(individue);
            case 2:
                return twoPointCrossover(individue);
            case 3:
                return uniformCrossover(individue);
            default:
                return null;
        }
    }

    private int[] onePointCrossover(int[] individue) {

        int[] aux;

        do {
            aux = POPULATION[new Random().nextInt(POPULATION_SIZE)];
        } while (aux == individue);

        int[] newIndividue = new int[INDIVIDUE_LENGHT];

        for (int i = 0; i < INDIVIDUE_LENGHT; i++) {
            newIndividue[i] = (i < (INDIVIDUE_LENGHT / 2)) ? individue[i] : aux[i];
        }

        return newIndividue;
    }

    private int[] twoPointCrossover(int[] individue) {

        int point1, point2;
        int[] aux;

        // garante que pelo menos haja 2 pontos diferentes sendo p1 < p2
        point1 = new Random().nextInt(INDIVIDUE_LENGHT - 1);

        do {
            point2 = new Random().nextInt(INDIVIDUE_LENGHT);
        } while (point1 > point2);

        do {
            aux = POPULATION[new Random().nextInt(POPULATION_SIZE)];
        } while (aux == individue);

        int[] newIndividue = new int[INDIVIDUE_LENGHT];

        for (int i = 0; i < newIndividue.length; i++) {
            newIndividue[i] = (i < point1 || i > point2) ? individue[i] : aux[i];
        }

        return newIndividue;
    }

    private int[] uniformCrossover(int[] individue) {

        int[] aux;

        do {
            aux = POPULATION[new Random().nextInt(POPULATION_SIZE)];
        } while (aux == individue);

        int[] newIndividue = new int[INDIVIDUE_LENGHT];

        for (int i = 0; i < newIndividue.length; i++) {
            int rand = new Random().nextInt(2);
            newIndividue[i] = (rand == 0) ? individue[i] : aux[i];
        }

        return newIndividue;
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

        for (int i = 0; i < K; i++) {
            int pos = new Random().nextInt(INDIVIDUE_LENGHT);
            individue[pos] = (individue[pos] == 0) ? 1 : 0;
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
        pos1 = new Random().nextInt(INDIVIDUE_LENGHT);

        do {
            pos2 = new Random().nextInt(INDIVIDUE_LENGHT);
        } while (pos2 == pos1);

        int aux = individue[pos1];
        individue[pos1] = individue[pos2];
        individue[pos2] = aux;

        return individue;
    }

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

    public void printIndividue(int[] individue) {

        for (int i = 0; i < individue.length; i++) {
            System.out.print(individue[i] + " ");
        }
        System.out.println();
    }
}
