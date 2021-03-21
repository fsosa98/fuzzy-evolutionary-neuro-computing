package hr.fer.ga.selection;

import java.util.List;
import java.util.Random;
import hr.fer.ga.Value;

public class TournamentSelection implements Selection {

	private Random random;
	private int k;

	public TournamentSelection(int k) {
		this.random = new Random();
		this.k = k;
	}

	@Override
	public int select(List<Value> population) {
		//System.out.println("size = " + population.size());
		int ind = random.nextInt(population.size());
		for (int i = 1; i < k; i++) {
			int randInd = random.nextInt(population.size());
			if (population.get(ind).getPenalty() > population.get(randInd).getPenalty()) {
				ind = randInd;
			}
		}
		//System.out.println("Vracam " + ind);
		return ind;
	}

}
