package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;

/**class UnaryOperatorButton models buttons for unary operations

 * 
 * @author Jure Rajcic
 *
 */
public class UnaryOperatorButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DoubleUnaryOperator op1;
	private DoubleUnaryOperator op2;
	private String text1;
	private String text2;
	
	/**
	 * 
	 * @param op1 unary operator
	 * @param op2 invers unary operator - when (inv chekbox is pressed)
	 * @param text text for unary operation
	 * @param text2 text for inverse unary operation
	 */
	public UnaryOperatorButton(DoubleUnaryOperator op1, DoubleUnaryOperator op2, String text1, String text2) {
		this.text1 = text1;
		this.text2 = text2;
		this.op1 = op1;
		this.op2 = op2;
		this.setText(this.text1);
		this.setBackground(Color.CYAN);
	}
	
	public DoubleUnaryOperator getOperator() {
		return this.op1;
	}
	
	/**
	 * Method changes operator, based on Inv check box
	 */
	public void changeOperation() {
		String tmp = this.text1;
		this.text1 = this.text2;
		this.text2 = tmp;
		DoubleUnaryOperator tmp2 = this.op1;
		this.op1 = this.op2;
		this.op2 = tmp2;
		this.setText(this.text1);
	}
}
