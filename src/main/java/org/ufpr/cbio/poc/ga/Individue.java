/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.ga;

/**
 *
 *
 * @author user
 */
public class Individue {

    private int[] moves;
    private int fitness;

    /**
     * @return the moves
     */
    public int[] getMoves() {

        return moves;
    }

    /**
     * @param moves the moves to set
     */
    public void setMoves(int[] moves) {

        this.moves = moves;
    }

    /**
     * @return the fitness
     */
    public int getFitness() {

        return fitness;
    }

    /**
     * @param fitness the fitness to set
     */
    public void setFitness(int fitness) {

        this.fitness = fitness;
    }

}
