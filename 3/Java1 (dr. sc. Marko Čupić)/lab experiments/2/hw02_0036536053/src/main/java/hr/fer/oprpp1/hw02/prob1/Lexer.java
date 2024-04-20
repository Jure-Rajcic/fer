package hr.fer.oprpp1.hw02.prob1;

import hr.fer.oprpp1.hw02.prob1.demo.ObjectStack;

public class Lexer {
    private char[] data; // ulazni tekst
    private Token token; // trenutni token
    private int currentIndex; // indeks prvog neobrađenog znaka
    // konstruktor prima ulazni tekst koji se tokenizira
    private ObjectStack objectStack; // lexerov memoriaj
    private LexerMode lm; // trenutni mod u kojem se lexer nalazi
    private LexerState state;


    /**
     * creates Lexer object
     * @param text represents input theat Lexer needs to turn in tokens
     * @thROWS NullPointerException if text is null
     */
    public Lexer(String text) {
        if (text == null) throw new NullPointerException();
        this.data = (" " + text + " ").toCharArray();
        this.currentIndex = 0;
        this.objectStack = new ObjectStack();
        this.state = LexerState.BASIC;
        this.lm = new LexerMode('\0', this.state);
        objectStack.push(lm);
    }

    // generira i vraća sljedeći token
    // baca LexerException ako dođe do pogreške
    public Token nextToken() {
        if (token != null && token.getType() == TokenType.EOF)
            throw new LexerException();
        while (currentIndex < data.length) {
            char c = data[currentIndex];
            LexerMode curr = lm;
            lm.updateMode(objectStack, new LexerMode(c, this.state));
            lm = (LexerMode) objectStack.peek();
            if (curr.isTokenReady()) {
                this.token = curr.getToken();
                break;
            }
            currentIndex++;
        }
        lm = (LexerMode) objectStack.peek();


        if (this.currentIndex == data.length) {
            if (token == null || token.getType() != TokenType.EOF)
                this.token = new Token(TokenType.EOF, null);
        }
        return getToken();
    }

    // vraća zadnji generirani token; može se pozivati
    // više puta; ne pokreće generiranje sljedećeg tokena
    public Token getToken() {
        if(token.getType() == TokenType.SYMBOL && ((Character) token.getValue()).equals('#')){
            LexerState newState = this.state == LexerState.BASIC ?  LexerState.EXTENDED : LexerState.BASIC;
            setState(newState);
        }
        return this.token;
    }

    public void setState(LexerState state) {
        if (state == null) throw new NullPointerException();
        this.state = state;
    }

}