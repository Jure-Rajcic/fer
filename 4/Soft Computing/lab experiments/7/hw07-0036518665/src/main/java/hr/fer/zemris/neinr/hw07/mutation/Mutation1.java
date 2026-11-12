package hr.fer.zemris.neinr.hw07.mutation;

import java.util.Random;

public class Mutation1 implements IMutationOperator {

	private double sigma;
	
	public Mutation1(double sigma) {
		this.sigma = sigma;
	}
	
	@Override
	public void mutate(double[] solution, double p, Random rand) {
		for(int i = 0; i < solution.length; i++) {
			if(rand.nextDouble() <= p)
				solution[i] = solution[i] + rand.nextGaussian()*sigma;
		}
	}

}
