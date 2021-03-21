package hr.fer.zemris.fuzzy;

public class Operations {

	public static IFuzzySet unaryOperation(IFuzzySet fuzzySet, IUnaryFunction unaryFunction) {
		MutableFuzzySet set = new MutableFuzzySet(fuzzySet.getDomain());
		for (DomainElement domainElement : fuzzySet.getDomain()) {
			set.set(domainElement, unaryFunction.valueAt(fuzzySet.getValueAt(domainElement)));
		}
		return set;
	}

	public static IFuzzySet binaryOperation(IFuzzySet fuzzySet1, IFuzzySet fuzzySet2, IBinaryFunction binaryFuncion) {
		MutableFuzzySet set = new MutableFuzzySet(fuzzySet1.getDomain());
		for (DomainElement domainElement : fuzzySet1.getDomain()) {
			set.set(domainElement,
					binaryFuncion.valueAt(fuzzySet1.getValueAt(domainElement), fuzzySet2.getValueAt(domainElement)));
		}
		return set;
	}

	public static IUnaryFunction zadehNot() {
		return new IUnaryFunction() {

			@Override
			public double valueAt(double a) {
				return 1 - a;
			}
		};
	}

	public static IBinaryFunction zadehAnd() {
		return new IBinaryFunction() {

			@Override
			public double valueAt(double a, double b) {
				return Math.min(a, b);
			}
		};
	}

	public static IBinaryFunction zadehOr() {
		return new IBinaryFunction() {

			@Override
			public double valueAt(double a, double b) {
				return Math.max(a, b);
			}
		};
	}

	public static IBinaryFunction hamacherTNorm(double koef) {
		return new IBinaryFunction() {

			@Override
			public double valueAt(double a, double b) {
				return (a * b) / (koef + (1 - koef) * (a + b - a * b));
			}
		};
	}

	public static IBinaryFunction hamacherSNorm(double koef) {
		return new IBinaryFunction() {

			@Override
			public double valueAt(double a, double b) {
				return (a + b - (2 - koef) * a * b) / (1 - (1 - koef) * a * b);
			}
		};
	}

}
