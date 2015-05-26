/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.jmetal;

import java.util.List;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.encodings.solutionType.IntSolutionType;
import jmetal.util.JMException;
import jmetal.util.wrapper.XInt;

import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.utils.Controller;
import org.ufpr.cbio.poc.utils.ResidueUtils;

/**
 *
 *
 * @author vfontoura
 */
public class PSPProblem extends Problem {

    private String proteinChain;

    public PSPProblem(String proteinChain, int numberOfObjectives) {

        this.proteinChain = proteinChain;
        numberOfVariables_ = proteinChain.length() - 2;
        numberOfObjectives_ = numberOfObjectives;
        numberOfConstraints_ = 1;
        problemName_ = "PspProblem";

        upperLimit_ = new double[numberOfVariables_];
        lowerLimit_ = new double[numberOfVariables_];

        for (int i = 0; i < numberOfVariables_; i++) {
            lowerLimit_[i] = 0.0;
            upperLimit_[i] = 2.0;
        }
        solutionType_ = new IntSolutionType(this);

    }

    @Override
    public void evaluate(Solution solution) throws JMException {

        XInt vars = new XInt(solution);
        int[] moves = new int[numberOfVariables_];
        for (int i = 0; i < numberOfVariables_; i++) {
            moves[i] = vars.getValue(i);
        }

        Controller controller = new Controller();
        List<Residue> residues = controller.parseInput(proteinChain, moves);
        Grid grid = controller.generateGrid(residues);

        int collisionsCount = ResidueUtils.getCollisionsCount(residues);
        int topologicalContacts = ResidueUtils.getTopologyContacts(residues, grid).size();
        double maxPointsDistance = ResidueUtils.getMaxPointsDistance(residues);
        if (collisionsCount > 0) {
            topologicalContacts = topologicalContacts - collisionsCount;
        }
        if (residues.size() != proteinChain.length()) {
            topologicalContacts = 0;
            maxPointsDistance = 100;
        }

        solution.setObjective(0, -topologicalContacts);
        solution.setObjective(1, maxPointsDistance);

    }

    public SolutionSet removeDominateds(SolutionSet result) {

        boolean dominador, dominado;
        double valor1 = 0;
        double valor2 = 0;

        for (int i = 0; i < (result.size() - 1); i++) {
            for (int j = (i + 1); j < result.size(); j++) {
                dominador = true;
                dominado = true;

                for (int k = 0; k < numberOfObjectives_; k++) {
                    valor1 = result.get(i).getObjective(k);
                    valor2 = result.get(j).getObjective(k);

                    if (valor1 > valor2 || dominador == false) {
                        dominador = false;
                    } else if (valor1 <= valor2) {
                        dominador = true;
                    }

                    if (valor2 > valor1 || dominado == false) {
                        dominado = false;
                    } else if (valor2 < valor1) {
                        dominado = true;
                    }
                }

                if (dominador) {
                    result.remove(j);
                    j = j - 1;
                } else if (dominado) {
                    result.remove(i);
                    j = i;
                }
            }
        }

        return result;
    }

    public SolutionSet removeDuplicates(SolutionSet result) {

        String solucao;

        for (int i = 0; i < result.size() - 1; i++) {
            solucao = result.get(i).getDecisionVariables()[0].toString();
            for (int j = i + 1; j < result.size(); j++) {
                if (solucao.equals(result.get(j).getDecisionVariables()[0].toString())) {
                    result.remove(j);
                }
            }
        }

        return result;
    }

