package hr.fer.zemris.java.tecaj.hw5.db.lexer;

/**
 * Represents states of lexer.
 * 
 * @author Stjepan
 * @version 1.0
 */
public enum LexerState {
	/** Tells lexer to expect command */
	COMMANDS, 
	
	/** Tells lexer to expect body of command */
	BODY
}
