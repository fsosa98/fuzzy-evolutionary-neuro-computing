package hr.fer.ga.mutation;

import java.util.Random;

import hr.fer.ga.Value;

public class GaussianMutation2 implements Mutation {

	private double sigma;
	private double pm;
	private Random random;

	public GaussianMutation2(double pm, double sigma) {
		this.sigma = sigma;
		this.pm = pm;
		this.random = new Random();
	}

	@Override
	public Value mutate(Value value) {
		double[] parameters = new double[value.getN()];
		for (int i = 0; i < value.getN(); i++) {
			if (pm >= random.nextDouble()) {
				parameters[i] = random.nextGaussian() * sigma;
			} else {
				parameters[i] = value.getParameters()[i];
			}
		}
		return new Value(parameters);
	}

}
