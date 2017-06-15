/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Represents one token of lexer.
 * 
 * @author Stjepan
 *
 */
public class Token {
	/**
	 * type of lexer, defined with enum {@link TokenType}
	 */
	private final TokenType type;
	
	/**
	 * value of lexer
	 */
	private final Object value;
	
	/**
	 * Public constructor which takes two arguments: type and value.
	 * 
	 * @param type type of token.
	 * @param value value of token.
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Gets and returns value.
	 * 
	 * @return value of lexer
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Gets and returns type.
	 * 
	 * @return type of token.
	 */
	public TokenType getType() {
		return type;
	}
		
}
