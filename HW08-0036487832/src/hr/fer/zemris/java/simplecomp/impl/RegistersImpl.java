package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementation of {@linkplain Registers} .
 * 
 * It provides storage of array which represents memory for {@linkplain Computer}
 * 
 * 
 * @author Stjepan
 * @version 1.0
 */
public class RegistersImpl implements Registers {
	/** Number of registers */
	private final int regsLen;
	
	/** Register flag */
	private boolean flag = false;
	
	/** Program counter */
	private int programCounter = 0;
	
	/** Registers as objects. */
	private Object [] registers ;
	
	/**
	 * Constructor which takes one argument - length of registers.
	 * 
	 * @param regsLen length of registers.
	 * @throws
	 * 			IllegalArgumentException if provided argument is less than stack pointer 
	 * 										register index
	 */
	public RegistersImpl(int regsLen) {
		if (regsLen <= Registers.STACK_REGISTER_INDEX){
			throw new IllegalArgumentException("Number of registers should be at least "
					+ (Registers.STACK_REGISTER_INDEX + 1)  + " because of stack pointer. ");
		}
		
		this.regsLen = regsLen;
		registers = new Object[regsLen];
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Registers#getRegisterValue(int)
	 */
	@Override
	public Object getRegisterValue(int index) {
		if ( index >= regsLen || index < 0){
			throw new IndexOutOfBoundsException("Index of registers must be from 0 to " 
								+ regsLen);
		}
		
		return registers[index];
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Registers#setRegisterValue(int, java.lang.Object)
	 */
	@Override
	public void setRegisterValue(int index, Object value) {
		if ( index >= regsLen || index < 0){
			throw new IndexOutOfBoundsException("Index of registers must be from 0 to " 
								+ regsLen);
		}
		
		registers[index] = value;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Registers#getProgramCounter()
	 */
	@Override
	public int getProgramCounter() {
		return programCounter;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Registers#setProgramCounter(int)
	 */
	@Override
	public void setProgramCounter(int value) {
		programCounter = value;

	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Registers#incrementProgramCounter()
	 */
	@Override
	public void incrementProgramCounter() {
		programCounter++;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Registers#getFlag()
	 */
	@Override
	public boolean getFlag() {
		return flag;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Registers#setFlag(boolean)
	 */
	@Override
	public void setFlag(boolean value) {
		flag = value;

	}

}
