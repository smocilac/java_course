package hr.fer.zemris.java.fractals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.complex.Complex;
import hr.fer.zemris.java.complex.ComplexPolynomial;
import hr.fer.zemris.java.complex.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * Class which represents produces of fractals.
 * It works parallel by using {@linkplain ExecutorService}
 * 
 * @author Stjepan
 * @version 1.0
 */
public class IFractalProducerImpl implements IFractalProducer {
	/**
	 * threshold
	 */
	private final double THRESHOLD = 0.001;
	/**
	 * root distance
	 */
	private final double ROOT_DISTANCE = 0.002;
	/**
	 * max number of iterations.
	 */
	private final int MAX_ITER = 256;
	/**
	 * data as array of shorts.
	 */
	private short data [] ;
	/**
	 * Polynomial complex rooted.
	 */
	private final ComplexRootedPolynomial polynomial;
	/**
	 * Polynomial complex.
	 */
	private ComplexPolynomial pol;
	/**
	 * Number of available processors.
	 */
	private final int numOfProcessors = Runtime.getRuntime().availableProcessors();
	
	

	/**
	 * Constructor which takes one argument - instance of {@linkplain ComplexRootedPolynomial}
	 * 
	 * @param polynomial complex polynomial 
	 * @throws IllegalArgumentException if argument is null
	 */
	public IFractalProducerImpl(ComplexRootedPolynomial polynomial) {
		if (polynomial == null){
			throw new IllegalArgumentException();
		}
		
		this.polynomial = polynomial;
		pol = polynomial.toComplexPolynom();
	}
	
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.fractals.viewer.IFractalProducer#produce(double, double, double, double, int, int, long, hr.fer.zemris.java.fractals.viewer.IFractalResultObserver)
	 */
	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo, 
				IFractalResultObserver observer) {
		
		data = new short[width * height];
		
		class Job implements Runnable {
			
			private final int from;
			private final int to;
			
			public Job(int from, int to) {
				this.from = from;
				this.to = to;
			}

			@Override
			public void run() {
				ComplexPolynomial derived = pol.derive();
				for (int y = from; y < to; y++){
					for(int x = 0; x < width; x++){
						double real = x / (width-1.0d) * (reMax - reMin) + reMin;
					    double imag = (height-1.0d-y) / (height-1) * (imMax - imMin) + imMin;
					    
					    Complex zn = new Complex(real, imag);
					    Complex zn1;
					    double module ;
					    
					    int iter = 0;
					    do {
					    	Complex numerator = polynomial.apply(zn);
					    	Complex denominator = derived.apply(zn);
					    	Complex fraction = numerator.divide(denominator);
					    	zn1 = zn.sub(fraction);
					    	module = zn1.sub(zn).module();
					    	zn = zn1;
					    } while (module > THRESHOLD && iter++ < MAX_ITER);
					    
					    short index = (short) polynomial.indexOfClosestRootFor(zn1, ROOT_DISTANCE);
					    if(index == -1) { 
					    	data[y * width + x] = 2; 
					    } else { 
					    	data[y * width + x] = (short) (index + 2); 
					    }
					}
				}
				
			}
		}
		int size = 8 * numOfProcessors;
		
		Future<?> [] futures = new Future[size + 1];
		
		ThreadFactory fact = new DaemonicThreadFactory();
		
		ExecutorService service = Executors.newFixedThreadPool(numOfProcessors, fact);
		int y = 0;
		int step = height / size;
		for (int i = 0; i <= size; i++){
			if (i == size){
				futures[i] = service.submit(new Job(y, height));
				break;
			}
			futures[i] = service.submit(new Job(y, y + step));
			y += step;
		}
		
		for (int i = 0; i <= size; i++){
			while(true){
				try {
					futures[i].get();
					break;
				} catch (InterruptedException | ExecutionException ignorable) {
				}
			}
		}
		
		observer.acceptResult(data, (short)(pol.order()+1), requestNo);
	}

	/**
	 * Class which creates new {@linkplain Thread}, sets daemon flag to true 
	 * and returns daemon thread.
	 * 
	 * @author Stjepan
	 * @version 1.0
	 */
	public static class DaemonicThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			return thread;
		}
	}
}
