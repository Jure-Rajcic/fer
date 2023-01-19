package hr.fer.oprpp1.hw05.crypto;

import hr.fer.zemris.java.hw05.shell.ShellIOException;

/**
 * class Util gives method for changing binary to hex (and hex to binary) encoded data
 * 
 * @author Jure Rajcic
 *
 */
public class Util {

	/* PITAT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 * jeli oo okej: opaska na koentar "You yourself are required to directly
		 * implement these methods. You are not allowed to utilize other methods which
		 * can perform the required
		 * transformations for you."
		 */

	/**
	 * method @return String hex representation of encoded @param bytearray
	 */
	public static String bytetohex(byte[] bytearray) {
		String res = new String();
		if (bytearray.length == 0)
			return res;
		for (byte b : bytearray) {
			res += String.format("%02x", b);
		}
		return res;
	}

	
	/**
	 * method @return byte array representing binary code of @param keyText
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() == 0)
			return new byte[0];
		if ((keyText.length() & 0x1) != 0)
			throw new ShellIOException(keyText + "has length that is not even");

		byte[] res = new byte[keyText.length() / 2];

		for (int i = 0; i < res.length; i++) {
			String hex = keyText.substring(i*2, i*2 + 2);
			int value = Integer.parseInt(hex, 16); 
        	boolean neg = switch (hex.charAt(0)) {
				case '0', '1', '2', '3', '4', '5', '6', '7' -> false;
				case '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' -> true;
				default -> throw new ShellIOException("Illegal hex value: " + hex);
			};
			if (neg) value -= 256;
			res[i] = (byte) value;
		}
		return res;
	}
}
