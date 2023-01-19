package hr.fer.oprpp1.custom.scripting.parser;

/**
 * class SmartScriptParserException represents child of RuntimeException that
 * ocures when
 * class SmartScriptParser rules for given state are not satisfied
 * 
 * @author Jure Rajcic
 */

public class SmartScriptParserException extends RuntimeException {
    /**
     * Creates a new SmartScriptParserException
     */
    public SmartScriptParserException() {
    }

    /**
     * Creates a new SmartScriptParserException from @param message
     */
    public SmartScriptParserException(String message) {
        super(message);
    }

    /**
     * Creates a new SmartScriptParserException from @param cause
     */
    public SmartScriptParserException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new SmartScriptParserException from @param message and @param cause
     */
    public SmartScriptParserException(String message, Throwable cause) {
        super(message, cause);
    }

}
