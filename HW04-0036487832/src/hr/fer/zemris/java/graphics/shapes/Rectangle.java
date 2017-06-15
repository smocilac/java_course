/**
 * 
 */
package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Rectangle is specified by its x,y coordinates of its top-left corner and by its width 
 * and height).
 * 
 * Some of the parameters does not have to be positive numbers. For example, it is OK to 
 * have a rectangle whose top left corner is at (-2, -1). 
 * However, geometric shapes renders only the part of the shape which is inside the given 
 * raster.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class Rectangle extends GeometricShape{
	/** x coordinate of rectangle */
	private int x;
	
	/** y coordinate of rectangle */
	private int y;
	
	/** width of rectangle */
	private int width;
	
	/** height of rectangle */
	private int height;
	
	/**
	 * Public constructor which takes 4 arguments: x coordinate, y coordinate, width and
	 * height of rectangle. 
	 * 
	 * 
	 * @param x {@link #x}
	 * @param y {@link #y}
	 * @param w {@link #width}
	 * @param h {@link #height}
	 * 
	 * @throws IllegalArgumentException if width or height in argument is zero or less.
	 */
	public Rectangle(int x, int y, int w, int h) {
		if (h <= 0) {
			throw new IllegalArgumentException("Height must not be zero or less.");
		}
		if (w <= 0) {
			throw new IllegalArgumentException("Width must not be zero or less.");
		}
		this.x = x;
		this.y = y;
		width = w;
		height = h;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.shapes.PointerContainer#containsPoint(int, int)
	 */
	@Override
	public boolean containsPoint(int x, int y) {
		if (x < this.x) return false;
		if (y < this.y) return false;
		if (x >= this.x + width) return false;
		if (y >= this.y + height) return false;
		
		return true;
	}
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.shapes.GeometricShape#draw(BWRaster)
	 */
	@Override
	public void draw(BWRaster raster) {
		int ymax = this.y + height < raster.getHeight() ? 
				this.y + height : raster.getHeight() ;

		int xmax = this.x + width < raster.getWidth() ? 
				this.x + width : raster.getWidth();
		
		int y = this.y < 0 ? 0 : this.y;
		int xconst = this.x < 0 ? 0 : this.x;
		
		for (; y < ymax; y++) {
			int x = xconst;
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
	 * Gets and returns width.
	 * 
	 * @return {@link #width}
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Sets {@link #width} to value from argument.
	 * 
	 * @param width new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Gets and returns height
	 * 
	 * @return {@link #height}
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Sets {@link #height} to value from argument.
	 * 
	 * @param height new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
