package hr.fer.zemris.java.complex;

import java.util.ArrayList;
import java.util.List;

/**
 * Class <code>Complex</code> represents immutable complex number.
 * It provides some basic operations over complex numbers.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class Complex {
	/**
	 * Real part of complex number.
	 */
	private final double real;
	/**
	 * Imaginary part of complex number
	 */
	private final double imag;
	/**
	 * Constant <code>Complex</code> which has value 0.
	 */
	public static final Complex ZERO = new Complex(0,0);
	/**
	 * Constant <code>Complex</code> which has real value one, and does not have imaginary value.
	 */
	public static final Complex ONE = new Complex(1,0);
	/**
	 * Constant <code>Complex</code> which has real value minus one, and does not have imaginary value.
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);
	/**
	 * Constant <code>Complex</code> which has imaginary value one, and does not have real value.
	 */
	public static final Complex IM = new Complex(0,1);
	/**
	 * Constant <code>Complex</code> which has imaginary value minus one, and does not have real value.
	 */
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * Constructor which delegates to {@link #Complex(double, double)} with both argument of value equals to zero.
	 */
	public Complex() {
		this(0,0);
	}
	
	/**
	 * Constructor which takes two arguments: real value and imaginary value of complex number.
	 * 
	 * @param real real value
	 * @param imag imaginary value
	 */
	public Complex(double real, double imag) {
		this.real = real;
        this.imag = imag;
	}
	
	/**
	 * Returns module of complex number
	 * 
	 * @return module of complex number
	 */
	public double module() {
		return Math.hypot(this.real, this.imag);
	}
	
	
	/**
	 * Returns <code>this * c</code>
	 * 
	 * @param c {@code Complex} to multiply with
	 * @return result of multiply operation
	 * @throws IllegalArgumentException if argument is null
	 */
	public Complex multiply(Complex c) {
		if (c == null){
			throw new IllegalArgumentException("Cannot multiply with null.");
		}
		
		return new Complex( this.real * c.real - this.imag * c.imag, 
							this.real * c.imag + this.imag * c.real);
	}
	
	
	/**
	 * Returns <code>this / c</code>
	 * 
	 * @param c {@code Complex} to divide with
	 * @return result of divide operation
	 * @throws IllegalArgumentException if argument is null
	 */
	public Complex divide(Complex c) {
		if (c == null){
			throw new IllegalArgumentException("Cannot divide with null.");
		}
		
		double div;
		if ((div = Math.pow(c.real, 2) + Math.pow(c.imag, 2)) == 0){
			return new Complex();
		}
		
		return new Complex( (this.real * c.real + this.imag * c.imag) / div, 
							(this.imag * c.real - this.real * c.imag) / div);                                          
	}
	
	
	/**
	 * Returns <code>this + c</code>
	 * 
	 * @param c {@code Complex} to add 
	 * @return result of add operation
	 * @throws IllegalArgumentException if argument is null
	 */
	public Complex add(Complex c) {
		if (c == null){
			throw new IllegalArgumentException("Cannot multiply with null.");
		}
		
		return new Complex(this.real + c.real, this.imag + c.imag);
	}
	
	
	/**
	 * Returns <code>this - c</code>
	 * 
	 * @param c {@code Complex} to subtract with
	 * @return result of divide operation
	 * @throws IllegalArgumentException if argument is null
	 */
	public Complex sub(Complex c) {
		if (c == null){
			throw new IllegalArgumentException("Cannot multiply with null.");
		}
		
		return new Complex(this.real - c.real, this.imag - c.imag);
	}
	
	
	/**
	 * Returns new {@linkplain Complex} of value <code>-this</code>
	 * 
	 * @return negated <code>this</code>
	 */
	public Complex negate() {
		return new Complex(-1 * this.real, -1 * this.imag);
	}
	
	
	/**
	 * Returns <code>this^n</code>, <code>n</code> is non-negative integer
	 * 
	 * @param n non negative integer
	 * @return <code>this^n</code>
	 */
	public Complex power(int n) {
		if (n < 0){
			throw new IllegalArgumentException("Argument for power must not be less than zero.");
		}
		
        double angle = Math.atan2(imag, real);
        double magn = Math.pow(module(), n);
        if (angle < 0){
        	angle = angle + 2 * Math.PI;
        }        
		
		return new Complex(	magn * (Math.cos(n * angle)), 
							magn * (Math.sin(n * angle)));
	}
	
	
	/**
	 * Returns n-th root of this, <code>n</code>  is positive integer
	 * 
	 * @param n positive integer
	 * @return n-th root of this
	 */
	public List<Complex> root(int n) {
		if (n <= 0){
			throw new IllegalArgumentException("Argument for root must be greater than zero.");
		}
		
		List<Complex> roots = new ArrayList<>(n);
		double angle = Math.atan2(imag, real);
	    double magn = Math.pow(module(), 1.0d / n);
	    for (int i = 0; i < n ; i++){
	    	roots.add( new Complex( magn * Math.cos((angle + 2 * i * Math.PI) / n),
	    							magn * Math.sin((angle + 2 * i * Math.PI) / n)));
	    }
	    
	    return roots;	    
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(real);
		if (imag >= 0) {
			sb.append("+");
		}
		sb.append(imag).append(imag);
		
		return sb.toString();		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imag);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Complex other = (Complex) obj;
		if (Double.doubleToLongBits(imag) != Double.doubleToLongBits(other.imag))
			return false;
		if (Double.doubleToLongBits(real) != Double.doubleToLongBits(other.real))
			return false;
		return true;
	}

	
}
