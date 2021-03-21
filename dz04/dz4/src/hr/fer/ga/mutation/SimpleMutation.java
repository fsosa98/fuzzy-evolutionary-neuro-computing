package hr.fer.ga.mutation;

import java.util.Random;

import hr.fer.ga.Value;

public class SimpleMutation implements Mutation {
	
	private double koef;
	private Random random;
	
	public SimpleMutation(double koef) {
		this.koef = koef;
		this.random = new Random();
	}

	@Override
	public Value mutate(Value value) {
		double[] beta = new double[value.getN()];
		for (int i = 0; i < value.getN(); i++) {
			if (koef > random.nextDouble()) {
				beta[i] = random.nextDouble();
			}else {
				beta[i] = value.getBeta()[i];
			}
		}
		return new Value(beta);
	}

}
