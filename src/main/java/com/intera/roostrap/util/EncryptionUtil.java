/*
 * Copyright 2013 Muhammad Ichsan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http:
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intera.roostrap.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.output.ByteArrayOutputStream;

/**
 *
 * @author '<a href="mailto:ichsan@gmail.com">Muhammad Ichsan</a>'
 *
 */
public class EncryptionUtil {
	private static final String UTF_8 = "UTF-8";

	public static void encrypt(String encryptionKey, InputStream is,
			OutputStream os) throws InvalidKeyException, IOException {
		encryptOrDecrypt(encryptionKey, Cipher.ENCRYPT_MODE, is, os);
	}

	public static void decrypt(String encryptionKey, InputStream is,
			OutputStream os) throws InvalidKeyException, IOException {
		encryptOrDecrypt(encryptionKey, Cipher.DECRYPT_MODE, is, os);
	}

	private static void encryptOrDecrypt(String encryptionKey, int mode,
			InputStream is, OutputStream os) throws InvalidKeyException,
			IOException {
		DESKeySpec keySpec = new DESKeySpec(toBytes(encryptionKey));

		SecretKey key = null;
		Cipher cipher = null;
		try {
			SecretKeyFactory secretKeyFactory = SecretKeyFactory
					.getInstance("DES");
			key = secretKeyFactory.generateSecret(keySpec);
			cipher = Cipher.getInstance("DES"); 
												
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (mode == Cipher.ENCRYPT_MODE) {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			CipherInputStream cis = new CipherInputStream(is, cipher);
			doCopy(cis, os);
		} else if (mode == Cipher.DECRYPT_MODE) {
			cipher.init(Cipher.DECRYPT_MODE, key);
			CipherOutputStream cos = new CipherOutputStream(os, cipher);
			doCopy(is, cos);
		}
	}

	private static byte[] toBytes(String string) {
		try {
			return string.getBytes(UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private static String toString(byte[] bytes) {
		try {
			return new String(bytes, UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private static void doCopy(InputStream is, OutputStream os)
			throws IOException {
		byte[] bytes = new byte[64];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {
			os.write(bytes, 0, numBytes);
		}
		os.flush();
		os.close();
		is.close();
	}

	public static String encrypt(String encryptionKey, String plainText)
			throws InvalidKeyException {
		InputStream inStream = new ByteArrayInputStream(toBytes(plainText));
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		try {
			encrypt(encryptionKey, inStream, outStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Base64 base64 = new Base64();
		return toString(base64.encode(outStream.toByteArray()));
	}

	public static String decrypt(String encryptionKey, String encryptedText)
			throws InvalidKeyException {
		Base64 base64 = new Base64();
		InputStream inStream = new ByteArrayInputStream(
				base64.decode(toBytes(encryptedText)));
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		try {
			decrypt(encryptionKey, inStream, outStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return toString(outStream.toByteArray());
	}

}
