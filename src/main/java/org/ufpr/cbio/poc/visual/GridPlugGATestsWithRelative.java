package org.ufpr.cbio.poc.visual;

import com.google.common.collect.Lists;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;

import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.domain.Residue.Point;
import org.ufpr.cbio.poc.domain.ResidueType;
import org.ufpr.cbio.poc.domain.TopologyContact;
import org.ufpr.cbio.poc.ga.GeneticAlgorithm;
import org.ufpr.cbio.poc.ga.Individue;
import org.ufpr.cbio.poc.utils.Controller;
import org.ufpr.cbio.poc.utils.EnumMovements;
import org.ufpr.cbio.poc.utils.Movements;
import org.ufpr.cbio.poc.utils.ResidueUtils;

/**
 *
 *
 * @author vfontoura
 */
public class GridPlugGATestsWithRelative {

    private static final int SCREEN_SIZE = 700;
    private static final int MIN_SIZE_FACTOR = 20;
    private static final int MAX_SIZE_SCROLL_PANEL = 500;

    private static int sizeFactor = 40;
    private static int maxGridSize = 700;
    private static int slots = 0;

    private static List<Residue> residues;

    private static List<Residue> residuesInitial;

    // private static final String PROTEIN_CHAIN = "HPHPPHHPHPPHPHHPPHPH";
    // private static final String PROTEIN_CHAIN =
    // "HHHHPPPPHHHHHHHHHHHHPPPPPPHHHHHHHHHHHHPPPHHHHHHHHHHHHPPPHHHHHHHHHHHHPPPHPPHHPPHHPPHPH";
    private static final String PROTEIN_CHAIN =
        "PPPPPPHPHHPPPPPHHHPHHHHHPHHPPPPHHPPHHPHHHHHPHHHHHHHHHHPHHPHHHHHHHPPPPPPPPPPPHHHHHHHPPHPHHHPPPPPPHPHH";
    private static final int INDIVIDUE_LENGHT = PROTEIN_CHAIN.length();
    private static final int POPULATION_SIZE = 1000;
    private static final int GENERATIONS = 1000;
    private static final int MUTATION = 1;
    private static final int CROSSOVER = 3;
    private static final int SELECTION = 1;

    private static final double CROSSOVER_RATE = 0.9;
    private static final double MUTATION_RATE = 0.2;
    private static final int ELITISM_PERCENTAGE = 10;
    private static final int SEED = 2;

    private static final boolean DYNAMIC_CHANGE_REFERENCE = true;
    private static final int MAX_REFERENCE_CHANGE = 60;
    private static final int MAX_REPEATED_FITNEES = 30;

