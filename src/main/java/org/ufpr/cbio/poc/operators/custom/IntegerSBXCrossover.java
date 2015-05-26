package org.ufpr.cbio.poc.operators.custom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import jmetal.core.Solution;
import jmetal.encodings.solutionType.IntSolutionType;
import jmetal.operators.crossover.Crossover;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;

public class IntegerSBXCrossover extends Crossover {

    /** EPS defines the minimum difference allowed between real values */
    private static final double EPS = 1.0e-14;

    private double distributionIndex;
    private double crossoverProbability;

    private static final List VALID_TYPES = Arrays.asList(IntSolutionType.class);

    // private JMetalRandom randomGenerator;

    /** Constructor */
    public IntegerSBXCrossover(HashMap<String, Object> parameters) {

        super(parameters);
    }

    /* Getters */
    public double getCrossoverProbability() {

        return crossoverProbability;
    }

    public double getDistributionIndex() {

        return distributionIndex;
    }

    // /** Execute() method */
    // @Override
    // public List<IntegerSolution> execute(List<IntegerSolution> solutions) {
    //
    // if (null == solutions) {
    // throw new JMetalException("Null parameter");
    // } else if (solutions.size() != 2) {
    // throw new JMetalException("There must be two parents instead of " +
    // solutions.size());
    // }
    //
    // return doCrossover(crossoverProbability, solutions.get(0),
    // solutions.get(1));
    // }

    /**
     * doCrossover method
     * 
     * @throws JMException
     */
    public Solution[] doCrossover(double probability, Solution parent1, Solution parent2) throws JMException {

        Solution[] solutions = new Solution[2];
        List<Solution> offspring = new ArrayList<Solution>(2);

        offspring.add(copySolution(parent1));
        offspring.add(copySolution(parent2));

        int i;
        double rand;
        double y1, y2, yL, yu;
        double c1, c2;
        double alpha, beta, betaq;
        int valueX1, valueX2;

        if (PseudoRandom.randDouble() <= probability) {
            for (i = 0; i < parent1.getDecisionVariables().length; i++) {
                valueX1 = (int) parent1.getDecisionVariables()[i].getValue();
                valueX2 = (int) parent2.getDecisionVariables()[i].getValue();
                if (PseudoRandom.randDouble() <= 0.5) {
                    if (Math.abs(valueX1 - valueX2) > EPS) {

                        if (valueX1 < valueX2) {
                            y1 = valueX1;
                            y2 = valueX2;
                        } else {
                            y1 = valueX2;
                            y2 = valueX1;
                        }

                        yL = parent1.getDecisionVariables()[i].getLowerBound();
                        yu = parent1.getDecisionVariables()[i].getUpperBound();
                        rand = PseudoRandom.randDouble();
                        beta = 1.0 + (2.0 * (y1 - yL) / (y2 - y1));
                        alpha = 2.0 - Math.pow(beta, -(distributionIndex + 1.0));

                        if (rand <= (1.0 / alpha)) {
                            betaq = Math.pow((rand * alpha), (1.0 / (distributionIndex + 1.0)));
                        } else {
                            betaq = Math.pow(1.0 / (2.0 - rand * alpha), 1.0 / (distributionIndex + 1.0));
                        }

                        c1 = 0.5 * ((y1 + y2) - betaq * (y2 - y1));
                        beta = 1.0 + (2.0 * (yu - y2) / (y2 - y1));
                        alpha = 2.0 - Math.pow(beta, -(distributionIndex + 1.0));

                        if (rand <= (1.0 / alpha)) {
                            betaq = Math.pow((rand * alpha), (1.0 / (distributionIndex + 1.0)));
                        } else {
                            betaq = Math.pow(1.0 / (2.0 - rand * alpha), 1.0 / (distributionIndex + 1.0));
                        }

                        c2 = 0.5 * (y1 + y2 + betaq * (y2 - y1));

                        if (c1 < yL) {
                            c1 = yL;
                        }

                        if (c2 < yL) {
                            c2 = yL;
                        }

                        if (c1 > yu) {
                            c1 = yu;
                        }

                        if (c2 > yu) {
                            c2 = yu;
                        }

                        if (PseudoRandom.randDouble() <= 0.5) {
                            offspring.get(0).getDecisionVariables()[i].setValue(c2);
                            offspring.get(1).getDecisionVariables()[i].setValue(c1);
                        } else {
                            offspring.get(0).getDecisionVariables()[i].setValue(c1);
                            offspring.get(1).getDecisionVariables()[i].setValue(c2);
                        }
                    } else {
                        offspring.get(0).getDecisionVariables()[i].setValue(valueX1);
                        offspring.get(1).getDecisionVariables()[i].setValue(valueX2);
                    }
                } else {

                    offspring.get(0).getDecisionVariables()[i].setValue(valueX2);
                    offspring.get(1).getDecisionVariables()[i].setValue(valueX1);
                }
            }
        }

        return offspring.toArray(solutions);
    }

    private Solution copySolution(Solution solution) {

        Solution copySolution = new Solution(solution.getNumberOfObjectives());
        copySolution.setCrowdingDistance(solution.getCrowdingDistance());
        copySolution.setDecisionVariables(solution.getDecisionVariables());
        copySolution.setDistanceToSolutionSet(solution.getDistanceToSolutionSet());
        copySolution.setFitness(solution.getFitness());
        copySolution.setLocation(solution.getLocation());
        copySolution.setNumberOfViolatedConstraint(solution.getNumberOfViolatedConstraint());
        for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
            copySolution.setObjective(i, solution.getObjective(i));
        }
        copySolution.setOverallConstraintViolation(solution.getOverallConstraintViolation());
        copySolution.setRank(solution.getRank());
        copySolution.setType(solution.getType());
        return copySolution;
    }

    @Override
    public Object execute(Object object) throws JMException {

        Solution[] parents = (Solution[]) object;

        if (!(VALID_TYPES.contains(parents[0].getType().getClass()) && VALID_TYPES.contains(parents[1].getType()
            .getClass()))) {

            Configuration.logger_.severe("SinglePointCrossover.execute: the solutions "
                + "are not of the right type. The type should be 'Binary' or 'Int', but " + parents[0].getType()
                + " and " + parents[1].getType() + " are obtained");

            Class cls = java.lang.String.class;
            String name = cls.getName();
            throw new JMException("Exception in " + name + ".execute()");
        } // if

        if (parents.length < 2) {
            Configuration.logger_.severe("SinglePointCrossover.execute: operator " + "needs two parents");
            Class cls = java.lang.String.class;
            String name = cls.getName();
            throw new JMException("Exception in " + name + ".execute()");
        }

        Solution[] offSpring;
        offSpring = doCrossover(crossoverProbability, parents[0], parents[1]);

        // -> Update the offSpring solutions
        for (int i = 0; i < offSpring.length; i++) {
            offSpring[i].setCrowdingDistance(0.0);
            offSpring[i].setRank(0);
        }
        return offSpring;
    }
}
