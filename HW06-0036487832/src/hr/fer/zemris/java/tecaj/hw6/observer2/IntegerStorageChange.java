package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Interface which provides three method that every storage for integer should implement
 * in order to get values.
 * 
 * @author Stjepan
 *
 */
public interface IntegerStorageChange {
	/** gets storage */
	IntegerStorage getIntegerStorage();
	
	/** gets current value */
	Integer getCurrentValue();
	
	/** gets last stored value.*/
	Integer getLastStored();
}
