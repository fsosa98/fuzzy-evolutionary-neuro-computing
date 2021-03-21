package hr.fer.zemris.fuzzy3;

import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IFuzzySet;

public class CuttedFuzzySet {

	private IFuzzySet fuzzySet;
	private double threshold;
	
	public CuttedFuzzySet(IFuzzySet fuzzySet, double threshold) {
		this.fuzzySet = fuzzySet;
		this.threshold = threshold;
	}
	
	public double getValueAt(DomainElement domainElement) {
		return Math.min(threshold, fuzzySet.getValueAt(domainElement));
	}
	
}
