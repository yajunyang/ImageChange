
package yang.math;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;

/**
 * This class implements the Mahalanobis distance using the Apache commons math library.
 * UNFINISHED! Complete distance/magnitude methods.
 */
public class MahalanobisDistance extends VectorNorm {
	
	final int N;			// number of samples
	final int K;			// feature dimension
	final double[][] iS;	// inverse covariance matrix = S^{-1} of size K x K
	
	public MahalanobisDistance (double[][] samples) {
		N = samples.length;
		K = samples[0].length;
		Covariance cov = new Covariance(samples);
		RealMatrix S = cov.getCovarianceMatrix();
		// condition the covariance matrix to avoid singularity 
		// (add a small quantity to the diagonal)
		for (int i = 0; i < K; i++) {
			S.addToEntry(i, i, 0.0001);
		}
		// get the inverse covariance matrix
		RealMatrix iSM = new LUDecomposition(S).getSolver().getInverse();
		iS = iSM.getData();
	}
	
	public int getNumberOfSamples() {
		return N;
	}

	public int getSampleDimension() {
		return K;
	}
	
	@Override
	public double distance(double[] a, double[] b) {
		return Math.sqrt(distance2(a, b));
	}
	
	// check calculation again!
	@Override
	public double distance2 (double[] X, final double[] Y) {
		if (X.length != K || Y.length != K) {
			throw new IllegalArgumentException("vectors must be of length " + K);
		}
		// d(X,Y) = sqrt[(X-Y)^T . S^{-1} . (X-Y)]		
		int m = X.length;
		double[] diffXY = new double[m];	// = (X-Y)
		for (int j=0; j<m; j++) {
			diffXY[j] = X[j] - Y[j];
		}
		double[] SdiffXY = new double[m];   // = S^{-1} . (X-Y)
		for (int j=0; j<m; j++) {	
			for (int i=0; i<m; i++) {
				SdiffXY[j] += iS[i][j] * diffXY[i];
			}
		}
		double d = 0;
		for (int i=0; i<m; i++) {
			d += diffXY[i] * SdiffXY[i];
		}									// d = (X-Y)^T . S^{-1} . (X-Y)
		return d;
	}

	@Override
	public double distance(int[] a, int[] b) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double distance2(int[] a, int[] b) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double magnitude(double[] X) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double magnitude(int[] X) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getScale(int n) {
		return 1.0;
	}
}
