package hr.fer.zemris.neinr.hw05.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataUtils {

	public static List<String> convertData(List<String> data, int M) {
		int sumX = 0, sumY = 0;
		double meanX, meanY;
		List<Double> x = new ArrayList<>(), y = new ArrayList<>();
		List<String> result = new ArrayList<>();
		
		for(String s : data) {
			sumX += Integer.valueOf(s.split(",")[0]);
			sumY += Integer.valueOf(s.split(",")[1]);
		}
		
		meanX = (double) sumX / (double) data.size();
		meanY = (double) sumY / (double) data.size();
		
		for(String s : data) {
			x.add(Double.valueOf(s.split(",")[0]) - meanX);
			y.add(Double.valueOf(s.split(",")[1]) - meanY);
		}
		
		double maxX = maxElement(x), maxY = maxElement(y), max = Math.max(maxX, maxY);
		
		for(int i = 0; i < data.size(); i++) {
			x.set(i, x.get(i) / max);
			y.set(i, y.get(i) / max);
		}
		
		double distance = 0.;
		for(int i = 1; i < x.size(); i++) {
			distance += Math.sqrt(Math.pow(x.get(i - 1) - x.get(i), 2.) + Math.pow(y.get(i - 1) - y.get(i), 2.));
		}
		
		for(int k = 0; k < M; k++) {
			double d = k * distance / (M - 1);
			result.add(findClosest(x, y, d));
		}
		
		return result;
	}
	
	private static String findClosest(List<Double> x, List<Double> y, double d) {
		int i;
		for(i = 1; i < x.size(); i++) {
			double distance = Math.sqrt(Math.pow(x.get(0) - x.get(i), 2.) + Math.pow(y.get(0) - y.get(i), 2.));
			if(distance < d)
				continue;
			else if (distance > d) {
				double distance2 = Math.sqrt(Math.pow(x.get(0) - x.get(i - 1), 2.) + Math.pow(y.get(0) - y.get(i - 1), 2.));
				if(Math.abs(distance - d) < Math.abs(d - distance2))
					break;
				else {
					i--;
					break;
				}
			} else
				break;
		}
		
		if(i >= x.size())
			return x.get(x.size() - 1) + "," + y.get(y.size() - 1);
		
		return x.get(i) + "," + y.get(i);
	}

	private static double maxElement(List<Double> list) {
		double max = list.get(0);
		for(int i = 1; i < list.size(); i++)
			if(list.get(i) > max)
				max = list.get(i);
		return max;
	}
	
	public static void saveData(Map<Integer, List<List<String>>> data) {
		StringBuilder sb = new StringBuilder();
		
		for(Integer i : data.keySet()) {
			for(List<String> list : data.get(i)) {
				for(String s : list) {
					sb.append(s.split(",")[0] + " " + s.split(",")[1] + " ");
				}
				sb.append(i == 1 ? "1 " : "0 ");
				sb.append(i == 2 ? "1 " : "0 ");
				sb.append(i == 3 ? "1 " : "0 ");
				sb.append(i == 4 ? "1 " : "0 ");
				sb.append(i == 5 ? "1" : "0");
				sb.append("\n");
			}
		}
		
		try {
			Files.writeString(Paths.get("src/main/resources/data.txt"), sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Map<Double, List<List<Double>>> loadData(String line, int M) {
		Map<Double, List<List<Double>>> res = new LinkedHashMap<>();
		
		List<String> lines = new ArrayList<>();
		for(String s : line.split("\n")) {
			if(!s.isBlank())
				lines.add(s);
		}
		
		for(String s : lines) {
			List<Double> list = new ArrayList<>();
			String[] arr = s.split(" ");
			for(int i = 0; i <= arr.length; i++) {
				if(i < 2*M) {
					list.add(Double.valueOf(arr[i]));
					continue;
				} else if(Integer.valueOf(arr[i]) == 1) {
					Double key = (double)(i - 2*M + 1);
					if(!res.containsKey(key)) {
						res.put(key, new ArrayList<>());
					}
					res.get(key).add(list);
					break;
				}
			}
		}
		return res;
	}
}
