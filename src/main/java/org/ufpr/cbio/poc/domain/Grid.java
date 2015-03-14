package org.ufpr.cbio.poc.domain;

import java.util.List;

/**
 *
 *
 * @author vfontoura
 */
public class Grid {

    private int[][] matrix;

    /**
     * @param matrix
     */
    public Grid(int xDim, int yDim) {

        super();
        matrix = new int[xDim][yDim];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = -1;
            }

        }
    }

    /**
     * @param matrix
     */
    public Grid(int[][] matrix) {

        super();
        this.matrix = matrix;
    }

    /**
     * @return the matrix
     */
    public int[][] getMatrix() {

        return matrix;
    }

    /**
     * @param matrix the matrix to set
     */
    public void setMatrix(int[][] matrix) {

        this.matrix = matrix;
    }

    public int[][] buildResidueStructure(List<Residue> residues) {

        for (int i = 0; i < residues.size(); i++) {
            matrix[residues.get(i).getPoint().y][residues.get(i).getPoint().x] = i;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int value = matrix[i][j];
                System.out.print(value + "\t");
            }
            System.out.println();
        }

        return matrix;
    }
}
