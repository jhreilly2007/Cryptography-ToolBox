/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cryptography;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *  Advanced password security using PBKDF2WithHmacSHA1 algorithm
 * 
 * @author Janice
 * 
 * So far we learned about creating secure hashes for password, and using salt 
 * to make it even more secure. But the problem today is that hardwares have become 
 * so much fast that any brute force attack using dictionary and rainbow tables, 
 * any password can be cracked in some less or more time.

* To solve this problem, general idea is to make brute force attack slower so that 
* damage can be minimized. Our next algorithm, works on this very concept. The goal 
* is to make the hash function slow enough to impede attacks, but still fast enough 
* to not cause a noticeable delay for the user.

* This feature is essentially implemented using some CPU intensive algorithms such 
* as PBKDF2, Bcrypt or Scrypt. These algorithms take a work factor (also known as 
* security factor) or iteration count as an argument. This value determines how slow 
* the hash function will be. When computers become faster next year we can increase 
* the work factor to balance it out.

Java has implementation of “PBKDF2�? algorithm as “PBKDF2WithHmacSHA1“.
 */

public class PDKDF2_StrongPassword 
{
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException 
    {
        String  originalPassword = "password1";
        String generatedSecuredPasswordHash = generateStorngPasswordHash(originalPassword);
        System.out.println(generatedSecuredPasswordHash);
         
        boolean matched = validatePassword("password1", generatedSecuredPasswordHash);
        System.out.println(matched);
        
        //Will return true if password is validated
        matched = validatePassword("password1", generatedSecuredPasswordHash);
        System.out.println(matched);
    }
    
    private static String generateStorngPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();
         
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }
     
    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
     
    private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

//  Next step is to have a function which can be used to validate the password again 
//  when user comes back and login.
    private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);
         
        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();
         
        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }
    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
    }
