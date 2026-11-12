package hr.fer.zemris.hw04.mutation;

import hr.fer.zemris.hw04.main.Point;

/**
 * Sučelje <code>IMutationOperator</code> predstavlja operator mutacije
 * 
 * @author Dorian Kablar
 *
 */
public interface IMutationOperator {

	/**
	 * Metoda koja mutira dijete s zadanom vjerojatnošću
	 * 
	 * @param point dijete
	 * @param probability vjerojatnost mutacije
	 */
	public void mutate(Point point, double probability);
}
