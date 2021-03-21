package hr.fer.zemris.fuzzy3;

import java.util.List;

import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IBinaryFunction;
import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.Operations;

public abstract class FuzzySystem {

	protected Defuzzifier defuzzifier;
	protected List<Rule> rules;
	protected IBinaryFunction tNorm;

	public FuzzySystem(Defuzzifier defuzzifier, IBinaryFunction tNorm) {
		this.defuzzifier = defuzzifier;
		initializeRules();
		this.tNorm = tNorm;
	}

	protected abstract void initializeRules();

	public int conclude(int L, int D, int LK, int DK, int V, int S) {

		int[] input = scaleInput(new int[] { L, D, LK, DK, V, S });
		IFuzzySet resultFuzzySet = null;

		for (Rule rule : rules) {
			int i = 0;
			double limit = 1;
			for (IFuzzySet fuzzySet : rule.getAntecedents()) {
				if (fuzzySet != null) {
					limit = tNorm.valueAt(limit, fuzzySet.getValueAt(DomainElement.of(input[i])));
				}
				i++;
			}
			IFuzzySet consequence = rule.getConsequence().copy();
			consequence.setLimit(limit);
			if (resultFuzzySet == null) {
				resultFuzzySet = consequence;
			} else {
				resultFuzzySet = Operations.binaryOperation(resultFuzzySet, consequence, Operations.zadehOr());
			}
		}
		return defuzzifier.defuzzification(resultFuzzySet);
	}

	public static int[] scaleInput(int[] input) {
		int[] newInput = new int[input.length];
		newInput[0] = input[0] * 100 / (input[0] + input[1]);
		newInput[1] = 100 - newInput[0];
		newInput[2] = input[2] * 100 / (input[2] + input[3]);
		newInput[3] = 100 - newInput[2];
		newInput[4] = input[4];
		newInput[5] = input[5] * 10;
		return newInput;
	}

	public List<Rule> getRules() {
		return rules;
	}

}
