package yang.utility;

import java.awt.Point;

public class TransEquation {

	private int xCons;	// Constrains of x value
	private int yCons;	// Constrains of y value
	
//	private int preX;
//	private int preY;
	
	public TransEquation() {
		setConstrains(0, 0);
	}
	
	public TransEquation(int xCons, int yCons) {
		setConstrains(xCons, yCons);
	}
	
	public TransEquation(Point pConstrains) {
		this.xCons = pConstrains.x;
		this.yCons = pConstrains.y;
	}
	
	public void setConstrains(int xCons, int yCons) {
		this.xCons = xCons;
		this.yCons = yCons;
	}
	
	/** the transformation to sphere */
	public Point toSphere(int x, int y) {
		int r = Math.min(xCons, yCons);
		int x1 = (int)(r * Math.sin(x * Math.PI / (2 * r)) + 0.5);
		int y1 = (int)(r * Math.sin(y * Math.PI / (2 * r)) + 0.5);
		
		return check(x1, y1);
	}
	
	/**A transformation by the polar coordinate*/
	public Point toPolarCoord(int x, int y) {
		double x1 = (double)x * 2 / xCons - 1.0;	// -1 <= x1 <= 1
		double y1 = (double)y * 2 / yCons - 1.0;    // -1 <= y1 <= 1
		double r = 0.5 * (y1 - 1);
		double sita = x1 * Math.PI;
		int x2 = (int) ((r * Math.sin(sita) + 1) * xCons / 2 + 0.5);
		int y2 = (int) ((r * Math.cos(sita) + 1) * yCons / 2 + 0.5);
		
		return check(x2, y2);
	}
	
//	public Point warping(int x, int y) {
//		
//	}
	
	private Point check(int x, int y) {
//		if(Math.abs(x-preX) > 1 || Math.abs(y-preY) > 1) {	// not connected
//			if(x-preX == 0) y = preY < y ? preY + 1 : preY - 1;
//			else if(y-preY == 0) x = preX < x ? preX + 1 : preX - 1;
//			else if(x - preX < 0 && y - preY < 0) { x = preX - 1; y = preY - 1; }
//			else if(x - preX > 0 && y - preY < 0) { x = preX + 1; y = preY - 1; }
//			else if(x - preX < 0 && y - preY > 0) { x = preX - 1; y = preY + 1; }
//			else {
//				x = preX + 1; y = preY + 1;
//			}
//		}
//		preX = x;
//		preY = y;
		if(x >= xCons) x = xCons-1;
		if(y >= yCons) y = yCons-1;
		if(x < 0) x = 0;
		if(y < 0) y = 0;
		return new Point(x, y);
	}
}
