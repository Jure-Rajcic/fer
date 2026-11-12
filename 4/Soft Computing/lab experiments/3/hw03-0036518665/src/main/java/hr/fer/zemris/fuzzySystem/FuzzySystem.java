package hr.fer.zemris.fuzzySystem;

import hr.fer.zemris.fuzzy.IFuzzySet;

public interface FuzzySystem {

	public int zaključi(int L, int D, int LK, int DK, int V, int S, boolean print);
	
	public IFuzzySet getZakljucakFoRule(int number);
}
