package org.ufpr.cbio.poc.domain;

import java.util.Random;

public class GeneticAlgorithm {

	//Par�metros
	private int INDIVIDUE_LENGHT;
	private int POPULATION_SIZE;
	private int GENERATIONS;
	private int MUTATION;
	private int CROSSOVER;
	
	private int K = 0;
	
	private int[][] POPULATION;
	private int[] FITNESS;
	
	public GeneticAlgorithm(int INDIVIDUE_LENGHT, int POPULATION_SIZE, int GENERATIONS, int MUTATION, int CROSSOVER) {
		super();
		this.INDIVIDUE_LENGHT = INDIVIDUE_LENGHT;
		this.POPULATION_SIZE = POPULATION_SIZE;
		this.GENERATIONS = GENERATIONS;
		this.MUTATION = MUTATION;
		this.CROSSOVER = CROSSOVER;
	}
	
	public GeneticAlgorithm() {}
	
	public int getK() {
		return K;
	}

	public void setK(int k) {
		K = k;
	}

	public int getINDIVIDUE_LENGHT() {
		return INDIVIDUE_LENGHT;
	}

	public int getPOPULATION_SIZE() {
		return POPULATION_SIZE;
	}

	public int getGENERATIONS() {
		return GENERATIONS;
	}

	public int getMUTATION() {
		return MUTATION;
	}

	public int getCROSSOVER() {
		return CROSSOVER;
	}

	public int[] executeAlgorithm() {
		
		//Inicializa��o
		generateInitialPopulation();
		
		//Avalia��o inicial
		calculatePopulationFitness();
		
		//Gera��es
		for (int i = 0; i < GENERATIONS; i++) {
			
			//Sele��o
			for (int j = 0; j < 1; j++) {
				
				int[] newIndividue = null;
				int fitness = 0;
				
				//Cruzamento
				newIndividue = crossover(POPULATION[j]);
				
				//Muta��o
				newIndividue = mutation(newIndividue);
				
				//Avalia��o do novo indiv�duo
				fitness = calculateIndividueFitness(newIndividue);
				
				//Compara��o
				POPULATION[j] = selection(j, POPULATION[j], newIndividue);
			}
		}
		
		return getBestIndividue();
	}

	private void generateInitialPopulation() {
		POPULATION = new int[POPULATION_SIZE][INDIVIDUE_LENGHT];
		for (int i = 0; i < POPULATION_SIZE; i++) {
			for (int j = 0; j < INDIVIDUE_LENGHT; j++) {
				POPULATION[i][j] = new Random().nextInt(2);
			}
		}
	}
	
	private int calculateIndividueFitness(int[] individue) {
		return new Random().nextInt(11)*-1; //TODO juntar com o calculo de energia.
	}
	
	private void calculatePopulationFitness() {
		FITNESS = new int[POPULATION_SIZE];
		for (int i = 0; i < POPULATION_SIZE; i++) {
			FITNESS[i] = calculateIndividueFitness(POPULATION[i]);
		}
	}

	private int[] crossover(int[] individue) {
		// TODO Faz o crossover passado como par�metro
		switch(CROSSOVER) {
			case 1:
				return onePointCrossover(individue);
			case 2:
				return twoPointCrossover(individue);
			case 3:
				return uniformCrossover(individue);
			default:
				return null;
		}
	}
	
	private int[] onePointCrossover(int[] individue) {
		int[] aux;
		
		do {
			aux = POPULATION[new Random().nextInt(POPULATION_SIZE)];
		} while(aux == individue);
		
		int[] newIndividue = new int[INDIVIDUE_LENGHT];
		
		for (int i = 0; i < INDIVIDUE_LENGHT; i++) {
			newIndividue[i] = (i < (INDIVIDUE_LENGHT/2)) ? individue[i] : aux[i];
		}
		
		return newIndividue;
	}
	
	private int[] twoPointCrossover(int[] individue) {
		int point1, point2;
		int[] aux;
		
		//garante que pelo menos haja 2 pontos diferentes sendo p1 < p2
		point1 = new Random().nextInt(INDIVIDUE_LENGHT-1);
		
		do {
			point2 = new Random().nextInt(INDIVIDUE_LENGHT);
		} while(point1 <= point2);
		
		do {
			aux = POPULATION[new Random().nextInt(POPULATION_SIZE)];
		} while(aux == individue);
		
		int[] newIndividue = new int[INDIVIDUE_LENGHT];
		
		for (int i = 0; i < newIndividue.length; i++) {
			newIndividue[i] = (i < point1 || i > point2) ? individue[i] : aux[i];
		}
		
		return newIndividue;
	}
	
	private int[] uniformCrossover(int[] individue) {
		int[] aux;
		
		do {
			aux = POPULATION[new Random().nextInt(POPULATION_SIZE)];
		} while(aux == individue);
		
		int[] newIndividue = new int[INDIVIDUE_LENGHT];
		
		for (int i = 0; i < newIndividue.length; i++) {
			int rand = new Random().nextInt(2);
			newIndividue[i] = (rand == 0) ? individue[i] : aux[i];
		}
		
		return newIndividue;
	}
	
	private int[] mutation(int[] individue) {
		// TODO Faz a muta��o passada como par�metro
		switch(MUTATION) {
			case 1:
				return bitStringMutation(individue);
			case 2:
				return flipBitMutation(individue);
			case 3:
				return orderChangingMutation(individue);
			default:
				//TODO exception valor inv�lido
				return null;
		}
	}
	
	private int[] bitStringMutation(int[] individue) {
		for (int i = 0; i < K; i++) {
			int pos = new Random().nextInt(INDIVIDUE_LENGHT);
			individue[pos] = (individue[pos] == 0) ? 1 : 0;
		}
		return individue;
	}
	
	private int[] flipBitMutation(int[] individue) {
		for (int i = 0; i < individue.length; i++) {
			individue[i] = (individue[i] == 0) ? 1 : 0;
		}
		return individue;
	}
	
	private int[] orderChangingMutation(int[] individue) {
		int pos1, pos2;
		pos1 = new Random().nextInt(INDIVIDUE_LENGHT);
		
		do {
			pos2 = new Random().nextInt(INDIVIDUE_LENGHT);
		} while(pos2 == pos1);
		
		int aux = individue[pos1];
		individue[pos1] = individue[pos2];
		individue[pos2] = aux;
		
		return individue;
	}
	
	private int[] selection(int index, int[] oldIndividue, int[] newIndividue) {
		//TODO Compara o antigo e o novo individuo e retorna o melhor
		
		int oldFit = calculateIndividueFitness(oldIndividue);
		int newFit = calculateIndividueFitness(newIndividue);
		
		if(newFit > oldFit) {
			FITNESS[index] = newFit;
			return newIndividue;
		}
		
		return oldIndividue;		
	}

	private int[] getBestIndividue() {
		int bestIndex = 0;
		int[] best = POPULATION[0];
		
		for (int i = 1; i < POPULATION_SIZE; i++) {
			
			if (FITNESS[i] > FITNESS[bestIndex]) {
				best = POPULATION[i];
				bestIndex = i;
			}
		}	
		return best;
	}
	
	public void printIndividue(int[] individue) {
		for (int i = 0; i < individue.length; i++) {
			System.out.print(individue[i]+" ");
		}
		System.out.println();
	}
}
