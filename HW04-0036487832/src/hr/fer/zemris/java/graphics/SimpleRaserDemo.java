package hr.fer.zemris.java.graphics;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.Rectangle;
import hr.fer.zemris.java.graphics.views.RasterView;
import hr.fer.zemris.java.graphics.views.SimpleRasterView;

/*
 * Test from hw04.pdf
 */

public class SimpleRaserDemo {

	
	public static void main(String[] args) {
		Rectangle rect1 = new Rectangle(0, 0, 4, 4);
		Rectangle rect2 = new Rectangle(1, 1, 2, 2);
		BWRaster raster = new BWRasterMem(6, 5);
		
		raster.enableFlipMode();
		
		rect1.draw(raster);
		rect2.draw(raster);
		
		RasterView view = new SimpleRasterView();
		
		view.produce(raster);
		System.out.println();
		view.produce(raster);
		
		System.out.println();
		
		RasterView view2 = new SimpleRasterView('_','X');
		
		view2.produce(raster);
		
//		RasterView view3 = new StringRasterView('l', 's');
//		String str = (String) view3.produce(raster);
//		System.out.println(str);

	}

}
