package net.nilsramstoeck.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import net.nilsramstoeck.util.data.DataConverter;

/**
 * A simple class to hash an validate passwords using PBKDF2
 * @author Nils Ramstoeck
 *
 */
public final class PasswordManager {

	/**
	 * Amount of Iterations during the encryption
	 */
	private static final int PBKDF2_ITERATIONS = 65536;

	/**
	 * Position of the iterations in the hashed password
	 */
	private static final int ITERATION_INDEX = 0;
	/**
	 * Position of the salt in the hashed password
	 */
	private static final int SALT_INDEX = 1;
	/**
	 * Position of the password inside the hash
	 */
	private static final int HASH_INDEX = 2;

	/**
	 * Standard password hash length
	 */
	private static final int STD_LENGTH = 256;

	/**
	 * Hashes a password and returns it hashed in the format
	 * {@code iterations:salt:hash}
	 * 
	 * @param password Password to hash
	 * @return hashed password
	 */
	public static String hashPassword(String password) {
		// TODO Auto-generated method stub
		return hashPassword(password, STD_LENGTH);
	}

	/**
	 * Hashes a password and returns it hashed in the format
	 * {@code iterations:salt:hash}
	 * 
	 * @param password   Password to hash
	 * @param hashlength length of the hashed password
	 * @return hashed password
	 */
	public static String hashPassword(String password, int hashlength) {
		// Create secure, random, salt
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		byte[] hash = null;
		random.nextBytes(salt);

		// create hash specification
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, PBKDF2_ITERATIONS, hashlength);
		SecretKeyFactory factory = null;

		try {
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// hash password
		try {
			hash = factory.generateSecret(spec).getEncoded();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

		// build hashed password string
		StringBuilder sb = new StringBuilder();
		sb.append(Integer.toString(PBKDF2_ITERATIONS));
		sb.append(":");
		sb.append(DataConverter.byteArrayToHexString(salt));
		sb.append(":");
		sb.append(DataConverter.byteArrayToHexString(hash));
		return sb.toString();
	}

	/**
	 * Validates a password against a hashed password
	 * 
	 * @param password Password that needs validating
	 * @param hash Hashed Password to validate against
	 * @return Password validation result
	 */
	public static boolean validatePassword(String password, String hash) {
		// Get salt, hash and iterations from hashed Password
		byte[] salt = DataConverter.hexStringToByteArray(hash.split(":")[SALT_INDEX]);
		int iterations = Integer.parseInt(hash.split(":")[ITERATION_INDEX]);
		String passHash = hash.split(":")[HASH_INDEX];

		// create hash specification
		// 1 byte = 8 digits
		// 1 byte in Hex = 2 digits
		// => hexHash is 4 times shorter, so the length needs to be multiplied by 4
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, passHash.length() * 4);
		SecretKeyFactory factory = null;

		try {
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// hash password to validate
		byte[] newHash = null;
		try {
			newHash = factory.generateSecret(spec).getEncoded();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

		// build hashed password string
		StringBuilder sb = new StringBuilder();
		sb.append(DataConverter.byteArrayToHexString(newHash));
		return passHash.equals(sb.toString());
	}
}
