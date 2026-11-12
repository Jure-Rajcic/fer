package hr.fer.zemris.neinr.hw07.neuroevol;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.neinr.hw07.crossing.AritmeticCrossing;
import hr.fer.zemris.neinr.hw07.crossing.ICrossingOperator;
import hr.fer.zemris.neinr.hw07.crossing.OnePointCrossing;
import hr.fer.zemris.neinr.hw07.crossing.RandomCrossing;
import hr.fer.zemris.neinr.hw07.mutation.IMutationOperator;
import hr.fer.zemris.neinr.hw07.mutation.Mutation1;
import hr.fer.zemris.neinr.hw07.mutation.Mutation2;
import hr.fer.zemris.neinr.hw07.utils.Dataset;
import hr.fer.zemris.neinr.hw07.utils.Point;

public class GenetskiAlgoritam {

	private List<Solution> population;
	private Random rand;
	private NeuronskaMreza mreza = null;
	private Dataset dataset = new Dataset();
	
	public GenetskiAlgoritam() {
		this.population = new ArrayList<>();
		this.rand = new Random();
	}
	
	public void setMreza(NeuronskaMreza mreza) {
		this.mreza = mreza;
	}
	
	public void train(int popSize, int maxIter, double pm1, double pm2, double pm3, double t1, double t2, double t3) {
		
		this.initialize(popSize);
		this.calcError();
		
		double error = this.bestError();
		
		System.out.println("GA start: error = " + error);
		
		for(int i = 0; i < maxIter && error >= 1E-7; i++) {
			List<Solution> newPop = new ArrayList<>();
			
			newPop.add(this.bestSolution());
			
			while(newPop.size() < popSize) {
				Solution p1 = this.threeTournamentSelection(popSize);
				Solution p2 = this.threeTournamentSelection(popSize);
				Solution c = this.crossing(p1, p2, popSize);
				this.mutate(c, pm1, pm2, pm3, t1, t2, t3);
				
				newPop.add(c);
			}
			
			population = newPop;
			this.calcError();
			error = this.bestError();
			
			System.out.println("Iteration no. " + (i + 1) + ", error = " + error);
		}
		
		System.out.println(this.bestSolution().toString());
	}
	
	public double[] test(Point p) {
		return mreza.calcOutput(this.bestSolution().getParams(), p);
	}

	private void initialize(int popSize) {
		population.clear();
		int size = mreza.numberOfParameters();
		for(int i = 0; i < popSize; i++) {
			double[] params = new double[size];
			for(int j = 0; j < size; j++)
				params[j] = rand.nextDouble();
			population.add(new Solution(params));
		}
	}
	
	private void calcError() {
		for(Solution s : population)
			s.setError(mreza.calcError(dataset, s.getParams()));
	}
	
	private double bestError() {
		double best = population.get(0).getError();
		
		for(int i = 1; i < population.size(); i++) {
			if(population.get(i).getError() < best) {
				best = population.get(i).getError();
			}
		}
		
		return best;
	}
	
	private Solution bestSolution() {
		double best = population.get(0).getError();
		Solution s = population.get(0);
		
		for(int i = 1; i < population.size(); i++) {
			if(population.get(i).getError() < best) {
				best = population.get(i).getError();
				s = population.get(i);
			}
		}
		
		return s;
	}
	
	private Solution threeTournamentSelection(int popSize) {
		Solution p1 = this.population.get((int) (rand.nextDouble() * popSize));
		Solution p2 = this.population.get((int) (rand.nextDouble() * popSize));
		Solution p3 = this.population.get((int) (rand.nextDouble() * popSize));
		double value1 = p1.getError();
		double value2 = p2.getError();
		double value3 = p3.getError();
		
		return Math.min(value1, value2) == value1 ? 
				(Math.min(value1, value3) == value1 ? p1 : p3) :
					(Math.min(value2, value3) == value2 ? p2 : p3);
	}
	
	private Solution crossing(Solution p1, Solution p2, int popSize) {
		double p = rand.nextDouble();
		ICrossingOperator op1 = new AritmeticCrossing();
		ICrossingOperator op2 = new RandomCrossing(rand);
		ICrossingOperator op3 = new OnePointCrossing(rand.nextInt(popSize));
		
		double[] child = p < 0.33 ? op1.cross(p1.getParams(), p2.getParams()) : 
			(p < 0.67 ? op2.cross(p1.getParams(), p2.getParams()) : op3.cross(p1.getParams(), p2.getParams()));
		
		return new Solution(child);
	}
	
	private void mutate(Solution c, double pm1, double pm2, double pm3, double t1, double t2, double t3) {
		double v1 = t1 / (t1 + t2 + t3), v2 = t2 / (t1 + t2 + t3);
		IMutationOperator op1 = new Mutation1(0.4);
		IMutationOperator op2 = new Mutation1(0.7);
		IMutationOperator op3 = new Mutation2(0.8);
		
		double p = rand.nextDouble();
		
		if(p < v1) op1.mutate(c.getParams(), pm1, rand);
		else if(p < v1 + v2) op2.mutate(c.getParams(), pm2, rand);
		else op3.mutate(c.getParams(), pm3, rand);
	}
	
	public Solution getBest() {
		return this.bestSolution();
	}
}
