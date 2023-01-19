package hr.fer.zemris.java.gui.charts;

/**
 * class XYValue represents histogram values
 * 
 * @author Jure Rajcic
 *
 */
public class XYValue {

	private int x;
	private int y;
	
	
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	
	public int getX() {
		return x;
	}

	
	public int getY() {
		return y;
	}
}
