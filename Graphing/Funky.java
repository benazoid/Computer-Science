public class Funky {

	public static void main(String[] args) {
		int num1 = 1;
		int num2 = 0;
		while (num1 <= 55) {
			int stor = num1;
			num1 += num2;
			num2 = stor;
			System.out.println(stor);
		}
	}

	public static final double PI = 3.14159265358979323846;
	public static final double E = 2.71828182845904523536;

	private static int defaultAmt = 100;

	public static interface FunctStore {
		public double function(double x);
	}

	// Function for sin(x) in radians
	// Domain - All Reals, Range - [-1, 1]
	// Approximataion
	public static double sin(double x) {
		double b = ((x - PI) % (2 * PI)) + PI;// - PI;
		double s = 0;
		for (int i = 0; i < defaultAmt / 10; i++) {
			System.out.println(b);
			s += intPow(-1, i) * (intPow(b, 2 * i + 1) / fact(2 * i + 1));
		}
		return s;
	}

	// Function for a^b
	// Domain (a) - [0, inf), Domain (b) - All Reals, Range - All Reals
	// Approximation
	public static double pow(double a, double b) {
		return exp(b * ln(a), defaultAmt);
	}

	// Function for n! (n factorial)
	// Domain - All Real Integers > 0, Range - Some Real Integers > 0
	public static int fact(int n) {
		if (n == 0)
			return 1;
		return n * fact(n - 1);
	}

	// Function for a^n
	// Domain (a) - All Reals, Domain (n) - All Real Integers > 0
	// Range - All Reals
	public static double intPow(double a, int n) {
		int p = 1;
		for (int i = 0; i < n; i++) {
			p *= a;
		}
		return p;
	}

	// Function for e^x (e being Euler's Number ~ 2.71)
	// Domain - All Reals, Range - All Reals
	// Approximation
	public static double exp(double x, int precision) {
		final int amt = precision;
		double s = 0;
		for (int i = 0; i < amt; i++) {
			s += intPow(x, i) / fact(i);
		}
		return s;
	}

	public static double exp(double x) {
		return exp(x, 100);
	}

	// Function for ln(x) (Natural Log, Log Base e)
	// Domain - (0, inf), Range - All Reals
	public static double ln(double x) {
		FunctStore f = new FunctStore() {
			public double function(double x) {
				return 1 / x;
			}
		};
		return defInt(1, x, f, defaultAmt);
	}

	public static double abs(double x) {
		if (x > 0)
			return x;
		return -x;
	}

	// Definate integral of the function fs.function from lower to higher
	// Approximation with reimman sum using given precision
	public static double defInt(double lower, double higher, FunctStore fs, int precision) {
		final int amt = precision;
		final double dx = (higher - lower) / amt;
		double s = 0;
		for (int i = 0; i < amt; i++) {
			s += fs.function(lower + i * dx) * dx;
		}
		return s;
	}

	public static class Vector2 {

		public double x;
		public double y;

		public Vector2() {

		}

		public Vector2(double x, double y) {
			this.x = x;
			this.y = y;
		}

	}

}
