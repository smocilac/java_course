package hr.fer.zemris.java.tecaj.hw5.db.lexer;

/**
 * Class which represents one token.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class Token {

	/**
	 * type of token
	 */
	private final TokenType type;
	
	/**
	 * value of token
	 */
	private final Object value;
	
	/**
	 * Public constructor which takes two argument: type and value of token
	 * 
	 * @param type Type of token {@link TokenType}
	 * @param value Value of token
	 */
	public Token(TokenType type, Object value) {
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
	public TokenType getType() {
		return type;
	}
	
}
