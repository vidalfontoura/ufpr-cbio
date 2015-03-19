/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.utils;

import java.util.ArrayList;
import java.util.List;

import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.domain.Residue.Point;
import org.ufpr.cbio.poc.domain.ResidueType;

/**
 *
 *
 * @author user
 */
public class TestRepair {

    public static void main(String[] args) {

        // int[] x = new int[] { 1, 0, -1, -2, -3, -4, -4, -5, -5, -5, -5 };
        // int[] y = new int[] { 2, 2, 2, 2, 2, 2, 3, 3, 2, 1, 0 };
        // for (int i = 0; i < x.length; i++) {
        // x[i] = x[i] - (-5);
        // System.out.print(x[i]);
        // }
        // System.out.println();
        //
        // for (int j = 0; j < y.length; j++) {
        // y[j] = y[j] - (0);
        // System.out.print(y[j]);
        // }

        List<Residue> residues = new ArrayList<Residue>();
        // residues.add(new Residue(new Point(1, 1), ResidueType.H));
        // residues.add(new Residue(new Point(1, 2), ResidueType.H));
        // residues.add(new Residue(new Point(2, 2), ResidueType.H));
        // residues.add(new Residue(new Point(3, 2), ResidueType.H));
        // residues.add(new Residue(new Point(4, 2), ResidueType.H));
        // residues.add(new Residue(new Point(5, 2), ResidueType.H));
        // residues.add(new Residue(new Point(5, 1), ResidueType.H));

        // upper left corner
        // residues.add(new Residue(new Point(2, 4), ResidueType.H));
        // residues.add(new Residue(new Point(1, 4), ResidueType.H));
        // residues.add(new Residue(new Point(1, 3), ResidueType.H));

        // bottom left corner
        // residues.add(new Residue(new Point(3, 4), ResidueType.H));
        // residues.add(new Residue(new Point(4, 3), ResidueType.H));
        // residues.add(new Residue(new Point(4, 4), ResidueType.H));

        // residues.add(new Residue(new Point(1, 1), ResidueType.H));
        // residues.add(new Residue(new Point(1, 2), ResidueType.H));
        // residues.add(new Residue(new Point(1, 3), ResidueType.H));
        // residues.add(new Residue(new Point(2, 3), ResidueType.H));
        // residues.add(new Residue(new Point(2, 4), ResidueType.H));
        // residues.add(new Residue(new Point(2, 5), ResidueType.H));
        // residues.add(new Residue(new Point(3, 5), ResidueType.H));
        // residues.add(new Residue(new Point(4, 5), ResidueType.H));
        // residues.add(new Residue(new Point(4, 4), ResidueType.H));
        // residues.add(new Residue(new Point(4, 3), ResidueType.H));
        // residues.add(new Residue(new Point(5, 3), ResidueType.H));
        // residues.add(new Residue(new Point(5, 2), ResidueType.H));
        // residues.add(new Residue(new Point(5, 1), ResidueType.H));
        // residues.add(new Residue(new Point(4, 1), ResidueType.H));
        // residues.add(new Residue(new Point(3, 1), ResidueType.H));
        // residues.add(new Residue(new Point(3, 2), ResidueType.H));
        // residues.add(new Residue(new Point(2, 2), ResidueType.H));

        // int[] x = new int[] { 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
        // 3 };

        // int[] x = new int[] { 1, 3, 3 };

        // First crankshaft
        // residues.add(new Residue(new Point(3, 5), ResidueType.H));
        // residues.add(new Residue(new Point(3, 4), ResidueType.H));
        // residues.add(new Residue(new Point(2, 4), ResidueType.H));
        // residues.add(new Residue(new Point(2, 3), ResidueType.H));
        // residues.add(new Residue(new Point(3, 3), ResidueType.H));
        // residues.add(new Residue(new Point(3, 2), ResidueType.H));

        // Second crankshaft
        // residues.add(new Residue(new Point(2, 1), ResidueType.H));
        // residues.add(new Residue(new Point(2, 2), ResidueType.H));
        // residues.add(new Residue(new Point(2, 3), ResidueType.H));
        // residues.add(new Residue(new Point(1, 3), ResidueType.H));
        // residues.add(new Residue(new Point(1, 4), ResidueType.H));
        // residues.add(new Residue(new Point(2, 4), ResidueType.H));
        // residues.add(new Residue(new Point(2, 5), ResidueType.H));
        // residues.add(new Residue(new Point(2, 6), ResidueType.H));
        //

        residues.add(new Residue(new Point(1, 4), ResidueType.H));
        residues.add(new Residue(new Point(1, 3), ResidueType.H));
        residues.add(new Residue(new Point(2, 3), ResidueType.H));
        residues.add(new Residue(new Point(2, 2), ResidueType.H));
        residues.add(new Residue(new Point(1, 2), ResidueType.H));
        residues.add(new Residue(new Point(1, 1), ResidueType.H));
        int[] x = new int[] { 4, 4, 4, 4, 4, 4 };
        List<Residue> applySolution = ResidueUtils.applySolution(residues, x);

        for (Residue residue : applySolution) {
            System.out.println(residue.getPoint().getX() + "," + residue.getPoint().getY());
        }

    }

}
