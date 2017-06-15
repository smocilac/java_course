/**
 * 
 */
package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.tecaj.hw2.ComplexNumber;

/**
 * Class represents command-line application that takes no argument. Application 
 * illustrates usage and functionality of complex number.
 *  
 * @author Stjepan
 *
 */
public class ComplexDemo {

	/**
	 * Command-line application with no efficient argument.
	 * Demonstrates complex number usage.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("-2.2i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57))
				.div(c2).power(3).root(2)[1];
		System.out.println(c3);
		
//		ComplexNumber c1 = new ComplexNumber(2, 3);
//		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
//		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57));
//		ComplexNumber c4 = c3.div(c2);
//		ComplexNumber c5 = c4.power(3);
//		ComplexNumber c6 = c5.root(2)[1];
//		System.out.println(c6);
	}

}
