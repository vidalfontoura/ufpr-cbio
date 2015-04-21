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
public class GridPlugGA {

    private static final int SCREEN_SIZE = 700;
    private static final int MIN_SIZE_FACTOR = 20;
    private static final int MAX_SIZE_SCROLL_PANEL = 500;

    private static int sizeFactor = 40;
    private static int maxGridSize = 700;
    private static int slots = 0;

    private static List<Residue> residues;

    private static List<Residue> residuesInitial;

    private static final int INDIVIDUE_LENGHT = 100;
    private static final int POPULATION_SIZE = 20;
    private static final int GENERATIONS = 1000;
    private static final int MUTATION = 3;
    private static final int CROSSOVER = 3;
    private static final int SELECTION = 1;
    private static final String PROTEIN_CHAIN =
        "PPPPPPHPHHPPPPPHHHPHHHHHPHHPPPPHHPPHHPHHHHHPHHHHHHHHHHPHHPHHHHHHHPPPPPPPPPPPHHHHHHHPPHPHHHPPPPPPHPHH";

    private static final double CROSSOVER_RATE = 0.9;
    private static final double MUTATION_RATE = 0.095;
    private static final int ELITISM_PERCENTAGE = 10;
    private static final int SEED = 1;

    private static final boolean DYNAMIC_CHANGE_REFERENCE = false;
    private static final int MAX_REFERENCE_CHANGE = 20;
    private static final int MAX_REPEATED_FITNEES = 5;

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
        pane.add(crossOverLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(crossoverComboBox, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        pane.add(crossOverRateLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 0;
        pane.add(crossOverRateField, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 4;
        c.gridy = 0;
        pane.add(mutationLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 5;
        c.gridy = 0;
        pane.add(mutationComboBox, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 6;
        c.gridy = 0;
        pane.add(selectionLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 7;
        c.gridy = 0;
        pane.add(selectionComboBox, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(populationLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        pane.add(populationTextField, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 1;
        pane.add(generationsLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 1;
        pane.add(genTextField, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 4;
        c.gridy = 1;
        pane.add(runGAButton, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 5;
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

        residues = ResidueUtils.createDefaultReference100_1(PROTEIN_CHAIN);

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
