package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.parser.*;

/**
 * class Element are abstract building blocks for tags "FOR" and "="
 * 
 * @author Jure Rajcic
 */

public class Element {

    /**
     * servers like abstract method
     * 
     * @throws SmartScriptParserException if child class doesn't @Override this
     *                                    method
     * @returns custom String representation of child class
     */
    public String asText() {
        throw new SmartScriptParserException("Element object did not override asText() method");
        // Im more comfterble with this then just returning default string ""
    }

    /**
     * @returns custom String representation of element child
     */
    @Override
    public String toString() {
        return asText();
    }

}
