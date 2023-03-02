
public class BetterWorstSort {

	public static void main(String[] args) {

		int[] list = { 1, 2, 3, 4 };

		for (int j = 0; j < fact(list.length); j++) {
			for (int i = 0; i < list.length; i++) {
				System.out.print(permutationAtIndex(list.length, j, i) + 1);
			}
			System.out.println();
		}
	}

	public static int[] worst(int[] list, int depth) {
		int[] out = new int[list.length];

		int finalSize = factDepth(list.length, depth);

		int bestIndex = 0;
		int bestValue = 0;
		for (int i = 0; i < finalSize; i++) {
			int value = getValue(list, depth, i);
			if (value > bestValue) {
				bestValue = value;
				bestIndex = i;
			}
		}

		return out;
	}

	public static int getValue(int[] list, int depth, int position) {
		if (depth == 0)
			return list[position];

		int ct = 0;
		int scrambledLength = factDepth(list.length, depth - 1);
		for (int i = 1; i < scrambledLength; i++) {
			int valueIndex = permutationAtIndex(list.length - 1, scrambledLength, i);
			int value = getValue(list, depth - 1, valueIndex);
			int prevIndex = permutationAtIndex(list.length - 1, scrambledLength, i - 1);
			int prevValue = getValue(list, depth - 1, prevIndex);

			if (value > prevValue) {
				ct++;
			}
		}
		return ct;
	}

	public static int permutationAtIndex(int length, int version, int index) {
		if (index == 0)
			return version / fact(length - 1);

		int firstDigit = version / fact(length - 1);
		int newVersion = version % fact(length - 1);

		int prevLayer = permutationAtIndex(length - 1, newVersion, index - 1);

		// Translating between layers
		if (prevLayer >= firstDigit)
			prevLayer++;

		return prevLayer;
	}

	public static int fact(int n) {
		if (n == 0)
			return 1;
		return n * fact(n - 1);
	}

	public static int factDepth(int startLength, int depth) {
		int amt = startLength;
		for (int i = 0; i < depth - 1; i++)
			amt = fact(amt);
		return amt;
	}

}
