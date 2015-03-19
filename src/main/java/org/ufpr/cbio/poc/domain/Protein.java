package org.ufpr.cbio.poc.domain;

import java.util.List;

public class Protein {

	private String chain;
	private List<Residue> residues = null;
	private Grid structure = null;
	private Integer energy = 0;
	
	/**
	 * Empty Construtor
	 */
	public Protein() {}
	
	/**
	 * Construtor
	 * @param residues The list of Residues
	 * @param structure The Grid structure of the Protein
	 * @param energy The value of the energy
	 */
	public Protein(String chain, List<Residue> residues, Grid structure, Integer energy) {
		super();
		this.chain = chain;
		this.residues = residues;
		this.structure = structure;
		this.energy = energy;
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
	public Grid getStructure() {
		return structure;
	}
	public void setStructure(Grid structure) {
		this.structure = structure;
	}
	public Integer getEnergy() {
		return energy;
	}
	public void setEnergy(Integer energy) {
		this.energy = energy;
	}
}
