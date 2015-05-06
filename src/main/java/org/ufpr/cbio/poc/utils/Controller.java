package org.ufpr.cbio.poc.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Protein;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.domain.Residue.Point;
import org.ufpr.cbio.poc.domain.ResidueType;

public class Controller implements ControllerInterface {

    /**
     * Parse a String Chain of H/P values into
     */
    public List<Residue> parseInput(String chain) {

        return executeParse(chain, createBasicSolution(chain.length()));
    }

    public List<Residue> parseInput(String chain, int[] solution) {

        return executeParse2(chain, solution);
    }

    private List<Residue> executeParse(String chain, int[] solution) {

        int x = 0, y = 0, minX = 0, minY = 0, direction = 1;
        int chainIndex = 0;

        List<Residue> residues = new ArrayList<>();
        Set<Point> points = new HashSet<>();

        // Adiciona ponto inicial em (0,0) 1 Residuo
        residues.add(new Residue(new Point(x, y), ResidueType.valueOf(String.valueOf(chain.charAt(0)))));
        points.add(new Point(x, y));
        chainIndex++;

        // Se tem 2 ou mais Residuos
        if (chain.length() >= 2 && solution.length == chain.length() - 2) {

            x++;
            residues.add(new Residue(new Point(x, y), ResidueType.valueOf(String.valueOf(chain.charAt(1)))));
            chainIndex++;
            points.add(new Point(x, y));

            for (int i = 0; i < solution.length; i++) {
                int step = solution[i];
                switch (direction) {
                    case 0:// LOOKING UP
                        switch (step) {
                            case 0:
                                x--;
                                direction = 3;
                                break;
                            case 1:
                                y++;
                                direction = 0;
                                break;
                            case 2:
                                x++;
                                direction = 1;
                                break;
                            default:
                                // TODO Exception movimento invalido
                                break;
                        }
                        break;
                    case 1:// LOOKING FORWARD
                        switch (step) {
                            case 0:
                                y++;
                                direction = 0;
                                break;
                            case 1:
                                x++;
                                direction = 1;
                                break;
                            case 2:
                                y--;
                                direction = 2;
                                break;
                            default:
                                // TODO Exception movimento inválido
                                break;
                        }
                        break;
                    case 2:// LOOKING DOWN
                        switch (step) {
                            case 0:
                                x++;
                                direction = 1;
                                break;
                            case 1:
                                y--;
                                direction = 2;
                                break;
                            case 2:
                                x--;
                                direction = 3;
                                break;
                            default:
                                // TODO Exception movimento inválido
                                break;
                        }
                        break;
                    case 3:// LOOKING BACK
                        switch (step) {
                            case 0:
                                y--;
                                direction = 2;
                                break;
                            case 1:
                                x--;
                                direction = 3;
                                break;
                            case 2:
                                y++;
                                direction = 0;
                                break;
                            default:
                                // TODO Exception movimento inválido
                                break;
                        }
                        break;
                    default:
                        break;
                }
                if (x < minX) {
                    minX = x;
                }
                if (y < minY) {
                    minY = y;
                }
                residues.add(new Residue(new Point(x, y), ResidueType.valueOf(String.valueOf(chain.charAt(chainIndex++)))));
            }
        }
        for (Residue residue : residues) {
            residue.setPoint(new Residue.Point(residue.getPoint().x - minX + 1, residue.getPoint().y - minY + 1));
        }
        return residues;
    }

