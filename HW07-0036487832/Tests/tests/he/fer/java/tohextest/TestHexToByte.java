package tests.he.fer.java.tohextest;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw07.crypto.Crypto;


public class TestHexToByte {
	
	//@Ignore
	@Test
	public void testSimple(){
		byte [] method = Crypto.hextobyte("1111aaaabbbb");
		byte [] array = new byte[] {17, 17, -86, -86, -69, -69};
		for (int i = 0; i < 6; i++){
			assertEquals(array[i], method[i]);
		}
	}
	
	//@Ignore
	@Test
	public void testAnother(){
		byte [] method = Crypto.hextobyte("1234567890");
		byte [] array = new byte[] {18, 52, 86, 120, -112};
		for (int i = 0; i < 5; i++){
			assertEquals(array[i], method[i]);
		}
	}
	
	//@Ignore
	@Test(expected = IndexOutOfBoundsException.class)
	public void testWillThrow(){
		@SuppressWarnings("unused")
		byte [] method = Crypto.hextobyte("123456789");
		
	}
	
}
