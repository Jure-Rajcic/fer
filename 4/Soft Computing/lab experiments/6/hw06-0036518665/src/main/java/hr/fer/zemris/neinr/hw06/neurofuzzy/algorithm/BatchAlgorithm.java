package hr.fer.zemris.neinr.hw06.neurofuzzy.algorithm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.neinr.hw06.neurofuzzy.utils.Matrica;
import hr.fer.zemris.neinr.hw06.neurofuzzy.utils.Rule;

public class BatchAlgorithm implements Algorithm {

	private List<Rule> rules;
	private List<Double> wk;
	private List<Double> w;
	private List<Double> fi;
	private int maxIter;
	
	public BatchAlgorithm(int maxIter) {
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
			Matrica f = new Matrica(data.getRows(), 1);
			for(int j = 0; j < data.getRows(); j++) {
				f.setElement(this.propagation(data.getElement(j, 0), data.getElement(j, 1)), j, 0);
				error += Math.pow(fValues.getElement(j, 0) - f.getElement(j, 0), 2.);
			}
			
			error /= 2;
			System.out.println("Iteracija no. " + (i + 1) + ", error = " + error);
			sb.append((i + 1) + " " + error + "\n");
			
			this.update(0.0001, f, fValues, data);
		}
		
		System.out.println("Konačna vrijednost parametara: ");
		for(int i = 0; i < rules.size(); i++) {
			System.out.println("========================");
			System.out.println("Rule " + (i + 1) +":");
			System.out.println(rules.get(i).toString());
		}
		
		try {
			Files.writeString(Paths.get("src/main/resources/batch-error-epoch.dat"), sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Matrica errorMatrix = new Matrica(fValues.getRows(), 1);
		for(int i = 0; i < errorMatrix.getRows(); i++) {
			Matrica test = new Matrica(1, 2);
			test.setElement(data.getElement(i, 0), 0, 0);
			test.setElement(data.getElement(i, 1), 0, 1);
			errorMatrix.setElement(this.test(test) - fValues.getElement(i, 0), i, 0);
		}
		
		return errorMatrix;
	}

	@Override
	public double test(Matrica testData) {
		if(testData.getColumns() != 2 || testData.getRows() != 1)
			throw new IllegalArgumentException("Očekuje se matrica dimenzija 1x2");
		
		List<Double> w = new ArrayList<>();
		List<Double> f = new ArrayList<>();
		for(Rule r : this.rules) {
			w.add(r.antecedent(testData.getElement(0, 0), testData.getElement(0, 1)));
			f.add(r.consequence(testData.getElement(0, 0), testData.getElement(0, 1)));
		}
		
		List<Double> wk = new ArrayList<>();
		double denominator = 0.;
		for(Double d : w)
			denominator += d;
		
		for(Double d : w)
			wk.add(d / denominator);
		
		double fValue = 0.;
		for(int i = 0; i < wk.size(); i++) {
			fValue += wk.get(i) * f.get(i);
		}
		
		return fValue;
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
	
	private void update(double eta, Matrica f, Matrica y, Matrica data) {
		double newp = 0., newq = 0., newr = 0., newa = 0., newb = 0., newc = 0., newd = 0.;
		for(int i = 0; i < data.getRows(); i++) {
			this.propagation(data.getElement(i, 0), data.getElement(i, 1));
			
			double wiSum = 0., wifiSum = 0.;
			for(int j = 0; j < rules.size(); j++) {
				wiSum += w.get(j);
				wifiSum += w.get(j) * fi.get(j);
			}
			
			for(int j = 0; j < rules.size(); j++) {
				double alpha = rules.get(j).alpha(data.getElement(i, 0));
				double beta = rules.get(j).beta(data.getElement(i, 1));
				double a = rules.get(j).getA(), b = rules.get(j).getB(), c = rules.get(j).getC(), d = rules.get(j).getD();
				
				newp += (y.getElement(i, 0) - f.getElement(i, 0)) * wk.get(j) * data.getElement(i, 0);
				newq += (y.getElement(i, 0) - f.getElement(i, 0)) * wk.get(j) * data.getElement(i, 1);
				newr += (y.getElement(i, 0) - f.getElement(i, 0)) * wk.get(j);
				newa += (y.getElement(i, 0) - f.getElement(i, 0)) * ((fi.get(j) * wiSum - wifiSum) / Math.pow(wiSum, 2.)) *
						beta * b * alpha * (1 - alpha);
				newb += (y.getElement(i, 0) - f.getElement(i, 0)) * ((fi.get(j) * wiSum - wifiSum) / Math.pow(wiSum, 2.)) * 
						beta * (data.getElement(i, 0) - a) * alpha * (1 - alpha);
				newc += (y.getElement(i, 0) - f.getElement(i, 0)) * ((fi.get(j) * wiSum - wifiSum) / Math.pow(wiSum, 2.)) *
						alpha * d * beta * (1 - beta);
				newd += (y.getElement(i, 0) - f.getElement(i, 0)) * ((fi.get(j) * wiSum - wifiSum) / Math.pow(wiSum, 2.)) * 
						alpha * (data.getElement(i, 1) - c) * beta * (1 - beta);
			}
		}
		
		for(int i = 0; i < rules.size(); i++) {
			rules.get(i).setP(rules.get(i).getP() + eta * newp);
			rules.get(i).setQ(rules.get(i).getQ() + eta * newq);
			rules.get(i).setR(rules.get(i).getR() + eta * newr);
			rules.get(i).setA(rules.get(i).getA() + eta * newa);
			rules.get(i).setB(rules.get(i).getB() - eta * newb);
			rules.get(i).setC(rules.get(i).getC() + eta * newc);
			rules.get(i).setD(rules.get(i).getD() - eta * newd);
		}
	}
}
