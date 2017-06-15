package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

// demonstrations of SimpleHashtable class from HW05.pdf
public class Demonstration {
	
	
	public static void main(String[] args) {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		
		// simple usage
		firstTask(examMarks);
		
		// simple check if iterator works properly
		thirdTask1(examMarks);
		
		// check if loop inside loop works, should write cartesian product on System out
		thirdTask2(examMarks);
		
		// removes ivana
		thirdTask3(examMarks);
		
		try {
			thirdTask4(examMarks);
		} catch (IllegalStateException e) {
			System.out.println("This must be written !!!!");
		}
		
		// lets put Kristina and Ivana back into table
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); 
		
		try {
			thirdTask5(examMarks);
		} catch (ConcurrentModificationException e) {
			System.out.println("This must be written too !!!!");
		}
	}
	
	private static void firstTask(SimpleHashtable<String,Integer> examMarks) {
		// query collection:
		Integer kristinaGrade = examMarks.get("Kristina");
		System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5
		// What is collection's size? Must be four!
		System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4
	}
	
	
	private static void thirdTask1(SimpleHashtable<String,Integer> examMarks) {
		
		for(SimpleHashtable.TableEntry<String,Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}
		
	}
	
	// cartesian product
	private static void thirdTask2(SimpleHashtable<String,Integer> examMarks) {
		for(SimpleHashtable.TableEntry<String,Integer> pair1 : examMarks) {
			for(SimpleHashtable.TableEntry<String,Integer> pair2 : examMarks) {
				System.out.printf(
								"(%s => %d) - (%s => %d)%n",
								pair1.getKey(), pair1.getValue(),
								pair2.getKey(), pair2.getValue()
				);
			}
		}
	}
	
	private static void thirdTask3(SimpleHashtable<String,Integer> examMarks) {
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				iter.remove(); // iterator removes current element
			}
		}
	}
	
	// will throw because of multiply call of remove() method
	private static void thirdTask4(SimpleHashtable<String,Integer> examMarks) {
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Kristina")) {
				iter.remove();
				iter.remove();
			}
		}
	}
	
	// will throw because call of method next() is done after table is modified.
	private static void thirdTask5(SimpleHashtable<String,Integer> examMarks) {
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			
			if(pair.getKey().equals("Kristina")) {
				examMarks.remove("Kristina");
			}
		}
	}
}
