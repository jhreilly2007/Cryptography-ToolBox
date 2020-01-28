/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cryptography;

/**PBE password based encryption
 *
 * @author Janice
 * 
 * Java File Encryption Decryption using AES Password Based Encryption (PBE)
 * 
 * All these three, password, iv and salt should be passed on to the recipient in 
 * a secure mechanism and not along with the encrypted file. The recipient will 
 * use the plain text password, iv (to initialize the cypher) and the salt to 
 * decrypt the file. 
 * 
 * 
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESFileEncryption {
	public static void main(String[] args) throws Exception {

		// file to be encrypted
		FileInputStream inFile = new FileInputStream("C:\\Users\\pc1\\AES_plainfile.txt");

		// encrypted file
		FileOutputStream outFile = new FileOutputStream("C:\\Users\\pc1\\AES_encryptedfile.des");

		// password to encrypt the file
		String password = "javapapers";

		// password, iv and salt should be transferred to the other end
		// in a secure manner

		// salt is used for encoding
		// writing it to a file
		// salt should be transferred to the recipient securely
		// for decryption
		byte[] salt = new byte[8];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(salt);
		FileOutputStream saltOutFile = new FileOutputStream("C:\\Users\\pc1\\salt.enc");
		saltOutFile.write(salt);
		saltOutFile.close();

		SecretKeyFactory factory = SecretKeyFactory
				.getInstance("PBKDF2WithHmacSHA1");
		KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536,
				256);
		SecretKey secretKey = factory.generateSecret(keySpec);
		SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

		//
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();

		// iv adds randomness to the text and just makes the mechanism more
		// secure
		// used while initializing the cipher
		// file to store the iv
		FileOutputStream ivOutFile = new FileOutputStream("C:\\Users\\pc1\\iv.enc");
		byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
		ivOutFile.write(iv);
		ivOutFile.close();

		//file encryption
		byte[] input = new byte[64];
		int bytesRead;

		while ((bytesRead = inFile.read(input)) != -1) {
			byte[] output = cipher.update(input, 0, bytesRead);
			if (output != null)
				outFile.write(output);
		}

		byte[] output = cipher.doFinal();
		if (output != null)
			outFile.write(output);

		inFile.close();
		outFile.flush();
		outFile.close();

		System.out.println("File Encrypted.");
		
	}

}
