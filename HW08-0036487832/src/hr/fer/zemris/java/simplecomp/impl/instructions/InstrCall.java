package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementation of {@linkplain Instruction}
 * Class which is used for call subprogram.
 * 
 * <p> This command expects one argument - address of subprogram. 
 * 	   The result of operation is update of program counter.
 *     Old address is pushed to stack which is on {@linkplain Registers#STACK_REGISTER_INDEX} 
 *     index. 
 * 
 * @author Stjepan
 * @version 1.0
 */
public class InstrCall implements Instruction {
	/** address of subprogram */
	private int address;
	
	/**
	 * Constructor which takes one argument - list of register names to execute operation.
	 * 
	 * @param arguments 
	 * 					Arguments for call operation.
	 * @throws IllegalArgumentException 
	 * 					If number of arguments is not one.
	 * 					If comes to type mismatch for argument.
	 * 								
	 */
	public InstrCall(List<InstructionArgument> arguments) {
		if(arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument1!");
		}
		
		if(!arguments.get(0).isNumber()){
			throw new IllegalArgumentException( "Type mismatch for argument!" );
		}
		
		this.address = (Integer) arguments.get(0).getValue();
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Instruction#execute(hr.fer.zemris.java.simplecomp.models.Computer)
	 */
	@Override
	public boolean execute(Computer computer) {
		// gets value of PC
		Integer programCounter = (Integer)computer.getRegisters().getProgramCounter();
		// peek
		Integer stackAdress = (Integer) computer.getRegisters().
										getRegisterValue(Registers.STACK_REGISTER_INDEX);
		// writes PC value on top of stack
		computer.getMemory().setLocation(stackAdress, programCounter);
		// push 
		computer.getRegisters().
						setRegisterValue(Registers.STACK_REGISTER_INDEX, stackAdress - 1);
		// set new PC address
		computer.getRegisters().setProgramCounter(address);
		return false;
	}

}
