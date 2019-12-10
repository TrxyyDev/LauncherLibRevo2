package com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.update;

import java.io.File;
import java.util.HashMap;

import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.OSUtil;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.components.LauncherProgressBar;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.utils.LauncherFile;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.utils.Logger;

public class GameUpdater {
	public static HashMap<String, LauncherFile> filesToUpdate = new HashMap();
	public GameDownloader downloadTask;
	public Thread updateThread;
	public boolean updating = false;
	public static LauncherProgressBar progressBar;

	public void checkForUpdate(LauncherProgressBar suppBar) {
		
		File file = new File(OSUtil.getDirectory(), "bin");
		file.getParentFile().mkdirs();
		Logger.write("Trying to create file: " + file.getPath());
		if (!file.exists()) {
			file.mkdirs();
		}
		
		if (suppBar != null) {
			progressBar = suppBar;
		} else {	
			progressBar = new LauncherProgressBar();
		}
		updateThread = new Thread() {
			public void run() {
				GameParser.prepareFiles();
				downloadTask = new GameDownloader(filesToUpdate, updateThread);
				updating = true;
				downloadTask.startDownloading();
			}
		};
		updateThread.start();
	}
	
	public void checkForUpdateLauncher() {
		updateThread = new Thread() {
			public void run() {
				GameParser.prepareFiles();
				if (!filesToUpdate.isEmpty()) {
					downloadTask = new GameDownloader(filesToUpdate, updateThread);
					updating = true;
					downloadTask.startDownloadingLauncher();
				} else {
					downloadTask = new GameDownloader(filesToUpdate, updateThread);
					updating = false;
					Logger.write("Aucune mise a jour requise launcher.");
					downloadTask.verifyIntegrity(OSUtil.getDirectory());
					downloadTask.launchLauncher();
				}
			}
		};
		updateThread.start();
	}

	public GameDownloader getDownloader() {
		return this.downloadTask;
	}

	public static LauncherProgressBar getProgressBar() {
		return progressBar;
	}
	
	public boolean isUpdating() {
		return this.updating;
	}
}
