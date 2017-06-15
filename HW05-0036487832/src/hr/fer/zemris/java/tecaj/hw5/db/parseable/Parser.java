package hr.fer.zemris.java.tecaj.hw5.db.parseable;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.ConditionalExpression;
import hr.fer.zemris.java.tecaj.hw5.db.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.Lexer;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.Token;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.TokenType;
import hr.fer.zemris.java.tecaj.hw5.db.operators.*;

/**
 * 
 * @author Stjepan
 * @version 1.0
 */
public class Parser {
	
	/**	lexer used for production of tokens */
	private Lexer lexer;
	
	/** all expressions that was parsed by parser */
	private List<ConditionalExpression> expressions;
	
	/** All comparison operators.*/
	private final String[] comparisonOperators = {
			"<", "<=", ">", ">=", "=", "!=", "LIKE"
	};
	
	/**
	 * Constructor which takes one argument : String to parse.
	 * 
	 * @param documentBody commands body to parse
	 * @throws ParserException if argument is null
	 */
	public Parser(String documentBody) {
		if (documentBody == null) {
			throw new ParserException("Document body must not be null.");
		}
		
		expressions = new ArrayList<>();
		lexer = new Lexer(documentBody);
		
		while (true){
			parseDocument();
			Token token;
			
			try{
				token = lexer.next();
				if (token.getType() == TokenType.EOF){
					break;
				} else if (token.getType() == TokenType.LOGIC_OPERATOR){
					continue;
				} else {
					throw new ParserException("Wrong expression, expected logic operator, "
							+ "found " + token.getValue().toString());
				}
			} catch(Exception e){
				throw new ParserException(e.getMessage());
			}
		}
	}

	/**
	 * Method which parses commands body to one or more expressions.
	 * Only one logic operator is allowed between expressions - AND.
	 * 
	 */
	private void parseDocument() {
		Token token = getToken(TokenType.ATTRIBUTE_NAME);
		IFieldValueGetter valueGetter = getValueGetter(token.getValue().toString());
		
		token = getToken(TokenType.OPERATOR);
		IComparisonOperator operator = getComparisonOperator(token.getValue().toString());
		
		token = getToken(TokenType.STRING_LITERAL);
		String literal = token.getValue().toString();
		
		if (! (operator instanceof LikeOperator )){
			if (literal.contains("*")){
				throw new ParserException("Wildcard * is not allowed on this operator.");
			}
		}
		
		expressions.add(new ConditionalExpression(valueGetter, operator, literal ));
		
	}
	
	/**
	 * Gets and returns value.
	 * 
	 * @param string defines which getter is needed
	 * @return instance of class that inherits {@code IFieldValueGetter}
	 */
	private IFieldValueGetter getValueGetter(String string) {
		IFieldValueGetter getter = StudentRecord.getFieldValueGetter(string);
		
		if (getter == null){
			throw new ParserException("Attribute " + string + " does not exist");
		}
		
		return getter;
		
	}
	
	/**
	 * Returns operator depending on argument. If no operator is defined with argument
	 * returns null;
	 * 
	 * @param string operator to return
	 * @return operator
	 */
	private IComparisonOperator getComparisonOperator(String string) {
		if (string.equals(comparisonOperators[0])) return new LessThanOperator();
		if (string.equals(comparisonOperators[1])) return new LessOrEqualOperator();
		if (string.equals(comparisonOperators[2])) return new GreaterThanOperator();
		if (string.equals(comparisonOperators[3])) return new GreaterOrEqualOperator();
		if (string.equals(comparisonOperators[4])) return new EqualOperator();
		if (string.equals(comparisonOperators[5])) return new NotEqualOperator();
		if (string.equals(comparisonOperators[6])) return new LikeOperator();
		
		return null;
	}

	/**
	 * Gets next token. Catches exceptions from {@link #lexer} and rethrows 
	 * {@link ParserException}
	 * 
	 * @param attributeName expected token type
	 * @return next token
	 * @throws ParserException if next token is invalid or cannot be generated
	 */
	private Token getToken(TokenType attributeName) {
		Token token;
		try {
			token = lexer.next();
		} catch (Exception e){
			//e.printStackTrace();
			throw new ParserException(e.getMessage());
		}
		
		if (token == null || token.getType() != attributeName){
			throw new ParserException("Wrong expression. ");
		}
		
		return token;
	}
	
	/**
	 * Gets and returns list of expressions.
	 * 
	 * @return {@link #expressions}
	 */
	public List<ConditionalExpression> getExpressions() {
		return expressions;
	}
	
	
	
}
