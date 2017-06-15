/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw6.observer1;

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
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Provided new value " + istorage.getValue() + ". Square is "
				+ (int)Math.pow(istorage.getValue(), 2));

	}

}
