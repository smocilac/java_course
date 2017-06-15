/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Writes square value of value every time it changes on system out.
 * 
 * @author Stjepan
 *
 */
public class SquareValue implements IntegerStorageObserver {


	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorageObserver#valueChanged(hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorage)
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.println("Provided new value " + istorage.getCurrentValue() + ". Square is "
				+ (int)Math.pow(istorage.getCurrentValue(), 2));

	}

}
