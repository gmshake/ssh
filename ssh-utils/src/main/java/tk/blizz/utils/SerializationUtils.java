package tk.blizz.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Serialization Utils
 * 
 * @author zlei.huang@gmail.com 2013-07-28
 * 
 */
public class SerializationUtils {

	/**
	 * serialize an object to byte array
	 * 
	 * @param obj
	 * @return
	 * @throws NotSerializableException
	 *             when any member of obj is not serializable
	 */
	public static byte[] serialize(Serializable obj)
			throws NotSerializableException {
		final ByteArrayOutputStream bs = new ByteArrayOutputStream(8192);
		ObjectOutputStream os = null;

		try {
			os = new ObjectOutputStream(bs);
			os.writeObject(obj);
			return bs.toByteArray();
		} catch (final NotSerializableException e) {
			throw e;
		} catch (final IOException e) {
		} finally {
			try {
				bs.close();
			} catch (IOException e) {
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
		}
		return new byte[0];
	}

	/**
	 * write object to OutputStream
	 * 
	 * @param obj
	 * @param outputStream
	 * @throws IOException
	 */
	public static void serialize(Serializable obj, OutputStream outputStream)
			throws IOException {
		ObjectOutputStream os = null;

		try {
			os = new ObjectOutputStream(outputStream);
			os.writeObject(obj);
		} catch (final IOException e) {
			throw e;
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
