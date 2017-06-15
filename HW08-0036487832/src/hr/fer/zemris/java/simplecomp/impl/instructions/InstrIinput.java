package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.Simulator;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementation of {@linkplain Instruction}
 * Class which is used to read from {@linkplain System#in}.
 * 
 * <p> This command expects address in memory to store input which must be {@linkplain Integer}. 
 * 	   The result is {@code Integer} stored at provided address and flag set to true with
 *     {@linkplain Registers#setFlag(boolean)}   
 *     If input is not integer {@code flag} will be false.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class InstrIinput implements Instruction {
	/** Address to store input */
	private int index;
	
	
	
	/**
	 * Constructor which takes one argument - list of register names to execute operation.
	 * 
	 * @param arguments 
	 * 					Arguments {@code iinput} operation.
	 * @throws IllegalArgumentException 
	 * 					If number of arguments is not one - address to store input.
	 * 					If comes to type mismatch for any of arguments.
	 * 								
	 */
	public InstrIinput(List<InstructionArgument> arguments) {
		if(arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if ( arguments.get(0).isNumber() || arguments.get(0).isString()){
			this.index = (Integer)arguments.get(0).getValue();
			return;
		}	
		
		throw new IllegalArgumentException("Unsupported argument for iinput operation.");		
	}
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Instruction#execute(hr.fer.zemris.java.simplecomp.models.Computer)
	 */
	@Override
	public boolean execute(Computer computer) {
		Integer value = null;
		String input = "";
		
		try{
			input = Simulator.scanner.nextLine();
			value = Integer.parseInt(input);
			
		}catch (Exception e){ // something went wrong
			computer.getRegisters().setFlag(false);
			return false;
		}
		
		computer.getRegisters().setFlag(true);
		computer.getMemory().setLocation(index, value);
		
		return false;
	}

}
