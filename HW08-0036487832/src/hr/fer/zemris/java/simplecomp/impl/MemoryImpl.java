package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * Class which implements {@link Memory} and provides storage of objects which represents
 * memory for {@linkplain Computer}
 * 
 * @author Stjepan
 * @version 1.0
 *
 */
public class MemoryImpl implements Memory {
	/** Size of memory */
	private final int size;
	
	/** Objects which represents memory locations.*/
	Object objects[];
	
	/**
	 * Constructor of {@code MemoryImpl} which takes one argument - size of memory.
	 * 
	 * @param size number of memory locations
	 * @throws IllegalArgumentException if argument less than 1
	 */
	public MemoryImpl(int size) {
		if (size < 1 ) {
			throw new IllegalArgumentException("Size of memory must be at least one.");
		}
		
		this.size = size;
		objects = new Object[size];
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Memory#setLocation(int, java.lang.Object)
	 */
	@Override
	public void setLocation(int location, Object value) {
		if (location < 0 || location >= size){
			throw new IndexOutOfBoundsException("Location is out of array 0 - " + (size - 1));
		}
		
		objects[location] = value;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.simplecomp.models.Memory#getLocation(int)
	 */
	@Override
	public Object getLocation(int location) {
		if (location < 0 || location >= size){
			throw new IndexOutOfBoundsException("Location is out of array 0 - " + (size - 1));
		}
		
		return objects[location];
	}

}
