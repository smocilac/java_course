/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Document node inherits {@link Node} and represents node as document.
 * 
 * @author Stjepan
 *
 */
public class DocumentNode extends Node{
	
	/**
	 * Public empty constructor 
	 */
	public DocumentNode() {
		
	}
	
	@Override
	public String toString() {
		String docBody = new String();
		for (int i = 0 ; i < this.numberOfChildren(); i++) {
			docBody += this.getChild(i).toString();
		}
		return docBody;
	}
}
