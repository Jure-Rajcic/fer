package hr.fer.zemris.hw04.function;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.hw04.main.Point;

/**
 * Razred <code>Function</code> predstavlja funkciju: 
 * f(x,y) = sin(b0 + b1*x) + b2 * cos(x*(b3 + y))* 1/(1 + e^((x - b4)^2))
 * 
 * @author Dorian Kablar
 *
 */
public class Function implements IFunction {

	private List<Double> x = new ArrayList<>();
	private List<Double> y = new ArrayList<>();
	private List<Double> f = new ArrayList<>();
	
	public Function(String filename) {
		File f = new File(filename);
		
		if(!f.exists())
			throw new IllegalArgumentException("File " + filename + " does not exist.");
		
		try {
			List<String> lines = Files.readAllLines(Paths.get(filename));
			for(String s: lines) {
				String[] arr = s.trim().split("\t");
				this.x.add(Double.valueOf(arr[0]));
				this.y.add(Double.valueOf(arr[1]));
				this.f.add(Double.valueOf(arr[2]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public double valueAt(Point p) {
		double value = 0.;
		for(int i = 0; i < this.f.size(); i++) {
			double functionValue = Math.sin(p.getPoint(0) + p.getPoint(1) * this.x.get(i)) + 
					p.getPoint(2) * Math.cos(this.x.get(i) * (p.getPoint(3) + this.y.get(i))) *
					1. / (1. + Math.pow(Math.E, Math.pow(this.x.get(i) - p.getPoint(4), 2.)));
			value += Math.pow(this.f.get(i) - functionValue, 2.);
		}
		return value / (double) (this.f.size());
	}

	@Override
	public int noOfVariables() {
		return 5;
	}
}
