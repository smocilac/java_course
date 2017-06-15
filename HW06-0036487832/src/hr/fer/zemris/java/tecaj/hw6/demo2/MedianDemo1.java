package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.Optional;

public class MedianDemo1 {
	
	/*
	 * Test from HW06.pdf
	 */
	public static void main(String[] args) {
		LikeMedian<Integer> likeMedian = new LikeMedian<Integer>();
		
		likeMedian.add(new Integer(10));
		likeMedian.add(new Integer(5));
		likeMedian.add(new Integer(3));
//		likeMedian.add(new Integer(1));
//		likeMedian.add(new Integer(2));
//		likeMedian.add(new Integer(6));
		
		Optional<Integer> result = likeMedian.get();
		
		System.out.println(result.get());
		for(Integer elem : likeMedian) {
			System.out.println(elem);
		}
		
//		likeMedian.forEach(System.out::println);

	}

}
