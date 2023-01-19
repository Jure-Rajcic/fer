package hr.fer.oprpp1.custom.scripting.nodes;
/**
 * class DocumentNode represents scripts (tree) model 
 * primary purpose is to store other Nodes
 * 
 * @author Jure Rajcic
 */
public class DocumentNode extends Node {
	/**
	 * Creates a new DocumentNode instance
	 */
	public DocumentNode() {
		super();
	}

	/**
	 * @return Strign representation of documentNode that needs to have same
	 *         document model as the original script 
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.numberOfChildren(); i++) {
			Node child = this.getChild(i);
			sb.append(child.toString());
		}
		return sb.toString();
	}


	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof DocumentNode)) {
			return false;
		}
		DocumentNode documentNode = (DocumentNode) o;
		return this.toString().equals(documentNode.toString());
	}

}
