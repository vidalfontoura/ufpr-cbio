/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.domain.Residue.Point;
import org.ufpr.cbio.poc.domain.ResidueType;
import org.ufpr.cbio.poc.domain.TopologyContact;

/**
 *
 *
 * @author vfontoura
 */
public class ResidueUtils {

    private static int[] FIXED_SOLUTION = new int[] { 1, 0, 1, 2, 1, 2, 2, 3, 3, 0 };

    // private static int[] FIXED_SOLUTION = new int[] { 0, 0, 0, 1, 2, 2, 2, 1,
    // 1, 2 };

    // private static int[] FIXED_SOLUTION = new int[] { 2, 3, 0, 3, 0, 0, 0, 1,
    // 2, 2 };

    public static List<Residue> parseChainSequence(String chain) {

        int chainLength = chain.length();
        // int[] generateSolution = generatePossibleSolution(0, 0, chainLength);
        //
        // // Print generated solution
        // for (int i = 0; i < generateSolution.length; i++) {
        // System.out.print(generateSolution[i] + ",");
        // }
        // System.out.println();

        int x = 0, y = 0, minX = 0, minY = 0;
        List<Residue> residues = new ArrayList<>();
        Residue initialResidue = null;
        initialResidue =
            new Residue(createInitialPoint(chainLength), ResidueType.valueOf(String.valueOf(chain.charAt(0))));

        for (int i = 0; i < chainLength; i++) {
            if (i == 0) {
                residues.add(initialResidue);
                x = initialResidue.getPoint().getX();
                y = initialResidue.getPoint().getY();
                minX = x;
                minY = y;
            } else {
                int solution = FIXED_SOLUTION[i - 1];
                // int solution = generateSolution[i - 1];
                switch (solution) {
                // 0 - LEFT
                    case 0:
                        y++;
                        break;
                    // 1 - FORWARD
                    case 1:
                        x++;
                        break;
                    // 2 - RIGHT
                    case 2:
                        y--;
                        break;
                    case 3:
                        x--;
                        break;
                    default:
                        // TODO: exception
                        break;
                }
                if (x < minX) {
                    minX = x;
                }
                if (y < minY) {
                    minY = y;
                }
                residues.add(new Residue(new Point(x, y), ResidueType.valueOf(String.valueOf(chain.charAt(i)))));
            }

        }
        for (Residue residue : residues) {
            residue.setPoint(new Residue.Point(residue.getPoint().x - minX, residue.getPoint().y - minY));
        }
        return residues;
    }

