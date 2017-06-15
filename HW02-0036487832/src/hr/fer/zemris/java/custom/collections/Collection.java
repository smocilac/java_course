/**
 * 
 */
package hr.fer.zemris.java.custom.collections;


/**
 * Class Collection represents general collection of objects.  
 * It provides protected default constructor.
 * 
 * @author Stjepan
 */
public class Collection {
	
	/**
	 * Default protected constructor, takes no arguments.
	 */
	protected Collection() {
		
	}
	
	/**
	 * Returns true if collection contains no objects and false otherwise.
	 * 
	 * @return true if collection is not empty, else returns false
	 */
	public boolean isEmpty(){
		return size() == 0;
	}
	
	/**
	 * Returns the number of currently stored objects in this collections.
	 * 
	 * @return number of objects in collection
	 */
	public int size(){
		return 0;
	}
	
	/**
	 * Adds the given object into this collection.
	 * 
	 * @param value Object to add in collection
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * Returns true only if the collection contains given value, 
	 * as determined by equals method.
	 * 
	 * @param value
	 * @return true if collection contains given object, else false
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Returns true only if the collection contains given value as determined 
	 * by equals method and removes one occurrence of it,in this class it is 
	 * not specified which one.
	 * 
	 * @param value object to remove from collection
	 * @return true if collection contains given value
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Allocates new array with size equals to the size of this collections, 
	 * fills it with collection content and returns the array. This method 
	 * never returns null.
	 * 
	 * @return new array filled with elements in this collection.
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Method calls processor.process(.) for each element of this collection.
	 * The order in which elements will be sent is undefined in this class.
	 * 
	 * @param processor
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Method adds into itself all elements from given collection. 
	 * Collection in argument remains unchanged.
	 * 
	 * @param other elements to add in this collection
	 */
	public void addAll(Collection other) {
		class Proc extends Processor{
			@Override
			public void process(Object value){
				add(value);
			}
		}
		Proc processorArg = new Proc();
		other.forEach(processorArg);
	}
	
	/**
	 * Removes all elements from this collection.
	 */
	public void clear() {
		
	}
}
