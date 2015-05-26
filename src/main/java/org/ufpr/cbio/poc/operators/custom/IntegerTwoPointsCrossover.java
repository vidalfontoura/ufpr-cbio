package org.ufpr.cbio.poc.operators.custom;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.encodings.solutionType.IntSolutionType;
import jmetal.operators.crossover.Crossover;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;

public class IntegerTwoPointsCrossover extends Crossover {

    /**
     * Valid solution types to apply this operator
     */
    private static final List VALID_TYPES = Arrays.asList(IntSolutionType.class);

    private Double crossoverProbability_ = null;

    /**
     * Constructor Creates a new intance of the two point crossover operator
     */
    public IntegerTwoPointsCrossover(HashMap<String, Object> parameters) {

        super(parameters);

        if (parameters.get("probability") != null)
            crossoverProbability_ = (Double) parameters.get("probability");
    } // TwoPointsCrossover

    /**
     * Constructor
     * 
     * @param A properties containing the Operator parameters Creates a new
     *        intance of the two point crossover operator
     */
    // public TwoPointsCrossover(Properties properties) {
    // this();
    // }

    /**
     * Perform the crossover operation
     * 
     * @param probability Crossover probability
     * @param parent1 The first parent
     * @param parent2 The second parent
     * @return Two offspring solutions
     * @throws JMException
     */
    public Solution[] doCrossover(double probability, Solution parent1, Solution parent2) throws JMException {

        int point1, point2;

        // Ensure that the two points are different p1 < p2
        // point1 = RANDOM.nextInt(INDIVIDUE_LENGHT - 1);
        //
        // do {
        // point2 = RANDOM.nextInt(INDIVIDUE_LENGHT);
        // } while (point1 > point2);
        //
        // int[] newIndividue1 = new int[INDIVIDUE_LENGHT];
        // int[] newIndividue2 = new int[INDIVIDUE_LENGHT];
        //
        // for (int i = 0; i < newIndividue1.length; i++) {
        // newIndividue1[i] = (i < point1 || i > point2) ? individue1[i] :
        // individue2[i];
        // newIndividue2[i] = (i < point1 || i > point2) ? individue2[i] :
        // individue1[i];
        // }
        //
        // return new int[][] { newIndividue1, newIndividue2 };

        Solution[] offspring = new Solution[2];

        offspring[0] = new Solution(parent1);
        offspring[1] = new Solution(parent2);

        if (PseudoRandom.randDouble() < probability) {
            int crosspoint1;
            int crosspoint2;
            Variable[] parent1Vector;
            Variable[] parent2Vector;
            Variable[] offspring1Vector;
            Variable[] offspring2Vector;

            parent1Vector = parent1.getDecisionVariables();
            parent2Vector = parent2.getDecisionVariables();
            offspring1Vector = offspring[0].getDecisionVariables();
            offspring2Vector = offspring[1].getDecisionVariables();

            // STEP 1: Get two cutting points
            crosspoint1 = PseudoRandom.randInt(0, parent1.getDecisionVariables().length - 1);
            crosspoint2 = PseudoRandom.randInt(0, parent2.getDecisionVariables().length - 1);

            while (crosspoint2 == crosspoint1)
                crosspoint2 = PseudoRandom.randInt(0, parent1.getDecisionVariables().length - 1);

            if (crosspoint1 > crosspoint2) {
                int swap;
                swap = crosspoint1;
                crosspoint1 = crosspoint2;
                crosspoint2 = swap;
            } // if

            // STEP 2: Obtain the first child
            int m = 0;
            for (int j = 0; j < parent1.getDecisionVariables().length; j++) {
                Variable variable1 =
                    j < crosspoint1 || j > crosspoint2 ? parent1.getDecisionVariables()[j] : parent2
                        .getDecisionVariables()[j];

                Variable variable2 =
                    j < crosspoint1 || j > crosspoint2 ? parent2.getDecisionVariables()[j] : parent1
                        .getDecisionVariables()[j];

                offspring1Vector[j].setValue(variable1.getValue());
                offspring2Vector[j].setValue(variable2.getValue());

            } // for

        } // if

        return offspring;
    } // makeCrossover

    /**
     * Executes the operation
     * 
     * @param object An object containing an array of two solutions
     * @return An object containing an array with the offSprings
     * @throws JMException
     */
    public Object execute(Object object) throws JMException {

        Solution[] parents = (Solution[]) object;
        Double crossoverProbability;

        if (!(VALID_TYPES.contains(parents[0].getType().getClass()) && VALID_TYPES.contains(parents[1].getType()
            .getClass()))) {

            Configuration.logger_.severe("TwoPointsCrossover.execute: the solutions "
                + "are not of the right type. The type should be 'Permutation', but " + parents[0].getType() + " and "
                + parents[1].getType() + " are obtained");
        } // if

        crossoverProbability = (Double) getParameter("probability");

        if (parents.length < 2) {
            Configuration.logger_.severe("TwoPointsCrossover.execute: operator needs two " + "parents");
            Class cls = java.lang.String.class;
            String name = cls.getName();
            throw new JMException("Exception in " + name + ".execute()");
        }

        Solution[] offspring = doCrossover(crossoverProbability_, parents[0], parents[1]);

        return offspring;
    } // execute
}
