
package yang.math;

public class Arithmetic {
	
	public static final float EPSILON_FLOAT 	= 1e-35f;	// smallest possible float denominator is ~ 1e-38f
	public static final double EPSILON_DOUBLE 	= 1e-300;	// smallest possible float denominator is ~ 1e-308f
		
	public static int sqr(int x) {
		return x*x;
	}
	
	public static float sqr(float x) {
		return x*x;
	}
	
	public static double sqr(double x) {
		return x*x;
	}
	
	public static int mod(int a, int b) {
		if (b == 0)
			return a;
		if (a * b >= 0)	// a,b are either both positive or negative
			return a - b * (a / b);	
		else
			return a - b * (a / b - 1);
	}
	
	public static double mod(double a, double n) {
		return a - n * Math.floor(a / n);
	}

}
