/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

/**
 * Echo node inherits {@link Node}.
 * 
 * @author Stjepan
 *
 */
public class EchoNode extends Node{
	/**
	 * All elements of echo node.
	 */
	private final Element [] elements ;
	
	/**
	 * Public constructor with one argument: elements.
	 * 
	 * @param elements elements of echo node. Cannot be changed.
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}
	
	/**
	 * Gets and returns elements.
	 * 
	 * @return all elements of node.
	 */
	public Element[] getElements() {
		return elements;
	}
	
	@Override
	public String toString() {
		String stringValue = new String();
		stringValue += "{$= ";
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] instanceof ElementString) {
				stringValue += "\"" + (elements[i].asText() + "\"" + " ");
			} else {
				stringValue += (elements[i].asText() + " ");
			}
		}
		stringValue += "$}";
		return stringValue;
	}
	
	
}
