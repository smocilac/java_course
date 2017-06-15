/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents operator element.
 * 
 * @author Stjepan
 *
 */
public class ElementOperator extends Element{
	/**
	 * symbol of operator.
	 */
	private final String symbol ;
	
	/**
	 * Public constructor which takes one argument: symbol of operator.
	 * 
	 * @param symbol operator symbol
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * @return symbol name 
	 */
	@Override
	public String asText() {
		return symbol;
	}
	
	/**
	 * Gets symbol of operator. 
	 * 
	 * @return symbol of operator
	 */
	public String getSymbol() {
		return symbol;
	}
}
