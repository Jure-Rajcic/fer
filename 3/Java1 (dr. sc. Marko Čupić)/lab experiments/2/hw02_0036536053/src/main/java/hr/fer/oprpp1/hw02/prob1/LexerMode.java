package hr.fer.oprpp1.hw02.prob1;

import hr.fer.oprpp1.hw02.prob1.demo.ObjectStack;

public class LexerMode {
    private TokenType id;
    private char c;
    private Token token;
    private LexerState state;

    public LexerMode(char c, LexerState state) {
        this(c, null, state);
    }

    private LexerMode(char c, TokenType id, LexerState state) {
        this.c = c;
        if (id == null)
            id = modeIdentificator(c);
        this.state = state;
        if (this.state == LexerState.EXTENDED) {
            id = switch (id) {
                case NUMBER, BACKSLASH -> TokenType.WORD;
                default -> id;
            };
        }
        this.id = id;
        this.token = null;
    }

    /**
     * @return TokenType for @param c
     *         TokenType is used to distinguish Lexer behaviour in different situations
     *         depending on context
     */
    private TokenType modeIdentificator(char c) {
        if (c == '\0')
            return TokenType.START;
        if (c == '\\')
            return TokenType.BACKSLASH;
        if (c - '0' >= 0 && c - '0' <= 9)
            return TokenType.NUMBER;
        if (Character.isLetter(c))
            return TokenType.WORD;
        return switch (c) {
            case ' ', '\r', '\t', '\n' -> TokenType.WHITESPACE;
            default -> TokenType.SYMBOL;
        };
    }

    /**
     * @param stack represents memory storage for Lexer
     * @param lm    represents new behaviour for Lexer, depending on content on
     *              stack Lexer makes choices dynamicly
     */
    public void updateMode(ObjectStack stack, LexerMode lm) {
        TokenType nextId = lm.id;
        switch (this.id) {
            case START:
                stack.push(lm);
                return;
            case BACKSLASH:
                if (nextId == TokenType.BACKSLASH) {
                    stack.pop();
                    stack.push(new LexerMode('\\', TokenType.WORD, this.state));
                } else if (nextId == TokenType.NUMBER) {
                    stack.pop();
                    stack.push(new LexerMode(lm.c, TokenType.WORD, this.state));
                } else {
                    throw new LexerException();
                }

                return;
            case NUMBER:
                if (nextId == TokenType.BACKSLASH || nextId == TokenType.NUMBER) {
                    stack.push(lm);
                } else {
                    LexerMode curr = (LexerMode) stack.pop();
                    String sum = "";
                    while (curr.id == TokenType.NUMBER) {
                        sum = curr.c + sum;
                        curr = (LexerMode) stack.pop();
                    }
                    stack.push(curr);
                    if (sum.length() > 15)
                        throw new LexerException();
                    token = new Token(TokenType.NUMBER, Long.parseLong(sum));
                }
                return;
            case WORD:
                if (nextId == TokenType.BACKSLASH || nextId == TokenType.WORD) {
                    stack.push(lm);
                } else {
                    LexerMode curr = (LexerMode) stack.pop();
                    String word = "";
                    while (curr.id == TokenType.WORD) {
                        word = curr.c + word;
                        curr = (LexerMode) stack.pop();
                    }
                    stack.push(curr);
                    token = new Token(TokenType.WORD, word);
                }
                return;
            case WHITESPACE:
                if (nextId != TokenType.WHITESPACE) {
                    LexerMode curr = (LexerMode) stack.pop();
                    String word = "";
                    while (curr.id == TokenType.WHITESPACE) {
                        word = curr.c + word;
                        curr = (LexerMode) stack.pop();
                    }
                    stack.push(curr);
                }
                stack.push(lm);
                return;
            case SYMBOL:
                LexerMode curr = (LexerMode) stack.pop();
                token = new Token(TokenType.SYMBOL, curr.c);
                return;
            default:
                throw new LexerException("invalid lexer mode");
        }
    }

    /**
     * when Lexer changes behaviours token can be genereted or in process of
     * generating
     * 
     * @return true only if token is generated
     */
    public boolean isTokenReady() {
        return token != null;
    }

    /**
     * @returns generated token
     */
    public Token getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "c:" + c + " id:" + id + " token:" + token;
    }

}
