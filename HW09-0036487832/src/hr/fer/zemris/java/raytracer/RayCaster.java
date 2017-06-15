package hr.fer.zemris.java.raytracer;

import java.util.List;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Class which represents sequential ray caster.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class RayCaster {
	/**
	 * for comapring doubles.
	 */
	private final static Double EPSILON = 0.0001d ;

	/**
	 * Shows image on GUI.
	 * 
	 * @param args non
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(),
		new Point3D(10,0,0),
		new Point3D(0,0,0),
		new Point3D(0,0,10),
		20, 20);
	}
	
	/**
	 * Gets instance which calculates colors of points on screen.
	 * 
	 * @return instance of {@linkplain IRayTracerProducer}
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
							double horizontal, double vertical, int width, int height,
							long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
				@SuppressWarnings("unused")
				Point3D zAxis = null;
				Point3D yAxis = viewUp.normalize().sub(view.sub(eye).normalize().scalarMultiply(view.sub(eye)
						.normalize().scalarProduct(viewUp.normalize()))).normalize();
				Point3D xAxis = view.sub(eye).normalize().vectorProduct(yAxis).normalize();
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2)).add(yAxis.scalarMultiply(vertical / 2));
				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;
				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(xAxis
										.scalarMultiply(horizontal * ((double) x) / ((double)(width - 1))))
										.sub(yAxis.scalarMultiply(vertical * ((double) y) / ((double)(height - 1))));

						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			
			}
			
			/**
			 * Method calculates closest intersection in point.
			 * If found, calculates color of that point.
			 * 
			 * @param scene scene where objects beeing observed
			 * @param ray air which intersetion is required for
			 * @param rgb array in which are stored components of color.
			 */
			private void tracer(Scene scene, Ray ray, short[] rgb) {
				RayIntersection intersection ;
				rgb[0] = 15;
				rgb[1] = 15;
				rgb[2] = 15;
				
				List<LightSource> lightSources = scene.getLights();
				if ((intersection = findClosestIntersection(scene, ray)) == null){
					return; // cannot find such
				}
				
				for (int i = 0; i < lightSources.size(); i++){
					RayIntersection inter ;
					LightSource s;
					
					if ((inter = findClosestIntersection(scene, Ray.fromPoints(intersection.getPoint(), 
										(s = lightSources.get(i)).getPoint()))) == null){
						continue;
					}
					
					Point3D point = s.getPoint();
					
					double dist1 = intersection.getPoint().sub(point).norm();
					double dist2 = inter.getPoint().sub(point).norm();
					double diff = dist1 - dist2;
					diff = Math.abs(diff);
					
					if (! (diff < EPSILON)) {
						continue;
					}
					
					double i1d, i2r;
					Point3D repet =  inter.getPoint();
					
					Point3D normn = inter.getNormal().normalize();
					Point3D clInl = s.getPoint().sub(repet);
					Point3D normv = ray.start.sub(repet).normalize();
					Point3D clInr = normn.scalarMultiply(2).scalarMultiply(clInl.scalarProduct(normn))
										.sub(clInl).normalize();
					
					double r = (s.getR() * inter.getKdr() * (i1d = Math.max(normn.scalarProduct(clInl.normalize()), 0))
							+ inter.getKrr() * (i2r = Math.max(Math.pow(clInr.scalarProduct(normv), inter.getKrn()), 0)));
					double g = s.getG() * (inter.getKdg() * i1d + inter.getKrg() * i2r);
					double b = s.getB() * (inter.getKdb() * i1d + inter.getKrb() * i2r);
					
					rgb[0] += r;
					rgb[1] += g;
					rgb[2] += b;
				}							
			}
			
			/**
			 * Finds closest intersetion
			 * 
			 * @param scene
			 * @param ray
			 * @return {@linkplain RayIntersection}
			 */
			private RayIntersection findClosestIntersection(Scene scene, Ray ray) {
				List<GraphicalObject> list = scene.getObjects();
				RayIntersection closest = null;
				
				for (int i = 0; i < list.size(); i++){
					if (i == 0 || closest == null){
						closest = list.get(i).findClosestRayIntersection(ray);
					} else {
						RayIntersection compare = list.get(i).findClosestRayIntersection(ray);
						if (compare != null && closest != null && compare.getDistance() < 
								closest.getDistance()){
							closest = compare;
						}
					}
				}
				
				return closest;
			}
		};
	}
	
	

}
