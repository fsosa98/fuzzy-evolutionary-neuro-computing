package hr.fer.zemris.fuzzy;

public abstract class Domain implements IDomain {

	public Domain() {
	}

	public static IDomain intRange(int first, int last) {
		return new SimpleDomain(first, last);
	}

	public static Domain combine(IDomain domain1, IDomain domain2) {
		SimpleDomain[] simpleDomains = new SimpleDomain[domain1.getNumberOfComponents()
				+ domain2.getNumberOfComponents()];
		int i = 0;

		for (int j = 0; j < domain1.getNumberOfComponents(); j++) {
			simpleDomains[i++] = (SimpleDomain) domain1.geComponent(j);
		}

		for (int j = 0; j < domain2.getNumberOfComponents(); j++) {
			simpleDomains[i++] = (SimpleDomain) domain2.geComponent(j);
		}

		return new CompositeDomain(simpleDomains);
	}

	@Override
	public int indexOfElement(DomainElement domainElement) {
		int index = -1;
		boolean exists = false;
		for (DomainElement de : this) {
			index++;
			if (domainElement.equals(de)) {
				exists = true;
				break;
			}
		}
		return exists ? index : -1;
	}

	@Override
	public DomainElement elementForIndex(int index) {
		if (index < 0) {
			throw new IllegalArgumentException();
		}
		for (DomainElement domainElement : this) {
			if (index == 0) {
				return domainElement;
			}
			index--;
		}
		throw new IllegalArgumentException();
	}

}
