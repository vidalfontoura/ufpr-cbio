package org.ufpr.cbio.poc.operators;

import java.util.List;

import org.junit.Test;
import org.ufpr.cbio.poc.jmetal.PspProblemRelativeNSGAIII;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.impl.GenericIntegerSolution;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

public class IntegerSBXCrossoverJMetalTest {

	
	@Test
	public void testSBXCrossover() {
		JMetalRandom instance = JMetalRandom.getInstance();
		instance.setSeed(2);
		 SBXCrossover sbxCrossover = new SBXCrossover(0.9, 2.0);
		 
		 IntegerSBXCrossover integerSBXCrossover = new IntegerSBXCrossover(0.9, 2.0);
		 
		 GenericIntegerSolution parent1 = new GenericIntegerSolution(new PspProblemRelativeNSGAIII("aaaaaa"));
		 GenericIntegerSolution parent2 = new GenericIntegerSolution(new PspProblemRelativeNSGAIII("aaaaaa"));
		
		  for (int j = 0; j < parent1.getNumberOfVariables(); j++) {
              System.out.print(parent1.getVariableValue(j)+",");
          }
		  
		  System.out.println();
		  
		  for (int j = 0; j < parent2.getNumberOfVariables(); j++) {
              System.out.print(parent2.getVariableValue(j)+",");
          }
		  
		  System.out.println();
		 
		 List<IntegerSolution> doCrossover = integerSBXCrossover.doCrossover(0.9, parent1, parent2);
		 
		 for(IntegerSolution solution: doCrossover) {
			for(int i = 0; i<solution.getNumberOfVariables(); i++) {
				System.out.print(solution.getVariableValue(i)+",");
			}
			System.out.println();
		 }
		 
		
		 
		
	}
}
