package hr.fer.zemris.java.tecaj.hw6.demo3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents collection of prime numbers.
 * 
 * @author Stjepan 
 * @version 1.0
 */
public class PrimesCollection implements Iterable<Integer>{
	/** number of primes */
	private final int number;
	
	/**
	 * Only constructor. Takes number of primes that needs to be provided.
	 * 
	 * @param number number of prime numbers
	 * @throws IllegalArgumentException if number is less than 1
	 */
	public PrimesCollection(int number) {
		if (number < 1) {
			throw new IllegalArgumentException("Number of prime numbers must be positive.");
		}
		
		this.number = number;
	}


	@Override
	public Iterator<Integer> iterator() {
		return new PrimesIterator();
	}
	
	/**
	 * Iterator over prime numbers.
	 * 
	 */
	private class PrimesIterator implements Iterator<Integer>{
		/** next prime number*/
		private int next;
		/** counter of prime numbers. */
		private int counter = 0;
		
		/**
		 * Default constructor defines first value of next prime - 2.
		 */
		public PrimesIterator() {
			next = 2;
		}

		@Override
		public boolean hasNext() {
			return counter != number;
		}

		@Override
		public Integer next() {
			if (!hasNext()){
				throw new NoSuchElementException();
			}
			int ret = next;
			
			while (!isPrime(++next));
			counter++;
			
			return ret;
		}
		
		/**
		 * Checks if argument is prime number in complexity of {@code 0(sqrt(n))}
		 * 
		 * @param find number to find if prime or not
		 * @return true if number is prime, false otherwise.
		 */
		private boolean isPrime(int find){
			if (find % 2 == 0) return false;
			
			for (int i = 2, sqrt = (int)Math.sqrt(find); i <= sqrt; i++ ){
				if (find % i == 0){
					return false;
				}
			}
			
			return true;
			
		}
		
	}
	
	

}
