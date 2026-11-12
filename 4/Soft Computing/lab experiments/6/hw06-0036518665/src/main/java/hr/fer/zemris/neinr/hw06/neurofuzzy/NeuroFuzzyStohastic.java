package hr.fer.zemris.neinr.hw06.neurofuzzy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import hr.fer.zemris.neinr.hw06.neurofuzzy.algorithm.Algorithm;
import hr.fer.zemris.neinr.hw06.neurofuzzy.algorithm.StohasticAlgorithm;
import hr.fer.zemris.neinr.hw06.neurofuzzy.generator.Generator1;
import hr.fer.zemris.neinr.hw06.neurofuzzy.generator.IGenerator;
import hr.fer.zemris.neinr.hw06.neurofuzzy.utils.Matrica;

public class NeuroFuzzyStohastic {
	
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Očekujem broj pravila ako argument.");
		}
		
		int noOfRules = Integer.valueOf(args[0]);
		
		Matrica data = new Matrica(81, 2);
		Matrica fValues = new Matrica(81, 1);
		
		IGenerator gen = new Generator1();
		
		int i = 0;
		for(int j = -4; j <= 4; j++) {
			for(int k = -4; k <= 4; k++) {
				data.setElement(j, i, 0);
				data.setElement(k, i, 1);
				fValues.setElement(gen.generate(j, k), i++, 0);
			}
		}
		
		Algorithm alg = new StohasticAlgorithm(1000);
		Matrica errorMatrix = alg.train(noOfRules, data, fValues);
		
		StringBuilder sb = new StringBuilder();
		for(i = 0; i < errorMatrix.getRows(); i++) {
			sb.append(data.getElement(i, 0) + " " + data.getElement(i, 1) + " " + errorMatrix.getElement(i, 0) + "\n");
		}
		
		try {
			Files.writeString(Paths.get("src/main/resources/errors-stohastic.dat"), sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sb = new StringBuilder();
		for(i = 0; i < data.getRows(); i++) {
			Matrica test = new Matrica(1, 2);
			test.setElement(data.getElement(i, 0), 0, 0);
			test.setElement(data.getElement(i, 1), 0, 1);
			sb.append(data.getElement(i, 0) + " " + data.getElement(i, 1) + " " + alg.test(test) + "\n");
		}
		
		try {
			Files.writeString(Paths.get("src/main/resources/function-stohastic-1-rule.dat"), sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Unesite x i y za koje želite predikciju vrijednosti, ili 'kraj' za kraj rada.");
		while(true) {
			String s = sc.next();
			if(s.equals("kraj")) break;
			
			double x = Double.valueOf(s);
			
			s = sc.next();
			if(s.equals("kraj")) break;
			
			double y = Double.valueOf(s);
		
			Matrica test = new Matrica(1, 2);
			test.setElement(x, 0, 0);
			test.setElement(y, 0, 1);
		
			System.out.println("Predikcija: " + alg.test(test));
		}
	}
}
