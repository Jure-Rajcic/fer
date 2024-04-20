package hr.fer.zemris.java.custom.scripting.parser;


import hr.fer.zemris.java.custom.scripting.lexer.*;

import java.util.*;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

/**
 * class SmartScriptLexer represents parser thaht reads tokens from lexer
 * Parser validates tokens or throws exceptions depending on whether the tokens
 * are valid
 * 
 * @author Jure Rajcic
 */

public class SmartScriptParser {
    private SmartScriptLexer lexer;
    private DocumentNode documentNode;
    private Stack<DocumentNode> stack;

    public SmartScriptParser(String doc) {
        this.lexer = new SmartScriptLexer(doc);
        this.documentNode = new DocumentNode();
        this.stack = new Stack<>();
        this.parse();
    }

    /**
     * Parses tokens from lexer and builds document tree structure with Nodes thath
     * contain Elements
     */
    private void parse() {
        stack.push(documentNode);
        SmartScriptToken token = lexer.nextToken();
        // System.out.println(token);
        try {
            while (token.getType() != SmartscriptTokenType.EOF) {
                switch (token.getType()) {
                    case TAG_START:
                        token = lexer.nextToken();
                        // System.out.println(token);
                        if (token.getType() != SmartscriptTokenType.KEYWORD ) {
                            throw new SmartScriptParserException("Parser was expecting keyword after tag was opend");
                        }
                        String tagName = (String) token.getValue();
                        ArrayList<Element> collection = new ArrayList<>();
                        token = lexer.nextToken();
                        // System.out.println(token);
                        Element curr;
                        while (token.getType() != SmartscriptTokenType.TAG_END) {
                            switch (token.getType()) {
                                case VARIABLE:
                                    String variableName = (String) token.getValue();
                                    curr = new ElementVariable(variableName);
                                    break;
                                case FUNCTION:
                                    String functionName = (String) token.getValue();
                                    curr = new ElementFunction(functionName);
                                    break;
                                case INTEGER:
                                    Integer integer = (Integer) token.getValue();
                                    curr = new ElementConstantInteger(integer);
                                    break;
                                case DOUBLE:
                                    Double number = (Double) token.getValue();
                                    curr = new ElementConstantDouble(number);
                                    break;
                                case STRING:
                                    curr = new ElementString((String) token.getValue());
                                    break;
                                case OPERATOR:
                                    curr = new ElementOperator((String) token.getValue());
                                    break;
                                case TAG_END:
                                    curr = null;
                                    break;
                                default:
                                    throw new SmartScriptParserException("Invalid token type inside tag");
                            }
                            collection.add(curr);
                            token = lexer.nextToken();
                            // System.out.println(token);
                        }
                        Element[] elements = new Element[collection.size()];
                        for (int index = 0; index < elements.length; index++) {
                            Element e = (Element) collection.get(index);
                            elements[index] = e;
                        }
                        switch (tagName) {
                            case "=":
                                EchoNode echoNode = new EchoNode(elements);
                                this.documentNode.addChildNode(echoNode);
                                // System.out.println(echoNode);
                                break;
                            case "FOR":
                                Element stepExpression = switch (elements.length) {
                                    case 3 -> null;
                                    case 4 -> elements[3];
                                    default -> throw new SmartScriptParserException(
                                            "ForLoopNode can have minimum 3 and maximum 4 elemets in tag");
                                };
                                if (!(elements[0] instanceof ElementVariable))
                                    throw new SmartScriptParserException( "first element of ForLoopNode needs to be variable");
                                this.documentNode.addChildNode(new ForLoopNode((ElementVariable) elements[0],
                                        elements[1], elements[2], stepExpression));
                                break;
                            case "END":
                                List<Node> children = new ArrayList<>();
                                Node node = this.documentNode.removeChild(this.documentNode.numberOfChildren() - 1);
                                while (!(node instanceof ForLoopNode)) {
                                    children.add(0,node);
                                    node = this.documentNode.removeChild(this.documentNode.numberOfChildren() - 1);
                                }
                                for (Node n : children) node.addChildNode(n);
                                this.documentNode.addChildNode(node);
                                // System.out.println(this.documentNode);
                                break;
                            default:
                                throw new SmartScriptParserException("Unsuported tag keyword");
                        }
                        break;
                    case TEXT:
                        documentNode.addChildNode(new TextNode((String) token.getValue()));
                        break;
                    default:
                        throw new SmartScriptParserException("Unsuported lexer/parser state");
                }
                token = lexer.nextToken();
                // System.out.println(token);
            }
        // }catch (SmartScriptLexerException e) { // we interpret Lexer exceptions as parser exceptions
        //     throw new SmartScriptParserException(e.getMessage());
        } catch (SmartScriptParserException e) {
            throw new SmartScriptParserException(e.getMessage());
        }
    }

    /**
     * @return DocumentNode (document model) representation of script
     */
    public DocumentNode getDocumentNode() {
        // // after END tag this.documentNode is null
        // for (DocumentNode node : stack) {
        //     this.documentNode.addChildNode(node);
        // }
        return this.documentNode;
    }

}
