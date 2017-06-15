/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.exec;

/**
 * This class represents simple wrapper on {@link Object} class instance.
 * It implements {@link SimpleArithmeticOperation} and provides basic mathematical 
 * operations.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class ValueWrapper implements SimpleArithmeticOperation {
	/** Wrapped instance. */
	private Object object;
	/** For some basic compare */
	private final double EPSILON = 1E-5;

	/**
	 * Constructor takes one argument - object to store internally
	 * 
	 * @param object to store
	 * @throws IllegalArgumentException if mathematical operations cannot be implemented
	 * 			for this object.
	 */
	public ValueWrapper(Object object) {
		checkInstance(object);
		this.object = object;
	}
	
	/**
	 * gets value
	 * @return value
	 */
	public Object getValue() {
		return object;
	}
	
	/**
	 * Sets value
	 * 
	 * @param object new value
	 * @throws IllegalArgumentException if mathematical operations cannot be implemented
	 * 			for this object.
	 */
	public void setValue(Object object) {
		checkInstance(object);
		this.object = object;
	}
	

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.scripting.exec.SimpleArithmeticOperation#increment(java.lang.Object)
	 */
	@Override
	public void increment(Object incValue) {
		checkInstance(incValue);
		preformAction(incValue, '+');// here I know that argument is valid
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.scripting.exec.SimpleArithmeticOperation#decrement(java.lang.Object)
	 */
	@Override
	public void decrement(Object decValue) {
		checkInstance(decValue);
		preformAction(decValue, '-');// here I know that argument is valid

	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.scripting.exec.SimpleArithmeticOperation#multiply(java.lang.Object)
	 */
	@Override
	public void multiply(Object mulValue) {
		checkInstance(mulValue);
		preformAction(mulValue, '*');// here I know that argument is valid
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.scripting.exec.SimpleArithmeticOperation#divide(java.lang.Object)
	 */
	@Override
	public void divide(Object divValue) {
		checkInstance(divValue);
		preformAction(divValue, '/');// here I know that argument is valid
	}
	
	/**
	 * Checks if instance can provide mathematical operations.
	 * @param obj object to check if can preform operation
	 */
	private void checkInstance(Object obj) {
		if (obj == null) return;
		
		if ((obj instanceof Integer) 
				|| (obj instanceof Double) 
					){
			return;
		}
		
		if ( obj instanceof String){
			try {
				Double.parseDouble((String)obj);
				return;
			} catch (NumberFormatException e){
				throw new IllegalArgumentException("String: " + obj.toString() + 
						" is not parseable to number.");
			}
		}
		
		throw new IllegalArgumentException("Arithmetic operations are not allowed for instances"
				+ " of " + obj.getClass().getName() + " class.");		
	}
	
	/**
	 * Preforms mathematical operation with object from argument.
	 * 
	 * @param obj object to preform operation with
	 * @param ch char of operation
	 * @throws IllegalArgumentException if trying to divide with zero.
	 */
	private void preformAction(Object obj, char ch){
		Double value1 = getDoubleValue(object);
		Double value2 = getDoubleValue(obj);
		
		Double result = preformOperation(value1, value2, ch);
		
		if (object == null || object instanceof Integer){
			if ( obj == null || obj instanceof Integer || (!obj.toString().contains(".")
								&& !obj.toString().toUpperCase().contains("E"))){
				object = (int)((double) result);
				return;
			}
			object = result;
		} else if (object instanceof Double){
			object = result;
		} else {
			String obj1 = (String) object;
			String obj2 = (String) obj;
			if (obj1.contains(".") || obj1.toUpperCase().contains("E") || obj2.contains(".") || 
						obj2.toUpperCase().contains("E") ){
				object = result.toString();
				return;
			}
			object = ((Integer)((int) (double)result)).toString();
				
		}
	}
	
	/**
	 * Gets double value of object.
	 * 
	 * @param obj to make double value from
	 * @return double value of object
	 */
	private double getDoubleValue(Object obj){
		if (obj == null){
			return 0.; 
		} else if (obj instanceof Integer){
			return Double.parseDouble(obj.toString());
		} else if (obj instanceof Double){
			return (Double) obj;
		}
		
		try{
			Double val = Double.parseDouble((String) obj);
			return val;
		} catch (NumberFormatException e){
			throw new IllegalArgumentException(obj.toString() + " is not a number.");
		}
	}
	
	/**
	 * preforms wanted operation 
	 * 
	 * @param first first value
	 * @param second second value
	 * @param ch operation
	 * @return result of operaton
	 */
	private double preformOperation(double first, double second, char ch){
		switch (ch){
		case '+': return first + second;
		case '-': return first - second;
		case '*': return first * second;
		case '/': if (second == 0.){
						throw new IllegalArgumentException("Cannot divide with zero.");
				  }
				  return first / second;
				  
		default : throw new IllegalArgumentException("Unrecognized operation: " + ch);
		}
	}
	
	/**
	 * Compares double values of objects. For comparing is used {@link #EPSILON}
	 * 
	 * @param withValue object to compare with
	 * @return zero if equal, negative if lass than, positive if greater than
	 */
	public int numCompare(Object withValue){
		checkInstance(withValue);
		
		double val1 = getDoubleValue(object);
		double val2 = getDoubleValue(withValue);
		double difference = val1 - val2;
		
		if (Math.abs(difference) < EPSILON){
			return 0;
		}
		if (Math.abs(difference) < 1){
			return difference < 0 ? -1 : 1;
		}
		
		return (int) difference;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValueWrapper other = (ValueWrapper) obj;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		return true;
	}
	
	

}
