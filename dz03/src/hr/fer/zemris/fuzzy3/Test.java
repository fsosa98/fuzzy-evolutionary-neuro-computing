package hr.fer.zemris.fuzzy3;

import hr.fer.zemris.fuzzy.Operations;

public class Test {

	public static void main(String[] args) {
//		Defuzzifier def = new COADefuzzifier();
//		int x = def.defuzzification(new CalculatedFuzzySet(Domain.intRange(-90, 90), StandardFuzzySets.lambdaFunction(80, 90, 100)));
//		System.out.println(x);
		
		Defuzzifier def = new COADefuzzifier();
		FuzzySystem fsKormilo = new KormiloFuzzySystemMin(def, Operations.product());
		int kormilo = fsKormilo.conclude(20, 200, 50, 300, 5, 1);
		System.out.println(kormilo);
		kormilo = fsKormilo.conclude(80, 20, 300, 50, 5, 1);
		System.out.println(kormilo);
		kormilo = fsKormilo.conclude(110, 38, 66, 17, 40, 1);
		System.out.println(kormilo);
		
		FuzzySystem fsAkcel = new AkcelFuzzySystemMin(def, Operations.product());
		int akc= fsAkcel.conclude(110, 38, 66, 17, 40, 1);
		System.out.println(akc);
	}
	
	
}
