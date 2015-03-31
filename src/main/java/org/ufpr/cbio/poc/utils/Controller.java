package org.ufpr.cbio.poc.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Protein;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.domain.ResidueType;
import org.ufpr.cbio.poc.domain.Residue.Point;
import org.ufpr.cbio.poc.view.Visualization;

public class Controller{
	
	
	/*
	 * Parse a Chain into a Protein Object. The basic solution is generated.
	 * @param String chain. The Chain of Residues H/P.
	 */
    public Protein parseInput(String chain) {
    	return executeParse(chain, createBasicSolution(chain.length()));
    }
    
    /*
	 * Parse a Chain into a Protein Object.
	 * @param String chain. The Chain of Residues H/P.
	 * @param Integer[] solution. The basic solution in the form of relative representation.
	 */
	public Protein parseInput(String chain, Integer[] solution) {
		return executeParse(chain, solution);
	}
	
	/*
	 * Parse a Chain into a Protein Object.
	 * @param String chain. The Chain of Residues H/P.
	 * @param List<Points> points. The basic solution in the form of absolute representation.
	 */
	public Protein parseInput(String chain, List<Point> points) {
		
		Protein protein = new Protein();
		List<Residue> residues = null;
		
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = 0;
		int maxY = 0;
		
		if(chain.length() != points.size())
			return null;
		
		residues = new ArrayList<Residue>();
		
		for (int i=0; i < points.size(); i++) {
			residues.add(new Residue(points.get(i), ResidueType.valueOf(String.valueOf(chain.charAt(i)))));
			if(points.get(i).getX() < minX) minX = points.get(i).getX();
			if(points.get(i).getY() < minY) minY = points.get(i).getY();
			if(points.get(i).getX() > maxX) maxX = points.get(i).getX();
			if(points.get(i).getY() > maxY) maxY = points.get(i).getY();
		}
		
		protein.setResidues(residues);
		protein.setMinX(minX);
		protein.setMinY(minY);
		protein.setMaxX(maxX);
		protein.setMaxY(maxY);
		fixPositions(protein, minX, minY);
		protein.setGrid(generateGrid(residues));
		return protein;
	}
	
	private Protein executeParse(String chain, Integer[] solution) {
		
		Protein protein = new Protein();
		
		int x = 0, y = 0, direction = 1;
		
		
		
        List<Residue> residues = new ArrayList<>();

        //Adiciona ponto inicial em (0,0) 1 Residuo
        residues.add(new Residue(new Point(x, y), ResidueType.valueOf(String.valueOf(chain.charAt(0)))));
        
        //Se tem 2 ou mais Residuos
        if(chain.length() >= 2 && solution.length >= chain.length()-2) {
        	
        	int minX = residues.get(0).getPoint().getX();
        	int minY = residues.get(0).getPoint().getY();
        	int maxX = 0;
        	int maxY = 0;
        	
        	x++;
        	residues.add(new Residue(new Point(x, y), ResidueType.valueOf(String.valueOf(chain.charAt(1)))));
        	
	        for (int i = 0; i < chain.length()-2; i++) {
	        	int step = solution[i];
	            switch(direction) {
		            case 0://LOOKING UP
		            	switch(step) {
		            		case 0:
		            			x--;
		            			direction = 3;
		            			break;
		            		case 1:
		            			y++;
		            			direction = 0;
		            			break;
		            		case 2:
		            			x++;
		            			direction = 1;
		            			break;
	            			default:
	            				//TODO Exception movimento invalido
	            				break;
		            	}
		            	break;
		            case 1://LOOKING FORWARD
		            	switch(step) {
		            		case 0:
		            			y++;
		            			direction = 0;
		            			break;
		            		case 1:
		            			x++;
		            			direction = 1;
		            			break;
		            		case 2:
		            			y--;
		            			direction = 2;
		            			break;
		        			default:
		        				//TODO Exception movimento inválido
		        				break;
		            	}
		            	break;
		            case 2://LOOKING DOWN
		            	switch(step) {
	            		case 0:
	            			x++;
	            			direction = 1;
	            			break;
	            		case 1:
	            			y--;
	            			direction = 2;
	            			break;
	            		case 2:
	            			x--;
	            			direction = 3;
	            			break;
	        			default:
	        				//TODO Exception movimento inválido
	        				break;
		            	}
		            	break;
		            case 3://LOOKING BACK
		            	switch(step) {
	            		case 0:
	            			y--;
	            			direction = 2;
	            			break;
	            		case 1:
	            			x--;
	            			direction = 3;
	            			break;
	            		case 2:
	            			y++;
	            			direction = 0;
	            			break;
	        			default:
	        				//TODO Exception movimento inválido
	        				break;
		            	}
		            	break;
		            default:
		            	break;
	            }
	            if (x < minX) minX = x;
	            if (y < minY) minY = y;
	            if (x > maxX) maxX = x;
	            if (y > maxY) maxY = y;
	            
	            residues.add(new Residue(new Point(x, y), ResidueType.valueOf(String.valueOf(chain.charAt(i+2)))));
	        }
	        
	        protein.setResidues(residues);
			protein.setMinX(minX);
			protein.setMinY(minY);
			protein.setMaxX(maxX);
			protein.setMaxY(maxY);
			fixPositions(protein, minX, minY);
			protein.setGrid(generateGrid(residues));
        }
		return protein;
	}
    
