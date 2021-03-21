package hr.fer.ga.crossover;

import java.util.Random;

import hr.fer.ga.Value;

public class BLX implements Crossover {

	private double alpha;
	private Random random;

	public BLX(double alpha) {
		this.alpha = alpha;
		this.random = new Random();
	}

	@Override
	public Value cross(Value value1, Value value2) {
		double[] parameters = new double[value1.getN()];
		for (int i = 0; i < value1.getN(); i++) {
			double ciMin = Math.min(value1.getParameters()[i], value2.getParameters()[i]);
			double ciMax = Math.max(value1.getParameters()[i], value2.getParameters()[i]);
			double Ii = ciMax - ciMin;
			parameters[i] = (ciMin - Ii * alpha) + random.nextDouble() * (ciMax + Ii * alpha - ciMin + Ii * alpha);
		}
		return new Value(parameters);
	}
}
