package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementation of {@link Computer}
 * 
 * It provides two internally stored objects of type {@linkplain Registers} and 
 * {@linkplain Memory} which represents computer registers and memory. 
 * 
 * @author Stjepan
 * @version 1.0
 */
public class ComputerImpl implements Computer {
	/** Registers if computer. */
	private final RegistersImpl registers;
	
	/** Computers memory. */
	private final MemoryImpl memory;
	
	/**
	 * Constructor which takes two arguments: size of memory and number of registers.
	 * 
	 * @param memSize 
	 * 					size of memory
	 * @param regLen 
	 * 					number of registers
	 * @throws IllegalArgumentException 
	 * 					if {@link RegistersImpl#RegistersImpl(int)} or 
	 * 						{@link MemoryImpl#MemoryImpl(int)} not provide wanted size.
	 */
	public ComputerImpl(int memSize, int regLen) {
		registers = new RegistersImpl(regLen);
		memory = new MemoryImpl(memSize);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Computer#getRegisters()
	 */
	@Override
	public Registers getRegisters() {
		return registers;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Computer#getMemory()
	 */
	@Override
	public Memory getMemory() {
		return memory;
	}

}
