/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.lexer;


/**
 * Class which represents one token.
 * 
 * @author Stjepan
 *
 */
public class SmartScriptToken {
	/**
	 * type of token
	 */
	private final SSTokenType type;
	
	/**
	 * value of token
	 */
	private final Object value;
	
	/**
	 * Public constructor which takes two argument: type and value of token
	 * 
	 * @param type Type of token {@link SSTokenType}
	 * @param value Value of token
	 */
	public SmartScriptToken(SSTokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * 
	 * @return value of token
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * 
	 * @return tokens type
	 */
	public SSTokenType getType() {
		return type;
	}
	
	
}
