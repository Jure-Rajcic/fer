package hr.fer.zemris.java.custom.scripting.lexer;
/**
 * enum SmartscriptTokenType represents the type that tokens can have
 * @author Jure Rajcic
 */
public enum SmartscriptTokenType { 
    TEXT, TAG_START, TAG_END, EOF, INTEGER, DOUBLE, OPERATOR, STRING, KEYWORD, VARIABLE, FUNCTION
}
