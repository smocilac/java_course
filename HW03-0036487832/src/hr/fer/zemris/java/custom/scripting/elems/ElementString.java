/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents text as String value.
 * 
 * @author Stjepan
 *
 */
public class ElementString extends Element{
	/**
	 * String value of text
	 */
	private final String value;
	
	/**
	 * Public constructor, takes one argument: text.
	 * 
	 * @param value Value to store. Cannot be changed.
	 */
	public ElementString(String value) {
		this.value = value.toUpperCase();
	}
	
	/**
	 * @return String value of stored text with escaping undo
	 */
	@Override
	public String asText() {
		String newString = value.replaceAll("\\\\", "\\\\\\\\")
								.replaceAll("\"", "\\\\\\\"")
								.replaceAll("\\{", "\\\\\\{");
		return newString;
	}
	
	/**
	 * Gets text value.
	 * 
	 * @return String value of text without undo escaping 
	 */
	public String getValue() {
		return value;
	}
}
