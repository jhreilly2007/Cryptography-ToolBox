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
 * Notes: 
 * AES is a block cipher, an encryption algorithm that operates on 128-bit blocks.
 * CBC is a block cipher mode, a way of using a block cipher to encrypt large amounts of data.
 * Block cipher modes need an initialisation vector (IV), which is a block of initialisation data, 
 * usually the same size as the block size of the underlying cipher.
 * 
 * Notes on padding:
 * PKCS7 padding is actually technically the correct padding name, but Java blew it and called it PKCS5PADDING.
 * Technically, PKCS5 padding only applies to ciphers with a cipher block size of 64-bits, not 128-bits, but both PKCS5
 * and PKCS7 padding act identically for block sizes <= 255 bits.
 * Be sure to specify the mode explicitly as most JCE providers default to ECB mode, which is not secure!
 * For this example, we are use CFB mode with no padding in order to avoid padding attacks.
 *
 * Notes on initialization vectors (IVs):
 * The IV must be saved for later decryption and should not be reused for other encryption operations. It can be stored
 * separately or sent along with the encrypted data. Usually, the encrypted data is appended to the IV and the result
 * is encoded then stored or transmitted.
 */

//ONLINE DECRYPTION https://www.devglan.com/online-tools/aes-encryption-decryption

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;
import java.util.*;


public class AES_CFB_NoPadding{

  public static final int keyLength = 128;
  public static final String charEnc = "UTF-8";
  public static final String transformationString = "AES/CFB/NoPadding";

  public static void main(String[] args) {
      
        FileIO reader = new FileIO();
        Scanner scan = new Scanner(System.in);
		String[] inputs = reader.load("C:\\Users\\pc1\\toBeEncrypted.txt");    //Reading the File as a String array
        int length=inputs.length;
        String message = convertMe(inputs);

   // String message = "Hello World of Encryption using AES";
    System.out.println("Original Message : "+message);
    String cipherText;

    try {
      // 1. Generate an AES key of the desired length 
      //    (in bits) using an AES KeyGenerator.
      KeyGenerator keyGen = KeyGenerator.getInstance("AES");
      keyGen.init(keyLength);
      SecretKey secretKey = keyGen.generateKey();

      // 2. Get a Cipher instance of the desired algorithm, mode, and padding.
      Cipher aesCipherForEncryption = Cipher.getInstance(transformationString);

      // 3. Generate an initialization vector for our message of 
      //    the same size as the Cipher's blocksize.
      byte[] iv = new byte[aesCipherForEncryption.getBlockSize()];
      SecureRandom prng = new SecureRandom();
      prng.nextBytes(iv);

      // 4. Initialize the Cipher instance for encryption using the key 
      //    and initialization vector.
      aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

      // 5. Use the Cipher to encrypt the message (after encoding it to a byte[] 
      //    using the named Charset), and then append the encrypted data to the 
      //    IV and Base64-encode the result.
      byte[] encrypted = aesCipherForEncryption.doFinal(message.getBytes(charEnc));
      ByteBuffer cipherData = ByteBuffer.allocate(iv.length + encrypted.length);
      cipherData.put(iv);
      cipherData.put(encrypted);
      cipherText = new String(Base64.getEncoder().encode(cipherData.array()), charEnc);
      System.out
        .println("Encrypted and encoded message is: " + new String(Base64.getEncoder().encode(encrypted), charEnc));
      System.out.println(cipherText);
      System.out.println("\nThe receiver will now initialize the cipher using the IV and decrypt the ciphertext");

      // 6. Get a new Cipher instance of the same algorithm, mode, and padding 
      //    used for encryption.
      Cipher aesCipherForDecryption = Cipher.getInstance(transformationString);

      // 7. Base64-decode and split the data into the IV and the encrypted data, and then 
      //    initialize the cipher for decryption with the same key used for encryption 
      //    (symmetric), the IV, and the encrypted data.
      cipherData = ByteBuffer.wrap(Base64.getDecoder().decode(cipherText.getBytes(charEnc)));
      iv = new byte[aesCipherForDecryption.getBlockSize()];
      cipherData.get(iv);
      encrypted = new byte[cipherData.remaining()];
      cipherData.get(encrypted);
      aesCipherForDecryption.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

      // 8. Use the Cipher to decrypt the data, convert it to a String using the named Charset, 
      //    and display the message.
      byte[] decrypted = aesCipherForDecryption.doFinal(encrypted);
      System.out.println("Decrypted text message is: " + new String(decrypted, charEnc));
    } catch(NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | UnsupportedEncodingException ex) {
      System.err.println(ex);
    }
  }
  
    public static String convertMe(String [] inputs){
            String converted =" ";
            for(String s : inputs){
                converted+=s;
            }
        return converted;
        }

}
