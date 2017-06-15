/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * 
 * Writes double value when it changed. It writes message for n times, where n is defined
 * in constructor.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class DoubleValue implements IntegerStorageObserver {
	/** counter of changes */
	private int counter;
	
	/**
	 *  Takes one argument - number of repetitions.
	 *  
	 *  @throws IllegalArgumentException if argument is less than 1
	 */
	public DoubleValue(int counter) {
		if (counter < 1){
			throw new IllegalArgumentException();
		}
		this.counter = counter;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorageObserver#valueChanged(hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorage)
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		if ( counter == 0) {
			return;
		}
		
		System.out.println("Double value: " + istorage.getCurrentValue()*2);
		counter--;
	}

}
