/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.db.operators;

import hr.fer.zemris.java.tecaj.hw5.db.IComparisonOperator;

/**
 * Represents comparison which define if value1 is not equal to value2.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class NotEqualOperator implements IComparisonOperator {


	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw5.db.IComparisonOperator#satisfied(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean satisfied(String value1, String value2) {
		if (value1 == null || value2 == null){
			return value1 != value2;
		}
		
		return collator.compare(value1,value2) != 0;
	}

}
