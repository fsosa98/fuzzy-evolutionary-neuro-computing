package hr.fer.zemris.fuzzy3;

import java.util.ArrayList;

import java.util.Arrays;

import hr.fer.zemris.fuzzy.IBinaryFunction;

public class AkcelFuzzySystemMin extends FuzzySystem {

	public AkcelFuzzySystemMin(Defuzzifier defuzzifier, IBinaryFunction tNorm) {
		super(defuzzifier, tNorm);
	}

	protected void initializeRules() {
		rules = new ArrayList<Rule>();

		//rules.add(new Rule(Arrays.asList(Variables.distanceB, Variables.distanceB, null, null, null, null),Variables.accelerationP));
		rules.add(new Rule(Arrays.asList(null, null, Variables.distanceVS, null, null, null),Variables.accelerationVP));
		rules.add(new Rule(Arrays.asList(null, null, null, Variables.distanceVS, null, null),Variables.accelerationVP));
		rules.add(new Rule(Arrays.asList(null, null, null, null, Variables.speedB, null), Variables.accelerationN));
		rules.add(new Rule(Arrays.asList(null, null, null, null, Variables.speedS, null), Variables.accelerationP));
	}

}
