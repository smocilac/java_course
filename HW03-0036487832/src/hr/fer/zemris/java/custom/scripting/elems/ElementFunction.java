/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents funtion element.
 * 
 * @author Stjepan
 *
 */
public class ElementFunction extends Element{
	/**
	 * name of function
	 */
	private final String name ;
	
	/**
	 * Public constructor which takes one argument: name of function.
	 * 
	 * @param name function name
	 */
	public ElementFunction(String name) {
		this.name = name.toUpperCase();
	}
	
	/**
	 * @return function name starting with mark '@' 
	 */
	@Override
	public String asText() {
		return "@" + name;
	}
	
	/**
	 * Gets name of function.
	 * 
	 * @return name of function
	 */
	public String getName() {
		return name;
	}
}
