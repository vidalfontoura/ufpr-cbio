/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.main;

import java.util.List;
import java.util.Set;

import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.domain.TopologyContact;
import org.ufpr.cbio.poc.utils.Controller;
import org.ufpr.cbio.poc.utils.ResidueUtils;

/**
 *
 *
 * @author user
 */
public class Main {

    private static final String CHAIN_SEQUENCE = "HHHPHPPPPPH";

    // private static final String CHAIN_SEQUENCE = "PPHPHHHHHHP";

    public static void main(String[] args) {

//        List<Residue> residues = ResidueUtils.parseChainSequence(CHAIN_SEQUENCE);
//
//        for (Residue residue : residues) {
//            System.out.println("[" + residue.getPoint().getX() + "," + residue.getPoint().getY() + "]");
//        }
//
//        Grid grid = new Grid(CHAIN_SEQUENCE.length(), CHAIN_SEQUENCE.length());
//
//        grid.buildResidueStructure(residues);
//        grid.printResidueStructure();
//
//        Set<TopologyContact> topologyContacts = ResidueUtils.getTopologyContacts(residues, grid);
//        System.out.println(topologyContacts.size());
//        for (TopologyContact topologyContact : topologyContacts) {
//            System.out.print("r1[" + topologyContact.getR1().getPoint().getX() + ","
//                + topologyContact.getR1().getPoint().getY() + "]");
//            System.out.println("r2[" + topologyContact.getR2().getPoint().getX() + ","
//                + topologyContact.getR2().getPoint().getY() + "]");
//        }
        
        Controller c = new Controller();
        System.out.println(c.parseInput("HHHPHPPPPPH").size());
    }

}
