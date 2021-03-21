package hr.fer.zemris.fuzzy;

public interface IFuzzySet {

	public IDomain getDomain();

	public double getValueAt(DomainElement domainElement);
	
	public void setLimit(double limit);
	
	public IFuzzySet copy();

}
