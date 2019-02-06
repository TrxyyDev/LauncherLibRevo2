package fr.trxyy.launcherlib.update;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import fr.trxyy.launcherlib.Init;
import fr.trxyy.launcherlib.OSUtil;
import fr.trxyy.launcherlib.interfaces.IScreen;
import fr.trxyy.launcherlib.utils.FileUtil;
import fr.trxyy.launcherlib.utils.LauncherFile;
import fr.trxyy.launcherlib.utils.Logger;
import javafx.application.Platform;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class GameDownloader {
	
	public static int downloadedFiles;
	public static int needToDownload;
	public static HashMap<String, LauncherFile> customFiles = new HashMap();
	public Thread updateThread;
	public GameUpdater updater = new GameUpdater();
	public static String totalFiles;

	public GameDownloader(HashMap<String, LauncherFile> totalFiles, Thread thread) {
		this.customFiles = totalFiles;
		this.updateThread = thread;
		Logger.write(this.needToDownload + " files need to be up to date.");
	}
	
	public static void addToVerifyIntegrity(String addTo) {
        totalFiles = totalFiles + " " + addTo;
    }

	public void startDownloading() {
		for (String name : this.customFiles.keySet()) {
			String fileDest = name.replaceAll(Init.getConfiguration().getDownloadUrl(), "");
			String fileName = fileDest;
			int index = fileName.lastIndexOf("\\");
			String dirLocation = fileName.substring(index + 1);
			
			if (!name.endsWith("/")) {
				FileDownloader.download(Init.getConfiguration().getDownloadUrl() + name, OSUtil.getDirectory() + File.separator + dirLocation, dirLocation);
			}
		}
		this.updateThread.interrupt();
		this.updater.updating = false;
		Logger.write("Download finished.");
		IScreen.timeline.stop();
		IScreen.timeline_.stop();
		Logger.write("Verifying installation...");
		IScreen.verifyInstall();
		this.verifyIntegrity(OSUtil.getDirectory());
		IScreen.prepareLaunch();
		GameLaunch.createInstance();
	}
	
//	public void prepareNatives() {
//		File nativesDirectory = new File(OSUtil.getDirectory(), "natives");
//		Logger.write("Deleting old natives...");
//		if (nativesDirectory.exists()) {
//			FileUtil.deleteDir(nativesDirectory);
//		}
//		if (!nativesDirectory.isDirectory()) {
//			nativesDirectory.mkdirs();
//		}
//
//		Logger.write("Unpacking natives to: " + nativesDirectory);
//		extractNatives();
//	}
//
//	private static void extractNatives() {
//		ArrayList<File> libs = OSUtil.list(new File(OSUtil.getDirectory(), "libraries"));
//		String separator = System.getProperty("path.separator");
//		for (File lib : libs) {
//			if (lib.getName().contains("natives")) {
//				ZipFile zip;
//				try {
//					zip = new ZipFile(lib);
//					zip.extractAll(OSUtil.getDirectory() + File.separator + "natives");
//				} catch (ZipException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}

	public void startDownloadingLauncher() {
		for (String name : this.customFiles.keySet()) {
			String fileDest = name.replaceAll(Init.getConfiguration().getDownloadUrl(), "");
			String fileName = fileDest;
			int index = fileName.lastIndexOf("\\");
			String dirLocation = fileName.substring(index + 1);
			
			if (!name.endsWith("/")) {
				FileDownloader.downloadLauncher(Init.getConfiguration().getDownloadUrl() + name, OSUtil.getDirectory() + File.separator + dirLocation, dirLocation);
			}
			if (this.downloadedFiles == this.needToDownload) {
				this.updateThread.interrupt();
				this.updater.updating = false;
				Logger.write("Download finished.");
				launchLauncher();
			}
		}
	}
	
	public void launchLauncher() {
		ProcessBuilder pb = new ProcessBuilder("java", "-jar", OSUtil.getDirectory() + "/launcher.jar");
		pb.directory(OSUtil.getDirectory());
		Process p = null;
		try {
			p = pb.start();
			Platform.exit();
		} catch (IOException e) {e.printStackTrace();}
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String s = "";
		try {
			while((s = in.readLine()) != null){
			    System.out.println(s);
			}
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public void verifyIntegrity(File dir) {
		File[] subDirs = dir.listFiles();
		int folderIndex = 0;
		if (dir.isDirectory()) {
			
			for (folderIndex = 0; folderIndex < subDirs.length; folderIndex++) {
				File nextFolder = subDirs[folderIndex];
				verifyIntegrity(nextFolder);
				File forUrl = new File("" + subDirs[folderIndex].toString().replace(OSUtil.getDirectory() + "\\", ""));
				File location = new File("" + subDirs[folderIndex]);
				if (location.isFile()) {
					File f = new File(OSUtil.getDirectory() + "/" + subDirs[folderIndex].toString().replace(OSUtil.getDirectory() + "\\", ""));		
					boolean exists = exists(subDirs[folderIndex].toString().replace(OSUtil.getDirectory() + "\\", ""));
					if (!exists) {
							if (f.getName().contains("pack.cfg") || f.getName().contains("userprops.cfg")) {
							}
							else {
								String parentDirName = f.getAbsoluteFile().getParent();
								if (parentDirName.contains("bin") || parentDirName.contains("logs") || parentDirName.contains("natives")) {
									if (parentDirName.contains("mods")) {
										FileUtil.deleteSomething(f.getAbsolutePath());
									}
								} else {
									FileUtil.deleteSomething(f.getAbsolutePath());
								}
							}
					}
				}
			}
		}
	}
	
	public static long folderSize(File directory) {
	    long length = 0;
	    for (File file : directory.listFiles()) {
	        if (file.isFile())
	            length += file.length();
	        else
	            length += folderSize(file);
	    }
	    return length;
	}
	
	private boolean exists(String fll) {
		if (totalFiles.contains(fll.replace("\\", "/"))) {
			return true;
		}
		return false;
	}
}
