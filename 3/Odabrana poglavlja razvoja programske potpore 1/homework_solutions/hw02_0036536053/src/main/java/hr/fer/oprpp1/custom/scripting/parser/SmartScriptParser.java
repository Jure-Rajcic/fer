package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

import hr.fer.oprpp1.custom.scripting.demo.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptToken;
import hr.fer.oprpp1.custom.scripting.lexer.SmartscriptTokenType;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

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
    private ObjectStack stack;

    public SmartScriptParser(String doc) {
        this.lexer = new SmartScriptLexer(doc);
        this.documentNode = new DocumentNode();
        this.stack = new ObjectStack();
        this.parse();
    }

    /**
     * Parses tokens from lexer and builds document tree structure with Nodes thath
     * contain Elements
     */
    private void parse() {
        stack.push(documentNode);
        SmartScriptToken token = lexer.nextToken();
        try {
            while (token.getType() != SmartscriptTokenType.EOF) {
                switch (token.getType()) {
                    case TAG_START:
                        token = lexer.nextToken();
                        if (token.getType() != SmartscriptTokenType.KEYWORD)
                            throw new SmartScriptParserException("Parser was expecting keyword after tag was opend");
                        String tagName = (String) token.getValue();
                        ArrayIndexedCollection collection = new ArrayIndexedCollection();
                        token = lexer.nextToken();
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
                        }
                        Element[] elements = new Element[collection.size()];
                        for (int index = 0; index < elements.length; index++) {
                            Element e = (Element) collection.get(index);
                            elements[index] = e;
                        }
                        switch (tagName) {
                            case "=":
                                this.documentNode.addChildNode(new EchoNode(elements));
                                break;
                            case "FOR":
                                Element stepExpression = switch (elements.length) {
                                    case 3 -> null;
                                    case 4 -> elements[3];
                                    default -> throw new SmartScriptParserException(
                                            "ForLoopNode can have minimum 3 and maximum 4 elemets in tag");
                                };
                                if (!(elements[0] instanceof ElementVariable))
                                    throw new SmartScriptParserException(
                                            "first element of ForLoopNode needs to be variable");
                                this.documentNode.addChildNode(new ForLoopNode((ElementVariable) elements[0],
                                        elements[1], elements[2], stepExpression));
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
        return documentNode;
    }

}
