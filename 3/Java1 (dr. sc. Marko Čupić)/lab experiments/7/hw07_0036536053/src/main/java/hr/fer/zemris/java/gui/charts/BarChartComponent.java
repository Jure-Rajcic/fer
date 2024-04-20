package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.awt.Font;
import javax.swing.JComponent;

/**
 * class BarChartComponent represents histogram component
 * 
 * @author Jure Rajcic
 *
 */
public class BarChartComponent extends JComponent {

	
	private static final long serialVersionUID = 1L;
	
	private BarChart chart;
	
	
	public BarChartComponent(BarChart barChart) {
		this.chart = barChart;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Dimension dim = getSize();
		Graphics2D g2 = (Graphics2D) g;
		

		int lenDesc = 15; // description away from frame by 15 pixels
		int lenNumbers = lenDesc + 15 ; 
		int lenAxis = lenNumbers + lenDesc + ("" + chart.getYmax()).length() * 5; // enabling when Ymax is large number numbers eill apear more awat from axis
		
		int yStart = dim.height - lenAxis;
		int xStart = dim.width - lenAxis;

		//1. prikazane koordinatne osi zajedno sa strelicama na vrhu (bilo kakvim; popunjenim, nepopunjenim)
		drawArrowLine(g, lenAxis, yStart, xStart, yStart, 5, 5);//x os
		drawArrowLine(g, lenAxis, yStart, lenAxis, 0, 5, 5);//y os
		

		/*
		 * 2. prikazane popunjene stupce do visine koja odgovara vrijednosti koju reprezentiraju (mogu, a ne moraju puniti čitavu raspoloživu širinu stupca; mogu, a ne moraju imati prikazane sjene
		 */
		List<XYValue> list = chart.getList();
		int columnWidth = (dim.width - lenAxis * 2) / list.size();
		int maxY = chart.getYmax();
		for(int i = 0; i < list.size(); i++) {
			XYValue value = list.get(i);
			double y = (maxY - value.getY()) / (double)(maxY) * yStart;
			int x = lenAxis + columnWidth * i + 1;
			double columnHeight = (value.getY() / (double)maxY) * yStart;
			g.setColor(Color.BLUE);
			g.fill3DRect(x, (int)y, columnWidth - 1 ,(int) columnHeight + 1, true);
		}

		//3. prikazane brojeve po osima koji:

		// drawig numbers on y axis
		for(int i = chart.getYmin(); i <= chart.getYmax(); i += chart.getOffset()) {
			double y = (maxY - i) / (double) maxY * yStart;
			g.setColor(Color.BLACK);
			g.drawString(String.valueOf(i), lenNumbers, (int)y + 5); // because font is 10
		}

		// drawing numbers on x axis
		for(int i = 0; i < list.size(); i++) {
			int x = lenAxis + columnWidth * i;
			g.setColor(Color.BLACK);
			g.drawString("" +list.get(i).getX(), x + columnWidth/2 - ("" + x).length() / 2, dim.height - lenNumbers);
		}
		// drawing description on x axis
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.drawString(chart.getxDescription(), dim.width/2 - (3 * chart.getxDescription().length()), dim.height - 15);

		// drawing description on y axis
		g2.setFont(new Font("Arial", Font.PLAIN, 12).deriveFont(AffineTransform.getRotateInstance(-Math.PI / 2)));
		g2.drawString(chart.getyDescription(), lenDesc, dim.height/2 - (3 * chart.getyDescription().length()));		
	}
	
	private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
		// internet helped
	    int dx = x2 - x1;
		int dy = y2 - y1;
	    double hypotenus = Math.sqrt(dx*dx + dy*dy);
	    double sin = dy / hypotenus;
		double cos = dx / hypotenus;

	    double xm = hypotenus - d;
		double xn = xm;

		double ym = h;
		double yn = -h, x;

	    x = xm*cos - ym*sin + x1;
	    ym = xm*sin + ym*cos + y1;
	    xm = x;

	    x = xn*cos - yn*sin + x1;
	    yn = xn*sin + yn*cos + y1;
	    xn = x;

	    int[] xpoints = {x2, (int) xm, (int) xn};
	    int[] ypoints = {y2, (int) ym, (int) yn};

	    g.drawLine(x1, y1, x2, y2);
	    g.fillPolygon(xpoints, ypoints, 3);
	}
}