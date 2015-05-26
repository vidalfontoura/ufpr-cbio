package org.ufpr.cbio.poc.operators.custom;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import jmetal.core.Solution;
import jmetal.encodings.solutionType.IntSolutionType;
import jmetal.operators.crossover.Crossover;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;

public class UniformCrossover extends Crossover {

    /** EPS defines the minimum difference allowed between real values */
    private static final double EPS = 1.0e-14;

    private double distributionIndex;
    private double crossoverProbability;

    private static final List VALID_TYPES = Arrays.asList(IntSolutionType.class);

    // private JMetalRandom randomGenerator;

    /** Constructor */
    public UniformCrossover(HashMap<String, Object> parameters) {

        super(parameters);
        if (parameters.get("probability") != null)
            crossoverProbability = (Double) parameters.get("probability");
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
    /**
     * public Solution[] doCrossover(double probability, Solution parent1,
     * Solution parent2) throws JMException {
     * 
     * double lowerBound = parent1.getDecisionVariables()[0].getLowerBound();
     * double upperBound = parent1.getDecisionVariables()[0].getUpperBound();
     * 
     * Solution solution1 = copySolution(parent1); Solution solution2 =
     * copySolution(parent2); if (PseudoRandom.randDouble() < probability) {
     * Variable[] parentVariables1 = parent1.getDecisionVariables(); Variable[]
     * parentVariables2 = parent2.getDecisionVariables();
     * 
     * Variable[] childVariables1 = new Variable[parentVariables1.length];
     * Variable[] childVariables2 = new Variable[parentVariables1.length]; for
     * (int i = 0; i < parentVariables1.length; i++) { int rand =
     * PseudoRandom.randInt((int) lowerBound, (int) upperBound);
     * childVariables1[i] = (rand == 0) ? parentVariables1[i] :
     * parentVariables2[i]; childVariables2[i] = (rand == 0) ?
     * parentVariables2[i] : parentVariables1[i]; }
     * solution1.setDecisionVariables(childVariables1);
     * solution2.setDecisionVariables(childVariables2); } return new Solution[]
     * { solution1, solution2 }; }
     * 
     * @throws JMException
     */

    public Solution[] doCrossover(double probability, Solution parent1, Solution parent2) throws JMException {

        Solution[] offSpring = new Solution[2];
        offSpring[0] = new Solution(parent1);
        offSpring[1] = new Solution(parent2);

        int valueX1;
        int valueX2;
        for (int i = 0; i < parent1.numberOfVariables(); i++) {
            valueX1 = (int) parent1.getDecisionVariables()[i].getValue();
            valueX2 = (int) parent2.getDecisionVariables()[i].getValue();
            offSpring[0].getDecisionVariables()[i]
                .setValue(PseudoRandom.randDouble() < probability ? valueX2 : valueX1);
            offSpring[1].getDecisionVariables()[i]
                .setValue(PseudoRandom.randDouble() < probability ? valueX1 : valueX2);
        } // for

        return offSpring;
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
