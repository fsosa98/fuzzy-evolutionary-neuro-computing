package hr.fer.ga;

import java.util.ArrayList;

import java.util.List;

import hr.fer.ga.crossover.Crossover;
import hr.fer.ga.mutation.Mutation;
import hr.fer.ga.selection.Selection;

public class EliminationGA implements GeneticAlgorithm {

	private int populationSize;
	private int numberOfGenerations;
	private Selection selection;
	private Crossover crossover;
	private Mutation mutation;
	private List<Value> population;
	private Value bestValue;
	private double minPenalty;
	private double epsilon;

	public EliminationGA(int populationSize, int numberOfGenerations, Selection selection, Crossover crossover,
			Mutation mutation, double epsilon) {

		population = new ArrayList<Value>();

		this.populationSize = populationSize;
		this.numberOfGenerations = numberOfGenerations;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.epsilon = epsilon;
		minPenalty = Double.MAX_VALUE;

		initializePopulation();
	}

	private void initializePopulation() {
		for (int i = 0; i < populationSize; i++) {
			Value value = new Value();
			population.add(value);
			if (minPenalty > value.getPenalty()) {
				minPenalty = value.getPenalty();
				bestValue = value;
			}
		}
	}

	public void start() {

		for (int generation = 0; generation < numberOfGenerations; generation++) {
			if (bestValue.getPenalty() < epsilon)
				break;

			Value value1 = population.get(selection.select(population));
			Value value2 = population.get(selection.select(population));
			Value value3 = population.get(selection.select(population));

			Value newValue = null;
			if (value1.getPenalty() > value2.getPenalty()) {
				if (value1.getPenalty() > value3.getPenalty()) {
					newValue = mutation.mutate(crossover.cross(value2, value3));
					population.remove(value1);
				} else {
					newValue = mutation.mutate(crossover.cross(value1, value2));
					population.remove(value3);
				}
			} else {
				if (value2.getPenalty() > value3.getPenalty()) {
					newValue = mutation.mutate(crossover.cross(value1, value3));
					population.remove(value2);
				} else {
					newValue = mutation.mutate(crossover.cross(value1, value2));
					population.remove(value3);
				}
			}
			population.add(newValue);
			if (minPenalty > newValue.getPenalty()) {
				minPenalty = newValue.getPenalty();
				bestValue = newValue;
			}

			if (generation % 100 == 0) {
				System.out.println("GENERACIJA " + generation + ". iznosi " + bestValue.getPenalty());
			}
		}
	}

	public Value getResult() {
		return bestValue;
	}

}
