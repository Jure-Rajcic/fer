package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * class CalcModelImpl represents implementation of calculator model
 * 
 * @author Jure Rajcic
 *
 */
public class CalcModelImpl implements CalcModel {
	
	// pet članskih varijabli
	 // cen add new digit, decimal point or change prefix
	 private boolean isEditable = true;
	 // + or -
	private boolean isPositive = true; 
	// inputed value
	private String value = ""; 
	// inputed value in double
	private double numericValue = 0.0; 
	// something thaht is not displayed but its used in calculation thaht folows : 1 + 2 => 1 is frozen because after we click + user doesnt see 1 on calculator display
	private String frozenValue = null; 
	
	private DoubleBinaryOperator pendingOperation = null;
	private OptionalDouble activeOperand = OptionalDouble.empty();
	private List<CalcValueListener> listenerList = new ArrayList<>();
	
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listenerList.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listenerList.remove(l);
	}
	
	@Override
	public String toString() {
		if(this.hasFrozenValue() && !this.frozenValue.isEmpty())
			return this.isPositive ? this.frozenValue : "-" + this.frozenValue;
		return this.isPositive ? "0" : "-0";
	}

	@Override
	public double getValue() {
		return this.numericValue;
	}

	@Override
	public void setValue(double value) {
		this.numericValue = value;
		this.value = String.valueOf(value);
		this.isEditable = false;
		this.freezeValue(this.value);
		notifyListeners();
	}

	@Override
	public boolean isEditable() {
		return this.isEditable;
	}

	@Override
	public void clear() {
		this.value = "";
		this.numericValue = 0.;
		this.isEditable = true;
		this.freezeValue(this.value);
		notifyListeners();
	}

	@Override
	public void clearAll() {
		this.activeOperand = OptionalDouble.empty();
		this.pendingOperation = null;
		this.clear();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!this.isEditable)
			throw new CalculatorInputException("swapSign() throw exception because value is not editable.");
		this.isPositive = this.isPositive ? false : true;
		this.numericValue *= -1;
		for(CalcValueListener listener : this.listenerList)
			listener.valueChanged(this);
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!this.isEditable || this.value.contains(".") || this.value.isEmpty())
			throw new CalculatorInputException("Already has a decimal point.");
		this.value += ".";
		for(CalcValueListener listener : this.listenerList)
			listener.valueChanged(this);
	}

	
	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!this.isEditable)
			throw new CalculatorInputException("Model is not editable.");
		String tmp = (this.value.equals("0") && digit == 0) ? this.value : this.value + digit;
		
		try {
			this.numericValue = this.isPositive ? Double.valueOf(tmp) : Double.valueOf(tmp) * -1;
		} catch (NumberFormatException ex) {
			throw new CalculatorInputException("Cant parse non numeric");
		}
		if(this.numericValue > Double.MAX_VALUE)
			throw new CalculatorInputException("Too big number");
		this.value = tmp;
		this.freezeValue(this.value);
		notifyListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		return this.activeOperand.isPresent();
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!this.activeOperand.isPresent())
			throw new IllegalStateException("Active operand isnt seted.");
		return this.activeOperand.getAsDouble();
	}

	private void notifyListeners() {
		for(CalcValueListener listener : this.listenerList)
			listener.valueChanged(this);
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = OptionalDouble.of(activeOperand);
		notifyListeners();
	}

	@Override
	public void clearActiveOperand() {
		this.activeOperand = OptionalDouble.empty();
		notifyListeners();
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return this.pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
		notifyListeners();
	}

	@Override
	public void freezeValue(String value) {
		int i = 0;
		while(i+1 < value.length() && value.charAt(i+1) != '.' && value.charAt(i) == '0')
			value = value.substring(i+1);
		this.frozenValue = value;
		notifyListeners();
	}

	@Override
	public boolean hasFrozenValue() {
		return this.frozenValue != null;
	}

}
