/**
 * 
 */
package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * This class provides draw method which is able to draw any GeometricShape.
 * 
 * @author Stjepan
 * @version 1.0
 * 
 */
public abstract class GeometricShape implements PointerContainer{
	
	//public abstract GeometricShape execute();
	
	/**
	 * Method draws any possible geometric shape. 
	 * 
	 * This method must draws filled image of the geometric shape, not only its outline.
	 * 
	 * @param raster raster to draw picture on
	 */
	public void draw(BWRaster raster) {
		int width = raster.getWidth();
		for (int y = 0, height = raster.getHeight(); y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (this.containsPoint(x, y)) {
					raster.turnOn(x, y);
				}
			}
		}
	}
}
