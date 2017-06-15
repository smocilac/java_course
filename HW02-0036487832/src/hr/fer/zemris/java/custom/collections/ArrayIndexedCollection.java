/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

/**
 * Resizable array-backed collection of objects denoted as
 * ArrayIndexedCollection which extends class Collection.
 * 
 * @author Stjepan
 */
public class ArrayIndexedCollection extends Collection {
	/**
	 * Current size of array ( number of not null elements)
	 */
	private int size;
	
	/**
	 * Current capacity of an array.
	 */
	private int capacity;
	
	/**
	 * Array of elements 
	 */
	private Object[] elements;
	
	/**
	 * Creates instance of class with capacity from argument.
	 * 
	 * @param initialCapacity current max size of array
	 * @throws IllegalArgumentException if argument is less then 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Capacity must be greater" + 
						"then one.");
		}
		this.size = 0;
		this.capacity = initialCapacity;
		this.elements = new Object[capacity];
	}
	
	/** 
	 * Creates instance of class. Sets default capacity to 16.
	 */ 
	public ArrayIndexedCollection() {
		this(16);
	}
	
	/**
	 * Creates new instance of class filled with elements and capacity from
	 * arguments.
	 * 
	 * @param elements elements to store in array when initialize.
	 * @param initialCapacity default capacity of array, is not final.
	 * @throws IllegalArgumentException if capacity is less than one
	 */
	public ArrayIndexedCollection(Collection elements, int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Capacity must be greater" + 
						"then one.");
		}
		this.size = 0;
		this.capacity = initialCapacity;
		this.elements = new Object[capacity];
		this.addAll(elements);
	}
	
	/**
	 * Creates new instance of class filled with elements from collection
	 * in argument. Initialize default size of array ( 16 ).
	 * 
	 * @param elements elements to add in list when initialize
	 */
	public ArrayIndexedCollection(Collection elements) {
		this ( elements, 16);
	}
	
	/**
	 * Adds the given object into this collection (reference is added into 
	 * first empty place in the elements array). If the elements array is full, 
	 * it will be reallocated by doubling its size). Complexity of adding new 
	 * element is O(n).
	 * 
	 * @param value object to add into array
	 * @throws IllegalArgumentException if argument is null 
	 */
	@Override
	public void add(Object value) {
		if ( value == null) {
			throw new IllegalArgumentException("New object must not be null.");
		}
		
		if ( this.size >= this.capacity) {
			reallocate();
		}
		
		elements[size] = value;
		size++;	
	}
	
	/**
	 * Reallocates array, new capacity is two times current capacity.
	 */
	private void reallocate() {
		Object[] newArray = new Object[capacity*2];
		
		System.arraycopy(elements, 0, newArray, 0, size);
		
		this.capacity *= 2;
		elements = newArray;
	}
	
	/**
	 * Returns the object that is stored in backing array at position index. 
	 * Valid indexes are 0 to size-1. Complexity of this 
	 * method is O(1).
	 * 
	 * @param index defines index of wanted element
	 * @return element at position from argument
	 * @throws ArrayIndexOutOfBoundsException if index not valid
	 */
	public Object get(int index) {
		if (index < 0 || index > size - 1) {
			throw new ArrayIndexOutOfBoundsException("Index must be between 0 and size - 1.");
		}
		return elements[index];
	}
	
	/**
	 * Removes all elements from the collection. The allocated array is left at 
	 * current capacity.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			this.elements[i] = null;
		}
		size = 0;
	}
	
	/**
	 * Inserts (does not overwrite) the given value at the given position in 
	 * array ( before actual insertion elements at position and at greater 
	 * positions will be shifted one place toward the end). The legal positions 
	 * are 0 to size. Complexity of operation is O(n).
	 * 
	 * @param value object to insert into array
	 * @param position new position of an object from argument
	 * @throws ArrayIndexOutOfBoundsException if requested position is less 
	 * than zero or greater than size.
	 * @throws IllegalArgumentException if object is null.
	 */
	public void insert(Object value, int position) {
		if ( position < 0 || position > size) {
			throw new ArrayIndexOutOfBoundsException("New position must be " + 
					" greater than 0 and less or equal than size");
		} else if ( value == null) {
			throw new IllegalArgumentException("Object must not be null.");
		} else if ( this.size == this.capacity) {
			this.capacity *= 2;
			Object[] newArray = new Object[capacity];
			int i = 0;
			
			for (int oldCapacity = this.capacity/2; i < oldCapacity; i++) {
				if ( i >= position) {
					newArray[i+1] = this.elements[i];
				} else {
					newArray[i] = this.elements[i];
				}
			}
			
			newArray[position] = value;
			elements = newArray;
			size++;			
		} else {
			for (int  i = size - 1; i > position; i-- ) {
				elements[i] = elements[i - 1];
			}
			
			elements[position] = value;
			size++;			
		}
	}
	
	/**
	 * Searches the collection and returns the index of the first occurrence 
	 * of the given value or -1 if the value is not found. Complexity is 
	 * O(n).
	 * 
	 * @param value object to find
	 * @return index of the first occurrence if object is found, else -1
	 */
	public int indexOf(Object value) {
		for ( int i = 0; i < size; i++) {
			if (this.elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Removes element at specified index from collection. Element that was 
	 * previously at location index+1 after this operation is on location 
	 * index, etc. Legal indexes are 0 to size-1.
	 * 
	 * @param index position of element to remove
	 * @throws ArrayIndexOutOfBoundsException In case of invalid index
	 */
	public void remove(int index) {
		if ( index < 0 || index >= size) {
			throw new ArrayIndexOutOfBoundsException();
		}
		this.elements[index] = null;
		for (int i = index + 1; i < size ; i++) {
			this.elements[i-1] = this.elements[i];
		}
		size--;
	}
	
	@Override
	public int size(){
		return this.size;
	}
	
	@Override
	public boolean contains(Object value) {
		for ( int i = 0; i < this.size; i++) {
			if (elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean remove(Object value) {
		if (value == null) {
			return false;
		}
		int index;
		if ((index = this.indexOf(value)) == -1) {
			return false;
		}
		this.remove(index);
		return true;	
	}
	
	@Override
	public void forEach(Processor processor) {
		for (int i = 0 ; i < size ; i++) {
			processor.process(this.elements[i]);
		}
	}
	
	@Override
	public Object[] toArray() {
		Object[] obj = new Object[size];
		System.arraycopy(elements, 0, obj, 0, size);
		return obj;
	}

	
}
