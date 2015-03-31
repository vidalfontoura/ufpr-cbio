/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.utils;

/**
 *
 *
 * @author user
 */
public class TestRepair {

    public static void main(String[] args) {

        int[] x = new int[] { 1, 0, -1, -2, -3, -4, -4, -5, -5, -5, -5 };
        int[] y = new int[] { 2, 2, 2, 2, 2, 2, 3, 3, 2, 1, 0 };
        for (int i = 0; i < x.length; i++) {
            x[i] = x[i] - (-5);
            System.out.print(x[i]);
        }
        System.out.println();

        for (int j = 0; j < y.length; j++) {
            y[j] = y[j] - (0);
            System.out.print(y[j]);
        }

    }

}
