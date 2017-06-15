package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Crypto applications allows user to encrypt, decrypt or check sha digest of some file.
 * 
 * First argument is operation encrypt, decrypt or checksha.
 * 
 * Checksha requires one more argument, name of file to calculate digest.
 * Decrypt and encrypt requires name of file to encrypt/decrypt and name of file to store 
 * result of operation.
 * 
 * @author Stjepan
 *
 */
public class Crypto {
	/** crypto operation */
	private static CryptoOperation oper ;
	
	/** this is required for AES encrypt or decrypt mode*/ 
	private static int mode;
	
	/**
	 * Program to preform crypto operation.
	 * 
	 * @param args arguments of program
	 */
	public static void main(String[] args) {
		checkProgramArgumets(args);
		
		Scanner scanner = new Scanner (System.in);
		try{
			switch (oper){
				case CHECKSHA:
					checkSha(args[1], scanner);
					break;
				case DECRYPT:
					mode = Cipher.DECRYPT_MODE;
					decryptOrEncrypt(args[1], args[2], scanner);
					break;
				case ENCRYPT:
					mode = Cipher.ENCRYPT_MODE;
					decryptOrEncrypt(args[1], args[2], scanner);
					break;
			}
		} catch (Exception e){
			System.err.println("Unable to preform wanted operation. ");
			System.exit(-1);
		}
	}
	
	
	/**
	 * Preforms decrypt or encrypt operation.
	 * 
	 * @param src
	 * @param dest
	 * @param scanner
	 */
	private static void decryptOrEncrypt(String src, String dest, Scanner scanner) {
		System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. "
				+ "32 hex-digits):\n>");
		String keyText = scanner.nextLine();
		System.out.print("Please provide initialization vector as hex-encoded text (32 "
				+ "hex-digits):\n>");
		String ivText = scanner.nextLine();
		
