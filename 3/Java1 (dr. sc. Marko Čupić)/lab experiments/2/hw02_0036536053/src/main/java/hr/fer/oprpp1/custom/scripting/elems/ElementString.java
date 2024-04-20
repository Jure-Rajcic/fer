package hr.fer.oprpp1.custom.scripting.elems;
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
        return this.value;
    }

    /**
     * @return value in string format
     */
    @Override
    public String asText() {
        return getValue();
    }

}
