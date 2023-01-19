package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

/**
 * class BinaryOperatorButton models buttons for binary operations
 * 
 * @author Jure Rajcic
 *
 */
public class BinaryOperatorButton extends JButton {

	private static final long serialVersionUID = 1L;

	private DoubleBinaryOperator op1;
	private DoubleBinaryOperator op2;
	private String text1;
	private String text2;
	
	/**
	 * 
	 * @param op1 binary operator
	 * @param op2 invers binary operator - when (inv chekbox is pressed)
	 * @param text1 text for binary operation
	 * @param text2 text for inverse binary operation
	 */
	public BinaryOperatorButton(DoubleBinaryOperator op1, DoubleBinaryOperator op2, String text1, String text2) {
		this.text1 = text1;
		this.text2 = text2;
		this.op1 = op1;
		this.op2 = op2;
		this.setText(this.text1);
		this.setBackground(Color.CYAN);
	}
	
	public DoubleBinaryOperator getOperator() {
		return this.op1;
	}
	
	/**
	 * Method changes operator, based on Inv check box
	 */
	public void changeOperation() {
		String tmp = this.text1;
		this.text1 = this.text2;
		this.text2 = tmp;
		DoubleBinaryOperator tmp2 = this.op1;
		this.op1 = this.op2;
		this.op2 = tmp2;
		this.setText(this.text1);
	}
}
