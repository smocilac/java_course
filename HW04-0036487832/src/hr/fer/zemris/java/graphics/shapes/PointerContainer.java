/**
 * 
 */
package hr.fer.zemris.java.graphics.shapes;

/**
 * This interface provides method for checking if object contains coordinate.
 * 
 * @author Stjepan
 * @version 1.0
 */
public interface PointerContainer {
	
	/**
	 * Method containsPoint checks if given point belongs to specified geometric shape. 
	 * 
	 * The general contract for this method is that it must return false only if the 
	 * location is outside of the geometric shape. For all other cases it must return true.
     * 
     * @param x x coordinate 
     * @param y y coordinate
     * @return true if object contains coordinate (x,y), else false
     */
    boolean containsPoint(int x, int y);
}
