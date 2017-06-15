/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * Represents class which writes double value of observed value on system out
 * 
 * @author Stjepan
 * @version 1.0
 */
public class DoubleValue implements IntegerStorageObserver {
	/** Counter of changes. */
	private int counter;
	
	/**
	 *  Default constructor, sets counter to arguments value
	 */
	public DoubleValue(int counter) {
		if (counter < 1){
			throw new IllegalArgumentException("Double value must be asked at least one time.");
		}
		this.counter = counter;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorageObserver#valueChanged(hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorage)
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		if ( counter == 0) {
			istorage.getObservers().remove(this);
			return;
		}
		
		System.out.println("Double value: " + istorage.getValue()*2);
		counter--;
	}

}
