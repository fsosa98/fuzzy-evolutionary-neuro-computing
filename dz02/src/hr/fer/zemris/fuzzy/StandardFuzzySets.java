package hr.fer.zemris.fuzzy;

public class StandardFuzzySets {

	public static IIntUnaryFunction lFunction(int alpha, int beta) {
		return new IIntUnaryFunction() {

			@Override
			public double valueAt(int x) {
				if (x < alpha) {
					return 1;
				}
				if (x > beta) {
					return 0;
				}
				return (beta - x) * 1.0 / (beta - alpha);
			}
		};
	}

	public static IIntUnaryFunction gammaFunction(int alpha, int beta) {
		return new IIntUnaryFunction() {

			@Override
			public double valueAt(int x) {
				if (x < alpha) {
					return 0;
				}
				if (x > beta) {
					return 1;
				}
				return (x - alpha) * 1.0 / (beta - alpha);
			}
		};
	}

	public static IIntUnaryFunction lambdaFunction(int alpha, int beta, int gamma) {
		return new IIntUnaryFunction() {

			@Override
			public double valueAt(int x) {
				if (x < alpha) {
					return 0;
				}
				if (x > gamma) {
					return 0;
				}
				if (x < beta) {
					return (x - alpha) * 1.0 / (beta - alpha);
				}
				return (gamma - x) * 1.0 / (gamma - beta);
			}
		};
	}

}
