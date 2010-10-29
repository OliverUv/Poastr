package com.ollijepp.poastr.client.crypto;

/**
 * 
 * Uses Clipperz to encrypt and decrypt data
 * 
 * @author Jesper Axelsson
 *
 */

public class ClipperzCrypto 
{
	/**
	 * Used to encrypt a string with a given key
	 * 
	 * @param stringToEncrypt
	 * @param key
	 * @return the encrypted string as a hexstring
	 */
	public static native String encrypt(String stringToEncrypt, String key)
	/*-{
		return $wnd.Clipperz.Crypto.Base.encryptUsingSecretKey(key, stringToEncrypt);
	}-*/;
	
	/**
	 * Decrypts the given string using the given key
	 * 
	 * @param stringToDecrypt as a hexstring
	 * @param key
	 * @return the decrypted string
	 */
	public static native String decrypt(String stringToDecrypt, String key)
	/*-{
		return $wnd.Clipperz.Crypto.Base.decryptUsingSecretKey(key, stringToDecrypt);
	}-*/;
	
	public static native String hash(String stringToHash)
	/*-{
		return $wnd.Clipperz.Crypto.Base.computeHashValue(stringToHash);
	}-*/;
}
