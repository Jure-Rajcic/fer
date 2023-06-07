package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * class ComplexRootedPolynomial represents polynomial of complex numbers
 * f(z) = z0*(z-z1)*(z-z2)*...*(z-zn)
 * 
 * @author Jure Rajcic
 *
 */
public class ComplexRootedPolynomial {

	private final Complex constant;
	private final List<Complex> roots = new ArrayList<>();

	/**
	 * constructor
	 * z0 = @param constant
	 * z1, ... zn = @param roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.constant = constant;
		for (Complex complex : roots) {
			this.roots.add(complex);
		}
	}

	/**
	 * @return new Complex number which is result of f(@param z ) where f i
	 *         represents polynomial function
	 */
	// Radi se o polinomu f(z) oblika z0*(z-z1)*(z-z2)*...*(z-zn)
	public Complex apply(Complex z) {
		Complex result = constant;
		for (Complex zi : roots) {
			result = result.multiply(z.sub(zi));
		}
		return result;
	}

	/**
	 * 
	 * @return new ComplexPolynomial for of this polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial res = new ComplexPolynomial(constant);
		for (Complex c : roots)
			res = res.multiply(new ComplexPolynomial(c.negate(), Complex.ONE));
		return res;
	}

	@Override
	public String toString() {
		String s = constant.toString();
		// sb.append("(" + this.constant.toString() + ")");
		for (Complex complex : roots)
			s += String.format("*(z-%s)", complex.toString());
		return s;
	}

	/**
	 * A method that finds the index of the closest zero point for a given complex
	 * number @param z within a given threshold.
	 * If no such zero point exists, -1 is returned
	 *
	 * @param treshold the limit within which the difference between the zero point
	 *                 and @param z must be
	 * @return index of the nearest zero point, -1 if there is none
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		double min_dif = treshold;
		for(int i = 0; i < roots.size(); i++) {
			double diff = Math.sqrt(roots.get(i).sub(z).module());
			if(diff	< min_dif) index = i;
			min_dif = Math.min(diff, min_dif);
		}
		return index;
	}
}
