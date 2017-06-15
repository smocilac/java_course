/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw1;

/**
 * @author Stjepan
 * Class that gives first n numbers of Hofstadter Q order written on console
 */
public class HofstadterQ {

	/**
	 * Takes one argument, number of members of H. Q order to be wirtten
	 * on System.out
	 * @param args defines how many numbers of H. Q order is going to be written
	 */
	public static void main(String[] args) {
		if ( args.length != 1){
			System.err.println("One argument expected.");
			return;
		}
		
		if( Integer.parseInt(args[0]) < 1){
			System.err.println("Given number must be positive.");
			return;
		}
		
		System.out.print("You requested calculation of " + args[0]);
		System.out.print(". Number of Hofstadter's Q-sequence. The requested number is ");
		System.out.print(calculateHofstadterQ(Long.parseLong(args[0])) + "\n");

	}
	
	/**
	 * Calculates value of n-th member in H. Q order
	 * @param n n-the member
	 * @return value of n-th member in H. Q order
	 */
	private static long calculateHofstadterQ(long n){
		if (n < 3)
			return 1;
		return calculateHofstadterQ(n - calculateHofstadterQ(n-1)) +
			   calculateHofstadterQ(n - calculateHofstadterQ(n-2));
	}
	
	

}
