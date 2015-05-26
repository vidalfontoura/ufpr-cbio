package org.ufpr.cbio.poc.indicators;

import java.io.File;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.util.MetricsUtil;

public class HyperVolumeCalculate {

    public static void main(String[] args) {

        String NSGAIIBaseDir = "experiments" + File.separator + "NSGAII" + File.separator;
        String NSGAIIResultsPath = NSGAIIBaseDir + "FUN.txt";
        String SPEA2BaseDir = "experiments" + File.separator + "SPEA2" + File.separator;
        String SPEA2ResultsPath = SPEA2BaseDir + "FUN.txt";
        String IBEABaseDir = "experiments" + File.separator + "IBEA";
        String IBEAResultsPath = IBEABaseDir + "/FUN.txt";

        MetricsUtil metricsUtil = new MetricsUtil();

        SolutionSet nsgaIIKnownFront = metricsUtil.readNonDominatedSolutionSet(NSGAIIResultsPath);
        SolutionSet SPEA2KnownFront = metricsUtil.readNonDominatedSolutionSet(SPEA2ResultsPath);
        SolutionSet IBEAKnownFront = metricsUtil.readNonDominatedSolutionSet(IBEAResultsPath);

        File nsgaIIResultsDir = new File(NSGAIIBaseDir);
        File spea2ResultsDir = new File(SPEA2BaseDir);
        File ibeaResultsDir = new File(IBEABaseDir);

        File[] nsgaIIExecutions = nsgaIIResultsDir.listFiles();
        double maxValueObjective0 = Double.NEGATIVE_INFINITY;
        double maxValueObjective1 = Double.MIN_VALUE;
        double minValueObjective0 = Double.POSITIVE_INFINITY;
        double minValueObjective1 = Double.MAX_VALUE;
        for (int i = 0; i < nsgaIIExecutions.length; i++) {
            File file = nsgaIIExecutions[i];
            if (file.exists() && file.isDirectory()) {
                SolutionSet nsgaIIFront =
                    metricsUtil.readNonDominatedSolutionSet(file.getPath() + File.separator + "FUN.txt");

                double bestObjective0 =
                    nsgaIIFront.best(
                        (Object s1, Object s2) -> Double.compare(((Solution) s1).getObjective(0),
                            ((Solution) s2).getObjective(0))).getObjective(0);

                double bestObjective1 =
                    nsgaIIFront.best(
                        (Object s1, Object s2) -> Double.compare(((Solution) s1).getObjective(1),
                            ((Solution) s2).getObjective(1))).getObjective(1);

                double worstObjective0 =
                    nsgaIIFront.best(
                        (Object s1, Object s2) -> Double.compare(((Solution) s2).getObjective(0),
                            ((Solution) s1).getObjective(0))).getObjective(0);

                double worstObjective1 =
                    nsgaIIFront.best(
                        (Object s1, Object s2) -> Double.compare(((Solution) s2).getObjective(1),
                            ((Solution) s1).getObjective(1))).getObjective(1);

                if (maxValueObjective0 < worstObjective0) {
                    maxValueObjective0 = worstObjective0;
                }

                if (maxValueObjective1 < worstObjective1) {
                    maxValueObjective1 = worstObjective1;
                }

                if (minValueObjective0 > bestObjective0) {
                    minValueObjective0 = bestObjective0;
                }

                if (minValueObjective1 > bestObjective1) {
                    minValueObjective1 = bestObjective1;
                }

            }

        }

        File[] IBEAExecutions = ibeaResultsDir.listFiles();
        for (int i = 0; i < IBEAExecutions.length; i++) {
            File file = IBEAExecutions[i];
            if (file.exists() && file.isDirectory()) {
                SolutionSet ibeaFront =
                    metricsUtil.readNonDominatedSolutionSet(file.getPath() + File.separator + "FUN.txt");

                double bestObjective0 =
                    ibeaFront.best(
                        (Object s1, Object s2) -> Double.compare(((Solution) s1).getObjective(0),
                            ((Solution) s2).getObjective(0))).getObjective(0);

                double bestObjective1 =
                    ibeaFront.best(
                        (Object s1, Object s2) -> Double.compare(((Solution) s1).getObjective(1),
                            ((Solution) s2).getObjective(1))).getObjective(1);

                double worstObjective0 =
                    ibeaFront.best(
                        (Object s1, Object s2) -> Double.compare(((Solution) s2).getObjective(0),
                            ((Solution) s1).getObjective(0))).getObjective(0);

                double worstObjective1 =
                    ibeaFront.best(
                        (Object s1, Object s2) -> Double.compare(((Solution) s2).getObjective(1),
                            ((Solution) s1).getObjective(1))).getObjective(1);

                if (maxValueObjective0 < worstObjective0) {
                    maxValueObjective0 = worstObjective0;
                }

                if (maxValueObjective1 < worstObjective1) {
                    maxValueObjective1 = worstObjective1;
                }

                if (minValueObjective0 > bestObjective0) {
                    minValueObjective0 = bestObjective0;
                }

                if (minValueObjective1 > bestObjective1) {
                    minValueObjective1 = bestObjective1;
                }

            }
        }

        File[] speaExecutions = spea2ResultsDir.listFiles();
        for (int i = 0; i < speaExecutions.length; i++) {
            File file = speaExecutions[i];
            if (file.exists() && file.isDirectory()) {
                SolutionSet speaFront =
                    metricsUtil.readNonDominatedSolutionSet(file.getPath() + File.separator + "FUN.txt");

                double bestObjective0 =
                    speaFront.best(
                        (Object s1, Object s2) -> Double.compare(((Solution) s1).getObjective(0),
                            ((Solution) s2).getObjective(0))).getObjective(0);

                double bestObjective1 =
                    speaFront.best(
                        (Object s1, Object s2) -> Double.compare(((Solution) s1).getObjective(1),
                            ((Solution) s2).getObjective(1))).getObjective(1);

                double worstObjective0 =
                    speaFront.best(
                        (Object s1, Object s2) -> Double.compare(((Solution) s2).getObjective(0),
                            ((Solution) s1).getObjective(0))).getObjective(0);

                double worstObjective1 =
                    speaFront.best(
                        (Object s1, Object s2) -> Double.compare(((Solution) s2).getObjective(1),
                            ((Solution) s1).getObjective(1))).getObjective(1);

                if (maxValueObjective0 < worstObjective0) {
                    maxValueObjective0 = worstObjective0;
                }

                if (maxValueObjective1 < worstObjective1) {
                    maxValueObjective1 = worstObjective1;
                }

                if (minValueObjective0 > bestObjective0) {
                    minValueObjective0 = bestObjective0;
                }

                if (minValueObjective1 > bestObjective1) {
                    minValueObjective1 = bestObjective1;
                }

            }
        }

        double[] maxValues = new double[] { maxValueObjective0, maxValueObjective1 };
        double[] minValues = new double[] { minValueObjective0, minValueObjective1 };
        for (int i = 0; i < maxValues.length; i++) {
            System.out.println(maxValues[i]);
        }

        for (int i = 0; i < maxValues.length; i++) {
            System.out.println(minValues[i]);
        }

        // System.out.println("NSGAII normalized");
        // normalizeFront(nsgaIIKnownFront, maxValues, minValues);
        // for (int i = 0; i < nsgaIIKnownFront.size(); i++) {
        // System.out.println(nsgaIIKnownFront.get(i).getObjective(0));
        // System.out.println(nsgaIIKnownFront.get(i).getObjective(1));
        // }
        //
        // System.out.println("IBEA normalized");
        // normalizeFront(IBEAKnownFront, maxValues, minValues);
        //
        // for (int i = 0; i < IBEAKnownFront.size(); i++) {
        // System.out.println(IBEAKnownFront.get(i).getObjective(0));
        // System.out.println(IBEAKnownFront.get(i).getObjective(1));
        // }
        //
        // System.out.println("SPEA2 normalized");
        // normalizeFront(SPEA2KnownFront, maxValues, minValues);
        //
        // for (int i = 0; i < SPEA2KnownFront.size(); i++) {
        // System.out.println(SPEA2KnownFront.get(i).getObjective(0));
        // System.out.println(SPEA2KnownFront.get(i).getObjective(1));
        // }

    }

    public static void normalizeFront(SolutionSet solutionSet, double[] maxValues, double[] minValues) {

        for (int i = 0; i < solutionSet.size(); i++) {
            double value1 = solutionSet.get(i).getObjective(0);
            double value2 = solutionSet.get(i).getObjective(1);

            double normalizedValue1 = (value1 - minValues[0]) / (maxValues[0] - minValues[0]);
            double normalizedValue2 = (value2 - minValues[1]) / (maxValues[1] - minValues[1]);

            solutionSet.get(i).setObjective(0, normalizedValue1);
            solutionSet.get(i).setObjective(1, normalizedValue2);
        }
    }
}
