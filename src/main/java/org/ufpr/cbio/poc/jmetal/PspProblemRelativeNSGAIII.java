/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.jmetal;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.utils.Controller;
import org.ufpr.cbio.poc.utils.ResidueUtils;
import org.ufpr.cbio.poc.writer.OutputCSVWriter;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIII;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.fileoutput.SolutionSetOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

/**
 *
 *
 * @author vfontoura
 */
public class PspProblemRelativeNSGAIII extends org.uma.jmetal.problem.impl.AbstractIntegerProblem {

    private String proteinChain;

    public PspProblemRelativeNSGAIII() {

        this.proteinChain =
            "PPPPPPHPHHPPPPPHHHPHHHHHPHHPPPPHHPPHHPHHHHHPHHHHHHHHHHPHHPHHHHHHHPPPPPPPPPPPHHHHHHHPPHPHHHPPPPPPHPHH";
        this.setNumberOfObjectives(1);
        this.setNumberOfVariables(this.proteinChain.length() - 2);
        ArrayList<Integer> lowerLimit = Lists.newArrayList();
        ArrayList<Integer> upperLimit = Lists.newArrayList();
        for (int i = 0; i < getNumberOfVariables(); i++) {
            lowerLimit.add(0);
            upperLimit.add(2);

        }
        this.setLowerLimit(lowerLimit);
        this.setUpperLimit(upperLimit);

    }

    /*
     * (non-Javadoc)
     * @see
     * org.uma.jmetal.problem.Problem#evaluate(org.uma.jmetal.solution.Solution)
     */
    public void evaluate(IntegerSolution solution) {

        int[] moves = new int[getNumberOfVariables()];
        for (int i = 0; i < getNumberOfVariables(); i++) {
            Integer variableValue = solution.getVariableValue(i);
            moves[i] = variableValue;
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

        solution.setObjective(0, -topologicalContacts);

    }

    public static void main(String[] args) throws IOException {

        OutputCSVWriter outputCSVWriter = new OutputCSVWriter();

        Problem<IntegerSolution> problem;
        Algorithm algorithm;
        CrossoverOperator crossover;
        MutationOperator mutation;
        SelectionOperator selection;

        problem = new PspProblemRelativeNSGAIII();
        double crossoverProbability = 0.9;
        double crossoverDistributionIndex = 20.0;

        double mutationProbability = 1.0 / problem.getNumberOfVariables();
        double mutationDistributionIndex = 20.0;
        int maxEvaluations = 25000;
        int populationSize = 100;
        int divisions = 12;

        crossover = new IntegerSBXCrossover(crossoverProbability, crossoverDistributionIndex);
        mutation = new IntegerPolynomialMutation(mutationProbability, mutationDistributionIndex);
        selection = new BinaryTournamentSelection();

        algorithm =
            new NSGAIIIBuilder(problem).setCrossoverOperator(crossover).setMutationOperator(mutation)
                .setSelectionOperator(selection).setMaxEvaluations(maxEvaluations).setPopulationSize(populationSize)
                .setDivisions(divisions).build();

        String JMETAL_ROOT_DIR = "jmetal_output";
        boolean exists = new File(JMETAL_ROOT_DIR).exists();
        if (!exists) {
            new File(JMETAL_ROOT_DIR).mkdir();
        }
        String JMETAL_ALGORITHM_DIR =
            String.format(JMETAL_ROOT_DIR + File.separator + "%s", algorithm.getClass().getSimpleName());
        exists = new File(JMETAL_ALGORITHM_DIR).exists();
        if (!exists) {
            new File(JMETAL_ALGORITHM_DIR).mkdir();
        }
        String acumulatorFileName =
            JMETAL_ALGORITHM_DIR + File.separator + "%s_%s_%s_%s_%s_%s_%s_nVariables_%s_pop_%s_maxEval_%s_div_%s.ods";
        String POPULATION_DIR = "Population";

        for (int i = 0; i < 50; i++) {

            AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

            List<Solution> population = ((NSGAIII) algorithm).getResult();
            Set<Solution> unikeIndividues = Sets.newHashSet(population);

            Solution bestIndividue =
                population.stream().min((s1, s2) -> (int) s1.getObjective(0) - (int) s2.getObjective(0)).get();

            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < bestIndividue.getNumberOfVariables(); j++) {
                sb.append(bestIndividue.getVariableValue(j)).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);

            long computingTime = algorithmRunner.getComputingTime();

            outputCSVWriter.writeOutputToCSV(String.format(acumulatorFileName, crossover.getClass().getSimpleName(),
                crossoverProbability, crossoverDistributionIndex, mutation.getClass().getSimpleName(),
                String.valueOf(mutationProbability).subSequence(0, 4), mutationDistributionIndex, selection.getClass()
                    .getSimpleName(), problem.getNumberOfVariables(), populationSize, maxEvaluations, divisions),
                new String[] { String.valueOf(i), String.valueOf(bestIndividue.getObjective(0)), sb.toString() });

            new File(JMETAL_ALGORITHM_DIR + File.separator + POPULATION_DIR).mkdir();
            String solutionsOutputDir = JMETAL_ALGORITHM_DIR + File.separator + POPULATION_DIR + File.separator + i;
            new File(solutionsOutputDir).mkdir();

            new SolutionSetOutput.Printer(population).setSeparator("\t")
                .setVarFileOutputContext(new DefaultFileOutputContext(solutionsOutputDir + File.separator + "VAR.tsv"))
                .setFunFileOutputContext(new DefaultFileOutputContext(solutionsOutputDir + File.separator + "FUN.tsv"))
                .print();

            JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
            JMetalLogger.logger.info("Objectives values have been written to file" + solutionsOutputDir
                + File.separator + "VAR.tsv");
            JMetalLogger.logger.info("Variables values have been written to file" + solutionsOutputDir + File.separator
                + "VAR.tsv");

        }

    }
}
