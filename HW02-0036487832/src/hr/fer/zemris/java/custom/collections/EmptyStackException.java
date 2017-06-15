/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

/**
 * Extends Exception, created for stack implementations.
 * 
 * @author Stjepan
 */
public class EmptyStackException extends RuntimeException {
    /**
	 * serial version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new Exception.
	 */
	public EmptyStackException () {
        super();
    }
	
	/**
	 * Creates new Exception with message.
	 * @param message to write on error output.
	 */
	public EmptyStackException (String message) {
        super(message);
    }
}
