/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw5.db.lexer;

/**
 * Lexer allows generating one by one token.
 * It is planned to generate tokens for database queries
 * 
 * @author Stjepan
 * @version 1.0
 */
public class Lexer {
	/**
	 * Text from input
	 */
	private char[] data; 
	
	/**
	 * Temporary token
	 */
	private Token token;
	
	/**
	 * States of lexer.
	 * 
	 * @see {@link LexerState}
	 */
	private LexerState state = LexerState.BODY;
	
	/**
	 * index of the first untreated sign 
	 */
	private int currentIndex;
	
	/**
	 * All allowed operators.
	 */
	private final String[] comparisonOperators = {
			"<", "<=", ">", ">=", "=", "!=", "LIKE"
	};
	
	/**
	 * All of the logic operators, currently only AND operator allowed
	 */
	private final String[] logicOperators = {"AND"};
	
	/**
	 * Public constructor. Takes one argument: text to make tokens.
	 * 
	 * @param text Text on which tokens needs to be generated one by one.
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Input text of lexer must not be null!");
		}
		
		data = text.toCharArray();
		currentIndex = 0;
	}
	
	/**
	 * Finds end returns next token. Will throw if not able to generate next token.
	 * It can happen if EOF found or unknown symbol or operator found. Also it can be 
	 * thrown if 
	 * 
	 * @return next token if it exist.
	 * @throws LexerException if not able to generate next token.
	 */
	public Token next() {
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("Next token requested, but EOF found.");
		}
		
		switch (state) {
			case COMMANDS:  
				getNextCommand();
				break;
			case BODY: 
				getNextFromCommandsBody();
				break;
		}
		
		return token;
	}
	
	/**
	 * This is called when mode of lexer is command. 
	 * It makes new token with type {@code COMMAND} 
	 * 
	 */
	private void getNextFromCommandsBody() {
		if (! prepareNext()) { // ignore whitespaces and check if EOF
			return ;
		}
		
		if (Character.isLetter(data[currentIndex])) {
			recognizeAndMakeNext();
		} else if (data[currentIndex] == '\"' ) {
			makeStringLiteral();
		} else {
			makeComparisonOperator();
		}
	}
	
	/**
	 * Method is called when token starts with letter. In that case this method checks
	 * what token type should that token be and generates next token.
	 * 
	 */
	private void recognizeAndMakeNext() {
		StringBuilder builder = new StringBuilder();
		
		while (data.length > currentIndex && Character.isAlphabetic(data[currentIndex])) {
			builder.append(data[currentIndex++]);
		}
		
		String str = builder.toString();
		if (isComparisonOperator(str)) {
			token = new Token(TokenType.OPERATOR, str);
		} else if (isLogicOperator(str)) {
			token = new Token(TokenType.LOGIC_OPERATOR, str);
		} else {
			token = new Token(TokenType.ATTRIBUTE_NAME, str);
		}
	}
	
	/**
	 * Method which creates token with type {@code STRING_LITERAL}.
	 * 
	 * @throws LexerException if literal not defined properly
	 * 
	 */
	private void makeStringLiteral() {
		StringBuilder builder = new StringBuilder();
		currentIndex++;
		while (data.length > currentIndex && data[currentIndex] != '\"') {
			builder.append(data[currentIndex++]);
		}
		
		if (data.length > currentIndex++) {
			token = new Token (TokenType.STRING_LITERAL, builder.toString());
			return;
		}
		// if quote mark never finished
		throw new LexerException("String literal was marked by quotes, but end of quote "
				+ "was never found.");
	}

	/**
	 * Method which creates token with type {@code OPERATOR}.
	 * 
	 * @throws LexerException if comparison operator not exist
	 */
	private void makeComparisonOperator() {
		StringBuilder builder = new StringBuilder();
		
		while (currentIndex < data.length && !Character.isAlphabetic(data[currentIndex])
											&& data[currentIndex] != '\"'
											&& !Character.isWhitespace(data[currentIndex])) {
			builder.append(data[currentIndex++]);
		}
		
		String str;
		if (isComparisonOperator(str = builder.toString())) {
			token = new Token (TokenType.OPERATOR, str);
			return;
		}
		throw new LexerException("Unable to recognize " + builder.toString() + " operator.");
	}
	
	/**
	 * Method checks if comparison operator exist.
	 * 
	 * @param toCompare string to check if is operator.
	 * @return true if string is comparison operator, false otherwise
	 */
	private boolean isComparisonOperator(String toCompare) {
		for (int i = 0; i < comparisonOperators.length; i++) {
			if (comparisonOperators[i].equals(toCompare)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if string matches one of the logic operator name
	 * 
	 * @param toCompare string to check
	 * @return true if is logic operator.
	 */
	private boolean isLogicOperator(String toCompare) {
		for (int i = 0; i < logicOperators.length; i++) {
			if (logicOperators[i].equals(toCompare.toUpperCase())) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * It generates token with type {@code COMMAND}
	 * 
	 */
	private void getNextCommand() {
		if (! prepareNext()) { // ignore whitespaces and check if EOF
			return ;
		}
		
		// every command should start with letter and only contain letters
		if (!Character.isLetter(data[currentIndex++])) {
			throw new LexerException("Invalid name of command. It cannot start with "
					+ data[--currentIndex]);
		}
		
		StringBuilder builder = new StringBuilder();
		
		while (currentIndex < data.length && Character.isLetter(data[currentIndex])) {
			builder.append(data[currentIndex++]);
		}
		
		token = new Token(TokenType.COMMAND, builder.toString());
	}
	
	/**
	 * Method prepares next Token by avoiding all whitespaces and checking if EOF
	 * 
	 * @return true if next prepared, false if EOF found
	 */
	private boolean prepareNext() {
		// ignore all whitespace
		while (currentIndex < data.length && 
				Character.isWhitespace(data[currentIndex])) {
			currentIndex ++;
		}
		// last token to generate (end of file)
		if (data.length <= currentIndex) {
			token = new Token(TokenType.EOF, null);
			return false;
		}
		return true;
	}
	
	/**
	 * Sets state to read either commands or its body.
	 * 
	 * @see {@link LexerState}
	 * @param state new {@link #state} for this lexer
	 * @throws IllegalArgumentException if argument is null
	 */
	public void setState(LexerState state){
		if (state == null){
			throw new IllegalArgumentException();
		}
		this.state = state;
	}
}
