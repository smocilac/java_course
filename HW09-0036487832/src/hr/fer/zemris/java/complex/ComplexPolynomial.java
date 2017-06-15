package hr.fer.zemris.java.complex;

/**
 * Class which represents complex polynomial. 
 * Every instance is defined by array of {@linkplain Complex} factors.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class ComplexPolynomial {
	/**
	 * Factors of polynomial
	 */
	private final Complex[] factors;
	
	/**
	 * Constructor which takes one argument - array of {@linkplain Complex} factors.
	 * 
	 * @param factors factors of polynomial complex number
	 * @throws IllegalArgumentException if argument is null
	 */
	public ComplexPolynomial(Complex ...factors) {
		if (factors == null){
			throw new IllegalArgumentException("Null argument is not allowed when instance " + this.getClass());
		}
		
		this.factors = factors;
	}
	
	
	/**
	 * Returns order of this polynom; eg. For <code>(7+2i)z^3+2z^2+5z+1</code> returns 3
	 * 
	 * @returnorder of this polynom
	 */
	public short order() {
		return (short) factors.length;
	}
	
	
	/**
	 * Computes a new polynomial <code>this * p</code>
	 * 
	 * @param p complex polynomial to compute
	 * @return new complex <code>this * p</code>
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		if (p == null){
			throw new IllegalArgumentException("Cannot multiply with null. ");
		}
		
		Complex[] all = new Complex[ -1 + order() + p.order() ];
		for (int i = 0 ; i < order() ; i++){
			for (int j = 0; j < p.order(); j++){
				if (all[j + i] == null){
					all[j + i] = factors[i].multiply( p.factors[j] );
				} else {
					all[j + i] = all[j + i].add(factors[i].multiply( p.factors[j] ));
				}
			}
		}
		
		return new ComplexPolynomial(all);
		
	}
	
	
	/**
	 * Computes first derivative of this polynomial; for example, for
	 * <code>(7+2i)z^3+2z^2+5z+1</code> returns <code>(21+6i)z^2+4z+5</code>
	 * 
	 * @return
	 */
	public ComplexPolynomial derive() {
		int order ;
		if ((order = order()) == 1){
			return new ComplexPolynomial(Complex.ZERO);
		}
		
		Complex[] next = new Complex[order - 1];
		for (int j = 1 ; j <= next.length; j++){
			Complex compl = new Complex(j, 0);
			next[j - 1] = factors[j].multiply(compl);
		}
		
        return new ComplexPolynomial(next);
	}
	
	
	/**
	 * Computes polynomial value at given point z
	 * 
	 * @param z complex point
	 * @return value at point z
	 */
	public Complex apply(Complex z) {
		if (z == null){
			throw new IllegalArgumentException("Cannot apply with null argument.");
		}
		
		Complex compl = Complex.ZERO;
		int factL = order();
		
		for (int j = 0; j < factL; j++){
			Complex powered = z.power(j);
			Complex fin = factors[j].multiply(powered);
			compl = compl.add(fin);
		}
		
		return compl;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for (Complex fact : factors){
			if (fact.equals(Complex.ZERO)){
				continue;
			}
			builder.append(fact.toString());
		}
		
		return builder.toString();
	}
}
