package org.ufpr.cbio.poc.utils;

import java.util.ArrayList;
import java.util.List;

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

    public List<Residue> parseInput(String chain, Integer[] solution) {

        return executeParse(chain, solution);
    }

    private List<Residue> executeParse(String chain, Integer[] solution) {

        int x = 0, y = 0, minX = 0, minY = 0, direction = 1;

        List<Residue> residues = new ArrayList<>();

        // Adiciona ponto inicial em (0,0) 1 Residuo
        residues.add(new Residue(new Point(x, y), ResidueType.valueOf(String.valueOf(chain.charAt(0)))));

        // Se tem 2 ou mais Residuos
        if (chain.length() >= 2 && solution.length >= chain.length() - 2) {

            x++;
            residues.add(new Residue(new Point(x, y), ResidueType.valueOf(String.valueOf(chain.charAt(1)))));

            for (int i = 0; i < chain.length() - 2; i++) {
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
                residues.add(new Residue(new Point(x, y), ResidueType.valueOf(String.valueOf(chain.charAt(i)))));
            }
        }
        for (Residue residue : residues) {
            residue.setPoint(new Residue.Point(residue.getPoint().x - minX + 1, residue.getPoint().y - minY + 1));
        }
        return residues;
    }

    public List<Residue> parseInput(String chain, List<Residue.Point> points) {

        return null;
    }

    public Integer[] createBasicSolution(int chainlength) {

        Integer solution[] = new Integer[chainlength - 2];
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

    public Protein buildProtein(String chain, List<Residue> residues, Grid structure, Integer energy) {

        return new Protein(chain, residues, structure, energy);
    }
}