		SecretKeySpec keySpec = new SecretKeySpec(hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));
		Cipher cipher = getChiperInstance(keySpec, paramSpec);
	
		BufferedInputStream in = getNewBufferedInputStream(src);
		BufferedOutputStream out = getNewBufferedOutputStream(dest);
	   
		Crypto.doCrypt(in, out, cipher);
		
		String o = oper == CryptoOperation.DECRYPT ? "Decryption" : "Encryption";
		
		System.out.println(o + " completed. Generated file" + dest + " based on "
				+ " file " + src);
	    
	}
	
	
	/**
	 * Does crypting.
	 * 
	 * @param in
	 * @param out
	 * @param ci
	 */
	public static void doCrypt(BufferedInputStream in, BufferedOutputStream out,
			Cipher ci) {
		int blSize = ci.getBlockSize();
	    byte [] buffer = new byte[blSize];
	    int size;
	    try {
			while ((size = in.read(buffer)) != -1) {
				if (size < blSize){
					try {
						out.write(ci.doFinal(buffer));
					} catch (IllegalBlockSizeException e) {
						System.err.println(e.getMessage());
						System.exit(-1);
					} catch (BadPaddingException e) {
						System.err.println(e.getMessage());
						System.exit(-1);
					}
					continue;
				}
				out.write(ci.update(buffer));
			}
			in.close();
			out.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}

	/**
	 * Gets new Cipher instance specified by arguments.
	 * 
	 * @param keySpec
	 * @param paramSpec
	 * @return
	 */
	private static Cipher getChiperInstance(SecretKeySpec keySpec, AlgorithmParameterSpec 
																			paramSpec) {
		Cipher cipher = null;
		
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(mode, keySpec, paramSpec);
		} catch (NoSuchAlgorithmException e1) {
			System.err.println(e1.getMessage());
			System.exit(-2);
		} catch (NoSuchPaddingException e1) {
			System.err.println(e1.getMessage());
			System.exit(-2);
		} catch (InvalidKeyException e1) {
			System.err.println(e1.getMessage());
			System.exit(-2);
		} catch (InvalidAlgorithmParameterException e1) {
			System.err.println(e1.getMessage());
			System.exit(-2);
		}
		
		return cipher;
	}



	

	
	

	/**
	 * Preforms check sha operation of program
	 * 
	 * @param string
	 * @param scanner
	 */
	private static void checkSha(String string, Scanner scanner) {
		System.out.print("Please provide expected sha-256 digest for hw07part2.pdf:\n>");
		String expected = scanner.nextLine();
		
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
		byte[] result = null;
		
		try {
			result = Crypto.hash(digest, getNewBufferedInputStream(string), 1024);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
        String fin = fromByteToHexString(result);
        
		if (!fin.equals(expected)) {
			System.out.println("Digesting completed. Digest of hw07test.bin does not match "
					+ "the expected digest. Digest was:" );
		    System.out.println(fin);
		}else {
			System.out.println("Digesting completed. Digest of hw07test.bin matches "
					+ " expected digest.");
		}
	}
	
	
	/**
	 * Calculates hash of BufferedInputStream
	 * 
	 * @param digest
	 * @param in
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static byte [] hash(MessageDigest digest, BufferedInputStream in, int bufferSize) throws IOException {
	    byte [] array = new byte[bufferSize];
	    int sizeRead = -1;
	    
	    while ((sizeRead = in.read(array)) != -1) {
	        digest.update(array, 0, sizeRead);
	    }
	   
	    byte [] hash = null;
	    hash = new byte[digest.getDigestLength()];
	    in.close();
	    hash = digest.digest();
	    return hash;
	}



	/**
	 * Gets and return new {@link BufferedOutputStream}
	 * @param fromWhere
	 * @return
	 */
	private static BufferedOutputStream getNewBufferedOutputStream(String fromWhere){
		BufferedOutputStream br = null;
		try {
			br = new BufferedOutputStream(
									new FileOutputStream(fromWhere));
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
		return br;
	}
	
	
	
	
	
	/**
	 * gets and returns new {@link BufferedInputStream}
	 * 
	 * @param fromWhere
	 * @return
	 */
	private static BufferedInputStream getNewBufferedInputStream(String fromWhere){
		BufferedInputStream br = null;
		try {
			br = new BufferedInputStream(
									new FileInputStream(fromWhere));
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
		return br;
	}
	
	
	
	
	
	/**
	 * Checks arguments of program. Terminates if arguments are not correct
	 * 
	 * @param args
	 */
	private static void checkProgramArgumets(String[] args){
		if (args.length == 2 ){
			if (! args[0].toUpperCase().equals(CryptoOperation.CHECKSHA.toString())){
				System.err.println("Cannot recognize \"" + args[0] + "\" operation.");
				System.exit(2);
			}
			oper = CryptoOperation.CHECKSHA;
		} else if (args.length == 3) {
			String toCompare = args[0].toUpperCase();
			if (toCompare.equals(CryptoOperation.DECRYPT.toString())){
				oper = CryptoOperation.DECRYPT;
			} else if(toCompare.equals(CryptoOperation.ENCRYPT.toString())){
				oper = CryptoOperation.ENCRYPT;
			}else {
				System.err.println("Cannot recognize \"" + args[0] + "\" operation.");
				System.exit(3);
			}
			
		} else {
			System.err.println("Program expects 2 or 3 arguments.");
			System.exit(1);
		}
	}
	
	
	/**
	 * http://stackoverflow.com/questions/140131/convert-a-string-representation-of-a-hex-dump-to-a-byte-array-using-java
	 * @param arg
	 * @return
	 */
	public static byte[] hextobyte(String arg) {
		int len = arg.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(arg.charAt(i), 16) << 4)
	                             + Character.digit(arg.charAt(i+1), 16));
	    }
	    return data;
	}
	
	/**
	 * From byte to hex String.
	 * 
	 * @param result
	 * @return
	 */
	private static String fromByteToHexString(byte[] result){
		StringBuffer builder= new StringBuffer();
		
        for (int i = 0; i < result.length; i++) {
        	builder.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        return builder.toString();
	}

}
