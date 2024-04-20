package hr.fer.zemris.java.custom.scripting.nodes;
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


	// @Override
	// public boolean equals(Object o) {
	// 	if (o == this)
	// 		return true;
	// 	if (!(o instanceof DocumentNode)) {
	// 		return false;
	// 	}
	// 	WriterVisitor v1 = new WriterVisitor();
	// 	this.accept(v1);
	// 	WriterVisitor v2 = new WriterVisitor();
	// 	((DocumentNode) o).accept(v2);

	// 	return v1.getOutput().equals(v2.getOutput());
	// }

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}

}
