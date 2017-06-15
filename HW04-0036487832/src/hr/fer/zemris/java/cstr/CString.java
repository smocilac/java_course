/**
 * 
 */
package hr.fer.zemris.java.cstr;



/**
 * Class offers similar functionality as the old official implementation of the String 
 * class: it represents unmodifiable strings on which substring methods (and similar)
 * are executed in O(1) complexity, which is achieved by sharing the character array.
 * 
 * @author Stjepan
 * @version 1.0
 *
 */
public class CString {
	/** Unmodifiable private char array is used for character storage. */
	private final char data[];
	
	/** The offset is the first index of the storage that is used. */
	private int offset;
	
	/** Data length is the number of characters in the String. */
	private int length;
	
	/**
	 * Allocates a new CString that contains characters from a subarray of the character 
	 * array argument. The offset argument is the index of the first character of the 
	 * subarray and the count argument specifies the length of the subarray. The contents 
	 * of the subarray are copied; subsequent modification of the character array does 
	 * not affect the newly created string. If offset and length is zero, empty CString is 
	 * created
	 * 
	 * @param data source of characters to copy
	 * @param offset where to begin
	 * @param length how many characters to copy
	 * @throws IllegalArgumentException if offset or length less than zero, or offset plus
	 * 			length is greater than length of character array from argument.
	 */
	public CString(char[] data, int offset, int length){
		if ( data == null) {
			throw new IllegalArgumentException("Input data must not be null");
		}
		if (offset < 0) {
            throw new IllegalArgumentException("Offset must be 0 or greater.");
        }
        if (length <= 0) {
            if (length < 0) {
                throw new IllegalArgumentException("Length must be 0 or greater.");
            }
            if (offset <= data.length) {
                this.data = new char[0];
                return;
            }
        }
        if (offset > data.length - length) {
            throw new IllegalArgumentException("Offset must not be greater than length");
        }
		this.data = copyOfRange(data, offset, offset+length);;
		this.offset = 0;
		this.length = length;
	}
	
	/**
	 * Allocates a new CString so that it represents the sequence of characters currently 
	 * contained in the character array argument. The contents of the character array are 
	 * copied. Subsequent modification of the character array does not affect the newly 
	 * created string.
	 * 
	 * @param data source of characters to copy
	 */
	public CString(char[] data){
		this (data, 0, data.length);
	}
	
	/**
	 * Private constructor which takes 4 parameters. 
	 * This constructor uses char sequence from argument instead of copying them ( so that 
	 * some methods like substring and similar could work in O(1) complexity). 
	 * 
	 * New char sequence is created iff {@code offset} of {@code data} is zero  and 
	 * {@code length} is zero.
	 * 
	 * @param data 
	 * 				Data contains some substring which contains needed sequence.
	 * 				Is valid if not null.
	 * @param offset 
	 * 				Index of starting char.
	 * 				Is valid if in range from offset to offset + range.
	 * @param length 
	 * 				Defines how many chars to take.
	 * 				Must be equal or greater than zero.
	 * @param last 
	 * 				Index of first not included char
	 * 
	 * @throws IllegalArgumentException 
	 * 				Thrown if some of argument is not valid.
	 */
	private CString(char[] data, int offset, int length, int last) {
		if ( data == null) {
			throw new IllegalArgumentException("Input data must not be null");
		}
		if (offset < 0) {
            throw new IllegalArgumentException("Offset must be 0 or greater.");
        }
        if (length <= 0) {
            if (length < 0) {
                throw new IllegalArgumentException("Length must be 0 or greater.");
            }
            if (offset <= data.length) {
                this.data = new char[0];
                return;
            }
        }
        if (offset > data.length - length) {
            throw new IllegalArgumentException("Offset must not be greater than length");
        }
        
		this.data = data;
		this.offset = offset;
		this.length = length;
	}
	
	/**
	 * Initializes a newly created CString object so that it represents the same sequence 
	 * of characters as the argument. In other words, the newly created string is a copy 
	 * of the argument string.
	 * 
	 * @param original CString object to copy
	 */
	public CString(CString original){
		int size = original.length();
		char[] originalValue = original.toCharArray();
		char[] value;
		if (originalValue.length > size) {
			int offs = original.offset;
			value = copyOfRange(originalValue, offs, offs+size);
		} else {
			value = originalValue;
		}
		offset = 0;
		length = value.length;
		data = value;
	}
	
