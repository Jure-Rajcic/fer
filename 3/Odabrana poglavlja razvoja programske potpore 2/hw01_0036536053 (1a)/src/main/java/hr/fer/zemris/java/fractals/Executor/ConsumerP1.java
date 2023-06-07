package hr.fer.zemris.java.fractals.Executor;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;
	/**
	 * class Consumer computes image of fractals
	 * 
	 * @author Jure Rajcic
	 *
	 */
	public class ConsumerP1 implements Runnable {
		double reMin, reMax, imMin, imMax;
		int width, height, yMin, yMax, m;
		short[] data;
		AtomicBoolean cancel;
		ComplexRootedPolynomial roots;
		ComplexPolynomial polynomial, derived;
		public static ConsumerP1 STOP = new ConsumerP1();

		private ConsumerP1() {}

		public ConsumerP1(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax,
				int m, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial roots) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.roots = roots;
			this.polynomial = roots.toComplexPolynom();
			this.derived = polynomial.derive();
		}

		/**
		 * coloring pixel
		 */
		@Override
		public void run() {
			for (int offset = yMin * width, y = yMin; y < yMax + 1; y++) {
				if (cancel.get())
					break;
				double cim = (height - 1.0 - y) / (height - 1.0) * (imMax - imMin) + imMin;
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
					} while (module > 0.001 && iters < m);
					// obojimo pixel 'c'
					int index = roots.indexOfClosestRootFor(zn, 0.002);
					data[offset++] = (short) (index + 1);
				}
			}
		}
	}