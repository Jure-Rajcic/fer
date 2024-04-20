package hr.fer.zemris.java.custom.scripting.elems;
/**
 * class ElementString represents child of Element
 * primary purpose is to store string values
 * 
 * @author Jure Rajcic
 */
public class ElementString extends Element {

    private String value;

    /**
     * Creates new instance of ElementString with the given @param value
     */
    public ElementString(String value) {
        super();
        this.value = value;
    }

    /**
     * @return value of this string element
     */
    public String getValue() {
        // System.out.println("String stored in this element is: " + value);
        // System.out.println("String stored in this element is: " + this.value); // String stored in this element is: "text/plain"
        return value.replace("\"", "");
        // return this.value;
    }

    /**
     * @return value in string format
     */
    @Override
    public String asText() {
        return getValue();
    }

}
