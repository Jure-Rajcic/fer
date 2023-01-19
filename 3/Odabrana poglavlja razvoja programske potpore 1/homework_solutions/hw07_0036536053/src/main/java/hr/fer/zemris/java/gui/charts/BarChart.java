package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * class BarChart represents histogram model
 * 
 * @author Jure Rajcic
 *
 */
public class BarChart {

	private List<XYValue> list;
	private String xDescription;
	private String yDescription;
	private int ymin;
	private int ymax;
	private int offset;
	
	
	public BarChart(List<XYValue> list, String xDescription, String yDescription, int ymin, int ymax, int offset) {

		this.list = list;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.ymin = ymin;
		this.ymax = ymax;
		this.offset = offset;
		if (ymin < 0) throw new IllegalArgumentException("ymin cant be negative");
		if (ymax <= ymin) throw new IllegalArgumentException("ymax neds to bi greater then ymin");
		// jesamo dobro svatia ovu recenicu ??
		// (ako ymax-ymin ne dijeli taj offset, pri crtanju radite s prvim većim y-om koji je na cijelom broju razmaka od ymin)
		while((this.ymax - this.ymin) % this.offset != 0)
			this.offset++;
			
			if(list.stream().anyMatch((e) -> e.getY() < this.ymin))
				throw new IllegalArgumentException("y values in list cant be smaller than ymin.");
	}

	
	public List<XYValue> getList() {
		return list;
	}

	
	public String getxDescription() {
		return xDescription;
	}

	public String getyDescription() {
		return yDescription;
	}

	
	public int getYmin() {
		return ymin;
	}

	
	public int getYmax() {
		return ymax;
	}

	
	public int getOffset() {
		return offset;
	}
}
