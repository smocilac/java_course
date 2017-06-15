/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.db;



/**
 * This class represents an expression associated with a Condition. The 
 * ConditionalExpression contains an value getter of type {@code IFieldValueGetter}, an 
 * comparison operator  of type {@link IComparisonOperator}, and the string literal 
 * 
 * @author Stjepan
 * @version 1.0
 *
 */
public class ConditionalExpression {
	/** Gets the value for compare.*/
	private final IFieldValueGetter valueGetter;
	
	/** Represents comparing operator.*/
	private final IComparisonOperator operator;
	
	/** String literal, to compare {@link #valueGetter} with. */
	private final String literal;

	/**
	 * Public constructor gets three parameters: {@code #valueGetter}, {@code #operator} and
	 * {@code #literal}. 
	 * 
	 * @param valueGetter getter of comparing value 
	 * @param operator operator to compare
	 * @param literal string to compare to
	 * @throws IllegalArgumentException if any of the argument is null
	 */
	public ConditionalExpression(IFieldValueGetter valueGetter, IComparisonOperator operator, String literal) {
		if (valueGetter == null || operator == null || literal == null){
			throw new IllegalArgumentException("Unable to make expression with null value.");
		}
		
		this.valueGetter = valueGetter;
		this.operator = operator;
		this.literal = literal;
	}
	
	/**
	 * Getter for {@link #valueGetter}
	 * 
	 * @return {@link #valueGetter}
	 */
	public IFieldValueGetter getValueGetter() {
		return valueGetter;
	}

	/**
	 * Getter for {@link #operator}
	 * 
	 * @return {@link #operator}
	 */
	public IComparisonOperator getOperator() {
		return operator;
	}

	/**
	 * Getter for {@link #literal}
	 * 
	 * @return {@link #literal}
	 */
	public String getLiteral() {
		return literal;
	}
	
}
