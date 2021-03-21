package hr.fer.zemris.fuzzy;

import java.util.Arrays;

public class DomainElement {

	private int[] values;

	public DomainElement(int... values) {
		this.values = values;
	}

	public int getNumberOfComponents() {
		return values.length;
	}

	public int getComponentValue(int index) {
		if (index < 0 || index >= values.length) {
			throw new IllegalArgumentException();
		}
		return values[index];
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(values);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DomainElement other = (DomainElement) obj;
		if (!Arrays.equals(values, other.values))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if (values.length == 1) {
			return Integer.toString(values[0]);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (int i = 0; i < values.length; i++) {
			sb.append(values[i]);
			if (i != values.length - 1) {
				sb.append(",");
			}
		}
		sb.append(")");
		return values.length != 1 ? sb.toString() : sb.substring(sb.length() - 1);
	}

	public static DomainElement of(int... values) {
		return new DomainElement(values);
	}

}
