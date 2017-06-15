/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Represents getter of first name from {@link StudentRecord} instance.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class FirstNameFieldGetter implements IFieldValueGetter {

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw5.db.IFieldValueGetter#get(hr.fer.zemris.java.tecaj.hw5.db.StudentRecord)
	 */
	@Override
	public String get(StudentRecord record) {
		if (record == null){
			throw new IllegalArgumentException("Student record must not be null");
		}
		
		return record.getFirstName();
	}

}
