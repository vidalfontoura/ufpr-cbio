/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.visual;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;

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
 * @author vfontoura
 */
public class ApplyFixedSolution {

    public static EnumMovements[] FIXED_SOLUTION = new EnumMovements[] { EnumMovements.ROTATE_90_CLOCKWISE,
        EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER,
        EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER,
        EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER,
        EnumMovements.ROTATE_90_CLOCKWISE, };

    public static int index = 0;

    public static void addComponentsToPane(Container pane, List<Residue> residues) {

        pane.setLayout(null);

        Controller controller = new Controller();

        Grid generateGrid = controller.generateGrid(residues);
        MyCanvas myCanvas = new MyCanvas(generateGrid.getMatrix(), residues);

        JTextField textField = new JTextField("Solution");

        JButton b1 = new JButton("Apply Movements");

        b1.addActionListener(listener -> {
            Runnable task = () -> {
                for (int i = 0; i < residues.size(); i++) {
                    Residue residue = residues.get(i);
                    Movements.doMovement(residue, residues, generateGrid, FIXED_SOLUTION[i]);
                    pane.repaint();
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            new Thread(task).start();
        });

        pane.add(myCanvas);
        pane.add(b1);
        pane.add(textField);

        Insets insets = pane.getInsets();
        Dimension size = b1.getPreferredSize();
        b1.setBounds(25 + insets.left, 5 + insets.top, size.width, size.height);
        myCanvas.setBounds(30, 30, 500, 500);
        textField.setBounds(30, 30, 100, 20);
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {

        // Create and set up the window.
        JFrame frame = new JFrame("Protein Structure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<Residue> residues = new ArrayList<Residue>();

        residues.add(new Residue(new Point(1, 3), ResidueType.H));
        residues.add(new Residue(new Point(1, 4), ResidueType.H));
        residues.add(new Residue(new Point(2, 4), ResidueType.H));
        residues.add(new Residue(new Point(2, 5), ResidueType.H));
        residues.add(new Residue(new Point(3, 5), ResidueType.H));
        residues.add(new Residue(new Point(3, 4), ResidueType.H));
        residues.add(new Residue(new Point(4, 4), ResidueType.H));
        residues.add(new Residue(new Point(4, 3), ResidueType.H));
        residues.add(new Residue(new Point(4, 2), ResidueType.H));
        residues.add(new Residue(new Point(4, 1), ResidueType.H));
        residues.add(new Residue(new Point(3, 1), ResidueType.H));
        residues.add(new Residue(new Point(3, 0), ResidueType.H));
        residues.add(new Residue(new Point(2, 0), ResidueType.H));
        residues.add(new Residue(new Point(2, 1), ResidueType.H));
        residues.add(new Residue(new Point(1, 1), ResidueType.H));
        residues.add(new Residue(new Point(1, 2), ResidueType.H));

        // Set up the content pane.
        addComponentsToPane(frame.getContentPane(), residues);

        // Size and display the window.
        Insets insets = frame.getInsets();
        frame.setSize(500 + insets.left + insets.right, 500 + insets.top + insets.bottom);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                createAndShowGUI();
            }
        });
    }

    static class MyCanvas extends JComponent {

        private int[][] matrix;

        private List<Residue> residues;

        /**
         * @param matrix
         */
        public MyCanvas(int[][] matrix, List<Residue> residues) {

            super();
            this.matrix = matrix;
            this.residues = residues;
        }

        @Override
        public void paint(Graphics g) {

            g.drawRect(40, 40, 400, 400);
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    int rectX = 40 * (i + 1);
                    int rectY = 40 * (j + 1);
                    g.drawRect(rectX, rectY, 30, 30);
                }
            }
            Residue next = null;
            for (int i = 0; i < residues.size(); i++) {
                if (i != residues.size() - 1) {
                    next = residues.get(i + 1);
                }
                int residueX = 40 * (residues.get(i).getPoint().getX() + 1) + 5;
                int residueY = 40 * (residues.get(i).getPoint().getY() + 1) + 5;
                if (residues.get(i).getResidueType().equals(ResidueType.H)) {
                    g.setColor(Color.BLACK);
                    g.fillArc(residueX, residueY, 20, 20, 0, 360);
                } else {
                    g.drawArc(residueX, residueY, 20, 20, 0, 360);
                }

                if (next != null) {
                    int nextX = 40 * (next.getPoint().getX() + 1) + 5;
                    int nextY = 40 * (next.getPoint().getY() + 1) + 5;
                    g.drawLine(residueX + 10, residueY + 10, nextX + 10, nextY + 10);
                }

            }

        }
    }

}
