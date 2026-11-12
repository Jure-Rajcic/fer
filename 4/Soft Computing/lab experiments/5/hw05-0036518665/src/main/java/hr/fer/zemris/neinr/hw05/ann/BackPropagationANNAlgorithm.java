package hr.fer.zemris.neinr.hw05.ann;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.neinr.hw05.utils.Matrica;

public class BackPropagationANNAlgorithm implements ANNAlgorithm {

	private List<Matrica> weights = new ArrayList<>();
	private List<Matrica> errors = new ArrayList<>();
	private List<Matrica> bias = new ArrayList<>();
	private List<Matrica> output = new ArrayList<>();
	private List<Matrica> gradient = new ArrayList<>();
	private double error;
	
	private static ANNAlgorithm instance = null;
	
	public synchronized static ANNAlgorithm getInstance() {
		if(instance == null)
			instance = new BackPropagationANNAlgorithm();
		return instance;
	}
	
	@Override
	public void train(Map<Double, List<List<Double>>> data, List<Integer> layers, double maxError, int maxEpochs, double eta, int batchSize) {
		this.initalize(layers);
		
		List<List<Double>> patterns = new ArrayList<>();
		List<Double> classification = new ArrayList<>();
		for(Double i : data.keySet()) {
			List<List<Double>> tmp = data.get(i);
			for(List<Double> list : tmp) {
				patterns.add(list);
				classification.add(i);
			}
		}
		
		error = this.computeError(patterns, classification);
		for(int i = 0; i < maxEpochs && error > maxError; i++) {
			error = this.computeError(patterns, classification);
			System.out.println("Iteration no. " + i + ", error: " + error);
			int j = 0;
			while(j < patterns.size()) {
				for(int k = j; k < ((j + batchSize) < patterns.size() ?
						j + batchSize : patterns.size()); k++) {
					List<Double> prediction = this.predict(patterns.get(k), false);
					Matrica p = new Matrica(prediction.size(), 1);
					for(int l = 0; l < prediction.size(); l++) {
						p.setElement(prediction.get(l) * (1. - prediction.get(l)) * 
								((Double.compare((double)(l + 1), classification.get(k)) == 0 ? 1. : 0.) - prediction.get(l)), l, 0);
					}
					this.updateErrors(p);
					this.predict(patterns.get(k), true);
				}
				this.updateWeights(eta);
				this.resetOutputAndErrors();
				j += batchSize;
			}
		}
		
		System.out.println("w");
		for(Matrica w : weights)
			System.out.println(w.toString());
		
		System.out.println("b");
		for(Matrica b : bias)
			System.out.println(b.toString());
		
		System.out.println("o");
		for(Matrica o: output)
			System.out.println(o.toString());
		
		System.out.println("e");
		for(Matrica e : errors)
			System.out.println(e.toString());
	}

	@Override
	public List<Double> predict(List<Double> pattern, boolean update) {
		Matrica patternMatrix = new Matrica(pattern.size(), 1);
		for(int i = 0; i < patternMatrix.getRows(); i++)
			patternMatrix.setElement(pattern.get(i), i, 0);
		
		if(update) {
			output.set(0, patternMatrix);
			gradient.set(0, patternMatrix);
		}
		
		Matrica prediction = Matrica.multiply(weights.get(0), patternMatrix);
		for(int j = 0; j < prediction.getRows(); j++) {
			for(int k = 0; k < prediction.getColumns(); k++) {
				prediction.setElement(1. / (1. + Math.exp(-1.*(prediction.getElement(j, k) + bias.get(0).getElement(k, 0)))), j, k);
				if(update) {
					output.get(1).setElement(prediction.getElement(j, k), j, k);
					gradient.get(1).setElement(gradient.get(1).getElement(j, k) + output.get(1).getElement(j, k) * errors.get(0).getElement(j, 0), j, k);
				}
			}
		}
		
		for(int i = 1; i < weights.size(); i++) {
			prediction = Matrica.multiply(weights.get(i), prediction);
			for(int j = 0; j < prediction.getRows(); j++) {
				for(int k = 0; k < prediction.getColumns(); k++) {
					prediction.setElement(1. / (1. + Math.exp(-1.*(prediction.getElement(j, k) + bias.get(i).getElement(k, 0)))), j, k);
					if(update) {
						output.get(i+1).setElement(prediction.getElement(j, k), j, k);
						gradient.get(i+1).setElement(gradient.get(i+1).getElement(j, k) + output.get(i).getElement(j, k) * errors.get(i).getElement(k, 0), j, k);
					}
				}
			}
		}
		
		List<Double> result = new ArrayList<>();
		for(int i = 0; i < prediction.getRows(); i++) {
			result.add(prediction.getElement(i, 0));
		}
		
		return result;
	}
	
