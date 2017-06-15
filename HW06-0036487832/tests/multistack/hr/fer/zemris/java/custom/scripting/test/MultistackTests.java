package hr.fer.zemris.java.custom.scripting.test;

import static org.junit.Assert.assertEquals;

import java.util.EmptyStackException;

import org.junit.Ignore;
import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;



public class MultistackTests {
	
	@Ignore
	@Test(expected = IllegalArgumentException.class)
	public void testValueWrapperConstructor(){
		@SuppressWarnings("unused")
		ValueWrapper pr = new ValueWrapper(new ObjectMultistack());
	}
	
	
	//@Ignore
	@Test
	public void testValueWrapperDouble(){
		ValueWrapper price = new ValueWrapper(10.1);
		
		price.increment("25"); // should be double with 35.1 value
		assertEquals(price.getValue(), Double.valueOf(35.1));
		
		price.multiply(null); // should be double 0
		assertEquals(price.getValue(), Double.valueOf(0.));
		
		price.increment(1E2); // should be double 100
		assertEquals(price.getValue(), Double.valueOf(100.));
		
		price.divide(2.5); // should be double 40
		assertEquals(price.getValue(), Double.valueOf(40.));
	}
	
	//@Ignore
	@Test (expected = IllegalArgumentException.class)
	public void testValueWrapperDoubleShouldThrow(){
		ValueWrapper price = new ValueWrapper(10.1);
		
		price.divide("0"); // should throw
		
	}
	
	//@Ignore
	@Test (expected = IllegalArgumentException.class)
	public void testValueWrapperDoubleShouldThrow2(){
		ValueWrapper price = new ValueWrapper(10.1);
		
		price.divide(0); // should throw
		
	}
	
	//@Ignore
	@Test (expected = IllegalArgumentException.class)
	public void testValueWrapperDoubleShouldThrow3(){
		ValueWrapper price = new ValueWrapper(10.1);
		
		price.divide(0.); // should throw
	}
	
	//@Ignore
	@Test
	public void testValueWrapperInteger(){
		ValueWrapper price = new ValueWrapper(500); // Integer when autoboxing
		
		price.increment("250"); // should be Integer with value 750
		assertEquals(price.getValue(), Integer.valueOf(750));
		
		price.divide(3); // should be Integer with value 250
		assertEquals(price.getValue(), Integer.valueOf(250));
		
		price.multiply(null); // should be Integer with value 0
		assertEquals(price.getValue(), Integer.valueOf(0));
		
		price.increment(1E2); // should be Double 100
		assertEquals(price.getValue(), Double.valueOf(100.));
	}
	
	//@Ignore
	@Test (expected = IllegalArgumentException.class)
	public void testValueWrapperIntegerShouldThrow1(){
		ValueWrapper price = new ValueWrapper(10);
		
		price.divide("0."); // should throw
	}
	
	//@Ignore
	@Test (expected = IllegalArgumentException.class)
	public void testValueWrapperIntegerShouldThrow2(){
		ValueWrapper price = new ValueWrapper(10);
		
		price.divide(0); // should throw
	}
	
	//@Ignore
	@Test
	public void testValueWrapperString(){
		ValueWrapper price = new ValueWrapper("300"); // Integer when autoboxing
		
		price.increment("250"); // should be Integer with value 750
		assertEquals(price.getValue(), "550");
	}
		
	//@Ignore
	@Test (expected = IllegalArgumentException.class)
	public void testValueWrapperStringShouldThrow1(){
		ValueWrapper price = new ValueWrapper("152.");
		
		price.divide("0."); // should throw
	}
		
	//@Ignore
	@Test (expected = IllegalArgumentException.class)
	public void testValueWrapperStringhouldThrow2(){
		
		@SuppressWarnings("unused")
		ValueWrapper price = new ValueWrapper("1000.a");
	}
	
	//@Ignore
	@Test (expected = IllegalArgumentException.class)
	public void testValueWrapperStringhouldThrow3(){
		
		@SuppressWarnings("unused")
		ValueWrapper price = new ValueWrapper("");
	}
	
	
	//@Ignore
	@Test (expected = EmptyStackException.class)
	public void testStackWhenEmpty(){
		new ObjectMultistack().peek("");
	}
	
	//@Ignore
	@Test 
	public void testStack(){
		ObjectMultistack stack = new ObjectMultistack(8);
		
		ValueWrapper val1 = new ValueWrapper("1");
		ValueWrapper val2 = new ValueWrapper("2");
		ValueWrapper val3 = new ValueWrapper("3");
		ValueWrapper val4 = new ValueWrapper("4");
		
		stack.push("ime1", val1);
		stack.push("ime1", val2);
		stack.push("ime1", val3);
		stack.push("ime1", val4);
		
		ValueWrapper ret1 = stack.pop("ime1");
		ValueWrapper ret2 = stack.pop("ime1");
		ValueWrapper ret3 = stack.pop("ime1");
		ValueWrapper ret4 = stack.pop("ime1");
		
		assertEquals(ret1, val4);
		assertEquals(ret2, val3);
		assertEquals(ret3, val2);
		assertEquals(ret4, val1);
	}
	
	//@Ignore
	@Test (expected = EmptyStackException.class)
	public void testStackEmpty(){
		ObjectMultistack stack = new ObjectMultistack(8);
		
		ValueWrapper val1 = new ValueWrapper("1");
		ValueWrapper val2 = new ValueWrapper("2");
		ValueWrapper val3 = new ValueWrapper("3");
		ValueWrapper val4 = new ValueWrapper("4");
		
		stack.push("ime1", val1);
		stack.push("ime1", val2);
		stack.push("ime1", val3);
		stack.push("ime1", val4);
		
		stack.pop("ime1");
		stack.pop("ime1");
		stack.pop("ime1");
		stack.pop("ime1");
		stack.pop("ime1"); // should throw here
	}
	
}
