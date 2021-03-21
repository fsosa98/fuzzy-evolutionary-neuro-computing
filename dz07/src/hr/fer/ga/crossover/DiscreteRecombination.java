package hr.fer.ga.crossover;

import java.util.Random;

import hr.fer.ga.Value;

public class DiscreteRecombination implements Crossover {

	private Random random;

	public DiscreteRecombination() {
		this.random = new Random();
	}

	@Override
	public Value cross(Value value1, Value value2) {
		double[] parameters = new double[value1.getN()];
		for (int i = 0; i < value1.getN(); i++) {
			double p = random.nextDouble();
			if (p < 0.5) {
				parameters[i] = value1.getParameters()[i];
			} else {
				parameters[i] = value2.getParameters()[i];
			}
		}
		return new Value(parameters);
	}

}
