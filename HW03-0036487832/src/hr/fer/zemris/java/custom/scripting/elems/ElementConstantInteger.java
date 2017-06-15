/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents integer element.
 * 
 * @author Stjepan
 *
 */
public class ElementConstantInteger extends Element{
	/**
	 * Integer value 
	 */
	private final int value;
	
	/**
	 * 
	 * 
	 * @param value Value to store, cannot be changed
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * @return String value of element
	 */
	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
	/**
	 * Gets integer value of element
	 * @return integer value of element
	 */
	public int getValue() {
		return value;
	}
}
