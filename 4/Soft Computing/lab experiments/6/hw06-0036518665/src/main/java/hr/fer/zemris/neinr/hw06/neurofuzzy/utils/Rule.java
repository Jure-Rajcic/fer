package hr.fer.zemris.neinr.hw06.neurofuzzy.utils;

public class Rule {

	private double p;
	private double q;
	private double r;
	private double a;
	private double b;
	private double c;
	private double d;
	
	public Rule() {
		this.p = Math.random();
		this.q = Math.random();
		this.r = Math.random();
		this.a = Math.random();
		this.b = Math.random();
		this.c = Math.random();
		this.d = Math.random();
	}
	
	public double antecedent(double x, double y) {
		double alpha = 1. / (1. + Math.exp(b * (x - a)));
		double beta = 1. / (1. + Math.exp(d * (y - c)));
		
		return alpha * beta;
	}
	
	public double alpha(double x) {
		return 1. / (1. + Math.exp(b * (x - a)));
	}
	
	public double beta(double y) {
		return 1. / (1. + Math.exp(d * (y - c)));
	}
	
	public double consequence(double x, double y) {
		return p*x + q*y + r;
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}

	public double getQ() {
		return q;
	}

	public void setQ(double q) {
		this.q = q;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	@Override
	public String toString() {
		return "[p=" + p + ", q=" + q + ", r=" + r + ", a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + "]";
	}
}
