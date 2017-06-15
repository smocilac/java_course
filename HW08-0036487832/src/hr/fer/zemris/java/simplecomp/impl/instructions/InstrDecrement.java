package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementation of {@linkplain Instruction}
 * Class which is used for decrement.
 * 
 * <p> This command expects one argument - directly addressed register 
 * 	   The result of operation is decrement of registers value.
 *     index. 
 * 
 * @author Stjepan
 * @version 1.0
 */
public class InstrDecrement implements Instruction {
	
	/** Index of register to decrement value */
	private int indexRegister;
	
	/**
	 * Constructor which takes one argument - list of register names to execute operation.
	 * Decrement requires only one register as argument.
	 * 
	 * @param arguments 
	 * 					Arguments for decrement operation.
	 * @throws IllegalArgumentException 
	 * 					If number of arguments is not one.
	 * 					If comes to type mismatch for any of arguments.
	 * 								
	 */
	public InstrDecrement(List<InstructionArgument> arguments) {
		if(arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		
		if(!arguments.get(0).isRegister() ||
							RegisterUtil.isIndirect((Integer)arguments.get(0).getValue())) {
			throw new IllegalArgumentException( "Type mismatch for argument !" );
		}
		
		
		this.indexRegister = RegisterUtil.getRegisterIndex((Integer)arguments.get(0).getValue());
	}


	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Instruction#execute(hr.fer.zemris.java.simplecomp.models.Computer)
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value = computer.getRegisters().getRegisterValue(indexRegister);
		
		computer.getRegisters().setRegisterValue(indexRegister,
				Integer.valueOf((Integer)value - 1));
		return false;
	}

}
