/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Represents one text Node.
 * 
 * @author Stjepan
 *
 */
public class TextNode extends Node{
	/**
	 * Text of node
	 */
	private final String text;
	
	/**
	 * Public constructor takes one parameter : text to store
	 * 
	 * @param text Text to store.
	 */
	public TextNode (String text) {
		this.text = text;
	}
	
	/**
	 * 
	 * @return stored Text
	 */
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return text;
	}
	

	
}
