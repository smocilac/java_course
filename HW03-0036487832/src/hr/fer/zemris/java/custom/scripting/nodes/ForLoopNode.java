/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Class which represents for loop. 
 * 
 * @author Stjepan
 *
 */
public class ForLoopNode extends Node{
	/**
	 * variable to iterate over
	 */
	private final ElementVariable variable;
	
	/**
	 * first argument of expression
	 */
	private final Element startExpression;
	
	/**
	 * defines when will for loop finish
	 */
	private final Element endExpression;
	
	/**
	 * Can be null, defines step of first argument (variable)
	 */
	private final Element stepExpression;
	
	/**
	 * Public constructor which takes three parameters: variable, value to start loop, 
	 * value to end loop. Step is defined as null.
	 * 
	 * @param variable variable which will iterate
	 * @param startExpression Expression to start loop
	 * @param endExpression Expression to end loop
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression ) {
		this(variable, startExpression, endExpression, null);
		
	}
	
	/**
	 * Public constructor which takes three parameters: variable, value to start loop, 
	 * value to end loop and step of loop.
	 * 
	 * @param variable variable which will iterate
	 * @param startExpression Expression to start loop
	 * @param endExpression Expression to end loop
	 * @param stepExpression step of loop
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Gets and returns variable.
	 * @return variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * Gets and returns start expression.
	 * @return start expression
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * Gets and returns end expression.
	 * @return end expression
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 * Gets and returns step expression.
	 * @return step expression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	@Override
	public String toString() {
		String stringValue = new String();
		stringValue += "{$ FOR ";
		stringValue += (variable.asText() + " ");
		stringValue += (startExpression.asText() + " "+ endExpression.asText());
		if (stepExpression != null) {
			stringValue += (" " + stepExpression.asText());
		}
		stringValue += " $}";
		
		for (int i = 0; i < this.numberOfChildren(); i++) {
			stringValue += this.getChild(i).toString();
		}
		stringValue += "{$ END $}";
		
		
		return stringValue;
	}
	
	
}
