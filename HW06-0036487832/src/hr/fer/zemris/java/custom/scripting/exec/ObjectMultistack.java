package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class represents multistack. It means a mapped stack name-stack. All other functions
 * of every independent stack is similar or same to every other stack. Difference is 
 * complexety, pop, push and peek method has O(n) complexity.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class ObjectMultistack {
	/** mapped stack */
	private Map<String, MultistackEntry> data ; 
	
	/**
	 * Public constructor whith initial capacity 16.
	 * 
	 */
	public ObjectMultistack() {
		data = new HashMap<>();
	}
	
	/**
	 * Public constructor with capacity from argument. Must be greater than zero.
	 * 
	 * @param n capacity
	 * @throws IllegalArgumentException if argument is not greater than zero
	 */
	public ObjectMultistack(int n) {
		if (n < 1) {
			throw new IllegalArgumentException("Map size must be at least 1.");
		}
		data = new HashMap<>(n);
	}
	
	/**
	 * Class represents one entry of the stack. It stores {@link ValueWrapper} instance
	 * internally.
	 * 
	 * @author Stjepan
	 *
	 */
	public static class MultistackEntry{
		/** value of node */
		private ValueWrapper value;
		/** next node */
		private MultistackEntry next;
		
		/**
		 * Constructor which takes value and next element
		 * 
		 * @param value value to store
		 * @param next element
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Returns value
		 * 
		 * @return value
		 */
		public ValueWrapper getValue() {
			return value;
		}
		
		/**
		 * Sets value
		 * @param value
		 */
		public void setValue(ValueWrapper value) {
			this.value = value;
		}
		
		/**
		 * Gets next
		 * @return
		 */
		public MultistackEntry getNext() {
			return next;
		}
		
		/**
		 * Sets next
		 * @param next
		 */
		public void setNext(MultistackEntry next) {
			this.next = next;
		}
		
	}
	
	/**
	 * Pushes second argument to stack related with name in first argument. If name not
	 * exist creates slot for provided name and stores element on top of the stack.
	 * 
	 * @param name name of stack
	 * @param valueWrapper value to push
	 * @throws NullPointerException if first argument is null
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		if (name == null){
			throw new NullPointerException("Cannot map null name.");
		}
		MultistackEntry entry;
		if ((entry = data.get(name)) == null){
			data.put(name, new MultistackEntry(valueWrapper, null));
			return;
		}
		
		while (entry.next != null){
			entry = entry.next;
		}
		
		entry.setNext(new MultistackEntry(valueWrapper, null));
		
		
	}
	
	/**
	 * Method which removes element from top of the stack and returns its value.
	 * 
	 * @param name name of stack
	 * @return Element on the top of the stack
	 * @throws NullPointerException if argument is null
	 * @throws EmptyStackException if key matching argument or wanted stack is empty
	 */
	public ValueWrapper pop(String name) {
		if (name == null){
			throw new NullPointerException("Cannot map null name.");
		}
		
		MultistackEntry entry, previous;
		if ((entry = data.get(name)) == null){
			throw new EmptyStackException();
		} else if (entry.next == null){
			ValueWrapper value = entry.getValue();
			data.put(name, null);
			return value;
		}
		
		previous = entry;
		while (entry.next != null){
			previous = entry;
			entry = entry.next;
		}
		
		ValueWrapper value = entry.getValue();
		previous.setNext(null);
		
		return value;
	}
	
	/**
	 * Gets and returns element from top of the stack.
	 * 
	 * @param name name of stack
	 * @return value on the top of stack
	 * @throws NullPointerException if argument is null
	 * @throws EmptyStackException if key matching argument or wanted stack is empty
	 */
	public ValueWrapper peek(String name) {
		if (name == null){
			throw new NullPointerException("Cannot map null name.");
		}
		
		MultistackEntry entry;
		if ((entry = data.get(name)) == null){
			throw new EmptyStackException();
		}
		
		while (entry.next != null){
			entry = entry.next;
		}
		return entry.getValue();
	}
	
	/**
	 * Returns true if stack with provided name is empty. If no such name returns true also.
	 * Otherwise returns false.
	 * 
	 * @param name name of stack
	 * @return true if stack is empty or not exist, false otherwise
	 */
	public boolean isEmpty(String name) {
		if (name == null) return true;
		
		return data.get(name) == null;
	}

}
