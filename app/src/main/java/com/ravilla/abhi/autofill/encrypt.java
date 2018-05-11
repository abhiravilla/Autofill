package com.ravilla.abhi.autofill;

import android.content.Context;
import android.util.Log;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class encrypt{
	private static final byte[] SALT = {(byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03};
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 128;
    private Cipher eCipher;

    private String encry(String encrypt, String passphrase) throws Exception {
        Cipher dCipher;
        byte[] iv;
        Log.d("Key","1 ");
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec keySpec = new PBEKeySpec(passphrase.toCharArray(), SALT, ITERATION_COUNT, KEY_LENGTH);
        SecretKey secretKeyTemp = secretKeyFactory.generateSecret(keySpec);
        SecretKey secretKey = new SecretKeySpec(secretKeyTemp.getEncoded(), "AES");
        eCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        eCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        dCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        iv = eCipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
        dCipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        Log.d("Key","3 ");
    	byte[] bytes = encrypt.getBytes("UTF8");
        byte[] encrypted = encrypt(bytes);
        byte[] cipherText = new byte[encrypted.length + iv.length];
        System.arraycopy(iv, 0, cipherText, 0, iv.length);
        System.arraycopy(encrypted, 0, cipherText, iv.length, encrypted.length);
        Log.d("Key","4 ");

        return new String(Base64.encodeBase64(cipherText));
    }

    private byte[] encrypt(byte[] plain) throws Exception {
        return eCipher.doFinal(plain);
    }
    public String encryp(String text,String key){
        try {
            Log.d("In Encryp",""+text);
            return encry(text,key);

        }
        catch(Exception e){
            return "Caught an Exception";
        }
    }

}