    // public static void main(String[] args) throws SecurityException,
    // IOException, JMException, ClassNotFoundException {
    //
    // OutputCSVWriter outputCSVWriter = new OutputCSVWriter();
    //
    // Problem problem; // The problem to solve
    // Algorithm algorithm; // The algorithm to use
    // Operator crossover; // Crossover operator
    // Operator mutation; // Mutation operator
    // Operator selection; // Selection operator
    //
    // HashMap<String, Double> parameters; // Operator parameters
    //
    // problem = new PSPProblem("IntSolution", 98);
    //
    // algorithm = new NSGAII(problem);
    // // algorithm = new ssNSGAII(problem);
    //
    // // Algorithm parameters
    // int populationSize = 100;
    // algorithm.setInputParameter("populationSize", populationSize);
    // int maxEvaluations = 25000;
    // algorithm.setInputParameter("maxEvaluations", maxEvaluations);
    //
    // // Mutation and Crossover for Real codification
    // parameters = new HashMap<>();
    // double crossoverProbability = 0.9;
    // parameters.put("probability", crossoverProbability);
    // double crossoverDistributionIndex = 20.0;
    // parameters.put("distributionIndex", crossoverDistributionIndex);
    // crossover = CrossoverFactory.getCrossoverOperator("SinglePointCrossover",
    // parameters);
    //
    // parameters = new HashMap<>();
    // double mutationProbability = 1.0 / problem.getNumberOfVariables();
    // parameters.put("probability", mutationProbability);
    // double mutationDistributionIndex = 20.0;
    // parameters.put("distributionIndex", mutationDistributionIndex);
    // mutation = MutationFactory.getMutationOperator("BitFlipMutation",
    // parameters);
    //
    // // Selection Operator
    // parameters = null;
    // selection = SelectionFactory.getSelectionOperator("BinaryTournament2",
    // parameters);
    //
    // // Add the operators to the algorithm
    // algorithm.addOperator("crossover", crossover);
    // algorithm.addOperator("mutation", mutation);
    // algorithm.addOperator("selection", selection);
    //
    // String JMETAL_ROOT_DIR = "jmetal_output_4.5";
    // boolean exists = new File(JMETAL_ROOT_DIR).exists();
    // if (!exists) {
    // new File(JMETAL_ROOT_DIR).mkdir();
    // }
    // String JMETAL_ALGORITHM_DIR =
    // String.format(JMETAL_ROOT_DIR + File.separator + "%s",
    // algorithm.getClass().getSimpleName());
    // exists = new File(JMETAL_ALGORITHM_DIR).exists();
    // if (!exists) {
    // new File(JMETAL_ALGORITHM_DIR).mkdir();
    // }
    // String acumulatorFileName =
    // JMETAL_ALGORITHM_DIR + File.separator +
    // "%s_%s_%s_%s_%s_%s_%s_nVariables_%s_pop_%s_maxEval_%s.csv";
    // String POPULATION_DIR = "Population";
    //
    // // Logger object and file to store log messages
    // LOGGER = Configuration.logger_;
    // FILE_HANDLER = new FileHandler(JMETAL_ALGORITHM_DIR + File.separator +
    // "NSGAII_main.log");
    // LOGGER.addHandler(FILE_HANDLER);
    //
    // for (int i = 0; i < 50; i++) {
    // // Execute the Algorithm
    // long initTime = System.currentTimeMillis();
    // SolutionSet population = algorithm.execute();
    // long estimatedTime = System.currentTimeMillis() - initTime;
    //
    // Solution bestIndividue = population.best(new Comparator<Solution>() {
    //
    // public int compare(Solution s1, Solution s2) {
    //
    // return (int) s1.getObjective(0) - (int) s2.getObjective(0);
    // }
    // });
    //
    // XInt vars = new XInt(bestIndividue);
    // StringBuilder sb = new StringBuilder();
    // for (int j = 0; j < vars.getNumberOfDecisionVariables(); j++) {
    // sb.append(vars.getValue(j)).append(",");
    // }
    // sb.deleteCharAt(sb.length() - 1);
    //
    // outputCSVWriter.writeOutputToCSV(String.format(acumulatorFileName,
    // crossover.getClass().getSimpleName(),
    // crossoverProbability, crossoverDistributionIndex,
    // mutation.getClass().getSimpleName(),
    // String.valueOf(mutationProbability).subSequence(0, 4),
    // mutationDistributionIndex, selection.getClass()
    // .getSimpleName(), problem.getNumberOfVariables(), populationSize,
    // maxEvaluations), new String[] {
    // String.valueOf(i), String.valueOf(bestIndividue.getObjective(0)),
    // sb.toString() });
    //
    // new File(JMETAL_ALGORITHM_DIR + File.separator + POPULATION_DIR).mkdir();
    // String solutionsOutputDir = JMETAL_ALGORITHM_DIR + File.separator +
    // POPULATION_DIR + File.separator + i;
    // new File(solutionsOutputDir).mkdir();
    //
    // // Result messages
    // LOGGER.info("Total execution time: " + estimatedTime + "ms");
    // LOGGER.info("Variables values have been writen to file " +
    // solutionsOutputDir + File.separator + " FUN");
    // population.printVariablesToFile(solutionsOutputDir + File.separator +
    // "VAR");
    // LOGGER.info("Objectives values have been writen to file " +
    // solutionsOutputDir + File.separator + " FUN");
    // population.printObjectivesToFile(solutionsOutputDir + File.separator +
    // "FUN");
    //
    // }
    // }

}
