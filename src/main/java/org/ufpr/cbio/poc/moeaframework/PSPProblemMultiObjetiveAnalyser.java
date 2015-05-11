/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.moeaframework;

import java.io.IOException;
import java.util.List;

import org.moeaframework.Analyzer;
import org.moeaframework.Executor;
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
public class PSPProblemMultiObjetiveAnalyser extends AbstractProblem {

    private String proteinChain =
        "PPPPPPHPHHPPPPPHHHPHHHHHPHHPPPPHHPPHHPHHHHHPHHHHHHHHHHPHHPHHHHHHHPPPPPPPPPPPHHHHHHHPPHPHHHPPPPPPHPHH";

    public PSPProblemMultiObjetiveAnalyser(int numberOfVariables, int numberOfObjectives, int numberOfConstraints) {

        super(numberOfVariables, numberOfObjectives, numberOfConstraints);
    }

    public PSPProblemMultiObjetiveAnalyser(int numberOfVariables, int numberOfObjectives) {

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
        // solution.setObjective(1, maxPointsDistance);

    }

    public Solution newSolution() {

        Solution solution = new Solution(numberOfVariables, numberOfObjectives);
        for (int i = 0; i < 98; i++) {
            solution.setVariable(i, EncodingUtils.newInt(0, 2));
        }

        return solution;
    }

    public static void main(String[] args) throws IOException {

        Analyzer analyzer =
            new Analyzer().withProblemClass(PSPProblemMultiObjetiveAnalyser.class, 98, 1).includeAllMetrics()
                .showStatisticalSignificance();

        Executor executor =
            new Executor().withProblemClass(PSPProblemMultiObjetiveAnalyser.class, 98, 1).withMaxEvaluations(10000);

        analyzer.addAll("NSGAII", executor.withAlgorithm("NSGAII").runSeeds(50));

        analyzer.addAll("NSGAIII", executor.withAlgorithm("NSGAIII").runSeeds(50));
        //
        // analyzer.addAll("IBEA", executor.withAlgorithm("IBEA").runSeeds(50));
        //
        // analyzer.addAll("MOEAD",
        // executor.withAlgorithm("MOEAD").runSeeds(50));

        analyzer.printAnalysis();

    }
}
