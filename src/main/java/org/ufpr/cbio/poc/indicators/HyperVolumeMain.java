package org.ufpr.cbio.poc.indicators;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.util.MetricsUtil;

import org.ufpr.cbio.poc.indicators.statistics.KruskalWallisTest;

public class HyperVolumeMain {

    public static void main(String[] args) throws IOException, InterruptedException {

        String EXPERIMENTS_DIR = "psp_experiments/bkp_experiments_report_problem_with_eval";
        String NSGAIIBaseDir = EXPERIMENTS_DIR + File.separator + "NSGAII" + File.separator;
        // String NSGAIIResultsPath = NSGAIIBaseDir + "FUN.txt";
        String SPEA2BaseDir = EXPERIMENTS_DIR + File.separator + "SPEA2" + File.separator;
        // String SPEA2ResultsPath = SPEA2BaseDir + "FUN.txt";
        String IBEABaseDir = EXPERIMENTS_DIR + File.separator + "IBEA" + File.separator;
        // String IBEAResultsPath = IBEABaseDir + "/FUN.txt";
        String RandomBaseDir = EXPERIMENTS_DIR + File.separator + "Random" + File.separator;
        // String RandomResultsPath = RandomBaseDir + "/FUN.txt";

        MetricsUtil metricsUtil = new MetricsUtil();
        HypervolumeHandler hypervolumeHandler = new HypervolumeHandler();

        File nsgaIIResultsDir = new File(NSGAIIBaseDir);
        File spea2ResultsDir = new File(SPEA2BaseDir);
        File ibeaResultsDir = new File(IBEABaseDir);
        File RandomResultsDir = new File(RandomBaseDir);

        File[] nsgaIIExecutions = nsgaIIResultsDir.listFiles();
        for (int i = 0; i < nsgaIIExecutions.length; i++) {
            File file = nsgaIIExecutions[i];
            if (file.exists() && file.isDirectory()) {
                SolutionSet nsgaIIFront =
                    metricsUtil.readNonDominatedSolutionSet(file.getPath() + File.separator + "FUN.txt");
                hypervolumeHandler.addParetoFront(nsgaIIFront);

            }

        }

        File[] IBEAExecutions = ibeaResultsDir.listFiles();
        for (int i = 0; i < IBEAExecutions.length; i++) {
            File file = IBEAExecutions[i];
            if (file.exists() && file.isDirectory()) {
                SolutionSet ibeaFront =
                    metricsUtil.readNonDominatedSolutionSet(file.getPath() + File.separator + "FUN.txt");
                hypervolumeHandler.addParetoFront(ibeaFront);
            }
        }

        File[] speaExecutions = spea2ResultsDir.listFiles();
        for (int i = 0; i < speaExecutions.length; i++) {
            File file = speaExecutions[i];
            if (file.exists() && file.isDirectory()) {

                SolutionSet spea2Front =
                    metricsUtil.readNonDominatedSolutionSet(file.getPath() + File.separator + "FUN.txt");
                hypervolumeHandler.addParetoFront(spea2Front);
            }
        }

        File[] randomExecution = RandomResultsDir.listFiles();
        for (int i = 0; i < randomExecution.length; i++) {
            File file = randomExecution[i];
            if (file.exists() && file.isDirectory()) {

                SolutionSet randomFront =
                    metricsUtil.readNonDominatedSolutionSet(file.getPath() + File.separator + "FUN.txt");
                hypervolumeHandler.addParetoFront(randomFront);
            }
        }

        List<Front> nsgaIIFronts = new ArrayList<Front>();
        for (int i = 0; i < nsgaIIExecutions.length; i++) {
            File file = nsgaIIExecutions[i];
            if (file.exists() && file.isDirectory()) {
                SolutionSet nsgaIIFront =
                    metricsUtil.readNonDominatedSolutionSet(file.getPath() + File.separator + "FUN.txt");
                Front front = new Front();
                front.setHypervolume(hypervolumeHandler.calculateHypervolume(nsgaIIFront, 2));

                front.setSolutionSet(nsgaIIFront);
                front.setAlgorithm("NSGAII");
                nsgaIIFronts.add(front);
            }

        }

        List<Front> ibeaFronts = new ArrayList<Front>();
        for (int i = 0; i < IBEAExecutions.length; i++) {
            File file = IBEAExecutions[i];
            if (file.exists() && file.isDirectory()) {
                SolutionSet ibeaFront =
                    metricsUtil.readNonDominatedSolutionSet(file.getPath() + File.separator + "FUN.txt");

                Front front = new Front();
                front.setHypervolume(hypervolumeHandler.calculateHypervolume(ibeaFront, 2));

                front.setSolutionSet(ibeaFront);
                front.setAlgorithm("IBEA");
                ibeaFronts.add(front);
            }
        }

        List<Front> spea2Fronts = new ArrayList<Front>();
        for (int i = 0; i < speaExecutions.length; i++) {
            File file = speaExecutions[i];
            if (file.exists() && file.isDirectory()) {

                SolutionSet spea2front =
                    metricsUtil.readNonDominatedSolutionSet(file.getPath() + File.separator + "FUN.txt");

                Front front = new Front();
                front.setHypervolume(hypervolumeHandler.calculateHypervolume(spea2front, 2));

                front.setSolutionSet(spea2front);
                front.setAlgorithm("SPEA2");
                spea2Fronts.add(front);
            }
        }

        List<Front> randomSearchFronts = new ArrayList<Front>();
        for (int i = 0; i < randomExecution.length; i++) {
            File file = randomExecution[i];
            if (file.exists() && file.isDirectory()) {
                SolutionSet randomSearchFront =
                    metricsUtil.readNonDominatedSolutionSet(file.getPath() + File.separator + "FUN.txt");
                Front front = new Front();
                front.setHypervolume(hypervolumeHandler.calculateHypervolume(randomSearchFront, 2));

                front.setSolutionSet(randomSearchFront);
                front.setAlgorithm("Random");
                randomSearchFronts.add(front);
            }

        }

        List<Front> allFronts = new ArrayList<Front>();
        allFronts.addAll(nsgaIIFronts);
        allFronts.addAll(ibeaFronts);
        allFronts.addAll(spea2Fronts);
        allFronts.addAll(randomSearchFronts);

        Collections.sort(allFronts, (Front f1, Front f2) -> Double.compare(f2.getHypervolume(), f1.getHypervolume()));
        for (Front front : allFronts) {
            System.out.print("Hypervolume: " + front.getHypervolume());
            System.out.println(" Hypervolume2: " + front.getHypervolume2());
            System.out.print(" Algorithm: " + front.getAlgorithm());
            System.out.print(" Solution: " + front.getSolutionSet());
            System.out.println();
        }

        DoubleSummaryStatistics nsgaIIStatistics =
            nsgaIIFronts.stream().map(Front::getHypervolume).mapToDouble(a -> a).summaryStatistics();
        DoubleSummaryStatistics ibeaStatistics =
            ibeaFronts.stream().map(Front::getHypervolume).mapToDouble(a -> a).summaryStatistics();
        DoubleSummaryStatistics spea2Statistics =
            spea2Fronts.stream().map(Front::getHypervolume).mapToDouble(a -> a).summaryStatistics();
        DoubleSummaryStatistics randomSearchStatistics =
            randomSearchFronts.stream().map(Front::getHypervolume).mapToDouble(a -> a).summaryStatistics();

        double[] nsgaIIHVs = nsgaIIFronts.stream().map(Front::getHypervolume).mapToDouble(a -> a).toArray();
        double[] ibeaHVs = ibeaFronts.stream().map(Front::getHypervolume).mapToDouble(a -> a).toArray();
        double[] spea2HVs = spea2Fronts.stream().map(Front::getHypervolume).mapToDouble(a -> a).toArray();
        double[] randomHVs = randomSearchFronts.stream().map(Front::getHypervolume).mapToDouble(a -> a).toArray();

        double nsgaIIHVAvg = nsgaIIStatistics.getAverage();
        double ibeaAvg = ibeaStatistics.getAverage();
        double spea2Avg = spea2Statistics.getAverage();
        double randomAvg = randomSearchStatistics.getAverage();

        double nsgaIIHVMax = nsgaIIStatistics.getMax();
        double ibeaMax = ibeaStatistics.getMax();
        double spea2Max = spea2Statistics.getMax();
        double randomMax = randomSearchStatistics.getMax();

        double nsgaIIHVMin = nsgaIIStatistics.getMin();
        double ibeaMin = ibeaStatistics.getMin();
        double spea2Min = spea2Statistics.getMin();
        double randomMin = randomSearchStatistics.getMin();

        double stdDevNsgaII = findDeviation(nsgaIIHVs, nsgaIIHVAvg);
        double stdDevIbea = findDeviation(ibeaHVs, ibeaAvg);
        double stdDevSpea = findDeviation(spea2HVs, spea2Avg);
        double stdDevRandom = findDeviation(randomHVs, randomAvg);

        System.out.println("Hypervolume Averages: ");
        System.out.println("NSGA2 avg: " + nsgaIIHVAvg + " stddev: " + stdDevNsgaII + " max:" + nsgaIIHVMax + " min: "
            + nsgaIIHVMin);
        System.out.println("IBEA avg: " + ibeaAvg + " stddev: " + stdDevIbea + " max: " + ibeaMax + "min: " + ibeaMin);
        System.out.println("SPEA avg: " + spea2Avg + " stddev: " + stdDevSpea + " max: " + spea2Max + "min: "
            + spea2Min);
        System.out.println("Random avg: " + randomAvg + " stddev: " + stdDevRandom + " max " + randomMax + "min:"
            + randomMin);

        HashMap<String, double[]> values = new HashMap<String, double[]>();
        values.put("NSGAII", nsgaIIHVs);
        values.put("SPEA2", spea2HVs);
        values.put("IBEA", ibeaHVs);
        values.put("Random", randomHVs);

        HashMap<String, HashMap<String, Boolean>> kruskalWallisTest = KruskalWallisTest.test(values);

        Boolean boolean0 = kruskalWallisTest.get("NSGAII").get("NSGAII");
        System.out.println(boolean0);

        Boolean boolean2 = kruskalWallisTest.get("NSGAII").get("IBEA");
        System.out.println(boolean2);

        Boolean boolean1 = kruskalWallisTest.get("NSGAII").get("SPEA2");
        System.out.println(boolean1);

        Boolean boolean3 = kruskalWallisTest.get("NSGAII").get("Random");
        System.out.println(boolean3);

        // boolean0 = kruskalWallisTest.get("SPEA2").get("SPEA2");
        // System.out.println(boolean0);

        // boolean2 = kruskalWallisTest.get("SPEA2").get("NSGAII");
        // System.out.println(boolean2);

        // boolean1 = kruskalWallisTest.get("SPEA2").get("IBEA");
        // System.out.println(boolean1);
        //
        // boolean3 = kruskalWallisTest.get("SPEA2").get("Random");
        // System.out.println(boolean3);

        boolean0 = kruskalWallisTest.get("IBEA").get("IBEA");
        System.out.println(boolean0);

        boolean2 = kruskalWallisTest.get("IBEA").get("NSGAII");
        System.out.println(boolean2);

        // boolean1 = kruskalWallisTest.get("IBEA").get("SPEA2");
        // System.out.println(boolean1);

        boolean3 = kruskalWallisTest.get("IBEA").get("Random");
        System.out.println(boolean3);

    }

    public static double findDeviation(double[] nums, double mean) {

        double squareSum = 0;

        for (int i = 0; i < nums.length; i++) {
            squareSum += Math.pow(nums[i] - mean, 2);
        }

        return Math.sqrt((squareSum) / (nums.length - 1));
    }
}
