package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * class Calculator represents implementation of calculator
 * 
 * @author Jure Rajcic
 *
 */
public class Calculator extends JFrame {

	
	private static final long serialVersionUID = 1L;
	
	private CalcModel calcModel;
	private Stack<Double> stack;
	
	public Calculator() {
		this.calcModel = new CalcModelImpl();
		this.stack = new Stack<>();
		setTitle("Calculator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}
	
	/**
	 * initializes gui
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		
		// 1. creating display
		JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(label.getFont().deriveFont(30l));
		label.setText(this.calcModel.toString());
		label.setBackground(Color.YELLOW);
		label.setOpaque(true);
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		// asigning listeners
		CalcValueListener listener = model -> {
			label.setText(model.toString());
		};
		this.calcModel.addCalcValueListener(listener);
		cp.add(label, new RCPosition(1,1));
		
		// 2. creating number buttons
		ActionListener numberButtonAction = e -> {
			NumberButton b = (NumberButton)e.getSource();
			this.calcModel.insertDigit(Integer.valueOf(b.getText()));
		};
		int num = 0;
		for(int i = 5; i > 1; i--) {
			for(int j = 3; j < 6 ; j++) {
				NumberButton button = new NumberButton(String.valueOf(num));
				button.addActionListener(numberButtonAction);
				cp.add(button, new RCPosition(i,j));
				num++;
				if(i == 5)
					break;
			}
		}
		
		// 3. creating all other buttons ...
		// =
		cp.add(new MyJButton("=", (e) -> {
			DoubleBinaryOperator op = this.calcModel.getPendingBinaryOperation();
			if(op != null && this.calcModel.isActiveOperandSet()) {
				this.calcModel.setValue(op.applyAsDouble(this.calcModel.getActiveOperand(), this.calcModel.getValue()));
			}
			this.calcModel.clearActiveOperand();
		}), new RCPosition(1,6));

		//clr
		cp.add(new MyJButton("clr", (e) -> {
			this.calcModel.clear();
			label.setText(this.calcModel.toString());
		}), new RCPosition(1,7));

		//res
		cp.add(new MyJButton("res", (e) -> {
			this.calcModel.clearAll();
		}), new RCPosition(2,7));
		
		// +/-
		cp.add(new MyJButton("+/-", (e) -> {
			this.calcModel.swapSign();
		}), new RCPosition(5,4));

		// .
		cp.add(new MyJButton(".", (e) -> {
			this.calcModel.insertDecimalPoint();
		}), new RCPosition(5,5));

		// push
		cp.add(new MyJButton("push", (e) -> {
			this.stack.push(this.calcModel.getValue());
		}), new RCPosition(3,7));

		// pop
		cp.add(new MyJButton("pop", (e) -> {
			this.calcModel.setValue(this.stack.pop());
		}), new RCPosition(4,7));


		// binary operators

		ActionListener binaryOperatorAction = e -> {
			BinaryOperatorButton b = (BinaryOperatorButton)e.getSource();
			DoubleBinaryOperator op = this.calcModel.getPendingBinaryOperation();
			if(op != null && this.calcModel.isActiveOperandSet()) {
				this.calcModel.setValue(op.applyAsDouble(this.calcModel.getActiveOperand(), this.calcModel.getValue()));
			}
			this.calcModel.setActiveOperand(this.calcModel.getValue());
			this.calcModel.clear();
			this.calcModel.freezeValue(String.valueOf(this.calcModel.getActiveOperand()));
			this.calcModel.setPendingBinaryOperation(b.getOperator());
		};

		BinaryOperatorButton plusButton = new BinaryOperatorButton((left, right) -> left + right, null, "+", null);
		plusButton.addActionListener(binaryOperatorAction);
		cp.add(plusButton, new RCPosition(5,6));
		
		BinaryOperatorButton minusButton = new BinaryOperatorButton((left, right) -> left - right, null, "-", null);
		minusButton.addActionListener(binaryOperatorAction);
		cp.add(minusButton, new RCPosition(4,6));

		BinaryOperatorButton multiplyButton = new BinaryOperatorButton((left, right) -> left * right, null, "*", null);
		multiplyButton.addActionListener(binaryOperatorAction);
		cp.add(multiplyButton, new RCPosition(3,6));

		BinaryOperatorButton divideButton = new BinaryOperatorButton((left, right) -> left / right, null, "/", null);
		divideButton.addActionListener(binaryOperatorAction);
		cp.add(divideButton, new RCPosition(2,6));
		
		BinaryOperatorButton potencijaButton = new BinaryOperatorButton((left, right) -> Math.pow(left, right), (left, right) -> Math.pow(left, 1/right),
				"x^n", "x^(1/n)");
		potencijaButton.addActionListener(binaryOperatorAction);
		cp.add(potencijaButton, new RCPosition(5,1));

		// unary operator
		
		ActionListener unaryAction = e -> {
			UnaryOperatorButton b = (UnaryOperatorButton) e.getSource();
			DoubleUnaryOperator op = b.getOperator();
			this.calcModel.setValue(op.applyAsDouble(calcModel.getValue()));
		};
		
		UnaryOperatorButton sinus = new UnaryOperatorButton(Math::sin, Math::asin,
				"sin", "arcsin");
		sinus.addActionListener(unaryAction);
		cp.add(sinus, new RCPosition(2,2));

		UnaryOperatorButton cosinus = new UnaryOperatorButton(Math::cos, Math::acos,
				"cos", "arccos");
		cosinus.addActionListener(unaryAction);
		cp.add(cosinus, new RCPosition(3,2));

		UnaryOperatorButton tangens = new UnaryOperatorButton(Math::tan, Math::atan,
				"tan", "arctan");
		tangens.addActionListener(unaryAction);
		cp.add(tangens, new RCPosition(4,2));

		UnaryOperatorButton cotangens = new UnaryOperatorButton(operand -> 1. / Math.tan(operand), operand -> Math.atan(Math.tan(operand)),
				"ctg", "arcctg");
		cotangens.addActionListener(unaryAction);
		cp.add(cotangens, new RCPosition(5,2));

		UnaryOperatorButton inverseButton = new UnaryOperatorButton(operand -> 1. / operand, operand -> 1. / operand, "1/x", "1/x");
		inverseButton.addActionListener(unaryAction);
		cp.add(inverseButton, new RCPosition(2,1));

		UnaryOperatorButton logarithm = new UnaryOperatorButton(operand -> Math.log10(operand), operand -> Math.pow(10., operand),
				"log", "10^x");
		logarithm.addActionListener(unaryAction);
		cp.add(logarithm, new RCPosition(3,1));

		UnaryOperatorButton lnButton = new UnaryOperatorButton(operand -> Math.log(operand), operand -> Math.pow(Math.E, operand),
				"ln", "e^x");
		lnButton.addActionListener(unaryAction);
		cp.add(lnButton, new RCPosition(4,1));


		// inv checkbox
		JCheckBox inv = new JCheckBox("Inv");
		inv.addActionListener(e -> {
			sinus.changeOperation();
			cosinus.changeOperation();
			tangens.changeOperation();
			cotangens.changeOperation();
			logarithm.changeOperation();
			lnButton.changeOperation();
			potencijaButton.changeOperation();
			});
		cp.add(inv, new RCPosition(5,7));
	}

	// private class for code redundency
	private class MyJButton extends JButton {
		MyJButton(String text, ActionListener listener) {
			super(text);
			super.setBackground(Color.CYAN);
			super.addActionListener(listener);
		}
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new Calculator().setVisible(true);
		});
	}
}
