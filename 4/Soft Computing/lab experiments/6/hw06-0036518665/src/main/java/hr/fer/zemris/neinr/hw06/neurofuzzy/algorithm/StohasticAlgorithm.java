package hr.fer.zemris.neinr.hw06.neurofuzzy.algorithm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.neinr.hw06.neurofuzzy.utils.Matrica;
import hr.fer.zemris.neinr.hw06.neurofuzzy.utils.Rule;

public class StohasticAlgorithm implements Algorithm {

	private List<Rule> rules;
	private List<Double> wk;
	private List<Double> w;
	private List<Double> fi;
	private int maxIter;
	
	public StohasticAlgorithm(int maxIter) {
		this.maxIter = maxIter;
	}
	
	@Override
	public Matrica train(int noOfRules, Matrica data, Matrica fValues) {
		StringBuilder sb = new StringBuilder();
		this.rules = new ArrayList<>();
		for(int i = 0; i < noOfRules; i++) {
			this.rules.add(new Rule());
		}
		
		for(int i = 0; i < maxIter; i++) {
			double error = 0.;
			for(int j = 0; j < data.getRows(); j++) {
				double f = this.propagation(data.getElement(j, 0), data.getElement(j, 1));
				error += Math.pow(fValues.getElement(j, 0) - f, 2.);
				this.update(0.001, fValues.getElement(j, 0), f, data.getElement(j, 0), data.getElement(j, 1));
			}
			error /= 2;
			System.out.println("Iteracija no. " + (i + 1) + ", error = " + error);
			sb.append((i + 1) + " " + error + "\n");
		}
		
		System.out.println("Konačna vrijednost parametara: ");
		for(int i = 0; i < rules.size(); i++) {
			System.out.println("========================");
			System.out.println("Rule " + (i + 1) +":");
			System.out.println(rules.get(i).toString());
		}
		
		try {
			Files.writeString(Paths.get("src/main/resources/stohastic-error-epoch.dat"), sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Matrica errorMatrix = new Matrica(fValues.getRows(), 1);
		for(int i = 0; i < errorMatrix.getRows(); i++) {
			errorMatrix.setElement(this.propagation(data.getElement(i, 0), data.getElement(i, 1)) - fValues.getElement(i, 0), i, 0);
		}
		
		return errorMatrix;
	}

	@Override
	public double test(Matrica testData) {
		if(testData.getColumns() != 2 || testData.getRows() != 1)
			throw new IllegalArgumentException("Očekuje se matrica dimenzija 1x2");
		
		return this.propagation(testData.getElement(0, 0), testData.getElement(0, 1));
	}
	
	private double propagation(double x, double y) {
		w = new ArrayList<>();
		fi = new ArrayList<>();
		for(Rule r : rules) {
			w.add(r.antecedent(x, y));
			fi.add(r.consequence(x, y));
		}
		
		wk = new ArrayList<>();
		double denominator = 0.;
		for(Double d : w)
			denominator += d;
		
		for(Double d : w)
			wk.add(d / denominator);
		
		double fValue = 0.;
		for(int i = 0; i < wk.size(); i++) {
			fValue += wk.get(i) * fi.get(i);
		}
		
		return fValue;
	}

	private void update(double eta, double o, double f, double x, double y) {
		double wiSum = 0., wifiSum = 0.;
		for(int i = 0; i < rules.size(); i++) {
			wiSum += w.get(i);
			wifiSum += w.get(i) * fi.get(i);
		}
		
		for(int i = 0; i < rules.size(); i++) {
			double alpha = rules.get(i).alpha(x);
			double beta = rules.get(i).beta(y);
			double a = rules.get(i).getA(), b = rules.get(i).getB(), c = rules.get(i).getC(), d = rules.get(i).getD();
			
			rules.get(i).setP(rules.get(i).getP() + eta * (o - f) * wk.get(i) * x);
			rules.get(i).setQ(rules.get(i).getQ() + eta * (o - f) * wk.get(i) * y);
			rules.get(i).setR(rules.get(i).getR() + eta * (o - f) * wk.get(i));
			rules.get(i).setA(rules.get(i).getA() + eta * (o - f) * ((fi.get(i) * wiSum - wifiSum) / Math.pow(wiSum, 2.)) * 
					beta * b * alpha * (1 - alpha));
			rules.get(i).setB(rules.get(i).getB() - eta * (o - f) * ((fi.get(i) * wiSum - wifiSum) / Math.pow(wiSum, 2.)) * 
					beta * (x - a) * alpha * (1 - alpha));
			rules.get(i).setC(rules.get(i).getC() + eta * (o - f) * ((fi.get(i) * wiSum - wifiSum) / Math.pow(wiSum, 2.)) * 
					alpha * d * beta * (1 - beta));
			rules.get(i).setD(rules.get(i).getD() - eta * (o - f) * ((fi.get(i) * wiSum - wifiSum) / Math.pow(wiSum, 2.)) * 
					alpha * (y - c) * beta * (1 - beta));
		}
	}
}
