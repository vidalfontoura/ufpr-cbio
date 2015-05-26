package org.ufpr.cbio.poc.indicators;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.Hypervolume;
import jmetal.qualityIndicator.util.MetricsUtil;

public class HypervolumeHandler {

    private SolutionSet population;
    private final MetricsUtil metricUtil;
    private final Hypervolume hypervolume;

    public HypervolumeHandler() {

        this.population = new SolutionSet();
        this.metricUtil = new MetricsUtil();
        this.hypervolume = new Hypervolume();
    }

    public void addParetoFront(SolutionSet front) {

        population = population.union(front);
    }

    public void addParetoFront(String path) {

        addParetoFront(metricUtil.readNonDominatedSolutionSet(path));
    }

    public void clear() {

        this.population = new SolutionSet();
    }

    public double calculateHypervolume(String frontPath, int numberOfObjectives) {

        return calculateHypervolume(metricUtil.readNonDominatedSolutionSet(frontPath), numberOfObjectives);
    }

    public double calculateHypervolume(SolutionSet front, int numberOfObjectives) {

        if (population.size() != 0) {
            double[][] referencePoint = getReferencePoint(numberOfObjectives);
            double[] maximumValues =
                metricUtil.getMaximumValues(population.writeObjectivesToMatrix(), numberOfObjectives);
            double[] minimumValues =
                metricUtil.getMinimumValues(population.writeObjectivesToMatrix(), numberOfObjectives);
            double[][] objectives =
                metricUtil.getNormalizedFront(front.writeObjectivesToMatrix(), maximumValues, minimumValues);
            return hypervolume.hypervolume(objectives, referencePoint, 2);
        }
        return 0D;
    }

    public double[] getMaximumValues() {

        return metricUtil.getMaximumValues(population.writeObjectivesToMatrix(), 2);
    }

    public double[] getMinimumValues() {

        return metricUtil.getMinimumValues(population.writeObjectivesToMatrix(), 2);
    }

    private double[][] getReferencePoint(int numberOfObjectives) {

        double[][] referencePoint = new double[numberOfObjectives][numberOfObjectives];
        for (int i = 0; i < referencePoint.length; i++) {
            double[] objective = referencePoint[i];
            objective[i] = 1.01;
            for (int j = 0; j < objective.length; j++) {
                if (i != j) {
                    objective[j] = 0;
                }
            }
        }
        return referencePoint;
    }
}
