package hr.fer.zemris.java.cstr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;




public class CStringTests {
	
	//@Ignore
	@Test
	public void testIfSame() {
		String str = "abcdef";
		CString string = CString.fromString("abcdef");
		
		assertEquals("Method fromString went wrong! ", str, string.toString());
	}
	
	//@Ignore
	@Test
	public void testEmpty() {
		String str = "";
		CString string = CString.fromString("");
		
		assertEquals("Should return empty string! ", str, string.toString());
	}
	
	//@Ignore
	@Test(expected=IllegalArgumentException.class)
	public void testNullInput() {
		// this should throw
		new CString(null, 0, 1);
	}
	
	//@Ignore
	@Test(expected=IllegalArgumentException.class)
	public void testWrongIndex() {
		// this should throw (starting from index 2 possible length is from 0 to 2
		// but given length is 5
		new CString(new char[]{'a', 'b', 'c', 'd'}, 2, 5);
	}
	
	//@Ignore
	@Test
	public void testSufixAndPrefix() {
		// checks if returns true for valid sufix and prefix
		CString fullString = CString.fromString("vrati ovo 1 put ok ok "
				+ "jedan nije 2 ni 3 i");
		
		CString prefixCheck = CString.fromString("vrati ovo 1 ");
		CString sufixCheck = CString.fromString(" ni 3 i");
		
		assertEquals("Method startsWith went wrong! ", fullString.startsWith(prefixCheck), 
				true);
		assertEquals("Method endsWith went wrong! ", fullString.endsWith(sufixCheck), 
				true);
		
		// lets try if returns false when needed
		CString prefixCheck2 = new CString(CString.fromString("vrati ovo 2"));
		CString sufixCheck2 = new CString(CString.fromString("2 ni 3 j"));
		
		assertEquals("Method startsWith went wrong! ", fullString.startsWith(prefixCheck2), 
				false);
		assertEquals("Method endsWith went wrong! ", fullString.endsWith(sufixCheck2), 
				false);
	}
	
	//@Ignore
	@Test
	public void testLength() {
		char[] ch = new char[] {'1','2','3','4','5','6','7','8','9','0','1','2','3'};
		
		assertEquals("Method length() returns wrong value! ", 
					new CString(ch, 3, 7).length(), 7);
	}
	
	//@Ignore
	@Test
	public void testCharAt() {
		char[] ch = new char[] {'1','2','3','4','5','6','7','8','9','0','1','2','3'};
		
		assertEquals("Method charAt returns wron char! ", new CString(ch, 3, 7).charAt(2), 
					'6');
	}
	
	//@Ignore
	@Test
	public void testToCharArray() {
		char[] ch = new char[] {'0','1','2','3','4','5','6','7','8','9','0','1','2','3'};
		char[] string = new CString(ch).toCharArray();
		
		// lengths must be the same
		assertEquals("Length should be equal."  , ch.length, string.length);
		
		// now test char by char 
		for (int i = 0; i < ch.length; i++) {
			assertEquals("To char method returns wrong char array. ", ch[i], string[i]);
		}
	}
	
	//@Ignore
	@Test
	public void testToStringMethod() {
		char[] ch = new char[] {'1','2','3','4','5','6','7','8','9','0','1','2','3'};
		//expected string
		String string = "1234567890123";
		CString cString = new CString(ch, 0, ch.length);
		
		assertEquals("toString method returns unexpected.", cString.toString(), 
					string);
	}
	
	//@Ignore
	@Test
	public void testIndexOfMethod() {
		char[] ch = new char[] {'1','\\','6',' ','5','6',' ','8','9','0','\'','2','3'};
		//chars to find position in CString -> first, last and middle positions tested
		// find2 test if -1
		char find = ' ', find1 = '1', find2 = 'a', find3 = '\\', find4 = '3';
		CString cString = new CString(ch);
		
		assertEquals("indexOf went wrong ", cString.indexOf(find), 3);
		assertEquals("indexOf went wrong ", cString.indexOf(find1), 0);
		assertEquals("indexOf went wrong ", cString.indexOf(find2), -1);
		assertEquals("indexOf went wrong ", cString.indexOf(find3), 1);
		assertEquals("indexOf went wrong ", cString.indexOf(find4), 12);
	}
	
	//@Ignore
	@Test
	public void testContainsMethod() {
		char[] ch = new char[] {'1','2','3','4','5','6','7','8','9','0','a','b','c'};
		CString cString = new CString(ch, 0, ch.length);
		
		//test0 (identical first) , must be true
		CString test = CString.fromString("1234567890abc");
		assertEquals("Contains not working properly.", cString.contains(test), 
					true);
		
		//test0 (length 1) , must be true
		CString test0 = CString.fromString("9");
		assertEquals("Contains not working properly.", cString.contains(test0), 
					true);
	
		//test1 (starting from 0) , must be true
		CString test1 = CString.fromString("123");
		assertEquals("Contains not working properly.", cString.contains(test1), 
					true);
		
		//test2 (starting from 4) , must be true
		CString test2 = CString.fromString("567890");
		assertEquals("Contains not working properly.", cString.contains(test2), 
					true);
		
		//test3 (end of CString), must be true
		CString test3 = CString.fromString("abc");
		assertEquals("Contains not working properly.", cString.contains(test3), 
					true);
		
		//test4 (test.length > tested.length), must be false
		CString test4 = CString.fromString("1234567890abc1");
		assertEquals("Contains not working properly.", cString.contains(test4), 
					false);
		
		//test5 (starting from index 0 with true, one char is wrong), must be false
		CString test5 = CString.fromString("1235");
		assertEquals("Contains not working properly.", cString.contains(test5), 
					false);
		
		//test6 (something with length 1), must be false
		CString test6 = CString.fromString("d");
		assertEquals("Contains not working properly.", cString.contains(test6), 
					false);
		
		//test7 (end of string check), must be false
		CString test7 = CString.fromString("abcd");
		assertEquals("Contains not working properly.", cString.contains(test7), 
					false);
	}
	
