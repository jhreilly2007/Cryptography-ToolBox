/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cryptography;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 *
 * @author Janice
 * 
 * Wikipedia defines salt as random data that are used as an additional input to a one-way 
 * function that hashes a password or pass-phrase. In more simple words, salt is some 
 * randomly generated text, which is appended to the password before obtaining hash.
 * 
 * Important: We always need to use a SecureRandom to create good salts, and in Java, 
 * the SecureRandom class supports the â€œSHA1PRNGâ€? pseudo random number generator algorithm, 
 * and we can take advantage of it.
 */
public class MD5_Salt 
{
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException 
    {
        String passwordToHash = "password";
        byte[] salt = getSalt();////byte[] salt = {22,33,44};
         
        String securePassword = getSecurePassword(passwordToHash, salt);
        System.out.println(securePassword); //Prints 83ee5baeea20b6c21635e4ea67847f66
         
        String regeneratedPassowrdToVerify = getSecurePassword(passwordToHash, salt);
        System.out.println(regeneratedPassowrdToVerify); //Prints 83ee5baeea20b6c21635e4ea67847f66
    }
     
    private static String getSecurePassword(String passwordToHash, byte[] salt)
    {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(salt);
            //Get the hash's bytes 
            byte[] bytes = md.digest(passwordToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
     
    //Add salt
    private static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException
    {
        //Always use a SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        //Create array for salt
        byte[] salt = new byte[16];
        //Get a random salt
        sr.nextBytes(salt);
        //return salt
        	/*****TO GENERATE YOUR OWN SALT****
        	 * byte[] salt2 = {22,33,44,5};
        	 * System.out.println(salt2);
        	 */
        return salt;
    }
}
