/**
 * 
 */
package hr.fer.zemris.java.graphics;

import java.util.NoSuchElementException;
import java.util.Scanner;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.Circle;
import hr.fer.zemris.java.graphics.shapes.Ellipse;
import hr.fer.zemris.java.graphics.shapes.GeometricShape;
import hr.fer.zemris.java.graphics.shapes.Rectangle;
import hr.fer.zemris.java.graphics.shapes.Square;
import hr.fer.zemris.java.graphics.views.RasterView;
import hr.fer.zemris.java.graphics.views.SimpleRasterView;

/**
 * Program expects user to provide either a single argument or two arguments. In case 
 * that user provides a single argument, its value is interpreted as width and height 
 * of raster. In case that user provides two arguments, first is treated as width of 
 * raster and second as height of raster. 
 * 
 * In case there are zero arguments or more than two arguments program will write 
 * appropriate message end terminate. 
 * 
 * In case arguments can not be interpreted as numbers (or are inappropriate), again 
 * writes a message and terminate program.
 * 
 * @author Stjepan
 * @version 1.0
 *
 */
public class Demo {
	/** all elements of shapes from input */
	private static GeometricShape[] elements;
	
	/** rasters width */ 
	private static int width;
	
	/** rasters height */
	private static int height;

	/**
	 * Main method which takes either one ( width ) or two ( width and height ) arguments.
	 * 
	 * If number of argument is not correct, or arguments cannot be parsed to integer, 
	 * terminating program.
	 * 
	 * @param args width and/or height of raster
	 */
	public static void main(String[] args) {
		if (args.length == 1) {
			try  {
				width = height = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.err.println("First argument must be integer.");
				System.exit(6);
			}
		} else if (args.length == 2) {
			try  {
				width = Integer.parseInt(args[0]);
				height = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				System.err.println("Both arguments must be integer.");
				System.exit(6);
			}
		} else {
			System.err.println("Wrong argument number. ");
			System.exit(1);
		}
		BWRaster raster = null;
		
		try {
			raster = new BWRasterMem(width, height);
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			System.exit(7);
		}
		
		Scanner scanner = new Scanner(System.in);
		int numberOfElements = 0;
		
		// reads first line - number of elements to read
		try {
			numberOfElements = Integer.parseInt(scanner.nextLine());
		}catch (NumberFormatException e) {
			System.err.println("First argument must be integer greater than zero,"
					+ " terminating...");
			System.exit(2);
		}catch (NoSuchElementException e){
			System.err.println("No line to read found. ");
			System.exit(3);
		}
		
		elements = new GeometricShape[numberOfElements];
		
		for ( int i = 0; i < numberOfElements; i++) {
			if (scanner.hasNext()) {
				String str = scanner.next();
				addGeometricShape(i, str, scanner);
			} else {
				System.err.println("Wrong input data. ");
				System.exit(4);
			}
		}
		
		// all shapes are filled now
		
		for (int i = 0; i < numberOfElements; i++) {
			if (elements[i] == null) {
				raster.changeFlip();
			} else {
				elements[i].draw(raster);
			}
		}
		
		RasterView view = new SimpleRasterView();
		view.produce(raster);
		
	}
	
	
	/**
	 * Adds geometric shape from input to array of shapes.
	 * Checks how many argument is expected and then takes token by token while checks
	 * if token is valid.
	 * 
	 * @param index index of next empty position to add shape
	 * @param str name of shape
	 * @param scanner scanner to read input from
	 */
	private static void addGeometricShape(int index, String str, Scanner scanner) {
		int argNum = howManyArgumentToExpect(str);
		
		if (argNum == -1) {
			System.err.println("Unrecognized key word ! ");
			System.exit(5);
		}
		
		int shapeArguments[] = new int [argNum];
		
		for (int i = 0; i < argNum; i++) {
			if (scanner.hasNext()) {
				String next = scanner.next();
				
				try {
					shapeArguments[i] = Integer.parseInt(next);
				} catch (NumberFormatException e) {
					System.err.println(str + " requaries " + argNum + " integer arguments.");
					System.exit(6);
				}
				
			} else {
				System.err.println("Wrong input data. ");
				System.exit(4);
			}
		}
		
		makeNewShape(index, str, shapeArguments);
	}
	
	/**
	 * Check how many arguments does shape request. 
	 * 
	 * If shape is not recognized returns -1.
	 * 
	 * @param str name of shape
	 * @return number of argument for shape.
	 */
	private static int howManyArgumentToExpect(String str) {
		if (str.toUpperCase().equals("RECTANGLE")
				|| str.toUpperCase().equals("ELLIPSE")) return 4;
		
		if (str.toUpperCase().equals("CIRCLE")
				|| str.toUpperCase().equals("SQUARE")) return 3;
		
		if (str.toUpperCase().equals("FLIP")) return 0;
		return -1;
	}
	
	/**
	 * Makes new shape depending of users request.
	 * 
	 * @param index index of element to add shape
	 * @param str name of shape
	 * @param args arguments of shape
	 */
	private static void makeNewShape(int index, String str, int[] args) {
		try {
			if (str.toUpperCase().equals("RECTANGLE")){
				elements[index] = new Rectangle (args[0], args[1], args[2], args[3]);
			} else if (str.toUpperCase().equals("ELLIPSE")){
				elements[index] = new Ellipse (args[0], args[1], args[2], args[3]);
			} else if (str.toUpperCase().equals("SQUARE")){
				elements[index] = new Square (args[0], args[1], args[2]);
			} else if (str.toUpperCase().equals("CIRCLE")){
				elements[index] = new Circle (args[0], args[1], args[2]);
			} else {
				elements[index] = null;
			}
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			System.exit(7);
		}
	}
	


}
