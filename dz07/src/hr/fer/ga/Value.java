package hr.fer.ga;

import java.util.Arrays;
import java.util.Random;

import hr.fer.Dataset;
import hr.fer.NeuralNetwork;

public class Value implements Comparable<Value> {

	private int n;
	private double[] parameters;
	private Random r;
	private double penalty;

	public Value(double[] parameters) {
		this.parameters = parameters;
		this.n = parameters.length;
	}

	public Value(int n) {
		this.n = n;
		r = new Random();
		parameters = new double[n];
		for (int i = 0; i < n; i++) {
			if (i < 32) {
				parameters[i] = 1 * r.nextDouble();
			}else {
				parameters[i] = -1 + 2 * r.nextDouble();
			}
			
		}
	}

	public void calculatePenalty(Dataset dataset, NeuralNetwork neuralNetwork) {
		penalty = neuralNetwork.calcError(dataset, parameters);
	}

	public double getPenalty() {
		return penalty;
	}

	public double[] getParameters() {
		return parameters;
	}

	public int getN() {
		return n;
	}

	@Override
	public int compareTo(Value o) {
		return Double.compare(this.getPenalty(), o.getPenalty());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(parameters);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Value other = (Value) obj;
		if (!Arrays.equals(parameters, other.parameters))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(parameters[i] + ", ");
		}
		return sb.substring(0, sb.length() - 2).toString();
	}

}
