package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.EmptyStackException;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ObjectMultistackTests {
	//Mulistack = MS

	@Test(expected=EmptyStackException.class)
	public void testEmptyStack() {
		ObjectMultistack ms = new ObjectMultistack();
		ms.pop("nesto");		//empty stack
	}

	@Test(expected=IllegalArgumentException.class)
	public void testPushingNullKey() {
		ObjectMultistack ms = new ObjectMultistack();
		ms.push(null, new ValueWrapper("5"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testPushingNullValue() {
		ObjectMultistack ms = new ObjectMultistack();
		ms.push("firstStack", null);
	}

	@Test
	public void testPushingWrapperOfNullValue() {
		ObjectMultistack ms = new ObjectMultistack();
		ms.push("firstStack", new ValueWrapper(null));	//must pass
	}


	@Test
	public void testPop() {
		ObjectMultistack ms = new ObjectMultistack();
		ms.push("firstStack", new ValueWrapper(5));
		ms.push("firstStack", new ValueWrapper(7));
		assertEquals("Poped value should be 7!", ms.pop("firstStack").getValue(), 7);
	}

	@Test(expected=EmptyStackException.class)
	public void testPopWithEmptyStack() {
		ObjectMultistack ms = new ObjectMultistack();
		ms.push("firstStack", new ValueWrapper(5));
		ms.push("firstStack", new ValueWrapper(7));
		ms.pop("firstStack");
		ms.pop("firstStack");
		ms.pop("firstStack");		//must throw(empty stack)!
	}

	@Test(expected=IllegalArgumentException.class)
	public void testPopingWithNullArg() {
		ObjectMultistack ms = new ObjectMultistack();
		ms.push("firstStack", new ValueWrapper(5));
		ms.pop(null);				//must throw
	}

	@Test
	public void testPopingNullValueWrappper() {
		ObjectMultistack ms = new ObjectMultistack();
		ms.push("firstStack", new ValueWrapper(null));
		assertNull("Value of wrapper must be null!", ms.pop("firstStack").getValue());				//must not throw
	}

	@Test
	public void testPeekMethod() {
		ObjectMultistack ms = new ObjectMultistack();
		ms.push("firstStack", new ValueWrapper(5));
		ms.push("firstStack", new ValueWrapper("TEST"));
		assertEquals("Top value must be \"TEST\"!", ms.peek("firstStack").getValue(), "TEST");
		assertEquals("Top value must be \"TEST\"!", ms.peek("firstStack").getValue(), "TEST");
	}

	@Test(expected=EmptyStackException.class)
	public void testPeekWithEmptyStack() {
		ObjectMultistack ms = new ObjectMultistack();
		assertEquals("Top value must be \"TEST\"!", ms.peek("firstStack").getValue(), "TEST");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testPeekingWithNullArg() {
		ObjectMultistack ms = new ObjectMultistack();
		ms.pop(null);				//must throw
	}

	@Test//(expected=IllegalArgumentException.class)
	public void testIsEmptyMethodWithNullArgument() {
		ObjectMultistack ms = new ObjectMultistack();
		assertTrue("Stack must be empty!", ms.isEmpty(null));
	}

	@Test
	public void testIsEmptyMethod() {
		ObjectMultistack ms = new ObjectMultistack();
		assertTrue("Stack must be empty!", ms.isEmpty("neki stack"));
	}

	@Test
	public void testIsEmptyMethodWithRemovingEntries() {
		ObjectMultistack ms = new ObjectMultistack();
		ms.push("firstStack", new ValueWrapper(5));
		ms.pop("firstStack");
		assertTrue("Stack must be empty!", ms.isEmpty("firstStack"));
	}

}
