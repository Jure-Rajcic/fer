package hr.fer.zemris.neinr.hw07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import hr.fer.zemris.neinr.hw07.neuroevol.GenetskiAlgoritam;
import hr.fer.zemris.neinr.hw07.neuroevol.NeuronskaMreza;
import hr.fer.zemris.neinr.hw07.neuroevol.Solution;
import hr.fer.zemris.neinr.hw07.utils.Dataset;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Unesite veličinu populacije, broj iteracija, vjerojatnosti za sve tri mutacije, te poželjnosti sve tri mutacije:");
		int popSize = sc.nextInt();
		int maxIter = sc.nextInt();
		double vm1 = sc.nextDouble();
		double vm2 = sc.nextDouble();
		double vm3 = sc.nextDouble();
		double t1 = sc.nextDouble();
		double t2 = sc.nextDouble();
		double t3 = sc.nextDouble();
		
		Dataset dataset = new Dataset();
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < dataset.size(); i++) {
			sb.append(dataset.getSample(i).getPoint(0) + " " + dataset.getSample(i).getPoint(1) 
					+ " " + dataset.getClass(i) + "\n");
		}
		
		try {
			Files.writeString(Paths.get("src/main/resources/data.dat"), sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		NeuronskaMreza mreza = new NeuronskaMreza(8);
		GenetskiAlgoritam ga = new GenetskiAlgoritam();
		ga.setMreza(mreza);
		
		ga.train(popSize, maxIter, vm1, vm2, vm3, t1, t2, t3);
		
		int correct = 0;
		for(int i = 0; i < dataset.size(); i++) {
			double[] pred = ga.test(dataset.getSample(i));
			for(int j = 0; j < pred.length; j++)
				if(pred[j] < 0.5)
					pred[j] = 0.;
				else
					pred[j] = 1.;
			
			boolean c = false;
			if(Double.compare(pred[dataset.getClass(i) - 1], 1.) == 0) {
				c = true;
				for(int j = 0; j < pred.length; j++) {
					if(j != dataset.getClass(i) - 1 && Double.compare(pred[j], 0.) == 1.) {
						c = false;
					}
				}
			}
			
			if(c) correct++;
			
			String s = pred[0] + " " + pred[1] + " " + pred[2];
			System.out.println("Real output: " + dataset.getClass(i) + ", predicted output: " + s + ", correct = " + c);
		}
		
		System.out.println("Number of correct predictions = " + correct);
		System.out.println("Number of incorrect predictions = " + (dataset.size() - correct));
		
		Solution best = ga.getBest();
		sb = new StringBuilder();
		for(int i = 0; i < 32; i+= 4) {
			sb.append(best.getParams()[i] + " " + best.getParams()[i+2] + "\n");
		}
		
		try {
			Files.writeString(Paths.get("src/main/resources/best.dat"), sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sc.close();
	}
}
