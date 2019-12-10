package com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.utils;

public class LauncherConfiguration {
	private boolean currentlyMaintenance;
	private static String maintenanceUrl;
	private static String versionId;
	private static String launchClass;
	private static String assetIndex;
	private static String downloadUrl;
	private static String launchArguments;
	
//	public LauncherConfiguration(String vId, String assetIndx, String mainClass, String dlURL) {
//		
//	}

	public Boolean getCurrentlyMaintenance() {
		return currentlyMaintenance;
	}

	public void setCurrentlyMaintenance(boolean currentlyMaintenance) {
		this.currentlyMaintenance = currentlyMaintenance;
	}

	public static String getVersionId() {
		return versionId;
	}

	public static void setVersionId(String id) {
		versionId = id;
	}

	public String getLaunchClass() {
		return launchClass;
	}

	public static void setLaunchClass(String launch) {
		launchClass = launch;
	}

	public String getAssetIndex() {
		return assetIndex;
	}

	public static void setAssetIndex(String assetId) {
		assetIndex = assetId;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public static void setDownloadUrl(String url) {
		downloadUrl = url;
	}

	public String getLaunchArguments() {
		return launchArguments;
	}

	public static void setLaunchArguments(String args) {
		launchArguments = args;
	}

	public static String getMaintenanceUrl() {
		return maintenanceUrl;
	}

	public static void setMaintenanceUrl(String ull) {
		maintenanceUrl = ull;
	}
}