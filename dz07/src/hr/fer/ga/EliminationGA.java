package hr.fer.ga;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;

import hr.fer.Dataset;
import hr.fer.NeuralNetwork;
import hr.fer.ga.crossover.Crossover;
import hr.fer.ga.mutation.Mutation;

public class EliminationGA {

	private int populationSize;
	private int numberOfGenerations;
	private Crossover[] crossovers;
	private Mutation[] mutations;
	private List<Value> population;
	private Value bestValue;
	private double minPenalty;
	private double epsilon;
	private NeuralNetwork neuralNetwork;
	private Dataset dataset;
	private int n;
	private double[] v;
	private Random random;

	public EliminationGA(int populationSize, int numberOfGenerations, Crossover[] crossovers, Mutation[] mutations,
			double epsilon, NeuralNetwork neuralNetwork, Dataset dataset, double[] t) {

		population = new ArrayList<Value>();
		this.populationSize = populationSize;
		this.numberOfGenerations = numberOfGenerations;
		this.crossovers = crossovers;
		this.mutations = mutations;
		this.epsilon = epsilon;
		minPenalty = Double.MAX_VALUE;
		this.neuralNetwork = neuralNetwork;
		this.n = neuralNetwork.getNumberOfParameters();
		this.dataset = dataset;
		this.random = new Random();

		calculateV(t);
		initializePopulation();
	}

	private void calculateV(double[] t) {
		v = new double[t.length];
		double sum = 0;
		for (Double ti : t) {
			sum += ti;
		}
		for (int i = 0; i < t.length; i++) {
			v[i] = t[i] / sum;
		}
	}

	private void initializePopulation() {
		for (int i = 0; i < populationSize; i++) {
			Value value = new Value(n);
			value.calculatePenalty(dataset, neuralNetwork);
			population.add(value);
			if (minPenalty > value.getPenalty()) {
				minPenalty = value.getPenalty();
				bestValue = value;
			}
		}
	}

	private int selectMutation() {
		double k = random.nextDouble();
		double sum = 0;
		for (int i = 0; i < v.length; i++) {
			sum += v[i];
			if (k < sum) {
				return i;
			}
		}
		return v.length - 1;
	}

	public void start() {

		for (int generation = 0; generation < numberOfGenerations * populationSize; generation++) {
			if (bestValue.getPenalty() < epsilon)
				break;

			Value value1 = population.get((int) (Math.random() * (populationSize)));
			Value value2 = population.get((int) (Math.random() * (populationSize)));
			Value value3 = population.get((int) (Math.random() * (populationSize)));

			Crossover crossover = crossovers[(int) (Math.random() * (crossovers.length))];
			Mutation mutation = mutations[selectMutation()];

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
			newValue.calculatePenalty(dataset, neuralNetwork);
			population.add(newValue);

			if (minPenalty > newValue.getPenalty()) {
				minPenalty = newValue.getPenalty();
				bestValue = newValue;
			}

			if (generation % (1000 * populationSize) == 0) {
				System.out
						.println("GENERACIJA " + (generation / populationSize) + ". iznosi " + bestValue.getPenalty());
			}
		}
	}

	public Value getResult() {
		return bestValue;
	}

}
