package hr.fer.zemris.fuzzy;

public class Relations {

	public static boolean isUtimesURelation(IFuzzySet relation) {
		return relation.getDomain().getNumberOfComponents() == 2 && ((SimpleDomain) relation.getDomain().geComponent(0))
				.equals(((SimpleDomain) relation.getDomain().geComponent(1)));
	}

	public static boolean isSymmetric(IFuzzySet relation) {
		if (isUtimesURelation(relation)) {
			for (DomainElement domainElement : relation.getDomain()) {
				DomainElement simetricDomainElement = DomainElement.of(domainElement.getComponentValue(1),
						domainElement.getComponentValue(0));
				if (relation.getValueAt(domainElement) != relation.getValueAt(simetricDomainElement)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static boolean isReflexive(IFuzzySet relation) {
		if (isUtimesURelation(relation)) {
			for (DomainElement domainElement : relation.getDomain()) {
				if (domainElement.getComponentValue(0) == domainElement.getComponentValue(1)
						&& relation.getValueAt(domainElement) != 1) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static boolean isMaxMinTransitive(IFuzzySet relation) {
		if (isUtimesURelation(relation)) {
			for (DomainElement domainElement1 : relation.getDomain()) {
				for (DomainElement domainElement2 : relation.getDomain()) {
					if (domainElement1.getComponentValue(1) == domainElement2.getComponentValue(0)
							&& relation.getValueAt(DomainElement.of(domainElement1.getComponentValue(0),
									domainElement2.getComponentValue(1))) < Math.min(
											relation.getValueAt(domainElement1), relation.getValueAt(domainElement2))) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	public static IFuzzySet compositionOfBinaryRelations(IFuzzySet relation1, IFuzzySet relation2) {
		MutableFuzzySet relation3 = new MutableFuzzySet(
				Domain.combine(relation1.getDomain().geComponent(0), relation2.getDomain().geComponent(1)));
		for (DomainElement domainElement : relation3.getDomain()) {
			double value = -1;
			for (DomainElement domainElement1 : relation1.getDomain()) {
				for (DomainElement domainElement2 : relation2.getDomain()) {
					if (domainElement1.getComponentValue(0) == domainElement.getComponentValue(0)
							&& domainElement2.getComponentValue(1) == domainElement.getComponentValue(1)
							&& domainElement1.getComponentValue(1) == domainElement2.getComponentValue(0)) {
						value = Math.max(value,
								Math.min(relation1.getValueAt(domainElement1), relation2.getValueAt(domainElement2)));
					}
				}
			}
			relation3.set(domainElement, value);
		}
		return relation3;
	}
	
	public static boolean isFuzzyEquivalence(IFuzzySet relation) {
		return isReflexive(relation) && isSymmetric(relation) && isMaxMinTransitive(relation);
	}

}
