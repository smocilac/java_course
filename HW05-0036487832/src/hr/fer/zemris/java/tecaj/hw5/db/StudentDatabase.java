/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;

/**
 * Represents database for students.
 * 
 * It creates an index for fast retrieval of student records when jmbag is known, so that
 * complexity of getting students record is O(1) when jmbag is known.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class StudentDatabase{
	/** Mapped student records where jmbag is key */
	private SimpleHashtable<String, StudentRecord> table;
	
	/** List of students in correct order. */
	private List<StudentRecord> studentsList;
	
	/**
	 * Constructs students database from list in argument.
	 * List is valid if it is not null and if every string is parseable to four arguments:
	 * students jmbag, last name, first name and grade. Order also must be preserved.
	 * 
	 * @param database students to put in database.
	 * @throws IllegalArgumentException if database is null
	 * @throws NumberFormatException if final grade from list not parseable to Integer
	 * @throws NoSuchElementException 
	 * 					if list contains student which has not 4 arguments.
	 */
	public StudentDatabase(List <String> database) {
		if (database == null) {
			throw new IllegalArgumentException("Cannot make students database with null "
					+ " argument");
		}
		
		makeStudentRecordsTable(database);
	}
	
	/**
	 * Puts all students data from constructor into {@link #table}.
	 * 
	 * @param database list of students from constructor
	 */
	private void makeStudentRecordsTable(List<String> database) {
		table = new SimpleHashtable<>(database.size());
		studentsList = new ArrayList<>(database.size());
		
		for (String entry : database) {
			String[] args = entry.split("\\s+");
			
			if (args.length > 4) {
				int all = args.length;
				int lastNames =  all - 4 + 1;
				String[] newArgs = new String[4];
				newArgs[0] = args[0];
				newArgs[1] = "";
				all = 1;
				while (all <= lastNames){
					newArgs[1] += (args[all++]);
					if (all <= lastNames){
						newArgs[1] += " ";
					}
				}
				newArgs[2] = args[all++];
				newArgs[3] = args[all];
				args = newArgs;
			}
			StudentRecord record = new StudentRecord(args[0], args[1], args[2], 
															Integer.parseInt(args[3]));
			table.put(args[0], record);
			studentsList.add(record);
		}
			
	}

	
	/**
	 * Gets and returns student record with specified jmbag.
	 * 
	 * @param jmbag students jmbag
	 * @return null if argument is null or not exist in table, else it returns 
	 * 			student record with specified jmbag.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return table.get(jmbag);
	}
	
	/**
	 * It takes filter as argument, and returns all of the students record which returned
	 * true for specified filter operation.
	 * 
	 * @param filter filter
	 * @return filtered list of students records.
	 * @throws IllegalArgumentException if argument is null
	 */
	public List<StudentRecord> filter(IFilter filter){
		if (filter == null) {
			throw new IllegalArgumentException("Filter operation must not be null.");
		}
		
		List<StudentRecord> filtered = new ArrayList<>();
		
		for (StudentRecord entry : studentsList) {
			if (filter.accepts(entry)) {
				filtered.add(entry);
			}
		}
		
		return filtered;
	}

}