    public static List<Residue> applySolution(List<Residue> residues, int[] solution) {

        // Definition of solution
        // 1 - Clockwise
        // 2 - Two Clockwise
        // 3 - Corner

        // This will only work with the residue list it is order of insertion
        List<Residue> newResidues = new ArrayList<Residue>(residues);
        Residue previous = null;
        Residue next = null;
        for (int i = 0; i < residues.size(); i++) {
            Residue residue = residues.get(i);

            int movement = solution[i];
            if (i > 0) {
                previous = residues.get(i - 1);
            }
            if (i + 1 < residues.size()) {
                next = residues.get(i + 1);
            }
            switch (movement) {
                case 1:
                    // TODO:
                    break;
                case 2:
                    // TODO:
                    break;
                case 3:
                    // check if it is a corner
                    if (i - 1 >= 0 && i + 1 > 1) {
                        if (previous != null && next != null) {
                            // Check if is a upper left corner
                            if (isUpperLeftCorner(residue, previous, next)) {
                                // Check if it is possible to make the move
                                int xnew = residue.getPoint().getX() + 1;
                                int ynew = residue.getPoint().getY() - 1;
                                if (!isPointSet(residues, xnew, ynew)) {
                                    // Make the move
                                    newResidues.get(i).setPoint(new Point(xnew, ynew));
                                    break;
                                }
                            }
                            // Check if is a bottom left corner
                            if (isBottomLeftCorner(residue, previous, next)) {
                                // Check if it is possible to make the move
                                int xnew = residue.getPoint().getX() + 1;
                                int ynew = residue.getPoint().getY() + 1;
                                if (!isPointSet(residues, xnew, ynew)) {
                                    // Make the move
                                    newResidues.get(i).setPoint(new Point(xnew, ynew));
                                    break;
                                }
                            }
                            // Check if is bottom right corner
                            if (isBottomRightCorner(residue, previous, next)) {
                                // Check if it is possible to make the move
                                int xnew = residue.getPoint().getX() - 1;
                                int ynew = residue.getPoint().getY() + 1;
                                if (!isPointSet(residues, xnew, ynew)) {
                                    // Make the move
                                    newResidues.get(i).setPoint(new Point(xnew, ynew));
                                    break;
                                }
                            }
                            // Check if is upper right corner
                            if (isUpperRightCorner(residue, previous, next)) {
                                // Check if it is possible to make the move
                                int xnew = residue.getPoint().getX() - 1;
                                int ynew = residue.getPoint().getY() - 1;
                                if (!isPointSet(residues, xnew, ynew)) {
                                    // Make the move
                                    newResidues.get(i).setPoint(new Point(xnew, ynew));
                                    break;
                                }
                            }
                        }
                        // If does not have next == null || previous it is
                        // impossible to be a corner
                    }
                    break;
                case 4: {
                    if (previous != null && next != null) {
                        if (isUpperLeftCorner(residue, previous, next)) {
                            int xnew = residue.getPoint().getX() + 1;
                            int ynew = residue.getPoint().getY() - 1;
                            if (isPointSet(residues, xnew, ynew)) {
                                int xnewCrankshaft = residue.getPoint().getX() + 2;
                                if (!isPointSet(residues, xnewCrankshaft, residue.getPoint().getY())
                                    && !isPointSet(residues, xnewCrankshaft, residue.getPoint().getY() - 1)) {
                                    // Make crankshaft move
                                    newResidues.get(i).setPoint(new Point(xnewCrankshaft, residue.getPoint().getY()));
                                    newResidues.get(i + 1).setPoint(
                                        new Point(xnewCrankshaft, residue.getPoint().getY() - 1));
                                }
                            }
                        }
                        if (isBottomLeftCorner(residue, previous, next)) {
                            int xnew = residue.getPoint().getX() + 1;
                            int ynew = residue.getPoint().getY() + 1;
                            if (isPointSet(residues, xnew, ynew)) {
                                int xnewCrankshaft = residue.getPoint().getX() + 2;
                                if (!isPointSet(residues, xnewCrankshaft, residue.getPoint().getY())
                                    && !isPointSet(residues, xnewCrankshaft, residue.getPoint().getY() + 1)) {
                                    // Make crankshaft move
                                    newResidues.get(i).setPoint(new Point(xnewCrankshaft, residue.getPoint().getY()));
                                    newResidues.get(i + 1).setPoint(
                                        new Point(xnewCrankshaft, residue.getPoint().getY() + 1));
                                }
                            }
                        }

                        if (isUpperRightCorner(residue, previous, next)) {
                            int xnew = residue.getPoint().getX() - 1;
                            int ynew = residue.getPoint().getY() - 1;
                            if (isPointSet(residues, xnew, ynew)) {
                                int xnewCrankshaft = residue.getPoint().getX() - 2;
                                if (!isPointSet(residues, xnewCrankshaft, residue.getPoint().getY())
                                    && !isPointSet(residues, xnewCrankshaft, residue.getPoint().getY() - 1)) {
                                    // Make crankshaft move
                                    newResidues.get(i).setPoint(new Point(xnewCrankshaft, residue.getPoint().getY()));
                                    newResidues.get(i + 1).setPoint(
                                        new Point(xnewCrankshaft, residue.getPoint().getY() - 1));
                                }
                            }
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
        return newResidues;
    }

    public static boolean isUpperLeftCorner(Residue residue, Residue previous, Residue next) {

        if (previous.getPoint().getX() == residue.getPoint().getX()
            && previous.getPoint().getY() + 1 == residue.getPoint().getY()
            && next.getPoint().getX() - 1 == residue.getPoint().getX()
            && next.getPoint().getY() == residue.getPoint().getY()
            || (next.getPoint().getX() == residue.getPoint().getX()
                && next.getPoint().getY() + 1 == residue.getPoint().getY()
                && previous.getPoint().getX() - 1 == residue.getPoint().getX() && previous.getPoint().getY() == residue
                .getPoint().getY())) {
            return true;
        }
        return false;
    }

    public static boolean isBottomLeftCorner(Residue residue, Residue previous, Residue next) {

        if (previous.getPoint().getX() - 1 == residue.getPoint().getX()
            && previous.getPoint().getY() == residue.getPoint().getY()
            && next.getPoint().getX() == residue.getPoint().getX()
            && next.getPoint().getY() - 1 == residue.getPoint().getY()
            || (next.getPoint().getX() - 1 == residue.getPoint().getX()
                && next.getPoint().getY() == residue.getPoint().getY()
                && previous.getPoint().getX() == residue.getPoint().getX() && previous.getPoint().getY() - 1 == residue
                .getPoint().getY())) {
            return true;
        }
        return false;
    }

    public static boolean isBottomRightCorner(Residue residue, Residue previous, Residue next) {

        if (previous.getPoint().getX() == residue.getPoint().getX()
            && previous.getPoint().getY() - 1 == residue.getPoint().getY()
            && next.getPoint().getX() + 1 == residue.getPoint().getX()
            && next.getPoint().getY() == residue.getPoint().getY()
            || (next.getPoint().getX() == residue.getPoint().getX()
                && next.getPoint().getY() - 1 == residue.getPoint().getY()
                && previous.getPoint().getX() + 1 == residue.getPoint().getX() && previous.getPoint().getY() == residue
                .getPoint().getY())) {
            return true;
        }
        return false;

    }

    public static boolean isUpperRightCorner(Residue residue, Residue previous, Residue next) {

        if (previous.getPoint().getX() + 1 == residue.getPoint().getX()
            && previous.getPoint().getY() == residue.getPoint().getY()
            && next.getPoint().getX() == residue.getPoint().getX()
            && next.getPoint().getY() + 1 == residue.getPoint().getY()
            || (next.getPoint().getX() + 1 == residue.getPoint().getX()
                && next.getPoint().getY() == residue.getPoint().getY()
                && previous.getPoint().getX() == residue.getPoint().getX() && previous.getPoint().getY() + 1 == residue
                .getPoint().getY())) {
            return true;
        }
        return false;

    }

    public static boolean isBottomLeftCornorCrankshaft(Residue residue, Residue previous, Residue next) {
        //TODO:FIX IT
       if( isBottomLeftCorner(residue, previous, next)) {
            int xnew = residue.getPoint().getX() + 1;
            int ynew = residue.getPoint().getY() + 1;
            if (isPointSet(residues, xnew, ynew)) {
                int xnewCrankshaft = residue.getPoint().getX() + 2;
                if (!isPointSet(residues, xnewCrankshaft, residue.getPoint().getY())
                    && !isPointSet(residues, xnewCrankshaft, residue.getPoint().getY() + 1)) {
                    // Make crankshaft move
                    newResidues.get(i).setPoint(new Point(xnewCrankshaft, residue.getPoint().getY()));
                    newResidues.get(i + 1).setPoint(
                        new Point(xnewCrankshaft, residue.getPoint().getY() + 1));
                }
            }
    }

    public static boolean isPointSet(List<Residue> residues, int x, int y) {

        for (Residue residue : residues) {
            if (residue.getPoint().getX() == x && residue.getPoint().getY() == y) {
                return true;
            }
        }
        return false;

    }

    public static Residue.Point createInitialPoint(int max) {

        Random random = new Random();
        return new Residue.Point(random.nextInt(max), random.nextInt(max));
    }

    public static Set<TopologyContact> getTopologyContacts(List<Residue> residues, Grid grid) {

        Set<TopologyContact> topologyContacts = new HashSet<>();
        int[][] matrix = grid.getMatrix();
        int index = 0;
        for (int i = 0; i < residues.size(); i++) {
            if (residues.get(i).getResidueType().equals(ResidueType.P)) {
                continue;
            }
            if (residues.get(i).getPoint().y + 1 < matrix.length) {
                index = matrix[residues.get(i).getPoint().y + 1][residues.get(i).getPoint().x];
                // test up
                if (isTopologicalContact(i, index, residues)) {
                    topologyContacts.add(new TopologyContact(residues.get(i), residues.get(index)));
                }
            }
            if (residues.get(i).getPoint().x + 1 < matrix.length) {
                // test right
                index = matrix[residues.get(i).getPoint().y][residues.get(i).getPoint().x + 1];
                if (isTopologicalContact(i, index, residues)) {
                    topologyContacts.add(new TopologyContact(residues.get(i), residues.get(index)));
                }
            }
            if (residues.get(i).getPoint().y - 1 >= 0) {
                // test down
                index = matrix[residues.get(i).getPoint().y - 1][residues.get(i).getPoint().x];
                if (isTopologicalContact(i, index, residues)) {
                    topologyContacts.add(new TopologyContact(residues.get(i), residues.get(index)));
                }
            }
            if (residues.get(i).getPoint().x - 1 >= 0) {
                // test back
                index = matrix[residues.get(i).getPoint().y][residues.get(i).getPoint().x - 1];
                if (isTopologicalContact(i, index, residues)) {
                    topologyContacts.add(new TopologyContact(residues.get(i), residues.get(index)));
                }
            }

        }
        return topologyContacts;
    }

    public static boolean isTopologicalContact(int i, int index, List<Residue> residues) {

        if (i != index + 1 && i != index - 1 && index != -1) {

            return residues.get(index).getResidueType().equals(ResidueType.H);
        }
        return false;
    }

}
