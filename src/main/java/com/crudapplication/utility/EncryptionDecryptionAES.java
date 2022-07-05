package com.crudapplication.utility;
import org.apache.commons.codec.binary.Base64;

public class EncryptionDecryptionAES {  
   
	public static String encodePassword(String password) {
		String encodedString = new String(Base64.encodeBase64(password.getBytes()));
		return encodedString;
	}
	
	public static String decodePassword(String password) {
		String decodedString = new String(Base64.decodeBase64(password.getBytes()));
		return decodedString;
	}
	
	public static boolean compare(String stringPass,String encodedPass) {
		
		return (decodePassword(encodedPass).equals(stringPass));
	}
	
}
