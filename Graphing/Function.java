import java.util.ArrayList;

public class Function {

	public static ArrayList<Function> funcs = new ArrayList<>();
	private static Character[][] possibleOps = {
			{ '^' },
			{ '*', '/', '%' },
			{ '+', '-' },
	};
	private static Character[] opsList = { '^', '+', '-', '*', '/', '%' };
	private static String os = "^+-*/%";

	private int func1 = -1;
	private int func2 = -1;
	private int operator;
	private int index;

	private String num;

	public String getNum() {
		return num;
	}

	// Constructor stuff

	public Function(int f1, int op, int f2) {
		func1 = f1;
		func2 = f2;
		operator = op;
		addFun(this);
	}

	public Function(String n) {
		num = n;
		addFun(this);
	}

	public static void addFun(Function f) {
		funcs.add(f);
		f.index = funcs.size() - 1;
	}

	// Printing function
	public void printFun() {
		printFun(true);
	}

	public void printFun(boolean b) {
		if (func1 == -1) {
			System.out.print(num);
		} else {
			System.out.print('(');
			funcs.get(func1).printFun(false);
			System.out.print(opsList[operator]);
			funcs.get(func2).printFun(false);
			System.out.print(')');
		}
		if (b) {
			System.out.println("");
		}
	}

	// Making new function out of string

	public static Function createFunction(String input) {
		int ct = 0;
		while (!inFunNot(input)) {
			input = simplify(input);
			input = simpFirstTerms(input);
			if (ct > 10)
				break;
		}
		return funcs.get(Integer.parseInt(input.substring(1, input.length() - 1)));
	}

	public static String simplify(String input) {

		// Gets rid of spaces
		input = input.replace(" ", "");

		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == ')') {
				for (int j = i; j >= 0; j--) {
					if (input.charAt(j) == '(') {
						String rep = input.substring(j, i + 1);
						input = input.replace(rep, subFunction(rep, true));
					}
				}
			}
		}

		// Finds Numbers
		int ct = 0;
		for (int i = 0; i < input.length(); i++) {
			if ("1234567890qwertyuiopasdfghjklzxcvbnm.".contains(Character.toString(input.charAt(i)))) {
				ct++;
			}
		}
		if (ct == input.length() && !inFunNot(input)) {
			input = subFunction(new Function(input));
		}
		return input;
	}

	// Finds two terms with an operator inbetween
	public static String simpFirstTerms(String input) {
		outerloop: for (int i = 0; i < possibleOps.length; i++) {
			for (int j = 0; j < input.length(); j++) {
				char c = input.charAt(j);
				if (charArrayContains(possibleOps[i], c) && i != 0) {
					String first = "";
					String second = "";
					int f1 = 0;
					int f2 = 0;

					for (int k = j - 1; k >= 0; k--) {
						if ((os.contains(Character.toString(input.charAt(k))) && k != 0)) {
							first = input.substring(k + 1, j);
							f1 = k + 1;
						}
					}
					if (first == "") {
						f1 = 0;
						first = input.substring(0, j);
					}

					for (int k = j + 1; k < input.length() - 1; k++) {
						if (os.contains(Character.toString(input.charAt(k)))) {
							f2 = k;
							second = input.substring(j + 1, k);
						}
					}
					if (second == "") {
						f2 = input.length();
						second = input.substring(j + 1, input.length());
					}

					int firstFun = -1;
					if (inFunNot(first)) {
						firstFun = Integer.parseInt(first.substring(1, first.length() - 1));
					} else {
						Function f = new Function(first);
						firstFun = f.index;
					}

					int secFun = -1;
					if (inFunNot(second)) {
						secFun = Integer.parseInt(second.substring(1, second.length() - 1));
					} else {
						Function f = new Function(second);
						secFun = f.index;
					}

					String rep = input.substring(f1, f2);
					input = input.replace(rep,
							subFunction(new Function(firstFun, os.indexOf(input.charAt(j)), secFun)));
					break outerloop;
				}
			}
		}
		return input;
	}

	// Substitutes function in string
	public static String subFunction(String replace, boolean par) {
		if (par)
			replace = replace.substring(1, replace.length() - 1);
		Function f;
		if (inFunNot(replace))
			f = funcs.get(Integer.parseInt(replace.substring(1, replace.length() - 1)));
		else
			f = createFunction(replace);
		return "$" + f.index + "$";
	}

	public static String subFunction(Function f) {
		return "$" + f.index + "$";
	}

	// Array contains
	public static boolean charArrayContains(Character[] cs, Character c) {
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] == c) {
				return true;
			}
		}
		return false;
	}

	// Counts given chars in given string
	public static boolean ctChar(String st, char c, int ct) {
		for (int i = 0; i < st.length(); i++) {
			if (st.charAt(i) == c) {
				ct--;
			}
		}
		return ct == 0;
	}

	// Checks if string is in function notation $n$
	public static boolean inFunNot(String input) {
		return ctChar(input, '$', 2) && (input.charAt(0) == '$' && input.charAt(input.length() - 1) == '$');
	}
}
