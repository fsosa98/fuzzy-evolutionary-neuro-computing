package hr.fer.zemris.fuzzy3;

import hr.fer.zemris.fuzzy.CalculatedFuzzySet;
import hr.fer.zemris.fuzzy.Domain;
import hr.fer.zemris.fuzzy.IDomain;
import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.StandardFuzzySets;

public class Variables {
	
	private static IDomain speedDomain = Domain.intRange(0, 100);
	private static IDomain accelerationDomain = Domain.intRange(-50, 51);
	private static IDomain angleDomain = Domain.intRange(-90, 91);
	private static IDomain directionDomain = Domain.intRange(-1, 11);
	private static IDomain distanceDomain = Domain.intRange(0, 100);
	
	public static IFuzzySet distanceVS = new CalculatedFuzzySet(Variables.distanceDomain, StandardFuzzySets.lFunction(10, 20));
	public static IFuzzySet distanceS = new CalculatedFuzzySet(Variables.distanceDomain, StandardFuzzySets.lFunction(20, 30));
	public static IFuzzySet distanceM = new CalculatedFuzzySet(Variables.distanceDomain, StandardFuzzySets.lambdaFunction(40, 50, 60));
	
	public static IFuzzySet directionB = new CalculatedFuzzySet(Variables.directionDomain, StandardFuzzySets.lFunction(2, 5));
	public static IFuzzySet directionG = new CalculatedFuzzySet(Variables.directionDomain, StandardFuzzySets.gammaFunction(4, 8));
	
	public static IFuzzySet speedS = new CalculatedFuzzySet(Variables.speedDomain, StandardFuzzySets.lFunction(40, 50));
	public static IFuzzySet speedB = new CalculatedFuzzySet(Variables.speedDomain, StandardFuzzySets.gammaFunction(80, 90));
	
	public static IFuzzySet accelerationN = new CalculatedFuzzySet(Variables.accelerationDomain, StandardFuzzySets.lFunction(30, 45));
	public static IFuzzySet accelerationZ = new CalculatedFuzzySet(Variables.accelerationDomain, StandardFuzzySets.lambdaFunction(6, 10, 16));
	public static IFuzzySet accelerationP = new CalculatedFuzzySet(Variables.accelerationDomain, StandardFuzzySets.gammaFunction(55, 70));
	public static IFuzzySet accelerationVP = new CalculatedFuzzySet(Variables.accelerationDomain, StandardFuzzySets.gammaFunction(80, 100));
	
	public static IFuzzySet angleVN = new CalculatedFuzzySet(Variables.angleDomain, StandardFuzzySets.lFunction(0, 20));
	public static IFuzzySet angleN = new CalculatedFuzzySet(Variables.angleDomain, StandardFuzzySets.lFunction(10, 20));
	public static IFuzzySet angleZ = new CalculatedFuzzySet(Variables.angleDomain, StandardFuzzySets.lambdaFunction(75, 90, 105));
	public static IFuzzySet angleP = new CalculatedFuzzySet(Variables.angleDomain, StandardFuzzySets.gammaFunction(150, 160));
	public static IFuzzySet angleVP = new CalculatedFuzzySet(Variables.angleDomain, StandardFuzzySets.gammaFunction(160, 170));
	public static IFuzzySet angleVVP = new CalculatedFuzzySet(Variables.angleDomain, StandardFuzzySets.gammaFunction(175, 179));
	public static IFuzzySet angleVVN = new CalculatedFuzzySet(Variables.angleDomain, StandardFuzzySets.lFunction(0, 5));
	
}
