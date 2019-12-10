package com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.update;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.Init;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.LauncherConstants;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.OSUtil;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.OSUtil.OperatingSystem;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.accounts.Account;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.utils.LauncherConfiguration;
import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.utils.Logger;

import javafx.application.Platform;

public class GameLaunch {
	
	public static File workingDirectory = OSUtil.getDirectory();
	public static String gameDir = new File(workingDirectory, "bin").getAbsolutePath();
//	public static String gameFile = new File(gameDir, "minecraft.jar").getAbsolutePath();
	public static String gameFile = new File(gameDir, LauncherConfiguration.getVersionId() + ".jar").getAbsolutePath();
	public static String nativesDir = new File(workingDirectory, "natives").getAbsolutePath();
	public static String assetsDir = new File(workingDirectory, "assets").getAbsolutePath();
	public static String librariesDir = new File(workingDirectory, "libraries").getAbsolutePath();
	public static String mainClass = Init.getConfiguration().getLaunchClass();
	public static String version = Init.getConfiguration().getVersionId();
	public static String assetIndex = Init.getConfiguration().getAssetIndex();
	public static String minecraftArguments = Init.getConfiguration().getLaunchArguments();
	private static File launchFile;
	
	public static void createInstance() {
		ProcessBuilder pb = new ProcessBuilder(new String[0]);
		ArrayList<String> commands = new ArrayList();
		commands.add(getJavaDir());
		
		commands.add("-Xmx" + Account.getRam());
		
	    if (OSUtil.getOS().equals(OperatingSystem.windows)) {
	    	commands.add("-XX:+UnlockExperimentalVMOptions");
	    	commands.add("-XX:+UseG1GC");
	    	commands.add("-XX:G1NewSizePercent=20");
	    	commands.add("-XX:G1ReservePercent=20");
	    	commands.add("-XX:MaxGCPauseMillis=50");
	    	commands.add("-XX:G1HeapRegionSize=32M");
	    	commands.add("-XX:-UseAdaptiveSizePolicy");
	    	commands.add("-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump");
	    	commands.add("-Dos.name=Windows 10");
	    	commands.add("-Dos.version=10.0");
	    	commands.add("-Dfml.ignoreInvalidMinecraftCertificates=true");
	    	commands.add("-Dfml.ignorePatchDiscrepancies=true");
	    	commands.add("-Djava.library.path=" + new File(nativesDir).getAbsolutePath());
	    	commands.add("-Dminecraft.launcher.brand=Minecraft");
	    	commands.add("-Dminecraft.launcher.version=30");
	    	commands.add("-Dminecraft.client.jar=" + gameFile);
	    } else if (OSUtil.getOS().equals(OperatingSystem.linux)) {
	    	commands.add("-Xdock:name=Minecraft");
	    	commands.add("-Xdock:icon=" + new File(new File(assetsDir).getAbsolutePath(), "icons/minecraft.icns").getAbsolutePath());
	    	commands.add("-XX:+UnlockExperimentalVMOptions");
	    	commands.add("-XX:+UseG1GC");
	    	commands.add("-XX:G1NewSizePercent=20");
	    	commands.add("-XX:G1ReservePercent=20");
	    	commands.add("-XX:MaxGCPauseMillis=50");
	    	commands.add("-XX:G1HeapRegionSize=32M");
	    	commands.add("-XX:-UseAdaptiveSizePolicy");
	    	commands.add("-Dos.name=Windows 10");
	    	commands.add("-Dos.version=10.0");
	    	commands.add("-Djava.library.path=" + new File(nativesDir).getAbsolutePath());
	    	commands.add("-Dminecraft.launcher.brand=Minecraft");
	    	commands.add("-Dminecraft.launcher.version=30");
	    	commands.add("-Dminecraft.client.jar=" + gameFile);
	    } else if (OSUtil.getOS().equals(OperatingSystem.macos)) {
	    	commands.add("-Xdock:name=Minecraft");
	    	commands.add("-Xdock:icon=" + new File(new File(assetsDir).getAbsolutePath(), "icons/minecraft.icns").getAbsolutePath());
	    	commands.add("-XX:+UnlockExperimentalVMOptions");
	    	commands.add("-XX:+UseG1GC");
	    	commands.add("-XX:G1NewSizePercent=20");
	    	commands.add("-XX:G1ReservePercent=20");
	    	commands.add("-XX:MaxGCPauseMillis=50");
	    	commands.add("-XX:G1HeapRegionSize=32M");
	    	commands.add("-XX:-UseAdaptiveSizePolicy");
	    	commands.add("-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump");
	    	commands.add("-Dos.name=Windows 10");
	    	commands.add("-Dos.version=10.0");
	    	commands.add("-Djava.library.path=" + new File(nativesDir).getAbsolutePath());
	    	commands.add("-Dminecraft.launcher.brand=Minecraft");
	    	commands.add("-Dminecraft.launcher.version=30");
	    	commands.add("-Dminecraft.client.jar=" + gameFile);
	    }
	    
		commands.add("-cp");
		commands.add(getClasspath());
		commands.add(mainClass);
		minecraftArguments = minecraftArguments + " --width ${resolution_width} --height ${resolution_height}";
		minecraftArguments = minecraftArguments.replace("${auth_player_name}", Account.getUsername());
		minecraftArguments = minecraftArguments.replace("${version_name}", version);
		minecraftArguments = minecraftArguments.replace("${game_directory}", gameDir);
		minecraftArguments = minecraftArguments.replace("${assets_root}", assetsDir);
		minecraftArguments = minecraftArguments.replace("${assets_index_name}", assetIndex);
		minecraftArguments = minecraftArguments.replace("${auth_uuid}", Account.getUuID());
		minecraftArguments = minecraftArguments.replace("${auth_access_token}", Account.getAccessToken());
		minecraftArguments = minecraftArguments.replace("${user_properties}", "{}");
		minecraftArguments = minecraftArguments.replace("${user_type}", "legacy");
		minecraftArguments = minecraftArguments.replace("${version_type}", "release");
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		minecraftArguments = minecraftArguments.replace("${resolution_width}", "" + screenSize.getWidth());
//		minecraftArguments = minecraftArguments.replace("${resolution_height}", "" + screenSize.getHeight());
		minecraftArguments = minecraftArguments.replace("${resolution_width}", "" + LauncherConstants.getGameWidth());
		minecraftArguments = minecraftArguments.replace("${resolution_height}", "" + LauncherConstants.getGameHeight());
		
		String[] launchArgs = minecraftArguments.split(" ");
		commands.addAll(Arrays.asList(launchArgs));
		Logger.write("Launching game...");
//		printList(commands);
//		for (int i = 0; i < launchArgs.length; i++) {
//			if (launchArgs[i].startsWith("--")) {
//				Logger.write(launchArgs[i] + " " + launchArgs[i + 1]);
//			}
//		}
		if (OSUtil.getOS() == OSUtil.OperatingSystem.windows) {
			createProcess(pb, commands, 0);
		} else if (OSUtil.getOS() == OSUtil.OperatingSystem.macos) {
			createProcess(pb, commands, 1);
		} else if (OSUtil.getOS() == OSUtil.OperatingSystem.linux) {
			createProcess(pb, commands, 2);
		}
	}
	
