/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.ga;

import java.util.Arrays;

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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + fitness;
        result = prime * result + Arrays.hashCode(moves);
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Individue other = (Individue) obj;
        if (fitness != other.fitness)
            return false;
        if (!Arrays.equals(moves, other.moves))
            return false;
        return true;
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
