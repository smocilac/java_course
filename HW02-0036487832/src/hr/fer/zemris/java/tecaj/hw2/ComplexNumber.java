/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw2;

/**
 * Complex number class that allows manipulation on imaginary numbers. 
 * ComplexNumber represents an unmodifiable complex number.
 * 
 * @author Stjepan
 */
public class ComplexNumber {
	/**
	 * Real part of complex number, constant
	 */
	private final double real;   // the real part
	
	/**
	 * Imaginary part of complex number, constant
	 */
    private final double imaginary;   // the imaginary part
	
    /**
     * Constructs new unmodifiable complex number
     * 
     * @param real real part of complex number
     * @param imaginary imaginary part of complex number
     */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Provides creating new complex number without imaginary part.
	 * 
	 * @param real real part of complex number
	 * @return new complex number with real part only
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0.0);
	}
	
	/**
	 * Provides creating new complex number without real part.
	 * 
	 * @param imaginary imaginary part of complex number
	 * @return new complex number with imaginary part only
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0.0, imaginary);
	}
	
	/**
	 * Provides creating new complex number with magnitude and angle parameters 
	 * only. Storage is in additional way.
	 * 
	 * @param magnitude magnitude of complex number
	 * @param angle angle of complex number
	 * @return new complex number with parameters from argument
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), 
				magnitude * Math.sin(angle));
	}
	
	/**
	 * Makes new object of class ComplexNumber depending on argument ( complex 
	 * number as a String).
	 * 
	 * @param s complex number as string 
	 * @return new ComplexNumber object matching the expression in argument
	 * @throws NumberFormatException if argument is not parsable
	 */
	public static ComplexNumber parse(String s){
		String [] str;
		double im = 0.0, re = 0.0;
		if (s.contains("+")) {
			str = s.split("\\+");
			if (str.length == 2) {
				im = trimImag(str[1], true);
				re = Double.parseDouble(str[0]);
			}
		} else {
			if (s.contains("-")) {
				str = s.split("-");
			} else {
				str = new String[1];
				str[0] = s;
			}
			
			if (str.length == 1 || (str.length == 2 && s.startsWith("-"))) {
				if (s.contains("i")) {
					String str2 = s;
					if (s.startsWith("-")) {
						str2 = s.substring(1);
					}
					im = trimImag(str2, s.startsWith("-") ? false : true);
				} else {
					return new ComplexNumber(Double.parseDouble(s), 0.0);
				}
			} else if (str.length == 2) {
				im = trimImag(str[1], false);
				try {
					re = Double.parseDouble(str[0]);
				} catch (NumberFormatException e) {
					re = 0.0d;
				}
			} else if (str.length == 3) {
				im = trimImag(str[2], false);
				re = Double.parseDouble(str[1]) * (-1);
			} else {
				throw new NumberFormatException("Cannot parse String to complex.");
			}
		}
		return new ComplexNumber(re, im);
	}
	
	/**
	 * Takes parsable String without negative sign, instead isPositive is defined as
	 * second argument.
	 * 
	 * @param toTrim String to convert into double imaginary value
	 * @param isPositive says if new double needs to be positive or not
	 * @return double value of imaginary part
	 */
	private static double trimImag(String toTrim, boolean isPositive) {
		String [] finalString = toTrim.split("i");	
		if (finalString.length == 0 || (finalString[0].startsWith("-") && finalString[0].length() == 1)) {
			return isPositive ? 1.0d : -1.0d;
		}
		return finalString[0].isEmpty() ? 
				(isPositive ? 1.0 : -1.0) : 
					( isPositive ? Double.parseDouble(finalString[0]) : 
						((-1.0) * Double.parseDouble(finalString[0])));
	}
	
	/**
	 * Returns real value of complex number as a double value.
	 * 
	 * @return real part of complex numbers
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Gets and returns value of imaginary part
	 * 
	 * @return imaginary value of complex number
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Calculates and returns magnitude of complex number.
	 * 
	 * @return magnitude of complex number
	 */
	public double getMagnitude() {
		return imaginary != 0.0 || real != 0.0 ? 
				Math.sqrt(Math.pow(real,2) + Math.pow(imaginary,2)) : 0.0d;
	}
	
	/**
	 * Calculates and returns angle in radians.
	 * 
	 * @return angle of complex number
	 */
	public double getAngle() {
		return Math.atan2(imaginary, real) + Math.PI;
	}
	
	/**
	 * Adding current complex number to the one from argument. Current complex or
	 * the from argument is not changed after this method, instead new complex is 
	 * created.
	 * 
	 * @param c complex number to add with current one
	 * @return new complex number as a result of adding
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber (this.real + c.getReal(),
				this.imaginary + c.getImaginary());
	}
	
	/**
	 * Subtract current complex number with the one from argument. Current complex 
	 * or the from argument is not changed after this method, instead new complex is 
	 * created.
	 * 
	 * @param c complex number to subtract with current one
	 * @return new complex number as a result of subtraction
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber (this.real - c.getReal(),
				this.imaginary - c.getImaginary());
	}
	
	/**
	 * Multiply current complex number with the one from argument. Current complex 
	 * or the from argument is not changed after this method, instead new complex is 
	 * created.
	 * 
	 * @param c complex number to multiply with current one
	 * @return new complex number as a result of multiplying
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(real*c.getReal()-imaginary*c.getImaginary(),
				real*c.getImaginary()+imaginary*c.getReal());
	}
	
	/**
	 * Divide current complex number with the one from argument. Current complex 
	 * or the from argument is not changed after this method, instead new complex is 
	 * created.
	 * 
	 * @param c complex number to divide with current one
	 * @return new complex number as a result of divide
	 */
	public ComplexNumber div(ComplexNumber c) {
		double den=Math.pow(c.getMagnitude(),2);
		return new ComplexNumber((real * c.getReal() + imaginary * c.getImaginary())/den, 
				(imaginary * c.getReal() - real * c.getImaginary())/den);
	}
	
	/**
	 * Powering square root (doesn't change this complex number).
	 * 
	 * @param n must be zero or positive
	 * @return {@code pow(z, n);} where z is this Complex number.
	 */
	public ComplexNumber power(int n) { // n >= 0
		if ( n < 0) {
			throw new IllegalArgumentException("N must be positive or zero for "
					+ " power calculation of complex number! ");
		}
		double pow = Math.pow (this.getMagnitude(), n);
		double re = pow * Math.cos(this.getAngle() * n);
		double im = pow * Math.sin(this.getAngle() * n);
		
		return new ComplexNumber ( re, im);
	}
	
	/**
	 * Complex square root (doesn't change this complex number). Computes the 
	 * principal branch of the square root, which is the value with 0 to PI (PI not
	 * included)
	 * @param n must be positive, n-th root
	 * @return sqrt(z) where z is this Complex number.
	 */
	public ComplexNumber[] root(int n) { // n > 0
		if ( n <= 0) {
			throw new IllegalArgumentException("N must be positive for "
					+ "root calculation of complex number! ");
		}
		ComplexNumber [] complexNumbers = new ComplexNumber[n];
		double radius = this.getMagnitude();
		double fi = this.getAngle();
		double re, im;
		
		radius = Math.pow(radius, (1.0d/n));
		
		for (int i = 0; i < n; i++){
			re = radius * Math.cos((2*i*Math.PI + fi)/n);
			im = radius * Math.sin((2*i*Math.PI + fi)/n);
			complexNumbers[i] = new ComplexNumber(re, im);
		}
		return complexNumbers;
	}

	@Override
	public String toString() {
		String firstSign = real < 0 ? "-" : "";
		String secondSign = imaginary < 0 ? "-" : "+";
		if (imaginary == 0.0 && real == 0.0) {
			return "0.0";
		}else if (imaginary == 0.0) {
			return String.format("%s %f", firstSign, real);
		} else if ( real == 0.0 ) {
			return String.format("%s %fi", secondSign, imaginary);
		}
		
		return String.format("%s %f %s %fi", firstSign, Math.abs(real), secondSign, Math.abs(imaginary));
	}
	
}
