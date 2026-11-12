package hr.fer.zemris.hw04.main;

/**
 * Razred <code> Point </code> prikazuje točku u n-dimenzionalnom prostoru
 * 
 * @author kabla
 *
 */
public class Point {

	private double[] points;
	
	public Point(int length) {
		this.points = new double[length];
		for(int i = 0; i < length; i++)
			this.points[i] = 0.;
	}
	
	public Point(double ... points) {
		this.points = points;
	}
	
	public double[] getPoints() {
		return this.points;
	}

	public double getPoint(int index) {
		return points[index];
	}
	
	public void setPoint(int index, double value) {
		this.points[index] = value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i = 0; i < points.length; i++) {
			sb.append("x" + (i + 1) + "=");
			if(i != points.length - 1)
				sb.append(points[i]  + ", ");
			else
				sb.append(points[i]);
		}
		sb.append("]");
		return sb.toString();
	}
}
