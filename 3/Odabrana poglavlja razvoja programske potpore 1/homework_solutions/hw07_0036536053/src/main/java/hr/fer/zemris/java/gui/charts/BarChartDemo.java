package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * class BarChartDemo represents histogram demo
 * 
 * @author Jure Rajcic
 *
 */
public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private BarChart barChart;
	private String filePath;

	
	public BarChartDemo(BarChart barChart, String filePath) {
		super();
		this.barChart = barChart;
		this.filePath = filePath;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500,200);
		setTitle("BarChart");
		initGUI();
	}
	
	/**
	 * Metoda koja inicijalizira GUI
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		BarChartComponent barChart = new BarChartComponent(this.barChart);
		barChart.setOpaque(true);
		barChart.setBackground(Color.ORANGE);
		
		getContentPane().add(new JLabel(filePath), BorderLayout.PAGE_START);
		getContentPane().add(barChart, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		
		File f = new File(args[0]);
		List<XYValue> list = new ArrayList<>();
		
		try(Scanner sc = new Scanner(f)) {
			String xDescription = sc.nextLine().trim();
			String yDescription = sc.nextLine().trim();
			String[] data = sc.nextLine().trim().split(" ");
			for(String e : data){
				String[] ss = e.split(",");
				list.add(new XYValue(Integer.valueOf(ss[0]), Integer.valueOf(ss[1])));
			}
			Integer ymin = Integer.valueOf(sc.nextLine());
			Integer ymax = Integer.valueOf(sc.nextLine());
			Integer offset = Integer.valueOf(sc.nextLine());
			BarChart comp = new BarChart(list, xDescription, yDescription, ymin, ymax, offset);
			SwingUtilities.invokeLater(() -> {
					BarChartDemo barChartDemo = new BarChartDemo(comp, args[0]);
					barChartDemo.setSize(400, 600);
					barChartDemo.setVisible(true);
			});

		} catch (NullPointerException | NumberFormatException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
