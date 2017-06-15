package hr.fer.zemris.java.tecaj.hw6.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents storage of integer.
 * 
 * @author Stjepan
 * @version 1.0
 *
 */
public class IntegerStorage implements IntegerStorageChange{
	/** stored integer value */
	private Integer value;
	/** lest generated integer value.*/
	private Integer lastGeneratedValue;
	/** observers which follows changes.*/
	private List<IntegerStorageObserver> observers;
	
	/**
	 * Constructor which stores value from argument.
	 * 
	 * @param initialValue integer to store.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<>();
	}
	
	/**
	 * Adds observer if it is not null or already exist in list of observers.
	 * 
	 * @param observer observer to add
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observer == null) return;
		if (observers.contains(observer)) return;
		
		observers.add(observer);
	}
	
	/**
	 * Removes observer from {@link #observers} if is present. Else does nothing.
	 * 
	 * @param observer
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observer == null) return;
		
		observers.remove(observer);
	}
	
	/**
	 * Clears observers.
	 */
	public void clearObservers() {
		observers.clear();
	}
	
	/**
	 * Sets value of integer to value in argument.
	 * 
	 * @param value new value of stored integer.
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if(this.value!=value) {
			// Store this value
			this.lastGeneratedValue = this.value;
			// Update current value
			this.value = value;
			// Notify all registered observers
			if(observers != null) {
				for (int i = 0, size = observers.size(); i < observers.size(); i++){
					observers.get(i).valueChanged(this);
					if (size != observers.size()){
						size = observers.size();
						i--;
					}
				}
			}
		}
	}
	
	/**
	 * Gets and returns all observers
	 * 
	 * @return {@link #observers}
	 */
	public List<IntegerStorageObserver> getObservers() {
		return observers;
	}

	@Override
	public IntegerStorage getIntegerStorage() {
		return this;
	}

	@Override
	public Integer getCurrentValue() {
		return value;
	}

	@Override
	public Integer getLastStored() {
		return lastGeneratedValue;
	}
	
	
	
	

}
