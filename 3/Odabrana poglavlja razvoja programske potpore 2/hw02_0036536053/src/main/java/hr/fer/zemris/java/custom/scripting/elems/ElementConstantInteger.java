package hr.fer.zemris.java.custom.scripting.elems;
/**
 * class ElementConstantInteger represents child of Element
 * primary purpose is to store integer 
 * 
 * @author Jure Rajcic
 */
public class ElementConstantInteger extends Element {
    private int value;

    /**
     * Creates new instance of ElementConstantInteger with the given @param value
     */
    public ElementConstantInteger(int value) {
        this.value = value;
    }

    /**
     * @return integer value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * @return integer value in string format
     */
    @Override
    public String asText() {
        return String.format("%d", this.value);
    }

}
