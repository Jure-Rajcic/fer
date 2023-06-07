package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

public class WriterVisitor implements INodeVisitor {

    private StringBuilder sb = new StringBuilder();

    @Override
    public void visitTextNode(TextNode node) {
		char[] arr = node.getText().toCharArray();
		for(int i = 0; i < arr.length; i++) {
			char c = arr[i];
			if (c == '{' && i + 1 < arr.length && arr[i + 1] == '$')  sb.append('\\');
			sb.append(c);
		}
    }

    @Override
    public void visitForLoopNode(ForLoopNode node) {
        sb.append("{$ FOR ");
        sb.append(node.getVariable().toString() + " ");
        sb.append(node.getStartExpression().toString() + " ");
        sb.append(node.getEndExpression().toString() + " ");
        if (node.getStepExpression() != null)
            sb.append(node.getStepExpression().toString() + " ");
        sb.append("$}");
        for (int i = 0; i < node.numberOfChildren(); i++) {
            // System.out.println(node.getChild(i).getClass().getSimpleName());
            Node child = node.getChild(i);
            child.accept(this);
        }
        sb.append("{$END$}");
    }

    @Override
    public void visitEchoNode(EchoNode node) {
        sb.append("{$= ");
		for (Element e : node.getElements()) {
			sb.append(e.toString() + " ");
		}
		sb.append("$}");
    }

    @Override
    public void visitDocumentNode(DocumentNode node) {
        for (int i = 0; i < node.numberOfChildren(); i++) {
			Node child = node.getChild(i);
            child.accept(this);
		}
        // System.out.println("[" + this.getOutput() + "]");
        System.out.println(sb.toString());
    }


    // public String getOutput() {
    //     return sb.toString();
    // }
    
}
