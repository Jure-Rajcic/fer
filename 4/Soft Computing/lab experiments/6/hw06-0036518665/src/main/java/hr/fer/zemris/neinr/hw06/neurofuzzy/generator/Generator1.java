package hr.fer.zemris.neinr.hw06.neurofuzzy.generator;

public class Generator1 implements IGenerator {
	
	@Override
	public double generate(double x, double y) {
		return (Math.pow(x - 1., 2.) + Math.pow(y + 2., 2.) - 5.*x*y + 3) * Math.pow(Math.cos(x / 5.), 2.);
	}
}
