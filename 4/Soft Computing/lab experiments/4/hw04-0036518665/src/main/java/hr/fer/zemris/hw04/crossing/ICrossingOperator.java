package hr.fer.zemris.hw04.crossing;

import hr.fer.zemris.hw04.main.Point;

/**
 * Sučelje <code>ICrossingOperator</code> predstavlja operator križanja
 * 
 * @author Dorian Kablar
 *
 */
public interface ICrossingOperator {

	/**
	 * Metoda koja obavlja križanje dva roditelja, i vraća dijete
	 * 
	 * @param p1 prvi roditelj
	 * @param p2 drugi roditelj
	 * @return dijete
	 */
	public Point cross(Point p1, Point p2);
}
