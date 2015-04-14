/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.ga;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.domain.TopologyContact;
import org.ufpr.cbio.poc.utils.Controller;
import org.ufpr.cbio.poc.utils.EnumMovements;
import org.ufpr.cbio.poc.utils.Movements;
import org.ufpr.cbio.poc.utils.ResidueUtils;

/**
 *
 *
 * @author vfontoura
 */
public class BKP_GeneticAlgorithm {

    private static final int DEFAULT_NUMBER_OF_CHROMOSOMES = 500;
    private static final int DEFAULT_NUMBER_OF_GENES = 100;

    private static final String PROTEIN_CHAIN_100 =
        "HHPHHHPPPHHHHPHPHPHHHPPPPPPHHHHHHHHPPPPPPHHHHHPPPHHHHHHPPPHHHPPPHHHHHHPPPHHHHHPPPPHHHHPPPHHHHHPPPHH";

    private static final String PROTEIN_CHAIN_20 = "HHPHHHPPPHHHHPHPHPHHH";

    private static final int SELECTION_K = 2;

    private static final int CROSSOVER_POINT = 10;

    public int[][] initPopulation(int numberOfChromosomes, int genes) {

        Random random = new Random();
        int[][] population = new int[numberOfChromosomes][genes];
        for (int i = 0; i < population.length; i++) {
            for (int j = 0; j < genes; j++) {

                population[i][j] = random.nextInt(2);
            }
        }
        return population;
    }

    public List<Individue> computeEnergyValue(int[][] population) {

        List<Individue> individues = new ArrayList<Individue>();
        for (int i = 0; i < population.length; i++) {
            int fitness = evaluateFitness(population[i]);
            Individue individue = new Individue();
            individue.setFitness(fitness);
            individue.setMoves(population[i]);
            individues.add(individue);
        }
        return individues;

    }

    public Map<Integer, List<Individue>> run() {

        int count = 0;
        int[][] initialPop = initPopulation(DEFAULT_NUMBER_OF_CHROMOSOMES, DEFAULT_NUMBER_OF_GENES);
        List<Individue> result = new ArrayList<>();
        while (count < 5000) {
            List<Individue> parents = computeEnergyValue(initialPop);

            Individue parentSelected1 = selectionTournament(parents, SELECTION_K);
            Individue parentSelected2 = selectionTournament(parents, SELECTION_K);

            int[][] crossoverPop = crossover(parents);
            int[][] mutatedPop = mutation(crossoverPop);
            List<Individue> childs = computeEnergyValue(mutatedPop);

            int[] worstChildsIndex = getWorstChildsIndex(childs);
            childs.set(worstChildsIndex[0], parentSelected1);
            childs.set(worstChildsIndex[1], parentSelected2);

            result = naturalSelection(parents, childs);
            count++;
        }

        Map<Integer, List<Individue>> collect = result.stream().collect(Collectors.groupingBy(Individue::getFitness));
        for (Integer value : collect.keySet()) {
            System.out.print(value + ":");
            System.out.println(collect.get(value).size());
        }
        return collect;
    }

    public List<Individue> naturalSelection(List<Individue> parents, List<Individue> childs) {

        List<Individue> result = Lists.newArrayList();
        for (int i = 0; i < parents.size(); i++) {
            if (parents.get(i).getFitness() > childs.get(i).getFitness()) {
                result.add(parents.get(i));
            } else {
                result.add(childs.get(i));
            }
        }
        return result;
    }

    public int[] getWorstChildsIndex(List<Individue> individues) {

        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;

        int index1 = 0;
        int index2 = 0;

        for (int i = 0; i < individues.size(); i++) {
            if (individues.get(i).getFitness() < min2 && individues.get(i).getFitness() >= min1) {
                min2 = individues.get(i).getFitness();
                index2 = i;
            }
            if (individues.get(i).getFitness() < min1) {
                min1 = individues.get(i).getFitness();
                index1 = i;
            }
        }
        return new int[] { index1, index2 };
    }

    public int[][] mutation(int[][] population) {

        for (int i = 0; i < population.length; i++) {
            int[] individue = population[i];
            Random random = new Random();
            int randomIndexMutation = random.nextInt(DEFAULT_NUMBER_OF_GENES);
            int value = individue[randomIndexMutation];
            if (value == 0) {
                individue[randomIndexMutation] = 1;
            } else {
                individue[randomIndexMutation] = 0;
            }
        }
        return population;
    }

