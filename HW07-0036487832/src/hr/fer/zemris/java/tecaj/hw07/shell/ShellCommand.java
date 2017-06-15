package hr.fer.zemris.java.tecaj.hw07.shell;

import java.util.List;
/**
 * Interface for command shell .
 * 
 * @author Stjepan
 *
 */
public interface ShellCommand {
	/**
	 * Executes command and returns {@link ShellStatus} back to shell.
	 * 
	 * @param env {@link Environment} object
	 * @param arguments arguments for command
	 * @return {@link ShellStatus}
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Gets and returns command name.
	 * 
	 * @return command name
	 */
	String getCommandName();
	
	/**
	 * Gets and returns command description.
	 * 
	 * @return command description.
	 */
	List<String> getCommandDescription();
}
