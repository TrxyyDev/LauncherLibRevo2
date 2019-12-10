package com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.utils;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class FileUtil {

	public static String getEtag(final HttpURLConnection connection) {
		return getEtag(connection.getHeaderField("ETag"));
	}

	public static String getEtag(String etag) {
		if (etag == null)
			etag = "-";
		else if (etag.startsWith("\"") && etag.endsWith("\""))
			etag = etag.substring(1, etag.length() - 1);

		return etag;
	}

	public static String getMD5(final File file) {
		DigestInputStream stream = null;
		try {
			stream = new DigestInputStream(new FileInputStream(file), MessageDigest.getInstance("MD5"));
			final byte[] buffer = new byte[65536];

			int read = stream.read(buffer);
			while (read >= 1)
				read = stream.read(buffer);
		} catch (final Exception ignored) {
			return null;
		} finally {
			closeSilently(stream);
		}

		return String.format("%1$032x", new Object[] { new BigInteger(1, stream.getMessageDigest().digest()) });
	}

	public static String getSHA1(final File file) throws NoSuchAlgorithmException, IOException {
		final MessageDigest messageDigest = MessageDigest.getInstance("SHA1");

		try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
			final byte[] buffer = new byte[1024];
			for (int read = 0; (read = is.read(buffer)) != -1;) {
				messageDigest.update(buffer, 0, read);
			}
		}
		try (Formatter formatter = new Formatter()) {
			for (final byte b : messageDigest.digest()) {
				formatter.format("%02x", b);
			}
			return formatter.toString();
		}
	}

	public static boolean matchSHA1(File file, String sha1) {
		try {
			return getSHA1(file).equals(sha1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void closeSilently(final Closeable closeable) {
		if (closeable != null)
			try {
				closeable.close();
			} catch (final IOException localIOException) {
			}
	}

	public static void deleteFile(File element) {
		if (element.isDirectory()) {
			for (File sub : element.listFiles()) {
				Logger.write("Deleting..: " + sub);
				deleteFile(sub);
			}
		}
		element.delete();
	}

	public static void deleteSomething(String path) {
		Path filePath_1 = Paths.get(path);
		try {
			Files.delete(filePath_1);
		} catch (NoSuchFileException x) {
			System.err.format("%s: no such" + " file or directory%n", path);
		} catch (DirectoryNotEmptyException x) {
			System.err.format("%s not empty%n", path);
		} catch (IOException x) {
			System.err.println(x);
		}
	}

	public static void deleteDir(File file) {
		File[] contents = file.listFiles();
		if (contents != null) {
			for (File f : contents) {
				Logger.write("Deleting directory..." + f);
				deleteDir(f);
			}
		}
		file.delete();
	}
	
	public static void copy(String source, String string) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		Logger.write("Copie > " + source);
		try {
			InputStream is = FileUtil.class.getResourceAsStream(source);
			outputStream = new FileOutputStream(new File(string));
			byte[] bytBuff = new byte[1024];
			int bytesRead;
			while ((bytesRead = inputStream.read(bytBuff)) > 0) {
				outputStream.write(bytBuff, 0, bytesRead);
			}
		} finally {
			inputStream.close();
			outputStream.close();
		}
	}
}
