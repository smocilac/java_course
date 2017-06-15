package hr.fer.zemris.java.raytracer.model;

/**
 * Class which implements {@linkplain GraphicalObject}.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class Sphere extends GraphicalObject{
	/**
	 * Center point
	 */
	private Point3D center;
	/**
	 * Radius point
	 */
	private double radius;
	/**
	 * Factor of diffuse component - red.
	 */
	private double kdr;
	/**
	 * Factor of diffuse component - green.
	 */
	private double kdg;
	/**
	 * Factor of diffuse component - blue.
	 */
	private double kdb;
	/**
	 * Factor of reflecting component - red.
	 */
	private double krr;
	/**
	 * Factor of reflecting component - green.
	 */
	private double krg;
	/**
	 * Factor of reflecting component - blue.
	 */
	private double krb;
	/**
	 * Reflective index
	 */
	private double krn;
	/**
	 * For double comparing.
	 */
	private final Double EPSILON = 0.0001d ;
	
	
	/**
	 * Constructor created by using fields.
	 * 
	 * @param center 
	 * @param radius
	 * @param kdr
	 * @param kdg
	 * @param kdb
	 * @param krr
	 * @param krg
	 * @param krb
	 * @param krn
	 * 
	 * @throws IllegalArgumentException if center is null
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		if (center == null){
			throw new IllegalArgumentException("Center cannot be null. ");
		}
		
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.raytracer.model.GraphicalObject#findClosestRayIntersection(hr.fer.zemris.java.raytracer.model.Ray)
	 */
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		double det;
		double a = ray.direction.scalarProduct(ray.direction);
		Point3D sub = ray.start.sub(this.center);
		double b = ray.direction.scalarMultiply(2).scalarProduct(sub);
		sub = ray.start.sub(this.center);
		double c = ray.start.sub(this.center).scalarProduct(sub) - Math.pow(radius, 2);
		
		if ((det = Math.pow(b, 2) - 4 * a * c) < 0){
			return null;
		}
		// calculate two possible solutions of quadratic equation
		det = Math.sqrt(det);
		double solution1 = (- b + det) / (2 * a);
		double solution2 = (- b - det) / (2 * a);
		double difference = Math.abs(solution1 - solution2);
		
		if (difference <= EPSILON){
			return getCalculatedIntersection(ray, solution1, true);
		} else {
			if (solution1 > 0 && solution2 > 0){
				return getCalculatedIntersection(ray, solution1 < solution2 ? solution1 : solution2, true);
			}
			RayIntersection ri1 = getCalculatedIntersection(ray, solution1, false);
			RayIntersection ri2 = getCalculatedIntersection(ray, solution2, false);
			
			return (ri1.getDistance() < ri2.getDistance() ?  ri1 : ri2);
		}
	}
	
	/**
	 * Gets calculated intersection. 
	 * 
	 * @param ray
	 * @param solution of quadratic quadratic equation
	 * @param outer boolean
	 * @return instance of {@linkplain RayIntersection}
	 */
	private RayIntersection getCalculatedIntersection(Ray ray, double solution, boolean outer) {
		if (ray == null){
			throw new IllegalArgumentException("Ray must not be null. ");
		}
		
		Point3D scMul = ray.direction.scalarMultiply(solution);
		Point3D is = ray.start.add(scMul);
		Point3D difference = is.sub(ray.start);
		double distance = difference.norm();

		return new RayIntersection(is, distance, outer) {

			/* (non-Javadoc)
			 * @see hr.fer.zemris.java.raytracer.model.RayIntersection#getNormal()
			 */
			@Override
			public Point3D getNormal() {
				Point3D normal = getPoint().sub(center);
				return normal;
			}

			/* (non-Javadoc)
			 * @see hr.fer.zemris.java.raytracer.model.RayIntersection#getKdr()
			 */
			@Override
			public double getKdr() {
				return kdr;
			}

			/* (non-Javadoc)
			 * @see hr.fer.zemris.java.raytracer.model.RayIntersection#getKdg()
			 */
			@Override
			public double getKdg() {
				return kdg;
			}

			/* (non-Javadoc)
			 * @see hr.fer.zemris.java.raytracer.model.RayIntersection#getKdb()
			 */
			@Override
			public double getKdb() {
				return kdb;
			}

			/* (non-Javadoc)
			 * @see hr.fer.zemris.java.raytracer.model.RayIntersection#getKrr()
			 */
			@Override
			public double getKrr() {
				return krr;
			}

			/* (non-Javadoc)
			 * @see hr.fer.zemris.java.raytracer.model.RayIntersection#getKrg()
			 */
			@Override
			public double getKrg() {
				return krg;
			}

			/* (non-Javadoc)
			 * @see hr.fer.zemris.java.raytracer.model.RayIntersection#getKrb()
			 */
			@Override
			public double getKrb() {
				return krb;
			}

			/* (non-Javadoc)
			 * @see hr.fer.zemris.java.raytracer.model.RayIntersection#getKrn()
			 */
			@Override
			public double getKrn() {
				return krn;
			}
		
		};
	}
	
	

}
