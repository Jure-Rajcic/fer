package hr.fer.zemris.java.gui.layouts;

/**
 * class RCPosition represents position of component in calcLayout
 * 
 * @author Jure Rajcic
 *
 */
public class RCPosition {

	private int row;
	private int column;

	/**
	 * 
	 * @param row    component row
	 * @param column component column
	 * @throws CalcLayoutException if row not between [1, 5] or column not between
	 *                             [1, 7] or special case where row is 1 and column
	 *                             not equal to 1 or 6 or 7
	 */
	public RCPosition(int row, int column) {
		if (row < 1 || row > 5)
			throw new CalcLayoutException(" br. redaka<1 || br. redaka>5");
		if (column < 1 || column > 7)
			throw new CalcLayoutException("br. stupaca<1 || br. stupaca>7");
		if (row == 1 && (column > 1 && column < 6))
			throw new CalcLayoutException("ograničenje (1,s) gdje je s>1 && s<6");
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return this.row;
	}

	public int getColumn() {
		return this.column;
	}

	/**
	 * parser method thaht creates RCPosition object from text
	 */
	public static RCPosition parse(String text) {
		String[] arr = text.split(",");
		if (arr.length != 2)
			throw new IllegalArgumentException("Invalid arguments given.");
			int row = Integer.valueOf(arr[0].trim());
			int column = Integer.valueOf(arr[1].trim());
		return new RCPosition(row, column);
	}
}
