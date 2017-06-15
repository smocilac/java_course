package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Represents copy command of shell.
 * 
 * @author Stjepan
 *
 */
public class CopyCommand implements ShellCommand {
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
	public CopyCommand(String name, String description) {
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
		Charset set = StandardCharsets.UTF_8 ;
		
		if (arguments == null || arguments.trim().isEmpty()){
			System.err.println("Cannot execute " + name + " with null or empty argument.");
			return ShellStatus.CONTINUE;
		}
		String [] args = arguments.trim().split("\\s+");
		
		if (args.length == 2){
			File file = new File(args[0]);
			if (!file.exists()){
				System.err.println("File " + file.getAbsolutePath() + " doesnt exists.");
				return ShellStatus.CONTINUE;
			}
			File file2 = new File(args[1]);

			try {
				file2.getParentFile().mkdirs();
				
				if (file2.isDirectory()){
					file2 = new File (file2.getAbsolutePath() + "\\" +  file.getName());
			    }
				
				if (file2.exists()){
					env.write("Hey, this file already exists. Please enter Y for overwrite"
							+ " or N for cancel. Y/N: ");
					String s = env.readLine();
					if (s.toUpperCase().equals("N")){
						env.writeln("Ok, I wont do it, I am canceling copy operation "
								+ "because you said so. Canceled ! ");
						return ShellStatus.CONTINUE;
					} else if (! s.toUpperCase().equals("Y")){
						env.writeln("Ok, I am not sure what " + s + " means to you, but"
								+ " I will cancel operation anyway. Canceled. ");
						return ShellStatus.CONTINUE;
					}
				}
				file2.createNewFile();
				
				BufferedReader br = new BufferedReader( 
						new InputStreamReader( 
								new BufferedInputStream( 
										new FileInputStream(file)),set));
				
				BufferedWriter bw = new BufferedWriter( 
						new OutputStreamWriter( 
								new BufferedOutputStream( 
										new FileOutputStream(file2)),set));
				
				br.lines().forEach((v) -> {
					try {
						bw.write(v);
						bw.flush();
					} catch (Exception e) { // ignorable
						System.exit(-1);
					}
				});
				
				br.close();
				bw.close();
				env.writeln("Copied !");
				
			} catch (Exception e) {
				System.err.println("Cannot copy file from " + args[0] + " to " + args[1]);
			}
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

}
