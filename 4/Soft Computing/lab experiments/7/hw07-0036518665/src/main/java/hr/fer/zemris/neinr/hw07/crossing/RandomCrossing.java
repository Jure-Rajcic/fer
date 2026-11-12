package hr.fer.zemris.neinr.hw07.crossing;

import java.util.Random;

public class RandomCrossing implements ICrossingOperator {

	private Random rand;
	
	public RandomCrossing(Random rand) {
		this.rand = rand;
	}
	
	@Override
	public double[] cross(double[] p1, double[] p2) {
		double[] child = new double[p1.length];
		
		for(int i = 0; i < p1.length; i++) {
			child[i] = rand.nextDouble() < 0.5 ? p1[i] : p2[i];
		}
		
		return child;
	}
}
