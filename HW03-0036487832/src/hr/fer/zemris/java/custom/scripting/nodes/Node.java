/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;


/**
 * Class which represents one node. Node can have any number of child.
 * 
 * @author Stjepan
 *
 */
public class Node {
	/**
	 * All children in collection.
	 */
	private ArrayIndexedCollection collection;
	
	/**
	 * Public empty constructor.
	 */
	public Node() {
		
	}
	
	/**
	 * Method creates collection if not created and adds child to collection.
	 * 
	 * @param child child to add in collection
	 * @throws IllegalArgumentException if child is null
	 */
	public void addChildNode(Node child) {
		if (collection == null) {
			collection = new ArrayIndexedCollection();
		}
		collection.add(child);
	}
	
	/**
	 * 
	 * @return number of Children
	 */
	public int numberOfChildren() {
		return collection == null ? 0 : collection.size();
	}
	
	/**
	 * Gets and returns child Node based on index from argument. 
	 * 
	 * @param index index of child to return
	 * @return child at index from argument
	 * @throws ArrayIndexOutOfBoundsException if index is not valid
	 */
	public Node getChild(int index) {
		if ( collection.get(index) instanceof Node) {
			return (Node) collection.get(index);
		}
		return null;
	}
}
