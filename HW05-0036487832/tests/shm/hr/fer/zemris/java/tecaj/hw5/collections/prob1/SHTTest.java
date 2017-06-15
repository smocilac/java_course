package hr.fer.zemris.java.tecaj.hw5.collections.prob1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Ignore;
import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;
import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable.TableEntry;


public class SHTTest {
	
	// check constructor
	//@Ignore
	@Test
	public void testConstructor() {
		SimpleHashtable<String, String> constructor = new SimpleHashtable<>(1);
		
		assertEquals(constructor.size(), 0);
	}
	
	//@Ignore
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNegative() {
		@SuppressWarnings("unused")
		SimpleHashtable<String, String> constructor = new SimpleHashtable<>(-5);
	}

	//@Ignore
	@Test
	public void testPutAndGetMethod() {
		SimpleHashtable<String, String> constructor = new SimpleHashtable<>(1);
		
		constructor.put("jedan" , "dva");
		assertEquals("Put or get not working proparly", constructor.get("jedan"), "dva");
		
		constructor.put("jedan" , "tri");
		assertEquals("Put or get not working proparly", constructor.get("jedan"), "tri");
		
		assertNull("Get not returning null.", constructor.get("pet"));
	}
	
	
	//@Ignore
	@Test (expected=IllegalArgumentException.class)
	public void testPutMethodNullValue() {
		SimpleHashtable<String, String> constructor = new SimpleHashtable<>(1);
		
		constructor.put(null, "dva");
	}
	
	//@Ignore
	@Test
	public void testReallocating() {
		SimpleHashtable<String, String> students = new SimpleHashtable<>(1);
		
		// 0.75 * 8 = 6, expected capacity 8
		students.put("Ivana", "Ivana");
		students.put("Jasna", "Jasna");
		students.put("Luka", "Luka");
		students.put("Rajko", "Rajko");
		students.put("Kristina", "Kristina");
		students.put("Ime", "Ime");
		
		assertEquals("Expected size was 6", students.size(), 6);
		
		// reallocate to 16
		students.put("Jos", "Jos");
		students.put("Jo1s", "Jos1");
		students.put("Jos1s", "Jos1s");
		
		assertEquals("Expected size was 9", students.size(), 9);
	}
	
	//@Ignore
	@Test
	public void testRemoveMethod() {
		SimpleHashtable<String, String> students = new SimpleHashtable<>(1);
		
		students.put("Ivana", "Ivana");
		students.put("Jasna", "Jasna");
		students.put("Luka", "Luka");
		students.put("Rajko", "Rajko");
		students.put("Kristina", "Kristina");
		students.put("Ime", "Ime");
		
		students.remove("Jasna");
		
		assertNull("Expected null", students.get("Jasna"));
		
		students.remove(null); // must not throw
		
		students.put("Jasna", "Jasna");
		
		assertEquals("Expected jasna!", students.get("Jasna"), "Jasna");
	}
	
	//@Ignore
	@Test
	public void testContainsKeyMethod() {
		SimpleHashtable<String, String> students = new SimpleHashtable<>();
		
		students.put("Ivana", "Ivana");
		students.put("Jasna", "Jasna");
		students.put("Luka", "Luka");
		students.put("Rajko", "Rajko");
		students.put("Kristina", "Kristina");
		students.put("Ime", "Ime");
		
		assertTrue("Should contain this.", students.containsKey("Jasna"));
		
		students.remove("Rajko");
		
		assertFalse("Must not contain", students.containsKey("Rajko"));
		
		assertFalse("Must not contain null!", students.containsKey(null));
	}
	
	//@Ignore
	@Test
	public void testClearAndIsEmptyMethods() {
		SimpleHashtable<String, String> students = new SimpleHashtable<>();
		
		students.put("Ivana", "Ivana");
		students.put("Jasna", "Jasna");
		students.put("Luka", "Luka");
		students.put("Rajko", "Rajko123");
		students.put("Kristina", "Kristina");
		students.put("Ime", "Ime");

		assertTrue("Must contain", students.containsValue("Rajko123"));
		
		students.clear();
		
		assertTrue("Must be empty.", students.isEmpty());
		assertFalse("Must not contain", students.containsKey("Rajko123"));
	}
	
	//@Ignore
	@Test
	public void testCartesianProductOnIterator(){
		SimpleHashtable<String, String> students = new SimpleHashtable<>();
		StringBuilder builder = new StringBuilder();
		
		students.put("Ivana", "a");
		students.put("Jasna", "b");
		students.put("Luka", "c");
			
		String str1 = "(Ivana => a) - (Ivana => a)\n"
					+"(Ivana => a) - (Jasna => b)\n"
					+"(Ivana => a) - (Luka => c)\n"
					+"(Jasna => b) - (Ivana => a)\n"
					+"(Jasna => b) - (Jasna => b)\n"
					+"(Jasna => b) - (Luka => c)\n"
					+"(Luka => c) - (Ivana => a)\n"
					+"(Luka => c) - (Jasna => b)\n"
					+"(Luka => c) - (Luka => c)\n";
		
		for(SimpleHashtable.TableEntry<String,String> pair1 : students) {
			for(SimpleHashtable.TableEntry<String,String> pair2 : students) {
				builder.append(String.format("(%s => %s) - (%s => %s)",
						pair1.getKey(), pair1.getValue(),
						pair2.getKey(), pair2.getValue()));
				builder.append("\n");
			}
		}
		String str = builder.toString();
			
		assertEquals("Not expected that from iterator. ", str1, str);
	}
	
	
	//@Ignore
	@Test (expected=ConcurrentModificationException.class)
	public void testModificationCountOnIterator(){
		SimpleHashtable<String, String> students = new SimpleHashtable<>();
		
		students.put("Ivana", "a");
		students.put("Jasna", "b");
		students.put("Luka", "c");
		students.put("Luka1", "c");
		students.put("Luka2", "c");
		students.put("Luka3", "c");
		
		Iterator<TableEntry<String, String>> iter = students.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,String> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				students.remove("Ivana"); // should throw here
			}
		}
	}
	
		
	//@Ignore
	@Test(expected=IllegalStateException.class)
	public void testMultipleRemoveOnIterator() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ivana", 1);
		examMarks.put("Jasna", 1);
		examMarks.put("Luka", 1);
		examMarks.put("Luka1", 1);
		examMarks.put("Luka2", 1);
		examMarks.put("Luka3", 1);

		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
			
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				iter.remove();
				iter.remove();// should throw here
			}
		}
	}
		
	//@Ignore
	@Test
	public void testUpdateValueOnIterator() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Kruno", 1);
		examMarks.put("Jasna", 1);
		examMarks.put("Luka", 1);
		examMarks.put("Luka1", 1);
		examMarks.put("Luka2", 1);
		examMarks.put("Luka3", 1);

		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
			
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Kruno")) {
				examMarks.put("Kruno", 1);//should not throw
			}
		}
	}

}
