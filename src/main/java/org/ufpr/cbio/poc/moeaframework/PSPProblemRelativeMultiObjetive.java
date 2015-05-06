/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.moeaframework;

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

/**
 *
 *
 * @author vfontoura
 */
public class PSPProblemRelativeMultiObjetive extends AbstractProblem {

    private String proteinChain =
        "PPPPPPHPHHPPPPPHHHPHHHHHPHHPPPPHHPPHHPHHHHHPHHHHHHHHHHPHHPHHHHHHHPPPPPPPPPPPHHHHHHHPPHPHHHPPPPPPHPHH";

    public PSPProblemRelativeMultiObjetive(int numberOfVariables, int numberOfObjectives, int numberOfConstraints) {

        super(numberOfVariables, numberOfObjectives, numberOfConstraints);
    }

    public PSPProblemRelativeMultiObjetive(int numberOfVariables, int numberOfObjectives) {

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
        double maxPointsDistance = ResidueUtils.getMaxPointsDistance(residues);
        if (collisionsCount > 0) {
            topologicalContacts = topologicalContacts - (2 * collisionsCount);
        }
        if (residues.size() != proteinChain.length()) {
            topologicalContacts = 0;
            // solution.setConstraint(0, 1.0);
        }

        solution.setObjective(0, -topologicalContacts);
        solution.setObjective(1, maxPointsDistance);

    }

    public Solution newSolution() {

        Solution solution = new Solution(numberOfVariables, numberOfObjectives);
        for (int i = 0; i < 98; i++) {
            solution.setVariable(i, EncodingUtils.newInt(0, 2));
        }

        return solution;
    }

    public static void main(String[] args) {

        NondominatedPopulation result =
            new Executor().withProblemClass(PSPProblemRelativeMultiObjetive.class, 98, 2).withAlgorithm("IBEA")
                .withMaxEvaluations(25000).distributeOnAllCores().run();

        for (int i = 0; i < result.size(); i++) {
            Solution solution = result.get(i);
            double fitness = solution.getObjective(0);
            double maxDistance = solution.getObjective(1);

            System.out.println("Solution " + (i) + ":");
            System.out.println(" Energy: " + fitness);
            System.out.println(" Max Distance: " + maxDistance);
            for (int j = 0; j < 98; j++) {
                System.out.print(EncodingUtils.getInt(solution.getVariable(j)) + ",");
            }
            System.out.println();
        }

    }

}
