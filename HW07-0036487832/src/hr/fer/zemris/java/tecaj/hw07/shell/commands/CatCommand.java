package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Represents cat command of of shell.
 * 
 * @author Stjepan
 *
 */
public class CatCommand implements ShellCommand {
	
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
	public CatCommand(String name, String description) {
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
		if (arguments == null ){
			System.err.println("Cannot execute " + name + " with null argument.");
			return ShellStatus.CONTINUE;
		}
		String [] args = arguments.trim().split("\\s+");
		File file = new File(args[0]);
		
		if (!file.exists()){
			System.err.println("Cannot execute " + name + " with non existing file");
		} else if (args.length == 1){
			writeFileContent(file, env, "UTF-8");
		} else if (args.length == 2){
			writeFileContent(file, env, args[1]);
		} else {
			System.err.println("Cannot execute " + name + " with more"
					+ " than 2 arguments.");
		}
		
		return ShellStatus.CONTINUE;
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
	
	
	/**
	 * Writes content of file to environment. If char set not defined or doesnt exist 
	 * utf-8 will be used.
	 * 
	 * @param file file to read from
	 * @param env environment to write on
	 * @param string char set
	 */
	private void writeFileContent(File file, Environment env, String string) {
		Charset set = Charset.availableCharsets().get(string);
		set = set == null ? StandardCharsets.UTF_8 : set;
		
		try {
			BufferedReader br = new BufferedReader( 
					new InputStreamReader( 
							new BufferedInputStream( 
									new FileInputStream(file)),set));
			br.lines().forEach((v) -> {
				try {
					env.writeln(v);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
			br.close();
			
		} catch (Exception e) {
			System.err.println("Cannot read file " + file.getAbsolutePath());
		}
		
	}
	

}
