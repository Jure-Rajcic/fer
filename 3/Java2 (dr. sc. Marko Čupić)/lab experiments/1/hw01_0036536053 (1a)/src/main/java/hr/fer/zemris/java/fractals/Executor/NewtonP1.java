package hr.fer.zemris.java.fractals.Executor;


import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


/**
 * class NewtonParallel creates adn displays image, uses multithreading
 * 
 * @author Jure Rajcic
 *
 */
public class NewtonP1 {

	public static void main(String[] args) {
// java -cp target/hw01-0036536053-1.0.jar:lib/fractal-viewer-1.1.jar hr.fer.zemris.java.fractals.Executor.NewtonP1 -w 5 -t 7
		/// CONFIGURE WORKERS AND TRACKS
		if (args.length > 4)
			throw new IllegalArgumentException("Invalid number of arguments.");
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

		/// GET USER INPUT
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
			}
			sc.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		System.out.println("Image of fractal will appear shortly. Thank you.");


		/// DISPLAY IMAGE
		FractalViewer.show(new ProducerP1(workers, tracks, new ComplexRootedPolynomial(Complex.ONE, roots.toArray(new Complex[roots.size()]))));
	}
	
}
