/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.parseable.Parser;

/**
 * This class implements {@link IFilter} and provides filtering of query.
 * It uses {@link Parser} to parse query.
 * Query is stored in list of expression. It means that there is no tree structure 
 * because only one logic operator is allowed : AND.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class QueryFilter implements IFilter {
	/** All expression that needs to be evaluated. */
	List<ConditionalExpression> expressions;
	
	/**
	 * Constructor which takes one argument: query. 
	 * Query is being parsed to expressions, for that instance of {@link Parser} is used.
	 * 
	 * @param query query to filter by
	 */
	public QueryFilter(String query) {
		if (query == null) {
			throw new IllegalArgumentException();
		}
		
		Parser parser = new Parser(query);
		
		expressions = parser.getExpressions();
		
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw5.db.IFilter#accepts(hr.fer.zemris.java.tecaj.hw5.db.StudentRecord)
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression expr : expressions){
			if (! expr.getOperator().satisfied(expr.getValueGetter().get(record), 
											expr.getLiteral())){
				return false;
			}
		}
		
		return true;
	}

}
