package org.ufpr.cbio.poc.utils;

import java.util.List;

import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Residue;

public class Movements {

    public static void doMovement(Residue residue, List<Residue> residues, Grid grid, EnumMovements movement) {

        switch (movement) {
            case ROTATE_90_CLOCKWISE:
                rotate90Clockwise(residue, residues, grid);
                break;
            case ROTATE_180_CLOCKWISE:
                rotate180Clockwise(residue, residues, grid);
                break;
            case CORNER:
                moveCorner(residue, residues, grid);
                break;
            case CRANKSHAFT:
                moveCrankshaft(residue, residues, grid);
                ;
                break;
            default:
                break;
        }
    }

    private static void rotate90Clockwise(Residue residue, List<Residue> residues, Grid grid) {

        int x, y;
        int[][] matrix;

        x = residue.getPoint().getX();
        y = residue.getPoint().getY();

        matrix = grid.getMatrix();

        grid.printResidueStructure();// remover

        if (residue != residues.get(0) && residue != residues.get(residues.size() - 1))
            return;

        // CASO 1 X1 < X2
        if (x + 1 < matrix.length && isNeighbor(matrix, x, y, x + 1, y)) {
            if (matrix[y + 1][x + 1] == -1) {
                matrix[y + 1][x + 1] = matrix[y][x];
                matrix[y][x] = -1;
                residue.getPoint().setX(x + 1);
                residue.getPoint().setY(y + 1);
            }
        }
        // CASO 2 Y1 > Y2
        else if (y - 1 >= 0 && isNeighbor(matrix, x, y, x, y - 1)) {
            if (matrix[y - 1][x + 1] == -1) {
                matrix[y - 1][x + 1] = matrix[y][x];
                matrix[y][x] = -1;
                residue.getPoint().setX(x + 1);
                residue.getPoint().setY(y - 1);
            }
        }
        // CASO 3 X1 > X2
        else if (x - 1 >= 0 && isNeighbor(matrix, x, y, x - 1, y)) {
            if (matrix[y - 1][x - 1] == -1) {
                matrix[y - 1][x - 1] = matrix[y][x];
                matrix[y][x] = -1;
                residue.getPoint().setX(x - 1);
                residue.getPoint().setY(y - 1);
            }
        }
        // CASO 4 Y1 < Y2
        else if (y + 1 < matrix.length && isNeighbor(matrix, x, y, x, y + 1)) {
            if (matrix[y + 1][x - 1] == -1) {
                matrix[y + 1][x - 1] = matrix[y][x];
                matrix[y][x] = -1;
                residue.getPoint().setX(x - 1);
                residue.getPoint().setY(y + 1);
            }
        }

        grid.setMatrix(matrix);

        grid.printResidueStructure();// remover
    }

    private static void rotate180Clockwise(Residue residue, List<Residue> residues, Grid grid) {

        int x, y;
        int[][] matrix;

        x = residue.getPoint().getX();
        y = residue.getPoint().getY();

        matrix = grid.getMatrix();

        grid.printResidueStructure();// remover

        if (residue != residues.get(0) && residue != residues.get(residues.size() - 1))
            return;

        // CASO 1 X1 < X2
        if (x + 1 < matrix.length && isNeighbor(matrix, x, y, x + 1, y)) {
            if (matrix[y][x + 2] == -1) {
                matrix[y][x + 2] = matrix[y][x];
                matrix[y][x] = -1;
                residue.getPoint().setX(x + 2);
            }
        }
        // CASO 2 Y1 > Y2
        else if (y - 1 >= 0 && isNeighbor(matrix, x, y, x, y - 1)) {
            if (matrix[y - 2][x] == -1) {
                matrix[y - 2][x] = matrix[y][x];
                matrix[y][x] = -1;
                residue.getPoint().setY(y - 2);
            }
        }
        // CASO 3 X1 > X2
        else if (x - 1 >= 0 && isNeighbor(matrix, x, y, x - 1, y)) {
            if (matrix[y][x - 2] == -1) {
                matrix[y][x - 2] = matrix[y][x];
                matrix[y][x] = -1;
                residue.getPoint().setX(x - 2);
            }
        }
        // CASO 4 Y1 < Y2
        else if (y + 1 < matrix.length && isNeighbor(matrix, x, y, x, y + 1)) {
            if (matrix[y + 2][x] == -1) {
                matrix[y + 2][x] = matrix[y][x];
                matrix[y][x] = -1;
                residue.getPoint().setY(y + 2);
            }
        }

        grid.setMatrix(matrix);

        grid.printResidueStructure();// remover
    }

