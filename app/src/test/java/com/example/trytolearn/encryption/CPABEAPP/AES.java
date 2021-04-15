package com.example.trytolearn.encryption.CPABEAPP;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private final static String AES = "AES";
    private final static String UTF8 = "UTF-8";
    //Define an initial vector of 16 bytes
    private static final String IV_STRING = "1234567887654321";

    /**
     * Generates a 16-bit key string
     *
     * @return
     */

    public String aesEnc(String content, String key) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        byte[] contentByte = content.getBytes(UTF8);
        byte[] keyByte = key.getBytes();
        //Initializes a key object
        SecretKeySpec keySpec = new SecretKeySpec(keyByte, AES);
        //Initializes an initial vector
        byte[] initParam = IV_STRING.getBytes();
        IvParameterSpec ivSpec = new IvParameterSpec(initParam);
        // Specifies the encryption algorithm, mode of operation, and how to fill
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encryptedBytes = cipher.doFinal(contentByte);
        String encodedString = Base64.getEncoder().encodeToString(encryptedBytes);
        return encodedString;
    }

    public String aesDec(String content, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
        byte[] contentByte = Base64.getDecoder().decode(content);
        byte[] keyByte = key.getBytes();
        //Initializes a key object
        SecretKeySpec keySpec = new SecretKeySpec(keyByte, AES);
        //Initializes an initial vector
        byte[] initParam = IV_STRING.getBytes();
        IvParameterSpec ivSpec = new IvParameterSpec(initParam);
        // Specifies the encryption algorithm, mode of operation, and how to fill
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] result = cipher.doFinal(contentByte);
        return new String(result, UTF8);
    }
}
