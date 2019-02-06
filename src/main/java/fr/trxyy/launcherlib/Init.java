package fr.trxyy.launcherlib;

import fr.trxyy.launcherlib.OSUtil.OperatingSystem;
import fr.trxyy.launcherlib.utils.LauncherConfiguration;
import fr.trxyy.launcherlib.utils.PropertiesSaver;

public class Init {
	private static String buildJson;
	private static LauncherConfiguration configuration;
	private static boolean preInitFinished = false;
	private static PropertiesSaver userProps;

//	public static void loadConfiguration() {
//		userProps = new PropertiesSaver();
//		getUserProps().loadUserProps();
//		try {
//			buildJson = LauncherConstants.loadJsonFromUrl(LauncherConstants.getBaseUrl() + "launchercfg.json");
//			configuration = LauncherConstants.getGson().fromJson(buildJson, LauncherConfiguration.class);
//			Logger.write("JSON File successfully loaded.");
//			preInitFinished = true;
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (initialisationFinish()) {
//				Logger.write("Launcher configuration loaded.");
//			}
//		}
//	}

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
//		LauncherConstants.setBaseUrl(baseUrl);
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
