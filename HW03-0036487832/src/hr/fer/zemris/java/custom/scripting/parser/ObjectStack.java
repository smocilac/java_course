/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.nodes.ArrayIndexedCollection;

/**
 * Implementation of stack using ArrayIndexedCollection.
 * 
 * @author Stjepan
 */
public class ObjectStack {
	/**
	 * stack using ArrayIndexedCollection
	 */
	private ArrayIndexedCollection stack;
	
	/**
	 * Initializing ObjectStack with 16 default slots.
	 */
	public ObjectStack() {
		stack = new ArrayIndexedCollection();
	}

	/**
	 * Checks if stack has elements, if not returns true, else false.
	 * 
	 * @return true if stack has no elements
	 */
	public boolean isEmpty() {
		return stack.size() == 0 ? true : false;
	}
	
	/**
	 * Returns number of elements in stack.
	 * 
	 * @return number of elements in stack
	 */
	public int size() {
		return stack.size();
	}
	
	/**
	 * Puts element on the top of stack. Complexity is O(1) if reallocation is not
	 * needed.
	 * 
	 * @param value object to add on top of stack
	 * @throws IllegalArgumentException argument must not be null
	 */
	public void push(Object value) {
		if (value == null) {
			throw new IllegalArgumentException("Argument must not be null.");
		}
		stack.add(value);
	}
	
	/**
	 * Removes last value pushed on stack from stack and returns it.
	 * 
	 * @return removed object
	 * @throws EmptyStackException if there is no elements on stack
	 */
	public Object pop() throws EmptyStackException {
		int numOfElem = stack.size();
		if ( numOfElem == 0 ) {
			throw new EmptyStackException("Cannot pop element, stack is empty.");
		}
		Object obj = stack.get(stack.size() - 1);
		stack.remove(stack.size() - 1);
		return obj;
	}
	
	/**
	 * Similar as pop. Returns last element placed on stack but does not delete it 
	 * from stack.
	 * 
	 * @return last element on stack
	 * @throws EmptyStackException thrown when there is nothing to return( stack is
	 * empty)
	 */
	public Object peek() throws EmptyStackException {
		if ( stack.size() == 0 ) {
			throw new EmptyStackException("Cannot pop element, stack is empty.");
		}
		return stack.get(stack.size() - 1);
	}
	
	/**
	 * Removes all elements from stack.
	 */
	public void clear() {
		stack.clear();
	}
	
}
