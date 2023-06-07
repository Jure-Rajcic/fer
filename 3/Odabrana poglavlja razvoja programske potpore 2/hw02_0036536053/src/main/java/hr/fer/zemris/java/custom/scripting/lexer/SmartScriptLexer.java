package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.parser.*;

/**
 * class SmartScriptLexer represents lexer thaht creates tokens of input script
 * Lexer has 2 set of rules thath he folows (states) :
 * 1. when reading Text outside tags ({$...$})
 * 2. when reading text inside tags ({$...$})
 * 
 * @author Jure Rajcic
 */

public class SmartScriptLexer {
    private int start; // start index
    private char[] data; // char representatiton of the script
    private int tags; // dznamic number of active tags, if tag is 0 thath means lexer is in text state

    /**
     * creates a new SmartScriptLexer with @param doc which represents the script
     * text that needs to be tokenized
     */
    public SmartScriptLexer(String doc) {
        this.data = doc.trim().toCharArray();
        this.start = 0;
        this.tags = 0;
    }

    /**
     * method updates Lexer private variables, which we use to determine
     * Lexer state and set of given rues for given state
     * 
     * @returns dynamic SmartScriptToken that represents token based on lexer
     *          position in text
     */
    public SmartScriptToken nextToken() {
        while (start < data.length) {
            char c = data[start];
            if (tags == 0) {
                // Lexer in text state
                /*
                 * In document text (i.e. outside of tags) parser must accept only the following
                 * two escaping:
                 * \\ treat as \
                 * \{ treat as {
                 * Every other sequence which starts with \ should throw an exception. Please
                 * note, in this context, character {
                 * is just a regular character if it is not followed directly by $.
                 */
                int end = start;
                String s = "";
                while (end < data.length && (data[end] != '{' || data[end + 1] != '$')) {
                    
                    if (data[end] == '\\') {
                        if (data[end + 1] == '\\') {
                            s += String.copyValueOf(data, start, end - start + 1);
                            end = end + 1;
                            start = end;
                        } else if (data[end + 1] == '{') {
                            s += String.copyValueOf(data, start, end - start);
                            end = end + 1;
                            start = end;
                            if (data[end + 1] == '$') {
                                end += 1;
                            }
                        } else if (!Character.isWhitespace(data[end + 1])) { // [/ ] is not sequence
                            throw new SmartScriptParserException("Invalid sequence after \\ in script outside of tags");
                            // throw new SmartScriptLexerException("Invalid sequence after \\ in script outside of tags");
                        }
                    }
                    end++;
                }
                s += String.copyValueOf(data, start, end - start);
                start = end;
                this.tags++;
                if (!s.isEmpty()) // eg. script starts with {$
                    return new SmartScriptToken(SmartscriptTokenType.TEXT, s);
                else
                    return nextToken(); // now program wil go i tag state
            } else {
                // Lexer in tag state
                /*
                 * When parsing content inside tag, lexer should extract as many characters as
                 * possible into each token.
                 */
                if (data[start] == '{' && data[start + 1] == '$') {
                    start += 2;
                    return new SmartScriptToken(SmartscriptTokenType.TAG_START, "{$");
                }
                /*
                 * Spaces
                 * between tokens are ignorable and generally not required
                 */
                else if (Character.isWhitespace(data[start])) {
                    start++;
                }
                /*
                 * Decimal numbers are only recognized in digitsdot-digits format, but not in
                 * scientific notation
                 */
                else if (Character.isDigit(data[start])) {
                    // checking if previous character is minus sign
                    int prefix = data[start - 1] == '-' ? -1 : 1;

                    int end = start + 1;
                    while (Character.isDigit(data[end]))
                        end++;
                    if (data[end] == '.')
                        end++;
                    while (Character.isDigit(data[end]))
                        end++;
                    String number = String.copyValueOf(data, start, end - start);
                    start = end;
                    if (number.contains("."))
                        return new SmartScriptToken(SmartscriptTokenType.DOUBLE, prefix * Double.valueOf(number));
                    else
                        return new SmartScriptToken(SmartscriptTokenType.INTEGER, prefix * Integer.valueOf(number));

                    /*
                     * In lexer, when deciding what to do with minus sign, treat it as a symbol if
                     * immediately after it there is no
                     * digit. Only when immediately after it (no spaces between) a digit follows
                     * (lexer can check this!), treat it as
                     * part of negative number
                     */
                } else if ("+-*/^".contains(String.valueOf(c))) {
                    start++;
                    // eg. -5 will just increment start so it points to 5 and im method that checkis
                    // if something is digit we can check previus character
                    if (!(c == '-' && Character.isDigit(data[start])))
                        return new SmartScriptToken(SmartscriptTokenType.OPERATOR, String.valueOf(c));
                }

                /*
                 * In strings which are part of tags (and only in strings!) parser must accept
                 * the following escaping:
                 * \\ sequence treat as a single string character \
                 * \" treat as a single string character " (and not the end of the string)
                 * \n, \r and \t have its usual meaning (ascii 10, 13 and 9).
                 * Every other sequence which starts with \ should be treated as invalid and
                 * throw an exception.
                 */
                else if (c == '"') {
                    int end = start + 1;
                    String s = "";
                    while (data[end] != '"') {
                        if (data[end] == '\\') {
                            if (data[end + 1] == '\\' || data[end + 1] == '"') {
                                s += String.copyValueOf(data, start, end - start);
                                end = end + 1;
                                start = end;
                            } else if (data[end + 1] == 'n' || data[end + 1] == 'r' || data[end + 1] == 't') {
                                s += String.copyValueOf(data, start, end - start);
                                if (data[end + 1] == 'n')
                                    s += '\n';
                                else if (data[end + 1] == 'r')
                                    s += '\r';
                                else if (data[end + 1] == 't')
                                    s += '\t';
                                end = end + 2;
                                start = end;
                            } else if (!Character.isWhitespace(data[end + 1])) {
                                throw new SmartScriptParserException("Invalid sequence after \\ in script inside tags");
                                // throw new SmartScriptLexerException("Invalid sequence after \\ in script inside tags");
                            }
                        }
                        end++;
                    }
                    s += String.copyValueOf(data, start, end - start + 1);
                    start = end + 1;
                    return new SmartScriptToken(SmartscriptTokenType.STRING, s);
                } else if (c == '=') {
                    start++;
                    return new SmartScriptToken(SmartscriptTokenType.KEYWORD, String.valueOf(c));
                }
                /*
                 * we wil extract all words from tag and send it to parser, he will do name
                 * validation for us
                 */
                else if (Character.isLetter(c) || c == '@') {
                    int end = start + 1;
                    while (Character.isLetterOrDigit(data[end]) || data[end] == '_') {
                        end++;
                    }
                    String word = String.copyValueOf(data, start, end - start);
                    start = end;
                    if (word.toUpperCase().equals("END")) {
                        return new SmartScriptToken(SmartscriptTokenType.KEYWORD, "END");
                    } else if (word.toUpperCase().equals("FOR")) {
                        return new SmartScriptToken(SmartscriptTokenType.KEYWORD, "FOR");
                    } else if (word.contains("@")) {
                        return new SmartScriptToken(SmartscriptTokenType.FUNCTION, word);
                    } else {
                        return new SmartScriptToken(SmartscriptTokenType.VARIABLE, word);
                    }
                }
                /*
                 * when Lexer comes acros "..$}.." he needs to close tag, if that was nested tag
                 * we just decrement tags varable so in next iteration lexer (and parser) knwos
                 * which set of
                 * rules to follow (TEXT or TAG)
                 */
                else if (c == '$' && data[start + 1] == '}') {
                    start += 2;
                    this.tags--;
                    return new SmartScriptToken(SmartscriptTokenType.TAG_END, "$}");
                }
            }
        }
        return new SmartScriptToken(SmartscriptTokenType.EOF, null);
    }
}
