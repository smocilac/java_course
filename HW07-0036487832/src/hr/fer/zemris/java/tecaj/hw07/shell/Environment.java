package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;

/**
 * Represents environment for program.
 * 
 * @author Stjepan
 *
 */
public interface Environment {
	/**
	 * Reads line. Throws {@link IOException} if error occurs.
	 * 
	 * @return String representation of line
	 * @throws IOException if error occurs.
	 */
	String readLine() throws IOException;
	
	/**
	 * Writes text from argument to some output stream. 
	 * 
	 * @param text text to write
	 * @throws IOException if error occurs
	 */
	void write(String text) throws IOException;
	
	/**
	 * Writes text from argument followed with new line character to some output stream. 
	 * 
	 * @param text text to write
	 * @throws IOException if error occurs
	 */
	void writeln(String text) throws IOException;
	
	/**
	 * Returns commands to iterate on.
	 * @return commands
	 */
	Iterable<ShellCommand> commands();
	
	/**
	 * Gets and returns multi line symbol.
	 * 
	 * @return multi line symbol.
	 */
	Character getMultilineSymbol();
	
	/**
	 * Sets multi line symbol to one from argument.
	 * 
	 * @param symbol new symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Gets and returns prompt symbol.
	 * 
	 * @return prompt symbol.
	 */
	Character getPromptSymbol();
	
	/**
	 * Sets prompt symbol to character from argument
	 * 
	 * @param symbol new prompt symbol.
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Gets and returns more lines symbol.
	 * 
	 * @return more lines symbol.
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Sets more lines symbol to one from argument.
	 * 
	 * @param symbol new more lines symbol.
	 */
	void setMorelinesSymbol(Character symbol);
	
}
