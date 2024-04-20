package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * class SmartScriptLexerException represents child of RuntimeException that
 * ocures when
 * class SmartScriptLexer rules for given state are not satisfied
 * 
 * @author Jure Rajcic
 */
public class SmartScriptLexerException extends RuntimeException {

    /**
     * Creates a new SmartScriptLexerException
     */
    public SmartScriptLexerException() {
    }

    /**
     * Creates a new SmartScriptLexerException from @param message
     */
    public SmartScriptLexerException(String message) {
        super(message);
    }

    /**
     * Creates a new SmartScriptLexerException from @param cause
     */
    public SmartScriptLexerException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new SmartScriptLexerException from @param message and @param cause
     */
    public SmartScriptLexerException(String message, Throwable cause) {
        super(message, cause);
    }

}
