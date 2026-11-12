package hr.fer.zemris.hw04.crossing;

import hr.fer.zemris.hw04.main.Point;

/**
 * Razred <code>Crossing</code> predstavlja križanje kao aritmetičku sredinu roditelja
 * 
 * @author Dorian Kablar
 *
 */
public class Crossing implements ICrossingOperator {

	@Override
	public Point cross(Point p1, Point p2) {
		Point child = new Point(p1.getPoints().length);
		for(int i = 0; i < child.getPoints().length; i++)
			child.setPoint(i, (p1.getPoint(i) + p2.getPoint(i)) / 2.);
		return child;
	}
}
