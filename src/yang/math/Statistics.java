
package yang.math;

import org.apache.commons.math3.stat.correlation.Covariance;


public class Statistics {
	
	public static double[][] covarianceMatrix(double[][] samples) {
		Covariance sm = new Covariance(samples);
		return sm.getCovarianceMatrix().getData();
	}
	
	public static void main(String[] args) {
		// example from UTICS-C Appendix:
		// n = 4 samples
		// m = 3 dimensions
		double[][] samples = { // samples[i][j], i = column index, j = row index.
				{75, 37, 12},	// i = 0
				{41, 27, 20},	// i = 1
				{93, 81, 11},	// i = 2
				{12, 48, 52}	// i = 3
		};
		
		// covariance matrix Cov (3x3)
		double[][] cov = covarianceMatrix(samples);
		System.out.println("cov = " + Matrix.toString(cov));
		
		System.out.println();
		
		double[][] icov = Matrix.inverse(cov);
		System.out.println("icov = " + Matrix.toString(icov));
	}
	
}
