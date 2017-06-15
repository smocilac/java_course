package hr.fer.zemris.java.tecaj.hw6.demo5;

/**
 * Represents one student with data about his jmbag, last name, first name, tests scores
 * and final grade.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class StudentRecord {
	/** Students jmbag */
	private final String jmbag;
	/** Students last name */
	private final String lastName;
	/** Students first name */
	private final String firstName;
	/** Students score on MI */
	private double scoreMI;
	/** Students score on ZI  */
	private double scoreZI;
	/** Students core on lab */
	private double scoreLab;
	/** Students final grade */
	private int finalGrade;
	
	/**
	 * Constructor which takes 7 arguments.
	 * First three are not valid if are null.
	 * 
	 * @param jmbag
	 * @param lastName
	 * @param firstName
	 * @param scoreMI
	 * @param scoreZI
	 * @param scoreLab
	 * @param finalGrade
	 * @throws IllegalArgumentException if name, lase name of jmbag is not valid
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, double scoreMI, double scoreZI,
			double scoreLab, int finalGrade) {
		if ( jmbag == null || lastName == null || firstName == null){
			throw new IllegalArgumentException("Student cannot be defined with null.");
		}
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.scoreMI = scoreMI;
		this.scoreZI = scoreZI;
		this.scoreLab = scoreLab;
		this.finalGrade = finalGrade;
	}
	/**
	 * Gets and returns score at MI
	 * 
	 * @return
	 */
	public double getScoreMI() {
		return scoreMI;
	}
	
	/**
	 * Sets score at MI
	 * 
	 * @param scoreMI updated score.
	 */
	public void setScoreMI(double scoreMI) {
		this.scoreMI = scoreMI;
	}

	/**
	 * Gets and returns score at ZI
	 * 
	 * @return
	 */
	public double getScoreZI() {
		return scoreZI;
	}

	/**
	 * Sets score at ZI
	 * 
	 * @param scoreZI updated score.
	 */
	public void setScoreZI(double scoreZI) {
		this.scoreZI = scoreZI;
	}

	/**
	 * Gets and returns score at lab
	 * 
	 * @return score at lab
	 */
	public double getScoreLab() {
		return scoreLab;
	}

	/**
	 * Sets score at lab
	 * 
	 * @param scoreLab updated score.
	 */
	public void setScoreLab(double scoreLab) {
		this.scoreLab = scoreLab;
	}

	/**
	 * Gets and returns final grade
	 * 
	 * @return final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Sets final grade
	 * 
	 * @param scoreMI updated final grade
	 */
	public void setFinalGrade(int finalGrade) {
		this.finalGrade = finalGrade;
	}

	/**
	 * Gets and returns jmbag
	 * 
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Gets and returns last name
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets and returns first name
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		
		builder = builder.append(jmbag + " ").append(lastName + " ")
				 		 .append(firstName + " ").append(scoreMI + " ")
				 		 .append(scoreZI + " ").append(scoreLab + " ")
				 		 .append(finalGrade);
		
		return builder.toString();
		
	}
}
