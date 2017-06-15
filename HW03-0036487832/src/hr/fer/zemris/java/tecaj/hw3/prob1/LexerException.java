/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Exception class, inherits RuntimeException.
 * 
 * @author Stjepan
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * default serial version
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Empty public constructor, takes no argument
	 */
	public LexerException() {
		super();
	}
	
	
	/**
	 * Public constructor which takes two arguments: message and cause.
	 * 
	 * @param arg0 message
	 * @param arg1 cause
	 */
	public LexerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	/**
	 * Public constructor which takes one argument - message.
	 * 
	 * @param arg0 message
	 */
	public LexerException(String arg0) {
		super(arg0);
	}
	
	/**
	 * Public constructor which takes one argument - cause.
	 * 
	 * @param arg0 cause
	 */
	public LexerException(Throwable arg0) {
		super(arg0);
	}
}
