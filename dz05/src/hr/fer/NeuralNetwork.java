package hr.fer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NeuralNetwork {

	private List<List<Double>> xTrain = new ArrayList<List<Double>>();
	private List<List<Double>> yTrain = new ArrayList<List<Double>>();
	private List<List<Neuron>> layers = new ArrayList<List<Neuron>>();
	private List<double[][]> weights = new ArrayList<double[][]>();

	private int N;
	private int numberOfGestures;
	private int M;
	private Random random;
	private int numberOfLayers;
	private int batchSize;
	private double psi = 0.5;
	private String path;
	private List<Integer> architecture;
	private int algorithm;
	private int numberOfIterations;

	public NeuralNetwork(String path, int M, List<Integer> architecture, int algorithm, int numberOfGestures, int N,
			int numberOfIterations) {
		this.path = path;
		this.architecture = architecture;
		this.algorithm = algorithm;
		this.numberOfGestures = numberOfGestures;
		this.N = N;
		this.M = M;
		this.numberOfIterations = numberOfIterations;
		random = new Random();
		numberOfLayers = architecture.size();
		if (architecture.get(0) != 2 * M || architecture.get(architecture.size() - 1) != numberOfGestures) {
			throw new IllegalArgumentException("Wrong architecture");
		}

		// Load train data
		try {
			List<String> lines = Files.readAllLines(new File(path).toPath(), Charset.defaultCharset());
			int i = 0;
			int j = -1;
			for (String line : lines) {
				List<Double> x = new ArrayList<Double>();
				List<Double> y = new ArrayList<Double>(Collections.nCopies(5, 0.));
				for (String part : line.strip().split("\\s+")) {
					x.add(Double.parseDouble(part));
				}
				xTrain.add(x);
				if (i % N == 0) {
					j++;
				}
				y.set(j, 1.);
				yTrain.add(y);
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<List<Double>> xTrain2 = new ArrayList<List<Double>>();
		List<List<Double>> yTrain2 = new ArrayList<List<Double>>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < numberOfGestures; j++) {
				xTrain2.add(new ArrayList<Double>(xTrain.get(i + N * j)));
				yTrain2.add(new ArrayList<Double>(yTrain.get(i + N * j)));
			}
		}
		xTrain = xTrain2;
		yTrain = yTrain2;

		// Build neural network
		for (int j = 0; j < architecture.size(); j++) {
			if (j < architecture.size() - 1) {
				weights.add(xavierInitialization(architecture.get(j), architecture.get(j), architecture.get(j + 1)));
			}
			List<Neuron> neurons = new ArrayList<Neuron>();
			for (int i = 0; i < architecture.get(j); i++) {
				neurons.add(new Neuron());
			}
			layers.add(neurons);
		}

		// algorithm
		if (algorithm == 1) {
			batchSize = xTrain.size();
		} else if (algorithm == 2) {
			batchSize = 1;
		} else {
			batchSize = 2 * numberOfGestures;
		}
	}

	public int predict(List<Double> xTest) {
		forwardPass(xTest);
		int prediction = 0;
		double predictedValue = layers.get(layers.size() - 1).get(0).getValue();
		System.out.print(layers.get(layers.size() - 1).get(0).getValue() + " ");
		for (int i = 1; i < layers.get(layers.size() - 1).size(); i++) {
			System.out.print(layers.get(layers.size() - 1).get(i).getValue() + " ");
			if (predictedValue < layers.get(layers.size() - 1).get(i).getValue()) {
				prediction = i;
				predictedValue = layers.get(layers.size() - 1).get(i).getValue();
			}
		}
		System.out.println();
		return prediction + 1;
	}

	private List<double[][]> zeroWeights() {
		List<double[][]> dwWeights = new ArrayList<double[][]>();
		for (int i = 0; i < weights.size(); i++) {
			double[][] newWeights = new double[weights.get(i).length][weights.get(i)[0].length];
			for (int j = 0; j < weights.get(i).length; j++) {
				for (int k = 0; k < weights.get(i)[j].length; k++) {
					newWeights[j][k] = 0;
				}
			}
			dwWeights.add(newWeights);
		}
		return dwWeights;
	}

	public void train() {
		List<double[][]> dwWeights = zeroWeights();
		int counter = 1;
		for (int iter = 0; iter < numberOfIterations; iter++) {
			for (int s = 0; s < xTrain.size(); s++) {
				forwardPass(xTrain.get(s));
				backpropagation(yTrain.get(s));

				for (int i = 0; i < dwWeights.size(); i++) {
					for (int j = 0; j < dwWeights.get(i).length; j++) {
						for (int k = 0; k < dwWeights.get(i)[j].length; k++) {
							dwWeights.get(i)[j][k] += layers.get(i + 1).get(k).getError()
									* layers.get(i).get(j).getValue();
						}
					}
				}

				if (counter % batchSize == 0) {
					updateWeights(dwWeights);
					dwWeights = zeroWeights();
					// shuffle data
				}
				counter++;
			}
			calculateError();
		}
		System.out.println();
	}

	private void calculateError() {
		NeuralNetwork nn = new NeuralNetwork(path, M, architecture, algorithm, numberOfGestures, N, numberOfIterations);
		nn.setWeights(weights);
		double err = 0;
		for (int s = 0; s < xTrain.size(); s++) {
			nn.forwardPass(xTrain.get(s));
			for (int i = 0; i < numberOfGestures; i++) {
				err += Math.pow(yTrain.get(s).get(i) - nn.getLayers().get(nn.getLayers().size() - 1).get(i).getValue(),
						2);
			}
		}
		System.out.println(err / (N * numberOfGestures));
	}

	public List<List<Neuron>> getLayers() {
		return layers;
	}

	public void setWeights(List<double[][]> weights) {
		this.weights = weights;
	}

	private void updateWeights(List<double[][]> dwWeights) {
		for (int i = 0; i < weights.size(); i++) {
			for (int j = 0; j < weights.get(i).length; j++) {
				for (int k = 0; k < weights.get(i)[j].length; k++) {
					weights.get(i)[j][k] += psi * dwWeights.get(i)[j][k] / batchSize;
				}
			}
		}
	}

	private void backpropagation(List<Double> y) {
		for (int i = numberOfLayers - 1; i >= 0; i--) {
			if (i == numberOfLayers - 1) {
				for (int j = 0; j < layers.get(i).size(); j++) {
					double ysj = layers.get(i).get(j).getValue();
					layers.get(i).get(j).setError(ysj * (1 - ysj) * (y.get(j) - ysj));
				}
			} else {
				for (int j = 0; j < layers.get(i).size(); j++) {
					double sum = 0;
					for (int k = 0; k < layers.get(i + 1).size(); k++) {
						sum += layers.get(i + 1).get(k).getError() * weights.get(i)[j][k];
					}
					double ysj = layers.get(i).get(j).getValue();
					layers.get(i).get(j).setError(ysj * (1 - ysj) * sum);
				}
			}
		}
	}

	public void forwardPass(List<Double> x) {
		for (int i = 0; i < numberOfLayers; i++) {
			if (i == 0) {
				for (int j = 0; j < layers.get(i).size(); j++) {
					layers.get(i).get(j).setValue(x.get(j));
				}
			} else {
				for (int j = 0; j < layers.get(i).size(); j++) {
					double net = 0;
					for (int k = 0; k < layers.get(i - 1).size(); k++) {
						net += layers.get(i - 1).get(k).getValue() * weights.get(i - 1)[k][j];
					}
					layers.get(i).get(j).setValue(sigmoid(net));
				}
			}
		}
	}

	private double sigmoid(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	private double[][] xavierInitialization(int n, int row, int col) {
		double[][] weights = new double[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				weights[i][j] = (-1 / Math.sqrt(n)) + ((1 / Math.sqrt(n)) - (-1 / Math.sqrt(n))) * random.nextDouble();
			}
		}
		return weights;
	}

}
