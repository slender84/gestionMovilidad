
package model.utils;

import java.security.Key;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
//import sun.misc.BASE64Decoder;



public class Seguridad {
    
    private static String algorithm = "AES";
private static byte[] keyValue=new byte[] {'0','2','3','4','5','6','7','8','9','1','2','3','4','5','6','7'};
    
    public static String encrypt(String plainText) throws Exception 
    {
            Key key = generateKey();
            Cipher chiper = Cipher.getInstance(algorithm);
            chiper.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = chiper.doFinal(plainText.getBytes());
            //String encryptedValue = new BASE64Encoder().encode(encVal);
           String encryptedValue=DatatypeConverter.printBase64Binary(encVal);
            return encryptedValue;
    }

    // Performs decryption
    public static String decrypt(String encryptedText) throws Exception 
    {
            // generate key 
            Key key = generateKey();
            Cipher chiper = Cipher.getInstance(algorithm);
            chiper.init(Cipher.DECRYPT_MODE, key);
            //byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedText);
            byte[] decordedValue = DatatypeConverter.parseBase64Binary(encryptedText);
            byte[] decValue = chiper.doFinal(decordedValue);
            String decryptedValue = new String(decValue);
            return decryptedValue;
    }
    
    
    private static Key generateKey() throws Exception 
    {
            Key key = new SecretKeySpec(keyValue, algorithm);
            return key;
    }
    
    
     public static String md5Password(String password){
        
        
        try {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
        byte[] array = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
       }
        return sb.toString();
    } catch (java.security.NoSuchAlgorithmException e) {
    }
    return null;
        
        
        
    }

     
      public static String generarPassword(){
       
       return UUID.randomUUID().toString();
     
    
}
}
