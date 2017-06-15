/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * The interface which provides getter of some data value from {@link StudentRecord} instance.
 * 
 * 
 * @author Stjepan
 * @version 1.0
 */
public interface IFieldValueGetter {
	
	/**
	 * Getter of some value from {@link StudentRecord} instance in argument
	 * 
	 * @param record record to take value from
	 * @return some data value from argument
	 * @throws IllegalArgumentException if argument is null
	 */
	String get(StudentRecord record);
}
