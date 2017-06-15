package hr.fer.zemris.java.tecaj.hw5.db.lexer;
/**
 * Enum which represents possible types of {@link Token}.
 * 
 * @author Stjepan
 * @version 1.0
 */
public enum TokenType {
	/** Token is attribute */
	ATTRIBUTE_NAME,
	
	/** Token is command */
	COMMAND, 
	
	/** Token is string literal */
	STRING_LITERAL,
	
	/** Token is comparison operator */
	OPERATOR,
	
	/** Token is logic operator */
	LOGIC_OPERATOR,
	
	/** Token represents end of file */
	EOF
}