	/**
	 * Returns new CString object which has the same character data as given String 
	 * object.
	 * 
	 * @param s String to take characters from
	 * @return new CString matching String from argument
	 */
	public static CString fromString(String s) {
		return new CString (s.toCharArray(), 0, s.length());
	}
	
	/**
	 * Returns the length of this cstring. The length is equal to the number of Unicode 
	 * code units in the string.
	 * 
	 * @return length of CStrin
	 */
	public int length() {
		return length;
	}
	
	/**
	 * Returns the char value at the specified index. An index ranges from 0 to 
	 * length() - 1. The first char value of the sequence is at index 0, the next at 
	 * index 1, and so on, as for array indexing. 
	 * 
	 * @param index index of character to return
	 * @return character at index from argument
	 * @throws IndexOutOfBoundsException if the index argument is negative or not less 
	 * 			than the length of this string.
	 */
	public char charAt(int index) {
		if ((index < 0) || (index >= length)) {
			throw new StringIndexOutOfBoundsException(index);
		}
		
		return data[index + offset];
	}
	
	/**
	 * Allocates a new array of length equals to length of this CString object (not its 
	 * internal array ), copies string content into it and returns it.
	 * 
	 * @return string content
	 */
	public char[] toCharArray() {
		char charArray[] = new char[length];
		System.arraycopy(data, offset, charArray, 0, length);
		return charArray;
	}
	
