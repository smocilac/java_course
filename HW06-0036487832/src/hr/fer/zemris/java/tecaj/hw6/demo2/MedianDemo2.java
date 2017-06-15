package hr.fer.zemris.java.tecaj.hw6.demo2;

public class MedianDemo2 {

	/*
	 * Test from HW06.pdf
	 */
	public static void main(String[] args) {
		LikeMedian<String> likeMedian = new LikeMedian<String>();
		
		likeMedian.add("Joe");
		likeMedian.add("Jane");
		likeMedian.add("Adam");
		likeMedian.add("Zed");
		
		String result = likeMedian.get().get();
		System.out.println(result); // Writes: Jane
		
		for (String s : likeMedian) System.out.println(s);

	}
}
