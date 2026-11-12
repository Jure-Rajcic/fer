package hr.fer.zemris.neinr.hw05.ann;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ANNExample {

	public static void main(String[] args) {
		Map<Double, List<List<Double>>> data = new LinkedHashMap<>();
		
		List<Double> list1 = new ArrayList<>();
		list1.add(-1.);
		List<Double> list2= new ArrayList<>();
		list2.add(1.);
		data.put(1., new ArrayList<>());
		data.get(1.).add(list1);
		data.get(1.).add(list2);
		
		List<Double> list3 = new ArrayList<>();
		list3.add(-0.8);
		List<Double> list4 = new ArrayList<>();
		list4.add(0.8);
		data.put(0.64, new ArrayList<>());
		data.get(0.64).add(list3);
		data.get(0.64).add(list4);
		
		List<Double> list5 = new ArrayList<>();
		list5.add(-0.6);
		List<Double> list6 = new ArrayList<>();
		list6.add(0.6);
		data.put(0.36, new ArrayList<>());
		data.get(0.36).add(list5);
		data.get(0.36).add(list6);
		
		List<Double> list7 = new ArrayList<>();
		list7.add(-0.4);
		List<Double> list8 = new ArrayList<>();
		list8.add(0.4);
		data.put(0.16, new ArrayList<>());
		data.get(0.16).add(list7);
		data.get(0.16).add(list8);
		
		List<Double> list9 = new ArrayList<>();
		list9.add(-0.2);
		List<Double> list10 = new ArrayList<>();
		list10.add(0.2);
		data.put(0.04, new ArrayList<>());
		data.get(0.04).add(list9);
		data.get(0.04).add(list10);
		
		List<Double> list11 = new ArrayList<>();
		list11.add(0.);
		data.put(0., new ArrayList<>());
		data.get(0.).add(list11);
		
		ANNAlgorithm alg = BackPropagationANNAlgorithm.getInstance();
		List<Integer> layers = new ArrayList<>();
		layers.add(1);
		layers.add(4);
		layers.add(1);
		alg.train(data, layers, 0.02, 1000, 0.1, 1);
		List<Double> pattern = new ArrayList<>();
		pattern.add(0.5);
		List<Double> prediction = alg.predict(pattern, false);
		System.out.println(prediction.toString());
	}
}
