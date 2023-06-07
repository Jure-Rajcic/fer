package hr.fer.zemris.java.fractals.Executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.ComplexRootedPolynomial;
import java.util.concurrent.ExecutionException;

	/**
	 * class Producer creates fractals
	 *
	 * @author Jure
	 * 
	 */
	public class ProducerP1 implements IFractalProducer {

		private ExecutorService executorService;
        private List<Runnable> jobList;
        private List<Future<?>> results;

        private int workers;
		private int tracks;
		private ComplexRootedPolynomial roots;

		public ProducerP1(int workers, int tracks, ComplexRootedPolynomial roots) {
			this.roots = roots;
			this.workers = workers == -1 ? Runtime.getRuntime().availableProcessors() : workers;
			this.tracks = tracks == -1 ? 4 * Runtime.getRuntime().availableProcessors() : tracks;
            setup();
		}

		@Override
		public void setup() {
			executorService = Executors.newFixedThreadPool(this.workers);
            jobList = new ArrayList<>();
            results = new ArrayList<>();
		}

		
		@Override
		public synchronized void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			final int m = 16 * 16 * 16;
			final short[] data = new short[width * height];
			final int numberOfTracks = tracks > height ? height : tracks;
			final int nYPerTrack = height / numberOfTracks;
			System.out.println("Number of threads: " + workers + ", number of tracks: " + numberOfTracks);

			/// CREATE JOBS
			for (int i = 0; i < numberOfTracks; i++) {
				int yMin = i * nYPerTrack;
				int yMax = (i + 1) * nYPerTrack - 1;
				if (i == numberOfTracks - 1) yMax = height - 1;
				ConsumerP1 c = new ConsumerP1(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel, roots);
				results.add(executorService.submit(c));
			}

			/// WAIT FOR JOBS TO FINISH
			for (Future<?> f : results) {
				while (true) {
					try {
						f.get();
						break;
					} catch (InterruptedException | ExecutionException e) {
					}
				}
			}

			/// SEND RESULTS
            observer.acceptResult(data, (short) (roots.toComplexPolynom().order() + 1), requestNo);
        }


		@Override
		public void close() {
			results.clear();
            jobList.clear();
            executorService.shutdown();
            if(executorService.isShutdown()) {
                System.out.println("EXECUTORSERVICE IS SHUT DOWN");
            }
		}

		
	}