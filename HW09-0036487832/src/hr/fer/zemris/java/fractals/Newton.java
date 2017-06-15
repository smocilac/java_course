package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.complex.Complex;
import hr.fer.zemris.java.complex.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;

/**
 * Program takes no argument. 
 * It calculates Newton-Raphson iteration and draws image on GUI viewer. 
 * 
 * <p>When started, program expects user to input all roots of type complex
 * Syntax for complex number is <code>a + ib</code> 
 * Empty input will not be treated as zero and is not allowed.
 * Inputs such as <code> "0", "i", "2-i", "-i"</code> are allowed.  
 * 
 * 
 * @author Stjepan
 * @version 1.0
 */
public class Newton {
	/**
	 * Scanner which reads from console.
	 */
	public static final Scanner scanner = new Scanner(System.in);

	/**
	 * Program which takes no arguments.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
			
        Complex[] usersRoots = getRoots();
        
        System.out.format("Image of fractal will appear shortly. Thank you. %n");
        
        ComplexRootedPolynomial polynomial = new ComplexRootedPolynomial(usersRoots);
        IFractalProducer producer = new IFractalProducerImpl(polynomial);
        
        FractalViewer.show(producer);
	}

	/**
	 * Parses input and stores all provided roots to list of {@linkplain Complex}
	 * 
	 * @return all roots
	 */
	private static Complex[] getRoots() {
		System.out.format("Welcome to Newton-Raphson iteration-based fractal viewer.%n" + 
				"Please enter at least two roots, one root per line. Enter 'done' when done.%n");
		
		final String done = "DONE";
		List<Complex> list = new ArrayList<>();
		
		for (int i = 1; ; i++){			
			System.out.format("Root %d > ", i);
			String input = scanner.nextLine();
			
			// if user entered 'DONE' finish
			if (done.equals((input = input.trim().replaceAll("\\s+", "")).toUpperCase())){
				break;
			}
			
			if (input.isEmpty()){
				System.out.println("Input was empty. ");
				i--;
				continue;
			}
			
			// real part, imaginary part, valid if positive
			double[] reImVal = parseInput(input);
			
			if (reImVal[2] < 0){
				System.out.println("Invalid input. ");
				i--;
				continue;
			}
			list.add(new Complex(reImVal[0], reImVal[1]));				
		}
		
		Complex [] compl  = new Complex[list.size()];
		
		return list.toArray(compl);
	}

	/**
	 * Method to parse complex number. 
	 * General syntax is <code>a + ib</code>
	 * 
	 * @param input input with no whitespaces
	 * @return parsed input
	 */
	private static double[] parseInput(String input) {
		double result [] = new double[3];
		result[2] = 5;
		if (input.endsWith("i")){
			input = input.concat("1");
		}
	    //real and imaginary parts.  
	    Pattern both = Pattern.compile("([-]?[0-9]+\\.?[0-9]?)([-|+]+i[0-9]+\\.?[0-9]?)+");
	    //real number
	    Pattern real = Pattern.compile("([-]?[0-9]+\\.?[0-9]?)");
	    //imaginary number
	    Pattern imag = Pattern.compile("([-]?i[0-9]+\\.?[0-9]?|[-]?i)");
	    
	    // check if matches
	    Matcher isBoth = both.matcher(input);
	    Matcher isReal = real.matcher(input);
	    Matcher isImag = imag.matcher(input);

	    if (isBoth.matches()) { // if full format
	    	result[0] = Double.parseDouble(isBoth.group(1));
	    	result[1] = Double.parseDouble(isBoth.group(2).replace("i", ""));
	    } else if (isReal.matches()) { // if only real provided
	    	result[0] = Double.parseDouble(isReal.group(1));
	    	result[1] = 0.0d;
	    } else if (isImag.matches()) { // if only imag provided
	    	result[0] = 0.0d;
	    	String im = isImag.group(1).replace("i", "");
	    	if (im.isEmpty() || im.equals("-")){
	    		im = im.concat("1");
	    	}
	    	result[1] = Double.parseDouble(im);
	    } else {
	    	result[2] = -1.0d;
	    }
	    
	    return result;
	}

}
