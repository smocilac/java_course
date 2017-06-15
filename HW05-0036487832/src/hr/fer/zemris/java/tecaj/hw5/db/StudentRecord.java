/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.getters.FirstNameFieldGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getters.JmbagGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getters.LastNameFieldGetter;

/**
 * Represent records for each student. There can not exist multiple records for the same 
 * student. 
 * Two students are treated as equal if jmbags are equal.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class StudentRecord {
	/** Students unique JMBAG. */
	private final String jmbag;
	
	/** Students last name. */
	private final String lastName;
	
	/** Students first name. */ 
	private final String firstName;
	
	/** Students final grade.*/
	private int finalGrade;
	
	
	/**
	 * Constructs new students data. It stores students name, last name, jmbag and final 
	 * grade.
	 * 
	 * 
	 * @param jmbag {@link #jmbag} valid if not null
	 * @param lastName {@link #lastName} valid if not null
	 * @param firstName {@link #firstName} valid if not null
	 * @param finalGrade {@link #finalGrade} valid if in range 1 to 5 .
	 * @throws IllegalArgumentException if any of argument is not valid.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		if (jmbag == null) {
			throw new IllegalArgumentException("Every student must have jmbag. ");
		}
		if (lastName == null) {
			throw new IllegalArgumentException("Every student must have last name. ");
		}
		if (firstName == null) {
			throw new IllegalArgumentException("Every student must have first name. ");
		}
		if (finalGrade < 1 || finalGrade > 5) {
			throw new IllegalArgumentException("Students final grade must be in range 1"
					+ " to 5 !");
		}
		
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Gets and returns {@link #finalGrade}
	 * 
	 * @return {@link #finalGrade}
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/** 
	 * Sets {@link #finalGrade} to value in argument.
	 * Value is valid if in range 1 to 5.
	 * 
	 * @return {@link #finalGrade}
	 * @throws IllegalArgumentException if value in argument is not valid
	 */
	public void setFinalGrade(int finalGrade) {
		if (finalGrade < 1 || finalGrade > 5) {
			throw new IllegalArgumentException("Students final grade must be in range 1"
					+ " to 5 !");
		}
		
		this.finalGrade = finalGrade;
	}

	/**
	 * Gets and returns {@link #jmbag}
	 * 
	 * @return {@link #jmbag}
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Gets and returns {@link #lastName}
	 * 
	 * @return {@link #lastName}
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets and returns {@link #firstName}
	 * 
	 * @return {@link #firstName}
	 */
	public String getFirstName() {
		return firstName;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof StudentRecord)) {
			return false;
		}
		
		return this.jmbag.equals(((StudentRecord) obj).getJmbag());
	}


	@Override
	public int hashCode() {
		return jmbag.hashCode();
	}


	@Override
	public String toString() {
		return new StringBuilder()
						.append(String.format("|%12s  |", jmbag))
						.append(String.format(" %-20s |", lastName))
						.append(String.format(" %-20s |", firstName))
						.append(String.format(" %-3s|", finalGrade))
						.toString();
	}
	
	/**
	 * Returns all students attributes that can be queried.
	 * 
	 * @return attributes
	 */
	public static String [] getAttributes(){
		return new String[] {
				"jmbag", "lastName", "firstName"
		};
	}
	
	/**
	 * Gets and return value instance which implements {@link IFieldValueGetter} depending 
	 * on requested argument.
	 * If not exist returns null.
	 * 
	 * @param string attribute
	 * @return instance which implements {@link IFieldValueGetter}
	 */
	public static IFieldValueGetter getFieldValueGetter(String string){
		String[] att = getAttributes();
		
		if (att[0].equals(string)) return new JmbagGetter();
		if (att[1].equals(string)) return new LastNameFieldGetter();
		if (att[2].equals(string)) return new FirstNameFieldGetter();
		
		return null;
	}
	
	
	
}
