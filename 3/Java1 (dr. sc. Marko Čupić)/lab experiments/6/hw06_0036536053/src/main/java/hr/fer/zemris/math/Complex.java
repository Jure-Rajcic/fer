package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * class Complex represents Complex number z = 're' + i'im' with some basic
 * algebra
 * 
 * @author Jure Rajcic
 *
 */
public class Complex {

	private final double re;
	private final double im;

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);

	public Complex() {
		this(0.0, 0.0);
	}

	/**
	 * constructor
	 * 
	 * @param re real part
	 * @param im imaginary part
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	public double getRe() {
		return this.re;
	}

	public double getIm() {
		return im;
	}

	/**
	 * @return modul of complex number
	 */
	public double module() {
		return Math.sqrt(Math.pow(re, 2) + Math.pow(im, 2));
	}

	/**
	 * 
	 * @return new complex number creted by multiplying this and @param c
	 */
	public Complex multiply(Complex c) {
		// (a + ib) (c + id) = (ac - bd) + i(ad + bc)
		return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
	}

	/**
	 * @return new complex number creted by dividing this and @param c
	 */
	public Complex divide(Complex c) {
		// z1 = a + ib, z2 = c + id
		// z1 / z2 = (ac + bd)/(c^2 + d^2) + i(bc - ad)(c^2 + d^2)
		double div = Math.pow(c.module(), 2);
		return new Complex((re * c.re + im * c.im) / div, (im * c.re - re * c.im) / div);
	}

	/**
	 * @return new complex number creted by addition of this and @param c
	 */
	public Complex add(Complex c) {
		return new Complex(re + c.re, im + c.im);
	}

	/**
	 * @return new complex number creted by subtraction of this and @param c
	 */
	public Complex sub(Complex c) {
		return add(c.negate());
	}

	/**
	 * @return new complex number creted by negating this
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}

	/**
	 * private method to calculate complex number on potention of double
	 * (reusability when finding root)
	 * 
	 * @return new complex number creted by raising this to power of (double) @param
	 *         n
	 * @throws IllegalArgumentException if n is negative
	 */
	private Complex power(double n) {
		if (n < 0)
			throw new IllegalArgumentException("n must be non negative");

		double magnitude = this.module();
		double angle = Math.atan2(im, re);
		// e^(ix) = cos(x) + isin(x)
		// a * e^(ix) = a * cos(x) + i * a * sin(x)
		// (a * e^(ix))^n = a^n * cos(nx) + i * a^n * sin(nx)
		return new Complex(Math.pow(magnitude, n) * Math.cos(n * angle), Math.pow(magnitude, n) * Math.sin(n * angle));
	}

	/**
	 * @return new complex number creted by raising this to power of (int) @param n
	 * @throws IllegalArgumentException if n is negative
	 */
	public Complex power(int n) {
		return power((double) n);
	}

	/**
	 * @return list of roots form 1. to @param n
	 * @throws IllegalArgumentException if n is negative
	 */
	public List<Complex> root(int n) {
		List<Complex> list = new ArrayList<>();
		for (int i = 1; i <= n; i++)
			list.add(power(1.0 / i));
		return list;
	}

	@Override
	public String toString() {
		if (im < 0)
			return String.format("(%.1f-i%.1f)", re, -im);
		else
			return String.format("(%.1f+i%.1f)", re, im);
	}
}
