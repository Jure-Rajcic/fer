package hr.fer.zemris.hw04.main;

import java.util.Optional;
import java.util.Scanner;

import hr.fer.zemris.hw04.algorithm.EliminationGA;
import hr.fer.zemris.hw04.algorithm.GenerationGA;
import hr.fer.zemris.hw04.algorithm.GeneticAlgorithm;
import hr.fer.zemris.hw04.function.Function;
import hr.fer.zemris.hw04.function.IFunction;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Unesi veličinu populacije, broj iteracija, vjerojatnost mutacije i želiš li elitizam (true/false):");
		int popSize = sc.nextInt();
		int maxIter = sc.nextInt();
		double mutationProbability = Double.valueOf(sc.next());
		boolean elitism = sc.nextBoolean();
		
		System.out.println("=================================================");
		System.out.println("Generacijski GA");
		System.out.println("=================================================");
		
		GeneticAlgorithm algorithm = new GenerationGA(popSize, maxIter, mutationProbability, elitism);
		IFunction function = new Function("src/main/resources/zad4-dataset1.txt");
		Optional<Point> solution = algorithm.solve(function);
		if(solution.isPresent())
			System.out.println("Solution: " + solution.get().toString() + ", fit = " + function.valueAt(solution.get()) + "\n");
		
		function = new Function("src/main/resources/zad4-dataset2.txt");
		solution = algorithm.solve(function);
		if(solution.isPresent())
			System.out.println("Solution: " + solution.get().toString() + ", fit = " + function.valueAt(solution.get()) + "\n");
		
		System.out.println("=================================================");
		System.out.println("Eliminacijski GA");
		System.out.println("=================================================");
		
		GeneticAlgorithm algorithm2 = new EliminationGA(30, 10000, 0.3);
		function = new Function("src/main/resources/zad4-dataset1.txt");
		solution = algorithm2.solve(function);
		if(solution.isPresent())
			System.out.println("Solution: " + solution.get().toString() + ", fit = " + function.valueAt(solution.get()) + "\n");
		
		function = new Function("src/main/resources/zad4-dataset2.txt");
		solution = algorithm2.solve(function);
		if(solution.isPresent())
			System.out.println("Solution: " + solution.get().toString() + ", fit = " + function.valueAt(solution.get()) + "\n");
	}
}
