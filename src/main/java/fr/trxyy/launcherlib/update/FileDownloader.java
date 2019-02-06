package fr.trxyy.launcherlib.update;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Lists;

import fr.trxyy.launcherlib.utils.Logger;
import javafx.application.Platform;

public class FileDownloader {
	
	public static GameUpdater updater = new GameUpdater();
	public static int totalSize = 0;
	public static int fakeSize = 0;
	public static int downloadedSize;
	public static double percentage = 0;

	public static void download(String fileUrl, String fileName, String flNm) {
		Logger.write("Telechargement: " + flNm);
		String[] parts = flNm.split("/");
		String lastWord = parts[parts.length - 1];
		updater.getProgressBar().setCurrentFile(lastWord);
		try {
			URL url = new URL(fileUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			int filesize = connection.getContentLength();
			float totalDataRead = 0;

			File locate = new File(fileName);
			if (!locate.exists()) {
				locate.getParentFile().mkdirs();
				locate.createNewFile();
			}

			BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
			FileOutputStream fos = new FileOutputStream(fileName);
			BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
			byte[] data = new byte[1024];
			int read = 0;
			while ((read = in.read(data, 0, 1024)) >= 0) {
				downloadedSize += read;
				bout.write(data, 0, read);
				percentage = updater.getDownloader().downloadedFiles * 1.0D / updater.getDownloader().needToDownload;
			}
			Platform.runLater(() -> updater.getProgressBar().setProgress(percentage));
			bout.close();
			in.close();
			updater.downloadTask.downloadedFiles++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void downloadAsset(String fileUrl, String fileName, String flNm) {
		Logger.write("Telechargement: " + flNm);
		String[] parts = flNm.split("/");
		String lastWord = parts[parts.length - 1];
		updater.getProgressBar().setCurrentFile(lastWord);
		try {
			URL url = new URL(fileUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			int filesize = connection.getContentLength();
			float totalDataRead = 0;

			File locate = new File(fileName);
			if (!locate.exists()) {
				locate.getParentFile().mkdirs();
				locate.createNewFile();
			}

			BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
			FileOutputStream fos = new FileOutputStream(fileName);
			BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
			byte[] data = new byte[1024];
			int read = 0;
			while ((read = in.read(data, 0, 1024)) >= 0) {
				bout.write(data, 0, read);
			}
			bout.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void downloadLauncher(String fileUrl, String fileName, String flNm) {
		Logger.write("Telechargement: " + flNm);
		try {
			URL url = new URL(fileUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
			int filesize = connection.getContentLength();
			float totalDataRead = 0;

			File file = new File(fileName);
			file.getParentFile().mkdirs();
			Logger.write("Trying to create file: " + file.getPath());
			file.createNewFile();

			BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
			FileOutputStream fos = new FileOutputStream(fileName);
			BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
			byte[] data = new byte[1024];
			int read = 0;
			while ((read = in.read(data, 0, 1024)) >= 0) {
				downloadedSize += read;
				bout.write(data, 0, read);

				double total = totalSize;
				double actuel = downloadedSize;
				if (total == 0.0D) {
					total = 0.1D;
				}
				percentage = ((actuel / total * 100) / 10000.0D);
			}
			bout.close();
			in.close();
			updater.downloadTask.downloadedFiles++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void copyFile(File source, File dest) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		Logger.write("Copie > " + source);
		updater.downloadTask.downloadedFiles++;
//		percentage = updater.getDownloader().downloadedFiles * 1.0D / updater.getDownloader().needToDownload;
//		Platform.runLater(() -> updater.getProgressBar().setProgress(percentage));
		try {
			inputStream = new FileInputStream(source);
			outputStream = new FileOutputStream(dest);
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

	private static void deleteEmptyDirectories(File directory) {
		for (;;) {
			Collection<File> files = listEmptyDirectories(directory);
			if (files.isEmpty()) {
				return;
			}
			for (File file : files) {
				if (FileUtils.deleteQuietly(file)) {
					Logger.write("Deleted empty directory {" + file + "}");
				} else {
					return;
				}
			}
		}
	}

	private static Collection<File> listEmptyDirectories(File directory) {
		List<File> result = Lists.newArrayList();
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					File[] subFiles = file.listFiles();
					if ((subFiles == null) || (subFiles.length == 0)) {
						result.add(file);
					} else {
						result.addAll(listEmptyDirectories(file));
					}
				}
			}
		}
		return result;
	}
}
