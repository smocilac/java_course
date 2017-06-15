/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.db.parseable;

/**
 * Exception for parser.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class ParserException extends RuntimeException {

	/**
	 * default serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ParserException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor which takes one argument - message to display.
	 * 
	 * @param arg0 message to display.
	 */
	public ParserException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
