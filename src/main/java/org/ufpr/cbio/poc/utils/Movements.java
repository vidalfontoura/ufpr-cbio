package org.ufpr.cbio.poc.utils;

import java.util.List;

import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Protein;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.view.Visualization;

public class Movements {
	
	public static void doMovement(Residue residue, Protein protein, EnumMovements movement) {
		switch(movement) {
			case ROTATE_90_CLOCKWISE:
				rotate90Clockwise(residue, protein);
				break;
			case ROTATE_180_CLOCKWISE:
				rotate180Clockwise(residue, protein);
				break;
			case CORNER:
				//Vidal
				break;
			case CRANKSHAFT:
				crankShaft(residue, protein);
				break;
			default:
				break;
		}
	}
	
	private static void rotate90Clockwise(Residue residue, Protein protein) {
		
		int x, y;
		int[][] matrix;
		
		x = residue.getPoint().getX();
		y = residue.getPoint().getY();
		
		matrix = Controller.generateGrid(protein.getResidues()).getMatrix();
		
		if(residue != protein.getResidues().get(0) && residue != protein.getResidues().get(protein.getResidues().size()-1))
			return;
		
		//CASO 1 X1 < X2
		if(x+1 < matrix.length && isNeighbor(matrix, x, y, x+1, y)) {
			if(matrix[y+1][x+1] == -1) {
				//matrix[y+1][x+1] = matrix[y][x];
				//matrix[y][x] = -1;
				residue.getPoint().setX(x+1);
				residue.getPoint().setY(y+1);
			}
		}
		//CASO 2 Y1 > Y2
		else if(y-1 >= 0 && isNeighbor(matrix, x, y, x, y-1)) {
			if(matrix[y-1][x+1] == -1) {
				//matrix[y-1][x+1] = matrix[y][x];
				//matrix[y][x] = -1;
				residue.getPoint().setX(x+1);
				residue.getPoint().setY(y-1);
			}
		}
		//CASO 3 X1 > X2
		else if(x-1 >= 0 && isNeighbor(matrix, x, y, x-1, y)) {
			if(matrix[y-1][x-1] == -1) {
				//matrix[y-1][x-1] = matrix[y][x];
				//matrix[y][x] = -1;
				residue.getPoint().setX(x-1);
				residue.getPoint().setY(y-1);
			}
		}
		//CASO 4 Y1 < Y2
		else if(y+1 < matrix.length && isNeighbor(matrix, x, y, x, y+1)) {
			if(matrix[y+1][x-1] == -1) {
				//matrix[y+1][x-1] = matrix[y][x];
				//matrix[y][x] = -1;
				residue.getPoint().setX(x-1);
				residue.getPoint().setY(y+1);
			}
		}
	}

	private static void rotate180Clockwise(Residue residue, Protein protein) {
		
		int x, y;
		int[][] matrix;
		
		x = residue.getPoint().getX();
		y = residue.getPoint().getY();
		
		matrix = protein.getGrid().getMatrix();
		
		if(residue != protein.getResidues().get(0) && residue != protein.getResidues().get(protein.getResidues().size()-1))
			return;
		
		//CASO 1 X1 < X2
		if(x+1 < matrix.length && isNeighbor(matrix, x, y, x+1, y)) {
			if(matrix[y][x+2] == -1) {
				matrix[y][x+2] = matrix[y][x];
				matrix[y][x] = -1;
				residue.getPoint().setX(x+2);
			}
		}
		//CASO 2 Y1 > Y2
		else if(y-1 >= 0 && isNeighbor(matrix, x, y, x, y-1)) {
			if(matrix[y-2][x] == -1) {
				matrix[y-2][x] = matrix[y][x];
				matrix[y][x] = -1;
				residue.getPoint().setY(y-2);
			}
		}
		//CASO 3 X1 > X2
		else if(x-1 >= 0 && isNeighbor(matrix, x, y, x-1, y)) {
			if(matrix[y][x-2] == -1) {
				matrix[y][x-2] = matrix[y][x];
				matrix[y][x] = -1;
				residue.getPoint().setX(x-2);
			}
		}
		//CASO 4 Y1 < Y2
		else if(y+1 < matrix.length && isNeighbor(matrix, x, y, x, y+1)) {
			if(matrix[y+2][x] == -1) {
				matrix[y+2][x] = matrix[y][x];
				matrix[y][x] = -1;
				residue.getPoint().setY(y+2);
			}
		}
	}
	
	private static void corner(Residue residue, List<Residue> residues, Grid grid) {}
	
	private static void crankShaft(Residue residue, Protein protein) {
		
		int x, y, index;
		int[][] matrix;
		
		x = residue.getPoint().getX();
		y = residue.getPoint().getY();
		
		index = protein.getResidues().indexOf(residue);
		
		matrix = protein.getGrid().getMatrix();
		
		//TOP
		if( ( residue.getPoint().getY() == protein.getResidues().get(index+1).getPoint().getY() ) && 
				( protein.getResidues().get(index-1).getPoint().getY() == protein.getResidues().get(index+2).getPoint().getY() ) ) {

			System.out.println("A");
				
			if(matrix[y-2][x] == -1 && (matrix[y-2][x+1] == -1 || matrix[y-2][x-1] == -1)) {
				if(isNeighbor(matrix, x, y, x+1, y)) {
					matrix[y-2][x+1] = matrix[y][x+1];
					matrix[y][x+1] = -1;
				} else {
					matrix[y-2][x-1] = matrix[y][x-1];
					matrix[y][x-1] = -1;
				}
				matrix[y-2][x] = matrix[y][x];
				matrix[y][x] = -1;
				residue.getPoint().setY(y-2);
				protein.getResidues().get(index+1).getPoint().setY(y-2);
			}
		}
	}
	
	private static boolean isNeighbor(int[][] matrix, int x1, int y1, int x2, int y2) {
		return ((matrix[y1][x1] == matrix[y2][x2]-1 || matrix[y1][x1] == matrix[y2][x2]+1) && matrix[y2][x2] != -1) ? true : false;
	}
}
