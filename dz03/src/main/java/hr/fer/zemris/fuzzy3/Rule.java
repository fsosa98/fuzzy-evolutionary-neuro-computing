package hr.fer.zemris.fuzzy3;

import java.util.List;

import hr.fer.zemris.fuzzy.IFuzzySet;

public class Rule {
	
	private List<IFuzzySet> antecedents;
	private IFuzzySet consequence;
	
	public Rule(List<IFuzzySet> antecedents, IFuzzySet consequence) {
		this.antecedents = antecedents;
		this.consequence = consequence;
	}

	public List<IFuzzySet> getAntecedents() {
		return antecedents;
	}

	public IFuzzySet getConsequence() {
		return consequence;
	}

}
