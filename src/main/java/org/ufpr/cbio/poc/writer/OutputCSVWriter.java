/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.writer;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.ufpr.cbio.poc.ga.Individue;

/**
 *
 *
 * @author user
 */
public class OutputCSVWriter {

    public void writeOutputCSV(String fileName, List<Map<Integer, List<Individue>>> data) throws IOException {

        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {

            StringBuilder sb = new StringBuilder();
            writer.writeNext(sb.toString().split(","));

            int maxColumn = Integer.MIN_VALUE;
            int minColumn = Integer.MAX_VALUE;
            for (Map<Integer, List<Individue>> d : data) {
                for (Integer key : d.keySet()) {
                    if (maxColumn < key) {
                        maxColumn = key;
                    }
                    if (minColumn > key) {
                        minColumn = key;
                    }

                }
            }
            sb = new StringBuilder(",");
            for (int i = minColumn; i <= maxColumn; i++) {
                sb.append(i + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
            writer.writeNext(sb.toString().split(","));

            for (int i = 0; i < data.size(); i++) {

                Map<Integer, List<Individue>> map = data.get(i);
                sb = new StringBuilder();
                sb.append("Test " + i + ",");
                for (Integer value : map.keySet()) {
                    int size = map.get(value).size();
                    sb.append(size);
                    sb.append(",");

                }
                sb.deleteCharAt(sb.length() - 1);
                writer.writeNext(sb.toString().split(","));

            }
        }
    }
}
