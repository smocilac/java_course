/**
 * 
 */
package hr.fer.zemris.java.graphics.raster;

/**
 * The class BWRasterMem is an implementation of this interface which keeps all of its 
 * data in computer memory. 
 * 
 * It uses boolean array to store data. 
 * 
 * On creation of new objects of this class all pixels will be initially turned off. 
 * 
 * This class provides a single public constructor which accepts raster width and raster 
 * height; both must be at least 1.
 * 
 * @author Stjepan
 * @version 1.0
 * 
 */
public class BWRasterMem implements BWRaster {
	
	/** Stores all coordinate boolean values, there are stored as (x,y)*/
	private boolean[][] data ;
	
	/** Width of raster */
	private final int width;
	
	/** Height of raster */
	private final int height;
	
	/** Enabled when true, disabled otherwise. */
	private boolean flipMode;
	
	/**
	 * Public constructor which accepts raster width and raster height; both must be at 
	 * least 1 to be valid
	 * 
	 * @param width {@link #width}
	 * @param height {@link #height}
	 */
	public BWRasterMem(int width, int height) {
		if ( width < 1) {
			throw new IllegalArgumentException("Width must be greater than zero.");
		}
		if ( height < 1) {
			throw new IllegalArgumentException("Height must be greater than zero.");
		}
		
		flipMode = false; // initially enabled
		this.width = width;
		this.height = height;
		data = new boolean[width][height];
		clear();
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.raster.BWRaster#getWidth()
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.raster.BWRaster#getHeight()
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.raster.BWRaster#clear()
	 */
	@Override
	public void clear() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				data[i][j] = false;
			}
		}
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.raster.BWRaster#turnOn(int, int)
	 */
	@Override
	public void turnOn(int x, int y) {
		checkBoundaries(x,y);
		
		if (flipMode) {
			data[x][y] = !data[x][y];
			return;
		}
		
		data[x][y] = true;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.raster.BWRaster#turnOff(int, int)
	 */
	@Override
	public void turnOff(int x, int y) {
		checkBoundaries(x,y);
		
		data[x][y] = false;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.raster.BWRaster#enableFlipMode()
	 */
	@Override
	public void enableFlipMode() {
		flipMode = true;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.raster.BWRaster#disableFlipMode()
	 */
	@Override
	public void disableFlipMode() {
		flipMode = false;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.raster.BWRaster#isTurnedOn(int, int)
	 */
	@Override
	public boolean isTurnedOn(int x, int y) {
		checkBoundaries(x,y);
		
		return data[x][y];
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.graphics.raster.BWRaster#flip()
	 */
	@Override
	public void changeFlip() {
		if (flipMode) {
			disableFlipMode();
			return;
		}
		enableFlipMode();
	}
	
	/**
	 * Checks if y or x is in the range. 
	 * Coordinate x must be zero or greater AND less than {@code width} to be in the range.
	 * Coordinate y must be zero or greater AND less than {@code height} to be in the range.
	 * 
	 * @throws IllegalArgumentException if x or y not in the range.
	 */
	private void checkBoundaries(int x, int y) {
		if (x < 0) throw new IllegalArgumentException("x must be greater or equal to zero");
		if (y < 0) throw new IllegalArgumentException("y must be greater or equal to zero");
		if (x >= width) throw new IllegalArgumentException("x must be less then width");
		if (y >= height) throw new IllegalArgumentException("y must be less then height");
	}

}
