package hr.fer.zemris.hw04.mutation;

import java.util.Random;

import hr.fer.zemris.hw04.main.Point;

/**
 * Razred <code>Mutation</code> predstavlja mutaciju koja uz zadanu vjerojatnost rješenju dodaje malu
 * vrijednost iz Gaussove distribucije
 * 
 * @author Dorian Kablar
 *
 */
public class Mutation implements IMutationOperator {

	private final static double sigma = 0.9;
	private Random rand;
	
	public Mutation(Random rand) {
		this.rand = rand;
	}
	
	@Override
	public void mutate(Point point, double probability) {
		for(int i = 0; i < point.getPoints().length; i++) {
			if(rand.nextDouble() <= probability)
				point.setPoint(i, point.getPoint(i) + rand.nextGaussian()*sigma);
		}
	}
}
