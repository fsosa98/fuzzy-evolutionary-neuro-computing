package hr.fer.zemris.fuzzy3;

import java.util.ArrayList;
import java.util.Arrays;

import hr.fer.zemris.fuzzy.IBinaryFunction;

public class KormiloFuzzySystemMin extends FuzzySystem {

	public KormiloFuzzySystemMin(Defuzzifier defuzzifier, IBinaryFunction tNorm) {
		super(defuzzifier, tNorm);
	}

	protected void initializeRules() {
		rules = new ArrayList<Rule>();

		//rules.add(new Rule(Arrays.asList(Variables.distance2VS, null, null, null, null, null), Variables.angleVN));
		//rules.add(new Rule(Arrays.asList(Variables.distance2S, null, null,  null, null, null), Variables.angleVN));
		//rules.add(new Rule(Arrays.asList(null, Variables.distance2S, null,  null, null, null), Variables.angleVP));
		//rules.add(new Rule(Arrays.asList(null, null, Variables.distance2VS, null, null, null), Variables.angleVN));
		//rules.add(new Rule(Arrays.asList(null, Variables.distance2VS, null, null, null, null), Variables.angleVP));
		//rules.add(new Rule(Arrays.asList(null, null, null, Variables.distance2VS, null, null), Variables.angleVP));
		//rules.add(new Rule(Arrays.asList(null, null, Variables.distanceS, null, null, null), Variables.angleN));
		//rules.add(new Rule(Arrays.asList(null, null, null, Variables.distanceS, null, null), Variables.angleP));
		//rules.add(new Rule(Arrays.asList(Variables.distance2M, Variables.distance2M, null, null, null, null), Variables.angleZ));
		rules.add(new Rule(Arrays.asList(null, null, Variables.distanceS, null, null, null), Variables.angleN));
		rules.add(new Rule(Arrays.asList(null, null, null, Variables.distanceS, null, null), Variables.angleVP));
		rules.add(new Rule(Arrays.asList(Variables.distanceS, null, null, null, null, Variables.directionB), Variables.angleVVN));
		rules.add(new Rule(Arrays.asList(null, Variables.distanceS, null, null, null, Variables.directionB), Variables.angleVVP));
	}

}
