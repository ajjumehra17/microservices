package com.service.user.config;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class EncryptionUtil {
    private static final String ALGORITHM = "AES";

    public static String generateSymmetricKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(128);
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static String encrypt(String data, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKey secretKey = new javax.crypto.spec.SecretKeySpec(Base64.getDecoder().decode(key), ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decrypt(String encryptedData, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKey secretKey = new javax.crypto.spec.SecretKeySpec(Base64.getDecoder().decode(key), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedData);
    }
}
