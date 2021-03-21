package hr.fer.ga;

import hr.fer.ga.crossover.BLX;
import hr.fer.ga.crossover.Crossover;
import hr.fer.ga.mutation.Mutation;
import hr.fer.ga.mutation.SimpleMutation;
import hr.fer.ga.selection.Selection;
import hr.fer.ga.selection.TournamentSelection;

public class Main {

	public static void main(String[] args) {
		String path = "/home/filip/Desktop/FS/1.sem/NENR/DZ/dz04/zad4-dataset1.txt";
		Util.loadData(path);

		int populationSize = 500;
		int maxIter = 10000;
		Selection selection = new TournamentSelection(3);
		Crossover crossover = new BLX(0.5);
		Mutation mutation = new SimpleMutation(3. / 100);
		double elitism = 5 / 100.;
		double epsilon = 10e-6;

		GeneticAlgorithm ga = new GenerationalGA(populationSize, maxIter, elitism, selection, crossover, mutation, epsilon);
		//GeneticAlgorithm ga = new EliminationGA(populationSize, maxIter, selection, crossover, mutation, epsilon);
		
		ga.start();
		System.out.println("beta = (" + ga.getResult() + ")");
		System.out.println("Minimalna pogreška = " + ga.getResult().getPenalty());
	}

}
