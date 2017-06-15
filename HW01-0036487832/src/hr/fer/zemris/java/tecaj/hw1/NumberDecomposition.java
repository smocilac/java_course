/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw1;

/**
 * @author Stjepan
 * Class that provide number decomposition on prime factors.
 */
public class NumberDecomposition {

	/**
	 * Decomposition on prime factors and writing on console.
	 * @param args one argument need, whic number to decomposite
	 */
	public static void main(String[] args) {
		if ( args.length != 1){
			System.err.println("One argument expected.");
			return;
		}
		int whichNumber = 0;
		if( (whichNumber = Integer.parseInt(args[0])) < 2){
			System.err.println("number must not be less than zero (or zero).");
			return;
		}
		
		decompositeNumber(whichNumber);

	}
	
	/**
	 * Method that does decomposition on prime factors.
	 * @param whichNumber number for decomposition
	 */
	private static void decompositeNumber(int whichNumber) {
		System.out.println("You requested decomposition of number " +
					whichNumber + " onto prime factors. Here they are:");
		int a = 1;
		int saveMax = whichNumber;
		for (int i = 2; i * i <= saveMax; i++){
			while (whichNumber % i == 0){
				whichNumber /= i;
				System.out.println((a++) + ". " + i); 
			}
		}
	}

}
