package hr.fer.oprpp1.custom.scripting.elems;
/**
 * class ElementOperator represents child of Element
 * primary purpose is to store operator symbols 
 * 
 * @author Jure Rajcic
 */
public class ElementOperator extends Element {
    private String symbol;

    /**
     * Creates new instance of ElementOperator with the given @param symbol
     */
    public ElementOperator(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return operator symbol
     */
    public String getSymbol() {
        return this.symbol;
    }

    /**
     * @return operator in string format
     */
    @Override
    public String asText() {
        return getSymbol();
    }

}