	//@Ignore
	@Test (expected=IllegalArgumentException.class)
	public void testSubstringMethod() {
		// length is 29
		String str = "String za provjeru substringa";
		CString cString = CString.fromString(str);
		
		
		//test1 lets try if starts with of CString
		assertEquals("Substring not working properly.", cString.substring(0, 2), 
					CString.fromString("St"));
		
		//test2 lets try if ends with of CString
		assertEquals("Substring not working properly.", cString.substring(25, 35), 
					CString.fromString("inga"));
		
		//test3 lets try something in middle
		assertEquals("Substring not working properly.", cString.substring(5, 15), 
					CString.fromString("g za provj"));
		
		//test4 lets try if both out of range, but still start <= end
		assertNotNull("Didn't expect null here.", cString.substring(30, 35));
		
		//test5 must throw because start > end
		cString.substring(15, 14);
	}
	
	//@Ignore
	@Test (expected=IllegalArgumentException.class)
	public void testSubstring2Method() {
		String str = "String za provjeru substringa";
		CString cString = CString.fromString(str);
		
		//test6 lets try start index == end index
		assertEquals("Substring not working properly.", cString.substring(5, 5), 
					CString.fromString(""));
		
		//test7 must throw because start < 0
		cString.substring(-1, 14);
	}
	
	//@Ignore
	@Test
	public void testLeftAndRight() {
		// CString.left(int n) and CString.right(int n) calls substring
		// substring is checked, lets try only basics
		
		String str = "String za provjeru substringa";
		CString cString = CString.fromString(str);
		
		
		
		//test0 is left correct
		assertEquals("left() not working properly.", cString.left(11), 
					CString.fromString("String za p"));
		
		//test1 is right correct 
		assertEquals("right() not working properly.", cString.right(7), 
					CString.fromString("stringa"));
	}
	
	//@Ignore
	@Test(expected=IllegalArgumentException.class)
	public void testAddMethod() {
		// lets see if concatenation works
		CString cString1 = CString.fromString("1111111");
		CString cString2 = CString.fromString("+");
		CString cString3 = CString.fromString("2222222");
		CString cString4 = CString.fromString("");
		CString cString5 = CString.fromString("=");
		CString cString6 = CString.fromString("3333333");
		
		CString finalString = cString1.add(cString2)
									  .add(cString3).add(cString4)
									  .add(cString5).add(cString6);
		
		assertEquals("Add not working properly.", finalString, 
				CString.fromString("1111111+2222222=3333333"));
		
		// lets check null, must throw
		cString4.add(null);
	}
	
	//@Ignore
	@Test
	public void testReplaceAllCharMethod() {
		CString cString = new CString(CString.fromString("1111111111 33 33 33 33 3"));
		
		// replace 3 with 2
		assertEquals("ReplaceAll char not working properly.", cString.replaceAll('3', '2'), 
				CString.fromString("1111111111 22 22 22 22 2"));
		
		// replace \\ with 1 (should not throw)
		CString rep ;
		assertNotNull(rep = cString.replaceAll('\\', '2'));
		
		// and rep variable should be same as original
		assertEquals("ReplaceAll char not working properly.", rep, 
				CString.fromString("1111111111 33 33 33 33 3"));
	}
	
	//@Ignore
	@Test (expected=IllegalArgumentException.class)
	public void testReplaceAllCStringMethod() {
		CString cString = new CString(CString.fromString("1111111111 33 33 33 33 3"));
		
		// replace 1 with 2
		CString test = cString.replaceAll( CString.fromString("1"), 
											CString.fromString("2"));
		assertEquals("ReplaceAll CString not working properly.", test
				, CString.fromString("2222222222 33 33 33 33 3"));
		
		// replace 11 with 11 'space' 1
		CString test1 = cString.replaceAll( CString.fromString("11"), 
				CString.fromString("11 1"));
		assertEquals("ReplaceAll CString not working properly.", test1
				, CString.fromString("11 111 111 111 111 1 33 33 33 33 3"));
		
		// replace 33 with nothing (delete)
		CString test2 = cString.replaceAll( CString.fromString("33"), 
				CString.fromString(""));
		assertEquals("ReplaceAll CString not working properly.", test2
				, CString.fromString("1111111111     3"));
		
		// replace not existing char sequence, should be same as cString
		CString test3 = cString.replaceAll( CString.fromString("333"), 
				CString.fromString("258"));
		assertEquals("ReplaceAll CString not working properly.", test3
				, cString);
		
		// this should throw
		cString.replaceAll( null,CString.fromString(""));
	}
	
}
