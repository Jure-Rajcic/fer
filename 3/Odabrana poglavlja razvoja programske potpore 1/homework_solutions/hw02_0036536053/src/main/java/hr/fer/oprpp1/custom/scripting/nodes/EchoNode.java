package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
/**
 * class EchoNode represents tag "=" 
 * primary purpose is to store elements in given tag
 * 
 * @author Jure Rajcic
 */
public class EchoNode extends Node{
    private Element[] elements;

	/**
	 * creates a new EchoNode instance with the @param elements
	 * @throws SmartScriptParserException if @param elements is empty
	 */
	public EchoNode(Element[] elements) {
		if(elements.length == 0) throw new SmartScriptParserException("cant create EchoNode instance with empty elements array");		
		this.elements = elements;
	}

	/**
	 * @return elements in this node
	 */
	public Element[] getElements() {
		return this.elements;
	}

	
	/**
	 * @return String representation of this Node as String
	 */
	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
		sb.append("{$ = ");
		for (Element e : elements) {
			sb.append(e.toString() + " ");
		}
		sb.append("$}");
        return sb.toString();
	}
	
}
