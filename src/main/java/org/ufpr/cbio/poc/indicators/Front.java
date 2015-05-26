package org.ufpr.cbio.poc.indicators;

import jmetal.core.SolutionSet;

public class Front implements Comparable<Front> {

    private SolutionSet solutionSet;
    private double hypervolume;
    private String algorithm;

    private double hypervolume2;

    public double getHypervolume2() {

        return hypervolume2;
    }

    public void setHypervolume2(double hypervolume2) {

        this.hypervolume2 = hypervolume2;
    }

    @Override
    public int compareTo(Front o) {

        return Double.compare(this.hypervolume, o.getHypervolume());
    }

    public SolutionSet getSolutionSet() {

        return solutionSet;
    }

    public void setSolutionSet(SolutionSet solutionSet) {

        this.solutionSet = solutionSet;
    }

    public double getHypervolume() {

        return hypervolume;
    }

    public void setHypervolume(double hypervolume) {

        this.hypervolume = hypervolume;
    }

    public String getAlgorithm() {

        return algorithm;
    }

    public void setAlgorithm(String algorithm) {

        this.algorithm = algorithm;
    }

}
