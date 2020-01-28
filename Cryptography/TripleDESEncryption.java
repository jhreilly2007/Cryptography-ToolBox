/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cryptography;

/**
 *
 * @author Janice
 * 
 * PROGRAM: To Encrypt and Decrypt String using triple DES
 * 
 * â€œData Encryption Standard (DES)â€? is prone to brute-force attacks. 
 *  It is a old way of encrypting data.
 * Main issue with DES was the short encryption key size. 
 * Shorter the key, it is easier to break it with brute force attack. 
 */ 
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Scanner;
import java.util.*;

public class TripleDESEncryption {
	private static Cipher cipher = null;

	public static void main(String[] args) throws Exception {
            
            
        FileIO reader = new FileIO();
        Scanner scan = new Scanner(System.in);
		String[] inputs = reader.load("C:\\Users\\pc1\\toBeEncrypted.txt");    //Reading the File as a String array
        int length=inputs.length;
        String plainText = convertMe(inputs);

				// 1. Add Security Provider of Choice
                // Chosen: SunJCE Provider provided with the JDK
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
                
                // 2. Generate Secret Key
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");//DESede is triple DES
		// keysize must be equal to 112 or 168 for this provider
		keyGenerator.init(168);//initialise
                
                // 3. Algorithm to generate secret key
                //    I am using DESede but can use alternative algorithm..ieblowfish 
		SecretKey secretKey = keyGenerator.generateKey();
		cipher = Cipher.getInstance("DESede");
                
                // 4. This is my String to encode
		//String plainText = "Java Cryptography Extension";
		System.out.println("Plain Text Before Encryption: " + plainText);
                
                // For consistency across platform encode the plain 
                // text as byte using UTF-8 encoding.
		byte[] plainTextByte = plainText.getBytes("UTF8");
                
                // 5a. Encrypt Text. Call Encyrpt()
		byte[] encryptedBytes = encrypt(plainTextByte, secretKey);

                // 6. Convert Encrpytion to String
		String encryptedText = new String(encryptedBytes, "UTF8");
		System.out.println("Encrypted Text After Encryption: " + encryptedText);
                
                // 7a.Decrypt Text. Call Decrypt()
		byte[] decryptedBytes = decrypt(encryptedBytes, secretKey);
                
                // 8. Convert Encrpytion to String
		String decryptedText = new String(decryptedBytes, "UTF8");
		System.out.println("Decrypted Text After Decryption: " + decryptedText);
	}
        
            // 5b. Encrypt: Instantiate Cipher with ENCRYPT_MODE,
            //     use the secret key and encrypt the bytes.
	static byte[] encrypt(byte[] plainTextByte, SecretKey secretKey)
			throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedBytes = cipher.doFinal(plainTextByte);
		return encryptedBytes;
	}
        
            // 7b.Decrypt Text: Instantiate Cipher with DECRYPT_MODE, 
            //    use the same secret key and decrypt the bytes.

	static byte[] decrypt(byte[] encryptedBytes, SecretKey secretKey)
			throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		return decryptedBytes;
	}
       
        public static String convertMe(String [] inputs){
            String converted =" ";
            for(String s : inputs){
                converted+=s;
            }
        return converted;
        }
}
