package org.ufpr.cbio.poc.utils;

import java.util.List;

import org.ufpr.cbio.poc.domain.Residue;

public interface ControllerInterface {
	
	/**
	 * Parser that receive a HP chain to generate the grid.
	 * @param chain The HP chain.
	 */
	public List<Residue> parseInput(String chain);
	
	/**
	 * Parser that receive a Array of directions to generate the grid.
	 * @param solution The Array of directions.
	 */
	public List<Residue> parseInput(String chain, Integer[] solution);

	/**
	 * Parser that receive a List of Points to generate the grid.
	 * @param points The list of points.
	 */
	public List<Residue> parseInput(String chain, List<Residue.Point> points);
}
