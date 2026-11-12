package hr.fer.zemris.hw04.algorithm;

import java.util.Optional;

import hr.fer.zemris.hw04.function.IFunction;
import hr.fer.zemris.hw04.main.Point;

/**
 * Sučelje <code>GeneticAlgorithm</code> predstavlja genetski algoritam.
 * 
 * @author Dorian Kablar
 *
 */
public interface GeneticAlgorithm {

	/**
	 * Metoda koja optimira zadanu funkciju koristeći GA.
	 * 
	 * @param function funkcija koju treba optimirati
	 * @return rješenje
	 */
	public Optional<Point> solve(IFunction function);
}
