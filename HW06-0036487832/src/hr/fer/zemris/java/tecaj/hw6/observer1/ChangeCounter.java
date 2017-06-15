/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * Represents counter of changes over some class.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class ChangeCounter implements IntegerStorageObserver {
	/** Number of changes */
	private int numberOfAdding;
	
	/**
	 * Default constructor, sets number of changes to one.
	 */
	public ChangeCounter() {
		numberOfAdding = 1;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorageObserver#valueChanged(hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorage)
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Number of value changes since tracking:" + numberOfAdding++);
	}
	
	
}
