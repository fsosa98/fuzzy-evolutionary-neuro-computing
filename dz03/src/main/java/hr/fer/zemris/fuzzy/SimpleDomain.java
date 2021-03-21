package hr.fer.zemris.fuzzy;

import java.util.Iterator;

public class SimpleDomain extends Domain {

	private int first;
	private int last;

	public SimpleDomain(int first, int last) {
		this.first = first;
		this.last = last;
	}

	@Override
	public int getCardinality() {
		return last - first;
	}

	@Override
	public IDomain geComponent(int index) {
		if (index != 0) {
			throw new IllegalArgumentException();
		}
		return this;
	}

	@Override
	public int getNumberOfComponents() {
		return 1;
	}

	@Override
	public Iterator<DomainElement> iterator() {
		return new Iterator<DomainElement>() {

			private int current = first;

			@Override
			public boolean hasNext() {
				return current < last;
			}

			@Override
			public DomainElement next() {
				return new DomainElement(current++);
			}
		};
	}

	public int getFirst() {
		return first;
	}

	public int getLast() {
		return last;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleDomain other = (SimpleDomain) obj;
		if (first != other.first)
			return false;
		if (last != other.last)
			return false;
		return true;
	}
}
