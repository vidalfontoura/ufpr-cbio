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

            for (int j = 0; j < data.size(); j++) {
                sb = new StringBuilder();
                sb.append("Seed " + j + ",");
                Map<Integer, List<Individue>> map = data.get(j);
                for (int i = minColumn; i <= maxColumn; i++) {
                    List<Individue> list = map.get(i);
                    int size = 0;
                    if (list != null) {
                        size = list.size();
                    }
                    sb.append(size);
                    sb.append(",");

                }
                sb.deleteCharAt(sb.length() - 1);
                writer.writeNext(sb.toString().split(","));

            }
        }
    }

    public void writeOutputToCSV(String fileName, String[] newLine) throws IOException {

        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName, true))) {
            writer.writeNext(newLine);
        }
    }
}
