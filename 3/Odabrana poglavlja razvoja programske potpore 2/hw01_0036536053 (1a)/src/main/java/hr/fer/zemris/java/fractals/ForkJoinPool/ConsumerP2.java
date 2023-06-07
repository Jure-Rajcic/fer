package hr.fer.zemris.java.fractals.ForkJoinPool;

import java.util.concurrent.RecursiveAction;
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
	public class ConsumerP2 extends RecursiveAction {
		double reMin, reMax, imMin, imMax;
		int width, height, yMin, yMax, m, tracks;
		short[] data;
		AtomicBoolean cancel;
		ComplexRootedPolynomial roots;
		ComplexPolynomial polynomial, derived;
		public static ConsumerP2 STOP = new ConsumerP2();

		private ConsumerP2() {}

		public ConsumerP2(double reMin, double reMax, double imMin, double imMax, 
        int width,int height, int yMin, int yMax, int m, short[] data, 
        AtomicBoolean cancel, ComplexRootedPolynomial roots, int tracks) {
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
            this.tracks = tracks;
		}

		/**
		 * coloring pixel
		 */
		@Override
		public void compute() {
            if (cancel.get()) return;
            if ((yMax - yMin) <= this.tracks) {
                computeDirect();
                return;
            }
            int lower = (yMin + yMax) / 2;
            int higher = (yMin + yMax) / 2 + 1;

            ConsumerP2 job1 = new ConsumerP2(reMin, reMax, imMin, imMax, width, height, yMin, lower, m, data, cancel, roots, tracks);
            ConsumerP2 job2 = new ConsumerP2(reMin, reMax, imMin, imMax, width, height, higher, yMax, m, data, cancel, roots, tracks);
            invokeAll(job1, job2);
		}

        private void computeDirect() {
            for (int offset = yMin * width, y = yMin; y < yMax + 1; y++) {
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