package hr.fer;

import java.io.FileWriter;
import java.io.IOException;

import hr.fer.ga.EliminationGA;
import hr.fer.ga.GenerationalGA;
import hr.fer.ga.Value;
import hr.fer.ga.crossover.BLX;
import hr.fer.ga.crossover.Crossover;
import hr.fer.ga.crossover.DiscreteRecombination;
import hr.fer.ga.crossover.SimulatedCrossover;
import hr.fer.ga.mutation.GaussianMutation1;
import hr.fer.ga.mutation.GaussianMutation2;
import hr.fer.ga.mutation.Mutation;

public class Main {
	
	private static final double[] goodParameters = new double[] {0.8692301407847853, 0.2562598654541232, 0.8644442394967001, 0.7356410171043015, 0.37782378174778086, 0.2591253411900204, 0.6226491870785908, 0.742503120474032, 0.13312231355168397, 0.2609729953220581, 0.3723664840056063, 0.7417873801894479, 0.6279987840376601, 0.2569053450222305, 0.13244253625526742, 0.7434753362950612, 0.05466797270372843, 0.06546854104125982, -0.18866685473518108, 6.4881295103518535E-6, -0.1560332637964303, -0.0033257821945203592, -0.06198553602628378, 0.07692501033558267, -0.03113937323961592, 0.12315757273598098, 0.14328215502865196, 0.021315409253332075, 0.08628716271756413, 0.06109616956255222, 3.674670785894538E-5, 0.12543831939584654, 1.4435045142742207, 49.98978101792154, -38.87578705174174, -30.164570897568233, 64.94654314566372, 44.79938294776447, -24.140342016216675, -39.74056392893626, -25.06419338661749, 1.823401272003873, -26.451436843032152, 39.30981545703176, 57.125427500293426, -30.71406910315779, -39.82998546711205, -38.977587778199215, -25.375785127874323, 43.930142147695115, 0.0587137905371903, -25.54419931978635, -21.84157895152997, -27.735754118027625, -30.16333880065086, -28.088017824732894, 57.081741328637015, 59.9758271165054, -23.63604565444254
};
	

	public static void main(String[] args) {
		String path = "zad7-dataset.txt";
     	Dataset dataset = new Dataset(path);

		int populationSize = 200;
		int numberOfGenerations = 100000;
		Crossover[] crossovers = new Crossover[] { new BLX(0.5), new DiscreteRecombination(), new SimulatedCrossover(0.5) };
		// Crossover[] crossovers = new Crossover[]{new BLX(0.5),new BLX(0.5),new BLX(0.5)};
		Mutation[] mutations = new Mutation[] { new GaussianMutation1(0.01, 0.02), new GaussianMutation1(0.01, 0.5), new GaussianMutation2(0.01, 1) };
		double[] t = new double[] { 2, 1, 1 };
		double epsilon = 10e-7;
		NeuralNetwork neuralNetwork = new NeuralNetwork(2, 8, 4, 3);
		int n = neuralNetwork.getN();
     	int M = neuralNetwork.getM();
		EliminationGA ga = new EliminationGA(populationSize, numberOfGenerations, crossovers, mutations, epsilon, neuralNetwork, dataset, t);
		//GenerationalGA ga = new GenerationalGA(populationSize, numberOfGenerations, 0.1, crossovers, mutations, epsilon, neuralNetwork, dataset, t);
		ga.start();

		Value sol = ga.getResult();
		System.out.println("Solution");
		System.out.println(sol);
		for (int s = 0; s < dataset.getSize(); s++) {
			double[] y = neuralNetwork.calcOutput(sol.getParameters(), dataset.getInput(s, 2));
			for (int o = 0; o < M; o++) {
				System.out.print(y[o] + " ");
			}
			System.out.print("| ");
			for (int o = 0; o < M; o++) {
				System.out.print(dataset.getData(s)[n + o] + " ");
			}
			System.out.println();
		}
		
     	printResult(neuralNetwork, dataset, sol.getParameters(), n, M);
     	//writeAllParameters("/home/filip/Desktop/FS/1.sem/NENR/DZ/dz7/Report/zad_5_allParameters.txt", sol.getParameters());
     	//writeN1Neurons("/home/filip/Desktop/FS/1.sem/NENR/DZ/dz7/Report/zad_5_N1Neurons.txt", n, 8);
     	//writeS("/home/filip/Desktop/FS/1.sem/NENR/DZ/dz7/Report/s.txt", n, 8);
	}
	
	public static void writeAllParameters(String path, double[] parameters) {
		try {
			FileWriter writer = new FileWriter(path);
			for (int i = 0; i < parameters.length; i++) {
				writer.write(parameters[i] + System.lineSeparator());
			}
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void writeN1Neurons(String path, int n, int numberOfN1) {
		try {
			FileWriter writer = new FileWriter(path);
			for (int i = 0; i < numberOfN1; i++) {
				writer.write(goodParameters[i * 2] + " " + goodParameters[i * 2 + 1] + System.lineSeparator());
			}
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void writeS(String path, int n, int numberOfN1) {
		try {
			FileWriter writer = new FileWriter(path);
			int offset = n * numberOfN1;
			for (int i = 0; i < numberOfN1; i++) {
				writer.write(goodParameters[offset + i * 2] + " " + goodParameters[offset + i * 2 + 1] + System.lineSeparator());
			}
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void printResult(NeuralNetwork neuralNetwork, Dataset dataset, double[] parameters, int n, int M) {
		System.out.println("     y      |      t      | T/F ");
		for (int s = 0; s < dataset.getSize(); s++) {
			double[] y = neuralNetwork.calcOutput(parameters, dataset.getInput(s, n));
			for (int o = 0; o < M; o++) {
				double yo = y[o] < 0.5 ? 0 : 1;
				System.out.print(yo + " ");
			}
			System.out.print("| ");
			for (int o = 0; o < M; o++) {
				System.out.print(dataset.getData(s)[n + o] + " ");
			}System.out.print("| ");
			for (int o = 0; o < M; o++) {
				double yo = y[o] < 0.5 ? 0 : 1;
				if (yo == dataset.getData(s)[n + o]) {
					System.out.print("T");
				}else {
					System.out.print("F");
				}
			}
			System.out.println();
		}
	}

}
