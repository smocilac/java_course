package hr.fer.zemris.java.simplecomp.impl;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * Simulator for assembly code. 
 * 
 * Program expects one argument - path to textual file with assembly code.
 * 
 * If argument is not provided, it will be asked to enter path to {@linkplain System#in}
 * 
 * @author Stjepan
 * @version 1.0
 */
public class Simulator {
	/** Scanner of program used in each class where needed. */
	public static Scanner scanner = new Scanner(System.in, "utf-8");
	
	/**
	 * Main program.
	 * 
	 * @param args no arguments or one argument expected
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Path path;
		String toPath = "";
		if (args.length == 0) {
			System.out.println("Enter path to assembly code: ");
			toPath = scanner.nextLine().trim();
		} else if (args.length == 1){
			toPath = args[0];
		} else {
			System.err.println("Expected 0 or 1 argument, but number of arguments founds " 
					+ args.length);
			System.exit(-2);
		}
		
		path = getPathFromString(toPath);
		
		// Stvori računalo s 256 memorijskih lokacija i 16 registara
		Computer comp = new ComputerImpl(256, 16);
		
		// Stvori objekt koji zna stvarati primjerke instrukcija
		InstructionCreator creator = new InstructionCreatorImpl(
								"hr.fer.zemris.java.simplecomp.impl.instructions"
							);
		
		// Napuni memoriju računala programom iz datoteke; instrukcije stvaraj
		// uporabom predanog objekta za stvaranje instrukcija
		try {
			ProgramParser.parse(
									toPath,
									comp,
									creator
								);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(-3);
		}
		
		// Stvori izvršnu jedinicu
		ExecutionUnit exec = new ExecutionUnitImpl();
		
		// Izvedi program
		exec.go(comp);
		
		scanner.close();
	}
	
	/**
	 * Gets path from string. If cannot convert method will terminate program.
	 * 
	 * @param toPath string to convert.
	 * @return Path if argument can be converted to path
	 */
	private static Path getPathFromString(String toPath){
		Path path = null;
		try {
			path = Paths.get(toPath);
		} catch (InvalidPathException e){
			System.err.println("Invalid path. ");
			System.exit(-1);
		}
		
		return path;
	}
}
