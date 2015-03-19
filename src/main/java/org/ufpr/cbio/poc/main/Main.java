/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.main;

import java.util.List;

import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.utils.Controller;
import org.ufpr.cbio.poc.utils.EnumMovements;
import org.ufpr.cbio.poc.utils.Movements;

/**
 *
 *
 * @author user
 */
public class Main {

    public static void main(String[] args) {

        Controller c = new Controller();
        
        List<Residue> list = c.parseInput("HHHHH");
        Grid g = c.generateGrid(list);
        
//        Integer e = c.evaluateEnergy(list, g);
        
        Movements.doMovement(list.get(0), list, g, EnumMovements.ROTATE_90_CLOCKWISE);
        
        Movements.doMovement(list.get(0), list, g, EnumMovements.ROTATE_180_CLOCKWISE);
    }

}
