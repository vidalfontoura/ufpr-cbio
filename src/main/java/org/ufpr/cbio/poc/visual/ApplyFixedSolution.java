/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.domain.Residue.Point;
import org.ufpr.cbio.poc.domain.ResidueType;
import org.ufpr.cbio.poc.utils.Controller;
import org.ufpr.cbio.poc.utils.EnumMovements;
import org.ufpr.cbio.poc.utils.Movements;
import org.ufpr.cbio.poc.utils.ResidueUtils;

/**
 *
 *
 * @author vfontoura
 */
public class ApplyFixedSolution {

    private static final int SCREEN_SIZE = 600;
    private static final int MIN_SIZE_FACTOR = 20;
    private static final int MAX_SIZE_SCROLL_PANEL = 500;
    //
    // public static EnumMovements[] FIXED_SOLUTION = new EnumMovements[] {
    // EnumMovements.ROTATE_90_CLOCKWISE,
    // EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER,
    // EnumMovements.CORNER, EnumMovements.CORNER,
    // EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER,
    // EnumMovements.CORNER, EnumMovements.CORNER,
    // EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER,
    // EnumMovements.CORNER,
    // EnumMovements.ROTATE_90_CLOCKWISE, };
    //
    // public static EnumMovements[] FIXED_SOLUTION = new EnumMovements[] {
    // EnumMovements.ROTATE_90_CLOCKWISE,
    // EnumMovements.ROTATE_90_CLOCKWISE, EnumMovements.ROTATE_90_CLOCKWISE,
    // EnumMovements.CRANKSHAFT,
    // EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
    // EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT };

    // public static EnumMovements[] FIXED_SOLUTION = new EnumMovements[] {
    // EnumMovements.CRANKSHAFT,
    // EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
    // EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
    // EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
    // EnumMovements.CRANKSHAFT };
    //
    public static EnumMovements[] FIXED_SOLUTION = new EnumMovements[] { EnumMovements.ROTATE_180_CLOCKWISE,
        EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
        EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CORNER, EnumMovements.CORNER,
        EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CRANKSHAFT,
        EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER,
        EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER,
        EnumMovements.ROTATE_90_CLOCKWISE };

    private static int sizeFactor = 40;
    private static int maxGridSize = 600;
    private static int slots = 0;

