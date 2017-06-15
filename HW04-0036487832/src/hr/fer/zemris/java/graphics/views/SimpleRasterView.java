/**
 * 
 */
package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * SimpleRasterView is an implementation of this interface and outputs the textual 
 * representation of image to standard output and returns null as result.
 * 
 * This class has two constructors. One constructor allows user to specify which character 
 * will be used to represent pixels that are turned on and which character will be used to 
 * represent pixels that are turned off. The other constructor should be the default 
 * constructor that delegates the call to first constructor and provides '*' and '.' as 
 * required characters.
 * 
 * 
 * @author Stjepan
 * @version 1.0
 *
 */
public class SimpleRasterView implements RasterView {
	/** Represents pixel which is turned off  */
	private final char off;
	
	/** Represents pixel which is turned on */
	private final char on;
	
	/**
	 * Public constructor which allows to specify which character will be used to represent 
	 * pixels that are turned on and which character will be used to represent pixels that 
	 * are turned off.
	 * 
	 * 
	 * @param off {@link #off} turned off pixel character
	 * @param on {@link #on} turned on pixel character
	 */
	public SimpleRasterView(char off, char on) {
		this.off = off;
		this.on = on;
	}
	
	/**
	 * This constructor is the default constructor that delegates 
	 * the call to {@link #SimpleRasterView(char, char)} constructor and provides '*' 
	 * and '.' as required characters.
	 * 
	 */
	public SimpleRasterView() {
		this('.', '*');
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.views.RasterView#produce(hr.fer.zemris.java.graphics.raster.BWRaster)
	 */
	
	@Override
	public Object produce(BWRaster raster) {
		if (raster == null) {
			throw new IllegalArgumentException("Raster must not be null. ");
		}
		
		int maxw = raster.getWidth();
		
		for (int i = 0, max = raster.getHeight(); i < max; i++) {
			for (int j = 0; j < maxw; j++) {
				if (raster.isTurnedOn(j, i)) {
					System.out.print(on);
				} else {
					System.out.print(off);
				}
			}
			System.out.println();
		}
		
		return null;
	}

}
