package hr.fer.zemris.fuzzy;

public class MutableFuzzySet implements IFuzzySet {

	private double[] memberships;
	private IDomain domain;
	private double limit;

	public MutableFuzzySet(IDomain domain) {
		this.domain = domain;
		memberships = new double[domain.getCardinality()];
		this.limit = 1;
	}

	@Override
	public IDomain getDomain() {
		return domain;
	}

	@Override
	public double getValueAt(DomainElement domainElement) {
		return Math.min(limit, memberships[domain.indexOfElement(domainElement)]);
	}

	public MutableFuzzySet set(DomainElement domainElement, double value) {
		memberships[domain.indexOfElement(domainElement)] = value;
		return this;
	}

	@Override
	public void setLimit(double limit) {
		this.limit = limit;
	}

	@Override
	public IFuzzySet copy() {
		MutableFuzzySet copy = new MutableFuzzySet(domain);
		copy.memberships = this.memberships;
		copy.limit = this.limit;
		return copy;
	}

}
