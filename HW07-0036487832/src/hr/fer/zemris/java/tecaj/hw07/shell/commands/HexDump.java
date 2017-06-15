package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Represents hexdump operation of shell
 * 
 * @author Stjepan
 *
 */
public class HexDump implements ShellCommand {

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
	public HexDump(String name, String description) {
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
			System.err.println("Path cannot be null.");
			return ShellStatus.CONTINUE;
		}
		
		try{
			byte[] bytes = read(arguments, 0, 4048);
			if (bytes == null){
				env.writeln("Cannot hexdump. ");
				
				return ShellStatus.CONTINUE;
			}
			for (int index = 0; index < bytes.length; index += 16) {
				printHex(bytes, index, 16, env);
				printAscii(bytes, index, 16, env);
			}
		} catch (Exception e){
			try {
				env.writeln("Cannot hexdump. ");
			} catch (IOException e1) {
				//ignorable
			}
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
	
	/**
	 * Reads from file
	 * 
	 * @param inputFileName name of file
	 * @param start where to start
	 * @param end where to end
	 * @return content of file as byte array
	 */
	private static byte[] read(String inputFileName, int start, int end) {
		try {
			File theFile = new File(inputFileName);
			FileInputStream input = new FileInputStream(theFile);
			int skipped = 0;
			
			while (skipped < start) {
				skipped += input.skip(start - skipped);
			}
			
			int length = (int) (Math.min(end, theFile.length()) - start);
			byte[] bytes = new byte[length];
			int bytesRead = 0;
			while (bytesRead < bytes.length) {
				bytesRead = input.read(bytes, bytesRead, bytes.length - bytesRead);
				if (bytesRead == -1) {
					break;
				}
			}
			input.close();
			return bytes;
		}  catch (Exception e){ //ignorable
			return null;
		}
	}
	
	/**
	 * Prints hexa values to environments.
	 * 
	 * @param bytes
	 * @param offset
	 * @param width
	 * @param en
	 */
	private static void printHex(byte[] bytes, int offset, int width, Environment en) {
		try{
			for (int index = 0; index < width; index++) {
				if (index + offset < bytes.length) {
					en.write(String.format("%02x ", bytes[index + offset]));
					
				} else {
					en.write("	");
				}
			}
		}catch (Exception ignorable){
			return;
		}
	}
	
	/**
	 * Prints ascii values to environment.
	 * 
	 * @param bytes
	 * @param index
	 * @param width
	 * @throws UnsupportedEncodingException
	 */
	private static void printAscii(byte[] bytes, int index, int width, Environment en){
		try {
			if (index < bytes.length) {
				width = Math.min(width, bytes.length - index);
				en.writeln(
					":"
						+ new String(bytes, index, width, "UTF-8").replaceAll("\r\n", ".").replaceAll(
							"\n",
							"."));
			} else {
				en.writeln("");
			}
		} catch(Exception ignorable){
			return;
		}
	}

	
	
}
