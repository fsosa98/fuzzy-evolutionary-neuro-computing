package hr.fer.zemris.fuzzy;

public class Primjer1 {

	public static void main(String[] args) {

		IDomain d = Domain.intRange(0, 11); // {0,1,...,10}
		IFuzzySet set1 = new MutableFuzzySet(d)
				.set(DomainElement.of(0), 1.0)
				.set(DomainElement.of(1), 0.8)
				.set(DomainElement.of(2), 0.6)
				.set(DomainElement.of(3), 0.4)
				.set(DomainElement.of(4), 0.2);
		Debug.print(set1, "Set1:");
		
		IDomain d2 = Domain.intRange(-5, 6); // {-5,-4,...,4,5}
		IFuzzySet set2 = new CalculatedFuzzySet(
				d2,
				StandardFuzzySets.lambdaFunction(
						d2.indexOfElement(DomainElement.of(-4)),
						d2.indexOfElement(DomainElement.of(0)), 
						d2.indexOfElement(DomainElement.of(4))));
		Debug.print(set2, "Set2:");
		
		IDomain d3 = Domain.intRange(-5, 6); // {-5,-4,...,4,5}
		IFuzzySet set3 = new CalculatedFuzzySet(
				d3,
				StandardFuzzySets.lFunction(
						d3.indexOfElement(DomainElement.of(-4)),
						d3.indexOfElement(DomainElement.of(4))));
		Debug.print(set3, "Set3:");
		
		IDomain d4 = Domain.intRange(-5, 6); // {-5,-4,...,4,5}
		IFuzzySet set4 = new CalculatedFuzzySet(
				d4,
				StandardFuzzySets.gammaFunction(
						d4.indexOfElement(DomainElement.of(0)),
						d4.indexOfElement(DomainElement.of(3))));
		Debug.print(set4, "Set4:");

		
		IDomain d5 = Domain.combine(d, d2); // {0,1,...,10}
		IFuzzySet set5 = new CalculatedFuzzySet(
				d5,
				StandardFuzzySets.lambdaFunction(
						d5.indexOfElement(DomainElement.of(0, -1)),
						d5.indexOfElement(DomainElement.of(1, 1)), 
						d5.indexOfElement(DomainElement.of(2, 5))));
		Debug.print(set5, "Set5:");
	}

}
