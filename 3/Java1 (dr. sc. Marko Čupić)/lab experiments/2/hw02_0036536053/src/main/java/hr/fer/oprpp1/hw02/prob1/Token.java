package hr.fer.oprpp1.hw02.prob1;

public class Token {
    private TokenType type;
    private Object value;

    public Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    public TokenType getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Token))
            return false;
        Token t = (Token) o;
        if(value == null)  return type == t.type && value == t.value;
        return type == t.type && value.equals(t.value);
    }

    @Override
    public String toString() {
        return "type " + type + " value " + value;
    }
}
