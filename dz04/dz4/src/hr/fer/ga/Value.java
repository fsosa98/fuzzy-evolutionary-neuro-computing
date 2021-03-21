package hr.fer.ga;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Value implements Comparable<Value> {

	private int n = 5;
	private double[] beta;
	private Random r;
	private double penalty;

	public Value(double[] beta) {
		this.beta = beta;
		calculatePenalty();
	}

	public Value() {
		r = new Random();
		beta = new double[n];
		for (int i = 0; i < n; i++) {
			beta[i] = -4 + (4 - -4) * r.nextDouble();
		}
		calculatePenalty();
	}

	public void calculatePenalty() {
		penalty = 0;
		for (List<Double> line : Util.input) {
			double x = line.get(0);
			double y = line.get(1);
			double z = line.get(2);
			double f = Math.sin(beta[0] + beta[1] * x) + beta[2] * Math.cos(x * (beta[3] + y)) / (1 + Math.exp(Math.pow(x - beta[4], 2)));
			penalty += Math.pow(f - z, 2);
		}
		penalty /= Util.input.size();
	}

	public double getPenalty() {
		return penalty;
	}

	public double[] getBeta() {
		return beta;
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
		result = prime * result + Arrays.hashCode(beta);
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
		if (!Arrays.equals(beta, other.beta))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(beta[i] + ", ");
		}
		return sb.substring(0, sb.length() - 2).toString();
	}

}