	/**
	 * used for conversion of CString into String
	 * {@inheritDoc}
	 * 
	 * @return String value of CString object
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = offset; i < offset + length ; i++) {
			sb.append(data[i]);
		}
		
		return sb.toString();
	}
	
	/**
	 * Returns index of first occurrence of char, if char don't exist returns -1
	 * 
	 * @param c
	 * @return
	 */
	public int indexOf(char c) {
		for (int i = 0; i < length; i++) {
			if (data [i]== c) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns true if this string begins with given string, false otherwise.
	 * 
	 * @param s prefix
	 * @return true if the character sequence represented by the argument is a prefix of 
	 * the substring of this object, false otherwise.
	 */
	public boolean startsWith(CString s) {
		int thisOffset = offset;
		int prefixOffset = s.offset;
		int prefixLength = s.length();
		char[] prefixData = s.toCharArray();
		
		while (--prefixLength >= 0) {
			if (data[thisOffset++] != prefixData[prefixOffset++]) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Returns true if this string ends with given string, false otherwise
	 * 
	 * @param s sufix
	 * @return true if the character sequence represented by the argument is a sufix of 
	 * the substring of this object, false otherwise.
	 */
	public boolean endsWith(CString s) {
		int prefixOffset = s.offset;
		int prefixLength = s.length();
		char[] prefixData = s.toCharArray();
		int thisOffset = length - prefixLength;
		
		if (thisOffset < 0 ) return false;
		
		while (--prefixLength >= 0) {
			if (data[thisOffset++] != prefixData[prefixOffset++]) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Returns true if this string contains given string at any position, false otherwise
	 * 
	 * @param s CString to find 
	 * @return true if string from argument is present, false otherwise
	 */
	public boolean contains(CString s) {
		if (s == null) {
			return false;
		}
		
		int sLength = s.length();
		
		char[] sData = s.toCharArray();
		
		if ( sLength > length) return false;
		
		for ( int i = offset, max = offset + length - sLength + 1; i < max; i++) {
			int j = i;
			int p = 0;
			
			while (p < (sLength) && data[j] == sData[p]) {
				j++; p++;
			}
			
			if (p == sLength) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns new CString which represents a part of original string; position endIndex 
	 * does not belong to the substring; it holds: startIndex greater or equal to 0, 
	 * endIndex greater or equal to startIndex;
	 * its complexity is O(1)
	 * 
	 * @param startIndex index to begin with
	 * @param endIndex index to end with
	 * @return new CString (part of original string)
	 * @throws IllegalArgumentException if cannot hold index
	 */
	public CString substring(int startIndex, int endIndex) {
		if (startIndex < offset ) {
			throw new IllegalArgumentException("Starting index must be greater than zero.");
		}
		if (endIndex < startIndex) {
			throw new IllegalArgumentException("End index must be greater or equal to "
					+ "start index.");
		}
		// if out of range return empty CString
		if (startIndex > length + offset) {
			return new CString(new char[0]);
		}
		// if endIndex is greater than length, than new endIndex is position of last 
		// character in data (length + offset)
		endIndex = (endIndex >= length + offset) ? (length + offset ) : endIndex;
		
		return new CString(this.toCharArray(), startIndex, endIndex - startIndex, endIndex);
	}
	
	/**
	 * Returns new CString which represents starting part of original string and is of 
	 * length n; throw an exception if this can not be constructed; n greater or equal to 0
	 * 
	 * It is based on {@link #substring(int, int)} method.
	 * 
	 * @param n how many characters to take (from begin)
	 * @return CString object with n characters copied
	 * @throws IllegalArgumentException if argument is less than zero
	 */
	public CString left(int n) {
		return substring(0, n);
	}
	
	/**
	 * Returns new CString which represents ending part of original string and is of 
	 * length n.
	 * 
	 * It is based on {@link #substring(int, int)} method.
	 * 
	 * @param n how many characters to copy
	 * @return new CString, ending part of original string
	 * @throws IllegalArgumentException if this can not be constructed,  n must be 
	 * 								 greater or equal to 0
	 */
	public CString right(int n) {
		return substring(offset + length - n, offset + length);
	}
	
	/**
	 * Creates a new CString which is concatenation of current and given string
	 * 
	 * @param s string to concatenate
	 * @return concatenated CString object
	 * @throws IllegalArgumentException if argument is null
	 */
	public CString add(CString s) {
		if (s == null) {
			throw new IllegalArgumentException("Cannot add null.");
		}
		
		char[] newData = new char[length + s.length ];
		
		System.arraycopy(data, offset, newData, 0, length);
		System.arraycopy(s.toCharArray(), s.offset, newData, length, s.length());
				
		return new CString(newData);
	}
	
	/**
	 * Creates a new CString in which each occurrence of old character is replaces with 
	 * new character
	 * 
	 * @param oldChar the old character
	 * @param newChar the new character
	 * @return
	 */
	public CString replaceAll(char oldChar, char newChar) {
		if (oldChar != newChar) {
			int len = length;
			int i = -1;
			char[] val = data;  

			int off = offset;  

			while (++i < len) {
				if (val[off + i] == oldChar) {
					break;
				}
			}
			if (i < len) {
				char buf[] = new char[len];
				for (int j = 0 ; j < i ; j++) {
					buf[j] = val[off+j];
				}
				while (i < len) {
					char c = val[off + i];
					buf[i] = (c == oldChar) ? newChar : c;
					i++;
				}
				return new CString( buf, 0, len);
			}
		}
		return this;
	}
	
	/**
	 * Creates a new CString in which each occurrence of old substring is replaces with 
	 * the new substring
	 * 
	 * @param oldStr the old string
	 * @param newStr the new string
	 * @return replaced string
	 * @throws IllegalArgumentException 
	 * 				if one of the argument is null
	 */
	public CString replaceAll(CString oldStr, CString newStr) {
		if (oldStr == null || newStr == null) {
			throw new IllegalArgumentException("Cannot replace with null");
		}
		if (!contains(oldStr)) { // nothing to replace
			return this;
		}
		String from = oldStr.toString();
		String to = newStr.toString();
		StringBuilder builder = new StringBuilder(this.toString());
		int index = builder.indexOf(from);
	    while (index != -1)
	    {
	        builder.replace(index, index + from.length(), to);
	        index += to.length(); // Move to the end of the replacement
	        index = builder.indexOf(from, index);
	    }
		
	    String makeStr = builder.toString();
	    
		return CString.fromString(makeStr);
	}
	
	/**
	 * Copies the specified range of the specified array into a new array.
	 * 
	 * @param original the array from which a range is to be copied
	 * @param from the initial index of the range to be copied, inclusive
	 * @param to  the final index of the range to be copied, exclusive. 
	 * 				(This index may lie outside the array.)
	 * @return copy
	 */
	private static char[] copyOfRange(char[] original, int from, int to) {
		int newLength = to - from;
		if (newLength < 0)
			throw new IllegalArgumentException(from + " > " + to);
		char[] copy = new char[newLength];
		System.arraycopy(original, from, copy, 0,
						Math.min(original.length - from, newLength));
		return copy;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null ) return false;
		if (!(object instanceof CString)) return false;
		if (((CString)object).length() != length) return false;
		
		return contains(((CString)object));
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