    private List<Residue> executeParse2(String chain, int[] solution) {

        List<Residue> residues = new ArrayList<>();
        int x = 0, y = 0;
        int direction = 1;
        int chainIndex = 0;

        // set first residue
        Point firstPoint = new Point(x, y);
        setNewResidue(residues, firstPoint, ResidueType.valueOf(String.valueOf(chain.charAt(0))));
        chainIndex++;

        // Se tem 2 ou mais Residuos
        if (chain.length() >= 2 && solution.length == chain.length() - 2) {
            x++;
            Point secondPoint = new Point(x, y);
            setNewResidue(residues, secondPoint, ResidueType.valueOf(String.valueOf(chain.charAt(1))));
            chainIndex++;

            for (int i = 0; i < solution.length; i++) {
                int step = solution[i];
                int[] directions = getDirections(direction, step, x, y);
                // int[] firstDirections = cloneArray(directions);
                Point newPoint = new Point(directions[1], directions[2]);
                boolean added =
                    setNewResidue(residues, newPoint, ResidueType.valueOf(String.valueOf(chain.charAt(chainIndex + i))));
                int count = 0;
                while (!added) {
                    if (count == 3) {
                        break;
                    }
                    step = count;
                    directions = getDirections(direction, step, x, y);

                    newPoint = new Point(directions[1], directions[2]);
                    added =
                        setNewResidue(residues, newPoint,
                            ResidueType.valueOf(String.valueOf(chain.charAt(chainIndex + i))));
                    count++;

                }
                x = directions[1];
                y = directions[2];
                direction = directions[0];
            }
            residues = ResidueUtils.translateToOrigin(residues);
        }
        return residues;

    }

    private int[] getDirections(int direction, int step, int x, int y) {

        switch (direction) {
            case 0:// LOOKING UP
                switch (step) {
                    case 0:
                        x--;
                        direction = 3;
                        break;
                    case 1:
                        y++;
                        direction = 0;
                        break;
                    case 2:
                        x++;
                        direction = 1;
                        break;
                    default:
                        // TODO Exception movimento invalido
                        break;
                }
                break;
            case 1:// LOOKING FORWARD
                switch (step) {
                    case 0:
                        y++;
                        direction = 0;
                        break;
                    case 1:
                        x++;
                        direction = 1;
                        break;
                    case 2:
                        y--;
                        direction = 2;
                        break;
                    default:
                        // TODO Exception movimento inválido
                        break;
                }
                break;
            case 2:// LOOKING DOWN
                switch (step) {
                    case 0:
                        x++;
                        direction = 1;
                        break;
                    case 1:
                        y--;
                        direction = 2;
                        break;
                    case 2:
                        x--;
                        direction = 3;
                        break;
                    default:
                        // TODO Exception movimento inválido
                        break;
                }
                break;
            case 3:// LOOKING BACK
                switch (step) {
                    case 0:
                        y--;
                        direction = 2;
                        break;
                    case 1:
                        x--;
                        direction = 3;
                        break;
                    case 2:
                        y++;
                        direction = 0;
                        break;
                    default:
                        // TODO Exception movimento inválido
                        break;
                }
                break;
            default:
                break;
        }
        return new int[] { direction, x, y };
    }

    private boolean setNewResidue(List<Residue> residues, Point newPoint, ResidueType residueType) {

        Set<Point> points = residues.stream().map(Residue::getPoint).collect(Collectors.toSet());
        boolean ret = points.add(newPoint);
        if (ret) {
            residues.add(new Residue(newPoint, residueType));
        }
        return ret;
    }

    public List<Residue> parseInput(String chain, List<Residue.Point> points) {

        return null;
    }

    public int[] createBasicSolution(int chainlength) {

        int solution[] = new int[chainlength - 2];
        for (int i = 0; i < solution.length; i++) {
            solution[i] = 1;
        }
        return solution;
    }

    public Grid generateGrid(List<Residue> residues) {

        Grid g = new Grid(residues.size() + 2, residues.size() + 2);
        for (int i = 0; i < residues.size(); i++) {
            g.getMatrix()[residues.get(i).getPoint().y][residues.get(i).getPoint().x] = i;
        }
        return g;
    }

    public Integer evaluateEnergy(List<Residue> residues, Grid grid) {

        return 0;
    }

    public Protein buildProtein(String chain, List<Residue> residues) {

        return new Protein(chain, residues);
    }

    public int[] cloneArray(int[] fitness) {

        int[] clone = new int[fitness.length];
        for (int i = 0; i < fitness.length; i++) {
            clone[i] = fitness[i];
        }
        return clone;
    }
}
