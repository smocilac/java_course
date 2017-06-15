/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  This implementation provides map operations and permits null values, but not the null 
 *  key.
 *  This class makes no guarantees as to the order of the map; in particular, it does 
 *  not guarantee that the order will remain constant over time. 
 *  
 *  An instance of HashMap has two parameters that affect its performance: initial 
 *  capacity and load factor. The capacity is the number of buckets in the hash table, 
 *  and the initial capacity is simply the capacity at the time the hash table is created. 
 *  
 *  The load factor is a measure of how full the hash table is allowed to get before its 
 *  capacity is automatically increased. When the number of entries in the hash table 
 *  exceeds the product of the load factor and the current capacity, the hash table is 
 *  rehashed (that is, internal data structures are rebuilt) so that the hash table has 
 *  approximately twice the number of buckets. 
 *  
 *  As a general rule, the default load factor is 0.75f 
 *  
 *  If the map is structurally modified at any time after the iterator is created, in 
 *  any way except through the iterator's own remove method, the iterator will throw a 
 *  {@link ConcurrentModificationException}.
 * 
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * 
 * @author Stjepan
 * @version 1.0
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>>{
	/** The default initial capacity which must be a power of 2  */
	private static final int DEFAULT_CAPACITY = 16;
	
	/** The table which can be resized when necessary. Length must be a power of 2 */
	private TableEntry<K,V>[] table;
		
	/** The number of entries contained in this map. */
	private int size;
	
	/** The default load factor for the hash table.*/
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;
	
	/** The number of times this HashMap has been structurally modified Structural 
	 * modifications are those that change the number of mappings in the HashMap or 
	 * otherwise modify its internal structure
	 */
	private int modificationCount;
	
	/**
	 * Constructs an empty HashMap with the default initial capacity (16) and the default 
	 * load factor (0.75). 
	 * 
	 */
	public SimpleHashtable(){
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Constructs an empty HashMap with the specified initial capacity and the default load 
	 * factor (0.75). 
	 * 
	 * @param capacity the initial capacity
	 * @throws IllegalArgumentException if argument is less than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity){
		if (capacity < 1) {
			throw new IllegalArgumentException("Empty table is not allowed.");
		}
		
		int c = 1;
		// capacity must be power of 2
		while ( c < capacity) {
			c <<= 1; // ROTL
		}
		
		table = new TableEntry[c];
		size = 0;
	}
	
	/**
	 * Represents one entry of the map.
	 *
	 * @param <K> the type of keys maintained by this map
	 * @param <V> the type of mapped values
	 * 
	 * @author Stjepan
	 */
	public static class TableEntry<K, V> {
		/** key of entry */
		private K key;
		
		/** value of entry */
		private V value;
	    
	    /** next entry */
		private TableEntry<K,V> next;
	     
	    /**
	     * Constructs new entry.
	     * 
	     * @param key {@code key}
	     * @param value {@code value}
	     * @param next {@code next}
	     * 
	     * @throws IllegalArgumentException if key is null
	     */
	    public TableEntry(K key, V value, TableEntry<K,V> next){
	    	if (key == null) {
	    		throw new IllegalArgumentException();
	    	}
	        this.key = key;
	        this.value = value;
	        this.next = next;
	    }
	    
	    /**
	     * Gets end returns {@code value}
	     * 
	     * @return {@code value}
	     */
		public V getValue() {
			return value;
		}
		
		/**
		 * Sets {@code value}
		 * 
		 * @param value {@code value}
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * Gets and returns {@code key}
		 * 
		 * @return {@code key}
		 */
		public K getKey() {
			return key;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			
			builder.append(key.toString() + " = ");
			if (value != null) {
				builder.append(value.toString());
				return builder.toString();
			}
			
			return builder.toString();
			
		}
	}
	
    
    /**
     * Associates the specified value with the specified key in this map. If the map 
     * previously contained a mapping for the key, the old value is replaced.
     * 
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @throws IllegalArgumentException if trying to put entry with null key 
     */
    public void put(K key, V value) {
    	if ( key == null) {
    		throw new IllegalArgumentException("Cannot put null key into table !");
    	}
    	
    	int hash = key.hashCode();
    	int position = Math.abs(hash) & (table.length-1); // must be less than capacity
    	TableEntry<K,V> entry = table[position];
    	TableEntry<K,V> previous = entry;
    	
    	while (entry != null) {
    		Object obj;
    		
    		previous = entry;
    		if (entry.getKey().hashCode() == hash && 
    				((obj = entry.getKey()) == key || key.equals(obj))) {
    			entry.value = value;
    			
    			return;
    		}
    		
    		entry = entry.next;
    	}
    	
    	modificationCount++;
    	addEntryToTable(key, value, position, previous);
    }
    
    /**
     * Adds a new entry with the specified key, value and hash code to the specified 
     * position. It is the responsibility of this method to resize the table if 
     * appropriate.  
     * 
     * @param key specified key
     * @param value specified value
     * @param position position of specified
     * @param previous last entry on the spot at {@code position}
     */
    private void addEntryToTable(K key, V value, int position, TableEntry<K,V> previous) {
    	if (previous == null) {
    		table[position] = new TableEntry<K,V>(key, value, null);
    	} else {
    		previous.next = new TableEntry<K,V>(key, value, null);
    	}
    	// check if table resize needed
    	if (size++ >= (int) (((float)table.length) * DEFAULT_LOAD_FACTOR)) {
    		resizeTable();
    	}    	
	}

    /**
     * Rehashes the contents of this map into a new array with a larger capacity. 
     * 
     * This method is called automatically when the number of keys in this map reaches 
     * the {@link #DEFAULT_LOAD_FACTOR} relative percent of capacity value.
     * 
     */
	private void resizeTable() {
		TableEntry<K, V>[] currentTable = table;
		int oldCapacity = currentTable.length;
		int newCapacity = oldCapacity * 2;
		
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] newTable = new TableEntry[newCapacity];
		
		refillTable(currentTable,newTable);
		
		table = newTable;
	}
	
	/**
	 * Fills newTable with all entries from current table .  
	 * 
	 * @param currentTable current table 
	 * @param newTable new table
	 */
	private void refillTable(TableEntry<K, V>[] currentTable,TableEntry<K, V>[] newTable) {
		int newCapacity = newTable.length;
		
		for (int j = 0; j < currentTable.length; j++) {
			TableEntry<K,V> e = currentTable[j];
			
			if (e != null) {
				currentTable[j] = null;
				
				do {
					int i = Math.abs(e.getKey().hashCode()) & (newCapacity - 1);
					TableEntry<K,V> next = e.next;
					
					e.next = newTable[i];
					newTable[i] = e;
					e = next;
					
				} while (e != null);
			}
		}
	}

	/**
     * Returns the value to which the specified key is mapped, or null if this map 
     * contains no mapping for the key.
     * A return value of null does not necessarily indicate that the map contains no 
     * mapping for the key, it's also possible that the key in argument is null, in that 
     * case this method also returns null. 
     * 
     * The containsKey operation may be used to distinguish these two cases. 
     * 
     * @param key to find value which is mapped to
     * @return null if key does not exist in map or if is null
     */
    public V get(Object key) {
    	if (key == null) {
    		return null;
    	}
    	TableEntry<K, V> entry = getTableEntry(key);
    	
    	return entry == null ? null : entry.getValue();
    }
    
    /**
     * Returns the entry associated with the specified key in the HashMap. Returns null 
     * if the HashMap contains no mapping for the key. 
     * 
     * @param key key to find
     * @return null if not found, else {@code TableEntry} instance with specified key
     */
    private TableEntry<K, V> getTableEntry(Object key) {
    	if (key == null) {
    		return null;
    	}
    	int hash = key.hashCode();
    	int index = Math.abs(hash) & (table.length - 1);
    	
    	TableEntry<K, V> entry = table[index];
    	
    	while (entry != null) {
    		Object k ;
    		if (entry.getKey().hashCode() == hash && 
    				((k = entry.getKey()) == key || key.equals(k))) {
    			return entry;
    		}
    		entry = entry.next;
    	}
  
    	return null;
    }
    
    /**
     * Returns {@code true} if this map contains no key-value mappings, false otherwise.
     * 
     * @return 
     * 		   {@code true} if this map contains no key-value mappings, false otherwise.
     */
    public int size() {
    	return size;
    }
    
    /**
     * Returns {@code true} if this map contains a mapping for the specified key. 
     * 
     * @param key The key whose presence in this map is to be tested
     * @return {@code true} if this map contains a mapping for the specified key.
     */
    public boolean containsKey(Object key) {
    	return getTableEntry(key) != null;
    }
    
    /**
     * Returns {@code true} if this map maps one or more keys to the specified value. 
     * 
     * @param value whose presence in this map is to be tested
     * @return 
     * 			{@code true} if this map maps one or more keys to the specified value
     */
    public boolean containsValue(Object value) {
    	if (value == null) {
    		return containsNullVal();
    	}
    	TableEntry<K,V>[] tabRef = table;
    	
    	for (int i = 0; i < tabRef.length ; i++) {
    		for (TableEntry<K,V> entry = tabRef[i] ; entry != null ; entry = entry.next){
    			if (value.equals(entry.value)){
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * Special-case code for containsValue with null argument 
     * 
     * @return {@code true} if this map maps one or more keys to the value of null
     */
    private boolean containsNullVal() {
		TableEntry<K,V>[] tabRef = table;
		
		for (int i = 0; i < tabRef.length ; i++) {
    		for (TableEntry<K,V> entry = tabRef[i] ; entry != null ; entry = entry.next){
    			if (entry.getValue() == null){
    				return true;
    			}
    		}
    	}
		
		return false;
	}

	/**
     * Removes the mapping for the specified key from this map if present.
     * 
     * @param key key whose mapping is to be removed from the map
     */
    public void remove(Object key) {
    	removeWithKey(key);
    }
    
    /**
     * Returns {@code true} if this map contains no key-value mappings. 
     * 
     * @return
     * 			{@code true} if this map contains no key-value mappings. 
     */
    public boolean isEmpty() {
    	return size == 0;
    }
    
    /**
     * Removes all of the mappings from this map. 
     * After calling this method {@link #size} is zero and {@link table} has no entries.
     * 
     */
    public void clear() {
		TableEntry<K, V>[] tabRef = table;
    	
    	for (int i = 0; i < tabRef.length; i++) {
    		tabRef[i] = null;
    	}
    	modificationCount++;
    	size = 0;
    }
    
    @Override
    public String toString() {
    	StringBuilder builder = new StringBuilder();
    	Iterator<SimpleHashtable.TableEntry<K, V>> iter = iterator();
    	
    	builder.append("[");
    	while(iter.hasNext()) {
    		TableEntry<K,V> pair = iter.next();
    		builder.append(pair.toString());
    		if (iter.hasNext()) {
    			builder.append(", ");
    		}
    	}
    	builder.append("]");
    	
    	return builder.toString();
    }

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * Implementation of iterator.
	 * 
	 * @author Stjepan
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		/** Index of current slot in table.*/
		private int index = 0;
		
		/** Current entry */
		private TableEntry<K,V> current;
		
		/** Next entry.*/
		private TableEntry<K,V> next;
		
		/** For fast-fail of iteration.*/
		private int expectedModificationCount = modificationCount;
		
		/**
		 * Constructs iterator.
		 */
		public IteratorImpl() {
			// ignore all empty slots from beginning
			if ( size > 0) {
				findNext();
			}
		}
		
		/**
		 * Ignores all empty slots starting from {@link #index}
		 */
		private void findNext() {
			TableEntry<K,V>[] tableRef = table;
			while (index < tableRef.length && (next = tableRef[index++]) == null);
		}
		
		@Override
		public boolean hasNext() {
			if (expectedModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			
			return next != null;
		}
		
		/**
		 * {@inheritDoc}
		 * @throws ConcurrentModificationException if modification is made after creation
		 * 			of this iterator
		 */
		@Override
		public TableEntry<K,V> next() {
			if (next == null) {
				throw new NoSuchElementException();
			}
			if (expectedModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			
			TableEntry<K,V> entry = next;
			
			if ((next = entry.next) == null) {
				findNext();
			} 
			
			current = entry;
			return entry;
		}
		
		/**
		 * {@inheritDoc}
		 * @throws ConcurrentModificationException if modification is made after creation
		 * 			of this iterator
		 */
		@Override
		public void remove() {
			if (current == null) {
				throw new IllegalStateException();
			}
			if (expectedModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			
			Object ke = current.getKey();
			current = null;
			SimpleHashtable.this.removeWithKey(ke);
			expectedModificationCount = modificationCount;
		}
	}
	
	/**
	 * Removes and returns the entry associated with the specified key in the HashMap. 
	 * Returns null if the HashMap contains no mapping for this key. 
	 * 
	 * @param key entry with specified key will be removed if possible.
	 * @return null if the HashMap contains no mapping for this key. 
	 */
	private TableEntry<K, V> removeWithKey(Object key) {
		if (key == null) {
			return null;
		}
		int hash = key.hashCode();
		int index = Math.abs(hash) & (table.length-1);
		
		TableEntry<K,V> previous = table[index];
		TableEntry<K,V> removed = previous;
		
		while (removed != null) {
			TableEntry<K,V> next = removed.next;
			Object k ;
			
			if (removed.getKey().hashCode() == hash &&
					((k = removed.key) == key || (key != null && key.equals(k)))) {
				size--;
				modificationCount++;
				if ( previous == removed) {
					table[index] = next;
				} else {
					previous.next = next;
				}
				
				return removed;
			}
			
			previous = removed;
			removed = next;
		}
		
		return removed;
	}
	
}
