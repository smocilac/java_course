package hr.fer.zemris.java.complex;

/**
 * Class which is similar to {@linkplain ComplexPolynomial}, only this class is defined by roots.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class ComplexRootedPolynomial {
	/**
	 * Roots of complex number
	 */
	private final Complex[] roots;
	
	/**
	 * Constructor with one argument - roots of complex number
	 * 
	 * @param roots roots of complex number
	 * @throws IllegalArgumentException if argument is null
	 */
	public ComplexRootedPolynomial(Complex ...roots) {
		if (roots == null) {
			throw new IllegalArgumentException("Null argument is not supported.");
		}
		
		this.roots = roots;
	}
	
	
	/**
	 * Computes polynomial value at given point z
	 * 
	 * @param z point 
	 * @return value at point z
	 */
	public Complex apply(Complex z) {
		if ( z == null ){
			throw new IllegalArgumentException("Cannot compute on null point.");
		} else if (roots.length == 0){
			return new Complex();
		}
		
		Complex ret = Complex.ONE; // (1,0)
        for(Complex compl : roots) {
        	Complex sub = z.sub(compl);
            ret = ret.multiply(sub);
        }
        
        return ret;
	}
	
	
	/**
	 * Converts this representation to {@linkplain ComplexPolynomial} type
	 * 
	 * @return {@linkplain ComplexPolynomial} type
	 */
	public ComplexPolynomial toComplexPolynom() {
		if (roots.length == 0) {
			return new ComplexPolynomial(Complex.ZERO);
		}
		
		ComplexPolynomial polynomial = new ComplexPolynomial(roots[0].negate(), Complex.ONE);
		for (int j = 1; j < roots.length; j++){
			Complex compl = roots[j].negate();
			polynomial = polynomial.multiply(new ComplexPolynomial(compl, Complex.ONE));
		}
		
		return polynomial;
	}
	
	
	/**
	 * Finds index of closest root for given complex number z that is within treshold.
	 * If there is no such root, returns -1
	 * 
	 * @param z complex number
	 * @param treshold treshold
	 * @return index of closest root, if no such index returns -1
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		if (z == null){
			throw new IllegalArgumentException("Cannot calculate closest root with null argument.");
		}
		
        int position = -1;
        double min = -1;
        int i = 0;
        for(Complex r : roots) {
            double now = (z.sub(r)).module();
            if ((min == -1 || (now < min)) && (now < treshold)){
            	position = i; 
            	min = now;
            }
            i++;
        }
        
        return position;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0 ; i < roots.length; i++){
			builder.append("z = " + roots[i].negate().toString());
		}
		
		return builder.toString();
	}
}