    private static void moveCorner(Residue residue, List<Residue> residues, Grid grid) {

        int x = residue.getPoint().getX();
        int y = residue.getPoint().getY();

        int[][] matrix = grid.getMatrix();

        grid.printResidueStructure();

        if (residue == residues.get(0) && residue == residues.get(residues.size() - 1)) {
            return;
        }
        if (isLeftBottomCornerMatrix(matrix, x, y) && isEmpty(matrix, x + 1, y - 1)) {
            residue.getPoint().setX(x + 1);
            residue.getPoint().setY(y - 1);
            matrix[y - 1][x + 1] = matrix[y][x];
            matrix[y][x] = -1;

        }
        if (isLeftUpperCornerMatrix(matrix, x, y) && isEmpty(matrix, x + 1, y + 1)) {
            residue.getPoint().setX(x + 1);
            residue.getPoint().setY(y + 1);
            matrix[y + 1][x + 1] = matrix[y][x];
            matrix[y][x] = -1;
        }
        if (isRightBottomCornerMatrix(matrix, x, y) && isEmpty(matrix, x - 1, y - 1)) {
            residue.getPoint().setX(x - 1);
            residue.getPoint().setY(y - 1);
            matrix[y - 1][x - 1] = matrix[y][x];
            matrix[y][x] = -1;
        }
        if (isRightUpperCornerMatrix(matrix, x, y) && isEmpty(matrix, x - 1, y + 1)) {
            residue.getPoint().setX(x - 1);
            residue.getPoint().setY(y + 1);
            matrix[y + 1][x - 1] = matrix[y][x];
            matrix[y][x] = -1;
        }

    }

