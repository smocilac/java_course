package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementation of {@linkplain Instruction}
 * Class which is used terminating assembly program.
 * 
 * <p> This command expects no arguments 
 * 	   The result is terminating program.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class InstrHalt implements Instruction {
	
	/**
	 * Constructor which takes one argument - list of register names to execute operation.
	 * 
	 * @param arguments 
	 * 					Arguments for halt operation must be empty
	 * @throws IllegalArgumentException 
	 * 					If number of arguments is not 0.
	 * 								
	 */
	public InstrHalt(List<InstructionArgument> arguments){
		if (arguments.size() != 0){
			throw new IllegalArgumentException("Halt expects no arguments.");
		}
		
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Instruction#execute(hr.fer.zemris.java.simplecomp.models.Computer)
	 */
	@Override
	public boolean execute(Computer computer) {
		return true;
	}

}
