package org.ufpr.cbio.poc.visual;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
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
import org.ufpr.cbio.poc.utils.Controller;
import org.ufpr.cbio.poc.utils.ResidueUtils;

/**
 *
 *
 * @author vfontoura
 */
public class ApplyFixedRelativeSolution {

    // private static final String PROTEIN_CHAIN = "HPHPPHHPHPPHPHHPPHPH";

    // private static final String PROTEIN_CHAIN =
    // "HHHHPPPPHHHHHHHHHHHHPPPPPPHHHHHHHHHHHHPPPHHHHHHHHHHHHPPPHHHHHHHHHHHHPPPHPPHHPPHHPPHPH";

    private static final String PROTEIN_CHAIN =
        "PPPPPPHPHHPPPPPHHHPHHHHHPHHPPPPHHPPHHPHHHHHPHHHHHHHHHHPHHPHHHHHHHPPPPPPPPPPPHHHHHHHPPHPHHHPPPPPPHPHH";

    private static final int SCREEN_SIZE = 600;
    private static final int MIN_SIZE_FACTOR = 20;
    private static final int MAX_SIZE_SCROLL_PANEL = 500;

    private static int sizeFactor = 40;
    private static int maxGridSize = 600;
    private static int slots = 0;
    private static List<Residue> residues;
    private static int energyValue = 0;
    private static int maxDistance = 0;
    private static int collisions = 0;

    private static Grid grid = null;

    public static void addComponentsToPane(Container pane) {

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        MyCanvas myCanvas = new MyCanvas();
        if (grid == null) {
            slots = 0;
            maxGridSize = 0;
            sizeFactor = 0;
        }

        myCanvas.setPreferredSize(new Dimension(maxGridSize, maxGridSize));

        JScrollPane jScrollPane = new JScrollPane(myCanvas);
        jScrollPane.setBounds(5, 30, MAX_SIZE_SCROLL_PANEL, MAX_SIZE_SCROLL_PANEL);
        jScrollPane.setWheelScrollingEnabled(true);
        jScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

        JTextField solutionTextField = new JTextField("Enter the solution");
        JButton applyMovementsButton = new JButton("Apply Movements");
        JButton printPointsButton = new JButton("Print Points");

        JButton resetButton = new JButton("Reset");
        JLabel energyLabel = new JLabel("Energy value: " + energyValue);
        JLabel collisionsLabel = new JLabel("Collisions: " + collisions);
        JLabel chainLengthLabel = new JLabel("Chain length: " + (residues == null ? 0 : residues.size()));
        JLabel maxDistanceLabel = new JLabel("Max Distance: " + maxDistance);
        JLabel fitnessLabel = new JLabel("Fitness: " + (energyValue - collisions));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(applyMovementsButton, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(solutionTextField, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        pane.add(resetButton, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 0;
        pane.add(printPointsButton, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(energyLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 1;
        pane.add(collisionsLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 1;
        pane.add(chainLengthLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 4;
        c.gridy = 1;
        pane.add(maxDistanceLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 5;
        c.gridy = 1;
        pane.add(fitnessLabel, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 5;
        c.gridx = 0;
        c.gridy = 2;

        pane.add(jScrollPane, c);

        applyMovementsButton
            .addActionListener(listener -> {

                String text = solutionTextField.getText();
                int[] moves = fromStringToInt(text.split(","));
                Controller controller = new Controller();
                residues = controller.parseInput(PROTEIN_CHAIN, moves);
                grid = new Controller().generateGrid(residues);

                energyLabel.setText("Energy value: " + ResidueUtils.getTopologyContacts(residues, grid).size());
                collisionsLabel.setText("Collisions: " + ResidueUtils.getCollisionsCount(residues));
                fitnessLabel.setText("Fitness: "
                    + (ResidueUtils.getTopologyContacts(residues, grid).size() - ResidueUtils
                        .getCollisionsCount(residues)));
                chainLengthLabel.setText("Chain length: " + residues.size());
                maxDistanceLabel.setText("Max Distance: " + ResidueUtils.getMaxPointsDistance(residues));

                slots = (getBound(grid.getMatrix()) + 8);
                maxGridSize = calculateMaxGridSize(grid.getMatrix());
                sizeFactor = MAX_SIZE_SCROLL_PANEL / slots;
                if (sizeFactor < MIN_SIZE_FACTOR) {
                    sizeFactor = MIN_SIZE_FACTOR;
                }
                myCanvas.repaint();
            });
        resetButton.addActionListener(listener -> {

            Runnable task = () -> {
                residues = null;
                // energyLabel.setText("Energy value: "
                // + ResidueUtils.getTopologyContacts(residuesList, new
                // Controller().generateGrid(residues))
                // .size());
                jScrollPane.repaint();
            };
            new Thread(task).start();
        });

        printPointsButton.addActionListener(listener -> {
            for (int j = 0; j < residues.size(); j++) {
                System.out.println("residues.add(new Residue(new Point(" + residues.get(j).getPoint().getX() + ", "
                    + residues.get(j).getPoint().getY() + "), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt("
                    + j + ")))));");
            }

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

        // Set up the content pane.
        addComponentsToPane(frame.getContentPane());

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
            if (residues != null) {

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
