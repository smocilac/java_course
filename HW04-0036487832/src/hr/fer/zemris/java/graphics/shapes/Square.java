/**
 * 
 */
package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * It represents square specified by its x,y coordinates of its top-left corner and by 
 * its size).
 * 
 * Size of square must not be zero.
 * 
 * @author Stjepan
 * @version 1.0
 *
 */
public class Square extends GeometricShape {
	/** x coordinate of Square */
	private int x;
	
	/** y coordinate of Square */
	private int y;
	
	/** Size of Square */
	private int size;
	
	
	/**
	 * Public constructor which takes 3 arguments: x coordinate, y coordinate and size
	 * of square.
	 * 
	 * 
	 * @param x {@link #x}
	 * @param y {@link #y}
	 * @param size {@link #size}
	 * 
	 * @throws IllegalArgumentException if width or height in argument is zero or less.
	 */
	public Square(int x, int y, int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Size must not be zero or less.");
		}
		this.x = x;
		this.y = y;
		this.size = size;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.shapes.PointerContainer#containsPoint(int, int)
	 */
	@Override
	public boolean containsPoint(int x, int y) {
		if (x < this.x) return false;
		if (y < this.y) return false;
		if (x >= this.x + size) return false;
		if (y >= this.y + size) return false;
		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.shapes.GeometricShape#draw(BWRaster)
	 */
	@Override
	public void draw(BWRaster raster) {
		int ymax = this.y + this.size < raster.getHeight() ? 
									this.y + this.size : raster.getHeight();
		
		int xmax = this.x + this.size < raster.getWidth() ? 
				this.x + this.size : raster.getWidth();
		
		int y = this.y < 0 ? 0 : this.y;

		for (; y < ymax; y++) {
			int x = this.x < 0 ? 0 : this.x;
			for (; x < xmax; x++) {
				raster.turnOn(x, y);
			}
		}
	}

	/**
	 * Gets and returns x coordinate.
	 * 
	 * @return {@link #x}
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets {@link #x} to value from argument.
	 * 
	 * @param x new x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets and returns y coordinate.
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
	 * Gets and returns size.
	 * 
	 * @return {@link #size}
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Sets {@link #size} to value from argument.
	 * 
	 * @param size new size
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	

}
