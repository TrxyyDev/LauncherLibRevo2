package com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

public class LauncherConstants {
	
	private static String gameWidth;
	private static String gameHeight;
	
	private static String launcherTitle;
	private static int launcherWidth;
	private static int launcherHeight;
	private static String launcherDirectory;

	public static String libraryUpdateUrl = "http://localhost/beta/checkforupdate.php";
	public static String resourceLocation = "/resources/";

	public static Gson getGson() {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());
		gsonBuilder.enableComplexMapKeySerialization();
		gsonBuilder.setPrettyPrinting();
		return gsonBuilder.create();
	}

	public static String loadJsonFromUrl(String baseUrl) throws IOException {
		URL url = new URL(baseUrl);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		String json = new String();
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			json = json + inputLine;
		}
		return json;
	}

	public static URI constantURI(String input) {
		try {
			return new URI(input);
		} catch (URISyntaxException e) {
			throw new Error(e);
		}
	}

	public static URL constantURL(String input) {
		try {
			return new URL(input);
		} catch (MalformedURLException e) {
			throw new Error(e);
		}
	}

	@SuppressWarnings("unused")
	private int getFileSizeFromURLWithoutDownloading(URL theUrl) {
		HttpURLConnection httpUrlConnection = null;
		try {
			httpUrlConnection = (HttpURLConnection) theUrl.openConnection();
			httpUrlConnection.setRequestMethod("HEAD");
			httpUrlConnection.getInputStream();
			return httpUrlConnection.getContentLength();
		} catch (IOException e) {
			return -1;
		} finally {
			httpUrlConnection.disconnect();
		}
	}

//	public static String getBaseUrl() {
//		return baseUrl;
//	}
//
//	public static void setBaseUrl(String czaz) {
//		baseUrl = czaz;
//	}
	
	public static String getResourceLocation() {
		return resourceLocation;
	}

	public static void setResourceLocation(String locate) {
		resourceLocation = locate;
	}
	
	public static String getTitle() {
		return launcherTitle;
	}

	public static int getWidth() {
		return launcherWidth;
	}

	public static int getHeight() {
		return launcherHeight;
	}
	
	public static String getGameWidth() {
		return gameWidth;
	}

	public static String getGameHeight() {
		return gameHeight;
	}
	
	public static void setGameWidth(String w) {
		gameWidth = w;
	}

	public static void setGameHeight(String fff) {
		gameHeight = fff;
	}


	public static String getLauncherDirectory() {
		return launcherDirectory;
	}

	public static void setLauncherTitle(String tt) {
		launcherTitle = tt;
	}

	public static void setLauncherWidth(int w) {
		launcherWidth = w;
	}

	public static void setLauncherHeight(int fff) {
		launcherHeight = fff;
	}

	public static void setLauncherDirectory(String dir) {
		launcherDirectory = dir;
	}
}
