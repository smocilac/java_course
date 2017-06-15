package hr.fer.zemris.java.tecaj.hw6.demo5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * This is class which allows eight operations: 
 * 1. Number of students with sum of points more than 25
 * 2. Number of students graded 5
 * 3. List of {@link StudentRecord} instances with grade 5
 * 4. List of {@link StudentRecord} instances with grade 5, sorted
 * 5. List of students jmbag which are graded with 1
 * 6. Map with grade as a key, and all students with that grade as a value
 * 7. Map with grade as a key, and number of students with that grade as a value
 * 8. Map with boolean as a key, every (of two) key has a list of students record. All 
 *    students which are graded 2 or more are in true list, others in false.
 *    
 * No argument is required. Program reads from textual file named "studenti"
 * 
 * @author Stjepan
 *
 */
public class StudentDemo {

	/**
	 * Main method of program. Calls all 8 functionality and writes result on system out
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(".\\studenti.txt"));
		} catch (IOException e) {
			System.err.println("Cannot read from studenti.txt file.");
			System.exit(-1);
		}
		
		List<StudentRecord> records = convert(lines);
		
		System.out.println(records.size());
		
		// 1.
		System.out.println(sumMoreThan25(records));
		System.out.println("****************************************");
		
		// 2.
		System.out.println(numberOfStudentsWithGradeFive(records));
		System.out.println("****************************************");
		
		// 3.
		studentsWithGradeFive(records).forEach(System.out::println);
		System.out.println("****************************************");
		
		// 4.
		studentsWithGradeFiveSorted(records).forEach(System.out::println);
		System.out.println("****************************************");
		
		// 5.
		didNotPassSorted(records).forEach(System.out::println);
		System.out.println("****************************************");
		
		// 6.
		mapByGradeKeyStudentsValue(records).forEach((u,v) -> 
					System.out.println(u.toString() + " " + v.toString()));
		System.out.println("****************************************");
		
		// 7.
		mapByGradeKeyNumberOfStudentsValue(records).forEach((u,v) -> 
					System.out.println(u.toString() + " " + v.toString()));
		System.out.println("****************************************");
		
		// 8.
		Map<Boolean, List<StudentRecord>> map = mapByPassedKeyStudentValue(records);
		System.out.println(map.get(true).size() + " true\n" + map.get(false).size() 
				+ " false");
		map.forEach((u,v) -> System.out.println(u.toString() + " " + v.toString()));
		System.out.println("****************************************");
		
	}

	/**
	 * 
	 * @param records
	 * @return Number of students with sum of points more than 25
	 */
	private static Long sumMoreThan25(List<StudentRecord> records){
		return records.stream()
				.filter(s -> s.getScoreLab() + s.getScoreMI() + s.getScoreZI() > 25)
				.collect(Collectors.counting());
	}
	
	/**
	 * 
	 * @param records
	 * @return Number of students graded 5
	 */
	private static long numberOfStudentsWithGradeFive(List<StudentRecord> records){
		return records.stream()
				.filter(s -> s.getFinalGrade() == 5)
				.collect(Collectors.counting());
	}
	
	/**
	 * 
	 * @param records
	 * @return List of {@link StudentRecord} instances with grade 5
	 */
	private static List<StudentRecord> studentsWithGradeFive(List<StudentRecord> records){
		return records.stream()
				.filter(s -> s.getFinalGrade() == 5)
				.collect(Collectors.toList());
	}
	
	/**
	 * 
	 * 
	 * @param records
	 * @return List of {@link StudentRecord} instances with grade 5, sorted
	 */
	private static List<StudentRecord> studentsWithGradeFiveSorted(
						List<StudentRecord> records){
		return records.stream()
				.filter(s -> s.getFinalGrade() == 5)
				.sorted(new Comparator<Object>() {
					@Override
					public int compare(Object o1, Object o2) {
						StudentRecord v = (StudentRecord) o1;
						StudentRecord k = (StudentRecord) o2;
						double sum1 =  (v.getScoreLab() + v.getScoreMI() + v.getScoreZI());
						double sum2 =  (k.getScoreLab() + k.getScoreMI() + k.getScoreZI());
						double diff = sum2 - sum1;
						if (Math.abs(diff) < 1){
							return diff < 0 ? -1 : 1;
						}
						return (int)diff;
					}
				})
				.collect(Collectors.toList());
	}
	
	/**
	 * 
	 * 
	 * @param records
	 * @return List of students jmbag which are graded with 1
	 */
	private static List<String> didNotPassSorted(List<StudentRecord> records) {
		return records.stream()
				.filter(s -> s.getFinalGrade() == 1)
				.sorted((u,v) -> u.getJmbag().compareTo(v.getJmbag()))
				.map(StudentRecord::getJmbag)
				.collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param records
	 * @return Map with grade as a key, and all students with that grade as a value
	 */
	private static Map<Integer, List<StudentRecord>> mapByGradeKeyStudentsValue(
					List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.groupingBy(StudentRecord::getFinalGrade));
	}
	
	/**
	 * 
	 * 
	 * @param records
	 * @return  Map with grade as a key, and number of students with that grade as a value
	 */
	private static Map<Integer, Long> mapByGradeKeyNumberOfStudentsValue(
			List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.groupingBy(StudentRecord::getFinalGrade, 
						Collectors.counting()));
	}
	
	/**
	 * 
	 * 
	 * @param records
	 * @return Map with boolean as a key, every (of two) key has a list of students record. 
	 * 			All students which are graded 2 or more are in true list, others in false.
	 */
	private static Map<Boolean, List<StudentRecord>> mapByPassedKeyStudentValue(
			List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.partitioningBy(e -> e.getFinalGrade() > 1));
	}
	
	
	/**
	 * Convert list of string to list of {@link StudentRecord} instances.
	 * If not possible to convert, program will terminate.
	 * 
	 * @param lines lines to convert
	 * @return list of {@link StudentRecord} instances
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> students = new ArrayList<>(500);
		
		for (String line: lines){
			String[] data = line.split("\\s+");
			if (data.length != 7) {
				break;
			}
			
			try {
				double mi = Double.parseDouble(data[3]);
				double zi = Double.parseDouble(data[4]);
				double lab = Double.parseDouble(data[5]);
				int grade = Integer.parseInt(data[6]);
				
				students.add(new StudentRecord(data[0], data[1], data[2], mi, 
						zi, lab, grade));
			} catch (NumberFormatException e){
				System.err.println("Cannot parse to number in line: " + line);
				System.exit(-1);
			}			
		}
		
		return students;
	}

}
