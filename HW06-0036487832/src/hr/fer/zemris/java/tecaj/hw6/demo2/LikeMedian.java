package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Represents class for calculation of median
 * 
 *
 * @param <T> defines parameter for calculating median
 * 
 * @author Stjepan
 * @version 1.0
 */
public class LikeMedian<T extends Comparable<T>> implements Iterable<T>{
	/** set of data */ 
	List<T> data ;
	
	/**
	 * Default constructor.
	 * 
	 */
	public LikeMedian() {
		data = new ArrayList<>();
	}
	
	/**
	 * Adds object of parametar T to data.
	 * 
	 * @param object object to add into data
	 */
	public void add(T object) {
		if (object != null){
			data.add(object);
		}
	}
	
	/**
	 * Gets median.
	 * 
	 * @return empty optional if no data, else value of data
	 */
	public Optional<T> get() {
		if (data.isEmpty()){
			return Optional.empty();
		} 

		Collections.sort(data);
		
		return Optional.of(data.get((data.size()-1)/2));
	}

	@Override
	public void forEach(Consumer<? super T> action) {
		Iterable.super.forEach(action);
	}

	@Override
	public Iterator<T> iterator() {
		return data.iterator();
	}
	
}
