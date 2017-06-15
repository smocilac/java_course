package hr.fer.zemris.java.tecaj.hw5.db.prob1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.db.ConditionalExpression;
import hr.fer.zemris.java.tecaj.hw5.db.getters.FirstNameFieldGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getters.LastNameFieldGetter;
import hr.fer.zemris.java.tecaj.hw5.db.operators.EqualOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.GreaterOrEqualOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.GreaterThanOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LessOrEqualOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LessThanOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LikeOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.NotEqualOperator;
import hr.fer.zemris.java.tecaj.hw5.db.parseable.Parser;
import hr.fer.zemris.java.tecaj.hw5.db.parseable.ParserException;


public class TestDatabase {
	
	//@Ignore
	@Test
	public void testParserForValidInputs(){
		// should not throw
		Parser parser1 = new Parser("lastName LIKE \"N*\" and firstName < \"L\"");
		// lets put some tabs 
		Parser parser2 = new Parser("lastName\t\t\tLIKE \"N*\" and firstName <\t \"L\"");
		// lets add some more and check if 'and' is case sensitive
		Parser parser3 = new Parser("lastName LIKE \"N*\" And firstName < \"L\""
				+ " And lastName\t\t\tLIKE \"N*\" aND firstName <\t \"L\"");
		
		
		// now lets check if parser makes good expressions
		List<ConditionalExpression> list1 = parser1.getExpressions();
		List<ConditionalExpression> list2 = parser2.getExpressions();
		List<ConditionalExpression> list3 = parser3.getExpressions();
		
		// check if parser1 made good expression
		assertTrue (list1.get(0).getOperator() instanceof LikeOperator);
		assertTrue (list1.get(0).getValueGetter() instanceof LastNameFieldGetter);
		assertTrue (list1.get(1).getOperator() instanceof LessThanOperator);
		assertTrue (list1.get(1).getValueGetter() instanceof FirstNameFieldGetter);
		
		// check if parser2 made good expression
		assertTrue (list2.get(0).getOperator() instanceof LikeOperator);
		assertTrue (list2.get(0).getValueGetter() instanceof LastNameFieldGetter);
		assertTrue (list2.get(1).getOperator() instanceof LessThanOperator);
		assertTrue (list2.get(1).getValueGetter() instanceof FirstNameFieldGetter);
		
		
		// check if parser3 made good expression
		assertTrue (list3.get(0).getOperator() instanceof LikeOperator);
		assertTrue (list3.get(0).getValueGetter() instanceof LastNameFieldGetter);
		assertTrue (list3.get(1).getOperator() instanceof LessThanOperator);
		assertTrue (list3.get(1).getValueGetter() instanceof FirstNameFieldGetter);
		assertTrue (list3.get(2).getOperator() instanceof LikeOperator);
		assertTrue (list3.get(2).getValueGetter() instanceof LastNameFieldGetter);
		assertTrue (list3.get(3).getOperator() instanceof LessThanOperator);
		assertTrue (list3.get(3).getValueGetter() instanceof FirstNameFieldGetter);
	}
	
	
	//@Ignore
	@Test (expected=ParserException.class)
	public void testParserForInvalidInput1(){
		@SuppressWarnings("unused")
		Parser parser1 = new Parser(null);
	}
	
	//@Ignore
	@Test (expected=ParserException.class)
	public void testParserForInvalidInput2(){
		// throws because of <==
		@SuppressWarnings("unused")
		Parser parser1 = new Parser("   lastName <== \"I\"       ");
	}
	
	//@Ignore
	@Test (expected=ParserException.class)
	public void testParserForInvalidInput3(){
		//throws because of LastName
		@SuppressWarnings("unused")
		Parser parser1 = new Parser("   LastName <   \"I\"       ");
	}
	
	//@Ignore
	@Test (expected=ParserException.class)
	public void testParserForInvalidInput4(){
		//throws because of multiple quotes
		@SuppressWarnings("unused")
		Parser parser1 = new Parser("   lastName <   \"\"\"\"\"       ");
	}
	
