/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.main;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.domain.ResidueType;
import org.ufpr.cbio.poc.domain.TopologyContact;
import org.ufpr.cbio.poc.utils.ResidueUtils;

/**
 *
 *
 * @author user
 */
public class Main {

    private static final String CHAIN_SEQUENCE = "HHHPHPPPPPH";

    public static void main(String[] args) {

        List<Residue> residues = ResidueUtils.parseChainSequence(CHAIN_SEQUENCE);

        for (Residue residue : residues) {
            System.out.println("[" + residue.getPoint().getX() + "," + residue.getPoint().getY() + "]");
        }

        Grid grid = new Grid(CHAIN_SEQUENCE.length(), CHAIN_SEQUENCE.length());

        int[][] matrix = grid.buildResidueStructure(residues);
        Set<TopologyContact> topologyContacts = new HashSet<>();

        int index = 0;
        for (int i = 0; i < residues.size(); i++) {
            if (residues.get(i).getResidueType().equals(ResidueType.P)) {
                continue;
            }
            if (residues.get(i).getPoint().y + 1 < matrix.length) {
                index = matrix[residues.get(i).getPoint().y + 1][residues.get(i).getPoint().x];
                // test up
                if (isTopologicalContact(i, index, residues)) {
                    topologyContacts.add(new TopologyContact(residues.get(i), residues.get(index)));
                }
            }
            if (residues.get(i).getPoint().x + 1 < matrix.length) {
                // test right
                index = matrix[residues.get(i).getPoint().y][residues.get(i).getPoint().x + 1];
                if (isTopologicalContact(i, index, residues)) {
                    topologyContacts.add(new TopologyContact(residues.get(i), residues.get(index)));
                }
            }
            if (residues.get(i).getPoint().y - 1 >= 0) {
                // test down
                index = matrix[residues.get(i).getPoint().y - 1][residues.get(i).getPoint().x];
                if (isTopologicalContact(i, index, residues)) {
                    topologyContacts.add(new TopologyContact(residues.get(i), residues.get(index)));
                }
            }
            if (residues.get(i).getPoint().x - 1 >= 0) {
                // test back
                index = matrix[residues.get(i).getPoint().y][residues.get(i).getPoint().x - 1];
                if (isTopologicalContact(i, index, residues)) {
                    topologyContacts.add(new TopologyContact(residues.get(i), residues.get(index)));
                }
            }

        }

        topologyContacts = removeDuplicates(topologyContacts);
        System.out.println(topologyContacts.size());
        for (TopologyContact topologyContact : topologyContacts) {
            System.out.print("r1[" + topologyContact.getR1().getPoint().getX() + ","
                + topologyContact.getR1().getPoint().getY() + "]");
            System.out.println("r2[" + topologyContact.getR2().getPoint().getX() + ","
                + topologyContact.getR2().getPoint().getY() + "]");
        }

    }

    public static boolean isTopologicalContact(int i, int index, List<Residue> residues) {

        if (i != index + 1 && i != index - 1 && index != -1) {

            return residues.get(index).getResidueType().equals(ResidueType.H);
        }
        return false;
    }

    public static Set<TopologyContact> removeDuplicates(Set<TopologyContact> topologyContacts) {

        Set<TopologyContact> contacts = new HashSet<>();
        for (TopologyContact topologyContact : topologyContacts) {
            for (TopologyContact topologyContact2 : topologyContacts) {
                if (topologyContact.getR1().getPoint().getX() != topologyContact2.getR2().getPoint().getX()
                    && topologyContact.getR1().getPoint().getY() != topologyContact2.getR2().getPoint().getY()) {
                    contacts.add(topologyContact);
                }
            }
        }
        return contacts;
    }
}
