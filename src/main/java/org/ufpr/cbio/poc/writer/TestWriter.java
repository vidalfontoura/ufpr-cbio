/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.writer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ufpr.cbio.poc.ga.GeneticAlgorithm;
import org.ufpr.cbio.poc.ga.Individue;

/**
 *
 *
 * @author user
 */
public class TestWriter {

    public static void main(String[] args) throws IOException {

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        OutputCSVWriter csvWriter = new OutputCSVWriter();
        List<Map<Integer, List<Individue>>> data = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            data.add(geneticAlgorithm.run());
            System.out.println();
        }
        csvWriter.writeOutputCSV("100_residuos_5000_geracoes_ponto_cross_over_50.csv", data);
        System.out.println("Finished");

    }

}
