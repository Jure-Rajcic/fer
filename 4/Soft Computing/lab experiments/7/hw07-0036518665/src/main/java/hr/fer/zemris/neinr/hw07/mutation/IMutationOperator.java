package hr.fer.zemris.neinr.hw07.mutation;

import java.util.Random;

public interface IMutationOperator {

	public void mutate(double[] solution, double p, Random rand);
}
