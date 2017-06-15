package hr.fer.zemris.java.tecaj.hw6.demo3;

public class PrimesDemo {

	
	/*
	 * Example from HW06.pdf
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2); // 5: how many of them
		
//		for(Integer prime : primesCollection) {
//			System.out.println("Got prime: " + prime);
//		}
		
		for(Integer prime : primesCollection) {
			for(Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: "+prime+", "+prime2);
			}
		}

	}

}
