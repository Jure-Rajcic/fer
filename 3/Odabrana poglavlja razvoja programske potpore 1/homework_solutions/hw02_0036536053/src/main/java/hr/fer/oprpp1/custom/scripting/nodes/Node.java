package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
/**
 * class Node are abstract building blocks for Document model of script
 * 
 * @author Jure Rajcic
 */
public class Node {
	private ArrayIndexedCollection collection;

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
			this.collection = new ArrayIndexedCollection();
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
		return (Node) this.collection.get(index);
	}

}
