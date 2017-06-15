package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Provides method for simple mathematical operations on objects: increment, decrement, 
 * multiply and divide.
 * 
 * @author Stjepan
 * @version 1.0
 */
public interface SimpleArithmeticOperation {
	/**
	 * Should provide increment operation on Object.
	 * @param incValue object to increment with
	 */
	void increment(Object incValue);
	
	/**
	 * Should provide decrement operation on Object.
	 * @param incValue object to decrement with
	 */
	void decrement(Object decValue);
	
	/**
	 * Should provide multiply operation on Object.
	 * @param incValue object to multiply with
	 */
	void multiply(Object mulValue);
	
	/**
	 * Should provide divide operation on Object.
	 * @param incValue object to divide with
	 */
	void divide(Object divValue);
}
