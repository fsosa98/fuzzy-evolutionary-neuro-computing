package hr.fer.zemris.fuzzy;

public class Debug {

	public static void print(IDomain domain, String headingText) {

		if (headingText != null) {
			System.out.println(headingText);
		}

		for (DomainElement e : domain) {
			System.out.println("Element domene: " + e);
		}

		System.out.println("Kardinalitet domene je: " + domain.getCardinality());
		System.out.println();
	}

	public static void print(IFuzzySet fuzzySet, String headingText) {

		if (headingText != null) {
			System.out.println(headingText);
		}

		for (DomainElement domainElement : fuzzySet.getDomain()) {
			System.out.println("d(" + domainElement + ")="
					+ String.format("%.6f", fuzzySet.getValueAt(domainElement)));
		}

		System.out.println();
	}

}
