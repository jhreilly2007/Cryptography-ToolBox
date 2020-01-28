package Cryptography; 

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



public class CipherEncryption {
private static Cipher encryptCipher;
private static Cipher decryptCipher;
private static final byte[] iv = { 11, 22, 33, 44, 99, 88, 77, 66 }; //8 bytes - DES; 16 bytes - AES

public static void main(String[] args) throws InvalidAlgorithmParameterException {
String clearTextFile = "C:\\Users\\pc1\\plaintext.txt";
String cipherTextFile = "C:\\\\Users\\\\pc1\\\\cipher.txt";
String clearTextNewFile = "C:\\\\Users\\\\pc1\\\\decrypted.txt";

try {
// create new key
// SecretKey secretKey = KeyGenerator.getInstance("DES").generateKey();
// get base64 encoded version of the key
String encoded = "qwerqwer"; //8 bytes - DES; 16 bytes - AES
// String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
String encodedKey = Base64.getEncoder().encodeToString(encoded.getBytes("UTF-8"));
System.out.println(encodedKey);
// create SecretKey using KeyGenerator
byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
System.out.println(decodedKey);
// byte[] decodedKey = encodedKey.getBytes();
SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");
//SecretKey key = KeyGenerator.getInstance("DES").generateKey();
AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);

// get Cipher instance and initiate in encrypt mode
encryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding"); //DES/ECB/NoPadding
encryptCipher.init(Cipher.ENCRYPT_MODE, key);//, paramSpec);

// get Cipher instance and initiate in decrypt mode
decryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding"); //DES/ECB/NoPadding
decryptCipher.init(Cipher.DECRYPT_MODE, key);//, paramSpec);

// method to encrypt clear text file to encrypted file
encrypt(new FileInputStream(clearTextFile), new FileOutputStream(cipherTextFile));

// method to decrypt encrypted file to clear text file
decrypt(new FileInputStream(cipherTextFile), new FileOutputStream(clearTextNewFile));
System.out.println("DONE");
} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
|IOException e) {
e.printStackTrace();
}

}

private static void encrypt(InputStream is, OutputStream os) throws IOException {

// create CipherOutputStream to encrypt the data using encryptCipher
os = new CipherOutputStream(os, encryptCipher);
writeData(is, os);
}

private static void decrypt(InputStream is, OutputStream os) throws IOException {

// create CipherOutputStream to decrypt the data using decryptCipher
is = new CipherInputStream(is, decryptCipher);
writeData(is, os);
}

// utility method to read data from input stream and write to output stream
private static void writeData(InputStream is, OutputStream os) throws IOException {
byte[] buf = new byte[1024];
int numRead = 0;
// read and write operation
while ((numRead = is.read(buf)) >= 0) {
os.write(buf, 0, numRead);
}
os.close();
is.close();
}

}


