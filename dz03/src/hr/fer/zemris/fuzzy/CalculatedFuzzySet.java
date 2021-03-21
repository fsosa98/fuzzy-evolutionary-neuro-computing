package hr.fer.zemris.fuzzy;

public class CalculatedFuzzySet implements IFuzzySet {

	private IDomain domain;
	private IIntUnaryFunction unaryFunction;
	private double limit;

	public CalculatedFuzzySet(IDomain domain, IIntUnaryFunction unaryFunction) {
		this.domain = domain;
		this.unaryFunction = unaryFunction;
		this.limit = 1;
	}

	@Override
	public IDomain getDomain() {
		return domain;
	}

	@Override
	public double getValueAt(DomainElement domainElement) {
		return Math.min(limit, unaryFunction.valueAt(domain.indexOfElement(domainElement)));
	}

	@Override
	public void setLimit(double limit) {
		this.limit = limit;
	}

	public IIntUnaryFunction getUnaryFunction() {
		return unaryFunction;
	}

	@Override
	public IFuzzySet copy() {
		return new CalculatedFuzzySet(domain, unaryFunction);
	}

}
