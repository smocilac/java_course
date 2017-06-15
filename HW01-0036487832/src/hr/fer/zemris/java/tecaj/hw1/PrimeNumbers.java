/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw1;

/**
 * @author Stjepan
 * Class that provides console output of prime numbers.
 */
public class PrimeNumbers {
	private static long lastPrime = 1;
	
	/**
	 * writes n prime numbers, where n is argument
	 * @param args needed argument, number of prime numbers to write on console
	 */
	public static void main(String[] args) {
		if ( args.length != 1){
			System.err.println("One argument expected.");
			return;
		}
		int howMany = 0;
		if( (howMany = Integer.parseInt(args[0])) < 0){
			System.err.println("n must not be less than zero (or zero).");
			return;
		}
		System.out.println("First " + args[0] + " prime numbers are: ");
		//long primes;
		int a = 1;
		while (a <= howMany)
			System.out.println((a++) + ". " + nextPrime());
	}
	
	/**
	 * Returns next prime.
	 * @return next prime
	 */
	private static long nextPrime(){
		while(!isPrime(++lastPrime));
		return lastPrime;
	}
	
	/**
	 * Checking if number given in argument is prime, true if is prime
	 * @param n number to check
	 * @return true if prime number, else false
	 */
	private static boolean isPrime(long n){
		if(n > 2 && ((n % 2) == 0))
		       return false;
		// slozenost O(sqrt(n))
		for (int k = 3; k * k <= n; k++)
			if (n % k == 0)
				return false;
		
		return true;
	}
	
	
	

}
