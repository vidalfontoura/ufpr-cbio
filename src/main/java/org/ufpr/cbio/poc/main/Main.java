/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.main;

import java.util.ArrayList;
import java.util.List;

import org.ufpr.cbio.poc.domain.GeneticAlgorithm;
import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Protein;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.domain.Residue.Point;
import org.ufpr.cbio.poc.utils.Controller;
import org.ufpr.cbio.poc.utils.EnumMovements;
import org.ufpr.cbio.poc.utils.Movements;
import org.ufpr.cbio.poc.view.Visualization;

/**
 *
 *
 * @author user
 */
public class Main {

    public static void main(String[] args) {

        Controller c = new Controller();
        Protein protein = new Protein();
        
//        protein = c.parseInput("HHHHHH", new Integer[]{0, 2, 2, 0});//TOP1
//        protein = c.parseInput("PHHPHPPP", new Integer[]{2, 1, 2, 2, 0, 0});//TOP2
//        protein = c.parseInput("HPPPPPPPPPPPPPPPPPPPPPPPPH");//verificar
//        protein = c.parseInput("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
        protein = c.parseInput("HPHPPHPHHHPHPHPHHPHPPHHPPH", new Integer[]{0,2,2,0,1,1,2,1,2,0,2,2,0,0,1,1,0,2,2,1,0,0,2,0});
//        protein = c.parseInput("HHHHHHHHH", new Integer[]{1, 2, 1, 2, 1, 2, 2});
        
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(0,1));
        points.add(new Point(1,1));
        points.add(new Point(1,0));
        points.add(new Point(2,0));
//        protein = c.parseInput("HHHHH", points);
        
        Grid g = c.generateGrid(protein.getResidues());
        
        //VISUALIZAÇÃO
//        Visualization v = new Visualization();
//        v.showStructure(protein);
////        
//        Integer[] mov = new Integer[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
//        Controller.executeMovements(protein, mov);
        
        GeneticAlgorithm ga = new GeneticAlgorithm(6, 10, 1, 1, 1);
        ga.executeAlgorithm();
    }

}
