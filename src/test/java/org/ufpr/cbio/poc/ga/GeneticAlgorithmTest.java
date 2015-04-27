/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.ga;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

/**
 *
 *
 * @author vfontoura
 */
public class GeneticAlgorithmTest {

    private static final int SEED1 = 1;
    private static final int SEED2 = 2;

    private static final int INDIVIDUE_LENGTH = 10;
    private static final int[] PARENT1 = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private static final int[] PARENT2 = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

    private static final String PROTEIN_CHAIN = "PPHHPPHHPP";

    /**
     * <pre>
     * 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 
     * 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
     * 
     * 0, 0, 0, 0, 0, 1, 1, 1, 1, 1
     * 1, 1, 1, 1, 1, 0, 0, 0, 0, 0
     * 
     * </pre>
     */
    @Test
    public void testCrossover1() {

        double crossoverRate = 1;
        int crossoverMethod = 1;
        GeneticAlgorithm geneticAlgorithm =
            new GeneticAlgorithm(INDIVIDUE_LENGTH, 0, 0, 0, crossoverMethod, 0, PROTEIN_CHAIN, crossoverRate, 0, 0.0,
                SEED1, false, 0, 0);

        String expectedChild0 = "[0, 0, 0, 0, 0, 1, 1, 1, 1, 1]";
        String expectedChild1 = "[1, 1, 1, 1, 1, 0, 0, 0, 0, 0]";
        int[][] childs = geneticAlgorithm.crossover(PARENT1, PARENT2);
        Assert.assertEquals(expectedChild0, Arrays.toString(childs[0]));
        Assert.assertEquals(expectedChild1, Arrays.toString(childs[1]));

    }

    /**
     * <pre>
     * 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
     * 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
     * 
     * 0, 0, 0, 0, 0, 0, 1, 1, 1, 0
     * </pre>
     * 
     */
    @Test
    public void testCrossover2Seed1() {

        double crossoverRate = 1;
        int crossoverMethod = 2;
        GeneticAlgorithm geneticAlgorithm =
            new GeneticAlgorithm(INDIVIDUE_LENGTH, 0, 0, 0, crossoverMethod, 0, PROTEIN_CHAIN, crossoverRate, 0, 0.0,
                SEED1, false, 0, 0);

        String expectedChild0 = "[0, 0, 0, 0, 0, 0, 1, 1, 1, 0]";
        String expectedChild1 = "[1, 1, 1, 1, 1, 1, 0, 0, 0, 1]";

        int[][] childs = geneticAlgorithm.crossover(PARENT1, PARENT2);

        Assert.assertEquals(expectedChild0, Arrays.toString(childs[0]));
        Assert.assertEquals(expectedChild1, Arrays.toString(childs[1]));

    }

    /**
     * <pre>
     * 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
     * 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 
     * 
     * 0, 0, 0, 0, 1, 1, 1, 1, 0, 0
     * 1, 1, 1, 1, 0, 0, 0, 0, 1, 1
     * </pre>
     * 
     */
    @Test
    public void testCrossover2Seed2() {

        double crossoverRate = 1;
        int crossoverMethod = 2;
        GeneticAlgorithm geneticAlgorithm =
            new GeneticAlgorithm(INDIVIDUE_LENGTH, 0, 0, 0, crossoverMethod, 0, PROTEIN_CHAIN, crossoverRate, 0, 0.0,
                SEED2, false, 0, 0);

        String expectedChild0 = "[0, 0, 0, 0, 1, 1, 1, 1, 0, 0]";
        String expectedChild1 = "[1, 1, 1, 1, 0, 0, 0, 0, 1, 1]";

        int[][] childs = geneticAlgorithm.crossover(PARENT1, PARENT2);
        Assert.assertEquals(expectedChild0, Arrays.toString(childs[0]));
        Assert.assertEquals(expectedChild1, Arrays.toString(childs[1]));

    }

    /**
     * <pre>
     * 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
     * 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 
     * 
     * mask
     * 1, 0, 0, 0, 0, 0, 0, 1, 1, 1
     * 
     * 1, 0, 0, 0, 0, 0, 0, 1, 1, 1
     * </pre>
     * 
     */
    @Test
    public void testCrossover3Seed1() {

        double crossoverRate = 1;
        int crossoverMethod = 3;
        GeneticAlgorithm geneticAlgorithm =
            new GeneticAlgorithm(INDIVIDUE_LENGTH, 0, 0, 0, crossoverMethod, 0, PROTEIN_CHAIN, crossoverRate, 0, 0.0,
                SEED1, false, 0, 0);

        String expectedChild0 = "[1, 0, 0, 0, 0, 0, 0, 1, 1, 1]";
        String expectedChild1 = "[0, 1, 1, 1, 1, 1, 1, 0, 0, 0]";

        int[][] childs = geneticAlgorithm.crossover(PARENT1, PARENT2);
        Assert.assertEquals(expectedChild0, Arrays.toString(childs[0]));
        Assert.assertEquals(expectedChild1, Arrays.toString(childs[1]));

    }

    /**
     * <pre>
     * 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
     * 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 
     * 
     * mask
     * 1, 0, 1, 0, 0, 1, 1, 0, 1, 1
     * 
     * 1, 0, 1, 0, 0, 1, 1, 0, 1, 1
     * </pre>
     * 
     */
    @Test
    public void testCrossover3Seed2() {

        double crossoverRate = 1;
        int crossoverMethod = 3;
        GeneticAlgorithm geneticAlgorithm =
            new GeneticAlgorithm(INDIVIDUE_LENGTH, 0, 0, 0, crossoverMethod, 0, PROTEIN_CHAIN, crossoverRate, 0, 0.0,
                SEED2, false, 0, 0);

        String expectedChild0 = "[1, 0, 1, 0, 0, 1, 1, 0, 1, 1]";

        String expectedChild1 = "[0, 1, 0, 1, 1, 0, 0, 1, 0, 0]";

        int[][] childs = geneticAlgorithm.crossover(PARENT1, PARENT2);
        Assert.assertEquals(expectedChild0, Arrays.toString(childs[0]));
        Assert.assertEquals(expectedChild1, Arrays.toString(childs[1]));

    }

    /**
     * <pre>
     * 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 
     * 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
     * 
     * mask
     * 1, 0, 1, 0, 0, 1, 1, 0, 1, 1
     * 
     * 0, 1, 0, 1, 1, 0, 0, 1, 0, 0
     * </pre>
     * 
     */
    @Test
    public void testCrossover3Seed2ReverseParents() {

        double crossoverRate = 1;
        int crossoverMethod = 3;
        GeneticAlgorithm geneticAlgorithm =
            new GeneticAlgorithm(INDIVIDUE_LENGTH, 0, 0, 0, crossoverMethod, 0, PROTEIN_CHAIN, crossoverRate, 0, 0.0,
                SEED2, false, 0, 0);

        String expectedChild0 = "[0, 1, 0, 1, 1, 0, 0, 1, 0, 0]";

        String expectedChild1 = "[1, 0, 1, 0, 0, 1, 1, 0, 1, 1]";

        int[][] childs = geneticAlgorithm.crossover(PARENT2, PARENT1);
        Assert.assertEquals(expectedChild0, Arrays.toString(childs[0]));
        Assert.assertEquals(expectedChild1, Arrays.toString(childs[1]));

    }
}
