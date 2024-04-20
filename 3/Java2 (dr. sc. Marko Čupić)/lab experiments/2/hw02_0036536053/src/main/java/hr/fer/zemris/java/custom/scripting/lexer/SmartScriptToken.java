package hr.fer.zemris.java.custom.scripting.lexer;
/**
 * class SmartScriptToken represents end product of lexer
 * serves as comunication between SmartScriptLexer and SmartScriptParser
 * 
 * @author Jure Rajcic
 */
public class SmartScriptToken {
    private SmartscriptTokenType type;
    private Object value;

    /**
     * Creates a new SmartScriptToken with @param type and @param value
     */
    public SmartScriptToken(SmartscriptTokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    /**
     * @return token value
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * @returns toekn type
     */
    public SmartscriptTokenType getType() {
        return this.type;
    }

    /**
     * we take that a2 tokens are same if they have same value and same
     * type
     * 
     * @returns true only if 2 tokens are same :
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SmartScriptToken))
            return false;
        SmartScriptToken t = (SmartScriptToken) o;
        if (value == null)
            return type == t.type && value == t.value;
        return type == t.type && value.equals(t.value);
    }

    /**
     * return String representation of token
     */
    @Override
    public String toString() {
        return type + ": [" + value + "]";
    }
}
