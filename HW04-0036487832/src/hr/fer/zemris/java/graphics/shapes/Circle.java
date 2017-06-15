/**
 * 
 */
package hr.fer.zemris.java.graphics.shapes;

/**
 * Represents circle and inherits {@link GeometricShape}
 * 
 * Circles with radius smaller than 1 are not allowed. 
 * 
 * Circle with radius 1 would be rendered as a single turned-on pixel in circle's center. 
 * 
 * Circle with radius 2 would be rendered to with one additional pixel to the right and 
 * left (and top and bottom).
 * 
 * @author Stjepan
 * @version 1.0
 *
 */
public class Circle extends GeometricShape {
	/** x coordinate of center */
	private int x;
	
	/** y coordinate of center */
	private int y;
	
	/** radius of circle */
	private int r;
	
	
	/**
	 * Public constructor with three arguments: x coordinate of center, y coordinate of 
	 * center and radius of circle.
	 * 
	 * @param x {@link #x}
	 * @param y {@link #y}
	 * @param radius {@link #r}
	 * 
	 * @throws IllegalArgumentException If radius is less than 1
	 */
	public Circle(int x, int y, int radius) {
		if (radius < 1) {
			throw new IllegalArgumentException("Radius must be greater than zero.");
		}
		
		this.x = x;
		this.y = y;
		this.r = radius;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.shapes.PointerContainer#containsPoint(int, int)
	 */
	@Override
	public boolean containsPoint(int x, int y) {
		int dx = x - getX();
        int dy = y - getY();
        return dx*dx + dy*dy <= r*r;
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
	
	/**
	 * Gets and returns radius of circle.
	 * 
	 * @return {@link #r} radius
	 */
	public int getR() {
		return r;
	}

	/**
	 * Sets {@link #r} to value from argument.
	 * 
	 * @param r new value of radius
	 */
	public void setR(int r) {
		this.r = r;
	}
	
	

}