    private static void moveCrankshaft(Residue residue, List<Residue> residues, Grid grid) {

        int x = residue.getPoint().getX();
        int y = residue.getPoint().getY();

        int[][] matrix = grid.getMatrix();

        grid.printResidueStructure();

        if (residue == residues.get(0) && residue == residues.get(residues.size() - 1)) {
            return;
        }
        // if the residue sequence started from 0 to n
        if (isLeftBottomCornerMatrix(matrix, x, y) && !isEmpty(matrix, x + 1, y - 1)) {

            // if crankshaft is horizontal
            if (matrix[y - 2][x] == -1 && matrix[y - 2][x + 1] == -1) {
                Residue next = residues.get(matrix[y][x + 1]);

                int xnext = next.getPoint().getX();
                int ynext = next.getPoint().getY();

                if (matrix[y - 1][x - 1] == matrix[y][x] - 2
                    && matrix[ynext - 1][xnext + 1] == matrix[ynext][xnext] + 2) {
                    residue.getPoint().setX(x);
                    residue.getPoint().setY(y - 2);

                    next.getPoint().setY(y - 2);

                    matrix[y - 2][x] = matrix[y][x];
                    matrix[y][x] = -1;

                    // TODO: rever se tem como usar ynext e xnext
                    matrix[y - 2][x + 1] = matrix[y][x + 1];
                    matrix[y][x + 1] = -1;

                }

            }

            // if the crankshaft is in the vertical
            if (matrix[y][x + 2] == -1 && matrix[y - 1][x + 2] == -1) {

                Residue next = residues.get(matrix[y - 1][x]);
                int xnext = next.getPoint().getX();
                int ynext = next.getPoint().getY();

                if (matrix[y + 1][x + 1] == matrix[y][x] - 2
                    && matrix[ynext - 1][xnext + 1] == matrix[ynext][xnext] + 2) {
                    residue.getPoint().setX(x + 2);
                    residue.getPoint().setY(y);

                    next.getPoint().setX(x + 2);
                    next.getPoint().setY(ynext);

                    matrix[y][x + 2] = matrix[y][x];
                    matrix[y][x] = -1;

                    // TODO: rever se tem como usar ynext e xnext
                    matrix[y - 1][x + 2] = matrix[y - 1][x];
                    matrix[y - 1][x] = -1;

                }
            }

        }
        // 0 to n
        if (isLeftUpperCornerMatrix(matrix, x, y) && !isEmpty(matrix, x + 1, y + 1)) {

            if (matrix[y + 2][x] == -1 && matrix[y + 2][x + 1] == -1) {

                Residue next = residues.get(matrix[y][x + 1]);
                int xnext = next.getPoint().getX();
                int ynext = next.getPoint().getY();

                // if the crankshaft is in the horizontal
                if (matrix[y + 1][x - 1] == matrix[y][x] - 2
                    && matrix[ynext + 1][xnext + 1] == matrix[ynext][xnext] + 2) {
                    residue.getPoint().setX(x);
                    residue.getPoint().setY(y + 2);

                    next.getPoint().setY(y + 2);

                    matrix[y + 2][x] = matrix[y][x];
                    matrix[y][x] = -1;

                    // TODO: rever se tem como usar ynext e xnext
                    matrix[y + 2][x + 1] = matrix[y][x + 1];
                    matrix[y][x + 1] = -1;

                }

            }

            // if the crankshaft is in the vertical
            if (matrix[y][x + 2] == -1 && matrix[y + 1][x + 2] == -1) {

                Residue next = residues.get(matrix[y + 1][x]);
                int xnext = next.getPoint().getX();
                int ynext = next.getPoint().getY();

                if (matrix[y - 1][x + 1] == matrix[y][x] - 2
                    && matrix[ynext + 1][xnext + 1] == matrix[ynext][xnext] + 2) {
                    residue.getPoint().setX(x + 2);
                    residue.getPoint().setY(y);

                    next.getPoint().setX(x + 2);
                    next.getPoint().setY(ynext);

                    matrix[y][x + 2] = matrix[y][x];
                    matrix[y][x] = -1;

                    // TODO: rever se tem como usar ynext e xnext
                    matrix[y + 1][x + 2] = matrix[y + 1][x];
                    matrix[y + 1][x] = -1;

                }
            }
        }

        // if the residue started from n to 0
        if (isRightBottomCornerMatrix(matrix, x, y) && !isEmpty(matrix, x - 1, y - 1)) {

            // if the crankshaft is on horizontal
            if (matrix[y - 2][x] == -1 && matrix[y - 2][x - 1] == -1) {
                Residue next = residues.get(matrix[y][x - 1]);

                int xnext = next.getPoint().getX();
                int ynext = next.getPoint().getY();

                if (matrix[y - 1][x + 1] == matrix[y][x] - 2
                    && matrix[ynext - 1][xnext - 1] == matrix[ynext][xnext] + 2) {
                    residue.getPoint().setX(x);
                    residue.getPoint().setY(y - 2);

                    next.getPoint().setY(y - 2);

                    matrix[y - 2][x] = matrix[y][x];
                    matrix[y][x] = -1;

                    // TODO: rever se tem como usar ynext e xnext
                    matrix[y - 2][x - 1] = matrix[y][x - 1];
                    matrix[y][x - 1] = -1;

                }
            }

            // if the crankshaft is in the vertical
            if (matrix[y][x - 2] == -1 && matrix[y - 1][x - 2] == -1) {

                Residue next = residues.get(matrix[y - 1][x]);
                int xnext = next.getPoint().getX();
                int ynext = next.getPoint().getY();

                if (matrix[y + 1][x - 1] == matrix[y][x] - 2
                    && matrix[ynext - 1][xnext - 1] == matrix[ynext][xnext] + 2) {
                    residue.getPoint().setX(x - 2);
                    residue.getPoint().setY(y);

                    next.getPoint().setX(x - 2);
                    next.getPoint().setY(ynext);

                    matrix[y][x - 2] = matrix[y][x];
                    matrix[y][x] = -1;

                    // TODO: rever se tem como usar ynext e xnext
                    matrix[y - 1][x - 2] = matrix[y - 1][x];
                    matrix[y - 1][x] = -1;

                }
            }

        }

        // if the residue started from n to 0
        if (isRightUpperCornerMatrix(matrix, x, y) && !isEmpty(matrix, x - 1, y + 1)) {

            // if the crankshaft is on horizontal
            if (matrix[y + 2][x] == -1 && matrix[y + 2][x - 1] == -1) {
                Residue next = residues.get(matrix[y][x - 1]);

                int xnext = next.getPoint().getX();
                int ynext = next.getPoint().getY();

                if (matrix[y + 1][x + 1] == matrix[y][x] - 2
                    && matrix[ynext + 1][xnext - 1] == matrix[ynext][xnext] + 2) {
                    residue.getPoint().setX(x);
                    residue.getPoint().setY(y + 2);

                    next.getPoint().setY(y + 2);

                    matrix[y + 2][x] = matrix[y][x];
                    matrix[y][x] = -1;

                    // TODO: rever se tem como usar ynext e xnext
                    matrix[y + 2][x - 1] = matrix[y][x - 1];
                    matrix[y][x - 1] = -1;

                }

            }

            // if the crankshaft is in the vertical
            if (matrix[y][x - 2] == -1 && matrix[y + 1][x - 2] == -1) {

                Residue next = residues.get(matrix[y + 1][x]);
                int xnext = next.getPoint().getX();
                int ynext = next.getPoint().getY();

                if (matrix[y - 1][x - 1] == matrix[y][x] - 2
                    && matrix[ynext + 1][xnext - 1] == matrix[ynext][xnext] + 2) {
                    residue.getPoint().setX(x - 2);
                    residue.getPoint().setY(y);

                    next.getPoint().setX(x - 2);
                    next.getPoint().setY(ynext);

                    matrix[y][x - 2] = matrix[y][x];
                    matrix[y][x] = -1;

                    // TODO: rever se tem como usar ynext e xnext
                    matrix[y + 1][x - 2] = matrix[y + 1][x];
                    matrix[y + 1][x] = -1;

                }
            }
        }
    }

