package org.ufpr.cbio.poc.domain;

import java.util.List;

public class Protein {

	private String chain;
	private List<Residue> residues = null;
	private Grid grid = null;
	private Integer energy = 0;
	private Integer minX = Integer.MAX_VALUE;
	private Integer minY = Integer.MAX_VALUE;
	private Integer maxX = 0;
	private Integer maxY = 0;
	
	/**
	 * Empty Construtor
	 */
	public Protein() {}
	
	/**
	 * Construtor
	 * @param residues The list of Residues
	 * @param grid The Grid structure of the Protein
	 * @param energy The value of the energy
	 */
	public Protein(String chain, List<Residue> residues) {
		super();
		this.chain = chain;
		this.residues = residues;
	}
	
	public String getChain() {
		return chain;
	}
	
	public void setChain(String chain) {
		this.chain = chain;
	}
	
	public List<Residue> getResidues() {
		return residues;
	}
	public void setResidues(List<Residue> residues) {
		this.residues = residues;
	}
	public Grid getGrid() {
		return grid;
	}
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	public Integer getEnergy() {
		return energy;
	}
	public void setEnergy(Integer energy) {
		this.energy = energy;
	}

	public Integer getMinX() {
		return minX;
	}

	public void setMinX(Integer minX) {
		this.minX = minX;
	}

	public Integer getMinY() {
		return minY;
	}

	public void setMinY(Integer minY) {
		this.minY = minY;
	}

	public Integer getMaxX() {
		return maxX;
	}

	public void setMaxX(Integer maxX) {
		this.maxX = maxX;
	}

	public Integer getMaxY() {
		return maxY;
	}

	public void setMaxY(Integer maxY) {
		this.maxY = maxY;
	}
}
