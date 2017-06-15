package hr.fer.zemris.java.simplecomp.impl;


/**
 * {@code RegisterUtil} provides method to check descriptor.
 * 
 * Descriptor is described and documented on {@link InstructionArgument} class description.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class RegisterUtil {
	
	/**
	 * Method returns unsigned value of last arguments byte.
	 * For example, if 0x00100011 is 32 bits value of argument, method will return integer
	 * with hex value of 0x00000011
	 *  
	 * @param registerDescriptor descriptor 
	 * @return index of register
	 */
	public static int getRegisterIndex(int registerDescriptor) {
		return 0x0ff & (registerDescriptor);
	}
	
	/**
	 * Method which checks if 24th bit of 32 bit integer is 1. In that case returns true.
	 * Otherwise returns false.
	 * 
	 * @param registerDescriptor 
	 * 						descriptor
	 * @return true 
	 * 						if 24th bit is 1
	 * 						else false
	 */
	public static boolean isIndirect(int registerDescriptor) {
		return (0x01000000 & registerDescriptor) >> 24 == 1;
	}
	
	/**
	 * Method gets offset of register from descriptor. Offset is defined as 16 bit number.
	 * Bits which defines offset are 8th to 23th bit. 
	 * Method returns signed integer with value of provided bytes (second and third byte)
	 * 
	 * @param registerDescriptor
	 * 				descriptor
	 * @return 
	 * 				signed integer with value of provided 16 bits in middle of 32 bit number
	 * 				from argument
	 */
	public static int getRegisterOffset(int registerDescriptor) {		
		// check if negative
		boolean isNegative = (registerDescriptor & 0x00800000) >> 23 == 1 ; 
		// rotate and delete 16. to 31. bits
		int value =  (registerDescriptor >> 8) & 0x0FFFF; 
		if ( isNegative){
			value ^= 0x0FFFF; // XOR 
			value += 0x01; // and add one to get negative
			value *= -1; // finally change sign to minus
		}
		return value;
	}
}