    public int[][] crossover(List<Individue> parents) {

        int[][] childs = new int[DEFAULT_NUMBER_OF_CHROMOSOMES][DEFAULT_NUMBER_OF_GENES];
        for (int i = 0; i < parents.size(); i++) {
            if (i + 1 < DEFAULT_NUMBER_OF_CHROMOSOMES) {
                int[] parent1 = parents.get(i).getMoves();
                int randomIndex = new Random().nextInt(DEFAULT_NUMBER_OF_GENES + 1);
                int[] parent2 = parents.get(randomIndex).getMoves();

                int[] child1 = new int[parent1.length];
                int[] child2 = new int[parent1.length];
                for (int j = 0; j < CROSSOVER_POINT; j++) {
                    child1[j] = parent1[j];
                    child2[j] = parent2[j];
                }
                for (int j = CROSSOVER_POINT; j < parent2.length; j++) {
                    child1[j] = parent2[j];
                    child2[j] = parent1[j];
                }

                childs[i] = child1;
                childs[i + 1] = child2;

            }
        }
        return childs;
    }

    public int[][] crossoverRandomPoint(List<Individue> parents) {

        int[][] childs = new int[DEFAULT_NUMBER_OF_CHROMOSOMES][DEFAULT_NUMBER_OF_GENES];
        int crossPoint = new Random().nextInt(DEFAULT_NUMBER_OF_GENES + 1);
        for (int i = 0; i < parents.size(); i++) {
            if (i + 1 < DEFAULT_NUMBER_OF_CHROMOSOMES) {

                int[] parent1 = parents.get(i).getMoves();
                int randomIndex = new Random().nextInt(DEFAULT_NUMBER_OF_GENES + 1);
                int[] parent2 = parents.get(randomIndex).getMoves();

                int[] child1 = new int[parent1.length];
                int[] child2 = new int[parent1.length];
                for (int j = 0; j < crossPoint; j++) {
                    child1[j] = parent1[j];
                    child2[j] = parent2[j];
                }
                for (int j = crossPoint; j < parent2.length; j++) {
                    child1[j] = parent2[j];
                    child2[j] = parent1[j];
                }

                childs[i] = child1;
                childs[i + 1] = child2;

            }
        }
        return childs;
    }

    public int[][] crossoverPropostoGabriel(List<Individue> parents) {

        int[][] childs = new int[DEFAULT_NUMBER_OF_CHROMOSOMES][DEFAULT_NUMBER_OF_GENES];
        int lengthCrossPoints = DEFAULT_NUMBER_OF_GENES / 10;
        int[] crossPoints = new int[lengthCrossPoints];
        for (int i = 0; i < lengthCrossPoints; i++) {
            crossPoints[i] = new Random().nextInt(DEFAULT_NUMBER_OF_GENES - 1);
        }

        for (int i = 0; i < parents.size(); i++) {
            int[] parent1 = parents.get(i).getMoves();
            int randomIndex = 0;
            while (randomIndex == i && randomIndex == 0) {
                randomIndex = new Random().nextInt(DEFAULT_NUMBER_OF_GENES + 1);
            }
            int[] parent2 = parents.get(i).getMoves();

            for (int j = 0; j < crossPoints.length; j++) {
                int k = crossPoints[j];
                for (int t = 0; t < k; t++) {

                }
            }

        }
        return childs;
    }

    public Individue selectionTournament(List<Individue> movesEneryValues, int k) {

        Random random = new Random();
        List<Individue> tournamentValues = new ArrayList<Individue>();
        for (int i = 0; i < k; i++) {
            int index = random.nextInt(DEFAULT_NUMBER_OF_GENES);
            tournamentValues.add(movesEneryValues.get(index));
        }
        int fitness = 0;
        Individue selected = null;
        for (Individue individue : tournamentValues) {
            if (fitness <= individue.getFitness()) {
                selected = individue;
                fitness = individue.getFitness();
            }
        }
        return selected;

    }

    public int evaluateFitness(int[] individue) {

        EnumMovements[] movementsArray = ResidueUtils.toMovementsArray(individue);
        // TODO:Left as default sequence and using a random default generated
        // list of residues
        List<Residue> residuesReference = ResidueUtils.createDefaultReference100(PROTEIN_CHAIN_100);

        Grid generateGrid = new Controller().generateGrid(residuesReference);

        for (int i = 0; i < residuesReference.size(); i++) {
            Residue residue = residuesReference.get(i);
            Movements.doMovement(residue, residuesReference, generateGrid, movementsArray[i]);
        }

        Set<TopologyContact> topologyContacts = ResidueUtils.getTopologyContacts(residuesReference, generateGrid);
        return topologyContacts.size();

    }

    public static void main(String[] args) {

        BKP_GeneticAlgorithm algorithm = new BKP_GeneticAlgorithm();
        algorithm.run();
    }

}
