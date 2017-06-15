package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementation of {@linkplain Instruction}
 * Class which is used to compare values of two registers.
 * 
 * <p> This command expects two directly addressed registers. 
 * 	   The result is {@linkplain Registers#setFlag(boolean)} to {@code true} if values
 * 	   are equal, {@code false} otherwise.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class InstrTestEquals implements Instruction {
	/** Index of register one */
	private int indexRegister1;
	
	/** Index of register two */
	private int indexRegister2;
	
	public InstrTestEquals(List<InstructionArgument> arguments) {
		if(arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		
		for(int i = 0; i < 2; i++) {
			if(!arguments.get(i).isRegister() ||
						RegisterUtil.isIndirect((Integer)arguments.get(i).getValue())) {
				throw new IllegalArgumentException(
						"Type mismatch for argument "+i+"!"
						);
			}
		}
		
		this.indexRegister1 = RegisterUtil.getRegisterIndex((Integer)arguments.get(0).getValue());
		this.indexRegister2 = RegisterUtil.getRegisterIndex((Integer)arguments.get(1).getValue());
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Instruction#execute(hr.fer.zemris.java.simplecomp.models.Computer)
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(indexRegister1);
		Object value2 = computer.getRegisters().getRegisterValue(indexRegister2);
		
		if ( value1 == null || value2 == null){
			computer.getRegisters().setFlag(value1 == value2);
		} else {
			computer.getRegisters().setFlag(value1.equals(value2));
		}
		
		return false;
	}

}