    private static boolean isNeighbor(int[][] matrix, int x1, int y1, int x2, int y2) {

        return ((matrix[y1][x1] == matrix[y2][x2] - 1 || matrix[y1][x1] == matrix[y2][x2] + 1) && matrix[y2][x2] != -1)
            ? true : false;
    }

    public static boolean isEmpty(int[][] matrix, int x, int y) {

        if (matrix[y][x] == -1) {
            return true;
        }
        return false;
    }

    private static boolean isLeftBottomCornerMatrix(int[][] matrix, int x, int y) {

        // Check if the move would by pass the matrix boundaries
        // TODO: need to check if in this case y-1 need to check if is going to
        // the negative field
        if (y - 1 >= 0 && x + 1 < matrix.length) {
            // Check if is a left bottom corner in matrix
            int indexResidue = matrix[y][x];
            int rightNeighbor = matrix[y][x + 1];
            int upNeighbor = matrix[y - 1][x];
            if ((indexResidue + 1 == rightNeighbor || indexResidue - 1 == rightNeighbor)
                && (indexResidue + 1 == upNeighbor || indexResidue - 1 == upNeighbor)) {
                if (rightNeighbor != -1 && upNeighbor != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isLeftUpperCornerMatrix(int[][] matrix, int x, int y) {

        // Check if the move would by pass the matrix boundaries
        if (y + 1 < matrix.length && x + 1 < matrix.length) {
            // Check if is a left upper corner in matrix
            int indexResidue = matrix[y][x];
            int rightNeighbor = matrix[y][x + 1];
            int bottomNeighbor = matrix[y + 1][x];
            // Check if they are in sequence and if there is no residue in the
            // new
            // position
            if ((indexResidue + 1 == rightNeighbor || indexResidue - 1 == rightNeighbor)
                && (indexResidue + 1 == bottomNeighbor || indexResidue - 1 == bottomNeighbor)) {
                if (rightNeighbor != -1 && bottomNeighbor != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isRightBottomCornerMatrix(int[][] matrix, int x, int y) {

        // Check if the move would by pass the matrix boundaries
        // TODO: need to check if in this case y-1 and x-1 need to check if is
        // going to the negative field
        if (y - 1 >= 0 && x - 1 >= 0) {
            // Check if is a right bottom corner in matrix
            int indexResidue = matrix[y][x];
            int leftNeighbor = matrix[y][x - 1];
            int upNeighbor = matrix[y - 1][x];
            // Check if they are in sequence and if there is no residue in the
            // new
            // position
            if ((indexResidue + 1 == leftNeighbor || indexResidue - 1 == leftNeighbor)
                && (indexResidue + 1 == upNeighbor || indexResidue - 1 == upNeighbor)) {
                if (leftNeighbor != -1 && upNeighbor != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isRightUpperCornerMatrix(int[][] matrix, int x, int y) {

        // Check if the move would by pass the matrix boundaries
        // TODO: need to check if in this case y-1 and x-1 need to check if is
        // going to the negative field
        if (y + 1 < matrix.length && x - 1 >= 0) {
            // Check if is a right bottom corner in matrix
            int indexResidue = matrix[y][x];
            int leftNeighbor = matrix[y][x - 1];
            int bottomNeighor = matrix[y + 1][x];
            // Check if they are in sequence and if there is no residue in the
            // new
            // position
            if ((indexResidue + 1 == leftNeighbor || indexResidue - 1 == leftNeighbor)
                && (indexResidue + 1 == bottomNeighor || indexResidue - 1 == bottomNeighor)) {
                if (leftNeighbor != -1 && bottomNeighor != -1) {
                    return true;
                }
            }
        }
        return false;
    }

}
