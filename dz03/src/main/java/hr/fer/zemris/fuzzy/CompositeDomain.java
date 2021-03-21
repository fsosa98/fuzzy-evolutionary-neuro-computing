package hr.fer.zemris.fuzzy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompositeDomain extends Domain {

	private SimpleDomain[] componenets;

	public CompositeDomain(SimpleDomain... componenets) {
		this.componenets = componenets;
	}

	@Override
	public int getCardinality() {
		int cardinality = 1;
		for (SimpleDomain simpleDomain : componenets) {
			cardinality *= simpleDomain.getCardinality();
		}
		return cardinality;
	}

	@Override
	public IDomain geComponent(int index) {
		if (index < 0 || index >= componenets.length) {
			throw new IllegalArgumentException();
		}
		return componenets[index];
	}

	@Override
	public int getNumberOfComponents() {
		return componenets.length;
	}

	@Override
	public Iterator<DomainElement> iterator() {
		return new CompositeDomainIterator();
	}

	private class CompositeDomainIterator implements Iterator<DomainElement> {

		private int counter;
		private List<Iterator<DomainElement>> currentIterators;
		private int[] currentValues;

		public CompositeDomainIterator() {
			currentIterators = new ArrayList<Iterator<DomainElement>>();
			currentValues = new int[componenets.length];
			for (int i = 0; i < componenets.length; i++) {
				currentIterators.add(componenets[i].iterator());
				currentValues[i] = currentIterators.get(i).next().getComponentValue(0);
			}
		}

		@Override
		public boolean hasNext() {
			return counter < getCardinality();
		}

		@Override
		public DomainElement next() {
			counter++;
			if (counter == 1) {
				return new DomainElement(currentValues);
			}
			for (int i = currentIterators.size() - 1; i >= 0; i--) {
				if (currentIterators.get(i).hasNext()) {
					currentValues[i] = currentIterators.get(i).next().getComponentValue(0);
					break;
				} else {
					currentIterators.set(i, componenets[i].iterator());
					currentValues[i] = currentIterators.get(i).next().getComponentValue(0);
				}
			}
			return new DomainElement(currentValues);
		}

	}

}
