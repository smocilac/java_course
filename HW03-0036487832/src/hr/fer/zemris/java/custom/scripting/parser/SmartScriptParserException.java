/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.parser;

/**
 * @author Stjepan
 *
 */
public class SmartScriptParserException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor with no arguments.
	 */
	public SmartScriptParserException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor takes two arguments : message and cause.
	 * 
	 * @param message message of exception
	 * @param cause cause of exception
	 */
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor with one argument: message to take forward
	 * 
	 * @param message Message to take forward
	 */
	public SmartScriptParserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor with one argument: cause of exception
	 * @param cause Cause of exception
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
