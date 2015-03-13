/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.domain.Residue.Point;
import org.ufpr.cbio.poc.domain.ResidueType;

/**
 *
 *
 * @author vfontoura
 */
public class ResidueUtils {

    // private static int[] FIXED_SOLUTION = new int[] { 1, 0, 1, 2, 1, 2, 2, 3,
    // 3, 0 };

    private static int[] FIXED_SOLUTION = new int[] { 0, 0, 0, 1, 2, 2, 2, 1, 1, 2 };

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

    public static int[] generatePossibleSolution(int x, int y, int chainLength) {

        Set<Integer> possibleMoves = new HashSet<>();
        possibleMoves.add(0);
        possibleMoves.add(1);
        possibleMoves.add(2);
        possibleMoves.add(3);
        int[] solution = new int[chainLength];
        List<Point> points = new ArrayList<>();
        int movement = -1;
        points.add(new Point(x, y));
        int xr = x;
        int yr = y;
        for (int i = 0; i < chainLength; i++) {
            movement = possibleMoves.iterator().next();
            while (!isValidMovement(points, movement, xr, yr)) {
                possibleMoves.remove(movement);
                movement = possibleMoves.iterator().next();
            }
            solution[i] = movement;
            possibleMoves.add(0);
            possibleMoves.add(1);
            possibleMoves.add(2);
            possibleMoves.add(3);
        }
        return solution;
    }

    public static boolean isValidMovement(List<Point> points, int movement, int xr, int yr) {

        Point point = null;
        switch (movement) {
            case 0:
                yr++;
                point = new Point(xr, yr);
                if (points.contains(point)) {
                    // System.out.println("bad movement:" + 0);
                    return false;
                }
                points.add(point);
                return true;
            case 1:
                xr++;
                point = new Point(xr, yr);
                if (points.contains(point)) {
                    // System.out.println("bad movement:" + 1);
                    return false;
                }
                points.add(point);
                return true;
            case 2:
                yr--;
                point = new Point(xr, yr);
                if (points.contains(point)) {
                    // System.out.println("bad movement:" + 2);
                    return false;
                }
                points.add(point);
                return true;
            case 3:
                xr--;
                point = new Point(xr, yr);
                if (points.contains(point)) {
                    // System.out.println("bad movement:" + 3);
                    return false;
                }
                points.add(point);
                return true;
        }
        return false;
    }
}
