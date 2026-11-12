package hr.fer.zemris.neinr.hw07.neuroevol;

public class Solution {

	private double[] params;
	private double error;
	
	public Solution(double[] params) {
		this.params = params;
	}

	public double[] getParams() {
		return params;
	}

	public void setParams(double[] params) {
		this.params = params;
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Solution:\n");
		for(int i = 0; i < params.length; i++) {
			sb.append(i + ": " + params[i] + "\n");
		}
		return sb.toString();
	}
}