    public static void addComponentsToPane(Container pane, List<Residue> residues, Grid grid) {

        pane.setLayout(null);

        MyCanvas myCanvas = new MyCanvas(grid.getMatrix(), residues);

        slots = (getBound(grid.getMatrix()) + 4);
        maxGridSize = calculateMaxGridSize(grid.getMatrix());
        sizeFactor = MAX_SIZE_SCROLL_PANEL / slots;
        if (sizeFactor < MIN_SIZE_FACTOR) {
            sizeFactor = MIN_SIZE_FACTOR;
        }
        myCanvas.setPreferredSize(new Dimension(maxGridSize, maxGridSize));

        JScrollPane jScrollPane = new JScrollPane(myCanvas);
        jScrollPane.setBounds(5, 30, MAX_SIZE_SCROLL_PANEL, MAX_SIZE_SCROLL_PANEL);
        jScrollPane.setWheelScrollingEnabled(true);
        jScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

        pane.add(jScrollPane, BorderLayout.CENTER);

        JButton b1 = new JButton("Apply Movements");
        Dimension size = b1.getPreferredSize();
        b1.setBounds(0, 0, size.width, size.height);
        b1.addActionListener(listener -> {
            Runnable task = () -> {
                for (int i = 0; i < residues.size(); i++) {
                    Residue residue = residues.get(i);
                    Movements.doMovement(residue, residues, grid, FIXED_SOLUTION[i]);
                    jScrollPane.repaint();
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            new Thread(task).start();
        });
        pane.add(b1);

    }

    private static int calculateMaxGridSize(int[][] matrix) {

        return matrix.length * MIN_SIZE_FACTOR;
    }

    private static int getBound(int[][] matrix) {

        int maxX = -1;
        int maxY = -1;
        int minX = matrix.length;
        int minY = matrix.length;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                int value = matrix[i][j];
                if (value != -1) {
                    if (i > maxX) {
                        maxX = i;
                    }
                    if (j > maxY) {
                        maxY = j;
                    }
                    if (i < minY) {
                        minY = i;
                    }
                    if (i < minX) {
                        minX = i;
                    }
                }
            }
        }
        int width = maxX;
        int height = maxY;
        return width > height ? width : height;

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

        // residues.add(new Residue(new Point(1, 3), ResidueType.H));
        // residues.add(new Residue(new Point(1, 4), ResidueType.H));
        // residues.add(new Residue(new Point(2, 4), ResidueType.H));
        // residues.add(new Residue(new Point(2, 5), ResidueType.H));
        // residues.add(new Residue(new Point(3, 5), ResidueType.H));
        // residues.add(new Residue(new Point(3, 4), ResidueType.H));
        // residues.add(new Residue(new Point(4, 4), ResidueType.H));
        // residues.add(new Residue(new Point(4, 3), ResidueType.H));
        // residues.add(new Residue(new Point(5, 3), ResidueType.H));
        // residues.add(new Residue(new Point(5, 2), ResidueType.H));
        // residues.add(new Residue(new Point(4, 2), ResidueType.H));
        // residues.add(new Residue(new Point(4, 1), ResidueType.P));
        // residues.add(new Residue(new Point(4, 0), ResidueType.H));
        // residues.add(new Residue(new Point(3, 0), ResidueType.H));

        // residues.add(new Residue(new Point(1, 3), ResidueType.H));
        // residues.add(new Residue(new Point(1, 4), ResidueType.H));
        // residues.add(new Residue(new Point(2, 4), ResidueType.H));
        // residues.add(new Residue(new Point(2, 5), ResidueType.H));
        // residues.add(new Residue(new Point(3, 5), ResidueType.H));
        // residues.add(new Residue(new Point(3, 4), ResidueType.H));
        // residues.add(new Residue(new Point(4, 4), ResidueType.H));
        // residues.add(new Residue(new Point(4, 3), ResidueType.H));
        // residues.add(new Residue(new Point(4, 2), ResidueType.H));
        // residues.add(new Residue(new Point(4, 1), ResidueType.H));
        // residues.add(new Residue(new Point(3, 1), ResidueType.H));
        // residues.add(new Residue(new Point(3, 0), ResidueType.H));
        // residues.add(new Residue(new Point(2, 0), ResidueType.H));
        // residues.add(new Residue(new Point(2, 1), ResidueType.H));
        // residues.add(new Residue(new Point(1, 1), ResidueType.H));
        // residues.add(new Residue(new Point(1, 2), ResidueType.H));
        //
        // residues.add(new Residue(new Point(4, 2), ResidueType.H));
        // residues.add(new Residue(new Point(4, 1), ResidueType.H));
        // residues.add(new Residue(new Point(3, 1), ResidueType.H));
        // residues.add(new Residue(new Point(3, 0), ResidueType.H));
        // residues.add(new Residue(new Point(2, 0), ResidueType.P));
        // residues.add(new Residue(new Point(1, 0), ResidueType.H));
        // residues.add(new Residue(new Point(1, 1), ResidueType.H));
        // residues.add(new Residue(new Point(2, 1), ResidueType.H));

        // residues.add(new Residue(new Point(4, 4), ResidueType.H));
        // residues.add(new Residue(new Point(4, 5), ResidueType.H));
        // residues.add(new Residue(new Point(3, 5), ResidueType.H));
        // residues.add(new Residue(new Point(3, 6), ResidueType.H));
        // residues.add(new Residue(new Point(2, 6), ResidueType.P));
        // residues.add(new Residue(new Point(1, 6), ResidueType.H));
        // residues.add(new Residue(new Point(1, 5), ResidueType.H));
        // residues.add(new Residue(new Point(2, 5), ResidueType.H));

        // residues.add(new Residue(new Point(1, 3), ResidueType.H));
        // residues.add(new Residue(new Point(1, 2), ResidueType.H));
        // residues.add(new Residue(new Point(2, 2), ResidueType.H));
        // residues.add(new Residue(new Point(2, 1), ResidueType.H));
        // residues.add(new Residue(new Point(3, 1), ResidueType.P));
        // residues.add(new Residue(new Point(4, 1), ResidueType.H));
        // residues.add(new Residue(new Point(4, 2), ResidueType.H));
        // residues.add(new Residue(new Point(3, 2), ResidueType.H));

        // NEED FIX
        // residues.add(new Residue(new Point(2, 1), ResidueType.H));
        // residues.add(new Residue(new Point(1, 1), ResidueType.H));
        // residues.add(new Residue(new Point(1, 2), ResidueType.H));
        // residues.add(new Residue(new Point(0, 2), ResidueType.H));
        // residues.add(new Residue(new Point(0, 3), ResidueType.H));
        // residues.add(new Residue(new Point(0, 4), ResidueType.P));
        // residues.add(new Residue(new Point(1, 4), ResidueType.H));
        // residues.add(new Residue(new Point(1, 3), ResidueType.H));

        // residues.add(new Residue(new Point(3, 4), ResidueType.H));
        // residues.add(new Residue(new Point(2, 4), ResidueType.H));
        // residues.add(new Residue(new Point(2, 3), ResidueType.H));
        // residues.add(new Residue(new Point(1, 3), ResidueType.H));
        // residues.add(new Residue(new Point(1, 2), ResidueType.H));
        // residues.add(new Residue(new Point(1, 1), ResidueType.P));
        // residues.add(new Residue(new Point(2, 1), ResidueType.H));
        // residues.add(new Residue(new Point(2, 2), ResidueType.H));

        // residues.add(new Residue(new Point(2, 2), ResidueType.H));
        // residues.add(new Residue(new Point(3, 2), ResidueType.H));
        // residues.add(new Residue(new Point(3, 3), ResidueType.H));
        // residues.add(new Residue(new Point(4, 3), ResidueType.H));
        // residues.add(new Residue(new Point(4, 4), ResidueType.H));
        // residues.add(new Residue(new Point(4, 5), ResidueType.P));
        // residues.add(new Residue(new Point(3, 5), ResidueType.H));
        // residues.add(new Residue(new Point(3, 4), ResidueType.H));

        // residues.add(new Residue(new Point(1, 4), ResidueType.H));
        // residues.add(new Residue(new Point(2, 4), ResidueType.H));
        // residues.add(new Residue(new Point(2, 3), ResidueType.H));
        // residues.add(new Residue(new Point(3, 3), ResidueType.H));
        // residues.add(new Residue(new Point(3, 2), ResidueType.H));
        // residues.add(new Residue(new Point(3, 1), ResidueType.P));
        // residues.add(new Residue(new Point(2, 1), ResidueType.H));
        // residues.add(new Residue(new Point(2, 2), ResidueType.H));

        // residues.add(new Residue(new Point(1, 2), ResidueType.H));
        // residues.add(new Residue(new Point(2, 2), ResidueType.H));
        // residues.add(new Residue(new Point(2, 1), ResidueType.H));
        // residues.add(new Residue(new Point(3, 1), ResidueType.H));
        // residues.add(new Residue(new Point(3, 2), ResidueType.H));
        // residues.add(new Residue(new Point(4, 2), ResidueType.H));

        // residues.add(new Residue(new Point(2, 1), ResidueType.H));
        // residues.add(new Residue(new Point(2, 2), ResidueType.H));
        // residues.add(new Residue(new Point(1, 2), ResidueType.H));
        // residues.add(new Residue(new Point(1, 3), ResidueType.H));
        // residues.add(new Residue(new Point(2, 3), ResidueType.H));
        // residues.add(new Residue(new Point(2, 4), ResidueType.H));

        // residues.add(new Residue(new Point(1, 4), ResidueType.H));
        // residues.add(new Residue(new Point(1, 5), ResidueType.H));
        // residues.add(new Residue(new Point(2, 5), ResidueType.H));
        // residues.add(new Residue(new Point(2, 6), ResidueType.P));
        // residues.add(new Residue(new Point(3, 6), ResidueType.H));
        // residues.add(new Residue(new Point(4, 6), ResidueType.H));
        // residues.add(new Residue(new Point(4, 5), ResidueType.H));
        // residues.add(new Residue(new Point(3, 5), ResidueType.H));

        // Exemplo estourando matrix estourando no rotate clockwise
        //
        // residues.add(new Residue(new Point(3, 9), ResidueType.H));
        // residues.add(new Residue(new Point(3, 10), ResidueType.H));
        // residues.add(new Residue(new Point(4, 10), ResidueType.H));
        // residues.add(new Residue(new Point(4, 9), ResidueType.H));
        // residues.add(new Residue(new Point(5, 9), ResidueType.P));
        // residues.add(new Residue(new Point(5, 10), ResidueType.H));
        // residues.add(new Residue(new Point(6, 10), ResidueType.H));
        // residues.add(new Residue(new Point(7, 10), ResidueType.H));
        // residues.add(new Residue(new Point(7, 11), ResidueType.H));
        // residues.add(new Residue(new Point(7, 12), ResidueType.H));
        // residues.add(new Residue(new Point(6, 12), ResidueType.H));
        // residues.add(new Residue(new Point(5, 12), ResidueType.H));
        // residues.add(new Residue(new Point(5, 11), ResidueType.H));
        // residues.add(new Residue(new Point(4, 11), ResidueType.H));
        // residues.add(new Residue(new Point(4, 12), ResidueType.H));
        // residues.add(new Residue(new Point(3, 12), ResidueType.H));
        // residues.add(new Residue(new Point(3, 13), ResidueType.H));
        // residues.add(new Residue(new Point(3, 14), ResidueType.H));
        // residues.add(new Residue(new Point(4, 14), ResidueType.H));
        // residues.add(new Residue(new Point(4, 15), ResidueType.H));
        // residues.add(new Residue(new Point(4, 16), ResidueType.H));
        // residues.add(new Residue(new Point(4, 17), ResidueType.H));
        // residues.add(new Residue(new Point(5, 17), ResidueType.H));

        // residues.add(new Residue(new Point(3, 2), ResidueType.H));
        // residues.add(new Residue(new Point(4, 2), ResidueType.H));
        // residues.add(new Residue(new Point(4, 1), ResidueType.H));
        // residues.add(new Residue(new Point(5, 1), ResidueType.P));
        // residues.add(new Residue(new Point(5, 2), ResidueType.H));
        // residues.add(new Residue(new Point(6, 2), ResidueType.H));

        // residues.add(new Residue(new Point(0, 2), ResidueType.H));
        // residues.add(new Residue(new Point(0, 3), ResidueType.H));
        // residues.add(new Residue(new Point(0, 4), ResidueType.H));
        // residues.add(new Residue(new Point(0, 5), ResidueType.H));
        // residues.add(new Residue(new Point(0, 6), ResidueType.H));
        // residues.add(new Residue(new Point(0, 7), ResidueType.H));
        // residues.add(new Residue(new Point(0, 8), ResidueType.H));
        // residues.add(new Residue(new Point(0, 9), ResidueType.H));
        // residues.add(new Residue(new Point(0, 10), ResidueType.H));
        // residues.add(new Residue(new Point(0, 11), ResidueType.H));
        // residues.add(new Residue(new Point(0, 12), ResidueType.H));
        // residues.add(new Residue(new Point(0, 13), ResidueType.H));
        // residues.add(new Residue(new Point(0, 14), ResidueType.H));
        // residues.add(new Residue(new Point(0, 15), ResidueType.H));
        // residues.add(new Residue(new Point(0, 16), ResidueType.H));
        // residues.add(new Residue(new Point(0, 17), ResidueType.H));
        // residues.add(new Residue(new Point(0, 18), ResidueType.H));
        // residues.add(new Residue(new Point(0, 19), ResidueType.H));
        // residues.add(new Residue(new Point(0, 20), ResidueType.H));
        // residues.add(new Residue(new Point(0, 21), ResidueType.H));
        // residues.add(new Residue(new Point(0, 22), ResidueType.H));
        // residues.add(new Residue(new Point(0, 23), ResidueType.H));
        // residues.add(new Residue(new Point(0, 24), ResidueType.H));
        // residues.add(new Residue(new Point(0, 25), ResidueType.H));
        // residues.add(new Residue(new Point(0, 26), ResidueType.H));
        // residues.add(new Residue(new Point(0, 27), ResidueType.H));
        // residues.add(new Residue(new Point(0, 28), ResidueType.H));
        // residues.add(new Residue(new Point(0, 29), ResidueType.H));
        // residues.add(new Residue(new Point(0, 30), ResidueType.H));
        // residues.add(new Residue(new Point(0, 31), ResidueType.H));
        // residues.add(new Residue(new Point(0, 32), ResidueType.H));
        // residues.add(new Residue(new Point(0, 33), ResidueType.H));
        // residues.add(new Residue(new Point(0, 34), ResidueType.H));
        // residues.add(new Residue(new Point(0, 35), ResidueType.H));
        // residues.add(new Residue(new Point(0, 36), ResidueType.H));
        // residues.add(new Residue(new Point(0, 37), ResidueType.H));
        // residues.add(new Residue(new Point(0, 38), ResidueType.H));
        // residues.add(new Residue(new Point(0, 39), ResidueType.H));
        // residues.add(new Residue(new Point(0, 40), ResidueType.H));
        // residues.add(new Residue(new Point(0, 41), ResidueType.H));
        // residues.add(new Residue(new Point(0, 42), ResidueType.H));
        // residues.add(new Residue(new Point(0, 43), ResidueType.H));
        // residues.add(new Residue(new Point(0, 44), ResidueType.H));
        // residues.add(new Residue(new Point(0, 45), ResidueType.H));
        // residues.add(new Residue(new Point(0, 46), ResidueType.H));
        // residues.add(new Residue(new Point(0, 47), ResidueType.H));
        // residues.add(new Residue(new Point(0, 48), ResidueType.H));
        // residues.add(new Residue(new Point(0, 49), ResidueType.H));
        // residues.add(new Residue(new Point(0, 50), ResidueType.H));
        // residues.add(new Residue(new Point(0, 51), ResidueType.H));
        // residues.add(new Residue(new Point(0, 52), ResidueType.H));
        // residues.add(new Residue(new Point(0, 53), ResidueType.H));
        // residues.add(new Residue(new Point(0, 54), ResidueType.H));
        // residues.add(new Residue(new Point(0, 55), ResidueType.H));
        // residues.add(new Residue(new Point(0, 56), ResidueType.H));
        // residues.add(new Residue(new Point(0, 57), ResidueType.H));
        // residues.add(new Residue(new Point(0, 58), ResidueType.H));
        // residues.add(new Residue(new Point(0, 59), ResidueType.H));
        // residues.add(new Residue(new Point(0, 60), ResidueType.H));
        // residues.add(new Residue(new Point(0, 61), ResidueType.H));
        // residues.add(new Residue(new Point(0, 62), ResidueType.H));
        // residues.add(new Residue(new Point(0, 63), ResidueType.H));
        // residues.add(new Residue(new Point(0, 64), ResidueType.H));
        // residues.add(new Residue(new Point(0, 65), ResidueType.H));
        // residues.add(new Residue(new Point(0, 66), ResidueType.H));
        // residues.add(new Residue(new Point(0, 67), ResidueType.H));
        // residues.add(new Residue(new Point(0, 68), ResidueType.H));
        // residues.add(new Residue(new Point(0, 69), ResidueType.H));
        // residues.add(new Residue(new Point(0, 70), ResidueType.H));
        // residues.add(new Residue(new Point(0, 71), ResidueType.H));
        // residues.add(new Residue(new Point(0, 72), ResidueType.H));
        // residues.add(new Residue(new Point(0, 73), ResidueType.H));
        // residues.add(new Residue(new Point(0, 74), ResidueType.H));
        // residues.add(new Residue(new Point(0, 75), ResidueType.H));
        // residues.add(new Residue(new Point(0, 76), ResidueType.H));
        // residues.add(new Residue(new Point(0, 77), ResidueType.H));
        // residues.add(new Residue(new Point(0, 78), ResidueType.H));
        // residues.add(new Residue(new Point(0, 79), ResidueType.H));
        // residues.add(new Residue(new Point(0, 80), ResidueType.H));
        // residues.add(new Residue(new Point(0, 81), ResidueType.H));
        // residues.add(new Residue(new Point(0, 82), ResidueType.H));
        // residues.add(new Residue(new Point(0, 83), ResidueType.H));
        // residues.add(new Residue(new Point(0, 84), ResidueType.H));
        // residues.add(new Residue(new Point(0, 85), ResidueType.H));
        // residues.add(new Residue(new Point(0, 86), ResidueType.H));
        // residues.add(new Residue(new Point(0, 87), ResidueType.H));
        // residues.add(new Residue(new Point(0, 88), ResidueType.H));
        // residues.add(new Residue(new Point(0, 89), ResidueType.H));
        // residues.add(new Residue(new Point(0, 90), ResidueType.H));
        // residues.add(new Residue(new Point(0, 92), ResidueType.H));
        // residues.add(new Residue(new Point(0, 93), ResidueType.H));
        // residues.add(new Residue(new Point(0, 94), ResidueType.H));
        // residues.add(new Residue(new Point(0, 95), ResidueType.H));
        // residues.add(new Residue(new Point(0, 96), ResidueType.H));
        // residues.add(new Residue(new Point(0, 97), ResidueType.H));
        // residues.add(new Residue(new Point(0, 98), ResidueType.H));
        // residues.add(new Residue(new Point(0, 99), ResidueType.H));

        residues =
            ResidueUtils
                .createDefaultReference100("HHPHHHPPPHHHHPHPHPHHHPPPPPPHHHHHHHHPPPPPPHHHHHPPPHHHHHHPPPHHHPPPHHHHHHPPPHHHHHPPPPHHHHPPPHHHHHPPPHHP");

        Controller controller = new Controller();
        residues = ResidueUtils.translateToOrigin(residues);
        Grid generateGrid = controller.generateGrid(residues);

        // Set up the content pane.
        addComponentsToPane(frame.getContentPane(), residues, generateGrid);

        // Size and display the window.
        frame.setSize(SCREEN_SIZE, SCREEN_SIZE);
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

    static class MyCanvas extends JPanel implements MouseListener {

        private int[][] matrix;

        private List<Residue> residues;

        /**
         * @param matrix
         */
        public MyCanvas(int[][] matrix, List<Residue> residues) {

            super();
            this.matrix = matrix;
            this.residues = residues;
            addMouseListener(this);
        }

        @Override
        public void paint(Graphics g) {

            for (int i = 0; i < slots; i++) {
                for (int j = 0; j < slots; j++) {
                    int rectX = sizeFactor * (i);
                    int rectY = sizeFactor * (j);
                    g.drawRect(rectX, rectY, sizeFactor, sizeFactor);

                }
            }
            Residue next = null;
            for (int i = 0; i < residues.size(); i++) {
                if (i != residues.size() - 1) {
                    next = residues.get(i + 1);
                }
                int residueX = sizeFactor * (residues.get(i).getPoint().getX()) + sizeFactor / 4;
                int residueY = sizeFactor * (residues.get(i).getPoint().getY()) + sizeFactor / 4;
                if (residues.get(i).getResidueType().equals(ResidueType.H)) {
                    g.setColor(Color.BLACK);
                    g.fillArc(residueX, residueY, sizeFactor / 2, sizeFactor / 2, 0, 360);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillArc(residueX, residueY, sizeFactor / 2, sizeFactor / 2, 0, 360);
                }
                g.setColor(Color.BLACK);

                if (next != null) {
                    int nextX = sizeFactor * (next.getPoint().getX()) + sizeFactor / 4;
                    int nextY = sizeFactor * (next.getPoint().getY()) + sizeFactor / 4;
                    g.drawLine(residueX + sizeFactor / 4, residueY + sizeFactor / 4, nextX + sizeFactor / 4, nextY
                        + sizeFactor / 4);
                }

            }

        }

        private int getMinX(List<Residue> residues) {

            int min = residues.get(0).getPoint().getX();
            for (int i = 0; i < residues.size(); i++) {
                int residueX = residues.get(i).getPoint().getX();
                if (min > residueX) {
                    min = residueX;
                }
            }
            return min;
        }

        private int getMinY(List<Residue> residues) {

            int min = residues.get(0).getPoint().getY();
            for (int i = 0; i < residues.size(); i++) {
                int residueY = residues.get(i).getPoint().getY();
                if (min > residueY) {
                    min = residueY;
                }
            }
            return min;

        }

        /*
         * (non-Javadoc)
         * @see
         * java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
         */
        public void mouseClicked(MouseEvent e) {

            java.awt.Point pointClicked = e.getPoint();
            int x = ((pointClicked.x - 5) / sizeFactor);
            int y = ((pointClicked.y - 5) / sizeFactor);
            Point point = new Point(x, y);
            System.out.println("new Point(" + x + "," + y + ");");

            int residueIndex = getResidueIndex(point);
            // System.out.println(residueIndex);
            if (residueIndex != -1) {

            }

        }

        public int getResidueIndex(Point point) {

            for (int i = 0; i < residues.size(); i++) {
                if (residues.get(i).getPoint().equals(point)) {
                    return i;
                }
            }
            return -1;
        }

        /*
         * (non-Javadoc)
         * @see
         * java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
         */
        public void mousePressed(MouseEvent e) {

            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * @see
         * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
         */
        public void mouseReleased(MouseEvent e) {

            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * @see
         * java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
         */
        public void mouseEntered(MouseEvent e) {

            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * @see
         * java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
         */
        public void mouseExited(MouseEvent e) {

            // TODO Auto-generated method stub

        }
    }

}
