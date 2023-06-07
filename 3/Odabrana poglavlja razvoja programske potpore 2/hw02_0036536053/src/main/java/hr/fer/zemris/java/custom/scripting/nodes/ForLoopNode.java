package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.parser.*;

/**
 * class ForLoopNode represents tag "FOR"
 * primary purpose is to for lop information
 * 
 * @author Jure Rajcic
 */
public class ForLoopNode extends Node {
    private ElementVariable variable;
    private Element startExpression;
    private Element endExpression;
    private Element stepExpression;

    /**
     * creates new ForLoopNode with @param variable @param startExpression @param
     * endExpression @param stepExpression
     * 
     * @param variable        represents varaiable in foor loop
     * @param startExpression represent variable initial value
     * @param endExpression   represents condition in for loop
     * @param stepExpression  represents advencment after each iteration, can be
     *                        null
     */
    public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
        super();
        for (Element e : new Element[] { startExpression, endExpression, stepExpression }) {
            if (e != null && (e instanceof ElementFunction || e instanceof ElementOperator))
                throw new SmartScriptParserException("For loop cant have function element for startExpression");
        }
        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
    }

    /**
     * @returns variable
     */
    public ElementVariable getVariable() {
        return this.variable;
    }

    /**
     * @return starting starting expression
     */
    public Element getStartExpression() {
        return this.startExpression;
    }

    /**
     * @return starting end expression
     */
    public Element getEndExpression() {
        return this.endExpression;
    }

    /**
     * @return advancment expression
     */
    public Element getStepExpression() {
        return this.stepExpression;
    }

    // /**
    //  * @returns String representation foor loop with all its parameters
    //  */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{$ FOR ");
        sb.append(variable.toString() + " ");
        sb.append(startExpression.toString() + " ");
        sb.append(endExpression.toString() + " ");
        if (stepExpression != null)
            sb.append(stepExpression.toString() + " ");
        sb.append("$}");
        for (int i = 0; i < this.numberOfChildren(); i++) {
            sb.append(this.getChild(i).toString());
        }
        sb.append("{$END$}");
        return sb.toString();
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitForLoopNode(this);
    }


}
