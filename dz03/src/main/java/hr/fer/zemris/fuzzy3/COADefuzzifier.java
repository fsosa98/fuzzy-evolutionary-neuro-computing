package hr.fer.zemris.fuzzy3;

import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IFuzzySet;

public class COADefuzzifier implements Defuzzifier {

	@Override
	public int defuzzification(IFuzzySet fuzzySet) {
		double coa1 = 0;
		double coa2 = 0;
		for (DomainElement domainElement : fuzzySet.getDomain()) {
			coa1 += fuzzySet.getValueAt(domainElement) * domainElement.getComponentValue(0);
			coa2 += fuzzySet.getValueAt(domainElement);
		}
		return (int) (coa1 / coa2);
	}

}
