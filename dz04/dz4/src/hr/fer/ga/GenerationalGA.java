package hr.fer.ga;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.ga.crossover.Crossover;
import hr.fer.ga.mutation.Mutation;
import hr.fer.ga.selection.Selection;

public class GenerationalGA implements GeneticAlgorithm {

	private int populationSize;
	private int numberOfGenerations;
	private Selection selection;
	private Crossover crossover;
	private Mutation mutation;
	private double elitism;
	private List<Value> population;
	private double epsilon;

	public GenerationalGA(int populationSize, int numberOfGenerations, double elitism, Selection selection,
			Crossover crossover, Mutation mutation, double epsilon) {

		population = new ArrayList<Value>();

		this.populationSize = populationSize;
		this.numberOfGenerations = numberOfGenerations;
		this.elitism = elitism;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.epsilon = epsilon;

		initializePopulation();
		Collections.sort(population);
	}

	private void initializePopulation() {
		for (int i = 0; i < populationSize; i++) {
			population.add(new Value());
		}
	}

	public void start() {

		ExecutorService EXEC = Executors.newCachedThreadPool();

		for (int generation = 0; generation < numberOfGenerations; generation++) {

			if (population.get(0).getPenalty() < epsilon)
				break;

			List<Value> newPopulation = new ArrayList<>();
			int i;
			for (i = 0; i < populationSize; i++) {
				if (i < ((int) elitism * populationSize)) {
					newPopulation.add(population.get(i));
				} else {
					int parent1 = selection.select(population);
					int parent2 = selection.select(population);
					Value child = mutation.mutate(crossover.cross(population.get(parent1), population.get(parent2)));
					newPopulation.add(child);
				}
			}
			population = newPopulation;
			Collections.sort(population);

			if (generation % 10 == 0) {
				System.out.println("GENERACIJA " + generation + ". iznosi " + population.get(0).getPenalty());
			}
		}
		EXEC.shutdown();
	}

	public Value getResult() {
		return population.get(0);
	}

}
