package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementation of {@linkplain Instruction}
 * Class which is used for addition.
 * 
 * <p> This command expects three directly addressed registers. 
 * 	   The result is stored into first provided register.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class InstrAdd implements Instruction {
	/** Index of register one */
	private int indexRegister1;
	
	/** Index of register two */
	private int indexRegister2;
	
	/** Index of register three */
	private int indexRegister3;
	
	
	/**
	 * Constructor which takes one argument - list of register names to execute operation.
	 * 
	 * @param arguments 
	 * 					Arguments for add operation.
	 * @throws IllegalArgumentException 
	 * 					If number of arguments is not three.
	 * 					If comes to type mismatch for any of arguments.
	 * 								
	 */
	public InstrAdd(List<InstructionArgument> arguments) {
		if(arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		
		for(int i = 0; i < 3; i++) {
			if(!arguments.get(i).isRegister() ||
						RegisterUtil.isIndirect((Integer)arguments.get(i).getValue())) {
				throw new IllegalArgumentException(
						"Type mismatch for argument "+i+"!"
						);
			}
		}
		
		this.indexRegister1 = RegisterUtil.getRegisterIndex((Integer)arguments.get(0).getValue());
		this.indexRegister2 = RegisterUtil.getRegisterIndex((Integer)arguments.get(1).getValue());
		this.indexRegister3 = RegisterUtil.getRegisterIndex((Integer)arguments.get(2).getValue());
	}

	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Instruction#execute(hr.fer.zemris.java.simplecomp.models.Computer)
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(indexRegister2);
		Object value2 = computer.getRegisters().getRegisterValue(indexRegister3);
		computer.getRegisters().setRegisterValue(indexRegister1,
							Integer.valueOf((Integer)value1 + (Integer)value2));
		
		return false;
	}

}
