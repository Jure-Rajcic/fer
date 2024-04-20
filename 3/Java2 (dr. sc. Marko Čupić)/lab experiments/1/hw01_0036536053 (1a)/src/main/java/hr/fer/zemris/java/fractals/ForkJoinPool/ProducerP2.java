package hr.fer.zemris.java.fractals.ForkJoinPool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.ComplexRootedPolynomial;

	/**
	 * class Producer creates fractals
	 *
	 * @author Jure
	 * 
	 */
	public class ProducerP2 implements IFractalProducer {

		ForkJoinPool pool;

		private int tracks;
		private ComplexRootedPolynomial roots;

		public ProducerP2(int tracks, ComplexRootedPolynomial roots) {
			this.roots = roots;
			this.tracks = tracks == -1 ? 16 : tracks;
            setup();
		}

		@Override
		public void setup() {
			pool = new ForkJoinPool();
		}

		
		@Override
		public synchronized void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			final int m = 16 * 16 * 16;
			final short[] data = new short[width * height];
			
			ConsumerP2 c = new ConsumerP2(reMin, reMax, imMin, imMax, width, height, 0, height - 1, m, data, cancel, roots, tracks);
			pool.invoke(c);
			
			/// SEND RESULTS
            observer.acceptResult(data, (short) (roots.toComplexPolynom().order() + 1), requestNo);
        }


		@Override
		public void close() {
            pool.shutdown();
            if(pool.isShutdown()) {
                System.out.println("ForkJoinPool IS SHUT DOWN");
            }
		}

		
	}