package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementation of {@linkplain Instruction}
 * Class which is used for push to stack.
 * Address of stack is defined at {@linkplain Registers#STACK_REGISTER_INDEX}
 * 
 * <p> This command expects one directly addressed registers. 
 * 	   The result is push value from provided register to stack. 
 * 
 * @author Stjepan
 * @version 1.0
 */
public class InstrPush implements Instruction {
	
	/** Index of register. */
	private int indexRegister;
	
	/**
	 * Constructor which takes one argument - list of register names to execute operation.
	 * 
	 * @param arguments 
	 * 					Arguments for push operation.
	 * @throws IllegalArgumentException 
	 * 					If number of arguments is not one.
	 * 					If comes to type mismatch for any of arguments.
	 */
	public InstrPush(List<InstructionArgument> arguments) {
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
		// get current stack address
		int memAddress = (Integer)computer.getRegisters().
							getRegisterValue(Registers.STACK_REGISTER_INDEX);
		// move stack pointer to next address
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, 
							(Integer) memAddress - 1); 
		// put value from register to memory
		computer.getMemory().setLocation(memAddress, 
							computer.getRegisters().getRegisterValue(indexRegister));
		
		return false;
	}

}
