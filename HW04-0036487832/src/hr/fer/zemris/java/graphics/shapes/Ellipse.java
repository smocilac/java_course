/**
 * 
 */
package hr.fer.zemris.java.graphics.shapes;

/**
 * Ellipse represents geometric shape which is specified by its center and 
 * horizontal and vertical radius.
 * 
 * 
 * @author Stjepan
 * @version 1.0
 *
 */
public class Ellipse extends GeometricShape {
	
	/** x coordinate of center */
	private int x;
	
	/** y coordinate of center */
	private int y;
	
	/** Horizontal radius of ellipse */
	private int horizontalRadius;
	
	/** vertical radius of ellipse */
	private int verticalRadius;

	/**
	 * Public constructor which takes 4 arguments : x coordinate of center, y coordinate of 
	 * center, horizontal and vertical radius.
	 * 
	 * Radius is valid if is greater than zero.
	 * 
	 * @param x {@link #x}
	 * @param y {@link #y}
	 * @param horizontalRadius {@link #horizontalRadius}
	 * @param verticalRadius {@link #verticalRadius}
	 * 
	 * @throws IllegalArgumentException 
	 * 						if radius is less than 1
	 */
	public Ellipse(int x, int y, int horizontalRadius, int verticalRadius) {
		if (horizontalRadius < 1 || verticalRadius < 1) {
			throw new IllegalArgumentException("Radius must be greater than zero");
		}
		
		this.x = x;
		this.y = y;
		this.horizontalRadius = horizontalRadius;
		this.verticalRadius = verticalRadius;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.shapes.PointerContainer#containsPoint(int, int)
	 */
	@Override
	public boolean containsPoint(int x, int y) {
		if ( x < x - horizontalRadius) return false;
		if ( x > x + horizontalRadius) return false;
		if ( y < y - verticalRadius) return false;
		if ( y > y + verticalRadius) return false;
		
		int px = (x - this.x); 
		px *= px;
		
		int py = (y - this.y);
		py *= py;
		
		int hor = horizontalRadius * horizontalRadius;
		int ver = verticalRadius * verticalRadius;
		
		return ver*px + hor*py < hor*ver;
	}
	
	/**
	 * Gets and returns x coordinate of center.
	 * 
	 * @return {@link #x}
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Sets {@link #x} to value from argument .
	 * 
	 * @param x new x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Gets and returns y coordinate of center.
	 * 
	 * @return {@link #y}
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets {@link #y} to value from argument.
	 * 
	 * @param y new value of y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}
	
}
