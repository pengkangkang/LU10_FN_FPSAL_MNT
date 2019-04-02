package com.fn.fpsal.mnt.utils;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.tomcat.util.codec.binary.Base64;

public class RSAUtils {

	public static final String KEY_ALGORITHM = "RSA";
	
	public static final String SIGNATURE_ALGORITHM = "Md5withRSA";
	
	public static final String PUBLIC_KEY = "RSAPublicKey";
	
	public static final String PRIVATE_KEY = "RSAPrivateKey";
	
	public static final int MAX_ENCRYPT_BLOCK = 117;
	
	public static final int MAX_DECRYPT_BLOCK = 128;
	
	public static final int INITIALIZE_LENGTH = 1024;
	
	/**
	 *	生成密钥对,公钥和私钥
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> genKeypair() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(INITIALIZE_LENGTH);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String,Object> keyMap = new HashMap<String,Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY,privateKey);
		return keyMap;
	}
	
	/**
	 * 	用私钥对信息生成数字签名
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data,String privateKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(privateK);
		signature.update(data);
		return Base64.encodeBase64String(signature.sign());
	}
	
	/**
	 * 	校验数字签名
	 * @param data
	 * @param publicKey
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(byte[] data,String publicKey,String sign) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(Base64.decodeBase64(sign));
	}
	
	/**
	 * 	私钥解密
	 * @param encryptedData
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByprivateKey(byte[] encryptedData,String privateKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		/*对数据分段解密*/
		while (inputLen - offSet > 0) {
			if(inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData,offSet,MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData,offSet,inputLen - offSet);
			}
			out.write(cache,0,cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}
	
	/**
	 * 	公钥解密
	 * @param encryptedData
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] encryptedData,String publicKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePrivate(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		/*对数据分段解密*/
		while (inputLen - offSet > 0) {
			if(inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData,offSet,MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData,offSet,inputLen - offSet);
			}
			out.write(cache,0,cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}
	
	/**
	 * 	公钥加密
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data,String publicKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		/*对数据加密*/
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		/*对数据分段解密*/
		while (inputLen - offSet > 0) {
			if(inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data,offSet,MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data,offSet,inputLen - offSet);
			}
			out.write(cache,0,cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}
	
	/**
	 * 	私钥加密
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data,String privateKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		/*对数据加密*/
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		/*对数据分段解密*/
		while (inputLen - offSet > 0) {
			if(inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data,offSet,MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data,offSet,inputLen - offSet);
			}
			out.write(cache,0,cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}
	
	/**
	 * 	获取私钥
	 */
	public static String getPrivateKey(Map<String,Object> keyMap) {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return Base64.encodeBase64String(key.getEncoded());
	}
	
	/**
	 * 	获取公钥
	 */
	public static String getPublicKey(Map<String,Object> keyMap) {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return Base64.encodeBase64String(key.getEncoded());
	}
	
	/**
	 * java端公钥加密
	 * @param data
	 * @param publicKey
	 * @return
	 */
	public static String encryptDataOnJava(String data,String publicKey) {
		try {
			data = Base64.encodeBase64String(encryptByPublicKey(data.getBytes(), publicKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * java端私钥解密
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptDataOnJava(String data,String privateKey) throws Exception {
		byte[] rs = Base64.decodeBase64(data);
		return new String(decryptByprivateKey(rs, privateKey),"UTF-8");
	}
	
	public static void main(String[] args) throws Exception {
//		Map<String, Object> keyMap = genKeypair();
//		String publicKey = getPublicKey(keyMap);
//		String privateKey = getPrivateKey(keyMap);
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCGe5ImN/d/7CL4oy0I+etCOPynpiP8Z5y6QhfLMdxLRYRrZ3eRDET+V0QzkuR9ouxr9ZNYhRc1qUuJXTyf0Mq9mDgC0BoW15gj4LrIIFYQxGmrglJ94/08AKVhFYdRgsh7c0w5NOZIsUjmMKfKIAvnpgZqaK8raeLY+YFLOeOsrwIDAQAB";
		String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIZ7kiY393/sIvijLQj560I4/KemI/xnnLpCF8sx3EtFhGtnd5EMRP5XRDOS5H2i7Gv1k1iFFzWpS4ldPJ/Qyr2YOALQGhbXmCPgusggVhDEaauCUn3j/TwApWEVh1GCyHtzTDk05kixSOYwp8ogC+emBmporytp4tj5gUs546yvAgMBAAECgYB4YAez2gjaIl4zPKxFCp+AiU3uKpKvjXcGab6ujRTo83LSSK0H0nm3SBmuKGAJ7Vm+UPTxGUBrMfyBGkn3q4as27l5ozc03G5gWyYXoRbsNnURumQzhbiCQzsbLA4YO5KUEXrVZaR7tw/PgNUvFQx3weoE3m4c4lgMRPcSaYZ10QJBANLytYD2dfd7rxw2d8jCjBwu4q1dxvjnTXSjv0zeYAodjkGwopWOpIAOafSgaRD4am/iwqbj6U7c31rs6qc26msCQQCjNDlhUxc8qnTrCAqpXRW8cl0Gv9CPuJ8HDBqQqhyN1pOgi4Zr1tHDU/Wy5FqrQ9shp9z18rG1RMUmtmtFVR/NAkBbj5mUlmVxvC+vkTzA2SCzgu5EzbV1iAAopoJ8PPF2solyndaUD8v7AUagNZglE44vbj+K7NXxsxEHiFn5pm5tAkBrObqhWOghcZSTfpsSnxfS0giQkKHEa3YAOVGSzlEoV1xy1HiijWcPwC/jMujnMRPdzt37sVJUWDR3tM5RpRXZAkEAjFWk2+jMvDUnsVW3pyuW05fkvGpiQWWSnGicjNs1jVPPREjuaSmZ1oavFIodRkXPRTN6DdpvVWZU62Ow1IhTDg==";
		String value = "20190330211501_whiteList";
		System.out.println(value);
		String encodeValue = encryptDataOnJava(value, publicKey);
		System.out.println(encodeValue);
		String data = decryptDataOnJava(encodeValue, privateKey);
		System.out.println(data);
	}
	
}
