package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Represents tree command of shell.
 * 
 * @author Stjepan
 *
 */
public class TreeCommand implements ShellCommand {
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
	public TreeCommand(String name, String description) {
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
			Files.walkFileTree(Paths.get(arguments.trim()), new PrintTree(env));
			return ShellStatus.CONTINUE;
		} catch (IOException e){
			return ShellStatus.TERMINATE;
		} catch (IllegalArgumentException e){
			System.err.println("Argument for " + name + " is invalid or specified "
					+ "path does not exists.");
			return ShellStatus.CONTINUE;
		}
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
	
	private static class PrintTree implements FileVisitor<Path>{
		private int level;
		private Environment environment;
		

		public PrintTree(Environment environment) {
			if (environment == null){
				throw new IllegalArgumentException("Environment must not be null !");
			}
			this.environment = environment;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path arg0, IOException arg1) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes arg1) throws IOException {
			write(dir);
			level++;
			return  FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path f, BasicFileAttributes arg1) throws IOException {
			write(f);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException arg1) throws IOException {
			return FileVisitResult.CONTINUE;
		}
		

		
		private void write(Path path) throws IOException{
			if (level == 0){
				environment.writeln(path.normalize().toAbsolutePath().toString());
			} else {
				environment.write(String.format("%" + (2*level) +
						"s%s%n", "", path.getFileName()));
			}
		}
		
	}

}
