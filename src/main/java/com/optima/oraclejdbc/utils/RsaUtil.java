package com.optima.oraclejdbc.utils;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;

import javax.annotation.Resource;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.rsa.crypto.RsaSecretEncryptor;
import org.springframework.stereotype.Component;

@Component
public class RsaUtil {

	private static RsaUtil instance = new RsaUtil();
	
	@Resource(name="confProp")
	private Properties confProperties;
	
	private RsaUtil(){}
	
	public static RsaUtil getInstance(){
		return instance;
	}
	
	
	private PublicKey getPublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException{
		String oriPublicKey = confProperties.getProperty("public_key");
		oriPublicKey = oriPublicKey.replace("-----BEGIN PUBLIC KEY-----", "");
		oriPublicKey = oriPublicKey.replace("-----END PUBLIC KEY-----", "");
		oriPublicKey = oriPublicKey.trim();
		
		byte[] keyBytes = Base64.decode(oriPublicKey.getBytes());
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PublicKey publicKey = kf.generatePublic(spec);
		return publicKey;
	}
	
	private PrivateKey getPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException{		
		String oriPrivateKey = confProperties.getProperty("private_key");
		oriPrivateKey = oriPrivateKey.replace("-----BEGIN RSA PRIVATE KEY-----", "");
		oriPrivateKey = oriPrivateKey.replace("-----END RSA PRIVATE KEY-----", "");
		oriPrivateKey = oriPrivateKey.trim();
		byte[] keyBytes = Base64.decode(oriPrivateKey.getBytes());
		
		PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = kf.generatePrivate(privateSpec);
		return privateKey;
	}
	
	public String rsaEncrypt(String plainText){
		try {			
			TextEncryptor encryptor = new RsaSecretEncryptor(getPublicKey());
			return encryptor.encrypt(plainText);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}		
		return null;
	}
	
	public String rsaDecrypt(String encryptedText){
		
		try {
			Security.addProvider(new BouncyCastleProvider());
			TextEncryptor encryptor = new RsaSecretEncryptor("UTF-8", getPublicKey(), getPrivateKey());
			return encryptor.decrypt(encryptedText);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return null;
	}
}
