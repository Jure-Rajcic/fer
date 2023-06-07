package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * class ComplexPolynomial represents polynomial of complex numbers
 * f(z) = zn*z^n + zn-1*z^n-1 + ... + z1*z + z0
 * 
 * @author Jure Rajcic
 *
 */
public class ComplexPolynomial {

	private final List<Complex> factors = new ArrayList<>();

	/**
	 * constructor
	 * 
	 * @param factors list of complex numbers that form polynomial
	 */
	public ComplexPolynomial(Complex... factors) {
		for (Complex complex : factors)
			this.factors.add(complex);
	}

	/**
	 * @return degree of this polynomial
	 */
	public short order() {
		return (short) (factors.size() - 1);
	}

	/**
	 * @return new ComplexPolynomial creted by multiplying this and @param p
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] arr = new Complex[this.order() + p.order() + 1];
		for (int i = 0; i < arr.length; i++)
			arr[i] = Complex.ZERO;
		for (int i = 0; i < factors.size(); i++)
			for (int j = 0; j < p.factors.size(); j++)
				arr[i + j] = arr[i + j].add(this.factors.get(i).multiply(p.factors.get(j)));
		return new ComplexPolynomial(arr);
	}

	/**
	 * @return new ComplexPolynomial creted by derivation of this polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] arr = new Complex[this.order()];
		for (int i = 1; i <= arr.length; i++)
			arr[i - 1] = factors.get(i).multiply(new Complex(i, 0));
		return new ComplexPolynomial(arr);
	}

	/**
	 * 
	 * @return new Complex number which is result of f(@param z ) where f i
	 *         represents polynomial function
	 */
	// f(z) oblika (zn)*z^n+(zn-1)^zn-1+...+(z2)*z^2+(z1)*z^1+z0*z^0
	public Complex apply(Complex z) {
		if (this.factors.isEmpty())
			throw new IllegalArgumentException("polynomial is not defined!");
		Complex result = Complex.ZERO;
		for (int i = 0; i < factors.size(); i++)
			result = result.add(factors.get(i).multiply(z.power(i)));
		return result;
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = this.order(); i > 0; i--)
			s += String.format("%s*z^%d+", factors.get(i), i);
		s += String.format("%s", factors.get(0));
		return s;
	}
}
