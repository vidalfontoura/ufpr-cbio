package org.ufpr.cbio.poc.jmetal;

import java.io.File;
import java.util.HashMap;

import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.core.SolutionSet;
import jmetal.metaheuristics.nsgaII.NSGAII;
import jmetal.operators.crossover.CrossoverFactory;
import jmetal.operators.mutation.MutationFactory;
import jmetal.operators.selection.SelectionFactory;
import jmetal.util.JMException;

public class NSGAIIPSPProblemRelativeMain {

    public static void main(String[] args) throws JMException, ClassNotFoundException {

        File file = new File("psp_experiments");
        if (!file.exists()) {
            file.mkdir();
        }

        String path = "psp_experiments/experiments";
        String algorithms = "NSGAII";
        int executions = 30;

        PSPProblem problem; // The problem to solve
        Algorithm algorithm; // The algorithm to use
        Operator crossover; // Crossover operator
        Operator mutation; // Mutation operator
        Operator selection; // Selection operator

        HashMap<String, Double> parameters; // Operator parameters

        String proteinChain =
            "PPPPPPHPHHPPPPPHHHPHHHHHPHHPPPPHHPPHHPHHHHHPHHHHHHHHHHPHHPHHHHHHHPPPPPPPPPPPHHHHHHHPPHPHHHPPPPPPHPHH";
        int numberOfObjectives = 2;
        problem = new PSPProblem(proteinChain, numberOfObjectives);

        algorithm = new NSGAII(problem);

        // Algorithm parameters
        int populationSize = 100;
        algorithm.setInputParameter("populationSize", populationSize);
        int maxEvaluations = 25000;
        algorithm.setInputParameter("maxEvaluations", maxEvaluations);

        // Mutation and Crossover for Real codification
        parameters = new HashMap<>();
        double crossoverProbability = 0.9;
        parameters.put("probability", crossoverProbability);
        double crossoverDistributionIndex = 20.0;
        parameters.put("distributionIndex", crossoverDistributionIndex);
        crossover = CrossoverFactory.getCrossoverOperator("SinglePointCrossover", parameters);

        parameters = new HashMap<>();
        double mutationProbability = 1.0 / problem.getNumberOfVariables();
        parameters.put("probability", mutationProbability);
        double mutationDistributionIndex = 20.0;
        parameters.put("distributionIndex", mutationDistributionIndex);
        mutation = MutationFactory.getMutationOperator("BitFlipMutation", parameters);

        // Selection Operator
        parameters = null;
        selection = SelectionFactory.getSelectionOperator("BinaryTournament2", parameters);

        // Add the operators to the algorithm
        algorithm.addOperator("crossover", crossover);
        algorithm.addOperator("mutation", mutation);
        algorithm.addOperator("selection", selection);

        File rootDir = createDir(path);
        File algorithmDir = createDir(rootDir.getPath() + File.separator + algorithms + File.separator);
        File objectivesDir = createDir(algorithmDir.getPath() + File.separator);

        String outputDir = objectivesDir.getPath() + File.separator;

        SolutionSet allRuns = new SolutionSet();

        long allExecutionTime = 0;
        System.out.println("Starting executions...");
        for (int i = 0; i < executions; i++) {

            // Execute the Algorithm
            System.out.println("Execution: " + (i + 1));
            long initTime = System.currentTimeMillis();
            SolutionSet population = algorithm.execute();
            long estimatedTime = System.currentTimeMillis() - initTime;

            allExecutionTime += estimatedTime;

            String executionDirectory = outputDir + "EXECUTION_" + i;
            createDir(executionDirectory);

            problem.removeDominateds(population);
            problem.removeDuplicates(population);

            population.printVariablesToFile(executionDirectory + File.separator + "VAR.txt");
            population.printObjectivesToFile(executionDirectory + File.separator + "FUN.txt");

            allRuns = allRuns.union(population);
        }

        System.out.println();
        System.out.println("End of execution for problem " + problem.getClass().getName() + ".");
        System.out.println("Total time (seconds): " + allExecutionTime / 1000);
        System.out.println("Writing results.");

        problem.removeDominateds(allRuns);
        problem.removeDuplicates(allRuns);

        allRuns.printVariablesToFile(outputDir + "VAR.txt");
        allRuns.printObjectivesToFile(outputDir + "FUN.txt");

    }

    private static File createDir(String dir) {

        File file = new File(dir);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }
}
