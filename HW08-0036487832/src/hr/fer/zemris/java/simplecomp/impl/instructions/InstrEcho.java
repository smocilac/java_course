package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementation of {@linkplain Instruction}
 * Class which is used for print to {@linkplain System#out}
 * 
 * <p> This command expects one directly or indirectly addressed registers. 
 * 
 * @author Stjepan
 * @version 1.0
 */
public class InstrEcho implements Instruction {
	/** index of register  */
	private InstructionArgument argument ;
	/** flag which contains information about argument */
	private boolean isIndirect = false;
	
	/**
	 * Constructor which takes one argument - list of register names to execute operation.
	 * 
	 * @param arguments 
	 * 					Arguments for echo operation.
	 * @throws IllegalArgumentException 
	 * 					If number of arguments is not one
	 * 					If comes to type mismatch for argument (must be register )
	 * 								
	 */
	public InstrEcho(List<InstructionArgument> arguments){
		if(arguments.size()!=1) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}
		
		if(!(argument = arguments.get(0)).isRegister()) {
			throw new IllegalArgumentException( "Type mismatch for argument!" );
		}
		
		if(RegisterUtil.isIndirect((Integer)argument.getValue())){
			isIndirect = true;
		} 
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Instruction#execute(hr.fer.zemris.java.simplecomp.models.Computer)
	 */
	@Override
	public boolean execute(Computer computer) {
		Integer value = null;
		
		if (isIndirect){ 
			value = (Integer) computer.getRegisters().getRegisterValue(RegisterUtil.
								getRegisterIndex((Integer)argument.getValue()));
			value += (Integer)RegisterUtil.getRegisterOffset((Integer)argument.getValue());
			
			System.out.print(
							computer.getMemory().getLocation(value).toString());
		} else {
			value = RegisterUtil.getRegisterIndex((Integer)argument.getValue());
			Object write = computer.getRegisters().getRegisterValue(value);
			System.out.print(write.toString());
		}
		
		
		return false;
	}

}
