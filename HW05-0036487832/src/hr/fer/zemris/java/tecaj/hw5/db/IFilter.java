/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * The interface which provides one method: accepts. 
 * 
 * @author Stjepan
 * @version 1.0
 */
public interface IFilter {
	/**
	 * Method takes one argument: instance of {@link StudentRecord}
	 * If instance is accepted based on some condition or similar returns true, false 
	 * otherwise.
	 * 
	 * @param record
	 * @return true if instance is accepted, false otherwise
	 */ 
	public boolean accepts(StudentRecord record);
}
