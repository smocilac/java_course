package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;


/**
 * Implementation of {@linkplain Instruction}
 * Class which is used to move values.
 * Indirect addressing is allowed here.
 * Rules are described at {@link InstructionArgument}
 * 
 * <p> This command expects one directly or indirectly addressed registers and after that 
 *     address of memory location to take value from, or register (also directly or 
 *     indirectly addressed)
 * 
 * @author Stjepan
 * @version 1.0
 */
public class InstrMove implements Instruction {
	/** 
	 * First instruction argument
	 */
	private InstructionArgument first;
	
	/** 
	 * Second instruction argument
	 */
	private InstructionArgument second;
	
	/**
	 * Constructor which takes one argument - list of register names to execute operation.
	 * Indirect addressing is allowed.
	 * 
	 * @param arguments 
	 * 					Arguments for move operation.
	 * @throws IllegalArgumentException 
	 * 					If number of arguments is not two
	 * 					If comes to type mismatch for any of arguments
	 * 								
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		if(arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		
		if (!(first = arguments.get(0)).isRegister()){
			throw new IllegalArgumentException("First argument not defined proparly. ");
		}
		
		if(!(second = arguments.get(1)).isRegister() && !second.isNumber()) {
			throw new IllegalArgumentException("Type mismatch for second argument!");
		}
		
	}
	
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Instruction#execute(hr.fer.zemris.java.simplecomp.models.Computer)
	 */
	@Override
	public boolean execute(Computer computer) {
		int firstReg = RegisterUtil.getRegisterIndex((Integer)first.getValue());
		int firstMem = -1;
		
		// if first instruction argument is indirect
		if (RegisterUtil.isIndirect((Integer)first.getValue())){
			firstMem = (Integer) computer.getRegisters().getRegisterValue(firstReg) + 
					 RegisterUtil.getRegisterOffset((Integer)first.getValue());
		} 
		
		Object value = null;
		// if second is register
		if ( second.isRegister()){
			if (RegisterUtil.isIndirect((Integer)second.getValue())){
				int secMem = (Integer)computer.getRegisters().getRegisterValue(
						RegisterUtil.getRegisterIndex((Integer)second.getValue()));
				secMem += RegisterUtil.getRegisterOffset((Integer)second.getValue());
				value = computer.getMemory().getLocation(secMem);
			}else {
				value = computer.getRegisters().getRegisterValue(
									RegisterUtil.getRegisterIndex((Integer)second.getValue()));
			}
		} else if (second.isNumber()){
			value = second.getValue();
		} else {
			value = computer.getMemory().getLocation((Integer)second.getValue());
		}
		
		if (firstMem == -1 ) {
			computer.getRegisters().setRegisterValue(firstReg, value);
			return false;
		}
		
		computer.getMemory().setLocation(firstMem, value);
		return false;
	}

}
