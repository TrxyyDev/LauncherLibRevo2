package fr.trxyy.launcherlib.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import fr.trxyy.launcherlib.LauncherConstants;
import fr.trxyy.launcherlib.OSUtil;
import fr.trxyy.launcherlib.accounts.Account;

public class PropertiesSaver {

	public static String username;
	public static String ram;
	public static String gameWidth;
	public static String gameHeight;

	private static Properties prop;
	private static OutputStream output;
	private static InputStream input;

	public static void loadUserProps() {
		File f = new File(OSUtil.getDirectory() + "/userprops.cfg");
		if (f.exists()) {
			Logger.write("user Properties file exists. Loading.");
			PropertiesSaver.loadProps();
		} else {
			try {
				f.getParentFile().mkdirs();
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Logger.write("Creating user Properties folder.");
			PropertiesSaver.saveDefaultProps();
			PropertiesSaver.loadProps();
		}
	}

	private static void saveDefaultProps() {
		prop = new Properties();
		try {
			output = new FileOutputStream(OSUtil.getDirectory() + "/userprops.cfg");
			prop.setProperty("accountName", "Pseudo");
			prop.setProperty("ramAllowed", "1G");
			prop.setProperty("gameWidth", "854");
			prop.setProperty("gameHeight", "480");
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static void saveProps() {
		prop = new Properties();
		try {
			output = new FileOutputStream(OSUtil.getDirectory() + "/userprops.cfg");
			Logger.write("AccountNameSave: " + Account.getUsername());
			prop.setProperty("accountName", Account.getUsername());
			prop.setProperty("ramAllowed", Account.getRam());
			prop.setProperty("gameWidth", LauncherConstants.getGameWidth());
			prop.setProperty("gameHeight", LauncherConstants.getGameHeight());
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static void loadProps() {
		prop = new Properties();
		try {
			input = new FileInputStream(OSUtil.getDirectory() + "/userprops.cfg");
			prop.load(input);
			Logger.write(prop.getProperty("accountName"));
			username = prop.getProperty("accountName");
			Account.setUsername(prop.getProperty("accountName"));
			Logger.write(prop.getProperty("ramAllowed"));
			ram = prop.getProperty("ramAllowed");
			Account.setRam(prop.getProperty("ramAllowed"));
			gameWidth = prop.getProperty("gameWidth");
			LauncherConstants.setGameWidth(prop.getProperty("gameWidth"));
			gameHeight = prop.getProperty("gameHeight");
			LauncherConstants.setGameHeight(prop.getProperty("gameHeight"));

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		PropertiesSaver.username = username;
	}

	public static String getRam() {
		return ram;
	}

	public static void setRam(String ram) {
		PropertiesSaver.ram = ram;
	}

}
