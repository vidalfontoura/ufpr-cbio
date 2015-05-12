package org.ufpr.cbio.poc.visual;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.border.Border;

import org.jfree.chart.block.LineBorder;
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

//     private static final String PROTEIN_CHAIN = "HPHPPHHPHPPHPHHPPHPH";

//     private static final String PROTEIN_CHAIN = 
//    		 "HHHHPPPPHHHHHHHHHHHHPPPPPPHHHHHHHHHHHHPPPHHHHHHHHHHHHPPPHHHHHHHHHHHHPPPHPPHHPPHHPPHPH";

    private static final String PROTEIN_CHAIN =
        "PPPPPPHPHHPPPPPHHHPHHHHHPHHPPPPHHPPHHPHHHHHPHHHHHHHHHHPHHPHHHHHHHPPPPPPPPPPPHHHHHHHPPHPHHHPPPPPPHPHH";

    private static final int SCREEN_SIZE = 600;
    private static final int MIN_SIZE_FACTOR = 20;
    private static final int MAX_SIZE_SCROLL_PANEL = 500;

    private static int sizeFactor = 20;
    private static int maxGridSize = 500;
    private static int slots = 0;
    private static List<Residue> residues;
    private static int energyValue = 0;
    private static int maxDistance = 0;
    private static int collisions = 0;

    private static Grid grid = null;

    public static void addComponentsToPane(Container pane) {

        pane.setLayout(new FlowLayout());

        MyCanvas myCanvas = new MyCanvas();
        
        JScrollPane jScrollPane = new JScrollPane(myCanvas, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setPreferredSize(new Dimension(500, 500));
        jScrollPane.setBounds(5, 30, MAX_SIZE_SCROLL_PANEL, MAX_SIZE_SCROLL_PANEL);
        jScrollPane.setWheelScrollingEnabled(true);
        jScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
       
        JTextField chainTextField = new JTextField(PROTEIN_CHAIN);
        JButton applyMovementsButton = new JButton("Apply Movements");
        JTextField solutionTextField = new JTextField("Enter the solution");
        JButton printPointsButton = new JButton("Print Points");

        JButton resetButton = new JButton("Reset");
        JLabel energyLabel = new JLabel("Energy value: " + energyValue);
        JLabel collisionsLabel = new JLabel("Collisions: " + collisions);
        JLabel chainLengthLabel = new JLabel("Chain length: " + (residues == null ? 0 : residues.size()));
        JLabel maxDistanceLabel = new JLabel("Max Distance: " + maxDistance);
        JLabel fitnessLabel = new JLabel("Fitness: " + (energyValue - collisions));

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        
        panel2.setPreferredSize(new Dimension(200, 400));
        panel2.setLayout(new GridLayout(10, 1));
        
        panel1.add(jScrollPane);
        
        panel2.add(chainTextField);
        panel2.add(applyMovementsButton);
        panel2.add(solutionTextField);
        panel2.add(chainLengthLabel);
        panel2.add(energyLabel);
        panel2.add(maxDistanceLabel);
        panel2.add(fitnessLabel);
        panel2.add(resetButton);
        panel2.add(printPointsButton);
        
        pane.add(panel1);
        pane.add(panel2);
        
        applyMovementsButton
            .addActionListener(listener -> {

                String text = solutionTextField.getText();
                int[] moves = fromStringToInt(text.split(","));
                Controller controller = new Controller();
                String chain = chainTextField.getText();
                residues = controller.parseInput(chain, moves);
                grid = new Controller().generateGrid(residues);

                energyLabel.setText("Energy value: " + ResidueUtils.getTopologyContacts(residues, grid).size());
                collisionsLabel.setText("Collisions: " + ResidueUtils.getCollisionsCount(residues));
                fitnessLabel.setText("Fitness: "
                    + (ResidueUtils.getTopologyContacts(residues, grid).size() - ResidueUtils
                        .getCollisionsCount(residues)));
                chainLengthLabel.setText("Chain length: " + residues.size());
                maxDistanceLabel.setText("Max Distance: " + ResidueUtils.getMaxPointsDistance(residues));

                slots = (getBound(grid.getMatrix()) + 3);
                System.out.println(slots);
                maxGridSize = calculateMaxGridSize(grid.getMatrix());
                
                myCanvas.repaint();
            });
        
        resetButton.addActionListener(listener -> {

            Runnable task = () -> {
                residues = null;
                energyLabel.setText("Energy value: 0");
                collisionsLabel.setText("Collisions: 0");
                fitnessLabel.setText("Fitness: 0");
                chainLengthLabel.setText("Chain length: 0");
                maxDistanceLabel.setText("Max Distance: 0");
                slots = 0;
                myCanvas.repaint();
            };
            new Thread(task).start();
        });

        printPointsButton.addActionListener(listener -> {
        	if(residues != null) {
	        	for (int j = 0; j < residues.size(); j++) {
	                System.out.println("residues.add(new Residue(new Point(" + residues.get(j).getPoint().getX() + ", "
	                    + residues.get(j).getPoint().getY() + "), ResidueType.valueOf(String.valueOf(PROTEIN_CHAIN.charAt("
	                    + j + ")))));");
	            }
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
        frame.pack();
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

    	private int WIDTH;
    	private int HEIGHT;
    	
        public MyCanvas() {

            addMouseListener(this);
        }

        @Override
        public void paint(Graphics g) {
        	        	
        	WIDTH = HEIGHT = (maxGridSize > 500) ? maxGridSize : 480;
        	
        	System.out.println("WIDTH: "+WIDTH+" | "+getWidth());
        	
        	Graphics2D g2d = (Graphics2D) g.create(0, 0, WIDTH, HEIGHT);
        	
        	g2d.setColor(Color.white);
        	g2d.fillRect(0, 0, WIDTH, HEIGHT);
        	g2d.setColor(Color.LIGHT_GRAY);
        	
            for (int i = 0; i < slots; i++) {
                for (int j = 0; j < slots; j++) {
                    int rectX = sizeFactor * i;
                    int rectY = sizeFactor * j;
                    g2d.drawRect(rectX, rectY, sizeFactor, sizeFactor);
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
                        g2d.setColor(Color.BLACK);
                        g2d.fillRect(residueX, residueY, sizeFactor / 2, sizeFactor / 2);//desenha quadrado
                        //g2d.fillArc(residueX, residueY, sizeFactor / 2, sizeFactor / 2, 0, 360);//desenha bolinha
                    } else {
                        g2d.setColor(Color.LIGHT_GRAY);
                        g2d.fillArc(residueX, residueY, sizeFactor / 2, sizeFactor / 2, 0, 360);
                    }
                    g2d.setColor(Color.BLACK);

                    if (next != null) {
                        int nextX = sizeFactor * (next.getPoint().getX()) + sizeFactor / 4;
                        int nextY = sizeFactor * (next.getPoint().getY()) + sizeFactor / 4;
                        g2d.drawLine(residueX + sizeFactor / 4, residueY + sizeFactor / 4, nextX + sizeFactor / 4, nextY
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
