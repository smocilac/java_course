package hr.fer.zemris.java.tecaj.hw5.db.operators;

import hr.fer.zemris.java.tecaj.hw5.db.IComparisonOperator;

/**
 * Represents comparison which define if value1 is greater or equal to value2.
 * 
 * @author Stjepan
 * @version 1.0
 */
public class LikeOperator implements IComparisonOperator {
	
	
	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if second argument defined with multiple wildcards *
	 */
	@Override
	public boolean satisfied(String value1, String value2) {
		if (value1 == null || value2 == null){
			return value1 == value2;
		}
		
		if (value2.contains("*")){
			char[] ch = value2.toCharArray();
			int counter = 0;
			for (int i = 0; i < ch.length; i++){
				if ( ch[i] == '*'){
					counter ++;
				}
			}
			
			if (counter > 1){
				throw new IllegalArgumentException("Cannot evalute LIKE operator " 
						+ "because " + value2 + " is not applicable.");
			}
			
			String[] startAndEnd = value2.split("\\*");
			
			if (startAndEnd.length != 2 ){
				if( startAndEnd.length == 1 && value2.endsWith("*") ){
					return value1.startsWith(value2.replaceAll("\\*", ""));
				}
				throw new IllegalArgumentException("Cannot evalute LIKE operator " 
							+ "because " + value2 + " is not applicable.");
			}
 			
			if (startAndEnd[0].isEmpty()){
				return value1.endsWith(startAndEnd[1]);
			} else if (startAndEnd[1].isEmpty()){
				return value1.startsWith(startAndEnd[0]);
			}
			
			return value1.endsWith(startAndEnd[1]) && value1.startsWith(startAndEnd[0]);
		}
		
		return value1.equals(value2);
		
		
		
	}

}
