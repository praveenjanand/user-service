package com.group5.util;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;



public class Base64EncodingUtil {
	
	static final String STR_TO_BE_ENCRYPTED = "special";
	static final String SECRET_KEY = "AhWdphZ7QNl0OYvKq4xOvQ==";
	static final String CIPHER_INSTANCE = "AES/CBC/PKCS5Padding";
	static final int ITERATION_COUNT = 10000;
	static final int KEY_LENGTH = 128;
	static final String AES = "AES";
	static final String KEY_HASH_ALGORITHM = "PBKDF2WithHmacSHA512";

	//final static String ENC_KEY =System.getProperty("ENC_KEY");
	
	/*
	 * public static void main(String[] args) throws Exception { //if (ENC_KEY ==
	 * null) { // throw new
	 * IllegalArgumentException("Run with -Dpassword=<password>"); // } byte[] salt
	 * = STR_TO_BE_ENCRYPTED.getBytes(); SecretKeySpec secretKey =
	 * createSecretKey(BlueButtonConstant.SECRET_KEY.toCharArray(), salt,
	 * BlueButtonConstant.ITERATION_COUNT, BlueButtonConstant.KEY_LENGTH); byte[]
	 * decodedKey = Base64.getDecoder().decode(BlueButtonConstant.SECRET_KEY);
	 * SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length,
	 * "AES"); System.out.println("Original password: " + STR_TO_BE_ENCRYPTED);
	 * String encryptedPassword = encrypt(STR_TO_BE_ENCRYPTED, originalKey);
	 * System.out.println("Encrypted password: " + encryptedPassword);
	 * 
	 * 
	 * String decryptedPassword = decrypt("3aW0qv4pN");
	 * System.out.println("Decrypted password: " + decryptedPassword); }
	  }*/
	   private static SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_HASH_ALGORITHM);
	        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
	        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
	        return new SecretKeySpec(keyTmp.getEncoded(), AES);
	   }

	   public static String encrypt(String password) throws GeneralSecurityException{
	        Cipher pbeCipher = Cipher.getInstance(CIPHER_INSTANCE);
	        byte[] salt = password.getBytes();
	        SecretKeySpec secretKey = createSecretKey(SECRET_KEY.toCharArray(),
	                salt, ITERATION_COUNT, KEY_LENGTH);
	        pbeCipher.init(Cipher.ENCRYPT_MODE, secretKey);
	        AlgorithmParameters parameters = pbeCipher.getParameters();
	        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
	        byte[] cryptoText = pbeCipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
	        byte[] iv = ivParameterSpec.getIV();
	        return base64Encode(iv) + ":" + base64Encode(cryptoText);
	    }


	    private static String base64Encode(byte[] bytes) {
	        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	    }

	    public static String decrypt(String decryptString) throws GeneralSecurityException {
	    	byte[] salt = decryptString.getBytes();
	        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
	        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
	        String iv = decryptString.split(":")[0];
	        String property = decryptString.split(":")[1];
	        Cipher pbeCipher = Cipher.getInstance(CIPHER_INSTANCE);
	        pbeCipher.init(Cipher.DECRYPT_MODE, originalKey, new IvParameterSpec(base64Decode(iv)));
	        return new String(pbeCipher.doFinal(base64Decode(property)), StandardCharsets.UTF_8);
	    }
	    
	    private static byte[] base64Decode(String property) {
	        return Base64.getUrlDecoder().decode(property);
	    }
	

}
