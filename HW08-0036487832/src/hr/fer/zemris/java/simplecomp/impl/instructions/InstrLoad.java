package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementation of {@linkplain Instruction}
 * Class which is used to load from memory into register.
 * 
 * <p> This command expects one directly addressed registers and after that 
 *     address of memory location to take value from.
 * 	   The result is value writing value from address to provided register.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class InstrLoad implements Instruction {
	/** Address of memory location which contains new value for register */
	private final int indexMemory ;
	/** Register index to write on */ 
	private final int indexRegister ;
	
	/**
	 * Constructor which takes one argument - list of register names to execute operation.
	 * Load expects register as first argument and memory address which contain value to write
	 * to specified register as second argument.
	 * 
	 * @param arguments 
	 * 					Arguments for load operation.
	 * @throws IllegalArgumentException 
	 * 					If number of arguments is not 2.
	 * 					If comes to type mismatch for any of arguments.
	 * 								
	 */
	public InstrLoad(List<InstructionArgument> arguments){
		if(arguments.size()!=2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		
		if(!arguments.get(0).isRegister() ||
						RegisterUtil.isIndirect((Integer)arguments.get(0).getValue())) {
				throw new IllegalArgumentException( "Type mismatch for argument 0!");
		}
		
		if(!arguments.get(1).isNumber() ){
			throw new IllegalArgumentException( "Type mismatch for argument 1!");
		}
		
		indexRegister = RegisterUtil.getRegisterIndex((Integer)arguments.get(0).getValue());
		indexMemory = (Integer) arguments.get(1).getValue();
	}
	

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Instruction#execute(hr.fer.zemris.java.simplecomp.models.Computer)
	 */
	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setRegisterValue(indexRegister,
					computer.getMemory().getLocation(indexMemory));
		
		return false;
	}

}
