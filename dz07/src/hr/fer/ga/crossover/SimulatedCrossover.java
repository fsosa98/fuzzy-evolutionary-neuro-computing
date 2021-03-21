package hr.fer.ga.crossover;

import hr.fer.ga.Value;

public class SimulatedCrossover implements Crossover {

	private double alpha;

	public SimulatedCrossover(double alpha) {
		super();
		this.alpha = alpha;
	}

	@Override
	public Value cross(Value value1, Value value2) {
		double[] parameters = new double[value1.getN()];
		for (int i = 0; i < value1.getN(); i++) {
			parameters[i] = (1 + alpha) * value1.getParameters()[i] + (1 - alpha) * value2.getParameters()[i];
		}
		return new Value(parameters);
	}

}
