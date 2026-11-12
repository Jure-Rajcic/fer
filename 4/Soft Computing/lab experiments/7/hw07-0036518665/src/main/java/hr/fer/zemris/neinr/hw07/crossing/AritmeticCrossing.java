package hr.fer.zemris.neinr.hw07.crossing;

public class AritmeticCrossing implements ICrossingOperator {

	@Override
	public double[] cross(double[] p1, double[] p2) {
		double[] child = new double[p1.length];
		
		for(int i = 0; i < child.length; i++) {
			child[i] = (p1[i] + p2[i]) / 2.;
		}
		
		return child;
	}
}
