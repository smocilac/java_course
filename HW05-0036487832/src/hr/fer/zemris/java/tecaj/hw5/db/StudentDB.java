/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.tecaj.hw5.db.parseable.ParserException;

/**
 * The StudentDB program implements an application that displays answers for queries.
 * Every query must have command at the begging. 
 * Possible commands are: 
 * 		query - case sensitive ! It represents query based on checking every student if 
 * 				matches the condition. Conditions must be separated by AND. Searhing is 
 * 				possible by jmbag, firstName and lastName (case sensitive!).
 * 				Comparison are determined by these symbols: {@code <, <=, >, >=, =, !=, LIKE}
 * 				Comparison for LIKE can have one wildcard * which can be at the begging, in 
 * 				middle or at the end. If at begging than comparison is {@code endsWith()} etc.
 * 		indexquery - finds students by his jmbag. Here is impossible to have more than one
 * 				contidion. Only possible condition is : jmbag = "xyz" , where only 
 * 				whitespaces and xyz can be changed. Inside of quotes must be word ( which
 * 				has letters, digits or underscore ( '_' ) . This commands name is case 
 * 				sensitive.
 * 		exit -  to terminate program. It is not case sensitive.
 * 
 * Every condition must start with name attribute, than provide comparison operator and 
 * after that only string literal is allowed.
 * Some good examples of use:
 * {@code query lastName < "M"}
 * {@code query jmbag = "0000000000"}
 * {@code query firstName LIKE "M*" And lastName < "M"}
 * {@code indexquery jmbag = "000000342342"}
 * 
 * Program takes one argument - path to database file. 
 * If argument is not provided will terminate.
 * 
 * 
 * @author Stjepan
 * @version 1.0
 */
public class StudentDB {
	/** Heading of table. */
	private final static String topAndBottom = 
			"+==============+======================+======================+====+";
	
	/** Allowed commands. */
	private final static String[] commands = {"EXIT", "query", "indexquery"};
	
	/** Lines from database file */
	private static List<String> lines;
	
	/** Instance for students database. */
	private static StudentDatabase database;
	
	/** Filtered list of students. */
	private static List<StudentRecord> filtered ;
	
	/**
	 * Program takes one argument - path to database file.
	 * If argument is not provided program will terminate.
	 * 
	 * @param args path to database file.
	 */
	public static void main(String[] args) {
		if ( args.length != 1) {
			System.err.println("You must provide single argument: path to database file.");
			System.exit(1);
		}
		
		Scanner scanner = new Scanner ( System.in );
		lines = new ArrayList<>();
		
		readFromFile(args[0]);
		
		System.out.println("Welcome to database emulator. Write query please: ");
		
		database = new StudentDatabase(lines);
		
		while (true){
			System.out.print("> ");
			String entry = scanner.nextLine().trim();
			String query = "", expression = "";
			
			try { // query to one side, expression to other with substring
				query = entry.substring(0,entry.indexOf(' ')); 
				expression = entry.substring(entry.indexOf(' ')+1); 
			} catch (StringIndexOutOfBoundsException e){
				if (entry.isEmpty()) continue; // if only whitespaces entered do nothing
				query = entry;
			}
			
			if (query.toUpperCase().equals(commands[0])){ // EXIT command
				System.out.println("Goodbye!");
				break;
			} else if(query.equals(commands[1])){ // query command
				
				if (! getFilteredList(expression)){
					continue;
				}
				
				printFilteredEntrys();
				
			} else if(query.equals(commands[2])){ // indexquery command
				String jmbag = expression.trim().replaceAll("\\s+", "");
				
				if (jmbag.matches("jmbag=\"([\\w\\s]+)\"") ){
					jmbag = jmbag.substring(7, jmbag.length() - 1);
					printOneRecord(jmbag);
				} else {
					System.out.println("indexquery not possible on " + jmbag + " expression.");
				}
				
			} else { // unrecognized command
				System.out.println("Cannot recognize \"" + query + "\" command.");
				continue;
			}
		}
		
		scanner.close();

	}
	
	/**
	 * Print one record when indexquery requested.
	 * 
	 * @param jmbag students jmbag to find and print on System out
	 */
	private static void printOneRecord(String jmbag) {
		StudentRecord record = database.forJMBAG(jmbag);
		System.out.println("Using index for record retrieval.");
		if (record != null){
			System.out.println(topAndBottom + "\n"
									+ record.toString() + "\n"
									+ topAndBottom + "\n" 
									+ "Records selected: 1");
			
		} else {
			System.out.println("There is no entry with jmbag = " + jmbag);
		}
		
	}

	
	/**
	 * Prints all filtered entries to System out
	 * 
	 */
	private static void printFilteredEntrys() {
		if (filtered != null && !filtered.isEmpty()){
			System.out.println(topAndBottom);
			filtered.forEach(System.out::println);
			System.out.println(topAndBottom);
		}
		System.out.println("Records selected: " + filtered.size());
	}

	/**
	 * Gets filtered list for wanted expression.
	 * If expression is invalid returns false, true otherwise
	 * 
	 * @param expression expression to filter students
	 * @return true if valid expression
	 */
	private static boolean getFilteredList(String expression) {
		QueryFilter filter ;
		try {
			filter = new QueryFilter(expression);
			filtered = database.filter(filter);
		} catch (ParserException e){
			System.out.println(e.getMessage());
			return false;
		} catch (IllegalArgumentException e){
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	/**
	 * Method to read data from database file.
	 * Char set is UTF_8
	 * 
	 * @param string path to database file.
	 */
	private static void readFromFile(String string) {
		try {
			lines = Files.readAllLines(
					Paths.get(string),
					StandardCharsets.UTF_8
				);
		} catch (IOException e) {
			System.err.println("Unable to read or find file from path specified"
					+ " in argument. Path you have specified is: " + string );
			System.exit(1);
		}
		
	}

}
