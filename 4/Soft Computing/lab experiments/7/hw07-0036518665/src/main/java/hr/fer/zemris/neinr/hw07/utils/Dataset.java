package hr.fer.zemris.neinr.hw07.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Dataset {

	private List<Point> dataset;
	private List<Integer> classes;
	
	public Dataset() {
		this.dataset = new ArrayList<>();
		this.classes = new ArrayList<>();
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("src/main/resources/zad7-dataset.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String line : lines) {
			String[] arr = line.split("[ \t]");
			double x = Double.valueOf(arr[0]);
			double y = Double.valueOf(arr[1]);
			
			Point p = new Point(new double[] {x, y});
			
			int c = arr[2].equals("1") ? 1 : (arr[3].equals("1") ? 2 : 3);
			
			dataset.add(p);
			classes.add(c);
		}
	}
	
	public int size() {
		return dataset.size();
	}
	
	public Point getSample(int index) {
		return dataset.get(index);
	}
	
	public int getClass(int index) {
		return classes.get(index);
	}
}