	public static void printList(ArrayList<String> list){
	    for(String elem : list){
	        System.out.println(elem + " ");
	    }
	}
	
	private static void createProcess(ProcessBuilder pb, ArrayList<String> commands, int os) {
		pb.directory(workingDirectory);
		if (os == 0) {
			launchFile = OSUtil.writeFile("offwin.bat", commands.toString());
			pb.command(commands);
			pb.inheritIO();
			try {
				pb.start();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
		        Platform.exit();
		        System.exit(0);
			}
		}
		else if (os == 1) {
			launchFile = OSUtil.writeFile("offlinux.sh", commands.toString());
			launchFile.setExecutable(true);
			launchFile.setReadable(true);
			launchFile.setWritable(true);
			Process proc  = null;
			try {
				proc = pb.redirectErrorStream(true).start();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				proc.waitFor();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			Logger.write(commands.toString());
			pb.command(commands);
			pb.inheritIO();
			try {
				pb.start();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
		        Platform.exit();
		        System.exit(0);
			}
		}
		else if (os == 2) {
			launchFile = OSUtil.writeFile("offothers.sh", commands.toString());
			launchFile.setExecutable(true);
			launchFile.setReadable(true);
			launchFile.setWritable(true);
			try {
				Runtime.getRuntime().exec("chmod u=rwx,g=r,o=- " + launchFile.getAbsolutePath());
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			Process proc  = null;
			try {
				proc = pb.redirectErrorStream(true).start();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				proc.waitFor();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			Logger.write(commands.toString());
			pb.command(commands);
			pb.inheritIO();
			try {
				pb.start();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
		        Platform.exit();
		        System.exit(0);
			}
		}
			
	}
	
	private static String getClasspath() {
		String classpath = "";
		ArrayList<File> libs = OSUtil.list(new File(librariesDir));
		String separator = System.getProperty("path.separator");
		for (File lib : libs) {
			classpath += lib.getAbsolutePath() + separator;
		}
		classpath += gameFile;
		return classpath;
	}
	
	public static String getJavaDir() {
		String separator = java.lang.System.getProperty("file.separator");
		String path = java.lang.System.getProperty("java.home") + separator + "bin" + separator;
		
		if ((OSUtil.getOS() == OSUtil.OperatingSystem.windows)) {
			if (System.getProperty("os.arch").contains("64")) {
				path = OSUtil.getDirectory() + separator + "bin" + separator + "jre-64" + separator + "bin" + separator;
				if (new File(path + "javaw.exe").isFile()) {
					Logger.write("Used JRE(x64) " + path + "javaw.exe");
					return path + "javaw.exe";
				}
			}
			else {
				path = OSUtil.getDirectory() + separator + "bin" + separator + "jre-32" + separator + "bin" + separator;
				if ((OSUtil.getOS() == OSUtil.OperatingSystem.windows) && (new File(path + "javaw.exe").isFile())) {
					Logger.write("Used JRE(x32)" + path + "javaw.exe");
					return path + "javaw.exe";
				}
			}
		}
		Logger.write("No JRE installed in " + OSUtil.getDirectory() + "/bin/jre. Using default.");
		path = java.lang.System.getProperty("java.home") + separator + "bin" + separator;
		if ((OSUtil.getOS() == OSUtil.OperatingSystem.windows) && (new File(path + "javaw.exe").isFile())) {
		return path + "javaw.exe";
		}
		return path + "java";
	}
	
}
