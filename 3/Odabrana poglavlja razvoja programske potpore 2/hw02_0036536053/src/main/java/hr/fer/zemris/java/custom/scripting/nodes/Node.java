package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.ArrayList;

/**
 * class Node are abstract building blocks for Document model of script
 * 
 * @author Jure Rajcic
 */
public abstract class Node {
	private ArrayList<Node> collection;

	/**
	 * creates a new Node whit empty collection
	 */
	public Node() {
		this.collection = null;
	}

	/**
	 * adds a node to the collection
	 */
	public void addChildNode(Node child) {
		if (this.collection == null)
			this.collection = new ArrayList<>();
		this.collection.add(child);
	}

	/**
	 * @return number of children in the collection
	 */
	public int numberOfChildren() {
		if (this.collection == null)
			return 0;
		return this.collection.size();
	}

	/**
	 * @returns node on given index
	 * @throws IndexOutOfBoundsException if invalid index
	 */
	public Node getChild(int index) {
		return this.collection.get(index);
	}

	public Node removeChild(int index) {
		return this.collection.remove(index);
	}

	public abstract void accept(INodeVisitor visitor);
}
