/**
 * 
 */
package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * This interface provides a single method produce. 
 * 
 * Classes which implement this interface will be responsible for visualization of created 
 * images.
 * 
 * @author Stjepan
 * @version 1.0
 */
public interface RasterView {
	
	/**
	 * Responsible for visualization of created images.
	 * 
	 * @param raster to produce visualization
	 * @return result of producing
	 */
	Object produce(BWRaster raster);
}
