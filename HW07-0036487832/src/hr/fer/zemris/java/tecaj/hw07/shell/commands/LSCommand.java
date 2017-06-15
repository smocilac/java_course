package hr.fer.zemris.java.tecaj.hw07.shell.commands;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Represents ls command of shell
 * 
 * @author Stjepan
 *
 */
public class LSCommand implements ShellCommand {
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
	public LSCommand(String name, String description) {
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
		if (arguments == null){
			System.err.println("Argument for " + name + " command should not be null.");
			return ShellStatus.CONTINUE;
		}
		try{
			File f = new File(arguments);
			if ( ! f.exists()){
				env.writeln("Provided directory does not exists.");
			}else if( f.isDirectory()){
				String[] list = f.list();
				for (String name : list){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Path path = Paths.get(f.getAbsolutePath() + File.separator + name);
					BasicFileAttributeView faView = Files.getFileAttributeView(
					path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
					);
					BasicFileAttributes attributes = faView.readAttributes();
					FileTime fileTime = attributes.creationTime();
					String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
					
					// get specifications 
					File fin = new File(path.toString());
					StringBuilder sb = new StringBuilder();
					String whatAmI = "";
					sb.append(fin.isDirectory() ? "d" : "-");
					sb.append(fin.canRead() ? "r" : "-");
					sb.append(fin.canWrite() ? "w" : "-");
					sb.append(fin.canExecute() ? "x" : "-");
					whatAmI = sb.toString();
					
					env.writeln(String.format("%4s  %10d  %20s  %s", 
							whatAmI, fin.length(), formattedDateTime, name));
				}
			}
		} catch (Exception e){
			System.err.println(e.getMessage());
			return ShellStatus.CONTINUE;
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
