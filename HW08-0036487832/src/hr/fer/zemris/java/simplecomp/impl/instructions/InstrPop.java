package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementation of {@linkplain Instruction}
 * Class which is used for pop from stack.
 * Address of stack is defined at {@linkplain Registers#STACK_REGISTER_INDEX}
 * 
 * <p> This command expects one directly addressed registers. 
 * 	   The result is stack pop. Old value is stored to provided register.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class InstrPop implements Instruction {
	/** register of index to pop */
	private int indexRegister;
	
	/**
	 * Constructor which takes one argument - list of register names to execute operation.
	 * 
	 * @param arguments 
	 * 					Arguments for pop operation.
	 * @throws IllegalArgumentException 
	 * 					If number of arguments is not one.
	 * 					If comes to type mismatch for any of arguments.
	 * 								
	 */
	public InstrPop(List<InstructionArgument> arguments) {
		if(arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument1!");
		}
		
		if(!arguments.get(0).isRegister() ||
				RegisterUtil.isIndirect((Integer)arguments.get(0).getValue())){
			throw new IllegalArgumentException( "Type mismatch for argument!" );
		}
		
		this.indexRegister = RegisterUtil.getRegisterIndex((Integer)arguments.get(0).getValue());
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Instruction#execute(hr.fer.zemris.java.simplecomp.models.Computer)
	 */
	@Override
	public boolean execute(Computer computer) {
		// get current address
		int memAddress = (Integer)computer.getRegisters().
				getRegisterValue(Registers.STACK_REGISTER_INDEX);
		// pop from stack by increment address value
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, 
							(Integer) memAddress + 1); 
		// put value to register
		computer.getRegisters().setRegisterValue(indexRegister, 
				computer.getMemory().getLocation(memAddress + 1));
		
		return false;
	}

}
