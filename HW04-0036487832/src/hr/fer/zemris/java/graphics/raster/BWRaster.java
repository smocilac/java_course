/**
 * 
 */
package hr.fer.zemris.java.graphics.raster;

/**
 * Provides a procedure for drawing on raster devices
 * 
 * Letters BW stand for Black-and-White raster.
 * 
 * The coordinate system for raster has (0,0) at the top-left corner of raster; 
 * positive x-axis is to the right and positive y-axis is toward the bottom.
 * 
 * @author Stjepan
 * @version 1.0
 *
 */
public interface BWRaster {
	/**
	 * Returns appropriate dimension(width) of used raster.
	 * 
	 * @return dimension width
	 */
	int getWidth();
	
	/**
	 * Returns appropriate dimension(height) of used raster.
	 * 
	 * @return dimension height
	 */
	int getHeight();
	
	/**
	 * Turns off all pixels in raster.
	 * 
	 */
	void clear();
	
	/**
	 * Turns pixel on at the specified location. 
	 * 
	 * The working of turnOn method is closely controlled with flipping mode of raster. 
	 * If flipping mode of raster is disabled, then the call of the turnOn method turns on 
	 * the pixel at specified location (again, if location is valid). However, if flipping
	 * mode is enabled, then the call of the turnOn method flips the pixel at the specified 
	 * location (if it was turned on, it will be turned off, and if it was turned off, it 
	 * will be turned on).
	 * 
	 * @see BWRaster for information about coordinate system
	 * @param x x coordinate
	 * @param y y coordinate
	 * 
	 * @throws IllegalArgumentException 
	 * 			 if (x,y) is invalid with respect to raster dimensions
	 */
	void turnOn(int x, int y);
	
	/**
	 * Turns pixel off at the specified location. 
	 * 
	 * @see BWRaster for information about coordinate system
	 * @param x x coordinate
	 * @param y y coordinate
	 * 
	 * @throws IllegalArgumentException 
	 * 			 if (x,y) is invalid with respect to raster dimensions
	 */
	void turnOff(int x, int y);
	
	/**
	 * Controls the flipping mode which is initially disabled.
	 * 
	 * @see #turnOn(int, int)
	 */
	void enableFlipMode();
	
	/**
	 * Controls the flipping mode which is initially disabled. 
	 * 
	 * @see #turnOn(int, int)
	 */
	void disableFlipMode();
	
	/**
	 * Checks if the pixel at the given location is turned on and if it is, returns true, 
	 * otherwise returns false.
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return true if pixel on (x, y) is turned on, false otherwise
	 * 
	 * @throws IllegalArgumentException 
	 * 					if coordinate is not in rasters range
	 */
	boolean isTurnedOn(int x, int y);
	
	/**
	 * If flip is enabled this method will disable it .
	 * If flip is disabled this method will enable it .
	 */
	public void changeFlip();
}
