package hr.fer.zemris.fuzzy3;

import java.util.List;

import hr.fer.zemris.fuzzy.Debug;
import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IBinaryFunction;
import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.Operations;

public class Task1 {

	public static void main(String[] args) {
		Defuzzifier def = new COADefuzzifier();
		FuzzySystem fsAkcel = new AkcelFuzzySystemMin(def, Operations.product());
		Rule rule = fsAkcel.getRules().get(3);
		//1.
		IFuzzySet set = conclude(rule, Operations.product(), 50, 70, 100, 40, 30, 1);
		Debug.print(set, "FuzzySet");
		System.out.println("Dekodirana vrijednost:");
		System.out.println(def.defuzzification(set));
		
		System.out.println("----------------------------------");
		//2.
		IFuzzySet set2 = conclude(fsAkcel.getRules(), Operations.product(), 50, 70, 100, 40, 30, 1);
		Debug.print(set2, "FuzzySet");
		System.out.println("Dekodirana vrijednost:");
		System.out.println(def.defuzzification(set2));
	}
	
	public static IFuzzySet conclude(Rule rule, IBinaryFunction tNorm, int L, int D, int LK, int DK, int V, int S) {

		int[] input = FuzzySystem.scaleInput(new int[] { L, D, LK, DK, V, S });
		IFuzzySet resultFuzzySet = null;

		int i = 0;
		double limit = 1;
		for (IFuzzySet fuzzySet : rule.getAntecedents()) {
			if (fuzzySet != null) {
				limit = tNorm.valueAt(limit, fuzzySet.getValueAt(DomainElement.of(input[i])));
			}
			i++;
		}
		IFuzzySet consequence = rule.getConsequence().copy();
		consequence.setLimit(limit);
		resultFuzzySet = consequence;
		
		return resultFuzzySet;
	}
	
	public static IFuzzySet conclude(List<Rule> rules, IBinaryFunction tNorm, int L, int D, int LK, int DK, int V, int S) {

		int[] input = FuzzySystem.scaleInput(new int[] { L, D, LK, DK, V, S });
		IFuzzySet resultFuzzySet = null;

		for (Rule rule : rules) {
			int i = 0;
			double limit = 1;
			for (IFuzzySet fuzzySet : rule.getAntecedents()) {
				if (fuzzySet != null) {
					limit = tNorm.valueAt(limit, fuzzySet.getValueAt(DomainElement.of(input[i])));
				}
				i++;
			}
			IFuzzySet consequence = rule.getConsequence().copy();
			consequence.setLimit(limit);
			if (resultFuzzySet == null) {
				resultFuzzySet = consequence;
			} else {
				resultFuzzySet = Operations.binaryOperation(resultFuzzySet, consequence, Operations.zadehOr());
			}
		}
		return resultFuzzySet;
	}
	
}
