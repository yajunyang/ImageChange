package yang.utility;

import java.util.Vector;

/**
 * The class can only solve 2x2 or 3x3 hessian matrix.<br>
 * <b>If you want to use this class:
 * <hr><b> 
 * * Initial hessian matrix by constructing method or setHessian2DMatrix([]) method.<br>
 * * Call run() method to calculate the characteristics values and eigen vectors.<br>
 * * If you don't take the order. the exception will happen.
 * <b/><hr/>
 * @author yang
 */
public class HessianMatrix {

	private float[][] hessian;
	private int dimen;
	private double value1;
	private double value2;
	private double value3;
	private Vector<Double> eigenVector;
	
	private boolean valueState = false;
	private boolean vectorState = false;
	
	private static final double TWOPI = 2 * Math.PI;
	
	/** Reference Relationship with {@link hessian} parameter*/
	public HessianMatrix(float[][] hessian) {
		setHessian2DMatrix(hessian);
	}

	public void run() {
		calEigenValues();
		calAbsMaxEigenVector();
	}
	
	public void setHessian2DMatrix(float[][] hessian) {
		if (null == hessian) {
			throw new IllegalAccessError("Null Parameter!");
		}

		if (hessian.length != hessian[0].length) {

		}
		dimen = hessian.length;
		this.hessian = hessian;
		
		valueState = false;
		vectorState = false;
	}
	
	public double[] getValues() {
		if(!valueState) 
			throw new IllegalAccessError("You must call the calEigenValues() first");
		double[] values = new double[3];
		values[0] = value1;
		values[1] = value2;
		values[2] = value3;
		return values;
	}
	
	public Vector<Double> getEigenVector() {
		if(!vectorState) 
			throw new IllegalAccessError("You must call the method calAbsMaxEigenVector() first");
		return eigenVector;
	}
	
	public int getDimension() {
		return dimen;
	}
	
	public void calEigenValues() {
		if(dimen == 2) {
			final double b = -(hessian[0][0] + hessian[1][1]);
			final double c = hessian[0][0] * hessian[1][1] - hessian[0][1] * hessian[1][0];
			final double q = -0.5 * (b + (b < 0 ? -1 : 1) * Math.sqrt(b * b - 4 * c));
			value1 = q;
			value2 = c / q;
			if(Math.abs(value1) < Math.abs(value2)) {
				value1 = c / q;
				value2 = q;
			}
		} else if(dimen == 3){
			final double a = -(hessian[0][0] + hessian[1][1] + hessian[2][2]);
			final double b = hessian[0][0] * hessian[1][1] + hessian[0][0] * hessian[2][2] + hessian[1][1]
					* hessian[2][2] - hessian[0][1] * hessian[0][1] - hessian[0][2] * hessian[0][2] - hessian[1][2]
					* hessian[1][2];
			final double c = hessian[0][0] * (hessian[1][2] * hessian[1][2] - hessian[1][1] * hessian[2][2])
					+ hessian[1][1] * hessian[0][2] * hessian[0][2] + hessian[2][2] * hessian[0][1] * hessian[0][1]
					- 2 * hessian[0][1] * hessian[0][2] * hessian[1][2];
			final double q = (a * a - 3 * b) / 9;
			final double r = (a * a * a - 4.5 * a * b + 13.5 * c) / 27;
			final double sqrtq = (q > 0) ? Math.sqrt(q) : 0;
			final double sqrtq3 = sqrtq * sqrtq * sqrtq;
			double absh1, absh2, absh3;
			if (sqrtq3 == 0) {
				absh1 = absh2 = absh3 = 0;
				value1 = value2 = value3 = 0;
			} else {
				final double rsqq3 = r / sqrtq3;
				final double angle = (rsqq3 * rsqq3 <= 1) ? Math.acos(rsqq3) : Math.acos(rsqq3 < 0 ? -1: 1);
				
				value1 = -2 * sqrtq * Math.cos(angle / 3) - a / 3;
				value2 = -2 * sqrtq * Math.cos((angle + TWOPI) / 3) - a / 3;
				value3 = -2 * sqrtq * Math.cos((angle - TWOPI) / 3) - a / 3;
				
				absh1 = Math.abs(value1);
				absh2 = Math.abs(value2);
				absh3 = Math.abs(value3);
			} // get the characteristic value's absolute value be ordered by |a1| >= |a2| >= |a3|

			if (absh2 < absh3) {
				final double tmp = value2;
				value2 = value3;
				value3 = tmp;
			}
			if (absh1 < absh2) {
				final double tmp1 = value1;
				value1 = value2;
				value2 = tmp1;
				if (absh2 < absh3) {
					final double tmp2 = value2;
					value2 = value3;
					value3 = tmp2;
				}
			}
		}
		valueState = true;
	}
	
	public void calAbsMaxEigenVector() {
		calAbsMaxEigenVector(0.01, 1e-10);
	}
	
	public void calAbsMaxEigenVector(double allowCoeError, double allowDetError) {
		if(!valueState) 
			throw new IllegalAccessError("You must calculate the eigen values of the hessian matrix first!");
		
		if(dimen == 2) {
			double x=0, y=0;
			if (Math.abs(hessian[0][0] - value1) > allowCoeError
					&& // If element not near zero
					Math.abs(hessian[0][1]) > allowCoeError
					&& Math.abs(hessian[1][1] - value1) > allowCoeError) {
				double det = (hessian[0][0] - value1) * (hessian[1][1] - value1) - hessian[0][1] * hessian[0][1]; // calculate determinant
				if (Math.abs(det) < allowDetError) {
					x = -hessian[0][1];
					y = hessian[0][0] - value1;
				} else {
					x = y = 0;
				}
			}
			eigenVector.add(x);
			eigenVector.add(y);
		} else if(dimen == 3){
			throw new IllegalAccessError("3D condition exclude!");
		}
		vectorState = true;
	}
}
