package hr.fer.zemris.java.custom.scripting.elems;
/**
 * class ElementConstantDouble represents child of Element
 * primary purpose is to store double 
 * 
 * @author Jure Rajcic
 */
public class ElementConstantDouble extends Element {
    private double value;

    /**
     * Creates new instance of ElementConstantDouble with the given @param value
     */
    public ElementConstantDouble(double value) {
        this.value = value;
    }

    /**
     * @return double value
     */
    public double getValue() {
        return this.value;
    }

    /**
     * @return double value in string format
     */
    @Override
    public String asText() {
        return "" + this.value;
    }

}
