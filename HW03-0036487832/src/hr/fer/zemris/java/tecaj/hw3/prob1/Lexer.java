/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Class Lexer works in two modes: basic and extended. It generates one by one token.
 * In mode extended number token cannot be generated.
 * Word is every array made of one or more letters.
 * Number is every array made of one or more digit which is parseable to Long.
 * Last generated token is EOF, generate token after that is not allowed and will throw 
 * exception. 
 * 
 * @author Stjepan
 *
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
	 * index of the first untreated sign 
	 */
	private int currentIndex;
	
	/**
	 * 
	 */
	private LexerState state = LexerState.BASIC;
	
	/**
	 * Constructor that takes input text which being converted to tokens. Argument must
	 * not be null.
	 * 
	 * @param text input text
	 * @throws IllegalArgumentException if argument is null
	 */
	public Lexer(String text) { 
		if (text == null) {
			throw new IllegalArgumentException("Input text of lexer must not be null!");
		}
		
		data = text.toCharArray();
		currentIndex = 0;
	}
	
	/**
	 * Generates and returns next token.
	 * 
	 * @return next token
	 * @throws LexerException if EOF or input text is not valid
	 */
	public Token nextToken() {
		if (token != null && token.getType().equals(TokenType.EOF)) {
			throw new LexerException("Cannot generate next token because EOF is found.");
		}
		switch (state) {
			case BASIC: 
				generateBasicNextToken();
				break;
			case EXTENDED:
				generateExtendedNextToken();
				break;
		}
		return token;
	}
	
	/**
	 * This method generates next token only and only if 
	 * {@link #state} is {@link LexerState#EXTENDED}
	 * 
	 * @throws LexerException if cannot generate next token 
	 */
	private void generateExtendedNextToken() {
		if (! prepareNext()) {
			return;
		}
		if (Character.isLetterOrDigit(data[currentIndex]) 
				|| data[currentIndex] == '\\') {
			String newValue = new String();
			while (currentIndex < data.length && 
					(Character.isLetterOrDigit(data[currentIndex]) || 
							data[currentIndex] == '\\')) {
				newValue += data[currentIndex++];
			}
			token = new Token(TokenType.WORD, newValue);
		} else {
			token = new Token(TokenType.SYMBOL, data[currentIndex++]);
		}
	}

	/**
	 * This method generates next token only and only if 
	 * {@link #state} is {@link LexerState#BASIC}
	 * 
	 * @throws LexerException if cannot generate next token 
	 */
	private void generateBasicNextToken() {
		if (! prepareNext()) {
			return;
		}
		
		if (Character.isLetter(data[currentIndex]) 
				|| data[currentIndex] == '\\') { // word token
			String newToken = new String();
			
			while (currentIndex < data.length &&
					!Character.isWhitespace(data[currentIndex])&& 
					( Character.isLetter((data[currentIndex] )) ||
							data[currentIndex] == '\\')) {
				newToken = addCharToWordBasicToken(newToken);
			}
			token = new Token(TokenType.WORD, newToken);
			
		} else if (Character.isDigit(data[currentIndex])) { // number or word token
			String newToken = new String();
			while (currentIndex < data.length && 
					!Character.isWhitespace(data[currentIndex]) && 
					 Character.isDigit(data[currentIndex] )){
					newToken += data[currentIndex++];
			}
			
			try {
				token = new Token(TokenType.NUMBER, Long.parseLong(newToken));
			} catch (NumberFormatException e) {
				throw new LexerException("Input text is not valid. Cannot parse " + 
						newToken + " to double.");
			}
		} else { 
			token = new Token (TokenType.SYMBOL, data[currentIndex++]);
		}
	}
	
	/**
	 * Adds char on position on {@link #currentIndex} position to String.
	 * 
	 * @param fill string to add char
	 * @return string from argument
	 * @throws LexerException if cannot add letter
	 */
	private String addCharToWordBasicToken(String fill) {
		if (data[currentIndex] == '\\' && (currentIndex + 1 >= data.length || 
						Character.isLetter(data[currentIndex + 1]))) {
			throw new LexerException("Nothing found after escaping sign.");
		}
		
		if (data[currentIndex] == '\\') {
			fill +=  data[++currentIndex];
			++currentIndex;
		}else {
			fill += data[currentIndex++];
		}
		return fill;
	}
	
	/**
	 * Prepares next token by ignoring whitespaces and by checking if EOF came.
	 * 
	 * @return true if next token is prepared, else false
	 */
	private boolean prepareNext() {
		// ignore all whitespace
		while (currentIndex < data.length && 
			Character.isWhitespace(data[currentIndex])) {
		currentIndex ++;
		}
		// last token to generate (end of file)
		if (data.length <= currentIndex) {
			token = new Token(TokenType.EOF, null );
			return false;
		}
		return true;
		
	}
	

	/**
	 * Can be called more times ( without next token generation ). Gets and returns current
	 * token.
	 * 
	 * @return last generated token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Sets state of lexer.
	 * 
	 * @param state new state of lexer
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("Lexer state cannot be null. ");
		}
		this.state = state;
	}
}
