/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.moeaframework;

import java.io.IOException;
import java.util.List;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;
import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.utils.Controller;
import org.ufpr.cbio.poc.utils.ResidueUtils;
import org.ufpr.cbio.poc.writer.OutputCSVWriter;

/**
 *
 *
 * @author vfontoura
 */
public class PSPProblemRelativeSingleObjetive extends AbstractProblem {

    private String proteinChain =
        "PPPPPPHPHHPPPPPHHHPHHHHHPHHPPPPHHPPHHPHHHHHPHHHHHHHHHHPHHPHHHHHHHPPPPPPPPPPPHHHHHHHPPHPHHHPPPPPPHPHH";

    public PSPProblemRelativeSingleObjetive(int numberOfVariables, int numberOfObjectives) {

        super(numberOfVariables, numberOfObjectives);
    }

    public void evaluate(Solution solution) {

        int[] moves = new int[98];
        for (int i = 0; i < 98; i++) {
            moves[i] = EncodingUtils.getInt(solution.getVariable(i));
        }

        Controller controller = new Controller();
        List<Residue> residues = controller.parseInput(proteinChain, moves);
        Grid grid = controller.generateGrid(residues);

        int collisionsCount = ResidueUtils.getCollisionsCount(residues);
        int topologicalContacts = ResidueUtils.getTopologyContacts(residues, grid).size();
        if (collisionsCount > 0) {
            topologicalContacts = topologicalContacts - collisionsCount;
        }
        if (residues.size() != proteinChain.length()) {
            topologicalContacts = 0;
        }
        /**
         * int topologicalContacts = 0; if (collisionsCount == 0 &&
         * residues.size() == proteinChain.length()) { topologicalContacts =
         * ResidueUtils.getTopologyContacts(residues, grid).size(); }
         */

        solution.setObjective(0, -topologicalContacts);

    }

    /*
     * (non-Javadoc)
     * @see org.moeaframework.problem.AbstractProblem#getNumberOfVariables()
     */
    @Override
    public int getNumberOfVariables() {

        return 1;
    }

    public Solution newSolution() {

        Solution solution = new Solution(numberOfVariables, numberOfObjectives);
        for (int i = 0; i < 98; i++) {
            solution.setVariable(i, EncodingUtils.newInt(0, 2));
        }
        return solution;
    }

    public static void main(String[] args) throws IOException {

        String fileName = "algoritm_%s_nVariables_%s_nObjectives_%s_maxEval_%s.ods";
        String algorithm = "NSGAIII";
        int maxEvaluations = 25000;
        int numberOfVariables = 98;
        int numberOfObjectives = 1;
        OutputCSVWriter outputCSVWriter = new OutputCSVWriter();
        for (int j = 0; j < 1; j++) {
            System.out.println("Seed: " + j);
            NondominatedPopulation result =
                new Executor()
                    .withProblemClass(PSPProblemRelativeSingleObjetive.class, numberOfVariables, numberOfObjectives)
                    .withAlgorithm(algorithm).withMaxEvaluations(maxEvaluations).distributeOnAllCores().run();

            String[] newLine = new String[2];
            double maxFitness = Double.MIN_VALUE;
            double fitness = 0;
            for (int i = 0; i < result.size(); i++) {
                Solution solution = result.get(i);
                fitness = solution.getObjective(0);
                newLine[0] = String.valueOf(fitness);
                System.out.println("Solution " + (i) + ":");
                System.out.println("Energy: " + fitness);
                StringBuilder sb = new StringBuilder();
                for (int t = 0; t < 98; t++) {
                    sb.append(EncodingUtils.getInt(solution.getVariable(t))).append(",");
                    System.out.print(EncodingUtils.getInt(solution.getVariable(t)) + ",");

                }
                sb.deleteCharAt(sb.length() - 1);
                newLine[1] = sb.toString();
                outputCSVWriter.writeOutputToCSV(
                    String.format(fileName, algorithm, numberOfVariables, numberOfObjectives, maxEvaluations), newLine);
                if (maxFitness < fitness) {
                    maxFitness = fitness;
                }
                // outputCSVWriter.writeOutputToCSV(
                // String.format(fileName, algorithm, numberOfVariables,
                // numberOfObjectives, maxEvaluations),
                // new String[] { "Max Fitness", String.valueOf(maxFitness) });

            }
            System.out.println();
        }

    }
}
