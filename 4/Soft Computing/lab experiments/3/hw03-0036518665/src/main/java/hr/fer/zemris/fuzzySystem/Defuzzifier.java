package hr.fer.zemris.fuzzySystem;

import hr.fer.zemris.fuzzy.IFuzzySet;

public interface Defuzzifier {

	public int vrijednost(IFuzzySet set);
}
