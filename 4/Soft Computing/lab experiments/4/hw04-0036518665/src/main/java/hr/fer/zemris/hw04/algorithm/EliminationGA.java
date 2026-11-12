package hr.fer.zemris.hw04.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import hr.fer.zemris.hw04.crossing.Crossing;
import hr.fer.zemris.hw04.crossing.ICrossingOperator;
import hr.fer.zemris.hw04.function.IFunction;
import hr.fer.zemris.hw04.main.Point;
import hr.fer.zemris.hw04.mutation.IMutationOperator;
import hr.fer.zemris.hw04.mutation.Mutation;

/**
 * Razred <code>EliminationGA</code> predstavlja eliminacijski GA.
 * 
 * @author Dorian Kablar
 *
 */
public class EliminationGA implements GeneticAlgorithm {
	
	private int populationSize;
	private int maxIters;
	private double mutationProbability;
	
	private List<Point> population;
	private List<Double> populationFitness = new ArrayList<>();
	private double best;
	
	public EliminationGA(int populationSize, int maxIters, double mutationProbability) {
		this.populationSize = populationSize;
		this.maxIters = maxIters;
		this.mutationProbability = mutationProbability;
	}

	@Override
	public Optional<Point> solve(IFunction function) {
		// generiram random populaciju
		this.population = this.generatePopulation(function.noOfVariables());
		this.evaluatePopulation(function);
				
		System.out.println("Starting population:");
		for(int i = 0; i < population.size(); i++) {
			System.out.println("Assignment no. " + (i + 1) + ": " + this.population.get(i).toString() + ", fitness = " + this.populationFitness.get(i));
		}
				
		this.best = function.valueAt(this.getBestAssignment());
		System.out.println("Best assignment: " + this.getBestAssignment().toString());
				
		for(int i = 0; i < this.maxIters; i++) {
			Point best = this.getBestAssignment();
					
			if(function.valueAt(best) < this.best) {
				this.best = function.valueAt(best);
				System.out.println("Iteration no. " + i + " Best assignment: " + this.getBestAssignment().toString() 
						+ ", fitness = " + this.best);
			}
					
			// selekcija, križanje, mutacija
			this.newPopulation(function);
					
			// procjena populacije
			this.evaluatePopulation(function);
		}
				
		return Optional.of(this.getBestAssignment());
	}
	
	/**
	 * Pomoćna metoda koja obavlja selekciju, križanje i mutaciju za eliminacijski GA
	 * 
	 * @param function funkcija koja se minimizira
	 * @return
	 */
	private void newPopulation(IFunction function) {
		// troturnirska selekcija
		Point p1 = this.population.get((int) (Math.random() * this.populationSize));
		Point p2 = this.population.get((int) (Math.random() * this.populationSize));
		Point p3 = this.population.get((int) (Math.random() * this.populationSize));
		double value1 = function.valueAt(p1);
		double value2 = function.valueAt(p2);
		double value3 = function.valueAt(p3);
		
		double worst = Math.max(value1, value2) == value1 ? 
				(Math.max(value1, value3) == value1 ? value1 : value3) :
					(Math.max(value2, value3) == value2 ? value2 : value3);
		
		// najgoreg selektiranog izbriši, a ostala dva križaj, te dijete mutiraj i dodaj u populaciju
		if(worst == value1) {
			Point child = this.crossing(p2, p3);
			this.mutate(child);
			this.population.remove(p1);
			this.population.add(child);
		} else if(worst == value2) {
			Point child = this.crossing(p1, p3);
			this.mutate(child);
			this.population.remove(p2);
			this.population.add(child);
		} else {
			Point child = this.crossing(p1, p2);
			this.mutate(child);
			this.population.remove(p3);
			this.population.add(child);
		}
	}

	/**
	 * Pomoćna metoda koja generira početnu populaciju
	 * 
	 * @param noOfVariables broj varijabli koje ima jedna jedinka
	 * @return početna populacija
	 */
	private List<Point> generatePopulation(int noOfVariables) {
		List<Point> population = new ArrayList<>();
		for(int i = 0; i < this.populationSize; i++) {
			double[] points = new double[noOfVariables];
			for(int j = 0; j < noOfVariables; j++) {
				points[j] = Math.random() * 8. - 4.;
			}
			population.add(new Point(points));
		}
		return population;
	}
	
	/**
	 * Pomoćna metoda koja evaluira populaciju, tj. za svaku jedinku određuje dobrotu
	 * 
	 * @param function funkcija koju treba optimizirati
	 */
	private void evaluatePopulation(IFunction function) {
		for(int i = 0; i < this.populationSize; i++) {
			if(this.populationFitness.size() < i + 1)
				this.populationFitness.add(function.valueAt(this.population.get(i)));
			else
				this.populationFitness.set(i, function.valueAt(this.population.get(i)));
		}
	}
	
	/**
	 * Pomoćna metoda koja dohvaća najbolje rješenje iz generacije
	 */
	private Point getBestAssignment() {
		int bestIndex = 0;
		double best = this.populationFitness.get(bestIndex);
		
		for(int i = 1; i < this.populationSize; i++) {
			if(this.populationFitness.get(i) < best) {
				bestIndex = i;
				best = this.populationFitness.get(i);
			}
		}
		
		return this.population.get(bestIndex);
	}
	
	/**
	 * Pomoćna metoda koja obavlja križanje dvaju roditelja
	 * 
	 * @param parent1 prvi roditelj
	 * @param parent2 drugi roditelj
	 * @return dijete, tj. rezultat križanja roditelja
	 */
	private Point crossing(Point parent1, Point parent2) {
		ICrossingOperator op = new Crossing();
		return op.cross(parent1, parent2);
	}
	
	/**
	 * Pomoćna metoda koja obavlja mutiranje jedinke
	 * 
	 * @param p jedinka koju treba mutirati
	 */
	private void mutate(Point p) {
		IMutationOperator op = new Mutation(new Random());
		op.mutate(p, this.mutationProbability);
	}

}
