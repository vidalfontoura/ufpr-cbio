/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.main;

import java.util.ArrayList;
import java.util.List;

import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.domain.Residue.Point;
import org.ufpr.cbio.poc.domain.ResidueType;
import org.ufpr.cbio.poc.utils.Controller;
import org.ufpr.cbio.poc.utils.EnumMovements;
import org.ufpr.cbio.poc.utils.Movements;

/**
 *
 *
 * @author user
 */
public class MainTest {

    public static void main(String[] args) {

        Controller controller = new Controller();
        List<Residue> residues = new ArrayList<Residue>();
        // left bottom
        // residues.add(new Residue(new Point(2, 4), ResidueType.H));
        // residues.add(new Residue(new Point(1, 4), ResidueType.H));
        // residues.add(new Residue(new Point(1, 3), ResidueType.H));

        // left uppper in matrix
        // residues.add(new Residue(new Point(1, 2), ResidueType.H));
        // residues.add(new Residue(new Point(1, 1), ResidueType.H));
        // residues.add(new Residue(new Point(2, 1), ResidueType.H));

        // left bottom in matrix (working)
        // residues.add(new Residue(new Point(3, 4), ResidueType.H));
        // residues.add(new Residue(new Point(2, 4), ResidueType.H));
        // residues.add(new Residue(new Point(2, 3), ResidueType.H));

        // residues.add(new Residue(new Point(2, 3), ResidueType.H));
        // residues.add(new Residue(new Point(3, 3), ResidueType.H));
        // residues.add(new Residue(new Point(3, 2), ResidueType.H));

        // right bottom in matrix (working)
        // residues.add(new Residue(new Point(3, 2), ResidueType.H));
        // residues.add(new Residue(new Point(3, 3), ResidueType.H));
        // residues.add(new Residue(new Point(2, 3), ResidueType.H));

        // Right upper in matrix (working)
        // residues.add(new Residue(new Point(3, 3), ResidueType.H));
        // residues.add(new Residue(new Point(3, 2), ResidueType.H));
        // residues.add(new Residue(new Point(2, 2), ResidueType.H));

        residues.add(new Residue(new Point(1, 3), ResidueType.H));
        residues.add(new Residue(new Point(1, 4), ResidueType.H));
        residues.add(new Residue(new Point(2, 4), ResidueType.H));

        Grid g = controller.generateGrid(residues);

        Movements.doMovement(residues.get(1), residues, g, EnumMovements.CORNER);

        for (Residue residue : residues) {
            System.out.println(residue.getPoint().getX() + "," + residue.getPoint().getY());
        }

        g.printResidueStructure();

    }

    public static List<Residue> applyMovements(List<Residue> residues, EnumMovements[] movements) {

        Controller controller = new Controller();

        Grid generateGrid = controller.generateGrid(residues);
        for (int i = 0; i < residues.size(); i++) {
            Movements.doMovement(residues.get(i), residues, generateGrid, movements[i]);
        }
        return residues;
    }

}