    public Integer[] createBasicSolution(int chainlength) {
    	Integer solution[] = new Integer[chainlength-2];
    	for (int i = 0; i < solution.length; i++) {
			solution[i] = 1;
		}
    	return solution;
    }    
    
    public static Grid generateGrid(List<Residue> residues) {
    	Grid g = new Grid(residues.size(), residues.size());
    	for (int i = 0; i < residues.size(); i++) {
            g.getMatrix()[residues.get(i).getPoint().y][residues.get(i).getPoint().x] = i;
        }
    	return g;
    }

    public Integer evaluateEnergy(List<Residue> residues, Grid grid) {
    	return 0;
    }
    
    public static void fixPositions(Protein protein, int minX, int minY) {
    	for (Residue residue : protein.getResidues()) {
            residue.setPoint(new Residue.Point(residue.getPoint().x - minX + 1, residue.getPoint().y - minY + 1));
        }
    }
    
    public static void fixPositions(Protein protein) {
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = 0;
		int maxY = 0;
		for (Residue residue : protein.getResidues()) {
			if(residue.getPoint().getX() < minX) minX = residue.getPoint().getX();
			if(residue.getPoint().getY() < minY) minY = residue.getPoint().getY();
			if(residue.getPoint().getX() > maxX) maxX = residue.getPoint().getX();
			if(residue.getPoint().getY() > maxY) maxY = residue.getPoint().getY();
        }
		
		for (Residue residue : protein.getResidues()) {
            residue.setPoint(new Residue.Point(residue.getPoint().x - minX + 1, residue.getPoint().y - minY + 1));
        }
		protein.setMaxX(maxX);
		protein.setMaxY(maxY);
		protein.setMinX(minX);
		protein.setMinY(minY);
    }

    public static void executeMovements(Protein protein, Integer[] movements) {
    	
    	int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = 0;
		int maxY = 0;
    	
    	if(protein.getResidues().size() != movements.length)
    		return;
    	
    	for (int i = 0; i < protein.getResidues().size(); i++) {
    		
    		Residue r = protein.getResidues().get(i);
    		
    		try {
				Thread.sleep(500);
				Visualization.update();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		System.out.println("MOVIMENTO: "+i);
    		
    		EnumMovements m;
    		if(i == 0 || i == protein.getResidues().size()-1)
    			m = (movements[i] == 0) ? EnumMovements.ROTATE_90_CLOCKWISE : EnumMovements.ROTATE_180_CLOCKWISE;
    		else
    			m = (movements[i] == 0) ? EnumMovements.CORNER : EnumMovements.CRANKSHAFT;
			
    		Movements.doMovement(r, protein, m);
    	}
    	
    	for (int i = 0; i < protein.getResidues().size(); i++) {
    		Residue r = protein.getResidues().get(i);
    		if(r.getPoint().getX() < minX) minX = r.getPoint().getX();
			if(r.getPoint().getY() < minY) minY = r.getPoint().getY();
			if(r.getPoint().getX() > maxX) maxX = r.getPoint().getX();
			if(r.getPoint().getY() > maxY) maxY = r.getPoint().getY();
		}
    	
    	protein.setMaxX(maxX);
		protein.setMaxY(maxY);
		protein.setMinX(minX);
		protein.setMinY(minY);
    	fixPositions(protein, minX, minY);
        protein.setGrid(generateGrid(protein.getResidues()));
        Visualization.update();
    }
}
