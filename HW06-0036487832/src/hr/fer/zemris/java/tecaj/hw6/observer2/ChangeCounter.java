package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Represents number of changes on some storage instance.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class ChangeCounter implements IntegerStorageObserver {
	/** number of changes.*/
	private int numberOfAdding;
	/**
	 * sets number of changes to one.
	 */
	public ChangeCounter() {
		numberOfAdding = 1;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorageObserver#valueChanged(hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorage)
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.println("Number of value changes since tracking:" + numberOfAdding++);
	}
	
	
}
