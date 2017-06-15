package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Represents exit command of shell.
 * 
 * @author Stjepan
 *
 */
public class ExitCommand implements ShellCommand {
	/** Name of operation */
	private final String name;
	/** Description of operation*/
	private final String description;
	
	/**
	 * This constructor takes two arguments which must not be null - name and description
	 * of command.
	 * 
	 * @param name
	 * @param description
	 * @throws IllegalArgumentException if argument null
	 */
	public ExitCommand(String name, String description) {
		if(name == null){
			throw new IllegalArgumentException("Command name must not be null!");
		}
		this.name = name;
		this.description = description;
	}
	
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand#executeCommand(hr.fer.zemris.java.tecaj.hw07.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			env.write("Thank you for using MyShell. Goodbye! ");
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-5);
		}
		
		return ShellStatus.TERMINATE;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand#getCommandName()
	 */
	@Override
	public String getCommandName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand#getCommandDescription()
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add(description);
		return list;
	}

}
