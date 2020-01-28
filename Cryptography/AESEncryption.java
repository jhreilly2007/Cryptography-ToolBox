/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cryptography;
//ONLINE DECRYPTION https://www.devglan.com/online-tools/aes-encryption-decryption
/**
 *
 * @author Janice
 * PROGRAM: â€œAdvanced Encryption Standard (AES)â€?.
 * 
 * AES can use 128, 192 or 256 bit encryption. The block size used by DES is 64 bits a
 * nd by AES is 128 bits. 
 * Third difference is AES uses permutation substitution over the Feistel network used by DES.
 * 
 * In real time application, AES is the way to go.
 * 
 * In comparison to DESede Program there are only 2 Differneces: 
 * One is the AES encryption and another is Base64 encoding.
 */
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Scanner;
import java.util.*;

public class AESEncryption {
	static Cipher cipher;

	public static void main(String[] args) throws Exception {
            
            	/**FileIO reader = new FileIO();
                Scanner scan = new Scanner(System.in);
		String[] inputs = reader.load("C:\\Users\\pc1s\\hello.txt");    //Reading the File as a String array
                int length=inputs.length;
                String plainText = convertMe(inputs);*/
                
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		SecretKey secretKey = keyGenerator.generateKey();
		cipher = Cipher.getInstance("AES");//Only Difference to DES & BAse 64

		String plainText = "AES Symmetric Encryption Decryption";
		System.out.println("Plain Text Before Encryption: " + plainText);

		String encryptedText = encrypt(plainText, secretKey);
		System.out.println("Encrypted Text After Encryption: " + encryptedText);

		String decryptedText = decrypt(encryptedText, secretKey);
		System.out.println("Decrypted Text After Decryption: " + decryptedText);
	}
        
        // It is important to encode the binary data with Base64 to ensure it to be 
        // intact without modification when it is stored or transferred.    
	public static String encrypt(String plainText, SecretKey secretKey)
			throws Exception {
		byte[] plainTextByte = plainText.getBytes();
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedByte = cipher.doFinal(plainTextByte);
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);
		return encryptedText;
	}

	public static String decrypt(String encryptedText, SecretKey secretKey)
			throws Exception {
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(encryptedText);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
		String decryptedText = new String(decryptedByte);
		return decryptedText;
	}
        
        public static String convertMe(String [] inputs){
            String converted =" ";
            for(String s : inputs){
                converted+=s;
            }
        return converted;
        }
}
