package hr.fer.zemris.neinr.hw07.neuroevol;

import hr.fer.zemris.neinr.hw07.utils.Dataset;
import hr.fer.zemris.neinr.hw07.utils.Point;

public class NeuronskaMreza {

	private int N1;
	private int[] layers;
	private double[] neurons;
	
	public NeuronskaMreza(int N1, int... layers) {
		this.N1 = N1;
		this.layers = layers;
		
		int neuronNumber = 5 + N1;
		for(int i : layers)
			neuronNumber += i;
		
		neurons = new double[neuronNumber];
	}
	
	public int numberOfParameters() {
		int numberOfParameters = N1 * 2 * 2;
		for(int i = 0; i < layers.length; i++) {
			if(i == 0)
				numberOfParameters += layers[0] * (N1 + 1);
			else
				numberOfParameters += layers[i] * (layers[i - 1] + 1);
		}
		
		if(layers.length != 0) numberOfParameters += 3 * (layers[layers.length - 1] + 1);
		else numberOfParameters += 3 * (N1 + 1);
		
		return numberOfParameters;
	}
	
	public double[] calcOutput(double[] parameters, Point input) {
		double[] res = new double[3];
		
		neurons[0] = input.getPoint(0);
		neurons[1] = input.getPoint(1);

		int j = 0;
		for(int i = 0; i < N1; i++) {
			double value = Math.abs(neurons[0] - parameters[j]) / Math.abs(parameters[j+1]) + 
					Math.abs(neurons[1] - parameters[j+2]) / Math.abs(parameters[j+3]);
			j+= 4;
			neurons[2+i] = 1 / (1 + value);
		}
		
		int neuronIndex = 2 + N1;
		
		for(int i = 0; i < layers.length; i++) {
			if(i == 0) {
				for(int k = 0; k < layers[i]; k++) {
					double net = parameters[j];
					for(int l = 0; l < N1; l++) {
						net += parameters[j+l+1] * neurons[2+l];
					}
					neurons[neuronIndex + k] = 1 / (1 + Math.exp(-net));
					j+= N1 + 1;
				}
			} else {
				for(int k = 0; k < layers[i]; k++) {
					double net = parameters[j];
					for(int l = 0; l < layers[i-1]; l++) {
						net += parameters[j+l+1] * neurons[neuronIndex - layers[i-1] + l];
					}
					neurons[neuronIndex + k] = 1/ (1 + Math.exp(-net));
					j += layers[i-1] + 1;
				}
			}
			neuronIndex += layers[i];
		}
		
		for(int i = 0; i < 3; i++) {
			double net = parameters[j];
			int l = layers.length == 0 ? N1 : layers[layers.length - 1];
			for(int k = 0; k < l; k++) {
				net += parameters[j+k+1] * neurons[neuronIndex - l + k];
			}
			neurons[neurons.length - 3 + i] = 1/ (1 + Math.exp(-net));
			j += l + 1;
		}
		
		res[0] = neurons[neurons.length - 3];
		res[1] = neurons[neurons.length - 2];
		res[2] = neurons[neurons.length - 1];
		return res;
	}
	
	public double calcError(Dataset dataset, double[] parameters) {
		double error = 0.;
		
		for(int i = 0; i < dataset.size(); i++) {
			double[] output = this.calcOutput(parameters, dataset.getSample(i));
			int pred = dataset.getClass(i);
			for(int j = 0; j < 3; j++) {
				error += Math.pow((pred == j + 1 ? (double) pred : 0.) - output[j], 2.);
			}
		}
		
		return error / dataset.size();
	}
}
