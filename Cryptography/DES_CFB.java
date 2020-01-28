/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Cryptography;
//Online Decryption: http://extranet.cryptomathic.com/descalc/index
import javax.crypto.spec.*;
import java.security.*;
import javax.crypto.*;
  
public class DES_CFB
{
   static IvParameterSpec iv;
  
   public static void main(String []args) throws Exception {
      String toEncrypt = "The shorter you live, the longer you're dead!";
  
      System.out.println("Encrypting...");
      byte[] encrypted = encrypt(toEncrypt, "password");
  
      System.out.println("Decrypting...");
      String decrypted = decrypt(encrypted, "password");
     
      System.out.println("Decrypted text: " + decrypted);
   }
  
   public static byte[] encrypt(String toEncrypt, String key) throws Exception {
      // create a binary key from the argument key (seed)
      SecureRandom sr = new SecureRandom(key.getBytes());
      KeyGenerator kg = KeyGenerator.getInstance("DES");
      kg.init(sr);
      SecretKey sk = kg.generateKey();
  
      // create an instance of cipher
      Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
  
      // generate an initialization vector (IV)
      SecureRandom secureRandom = new SecureRandom();
      byte[] ivspec = new byte[cipher.getBlockSize()];
      secureRandom.nextBytes(ivspec);
      iv = new IvParameterSpec(ivspec);
  
      // initialize the cipher with the key and IV
      cipher.init(Cipher.ENCRYPT_MODE, sk, iv);
  
      // enctypt!
      byte[] encrypted = cipher.doFinal(toEncrypt.getBytes());
  
      return encrypted;
   }
  
   public static String decrypt(byte[] toDecrypt, String key) throws Exception {
      // create a binary key from the argument key (seed)
      SecureRandom sr = new SecureRandom(key.getBytes());
      KeyGenerator kg = KeyGenerator.getInstance("DES");
      kg.init(sr);
      SecretKey sk = kg.generateKey();
  
      // do the decryption with that key
      Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, sk, iv);
      byte[] decrypted = cipher.doFinal(toDecrypt);
  
      return new String(decrypted);
   }
}
