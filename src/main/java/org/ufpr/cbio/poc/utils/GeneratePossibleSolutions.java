/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.ufpr.cbio.poc.domain.Residue.Point;

/**
 *
 *
 * @author user
 */
public class GeneratePossibleSolutions {

    public static void main(String[] args) {

        int[] generatePossibleSolution = GeneratePossibleSolutions.generatePossibleSolution(1, 2, 10);
        for (int i = 0; i < generatePossibleSolution.length; i++) {
            System.out.print(generatePossibleSolution[i] + ",");
        }

    }

    public static int[] generatePossibleSolution(int x, int y, int chainLength) {

        int[] solution = new int[chainLength];
        List<Point> points = new ArrayList<>();
        int movement = -1;
        points.add(new Point(x, y));
        for (int i = 0; i < chainLength; i++) {
            movement = new Random().nextInt(4);
            while (!isValidMovement(points, movement, points.get(points.size() - 1).x, points.get(points.size() - 1).y)) {
                movement = new Random().nextInt(4);
            }
            solution[i] = movement;
        }

        for (Point point : points) {
            System.out.println(point.getX() + "," + point.getY());
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
