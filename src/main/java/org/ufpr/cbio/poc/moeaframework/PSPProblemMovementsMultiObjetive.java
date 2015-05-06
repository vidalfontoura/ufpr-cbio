/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.moeaframework;

import java.util.List;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.BinaryVariable;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;
import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.utils.Controller;
import org.ufpr.cbio.poc.utils.EnumMovements;
import org.ufpr.cbio.poc.utils.Movements;
import org.ufpr.cbio.poc.utils.ResidueUtils;

/**
 *
 *
 * @author user
 */
public class PSPProblemMovementsMultiObjetive extends AbstractProblem {

    private List<Residue> referenceResidues;

    public PSPProblemMovementsMultiObjetive(int numberOfVariables, int numberOfObjectives,
        List<Residue> referenceResidues) {

        super(numberOfVariables, numberOfObjectives);
        this.referenceResidues = referenceResidues;
    }

    public void evaluate(Solution solution) {

        boolean[] binarySolution = EncodingUtils.getBinary(solution.getVariable(0));
        int[] intArray = ResidueUtils.toIntArray(binarySolution);
        EnumMovements[] movementsArray = ResidueUtils.toMovementsArray(intArray);
        List<Residue> cloneResidueList = ResidueUtils.cloneResidueList(referenceResidues);
        Grid grid = new Controller().generateGrid(cloneResidueList);
        for (int i = 0; i < cloneResidueList.size(); i++) {
            Residue residue = cloneResidueList.get(i);
            Movements.doMovement(residue, cloneResidueList, grid, movementsArray[i]);
        }
        int topologicalContacts = ResidueUtils.getTopologyContacts(cloneResidueList, grid).size();
        // System.out.print("Fitness: " + topologicalContacts);
        // System.out.println(" Individuo: " +
        // Arrays.toString(intArray).replace(" ", ""));

        double maxPointsDistance = ResidueUtils.getMaxPointsDistance(cloneResidueList);

        solution.setObjective(0, -topologicalContacts);
        solution.setObjective(1, maxPointsDistance);

    }

    public Solution newSolution() {

        Solution solution = new Solution(numberOfVariables, numberOfObjectives);
        solution.setVariable(0, new BinaryVariable(referenceResidues.size()));
        return solution;
    }

    public static void main(String[] args) {

        List<Residue> referenceResidues =
            ResidueUtils
                .createDefaultReference100_1("PPPPPPHPHHPPPPPHHHPHHHHHPHHPPPPHHPPHHPHHHHHPHHHHHHHHHHPHHPHHHHHHHPPPPPPPPPPPHHHHHHHPPHPHHHPPPPPPHPHH");

        NondominatedPopulation result =
            new Executor().withProblemClass(PSPProblemMovementsMultiObjetive.class, 1, 2, referenceResidues)
                .withAlgorithm("NSGAIII").withMaxEvaluations(50000).distributeOnAllCores().run();

        for (int i = 0; i < result.size(); i++) {
            Solution solution = result.get(i);
            double fitness = solution.getObjective(0);
            double maxDistance = solution.getObjective(1);

            System.out.println("Solution " + (i) + ":");
            System.out.println(" Energy: " + fitness);
            System.out.println(" Max Distance: " + maxDistance);
            System.out.println(" Binary Value: " + solution.getVariable(0));

        }

    }

}
