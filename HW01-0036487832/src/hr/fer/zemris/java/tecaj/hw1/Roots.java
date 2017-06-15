/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw1;

/**
 * @author Stjepan
 * Class that provides calculation of complex number root
 */
public class Roots {

	/**
	 * Putting all roots values on System.out
	 * @param args three arguments needed, real part, imaginary part and which root
	 */
	public static void main(String[] args) {
		if ( args.length != 3){
			System.err.println("Three arguments expected.");
			return;
		}
		
		double realPart = Double.parseDouble(args[0]);
		double imagPart = Double.parseDouble(args[1]);
		int root = Integer.parseInt(args[2]);
		
		calculateRoots(realPart, imagPart, root);
	}

	
	/**
	 * Method does calculation of n-th root of complex number and prints 
	 * all solutions on System.out
	 * @param realPart double value of first argument
	 * @param imagPart double value of second argument
	 * @param root integer value of last argument
	 */
	private static void calculateRoots(double realPart, double imagPart, int root) {
		double radius = Math.sqrt (Math.pow(realPart,2) + Math.pow(imagPart, 2));
		double fi = Math.atan(imagPart/realPart);
		double re, im;
		
		radius = Math.pow(radius, (1.0/root));
		
		for (int i = 1; i <= root; i++){
			re = radius * Math.cos((2*i*Math.PI + fi)/root);
			im = radius * Math.sin((2*i*Math.PI + fi)/root);
			String real, imag = "";
			if (re < 0)
				real = String.format("%03f ",re);
			else
				real = String.format("%03f ",re);
			if (im < 0)
				imag = String.format("%03f i",im);
			else
				imag = String.format("+ %03f i",im);
			
			System.out.println(real + "" + imag);
		}
	}

}
