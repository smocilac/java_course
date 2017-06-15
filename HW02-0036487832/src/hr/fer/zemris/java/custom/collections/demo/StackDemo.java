/**
 * 
 */
package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class represents command-line application that takes one argument: numbers and 
 * operations on numbers. Order of calculation is first "operation" second, where 
 * operation is one of +, -, /, * or %. Application works with integers only. Parser
 * is implemented using stack.
 * 
 * @author Stjepan
 */
public class StackDemo {
	
	/**
	 * Command-line application which accepts a single command-line argument: 
	 * expression which should be evaluated. Expression must be in postfix
	 * representation and separated with blank. Suported operations are +, -,
	 *  /, * and % (remainder of integer division). Program works with integer only(
	 *  for example if input is "5 2 /" output is 2 ). 
	 *  
	 * @param args one argument - expression to evaluate
	 * @throws IllegalArgumentException if number of arguments is not one
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Application takes one, and only one"
					+ " argument : expression in postfix order.");
		}
		ObjectStack stack = new ObjectStack();
		String[] inputs = args[0].split("\\s+");
		
		for (String string : inputs) {
			if (isInteger(string, 10)) {
				stack.push(Integer.parseInt(string));
			} else {
				try {
					int i = (int) stack.pop();
					int j = (int) stack.pop();
					int result = preformOperation(j, i, string);
					stack.push(result);
				} catch (EmptyStackException e) { // processing not defined, terminating
					e.printStackTrace();
					System.exit(1);
				} catch (UnsupportedOperationException exc) {// processing not defined, terminating
					exc.printStackTrace();
					System.exit(1);
				} catch (ArithmeticException e) {// processing not defined, terminating
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
		
		if ( stack.size() != 1 ) {
			System.err.println("Error with stack size, expected one element, found " + 
					stack.size() + " elements. Possible couse is more numbers than "
							+ "operations. Argument must provide n numbers and " +
								" (n-1) operation where n is 2 or greater integer.");
			System.exit(1);
		}
		
		try {
			System.out.println("Result is : " + stack.pop());
		} catch (EmptyStackException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Method checks if String str can be parsed to integer. If string to check contains
	 * digits only ( or "-" on position 0, and after that at least one digit) returns 
	 * true.
	 * http://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
	 * @param str string to check weather can be parsed to int or not.
	 * @param radix needed radix
	 * @return true if is numeric, else false
	 */
	private static boolean isInteger(String str, int radix) {
	    if(str.isEmpty()) return false;
	    
	    for(int i = 0; i < str.length(); i++) {
	        if(i == 0 && str.charAt(i) == '-') {
	            if(str.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(str.charAt(i),radix) < 0) return false;
	    }
	    
	    return true;
	}
	
	/**
	 * Method that performs operation on two integers. Supported operations are 
	 * these: /, +, -, * and %. Order of calculation is : first (operation) second.
	 * 
	 * @param first first integer in calculation
	 * @param second second integer in calculation
	 * @param operation defines which operation should compute(/, +, -, * or %)
	 * @return result of operation 
	 * @throws ArithmeticException if second argument is 0 when operation is % or /
	 * @throws UnsupportedOperationException if operations is not /, +, -, * or %
	 */
	private static int preformOperation(int first, int second, String operation) {
		switch (operation) {
		case "/" : return first / second;
		case "*" : return first * second;
		case "-" : return first - second;
		case "+" : return first + second;
		case "%" : return first % second;
		default : throw new UnsupportedOperationException("Operation " + operation +
				" is not supported by this app. Permitted operations are: "
				+ "+, -, /, * or %");
		}
	}
}