    public static void addComponentsToPane(Container pane, List<Residue> residuesList, Grid grid) {

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        MyCanvas myCanvas = new MyCanvas();

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
        JButton resetButton = new JButton("Reset");

        JButton runGAButton = new JButton("Run GA");

        Set<TopologyContact> topologyContacts = ResidueUtils.getTopologyContacts(residuesList, grid);
        JLabel energyLabel = new JLabel("Energy value: " + topologyContacts.size());

        JLabel crossOverLabel = new JLabel("Crossover: ");
        Integer[] crossOptions = { 1, 2, 3 };
        JComboBox<Integer> crossoverComboBox = new JComboBox<Integer>(crossOptions);
        crossoverComboBox.setSelectedIndex(0);

        JLabel crossOverRateLabel = new JLabel("Crossover Rate: ");
        JTextField crossOverRateField = new JTextField();

        JLabel mutationLabel = new JLabel("Mutation: ");
        Integer[] mutation = { 1, 2, 3 };
        JComboBox<Integer> mutationComboBox = new JComboBox<Integer>(mutation);
        mutationComboBox.setSelectedIndex(0);

        JLabel selectionLabel = new JLabel("Selection: ");
        Integer[] selection = { 1 };
        JComboBox<Integer> selectionComboBox = new JComboBox<Integer>(selection);
        mutationComboBox.setSelectedIndex(0);

        JLabel populationLabel = new JLabel("Population Size: ");
        JTextField populationTextField = new JTextField();

        JLabel generationsLabel = new JLabel("Generations: ");
        JTextField genTextField = new JTextField();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        // pane.add(crossOverLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        // pane.add(crossoverComboBox, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        // pane.add(crossOverRateLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 0;
        // pane.add(crossOverRateField, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 4;
        c.gridy = 0;
        // pane.add(mutationLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 5;
        c.gridy = 0;
        // pane.add(mutationComboBox, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 6;
        c.gridy = 0;
        // pane.add(selectionLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 7;
        c.gridy = 0;
        // pane.add(selectionComboBox, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        // pane.add(populationLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        // pane.add(populationTextField, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 1;
        // pane.add(generationsLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 1;
        // pane.add(genTextField, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 4;
        c.gridy = 1;
        pane.add(runGAButton, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 5;
        c.gridy = 1;
        pane.add(resetButton, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 6;
        c.gridy = 1;
        pane.add(energyLabel, c);

        /* Genetic configs */

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 10;
        c.gridx = 0;
        c.gridy = 2;

        pane.add(jScrollPane, c);

        runGAButton.addActionListener(listener -> {
            Runnable task =
                () -> {
                    // GeneticAlgorithm geneticAlgorithm =
                    // new GeneticAlgorithm(INDIVIDUE_LENGHT, POPULATION_SIZE,
                    // GENERATIONS, MUTATION, CROSSOVER,
                    // SELECTION, PROTEIN_CHAIN, CROSSOVER_RATE, MUTATION_RATE,
                    // ELITISM_PERCENTAGE, SEED,
                    // residues);

                    GeneticAlgorithm geneticAlgorithm =
                        new GeneticAlgorithm(INDIVIDUE_LENGHT, POPULATION_SIZE, GENERATIONS, MUTATION, CROSSOVER,
                            SELECTION, PROTEIN_CHAIN, CROSSOVER_RATE, MUTATION_RATE, ELITISM_PERCENTAGE, SEED,
                            residues, DYNAMIC_CHANGE_REFERENCE, MAX_REPEATED_FITNEES, MAX_REFERENCE_CHANGE);

                    Map<Integer, List<Individue>> result = geneticAlgorithm.execute();
                    Integer maxFitnessSolutions = Collections.max(result.keySet());
                    System.out.println("max fitness: " + maxFitnessSolutions);
                    List<Individue> list = result.get(maxFitnessSolutions);
                    System.out.println(list.size());
                    Individue solution = list.get(0);
                    System.out.println("max fitness calculate: "
                        + geneticAlgorithm.calculateIndividueFitness(solution.getMoves()));
                    System.out.println(Arrays.toString(solution.getMoves()));
                    EnumMovements[] movementsArray = ResidueUtils.toMovementsArray(solution.getMoves());
                    Grid generateGrid = new Controller().generateGrid(residuesList);

                    for (int i = 0; i < residuesList.size(); i++) {
                        Residue residue = residuesList.get(i);
                        Movements.doMovement(residue, residuesList, generateGrid, movementsArray[i]);
                        jScrollPane.repaint();
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    energyLabel.setText("Energy value: "
                        + ResidueUtils.getTopologyContacts(residuesList, generateGrid).size());
                };
            new Thread(task).start();
        });
        resetButton.addActionListener(listener -> {

            Runnable task =
                () -> {
                    for (int i = 0; i < residuesInitial.size(); i++) {
                        residues.set(i, (Residue) residuesInitial.get(i).clone());
                    }
                    energyLabel.setText("Energy value: "
                        + ResidueUtils.getTopologyContacts(residuesList, new Controller().generateGrid(residues))
                            .size());
                    jScrollPane.repaint();
                };
            new Thread(task).start();
        });

    }

    private static int[] fromStringToInt(String[] moves) {

        int[] movements = new int[moves.length];
        for (int i = 0; i < moves.length; i++) {
            movements[i] = Integer.valueOf(moves[i]);
        }
        return movements;
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

        residues = new ArrayList<Residue>();
        residues.add(new Residue(new Point(8, 3), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(0)))));
        residues.add(new Residue(new Point(9, 3), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(1)))));
        residues.add(new Residue(new Point(9, 2), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(2)))));
        residues.add(new Residue(new Point(8, 2), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(3)))));
        residues.add(new Residue(new Point(7, 2), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(4)))));
        residues.add(new Residue(new Point(7, 3), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(5)))));
        residues.add(new Residue(new Point(7, 4), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(6)))));
        residues.add(new Residue(new Point(6, 4), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(7)))));
        residues.add(new Residue(new Point(6, 5), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(8)))));
        residues.add(new Residue(new Point(7, 5), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(9)))));
        residues.add(new Residue(new Point(7, 6), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(10)))));
        residues.add(new Residue(new Point(8, 6), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(11)))));
        residues.add(new Residue(new Point(9, 6), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(12)))));
        residues.add(new Residue(new Point(10, 6), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(13)))));
        residues.add(new Residue(new Point(11, 6), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(14)))));
        residues.add(new Residue(new Point(12, 6), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(15)))));
        residues.add(new Residue(new Point(12, 5), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(16)))));
        residues.add(new Residue(new Point(13, 5), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(17)))));
        residues.add(new Residue(new Point(13, 6), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(18)))));
        residues.add(new Residue(new Point(13, 7), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(19)))));
        residues.add(new Residue(new Point(12, 7), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(20)))));
        residues.add(new Residue(new Point(11, 7), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(21)))));
        residues.add(new Residue(new Point(11, 8), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(22)))));
        residues.add(new Residue(new Point(11, 9), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(23)))));
        residues.add(new Residue(new Point(12, 9), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(24)))));
        residues.add(new Residue(new Point(12, 8), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(25)))));
        residues.add(new Residue(new Point(13, 8), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(26)))));
        residues.add(new Residue(new Point(14, 8), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(27)))));
        residues.add(new Residue(new Point(14, 9), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(28)))));
        residues.add(new Residue(new Point(13, 9), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(29)))));
        residues.add(new Residue(new Point(13, 10), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(30)))));
        residues.add(new Residue(new Point(13, 11), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(31)))));
        residues.add(new Residue(new Point(14, 11), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(32)))));
        residues.add(new Residue(new Point(14, 12), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(33)))));
        residues.add(new Residue(new Point(13, 12), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(34)))));
        residues.add(new Residue(new Point(12, 12), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(35)))));
        residues.add(new Residue(new Point(12, 11), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(36)))));
        residues.add(new Residue(new Point(12, 10), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(37)))));
        residues.add(new Residue(new Point(11, 10), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(38)))));
        residues.add(new Residue(new Point(10, 10), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(39)))));
        residues.add(new Residue(new Point(10, 11), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(40)))));
        residues.add(new Residue(new Point(10, 12), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(41)))));
        residues.add(new Residue(new Point(9, 12), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(42)))));
        residues.add(new Residue(new Point(9, 13), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(43)))));
        residues.add(new Residue(new Point(10, 13), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(44)))));
        residues.add(new Residue(new Point(10, 14), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(45)))));
        residues.add(new Residue(new Point(11, 14), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(46)))));
        residues.add(new Residue(new Point(11, 13), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(47)))));
        residues.add(new Residue(new Point(12, 13), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(48)))));
        residues.add(new Residue(new Point(12, 14), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(49)))));
        residues.add(new Residue(new Point(13, 14), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(50)))));
        residues.add(new Residue(new Point(13, 15), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(51)))));
        residues.add(new Residue(new Point(12, 15), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(52)))));
        residues.add(new Residue(new Point(12, 16), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(53)))));
        residues.add(new Residue(new Point(13, 16), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(54)))));
        residues.add(new Residue(new Point(13, 17), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(55)))));
        residues.add(new Residue(new Point(12, 17), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(56)))));
        residues.add(new Residue(new Point(11, 17), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(57)))));
        residues.add(new Residue(new Point(11, 16), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(58)))));
        residues.add(new Residue(new Point(11, 15), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(59)))));
        residues.add(new Residue(new Point(10, 15), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(60)))));
        residues.add(new Residue(new Point(9, 15), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(61)))));
        residues.add(new Residue(new Point(9, 14), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(62)))));
        residues.add(new Residue(new Point(8, 14), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(63)))));
        residues.add(new Residue(new Point(8, 15), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(64)))));
        residues.add(new Residue(new Point(7, 15), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(65)))));
        residues.add(new Residue(new Point(7, 14), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(66)))));
        residues.add(new Residue(new Point(6, 14), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(67)))));
        residues.add(new Residue(new Point(5, 14), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(68)))));
        residues.add(new Residue(new Point(5, 13), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(69)))));
        residues.add(new Residue(new Point(6, 13), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(70)))));
        residues.add(new Residue(new Point(7, 13), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(71)))));
        residues.add(new Residue(new Point(8, 13), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(72)))));
        residues.add(new Residue(new Point(8, 12), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(73)))));
        residues.add(new Residue(new Point(7, 12), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(74)))));
        residues.add(new Residue(new Point(7, 11), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(75)))));
        residues.add(new Residue(new Point(7, 10), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(76)))));
        residues.add(new Residue(new Point(8, 10), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(77)))));
        residues.add(new Residue(new Point(9, 10), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(78)))));
        residues.add(new Residue(new Point(9, 9), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(79)))));
        residues.add(new Residue(new Point(10, 9), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(80)))));
        residues.add(new Residue(new Point(10, 8), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(81)))));
        residues.add(new Residue(new Point(10, 7), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(82)))));
        residues.add(new Residue(new Point(9, 7), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(83)))));
        residues.add(new Residue(new Point(9, 8), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(84)))));
        residues.add(new Residue(new Point(8, 8), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(85)))));
        residues.add(new Residue(new Point(8, 9), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(86)))));
        residues.add(new Residue(new Point(7, 9), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(87)))));
        residues.add(new Residue(new Point(6, 9), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(88)))));
        residues.add(new Residue(new Point(6, 10), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(89)))));
        residues.add(new Residue(new Point(5, 10), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(90)))));
        residues.add(new Residue(new Point(5, 11), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(91)))));
        residues.add(new Residue(new Point(5, 12), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(92)))));
        residues.add(new Residue(new Point(4, 12), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(93)))));
        residues.add(new Residue(new Point(3, 12), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(94)))));
        residues.add(new Residue(new Point(2, 12), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(95)))));
        residues.add(new Residue(new Point(2, 13), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(96)))));
        residues.add(new Residue(new Point(2, 14), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(97)))));
        residues.add(new Residue(new Point(3, 14), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(98)))));
        residues.add(new Residue(new Point(3, 15), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt(99)))));

        Controller controller = new Controller();
        residues = ResidueUtils.translateToOrigin(residues);
        residuesInitial = Lists.newArrayList();

        for (Residue residue : residues) {
            residuesInitial.add((Residue) residue.clone());
        }
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

        public MyCanvas() {

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
            System.out.println(x + "," + y);

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
