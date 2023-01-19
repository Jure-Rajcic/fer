package hr.fer.zemris.java.fractals;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * class NewtonParallel creates adn displays image, uses multithreading
 * 
 * @author Jure Rajcic
 *
 */
public class NewtonParallel {

	public static void main(String[] args) {
// /usr/bin/env /Library/Java/JavaVirtualMachines/jdk-18.0.2.jdk/Contents/Home/bin/java @/var/folders/5g/b9j9w6h17_s6g30651y3h5qcnxd657/T/cp_ebasat75hlxek5pbqcbbhzc14.argfile hr.fer.zemris.java.fractals.NewtonParallel -w 5 -t 7


		if (args.length > 4)
			throw new IllegalArgumentException("Invalid number of arguments.");

		// System.out.println("Welcome to Newton-Raphson iteration based fractal
		// viewer.");
		// System.out.println("Please enter at least two roots, one root per line. Enter
		// 'done' when done.");

		int workers = -1, tracks = -1, workersCnt = 0, tracksCnt = 0;

		for (int i = 0; i < args.length; i++) {
			String arg = args[i].trim();
			if (arg.trim().startsWith("--workers=")) {
				workersCnt++;
				workers = Integer.valueOf(arg.substring("--workers=".length()));
			} else if (arg.trim().startsWith("--tracks=")) {
				tracksCnt++;
				tracks = Integer.valueOf(arg.substring("--tracks=".length()));
			} else if (arg.trim().equals("-w")) {
				workersCnt++;
				workers = Integer.valueOf(args[i + 1]);
			} else if (arg.trim().equals("-t")) {
				tracksCnt++;
				tracks = Integer.valueOf(args[i + 1]);
			}
		}
		if (!(workersCnt <= 1 && tracksCnt <= 1))
			throw new IllegalArgumentException("Invalid arguments.");

		Complex[] roots = Newton.roots();
		FractalViewer
				.show(new NewtonParallelProducer(workers, tracks, new ComplexRootedPolynomial(Complex.ONE, roots)));
	}

	/**
	 * class Consumer computes image of fractals
	 * 
	 * @author Jure Rajcic
	 *
	 */
	public static class Consumer implements Runnable {
		double reMin, reMax, imMin, imMax;
		int width, height, yMin, yMax, m;
		short[] data;
		AtomicBoolean cancel;
		ComplexRootedPolynomial roots;
		ComplexPolynomial polynomial, derived;
		public static Consumer STOP = new Consumer();

		private Consumer() {
		}

		public Consumer(double reMin, double reMax, double imMin,
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
				// ??? skuzit TODO
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

	/**
	 * class NewtonParallelProducer creates image
	 * 
	 * @author Jure Rajcic
	 *
	 */
	public static class NewtonParallelProducer implements IFractalProducer {

		private int workers;
		private int tracks;
		private ComplexRootedPolynomial roots;
		private ComplexPolynomial polynomial;

		public NewtonParallelProducer(int workers, int tracks, ComplexRootedPolynomial roots) {
			this.workers = workers == -1 ? Runtime.getRuntime().availableProcessors() : workers;
			this.tracks = tracks == -1 ? 4 * Runtime.getRuntime().availableProcessors() : tracks;
			this.roots = roots;
			this.polynomial = roots.toComplexPolynom();
		}

		/**
		 * produces image where work is splitted on multiple threads
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			int m = 16 * 16 * 16;
			short[] data = new short[width * height];
			final int numberOfTracks = tracks > height ? height : tracks;
			int nYPerTrack = height / numberOfTracks;
			System.out.println("Number of threads: " + workers + ", number of tracks: " + numberOfTracks);
			final BlockingQueue<Consumer> queue = new LinkedBlockingQueue<>();

			Thread[] threads = new Thread[workers];
			for (int i = 0; i < threads.length; i++) {
				threads[i] = new Thread(
						() -> {
							while (true) {
								Consumer p = null;
								try {
									p = queue.take();
									if (p == Consumer.STOP)
										break;
								} catch (InterruptedException e) {
									continue;
								}
								p.run();
							}
						});
			}
			// fill queue with consumers
			for (int i = 0; i < numberOfTracks; i++) {
				int yMin = i * nYPerTrack;
				int yMax = (i + 1) * nYPerTrack - 1;
				if (i == numberOfTracks - 1) {
					yMax = height - 1;
				}
				Consumer consumer = new Consumer(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel,
						roots);
				while (true) {
					try {
						queue.put(consumer);
						break;
					} catch (InterruptedException e) {}
				}
			}

			// start consuming
			for (Thread t : threads)
				t.start();

			// add finished consumers to queue
			for (int i = 0; i < threads.length; i++) {
				while (true) {
					try {
						queue.put(Consumer.STOP);
						break;
					} catch (InterruptedException e) {}
				}
			}

			// join threads
			for (int i = 0; i < threads.length; i++) {
				while (true) {
					try {
						threads[i].join();
						break;
					} catch (InterruptedException e) {}
				}
			}
			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}
	}
}
