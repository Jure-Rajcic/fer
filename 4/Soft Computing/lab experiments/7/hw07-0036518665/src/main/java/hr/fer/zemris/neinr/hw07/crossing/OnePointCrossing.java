package hr.fer.zemris.neinr.hw07.crossing;

public class OnePointCrossing implements ICrossingOperator {

	private int index;
	
	public OnePointCrossing(int index) {
		this.index = index;
	}
	
	@Override
	public double[] cross(double[] p1, double[] p2) {
		double[] child = new double[p1.length];
		
		for(int i = 0; i < p1.length; i++) {
			if(i < index) 
				child[i] = p1[i];
			else
				child[i] = p2[i];
		}
		
		return child;
	}

}
