package hr.fer.zemris.neinr.hw05.ann;

import java.util.List;
import java.util.Map;

public interface ANNAlgorithm {

	public void train(Map<Double, List<List<Double>>> data, List<Integer> layers, double maxError, int maxEpochs, double eta, int batchSize);
	
	public List<Double> predict(List<Double> pattern, boolean update);
}
