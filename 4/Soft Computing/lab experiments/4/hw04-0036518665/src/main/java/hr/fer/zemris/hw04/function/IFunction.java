package hr.fer.zemris.hw04.function;

import hr.fer.zemris.hw04.main.Point;

/**
 * Sučelje <code>IFunction</code> predstavlja funkciju koju treba optimirati
 * 
 * @author Dorian Kablar
 *
 */
public interface IFunction {

	/**
	 * Metoda koja vraća vrijednost funkcije u zadanoj točki
	 * 
	 * @param p točka za koju treba izračunati vrijednost funkcije
	 * @return vrijednost funkcije
	 */
	public double valueAt(Point p);
	
	/**
	 * Metoda koja vraća broj varijabli koje treba optimirati
	 * 
	 * @return broj varijabli funkcije
	 */
	public int noOfVariables();
}
