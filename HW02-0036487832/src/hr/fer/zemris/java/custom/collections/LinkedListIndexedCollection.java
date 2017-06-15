/**
 * 
 */
package hr.fer.zemris.java.custom.collections;


/**
 * linked list-backed collection of objects denoted as
 * LinkedListIndexedCollection which extends class Collection
 * 
 * @author Stjepan
 */
public class LinkedListIndexedCollection extends Collection{
	/**
	 * size of collection (number of elements in collection)
	 */
	private int size;
	
	/**
	 * first node in list
	 */
	private ListNode first;
	
	/**
	 * last node in list
	 */
	private ListNode last;
	
	/**
	 * Represents one node of list, and has reference to next and previous 
	 * node
	 * @author Stjepan
	 */
	private static class ListNode{
		/**
		 * previous node list
		 */
		private ListNode previousNode;
		/**
		 * next list node
		 */
		private ListNode nextNode;
		/**
		 * value of one node
		 */
		private Object value;
	}
	
	/**
	 * Creates an empty collection with first and last equals null.
	 */
	public LinkedListIndexedCollection() {
		this.size = 0;
	}
	
	/**
	 * Creates new collection and adds all elements from collection in argument.
	 * @param collection Objects to add into this list
	 */
	public LinkedListIndexedCollection(Collection collection) {
		this();
		this.addAll(collection);
	}
	
	/**
	 * Adds the given object into this collection at the end of collection;
	 * newly added element becomes the element at the biggest index. 
	 * Complexity is O(1).
	 * 
	 * @param value object to add into list.
	 * @throws IllegalArgumentException refuse to add null element
	 */
	@Override
	public void add(Object value) {
		if ( value == null) {
			throw new IllegalArgumentException("New object must not be null.");
		}
		
		if (size == 0) {
			this.first = new ListNode();
			this.first.value = value;
			this.last = first;
		} else if (size == 1) {
			this.last = new ListNode();
			this.last.value = value;
			this.last.previousNode = first;
			this.first.nextNode = last;
		} else {
			this.last.nextNode = new ListNode();
			this.last.nextNode.previousNode = this.last;
			this.last.nextNode.value = value;
			this.last = this.last.nextNode;
		}
		size ++;
	}
	
	/**
	 * Returns the object that is stored in linked list at position index. 
	 * Valid indexes are 0 to size-1. Complexity is never greater than n/2+1.
	 * 
	 * @param index defines index of wanted element
	 * @return element at index given in argument
	 * @throws IndexOutOfBoundsException if index is not valid
	 */
	public Object get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index must be greater " + 
					"than 0 and less than size.");
		}
		
		ListNode node;
		
		if (index > size / 2) {
			int i = size;
			node = this.last;
			while (--i != index) {
				node = node.previousNode;
			}
		} else {
			int i = 0;
			node = this.first;
			while (i++ != index) {
				node = node.nextNode;
			}
		}
		
		return node.value;
	}
	
	/**
	 * Removes all elements from the collection. Collection “forgets” about 
	 * current linked list.
	 */
	@Override
	public void clear() {
		this.first = null;
		this.last = null;
		this.size = 0;
	}
	
	/**
	 * Inserts (does not overwrite) the given value at the given position in 
	 * linked. Elements starting from this position are shifted one position. 
	 * The legal positions are 0 to size.
	 * 
	 * @param value object to insert into array
	 * @param position new position of an object from argument
	 * @throws ArrayIndexOutOfBoundsException if requested position is less 
	 * than zero or greater than size.
	 * @throws IllegalArgumentException if value is null
	 */
	public void insert(Object value, int position) {
		if ( position < 0 || position > size) { // invalid position
			throw new ArrayIndexOutOfBoundsException("New position must be " + 
					" greater than 0 and less or equal than size");
		} else if (value == null) { // invalid objects value
			throw new IllegalArgumentException("Objects value must not be null");
		} else if ( this.size == position) { // changes last node in list
			add(value);
//			this.last.nextNode = new ListNode();
//			this.last.nextNode.value = value;
//			this.last.nextNode.previousNode = this.last;
//			this.last = this.last.nextNode;
		} else if (position == 0) { // changes first node in list
			this.first.previousNode = new ListNode();
			this.first.previousNode.nextNode = this.first;
			this.first.previousNode.value = value;
			this.first = this.first.previousNode;
		} else { // generally insertion (average complexity n/4, worst n/2 + 1)
			ListNode node;
			if (position > size / 2) {
				node = last;
				int lastIndex = size ;
				while (--lastIndex != position) {
					node = node.previousNode;
				}
			} else {
				node = first;
				int lastIndex = 0 ;
				while (lastIndex++ != position) {
					node = node.nextNode;
				}
			}
			ListNode newNode = new ListNode();
			// not to lose previous node
			ListNode keepPrevious = node.previousNode;
			newNode.value = value;
			node.previousNode = newNode;
			newNode.nextNode = node;
			keepPrevious.nextNode = newNode;
			newNode.previousNode = keepPrevious;
		}
		size ++ ;
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
		ListNode node = this.first;
		int i = 0;
		while (i++ < size) {
			if (node.value.equals(value)) {
				return i - 1;
			}
			node = node.nextNode;
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
		ListNode removeNode;
		int findIndex;
		
		if ( index == 0) {
			removeNode = first;
			first = removeNode.nextNode;
			removeNode = null;
			size--;
			return;
		} else if (index == size - 1) {
			removeNode = last;
			last = removeNode.previousNode;
			removeNode = null;
			size--;
			return;
		} else if (index > size / 2) {
			removeNode = last;
			findIndex = size;
			while (--findIndex != index) {
				removeNode = removeNode.previousNode;
			}
		} else {
			removeNode = first;
			findIndex = 0;
			while (findIndex++ != index) {
				removeNode = removeNode.nextNode;
			}
		}
		
		
		removeNode.previousNode.nextNode = removeNode.nextNode;
		removeNode.nextNode.previousNode = removeNode.previousNode;
		removeNode = null;
		size--;
	}
	
	@Override
	public int size(){
		return this.size;
	}
	
	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
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
		ListNode node = first;
		while (node != null) {
			processor.process(node.value);
			node = node.nextNode;
		}
	}
	
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		ListNode object = this.first;
		int i = 0;
		
		while (object != null) {
			array[i++] = object.value;
			object = object.nextNode;
		}
		return array;
	}
}
