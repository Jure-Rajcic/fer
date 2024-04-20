package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.JButton;

/**
 * class NumberButton represents button which when pressed displays clicked number
 * 
 * @author Jure Rajcic
 *
 */
public class NumberButton extends JButton {

	private static final long serialVersionUID = 1L;

	public NumberButton(String text) {
		this.setText(text);
		this.setFont(this.getFont().deriveFont(28l));
		this.setBackground(Color.CYAN);
	}
}
