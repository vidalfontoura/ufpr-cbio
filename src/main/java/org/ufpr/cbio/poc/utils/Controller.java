package org.ufpr.cbio.poc.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.domain.ResidueType;
import org.ufpr.cbio.poc.domain.Residue.Point;

public class Controller implements ControllerInterface{

    private static Integer[] FIXED_SOLUTION = new Integer[] { 1, 0, 1, 2, 1, 2, 2, 3, 3, 0 };
	
    public List<Residue> parseInput(String chain) {
    	return executeParse(chain, FIXED_SOLUTION);
    }
    	
	public List<Residue> parseInput(String chain, Integer[] solution) {
		return executeParse(chain, solution);
	}
	
	private List<Residue> executeParse(String chain, Integer[] solution) {
		
		int chainLength = chain.length();
		
		int x = 0, y = 0, minX = 0, minY = 0;
		
        List<Residue> residues = new ArrayList<>();
        
        Residue initialResidue = null;
        
        initialResidue = new Residue(createInitialPoint(chainLength), ResidueType.valueOf(String.valueOf(chain.charAt(0))));
        
        for (int i = 0; i < chainLength; i++) {
            if (i == 0) {
                residues.add(initialResidue);
                x = initialResidue.getPoint().getX();
                y = initialResidue.getPoint().getY();
                minX = x;
                minY = y;
            } else {
            	//TODO
                int step = solution[i - 1];
                switch (step) {
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
        for (Residue residue : residues) {
            residue.setPoint(new Residue.Point(residue.getPoint().x - minX, residue.getPoint().y - minY));
        }
		
		return residues;
	}

	
	public List<Residue> parseInput(String chain, List<Residue.Point> points) {
		return null;
	}
	
    public static Residue.Point createInitialPoint(int max) {
        Random random = new Random();
        return new Residue.Point(random.nextInt(max), random.nextInt(max));
    }
}
