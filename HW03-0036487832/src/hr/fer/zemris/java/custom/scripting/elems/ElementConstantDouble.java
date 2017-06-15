/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents double element.
 * 
 * @author Stjepan
 *
 */
public class ElementConstantDouble extends Element{
	/** 
	 * Double value, cannot be changed. 
	 */
	private final double value;
	
	/**
	 * Public constructor, takes one argument which can be any double value.
	 * @param value Value to store. Cannot be changed.
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * @return Returns value (double) as String.
	 */
	@Override
	public String asText() {
		return Double.toString(value);
	}
	
	/**
	 * Method to get double value.
	 * 
	 * @return double value
	 */
	public double getValue() {
		return value;
	}
	
}
