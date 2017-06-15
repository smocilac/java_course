package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.*;

/**
 * Program allows user shell commands like copy, cat, tree, ... 
 * 
 * Default symbol for prompt is >, for multi line is | and if user wants more lines symbol
 * \ should be used before new line character by default.
 * Change or symbol info is provided by key word "symbol"
 * Example of use: 
 * {@code symbol} - lists all symbols
 * {@code symbol multiline} - writes current multiline character
 * {@code symbol prompt *} - changes current prompt symbol to * 
 * 
 * IMPORTANT: by default more lines character is '\', so if your path ends with '\', you can
 * use it as : E:\\ (new line) (new line) 
 * It will be equal as E:\ , or you can simple change more lines character with symbol 
 * command.
 * 
 * 
 * @author Stjepan
 *
 */
public class MyShell {
	/**  status of shell of type {@link ShellStatus}*/
	private static ShellStatus status = ShellStatus.CONTINUE;
	/**  symbol command*/
	private static final String SYMBOL = "SYMBOL";
	/**  list of all commands.*/
	private static Map<String, ShellCommand> commands;
    
    static {
            commands = new HashMap<>();
            ShellCommand[] cc = {
                            new CatCommand("CAT", "Opens given file and writes its content"
                            		+ " to console"),
                            new CharsetsCommand("CHARSETS", "Lists names of supported "
                            		+ "charsets current Java platform"),
                            new CopyCommand("COPY", "Takes two arguments: source file name and "
                            		+ "destination file name. Copies source file to given "
                            		+ "destination. If already exist, asks user to override."),
                            new HexDump("HEXDUMP", "Prints out absolute path"),
                            new LSCommand("LS", "Writes a directory listing."),
                            new Mkdir("MKDIR", "Creates directory and creates the "
                            		+ "appropriate directory structure"),
                            new TreeCommand("TREE", "Prints a tree (each directory level "
                            		+ "shifts output two charatcers to the right)"), 
                            new ExitCommand("EXIT", "Terminates program. ")
            };
            for (ShellCommand c : cc) {
                commands.put(c.getCommandName(), c);
             }
    }
    
    
    /**
     * Program which takes no arguments.
     * 
     * @param args
     */
    public static void main(String[] args) {
		//System.out.println("Welcome to MyShell v 1.0");
		
		EnviromentImpl environment = new EnviromentImpl(System.in, System.out );

		try {
			environment.writeln("Welcome to MyShell v 1.0");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (status != ShellStatus.TERMINATE) {
			
			List<String> list = readLineOrLines(environment); // read lines
			if (list == null){
				//System.err.println("Empty input ."); //exit if nothing was 
				                         //provided  
				continue; // continue if nothing was provided
			}
			
			StringBuilder builder = new StringBuilder();
			for (String str : list){
				builder.append(str + " "); // concatenate lines into one semantic meaningful
			}
			
			String s = builder.substring(0, builder.length()-1).toString(); // store lines 
												//into one String s
			String commandName = s;
			String arguments = "";
			if (s.contains(" ")){
				commandName = s.substring(0,s.indexOf(' ')); 
				arguments = s.substring(s.indexOf(' ')+1);
			} 
			
			ShellCommand command = commands.get(commandName.toUpperCase());
			if (command != null) {
				status = command.executeCommand(environment, arguments);
				continue;
			}
			if ( commandName.toUpperCase().equals(SYMBOL)){
				status = updateOrProvideInfoForSymbol(environment, arguments);
				continue;
			}
			System.err.println("Command '" + commandName + "' not recognized.\n");
		}
	}



    /**
     * Method which solves symbol change or update command.
     * 
     * @param environment to write and read to/from
     * @param arguments arguments for symbol command
     * @return {@link ShellStatus#TERMINATE} if something cannot be handled
     * 						{@link ShellStatus#CONTINUE}
     */
	private static ShellStatus updateOrProvideInfoForSymbol(EnviromentImpl environment,
										String arguments) {
		if (arguments.isEmpty()) {
			environment.writeSymbols();
		}
		
		String[] args = arguments.trim().split("\\s+");
		try {
			String upperCommand = args[0].toUpperCase();
			Character sym = environment.getSymbols().get(upperCommand);
			if (sym == null){
				System.err.println("Symbol for '" + upperCommand + "' doesn't exists.");
				return ShellStatus.CONTINUE;
			} else if (args.length == 1){
				environment.writeln("Symbol for " + upperCommand + 
						" is '" + sym + "'");
			} else if (args.length == 2) {
				if (args[1].length() != 1){
					System.err.println("Symbol for " + upperCommand 
							+ " must be single character, but "
							+ "you have provided string.");
				}
				environment.getSymbols().put(upperCommand, args[1].charAt(0));
				environment.writeln("Symbol for " + upperCommand + " '"
							+ environment.getSymbols().get(upperCommand) + "' .");
			} else {
				System.err.println("I don't know how do you want to change symbol for "
					+ args[0].toUpperCase() + " because to many arguments was provided.");
			}
		} catch (IOException e){
			System.err.println(e.getMessage());
			System.exit(-8);
		}
		
		return ShellStatus.CONTINUE;
	}


	/**
	 * Reads one line, or more if line ends with more lines character.
	 * Terminates program if something unexpected shows up
	 * 
	 * @param environment environment to write on
	 * @return list of input lines
	 */
	private static List<String> readLineOrLines(EnviromentImpl environment) {
		List<String> result = new ArrayList<>();
		
		try {
			String entry = "";
			environment.write(environment.getPromptSymbol() + " ");
			entry = environment.readLine().trim();
			
			if (entry.isEmpty()){
				return null;
			}
			
			String[] splitted = entry.split("\\s+"); 
			int lastElem = splitted.length - 1;
			
			// read data while last character is morelines character 
			while (splitted[lastElem].endsWith(String.valueOf(environment.getMorelinesSymbol()))){
				int index = entry.lastIndexOf(environment.getMorelinesSymbol());
				if (index != 0){
					entry = entry.substring(0, index);
					result.add(entry);
				}
	
				environment.write(environment.getMultilineSymbol() + " ");
				entry = environment.readLine().trim();
				if (entry.isEmpty()){
					break;
				}
				
				splitted = entry.split("\\s+"); 
				lastElem = splitted.length - 1;
			}
			
			result.add(entry);
			
		} catch (IOException e){
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
		return result;
	}
	


    /**
     * Implementation of {@link Environment} interface
     * 
     * @author Stjepan
     *
     */
    private static class EnviromentImpl implements Environment {
    	private final BufferedWriter os;
    	private final BufferedReader is;
    	
    	private final Map<String, Character> envCommands ;
    	
		public EnviromentImpl(InputStream is, PrintStream os) {
			if (is == null || os == null) {
				throw new IllegalArgumentException("Stream must not be null. ");
			}
			this.os = new BufferedWriter(new OutputStreamWriter(os, 
								StandardCharsets.UTF_8));
			
			this.is = new BufferedReader(new InputStreamReader(is, 
										StandardCharsets.UTF_8));
			
			envCommands = new HashMap<>(4);
			envCommands.put("MORELINES", '\\');
			envCommands.put("PROMPT", '>');
			envCommands.put("MULTILINE", '|');
			
		}

		@Override
		public String readLine() throws IOException {
			return is.readLine();
		}

		@Override
		public void write(String text) throws IOException {
			os.write(text);
			os.flush();
		}

		@Override
		public void writeln(String text) throws IOException {
			os.write(text + "\n");
			os.flush();
		}

		@Override
		public Iterable<ShellCommand> commands() {
			return commands.values();
		}

		@Override
		public Character getMultilineSymbol() {
			return envCommands.get("MULTILINE");
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			envCommands.put("MULTILINE", symbol) ;
			
		}

		@Override
		public Character getPromptSymbol() {
			return envCommands.get("PROMPT");
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			envCommands.put("PROMPT", symbol);
			
		}

		@Override
		public Character getMorelinesSymbol() {
			return envCommands.get("MORELINES");
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			envCommands.put("MORELINES", symbol);
		}
		
		public Map<String, Character> getSymbols(){
			return envCommands;
		}
		
		public void writeSymbols(){			
			getSymbols().forEach((k,v) -> { try { 
				writeln("Symbol for " + k + " is '" + v + "'");
			} catch (IOException e) {
				System.err.println(e.getMessage());
				System.exit(6);
			}; });
		}
    	
    }
    
    /**
     * Method creates string from byte array.
     * 
     * @param result byte array to convert to string
     * @return string value of byte array
     */
    public static String fromByteToHexString(byte[] result){
		StringBuffer builder= new StringBuffer();
		
        for (int i = 0; i < result.length; i++) {
        	builder.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        return builder.toString();
	}
}
