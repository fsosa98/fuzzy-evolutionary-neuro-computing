package hr.fer;

import java.util.Arrays;
import java.util.Random;

public class NeuralNetwork {

	private int[] arhitecture;
	private int numberOfNeurons;
	private int n;
	private int M;

	public NeuralNetwork(int... arhitecture) {
		this.arhitecture = arhitecture;
		this.numberOfNeurons = getNumberOfNeurons();
		this.n = arhitecture[0];
		this.M = arhitecture[arhitecture.length - 1];
	}

	private int getNumberOfNeurons() {
		int num = 0;
		for (int layer : arhitecture) {
			num += layer;
		}
		return num;
	}

	public int getNumberOfParameters() {
		int num = 0;
		for (int i = 0; i < arhitecture.length - 1; i++) {
			if (i == 0) {
				num += 2 * arhitecture[i] * arhitecture[i + 1];
			} else {
				num += (1 + arhitecture[i]) * arhitecture[i + 1];
			}
		}
		return num;
	}

	private double sigmoid(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	public double[] calcOutput(double[] parameters, double... input) {
		double[] neurons = new double[numberOfNeurons];
		int neuronsOffset = 0;
		int parametersOffset = 0;
		for (int i = 0; i < arhitecture.length; i++) {
			if (i == 0) {
				for (int j = 0; j < arhitecture[i]; j++) {
					neurons[j] = input[j];
				}
			} else if (i == 1) {
				parametersOffset += arhitecture[i] * input.length;
				for (int j = 0; j < arhitecture[i]; j++) {
					neurons[neuronsOffset + j] = Util.calculateVectorSimilarity(input,
							Arrays.copyOfRange(parameters, j * input.length, (j + 1) * input.length),
							Arrays.copyOfRange(parameters, parametersOffset + j * input.length,
									parametersOffset + (j + 1) * input.length));
				}
				parametersOffset += arhitecture[i] * input.length;
			} else {
				for (int j = 0; j < arhitecture[i]; j++) {
					double net = parameters[parametersOffset];
					for (int k = 0; k < arhitecture[i - 1]; k++) {
						net += neurons[k + neuronsOffset - arhitecture[i - 1]] * parameters[parametersOffset + k + 1];
					}
					neurons[neuronsOffset + j] = sigmoid(net);
					parametersOffset += arhitecture[i - 1] + 1;
				}
			}
			neuronsOffset += arhitecture[i];
		}
		return Arrays.copyOfRange(neurons, neurons.length - M, neurons.length);
	}

	public double calcError(Dataset dataset, double[] parameters) {
		double mse = 0;
		for (int s = 0; s < dataset.getSize(); s++) {
			double[] y = calcOutput(parameters, dataset.getInput(s, arhitecture[0]));
			for (int o = 0; o < M; o++) {
				mse += Math.pow(y[o] - dataset.getData(s)[arhitecture[0] + o], 2);
			}
		}
		return mse / dataset.getSize();
	}

	public void printArray(double[] arr) {
		for (Double x : arr) {
			System.out.print(x + ", ");
		}
		System.out.println();
	}

	public int getN() {
		return n;
	}

	public int getM() {
		return M;
	}

}
