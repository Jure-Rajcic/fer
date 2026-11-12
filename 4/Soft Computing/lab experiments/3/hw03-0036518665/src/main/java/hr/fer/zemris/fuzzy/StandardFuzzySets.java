package hr.fer.zemris.fuzzy;

public class StandardFuzzySets {

	public StandardFuzzySets() {
	}
	
	public static IIntUnaryFunction lFunction(int alfa, int beta) {
		return (x) -> {
			if(x < alfa)
				return 1.;
			else if(x >= beta)
				return 0.;
			else
				return (double)(beta - x) / (double)(beta - alfa);
		};
	}
	
	public static IIntUnaryFunction gammaFunction(int alfa, int beta) {
		return (x) -> {
			if(x < alfa)
				return 0.;
			else if(x >= beta)
				return 1.;
			else
				return (double)(x - alfa) / (double)(beta - alfa);
		};
	}
	
	public static IIntUnaryFunction lambdaFunction(int alfa, int beta, int gama) {
		return (x) -> {
			if(x < alfa || x >= gama)
				return 0.;
			else if(x >= alfa && x < beta)
				return (double)(x - alfa) / (double)(beta - alfa);
			else
				return (double)(gama - x) / (double)(gama - beta);
		};
	}
}
