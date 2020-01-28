/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cryptography;

/**Java File Encryption Decryption using Password Based Encryption (PBE)
 *
 * @author Janice
 * 
 * How PBE Works?
 * A user supplied password which is a simple text phrase that can be easily remembered by the user
 * Along with that password text, a random number which is called salt is added and hashed.
 * Using this a AES or a DES encryption key is derived and encrypted.
 * The password text is shared between the two parties exchanging the encrypted content.
 * The receiver, uses the same password  and salt and decrypts the content.
 * Here the key is passing or preserving the password phrase in a secure manner.
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class TripleDES_FileEncryption {

	public static void main(String[] args) throws Exception {

		FileInputStream inFile = new FileInputStream("C:\\Users\\pc1\\DES_plainfile.txt");
		FileOutputStream outFile = new FileOutputStream("C:\\Users\\pc1\\DES_plainfile.des");

		String password = "helloWorld";
		PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
		SecretKeyFactory secretKeyFactory = SecretKeyFactory
				.getInstance("PBEWithMD5AndTripleDES");
		SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

		byte[] salt = new byte[8];
		Random random = new Random();
		random.nextBytes(salt);

		PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);
		Cipher cipher = Cipher.getInstance("PBEWithMD5AndTripleDES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, pbeParameterSpec);
		outFile.write(salt);

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
	}

}

