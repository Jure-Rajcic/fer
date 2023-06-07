package hr.fer.zemris.java.custom.scripting.nodes;
/**
 * class ForLoopNode represents script outside tags
 * primary purpose is to store script text outside tags
 * 
 * @author Jure Rajcic
 */
public class TextNode extends Node{
    private String text;
    public TextNode(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		char[] arr = this.text.toCharArray();
		for(int i = 0; i < arr.length; i++) {
			char c = arr[i];
			if (c == '{' && i + 1 < arr.length && arr[i + 1] == '$')  sb.append('\\');
			sb.append(c);
		}
		return sb.toString();
	}

	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
	
}
