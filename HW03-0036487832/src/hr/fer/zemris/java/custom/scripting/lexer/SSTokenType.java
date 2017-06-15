/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Represents types of Tokens.
 * 
 * @author Stjepan
 *
 */
public enum SSTokenType {
	/**Double type of token.*/
	DOUBLE, 
	/**Integer type of token.*/
	INTEGER, 
	/**Function type of token.*/
	FUNCTION, 
	/**Operator type of token.*/
	OPERATOR, 
	/**String type of token.*/
	STRING, 
	/**Variable type of token.*/
	VAR, 
	/**End of file type of token.*/
	EOF, 
	/**Symbol type of token.*/
	SYMBOL
}
