package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ValueWrapperTests {

	@SuppressWarnings("unused")
	@Test (expected=IllegalArgumentException.class)
	public void testValidConstructor() {		//must not throw
		ValueWrapper wrap1 = new ValueWrapper(null);
		ValueWrapper wrap2 = new ValueWrapper("STRING");
		ValueWrapper wrap3 = new ValueWrapper(2.82d);
		ValueWrapper wrap4 = new ValueWrapper(5);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInvalidConstructorWithCharArg() {
		@SuppressWarnings("unused")
		ValueWrapper wrap1 = new ValueWrapper('c');
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInvalidConstructorWithSomeClassAsArg() {
		@SuppressWarnings("unused")
		ValueWrapper wrap1 = new ValueWrapper(new ValueWrapper(2));
	}

	@Test
	public void testNullWrapperGetter() {
		ValueWrapper wrap1 = new ValueWrapper(null);
		assertNull("Must return null, not zero!", wrap1.getValue());
	}

	@Test
	public void testNormalGetters() {
		ValueWrapper wrap1 = new ValueWrapper("STRING");
		ValueWrapper wrap2 = new ValueWrapper(2.82d);
		ValueWrapper wrap3 = new ValueWrapper(5);
		assertEquals("Must return String value!", wrap1.getValue(), "STRING");
		assertEquals("Must return Double value!", wrap2.getValue(), 2.82);
		assertEquals("Must return Integer value!", wrap3.getValue(), 5);
	}

	@Test
	public void testNullValueSetter() {
		ValueWrapper wrap1 = new ValueWrapper(5);
		wrap1.setValue(null);		//must not throw!
		assertNull("Must return null, not zero!", wrap1.getValue());
	}

	@Test
	public void testNormalSetters() {
		ValueWrapper wrap1 = new ValueWrapper("STRING");
		assertEquals("Must return String value!", wrap1.getValue(), "STRING");
		wrap1.setValue(2.82d);
		assertEquals("Must return Double value!", wrap1.getValue(), 2.82);
		wrap1.setValue(5);
		assertEquals("Must return Integer value!", wrap1.getValue(), 5);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testIllegalSetterValue() {
		ValueWrapper wrap1 = new ValueWrapper(2);
		wrap1.setValue('c');		//must throw
	}
	/** TEST INCREMENT*/
	@Test
	public void testIncrementWithDoubles() {
		ValueWrapper wrap1 = new ValueWrapper(2.5d);
		wrap1.increment(4.5d);

		assertEquals("Result should be of type Double!", 7d, wrap1.getValue());
	}

	@Test
	public void testIncrementWithDoubleAndInteger() {
		ValueWrapper wrap1 = new ValueWrapper(2.5d);
		wrap1.increment(4);

		assertEquals("Result should be of type Double!", 6.5d, wrap1.getValue());
	}

	@Test
	public void testIncrementWithIntegers() {
		ValueWrapper wrap1 = new ValueWrapper(2);
		wrap1.increment(4);

		assertEquals("Result should be of type Integer!", 6, wrap1.getValue());
	}

	@Test
	public void testIncrementWithNullValue() {
		ValueWrapper wrap1 = new ValueWrapper(2);
		wrap1.increment(null);

		assertEquals("Result should be Integer value of 2!", 2, wrap1.getValue());
	}

	@Test
	public void testIncrementExistingNullValue() {
		ValueWrapper wrap1 = new ValueWrapper(null);
		wrap1.increment(4d);

		assertEquals("Result should be of Double value of 4!", 4d, wrap1.getValue());
	}

	@Test
	public void testIncrementWithDoubleStringValue() {
		ValueWrapper wrap1 = new ValueWrapper(2);
		wrap1.increment("4.5");

		assertEquals("Result should be Double value of 6.5!", 6.5d, wrap1.getValue());
	}

	@Test
	public void testIncrementWithDoubleScientificStringValue() {
		ValueWrapper wrap1 = new ValueWrapper(2);
		wrap1.increment("1E-2");

		assertEquals("Result should be Double value of 2.01!", 2.01d, wrap1.getValue());
	}

	@Test
	public void testIncrementWithIntegerStringValue() {
		ValueWrapper wrap1 = new ValueWrapper(null);
		wrap1.increment("4");

		assertEquals("Result should be of Integer value of 4!", 4, wrap1.getValue());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testIncrementWithInvalidValue() {
		ValueWrapper wrap1 = new ValueWrapper(3);
		wrap1.increment('c');	//must throw
	}

	/** TEST DECREMENT*/
	@Test
	public void testDecrementWithDoubles() {
		ValueWrapper wrap1 = new ValueWrapper(2.5d);
		wrap1.decrement(4.5d);

		assertEquals("Result should be of type Double!", -2d, wrap1.getValue());
	}

	@Test
	public void testDecrementWithDoubleAndInteger() {
		ValueWrapper wrap1 = new ValueWrapper(2.5d);
		wrap1.decrement(4);

		assertEquals("Result should be of type Double!", -1.5d, wrap1.getValue());
	}

	@Test
	public void testDecrementWithIntegers() {
		ValueWrapper wrap1 = new ValueWrapper(2);
		wrap1.decrement(4);

		assertEquals("Result should be of type Integer!", -2, wrap1.getValue());
	}

	@Test
	public void testDecrementWithNullValue() {
		ValueWrapper wrap1 = new ValueWrapper(2);
		wrap1.decrement(null);

		assertEquals("Result should be Integer value of 2!", 2, wrap1.getValue());
	}

	@Test
	public void testDecrementExistingNullValue() {
		ValueWrapper wrap1 = new ValueWrapper(null);
		wrap1.decrement(4d);

		assertEquals("Result should be of Double value of -4!", -4d, wrap1.getValue());
	}

	@Test
	public void testDecrementWithDoubleStringValue() {
		ValueWrapper wrap1 = new ValueWrapper(2);
		wrap1.decrement("4.5");

		assertEquals("Result should be Double value of -2.5!", -2.5d, wrap1.getValue());
	}

	@Test
	public void testDecrementWithDoubleScientificStringValue() {
		ValueWrapper wrap1 = new ValueWrapper(2);
		wrap1.decrement("1E-2");

		assertEquals("Result should be Double value of 1.99!", 1.99d, wrap1.getValue());
	}

	@Test
	public void testDecrementWithIntegerStringValue() {
		ValueWrapper wrap1 = new ValueWrapper(null);
		wrap1.decrement("4");

		assertEquals("Result should be of Integer value of -4!", -4, wrap1.getValue());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testDecrementWithInvalidValue() {
		ValueWrapper wrap1 = new ValueWrapper(3);
		wrap1.decrement('c');	//must throw
	}

	/** TEST MULTIPLY*/
	@Test
	public void testMultiplyWithDoubles() {
		ValueWrapper wrap1 = new ValueWrapper(2.5d);
		wrap1.multiply(-4.5d);

		assertEquals("Result should be of type Double!", -11.25, wrap1.getValue());
	}

	@Test
	public void testMultiplyWithDoubleAndInteger() {
		ValueWrapper wrap1 = new ValueWrapper(2.5d);
		wrap1.multiply(4);

		assertEquals("Result should be of type Double!", 10d, wrap1.getValue());
	}

	@Test
	public void testMultiplyWithIntegers() {
		ValueWrapper wrap1 = new ValueWrapper(2);
		wrap1.multiply(4);

		assertEquals("Result should be of type Integer!", 8, wrap1.getValue());
	}

	@Test
	public void testMultiplyWithNullValue() {
		ValueWrapper wrap1 = new ValueWrapper(2);
		wrap1.multiply(null);

		assertEquals("Result should be Integer value of 0!", 0, wrap1.getValue());
	}

	@Test
	public void testMultiplyExistingNullValue() {
		ValueWrapper wrap1 = new ValueWrapper(null);
		wrap1.multiply(4d);

		assertEquals("Result should be of Double value of 0!", 0d, wrap1.getValue());
	}

	@Test
	public void testMultiplyWithDoubleStringValue() {
		ValueWrapper wrap1 = new ValueWrapper(2);
		wrap1.multiply("4.5");

		assertEquals("Result should be Double value of 9!", 9d, wrap1.getValue());
	}

	@Test
	public void testMultiplyWithDoubleScientificStringValue() {
		ValueWrapper wrap1 = new ValueWrapper(2);
		wrap1.multiply("1E-2");

		assertEquals("Result should be Double value of 0.02!", 0.02d, wrap1.getValue());
	}

	@Test
	public void testMultiplyWithIntegerStringValue() {
		ValueWrapper wrap1 = new ValueWrapper(1);
		wrap1.multiply("4");

		assertEquals("Result should be of Integer value of 4!", 4, wrap1.getValue());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testMultiplyWithInvalidValue() {
		ValueWrapper wrap1 = new ValueWrapper(3);
		wrap1.multiply('c');	//must throw
	}

	/** TEST DIVIDE*/
	@Test
	public void testDivideWithDoubles() {
		ValueWrapper wrap1 = new ValueWrapper(2.5d);
		wrap1.divide(-1d);

		assertEquals("Result should be of type Double!", -2.5, wrap1.getValue());
	}

	@Test
	public void testDivideWithDoubleAndInteger() {
		ValueWrapper wrap1 = new ValueWrapper(10d);
		wrap1.divide(4);

		assertEquals("Result should be of type Double!", 2.5d, wrap1.getValue());
	}

	@Test
	public void testDivideWithIntegers() {
		ValueWrapper wrap1 = new ValueWrapper(8);
		wrap1.divide(4);

		assertEquals("Result should be of type Integer!", 2, wrap1.getValue());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testDivideWithNullValue() {
		ValueWrapper wrap1 = new ValueWrapper(2);
		wrap1.divide(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testDivideWithZero() {
		ValueWrapper wrap1 = new ValueWrapper(2);
		wrap1.divide(0);
	}

	@Test
	public void testDivideExistingNullValue() {
		ValueWrapper wrap1 = new ValueWrapper(null);
		wrap1.divide(4d);

		assertEquals("Result should be of Double value of 0!", 0d, wrap1.getValue());
	}

	@Test
	public void testDivideWithDoubleStringValue() {
		ValueWrapper wrap1 = new ValueWrapper(2);
		wrap1.divide("1.0");

		assertEquals("Result should be Double value of 2!", 2d, wrap1.getValue());
	}

	@Test
	public void testDivideWithDoubleScientificStringValue() {
		ValueWrapper wrap1 = new ValueWrapper(2);
		wrap1.divide("1E-2");

		assertEquals("Result should be Double value of 200!", 200d, wrap1.getValue());
	}

	@Test
	public void testDivideWithIntegerStringValue() {
		ValueWrapper wrap1 = new ValueWrapper(1);
		wrap1.divide("4");

		assertEquals("Result should be of Integer value of 0!", 0, wrap1.getValue());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testDivideWithInvalidValue() {
		ValueWrapper wrap1 = new ValueWrapper(3);
		wrap1.divide('c');	//must throw
	}

	/**TEST number compare*/
	@Test
	public void testNumCompareDoubleAndInteger() {
		ValueWrapper wrap1 = new ValueWrapper(2);

		assertEquals("Must return zero!", wrap1.numCompare(2.0), 0);
	}

	@Test
	public void testNumCompareDoubleAndStringInteger() {
		ValueWrapper wrap1 = new ValueWrapper(2);

		assertEquals("Must return zero!", wrap1.numCompare("2.0"), 0);
	}

	@Test
	public void testNumCompareDoubleAndNull() {
		ValueWrapper wrap1 = new ValueWrapper(2);

		assertTrue("Must return number greater then 0!", wrap1.numCompare(null) > 0);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testNumCompareWithInvalidArgument() {
		ValueWrapper wrap1 = new ValueWrapper(2);

		assertEquals("Must return zero!", wrap1.numCompare('c'), 0);
	}

}
