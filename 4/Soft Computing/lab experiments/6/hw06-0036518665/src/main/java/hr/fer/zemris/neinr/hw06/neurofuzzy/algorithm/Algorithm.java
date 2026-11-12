package hr.fer.zemris.neinr.hw06.neurofuzzy.algorithm;

import hr.fer.zemris.neinr.hw06.neurofuzzy.utils.Matrica;

public interface Algorithm {

	public Matrica train(int noOfRules, Matrica data, Matrica fValues);
	
	public double test(Matrica testData);
}
