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
