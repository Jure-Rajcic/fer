package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.webserver.RequestContext;
import java.text.DecimalFormat;

import java.io.IOException;
import java.util.*;

public class SmartScriptEngine {
    private DocumentNode documentNode;
    private RequestContext requestContext;
    private ObjectMultistack multistack = new ObjectMultistack();

    private INodeVisitor visitor = new INodeVisitor() {

        @Override
        public void visitTextNode(TextNode node) {
            try {
                requestContext.write(node.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void visitForLoopNode(ForLoopNode node) {
            String variableName = node.getVariable().asText();
            ValueWrapper startValue = new ValueWrapper(node.getStartExpression().asText());
            ValueWrapper endValue = new ValueWrapper(node.getEndExpression().asText());
            ValueWrapper stepValue = new ValueWrapper(node.getStepExpression() == null ? "1" : node.getStepExpression().asText());

            multistack.push(variableName, new ValueWrapper(startValue.getValue()));
            while (multistack.peek(variableName).numCompare(endValue.getValue()) <= 0) {

                for (int i = 0; i < node.numberOfChildren(); i++) {
                    // System.out.println(node.getChild(i).getClass().getSimpleName());
                    node.getChild(i).accept(this);
                }
                multistack.peek(variableName).add(stepValue.getValue());
                // System.out.println(multistack.peek(variableName).getValue());
            }
            multistack.pop(variableName);
        }


        @Override
        public void visitEchoNode(EchoNode node) {
            Stack<Object> tempStack = new Stack<>();
            for (final Element e : node.getElements()) {
                if (e instanceof ElementConstantInteger) {
                    tempStack.push(((ElementConstantInteger) e).getValue());
                } else if (e instanceof ElementConstantDouble) {
                    tempStack.push(((ElementConstantDouble) e).getValue());
                } else if (e instanceof ElementString) {
                    tempStack.push(((ElementString) e).getValue());
                } else if (e instanceof ElementVariable) {
                    ValueWrapper valueWrapper = multistack.peek(((ElementVariable) e).getName());
                    tempStack.push(valueWrapper.getValue());
                } else if (e instanceof ElementOperator) {
                    ValueWrapper valueWrapper1 = new ValueWrapper(tempStack.pop());
                    ValueWrapper valueWrapper2 = new ValueWrapper(tempStack.pop());
                    String operator = ((ElementOperator) e).getSymbol();
                    switch (operator) {
                        case "+" ->
                            valueWrapper2.add(valueWrapper1.getValue());
                        case "-" ->
                            valueWrapper2.subtract(valueWrapper1.getValue());
                        case "*" ->
                            valueWrapper2.multiply(valueWrapper1.getValue());
                        case "/" ->
                            valueWrapper2.divide(valueWrapper1.getValue());
                    }
                    tempStack.push(valueWrapper2.getValue());
                    // System.out.println(valueWrapper2.getValue());
                } else if (e instanceof ElementFunction) {
                    String functionName = ((ElementFunction) e).getName().substring(1);
                    switch (functionName) {
                        case "sin" -> {
                            double x = ((Number) tempStack.pop()).doubleValue();
                            double r = Math.sin(x);
                            tempStack.push(r);
                        }
                        case "decfmt" -> {
                            ValueWrapper f = new ValueWrapper(tempStack.pop());
                            ValueWrapper x = new ValueWrapper(tempStack.pop());
                            String format = new DecimalFormat(f.getValue().toString()).format(x.getValue());
                            tempStack.push(format);
                        }
                        case "dup" -> {
                            ValueWrapper x = new ValueWrapper(tempStack.pop());
                            tempStack.push(x.getValue());
                            tempStack.push(x.getValue());
                        }
                        case "swap" -> {
                            ValueWrapper a = new ValueWrapper(tempStack.pop());
                            ValueWrapper b = new ValueWrapper(tempStack.pop());
                            tempStack.push(a.getValue());
                            tempStack.push(b.getValue());
                        }
                        case "setMimeType" -> {
                            String x = tempStack.pop().toString();
                            requestContext.setMimeType(x);
                        }
                        case "paramGet" -> {
                            String dv = String.format("%s", tempStack.pop());
                            String name = (String) tempStack.pop();
                            String value = requestContext.getParameter(name);
                            tempStack.push(value == null ? dv : value);
                        }
                        case "pparamGet" -> {
                            String dv =  String.format("%s", tempStack.pop());
                            String name =  String.format("%s", tempStack.pop());
                            String value = requestContext.getPersistentParameter(name);
                            // System.out.println(dv);
                            // System.out.println(name);
                            // System.out.println(value);
                            tempStack.push(value == null ? dv : value);
                            // System.out.println(tempStack.peek());
                        }
                        case "pparamSet" -> {
                            String name = String.format("%s", tempStack.pop());
                            String value =  String.format("%s", tempStack.pop());
                            requestContext.setPersistentParameter(name, value);
                        }
                        case "pparamDel" -> {
                            String name =  String.format("%s", tempStack.pop());
                            requestContext.removePersistentParameter(name);
                        }
                        case "tparamGet" -> {
                            String dv =  String.format("%s", tempStack.pop());
                            String name = String.format("%s", tempStack.pop());
                            String value = requestContext.getTemporaryParameter(name);
                            tempStack.push(value == null ? dv : value);
                        }
                        case "tparamSet" -> {
                            String name = String.format("%s", tempStack.pop());
                            String value =  String.format("%s", tempStack.pop());
                            requestContext.setTemporaryParameter(name, value);
                            // for (String n : requestContext.getTemporaryParameterNames()) {
                            //     System.out.println("Name: " + n + " Value: " + requestContext.getTemporaryParameter(n));
                            // }
                        }
                        case "tparamDel" -> {
                            String name = String.format("%s", tempStack.pop());
                            requestContext.removeTemporaryParameter(name);
                        }
                    }
                }
            }
            try {
                for (Object value : tempStack) {
                    requestContext.write(value.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void visitDocumentNode(DocumentNode node) {
            for (int i = 0; i < node.numberOfChildren(); i++) {
                // System.out.println("Visiting child " + node.getChild(i).getClass().getSimpleName());
                node.getChild(i).accept(this);
            }
        }
    };

    public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
        this.documentNode = documentNode;
        this.requestContext = requestContext;
    }

    public void execute() {
        documentNode.accept(visitor);
    }
}