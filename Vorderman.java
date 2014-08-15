import java.util.*;

public class Vorderman {
	static double target;
	static double closestResult;
	static String closestEquation = null;

	public static void main(String[] args) {
		List<Double> numbers = new ArrayList<Double>();
		for (int i=0; i<6; i++) {
			numbers.add(Double.parseDouble(args[i]));
		}

		target = Double.parseDouble(args[6]);

		List<List<Double>> permutations = generatePermutations(numbers);

		for (List<Double> p : permutations) {
			solve(p, null, 0, false);
		}

		if (closestResult == target) {
			System.out.println("Exact solution:");
			System.out.println(prettify(closestEquation));
		}
		else {
			System.out.println("No exact solution. Closest was:");
			System.out.println(prettify(closestEquation) +" = " +closestResult);
		}
	}

	public static String prettify(String equation) {
		return equation.replace(".0", "").replaceAll("^\\((.+?)\\)$", "$1");
	}

	public static void solve(List<Double> list, String equation, double total, boolean lastOperatorWasWeak) {
		if (list.size() == 0) {
			return;
		}

		if ((total == target) || (Math.abs(total-target) < Math.abs(closestResult-target))) {

			if (closestResult != total) {
				closestEquation = null;
			} 
			if ((closestEquation == null) || (equation.length() < closestEquation.length())) {
				closestEquation = equation;
				closestResult = total;
			}
		}

		List<Double> newList = new ArrayList<Double>(list);
		Double f = newList.remove(0);
		if (equation == null) {
			solve(newList, ""+f, f, false);
		}
		else {
			solve(newList, equation +" + " +f, total + f, true);
			solve(newList, equation +" - " +f, total - f, true);
			
			
			if (lastOperatorWasWeak) {
				equation = "(" +equation +")";
			}
			solve(newList, equation +" * " +f, total * f, false);
			solve(newList, equation +" / " +f, total / f, false);
		}
	}

	public static List<List<Double>> generatePermutations(List<Double> list) {
		if (list.size() == 0) { 
			ArrayList<List<Double>> result = new ArrayList<List<Double>>();
			result.add(new ArrayList<Double>());
			return result;
		}

		Double insert = list.remove(0);
		List<List<Double>> result = new ArrayList<List<Double>>();
		List<List<Double>> permutations = generatePermutations(list);
		for (List<Double> smallerPermutated : permutations) {
			for (int i=0; i <= smallerPermutated.size(); i++) {
				List<Double> temp = new ArrayList<Double>(smallerPermutated);
				temp.add(i, insert);
				result.add(temp);
			}
		}
		return result;
	}
}
