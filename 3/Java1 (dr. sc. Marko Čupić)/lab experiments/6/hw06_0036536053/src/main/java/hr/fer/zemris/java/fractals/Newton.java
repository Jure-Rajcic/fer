package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * The main class for the Newton-Raphson iteration based fractal viewer.
 * This class is responsible for displaying the fractal and prompting the user
 * to enter roots, which are then used to calculate the fractal.
 * 
 * @author Jure Rajcic
 */
public class Newton {

	public static void main(String[] args) {
// /usr/bin/env /Library/Java/JavaVirtualMachines/jdk-18.0.2.jdk/Contents/Home/bin/java @/var/folders/5g/b9j9w6h17_s6g30651y3h5qcnxd657/T/cp_ebasat75hlxek5pbqcbbhzc14.argfile hr.fer.zemris.java.fractals.Newton 
/*
1
-1 + i0
i
0 - i1
done
 */

		Complex[] roots = Newton.roots();
		FractalViewer.show(new NewtonProducer(new ComplexRootedPolynomial(Complex.ONE, roots)));
	}

	/**
	 * Prompts the user to enter roots and @return an array of the entered roots
	 */
	public static Complex[] roots() {
		System.out.println("Welcome to Newton-Raphson iteration based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per input. Enter 'done' when done.");
		List<Complex> roots = new ArrayList<>();
		try (Scanner sc = new Scanner(System.in)) {
			for (int i = 1; true; i++) {
				System.out.printf("Root %d> ", i);
				String input = sc.nextLine().trim();
				if (input.isEmpty())
					throw new IllegalArgumentException("Invalid argument.");

				if (input.equals("done"))
					break;

				String[] strings = input.split(" ");
				Arrays.sort(strings);
				// System.out.println(strings); // [1] , [+, -1, i0], [i], [-, 0, i1]
				Complex complex;
				switch (strings.length) {
					case 1 -> {
						if (Arrays.stream(new String[] { "i", "-i", "+i" }).anyMatch((s) -> strings[0].startsWith(s))) {
							String number = strings[0].replace("i", "");
							if (number.isEmpty() || number.equals("-") || number.equals("+"))
								number += "1";
							complex = new Complex(0, Double.valueOf(number));
						} else {
							complex = new Complex(Double.valueOf(strings[0]), 0);
						}
					}
					case 3 -> {
						String re = strings[1];
						String im = strings[2];
						if (!im.startsWith("i"))
							throw new IllegalArgumentException("invalid imaginary part");
						im = im.substring(1);
						if (im.isEmpty())
							im += "1";
						im = strings[0] + im;
						complex = new Complex(Double.valueOf(re), Double.valueOf(im));
					}
					default -> throw new IllegalArgumentException("invalid syntax");
				}
				roots.add(complex);
				// System.out.println(complex);

			}
			sc.close();
		} catch (NumberFormatException e) {
			// u zadatku nije bilo navedeno ali ako ikad bude trebalo evo ode se triba
			// napisat error handling
			e.printStackTrace();
		}
		System.out.println("Image of fractal will appear shortly. Thank you.");
		Complex[] arr = roots.toArray(new Complex[roots.size()]);
		// System.out.println(Arrays.toString(arr));
		return arr;
	}

	/**
	 * class NewtonProducer creats and show image of fractals
	 * 
	 * @author Jure Rajcic
	 *
	 */
	public static class NewtonProducer implements IFractalProducer {

		private ComplexRootedPolynomial roots;
		private ComplexPolynomial polynomial;
		private ComplexPolynomial derived;

		/**
		 * costuctor
		 * 
		 * @param roots Polinom oblika z0*(z-z1)*(z-z2)*...*(z-zn)
		 */
		public NewtonProducer(ComplexRootedPolynomial roots) {
			this.roots = roots;
			this.polynomial = roots.toComplexPolynom();
			this.derived = polynomial.derive();
		}

		/**
		 * method produces image
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			short[] data = new short[width * height];
			for (int offset = 0, y = 0; y < height; y++) {
				if (cancel.get()) break;
				// y [0, height - 1] skaliramo na jednake djelove intervala te ga transformiramo
				// na dio IM intervala i linearno pomicemo
				double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;

				for (int x = 0; x < width; x++) {
					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
					// representation of pixel 'c' in complex coordinates
					Complex zn = new Complex(cre, cim);
					int iters = 0;
					double module = 0.;
					// constantly updating 'zn' with fixed number of iterations
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						// System.out.println("module: " + module);
						iters++;
					} while (module > 0.001 && iters < 16 * 16 * 16);
					// obojimo pixel 'c'
					int index = roots.indexOfClosestRootFor(zn, 0.002);
					data[offset++] = (short) (index + 1);
				}
			}
			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}
	}
}
