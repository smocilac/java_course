/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.db;

import java.text.Collator;
import java.util.Locale;

/**
 * The interface which provides one method: satisfied. It should tell whether some 
 * condition is satisfied or not.
 * 
 * @author Stjepan
 * @version 1.0
 *
 */
public interface IComparisonOperator {
	
	/** locale (Croatia) */
	public static final Locale locale = new Locale("hr", "HR");
	
	/** Collator for defined locale */
	public static final Collator collator = Collator.getInstance(locale);
	
	/**
	 * Method to check whether some condition which refers on Strings from argument is 
	 * satisfied. 
	 * Value1 should be compared with value2, not opposite.
	 * 
	 * @param value1 value to compare with
	 * @param value2 value to compare to
	 * @return true if condition is satisfied, false otherwise.
	 */
	boolean satisfied(String value1, String value2);
}
