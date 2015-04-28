/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.ga;

import org.junit.Test;

/**
 *
 *
 * @author user
 */
public class FindTopNNumbersFromArray {

    @Test
    public void test() {

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        int[] array = new int[] { 10, 1, 2, 4, 10, 12, 3, 4, 10, 2 };
        int[] findTopNValues = geneticAlgorithm.findTopNValues(array, 4);
        for (int i = 0; i < findTopNValues.length; i++) {
            System.out.print(findTopNValues[i] + ",");
        }
    }
}
