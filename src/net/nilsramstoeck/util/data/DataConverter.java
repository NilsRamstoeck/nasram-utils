package net.nilsramstoeck.util.data;

/**
 * A Collection of methods to convert various forms of data into another
 * 
 * @author Nils Ramstoeck
 *
 */
public final class DataConverter {

	/**
	 * Gets a Hex String from a ByteArray
	 * @param data ByteArray to be HexStringifyd 
	 * @return data as HexString
	 */
	public static String byteArrayToHexString(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for (byte b : data) {
			sb.append(String.format("%02X", b));
		}
		return sb.toString();
	}

	/**
	 * Gets ByteArray from a Hex String
	 * 
	 * @param s HexString
	 * @return Byte Array
	 */
	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

}
