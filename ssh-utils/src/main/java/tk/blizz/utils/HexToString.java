package tk.blizz.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HexToString {

	public static String hexToString(byte[] hex) {
		StringBuilder sb = new StringBuilder(hex.length * 2);
		for (byte b : hex) {
			sb.append(Character.toString((char) b));
		}
		return sb.toString();
	}

	/**
	 * hash(name + hash(pwd + salt) + salt);
	 * 
	 * @param name
	 * @param pwd
	 * @param salt
	 * @return
	 */
	public static String hashUserNamePwdSalt(String name, String pwd,
			String salt) {
		StringBuilder sb = new StringBuilder(name.length() + 128
				+ salt.length());
		sb.append(name);
		sb.append(md5Hash(pwd + salt));
		sb.append(salt);
		return md5Hash(sb.toString());
	}

	/**
	 * return md5 hash string
	 * 
	 * @param s
	 * @return
	 */
	public static String md5Hash(String s) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		return hexToString(md.digest(s.getBytes(Charset.forName("UTF-8"))));
	}
}
