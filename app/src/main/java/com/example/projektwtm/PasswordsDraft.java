package com.example.projektwtm;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PasswordsDraft {

    public String encryptText(String text, String key){
        try
        {
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            System.err.println(new String(encrypted));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public String encryptText(String text, String key){
        try
        {
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            String decrypted = new String(cipher.doFinal(text));
            System.err.println(decrypted);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }
}
