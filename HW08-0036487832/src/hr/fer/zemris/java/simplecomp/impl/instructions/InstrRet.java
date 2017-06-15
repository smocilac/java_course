package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementation of {@linkplain Instruction}
 * Class which is used for returning from subprogram.
 * It only reads address from top of the stack and sets {@code program counter} to its value.
 * Address of stack is defined at {@linkplain Registers#STACK_REGISTER_INDEX}
 * 
 * <p> This command expects no arguments
 * 	   The result is new value of {@code program counter}
 * 
 * @author Stjepan
 * @version 1.0
 */
public class InstrRet implements Instruction {
	
	/**
	 * Constructor which takes one argument - list of register names to execute operation.
	 * 
	 * @param arguments 
	 * 					Arguments for {@code ret} operation.
	 * @throws IllegalArgumentException 
	 * 					If number of arguments is not zero..
	 * 					If comes to type mismatch for any of arguments.
	 * 	
	 */
	public InstrRet(List<InstructionArgument> arguments) {
		if(arguments.size() != 0) {
			throw new IllegalArgumentException("'ret' doesn't expects arguments!");
		}
		
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Instruction#execute(hr.fer.zemris.java.simplecomp.models.Computer)
	 */
	@Override
	public boolean execute(Computer computer) {
		// get address
		Integer memAddr = (Integer) computer.getRegisters().
							getRegisterValue(Registers.STACK_REGISTER_INDEX);
		
		// move stack pointer to previous address
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, 
							memAddr + 1); 
		
		// set PC to value of recent stack top
		computer.getRegisters().setProgramCounter(
							(Integer) computer.getMemory().getLocation(memAddr+1));
		return false;
	}

}