	//@Ignore
	@Test (expected=ParserException.class)
	public void testParserForInvalidInput5(){
		//throws because of wildcard *
		@SuppressWarnings("unused")
		Parser parser1 = new Parser("   lastName <   \"*\"       ");
	}
	
	//@Ignore
	@Test (expected=ParserException.class)
	public void testParserForInvalidInput6(){
		//throws because of not finished quote mark
		@SuppressWarnings("unused")
		Parser parser1 = new Parser("   firstName =  \"Ivana       ");
	}
	
	//@Ignore
	@Test 
	public void testLessThan(){
		LessThanOperator less = new LessThanOperator();
		
		assertFalse(less.satisfied(null, null));
		
		assertFalse(less.satisfied("Nešto", "Mozda"));
	}
	
	//@Ignore
	@Test 
	public void testLessOrEqualThan(){
		LessOrEqualOperator lessEq = new LessOrEqualOperator();
			
		assertTrue(lessEq.satisfied(null, null));
		
		assertFalse(lessEq.satisfied("Nešto", "Nešta"));
		
		assertTrue(lessEq.satisfied("Nešto", "Neštz"));
		assertTrue(lessEq.satisfied("Nešto", "Nešto"));
	}
		
	//@Ignore
	@Test 
	public void testGreaterThan(){
		GreaterThanOperator greater = new GreaterThanOperator();
			
		assertFalse(greater.satisfied(null, null));
		
		assertTrue(greater.satisfied("Nešto", "Nešta"));
			
		assertFalse(greater.satisfied("Nešto", "Neštz"));
		assertFalse(greater.satisfied("Nešto", "Nešto"));
	}
	
	//@Ignore
	@Test 
	public void testGreaterOrEqualThan(){
		GreaterOrEqualOperator greater = new GreaterOrEqualOperator();
			
		assertTrue(greater.satisfied(null, null));
		
		assertTrue(greater.satisfied("Nešto", "Nešta"));
			
		assertFalse(greater.satisfied("Nešto", "Neštz"));
		assertTrue(greater.satisfied("Nešto", "Nešto"));
	}
	
	//@Ignore
	@Test 
	public void testEqualTo(){
		EqualOperator equal = new EqualOperator();
				
		assertTrue(equal.satisfied(null, null));
			
		assertFalse(equal.satisfied("Nešto", null));
				
		assertFalse(equal.satisfied("Nešto", "Neštz"));
		assertTrue(equal.satisfied("Nešto", "Nešto"));
	}
	
	
	//@Ignore
	@Test 
	public void testNotEqualTo(){
		NotEqualOperator nequal = new NotEqualOperator();
				
		assertFalse(nequal.satisfied(null, null));
			
		assertTrue(nequal.satisfied("Nešto", null));
				
		assertTrue(nequal.satisfied("Nešto", "Neštz"));
		assertFalse(nequal.satisfied("Nešto", "Nešto"));
	}
	
	//@Ignore
	@Test 
	public void testLikeComparison(){
		String str = "test for like comparison";
		
		LikeOperator like = new LikeOperator();
					
		assertTrue(like.satisfied(str, "tes*"));					
		assertTrue(like.satisfied(str, "*son"));
		assertTrue(like.satisfied(str, "test f*on"));
		
		assertFalse(like.satisfied(str, "tesn*"));					
		assertFalse(like.satisfied(str, "*som"));
		assertFalse(like.satisfied(str, "test f*an"));
	}
	
	//@Ignore
	@Test (expected = IllegalArgumentException.class)
	public void testLikeComparisonMustThrow(){
		String str = "test for like comparison";
			
		LikeOperator like = new LikeOperator();
		
		// uncomment one by one to check others!!!!
		
		like.satisfied(str, "te*s * " );
		//like.satisfied(str, "**" );
		//like.satisfied(str, "** tes" );
		//like.satisfied(str, "*" );
		//like.satisfied(str, "* fsdfdsfs *" );
	}
	
	
}
