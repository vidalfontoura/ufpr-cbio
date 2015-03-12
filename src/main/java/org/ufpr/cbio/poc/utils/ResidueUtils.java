/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.domain.ResidueType;

/**
 *
 *
 * @author vfontoura
 */
public class ResidueUtils {

    private static int[] FIXED_SOLUTION = new int[] { 1, 0, 1, 2, 1, 2, 2, 3, 3, 0 };

    public static List<Residue> parseChainSequence(String chain) {

        int chainLength = chain.length();
        int x = 0, y = 0, minX = 0, minY = 0;
        List<Residue> residues = new ArrayList<>();
        Residue initialResidue = null;
        for (int i = 0; i < chainLength; i++) {
            if (i == 0) {
                initialResidue =
                    new Residue(createInitialPoint(chainLength), ResidueType.valueOf(String.valueOf(chain.charAt(i))));
                residues.add(initialResidue);
                x = (int) initialResidue.getPoint().getX();
                y = (int) initialResidue.getPoint().getY();
                minX = x;
                minY = y;
            } else {
                int solution = FIXED_SOLUTION[i - 1];
                switch (solution) {
                // 0 - LEFT
                    case 0:
                        y++;
                        break;
                    // 1 - FORWARD
                    case 1:
                        x++;
                        break;
                    // 2 - RIGHT
                    case 2:
                        y--;
                        break;
                    case 3:
                        x--;
                        break;
                    default:
                        // TODO: exception
                        break;
                }
                if (x < minX) {
                    minX = x;
                }
                if (y < minY) {
                    minY = y;
                }
                residues.add(new Residue(new Point(x, y), ResidueType.valueOf(String.valueOf(chain.charAt(i)))));
            }

        }
        Stream<Object> map = residues.stream().map(r -> r.setPoint(new Point()));
        return residues;
    }

    public static Point createInitialPoint(int max) {

        Random random = new Random();
        return new Point(random.nextInt(max), random.nextInt(max));
    }
}
