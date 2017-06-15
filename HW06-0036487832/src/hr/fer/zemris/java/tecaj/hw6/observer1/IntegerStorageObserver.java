package hr.fer.zemris.java.tecaj.hw6.observer1;
/**
 * Interface which provides one method - valueChanged. It should do something if value is 
 * changed.
 * 
 * @author Stjepan
 *
 */
public interface IntegerStorageObserver {
	
	/**
	 * Preforms operation when value of storage is changed
	 * 
	 * @param istorage storage to preform operation on
	 */
	public void valueChanged(IntegerStorage istorage);
	
}
