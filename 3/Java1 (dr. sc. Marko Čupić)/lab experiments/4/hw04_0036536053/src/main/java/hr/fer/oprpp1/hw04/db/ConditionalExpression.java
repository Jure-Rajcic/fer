package hr.fer.oprpp1.hw04.db;

/**
 * class [ConditionalExpression] represents object of multiple strategies
 * 
 * @author Jure Rajcic
 */
public class ConditionalExpression {
    private IFieldValueGetter ifvg; // strategy for getting student atrbute e.g. [firstName]
    private String literal; // represents argument value e.g. [jure]
    private IComparisonOperator ico; // strategy for comparing e.g [EQUALS]

    public ConditionalExpression(IFieldValueGetter ifvg, String literal, IComparisonOperator ico) {
        this.ifvg = ifvg;
        this.literal = literal;
        this.ico = ico;
    }

    /**
     * @returns operator strategy
     */
    public IComparisonOperator getComparisonOperator() {
        return this.ico;
    }

    /**
     * @returns field (atribute) strategy
     */
    public IFieldValueGetter getFieldGetter() {
        return this.ifvg;
    }

    /**
     * @returns literal
     */
    public String getStringLiteral() {
        return this.literal;
    }

}
