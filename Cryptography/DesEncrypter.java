/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cryptography;

/**
 *
 * @author Janice
 */

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;
//Online Decryption: http://extranet.cryptomathic.com/descalc/index
public class DesEncrypter {
  
	Cipher ecipher;
	Cipher dcipher;

  DesEncrypter(SecretKey key) throws Exception {
    ecipher = Cipher.getInstance("DES");
    dcipher = Cipher.getInstance("DES");
    ecipher.init(Cipher.ENCRYPT_MODE, key);
    dcipher.init(Cipher.DECRYPT_MODE, key);
  }

  public String encrypt(String str) throws Exception {
    // Encode the string into bytes using utf-8
    byte[] utf8 = str.getBytes("UTF8");

    // Encrypt
    byte[] enc = ecipher.doFinal(utf8);

    // Encode bytes to base64 to get a string
    Base64.Encoder encoder = Base64.getEncoder();
    String encryptedText = encoder.encodeToString(enc);
		return encryptedText;
  }


  public String decrypt(String str) throws Exception {
    // Decode base64 to get bytes
    Base64.Decoder decoder = Base64.getDecoder();
    byte[] encryptedTextByte = decoder.decode(str);

    byte[] utf8 = dcipher.doFinal(encryptedTextByte);

    // Decode using utf-8
    return new String(utf8, "UTF8");
  }
  
  public static String convertMe(String [] inputs){
      String converted =" ";
            for(String s : inputs){
                converted+=s;
            }
        return converted;
        }

  public static void main(String[] args) throws Exception {
	 
	/**
	 * // SECRETKEY TO STRING: 		create new key
	 * SecretKey secretKey = KeyGenerator.getInstance("DES").generateKey();
	 * // get base64 encoded version of the key  
	 * String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
	 * 
	 * // STRING TO SECRET KEY: 	decode the base64 encoded string
	 * byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
	 * SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES"); 
	 * 
	 * TEST: 
	 * System.out.println("PRINT ME "+secretKey);
	 * System.out.println("PRINT ME "+encodedKey);
	 * System.out.println("PRINT ME "+originalKey);*/
	  
	/**If I need to use a given Key to Decode DES...
	 * You can convert the SecretKey to a byte array (byte[]), 
	 * then Base64 encode that to a String. To convert back to a SecretKey, 
	 * Base64 decode the String and use it in a SecretKeySpec to rebuild your original SecretKey.  
	 */
	String myKey="0e329232ea6";
	byte[] decodedKey = Base64.getDecoder().decode(myKey);
	System.out.println(decodedKey.length);
	SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");
	
	//SecretKey key = KeyGenerator.getInstance("DES").generateKey();
    DesEncrypter encrypter = new DesEncrypter(key);
    String message = "Don't tell anybody!";
    System.out.println(message);
    String encrypted = encrypter.encrypt(message);
    System.out.println(encrypted);
    String decrypted = encrypter.decrypt(encrypted);
    System.out.println(decrypted);
  }
}