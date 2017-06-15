/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents one variable.
 * 
 * @author Stjepan
 *
 */
public class ElementVariable extends Element{
	/**
	 * Name of variable
	 */
	private final String name ;
	
	/**
	 * Public constructor, takes one argument : name of variable.
	 * 
	 * @param name Name of variable, cannot be changed.
	 */
	public ElementVariable(String name) {
		this.name = name.toUpperCase();
	}
	
	/**
	 * @return name of variable
	 */
	@Override
	public String asText() {
		return name;
	}
	
	/**
	 * Gets name of variable.
	 * 
	 * @return name of variable
	 */
	public String getName() {
		return name;
	}
	
}
