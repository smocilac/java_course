/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.tecaj.hw3.prob1.LexerException;

/**
 * Class SmartScriptLexer works in two modes: tag and text. It generates one by one token.
 * In mode text: number token (double or integer) cannot be generated. Also, in this mode
 * escaping is permitted iff: \\ or \{ .
 * In mode tag: string can be generated only if under quotes. Also, in this mode escaping
 * is permitted iff: \\ or \" .
 * All variables end function names must start with letter, a contain only and only numbers
 * or underscore after first letter.
 * Last generated token is EOF, generate token after that is not allowed and will throw 
 * exception. 
 * 
 * @author Stjepan
 *
 */
public class SmartScriptLexer {
	/**
	 * Text from input
	 */
	private char[] data; 
	
	/**
	 * Temporary token
	 */
	private SmartScriptToken token;
	
	/**
	 * index of the first untreated sign 
	 */
	private int currentIndex;
	
	/**
	 * Current state of lexer, can be text or tag
	 */
	private SSLexerState state;
	
	/**
	 * All allowed operators.
	 */
	private final String allOperators = "+-*/^";
	
	/**
	 * Public constructor. Takes one argument: text to make tokens.
	 * 
	 * @param text Text on which tokens needs to be generated one by one.
	 */
	public SmartScriptLexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Input text of lexer must not be null!");
		}
		
		data = text.toCharArray();
		currentIndex = 0;
		state = SSLexerState.TEXT;
	}
	
	/**
	 * This method generates and returns next token. Has to modes: TEXT and Tag.
	 *  
	 * @return next token 
	 * @throws LexerException if next token cannot be generated
	 */
	public SmartScriptToken nextToken() {
		if (token != null && token.getType().equals(SSTokenType.EOF)) {
			throw new LexerException("Cannot generate next token because EOF is found.");
		}
		
		switch (state) {
			case TEXT: 
				textgenerateNextToken();
				break;
			case TAG:
				tagGenerateNextToken();
				break;
		}
		
		return token;
	}
	
	/**
	 * Gets current token.
	 * @return return active (last generated) token.
	 */
	public SmartScriptToken getToken() {
		return token;
	}
	
	/**
	 * Gets and return current state of lexer.
	 * @param state State of lexer.
	 */
	public void setState(SSLexerState state) {
		this.state = state;
	}
	
	/**
	 * Generates next token iff {@link #state} is {@link SSLexerState#TEXT}.
	 * It makes String from all characters till EOF or {$ found.
	 * Escaping is valid only with \\ and \{
	 * 
	 * @throws LexerException if unable to generate next token
	 */
	private void textgenerateNextToken() {
		
		if (data.length <= currentIndex) {
			token = new SmartScriptToken(SSTokenType.EOF, null);
			return;
		}
		
		String value = new String();
		if (currentIndex + 1 < data.length && data[currentIndex] == '{' && 
				data[currentIndex+1] == '$') {
			value = "{$";
			token = new SmartScriptToken(SSTokenType.SYMBOL, new ElementString(value));
			currentIndex += 2;
			return;
		}
		
		for (; currentIndex < data.length ;) {
			if (data[currentIndex] == '\\' ) {
				if (currentIndex + 1 == data.length && data[currentIndex + 1] != '\\' 
						&& data[currentIndex + 1] != '{') {
							throw new LexerException("Escaping went wrong.");
				} else {
					value += data[currentIndex + 1];
					currentIndex += 2;
					continue;
				}
			} else if (data[currentIndex] == '{' && currentIndex + 1 < data.length 
						&& data[currentIndex + 1] == '$') {
				/*currentIndex += 2;*/
				break;
			}
			value += data[currentIndex++];
		}
		token = new SmartScriptToken(SSTokenType.STRING, new ElementString(value));
	}

	/**
	 * Generates next token iff {@link #state} is {@link SSLexerState#TAG}
	 * 
	 * @throws LexerException if unable to generate next token.
	 */
	private void tagGenerateNextToken() {
		if (! prepareNext()) {// ignores whitespaces and checks if EOF is next token
			return;
		} 
		
		if (data[currentIndex] == '\"') {
			currentIndex++;
			makeStringUnderQuotes();
		} else if (data[currentIndex] == '@') {
			currentIndex++;
			makeFunctionToken();
		} else if (Character.isDigit(data[currentIndex]) || data[currentIndex] == '-'){
			if (currentIndex + 1 < data.length) {
				if (Character.isDigit(data[currentIndex + 1]) || data[currentIndex] != '-'){
					makeNumberToken();
				} else {
					makeOperatorToken();
				}
			} else {
				throw new LexerException("EOF not expected after minus sign.");
			}
		} else if (Character.isLetter(data[currentIndex])){
			makeVariableToken();
		} else if (data[currentIndex] == '=') {
			token = new SmartScriptToken(SSTokenType.SYMBOL, new ElementString("="));
			currentIndex++;
		} else if (data[currentIndex] == '$') {
			if (currentIndex < data.length - 1 && data[currentIndex + 1] == '}') {
				token = new SmartScriptToken(SSTokenType.SYMBOL, new ElementString("$}"));
				currentIndex += 2;
			} else {
				throw new LexerException("Excepted } after simbol of $.\n");
			}
		} else {
			makeOperatorToken();
		}
	}
	
	/**
	 * Method which makes variable token. Variable must be announced with @ 
	 * Variable name must start with letter and contain only letters, digit or underscore.
	 * 
	 * @throws LexerException if variable name is not valid or EOF found
	 */
	private void makeVariableToken() {
		String value = "";
		value += data[currentIndex++];
		
		while (currentIndex < data.length && (Character.isLetterOrDigit(data[currentIndex]) 
				|| data[currentIndex] == '_')) {
			value += data[currentIndex++];
		}
		
		token = new SmartScriptToken(SSTokenType.VAR, new ElementVariable(value));
	}
	
	/**
	 * Method which makes operator ( class {@link ElementOperator} represents operators)
	 * Valid operators are defined with private constant variable {@link #allOperators}
	 * 
	 * @throws LexerException if operator not defined in {@link #allOperators}
	 */
	private void makeOperatorToken() {
		
		for (int i = 0; i < allOperators.length(); i++) {
			if (data[currentIndex] == allOperators.charAt(i)) {
				String value = "";
				value += data[currentIndex++];
				token = new SmartScriptToken(SSTokenType.OPERATOR, 
							new ElementOperator(value));
				return;
			}
		}
			
		throw new LexerException("Operator " + data[currentIndex] 
				+ " not recognized.");	
	}
	
	/**
	 * Creates positive or negative value of integer or double.
	 * Number must start with minus or number. 
	 * Number is valid if has no floating point (integer) or contains one floating 
	 * point (double) and if starts with minus or digit.
	 * Element integer is represented with {@link ElementConstantInteger}
	 * Element double is represented with {@link ElementConstantDouble}
	 * 
	 * 
	 * @throws LexerException if number is not valid or EOF found
	 */
	private void makeNumberToken() {
		String value = "";
		boolean hasDecimalPart = false;
		
		if (data[currentIndex] == '-') {
			value += "-";
			currentIndex++;
		}
		//read number and put string value into String variable named value
		while (currentIndex < data.length && (Character.isDigit(data[currentIndex]) 
				|| data[currentIndex] == '.')) {
			if (data[currentIndex] == '.') {
				if (hasDecimalPart == true) {
					throw new LexerException("Cannot recognize decimal number.");
				}
				hasDecimalPart = true;
			}
			value += data[currentIndex++];
		}
		// check whether this is double or integer
		if (hasDecimalPart) {
			try {
				double val = Double.parseDouble(value);
				token = new SmartScriptToken(SSTokenType.DOUBLE, 
						new ElementConstantDouble(val));
			} catch (NumberFormatException e){
				throw new LexerException("Cannot parse " + value + " to double.");
			}
		} else {
			try {
				int val = Integer.parseInt(value);
				token = new SmartScriptToken(SSTokenType.INTEGER, 
						new ElementConstantInteger(val));
			} catch (NumberFormatException e){
				throw new LexerException("Cannot parse " + value + " to integer.");
			}
		}
	}
	
	/**
	 * Makes new function based on function name.
	 * Function name is valid iff starts with letter and contains only and only numbers,
	 * letters and/or underscores.
	 * Element function is represented with {@link ElementFunction}
	 * 
	 * @throws LexerException if function name is not valid or EOF found
	 */
	private void makeFunctionToken() {
		if (currentIndex >= data.length || !Character.isLetter(data[currentIndex])){
			throw new LexerException("Funtion name must begin with letter.");
		}
		
		String value = "";
		
		while (currentIndex < data.length && (Character.isLetterOrDigit(data[currentIndex])
				|| data[currentIndex] == '_')) {
			value += data[currentIndex++];
		}
		if (currentIndex >= data.length) {
			throw new LexerException("EOF found after function definition");
		}
		
		token = new SmartScriptToken(SSTokenType.FUNCTION, new ElementFunction(value));
	}

	/**
	 * Makes String when {@link #state} is {@link SSLexerState#TAG}
	 * String element is represented with {@link ElementString}
	 * 
	 * @throws LexerException if EOF found or escaping not used properly
	 */
	private void makeStringUnderQuotes() {
		String value = "";
		
		for (; data[currentIndex] != '\"'; ) {
			if (currentIndex + 1 >= data.length) {
				throw new LexerException("Quote never finished.");
			} else if (data[currentIndex] == '\\') {
				if (currentIndex + 1 >= data.length) {
					throw new LexerException("Quote never finished.");
				}
				currentIndex++;
			}
			value += data[currentIndex++];
		}
		currentIndex++; // move from closed quote sign
		
		token = new SmartScriptToken(SSTokenType.STRING, new ElementString(value));
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
			token = new SmartScriptToken(SSTokenType.EOF, null);
			return false;
		}
		return true;
	}
}
