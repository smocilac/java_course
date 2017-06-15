package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;

/**
 * Implementation of {@linkplain ExecutionUnit} 
 * 
 * It executes all operations.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class ExecutionUnitImpl implements ExecutionUnit {

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.ExecutionUnit#go(hr.fer.zemris.java.simplecomp.models.Computer)
	 */
	@Override
	public boolean go(Computer computer) {
		computer.getRegisters().setProgramCounter(0);
		boolean read = true;
		while (read) {
			Instruction o = (Instruction) computer.getMemory()
							.getLocation(computer.getRegisters().getProgramCounter());
			computer.getRegisters().incrementProgramCounter();
			read = ! o.execute(computer);
		}
		return true;
	}

}
