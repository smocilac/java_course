package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementation of {@linkplain Instruction}
 * Class which is used for jump to an address of the next instruction.
 * 
 * <p> This command expects one argument which must be address. 
 * 	   The result is {@linkplain Registers#setProgramCounter(int)} to value of address 
 *     from argument.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class InstrJump implements Instruction {
	
	/** Index of memory location to jump on */
	private int indexMem;
	
	/**
	 * Constructor which takes one argument - list of register names to execute operation.
	 * List should contain only new value for program counter.
	 * 
	 * @param arguments 
	 * 					Arguments for jump operation.
	 * @throws IllegalArgumentException
	 * 					If number of arguments is not one.
	 * 					If comes to type mismatch.
	 * 								
	 */
	public InstrJump(List<InstructionArgument> arguments) {
		if(arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		
		if(!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument!");
		}
		
		indexMem = (Integer)arguments.get(0).getValue();
	}

	

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Instruction#execute(hr.fer.zemris.java.simplecomp.models.Computer)
	 */
	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setProgramCounter(indexMem);
		return false;
	}

}