	/** 
	 * Pomoćna metoda koja inicijalizira sve težine i greške u neuronskoj mreži
	 * 
	 * @param layers slojevi neuronske mreže
	 */
	private void initalize(List<Integer> layers) {
		if(!weights.isEmpty()) {
			weights.clear();
			bias.clear();
			errors.clear();
			output.clear();
			gradient.clear();
		}
		
		for(int i = 1; i < layers.size(); i++) {
			Matrica weightMatrix = new Matrica(layers.get(i), layers.get(i-1));
			for(int j = 0; j < layers.get(i); j++) {
				for(int k = 0; k < layers.get(i-1); k++) {
					weightMatrix.setElement(Math.random() * 2. - 1., j, k);
				}
			}
			weights.add(weightMatrix);
			
			Matrica biasMatrica = new Matrica(layers.get(i), 1);
			for(int j = 0; j < layers.get(i); j++) {
				biasMatrica.setElement(Math.random() * 2. - 1., j, 0);
			}
			bias.add(biasMatrica);
			
			errors.add(new Matrica(layers.get(i), 1));
		}
		
		for(int i = 0; i < layers.size(); i++) {
			Matrica outputMatrica = new Matrica(layers.get(i), 1);
			for(int j = 0; j < layers.get(i); j++) {
				outputMatrica.setElement(0., j, 0);
			}
			output.add(outputMatrica);
			
			gradient.add(new Matrica(layers.get(i), 1));
		}
		
		System.out.println("w");
		for(Matrica w : weights)
			System.out.println(w.toString());
		
		System.out.println("b");
		for(Matrica b : bias)
			System.out.println(b.toString());
		
		System.out.println("o");
		for(Matrica o: output)
			System.out.println(o.toString());
		
		System.out.println("e");
		for(Matrica e : errors)
			System.out.println(e.toString());
		
	}
	
	/**
	 * Pomoćna metoda koja računa grešku modela
	 * 
	 * @param patterns uzorci na kojima se mreža uči
	 * @param classification klasifikacija uzoraka
	 * @return greška modela
	 */
	private double computeError(List<List<Double>> patterns, List<Double> classification) {
		double res = 0.;
		for(int i = 0; i < patterns.size(); i++) {
			List<Double> prediction = this.predict(patterns.get(i), false);
			double c = classification.get(i);
			for(int j = 0; j < prediction.size(); j++) {
				res += Math.pow((Double.compare((double)(j + 1), c) == 0 ? 1. : 0.) - prediction.get(j), 2.);
			}
		}
		return res / 2.;
	}
	
	/**
	 * Pomoćna metoda koja računa greške pojedinih neurona mreže
	 * 
	 * @param outputErrors matrica grešaka očitanih primjera
	 */
	private void updateErrors(Matrica outputErrors) {
		for(int i = 0; i < outputErrors.getRows(); i++) {
			errors.get(errors.size() - 1).setElement(errors.get(errors.size() - 1).getElement(i, 0)
					+ outputErrors.getElement(i, 0), i, 0);
		}
		
		for(int i = errors.size() - 2; i > 0; i--) {
			int j = errors.get(i).getRows(), k = errors.get(i + 1).getRows();
			for(int l = 0; l < j; l++) {
				double error = 0.;
				for(int m = 0; m < k; m++) {
					error += weights.get(i).getElement(l, m) * errors.get(i + 1).getElement(m, 0);
				}
				error *= output.get(i-1).getElement(l, 0) * (1. - output.get(i-1).getElement(l, 0));
				errors.get(i).setElement(errors.get(i).getElement(l, 0) + error, l, 0);
			}
		}
	}
	
	/**
	 * Pomoćna metoda koja ažurira težine neurona mreže
	 */
	private void updateWeights(double eta) {	
		for(int i = 0; i < bias.size(); i++) {
			Matrica b = bias.get(i);
			b = Matrica.add(b, errors.get(i).multiply(eta));
			bias.set(i, b);
		}
		
		for(int i = 0; i < weights.size(); i++) {
			Matrica w = weights.get(i);
			for(int j = 0; j < w.getRows(); j++) {
				for(int k = 0; k < w.getColumns(); k++) {
					w.setElement(w.getElement(j, k) + 
							eta * gradient.get(i).getElement(k, 0), j, k);
				}
			}
			weights.set(i, w);
		}
	}

	/**
	 * Metoda koja sve izlaze postavlja na nulu
	 */
	private void resetOutputAndErrors() {
		for(Matrica grad : gradient) {
			for(int i = 0; i < grad.getRows(); i++)
				for(int j = 0; j < grad.getColumns(); j++)
					grad.setElement(0., i, j);
		}
		
		for(Matrica error : errors) {
			for(int i = 0; i < error.getRows(); i++)
				for(int j = 0; j < error.getColumns(); j++)
					error.setElement(0., i, j);
		}
	}
}
