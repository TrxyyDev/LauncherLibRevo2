package com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2;

import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.OSUtil.OperatingSystem;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.utils.LauncherConfiguration;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.utils.PropertiesSaver;

public class Init {
	private static String buildJson;
	private static LauncherConfiguration configuration;
	private static boolean preInitFinished = false;
	private static PropertiesSaver userProps;


	public static PropertiesSaver getUserProps() {
		return userProps;
	}

	private static boolean initialisationFinish() {
		return preInitFinished;
	}

	public static LauncherConfiguration getConfiguration() {
		return configuration;
	}

	public static void registerLauncherConfiguration(String title, int width, int height, String dir, String locate/*, String baseUrl*/) {
		preConfigure();
		LauncherConstants.setLauncherTitle(title);
		LauncherConstants.setLauncherWidth(width);
		LauncherConstants.setLauncherHeight(height);
		LauncherConstants.setLauncherDirectory(dir);
		LauncherConstants.setResourceLocation(locate);
		userProps = new PropertiesSaver();
		getUserProps().loadUserProps();
	}
	
	public static void preConfigure() {
		if (OSUtil.getOS().equals(OperatingSystem.windows) && System.getProperty("os.version").contains("10")) {
			System.setProperty("os.name", "Windows 10");
			System.setProperty("os.version", "10.0");
		}
	}

	public static boolean isPreInitFinished() {
		return preInitFinished;
	}

	public static void setConfiguration(LauncherConfiguration config) {
		configuration = config;
	}
}
