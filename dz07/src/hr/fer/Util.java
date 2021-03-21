package hr.fer;

public class Util {

	public static double calculateVectorSimilarity(double[] x, double[] w, double[] s) {
		double diff = 0;
		double S = 0;
		for (int i = 0; i < x.length; i++) {
			diff += Math.abs(x[i] - w[i]);
			S += Math.abs(s[i]);
		}
		return 1 / (1 + (diff) / S);
	}

	public static double calculateSimilarity(double x, double w, double s) {
		return 1 / (1 + (Math.abs(x - w) / Math.abs(s)));
	}
	
	

}
