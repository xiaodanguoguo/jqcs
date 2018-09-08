package com.ebase.utils.secret.rsa;

import java.io.ByteArrayOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import com.ebase.utils.secret.base64.Base64Util;
import org.apache.poi.util.IOUtils;

/**
 * <p>
 * RSA 加密算法， 必须能通过RSAKeyUtil读取配置文件
 * </p>
 * 
 * @project core
 * @class RSAUtil
 */
public class RSAUtil {

	/**
	 * 最大文件加密块
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * 最大文件解密块
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * RSA
	 */
	private static String RSA_TRANSFORMATION = "RSA/ECB/PKCS1Padding";

	private final static String DEFAULT_CHARSET = "UTF-8";

	/**
	 * 公钥 加密
	 * 
	 * @param data
	 *            明文
	 * @return 密文
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String data, PublicKey publicKey) throws RuntimeException {
		try {
			byte[] dataBytes = data.getBytes(DEFAULT_CHARSET);
			Cipher cipher = Cipher.getInstance(RSA_TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			int inputLen = dataBytes.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(dataBytes, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(dataBytes, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return Base64Util.encodeByteToStr(encryptedData);
		} catch (RuntimeException rex) {
			throw rex;
		} catch (Exception ex) {
			throw new RuntimeException("RSA加密失败", ex);
		}

	}

	/**
	 * 用私钥来解密
	 * 
	 * @param encryptedData
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(String encryptedData, PrivateKey privateKey) throws RuntimeException {
		ByteArrayOutputStream out = null;
		try {
			// 取得私钥
			Cipher cipher = Cipher.getInstance(RSA_TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] encryptedDataBytes = Base64Util.decodeStrToByte(encryptedData);
			int inputLen = encryptedDataBytes.length;
			out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedDataBytes, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedDataBytes, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			return new String(decryptedData, DEFAULT_CHARSET);
		} catch (RuntimeException rex) {
			throw rex;
		} catch (Exception ex) {
			throw new RuntimeException("RSA解密失败!", ex);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

}
