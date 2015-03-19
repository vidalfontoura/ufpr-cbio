package org.ufpr.cbio.poc.utils;

import java.util.List;

import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Residue;

public class Movements {
	
	public static void doMovement(Residue residue, List<Residue> residues, Grid grid, EnumMovements movement) {
		switch(movement) {
			case ROTATE_90_CLOCKWISE:
				rotate90Clockwise(residue, residues, grid);
				break;
			case ROTATE_180_CLOCKWISE:
				rotate180Clockwise(residue, residues, grid);
				break;
			case CORNER:
				break;
			case NAO_LEMBRO:
				break;
			default:
				break;
		}
	}
	
	private static void rotate90Clockwise(Residue residue, List<Residue> residues, Grid grid) {
		
		int x, y;
		int[][] matrix;
		
		x = residue.getPoint().getX();
		y = residue.getPoint().getY();
		
		matrix = grid.getMatrix();
		
		grid.printResidueStructure();//remover
		
		if(residue != residues.get(0) && residue != residues.get(residues.size()-1))
			return;
		
		//CASO 1 X1 < X2
		if(x+1 < matrix.length && isNeighbor(matrix, x, y, x+1, y)) {
			if(matrix[y+1][x+1] == -1) {
				matrix[y+1][x+1] = matrix[y][x];
				matrix[y][x] = -1;
				residue.getPoint().setX(x+1);
				residue.getPoint().setY(y+1);
			}
		}
		//CASO 2 Y1 > Y2
		else if(y-1 >= 0 && isNeighbor(matrix, x, y, x, y-1)) {
			if(matrix[y-1][x+1] == -1) {
				matrix[y-1][x+1] = matrix[y][x];
				matrix[y][x] = -1;
				residue.getPoint().setX(x+1);
				residue.getPoint().setY(y-1);
			}
		}
		//CASO 3 X1 > X2
		else if(x-1 >= 0 && isNeighbor(matrix, x, y, x-1, y)) {
			if(matrix[y-1][x-1] == -1) {
				matrix[y-1][x-1] = matrix[y][x];
				matrix[y][x] = -1;
				residue.getPoint().setX(x-1);
				residue.getPoint().setY(y-1);
			}
		}
		//CASO 4 Y1 < Y2
		else if(y+1 < matrix.length && isNeighbor(matrix, x, y, x, y+1)) {
			if(matrix[y+1][x-1] == -1) {
				matrix[y+1][x-1] = matrix[y][x];
				matrix[y][x] = -1;
				residue.getPoint().setX(x-1);
				residue.getPoint().setY(y+1);
			}
		}
		
		grid.setMatrix(matrix);
		
		grid.printResidueStructure();//remover
	}

	private static void rotate180Clockwise(Residue residue, List<Residue> residues, Grid grid) {
		
		int x, y;
		int[][] matrix;
		
		x = residue.getPoint().getX();
		y = residue.getPoint().getY();
		
		matrix = grid.getMatrix();
		
		grid.printResidueStructure();//remover
		
		if(residue != residues.get(0) && residue != residues.get(residues.size()-1))
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
		
		grid.setMatrix(matrix);
		
		grid.printResidueStructure();//remover
	}
	
	private static boolean isNeighbor(int[][] matrix, int x1, int y1, int x2, int y2) {
		return ((matrix[y1][x1] == matrix[y2][x2]-1 || matrix[y1][x1] == matrix[y2][x2]+1) && matrix[y2][x2] != -1) ? true : false;
	}
}
