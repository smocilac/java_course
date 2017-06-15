/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
/**
 * 
 * Class provides information of rectangle depending on input of rectangle parameters
 * @author Stjepan
 */
public class Rectangle {
	private static double width;
	private static double height;
	
	/**
	 * Rectangle information input (width and height).
	 * Writes width, height, area and perimeter of rectangle.
	 * @param args represents width and height of rectangle
	 * 
	 */
	public static void main(String[] args) throws IOException{
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in))
				);
		
		if(args.length == 0){
			width = checkAndParseInput(reader, "width");
			height = checkAndParseInput(reader, "height");
		}
		else if (args.length == 2){
			if ((width = Double.parseDouble(args[0])) < 0 ||
					(height = Double.parseDouble(args[1])) < 0){
				System.err.println("Arguments must be positive numbers.");
				return;
			}
		}
		else {
			System.err.println("Invalid number of arguments was provided.");
			return;
		}
		
		System.out.print("You have specified a rectangle with width " + width + " ");
		System.out.print("and height " + height + ". Its area is " + (width*height) + " ");
		System.out.print("and its perimeter is " + ((2*height)+(2*width)) + ".\n");
		
	}
	
	/**
	 *  Checks if input data double value is less then 0 and returns it if not less.
	 * @param reader defines where from data needs to be taken.
	 * @param message name of parameter that needs to be written in.
	 * @return value of parameter
	 * @throws IOException is number not checked
	 */
	private static double checkAndParseInput (BufferedReader reader, String message)
			throws IOException{
		double retVal=0.0;
		while(true){
			System.out.print("please provide " + message + ": ");
			String redak = (reader.readLine());
			
			if ( redak != null)
				redak = redak.trim();
			else
				return -1;
			
			if (redak.isEmpty()) {
				System.out.println("nothing was given.");
				continue;
			}
			
			if ((retVal = Double.parseDouble(redak)) > 0)
				break;
			System.out.println(message + " is negative.");
		}
		return retVal;
	}

}
