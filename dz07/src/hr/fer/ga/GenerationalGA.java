package hr.fer.ga;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.Dataset;
import hr.fer.NeuralNetwork;
import hr.fer.ga.crossover.Crossover;
import hr.fer.ga.mutation.Mutation;

public class GenerationalGA {

	private int populationSize;
	private int numberOfGenerations;
	private Crossover[] crossovers;
	private Mutation[] mutations;
	private double elitism;
	private List<Value> population;
	private double epsilon;
	private NeuralNetwork neuralNetwork;
	private Dataset dataset;
	private int n;
	private double[] v;
	private Random random;

	public GenerationalGA(int populationSize, int numberOfGenerations, double elitism, Crossover[] crossovers,
			Mutation[] mutations, double epsilon, NeuralNetwork neuralNetwork, Dataset dataset, double[] t) {

		population = new ArrayList<Value>();

		this.populationSize = populationSize;
		this.numberOfGenerations = numberOfGenerations;
		this.elitism = elitism;
		this.crossovers = crossovers;
		this.mutations = mutations;
		this.epsilon = epsilon;
		this.neuralNetwork = neuralNetwork;
		this.n = neuralNetwork.getNumberOfParameters();
		this.dataset = dataset;
		this.random = new Random();

		calculateV(t);

		initializePopulation();
		Collections.sort(population);
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
		
		System.out.println(elitism);
		System.out.println(populationSize);
		System.out.println((int) (elitism * populationSize));

		for (int generation = 0; generation < numberOfGenerations; generation++) {

			if (population.get(0).getPenalty() < epsilon)
				break;
			
			Crossover crossover = crossovers[(int) (Math.random() * (crossovers.length))];
			Mutation mutation = mutations[selectMutation()];

			List<Value> newPopulation = new ArrayList<>();
			for (int i = 0; i < populationSize; i++) {
				if (i < ((int) (elitism * populationSize))) {
					newPopulation.add(population.get(i));
					System.out.println(newPopulation.get(i).getPenalty());
				} else {
					int parent1 = (int) (Math.random() * (populationSize));
					int parent2 = (int) (Math.random() * (populationSize));
					Value child = mutation.mutate(crossover.cross(population.get(parent1), population.get(parent2)));
					child.calculatePenalty(dataset, neuralNetwork);
					newPopulation.add(child);
				}
			}
			population = newPopulation;
			Collections.sort(population);

			if (generation % 100 == 0) {
				System.out.println("GENERACIJA " + generation + ". iznosi " + population.get(0).getPenalty());
			}
		}
	}

	public Value getResult() {
		return population.get(0);
	}

}
