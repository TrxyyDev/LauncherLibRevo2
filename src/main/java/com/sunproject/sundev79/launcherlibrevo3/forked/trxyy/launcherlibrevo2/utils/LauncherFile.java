package com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.utils;

public class LauncherFile {

	public double size;
	public String url;
	public String path;

	public LauncherFile(double s, String u, String p) {
		this.size = s;
		this.url = u;
		this.path = p;
	}

	public double getSize() {
		return this.size;
	}

	public String getUrl() {
		return this.url;
	}

	public String getPath() {
		return this.path;
	}

	@Override
	public String toString() {
		return "<" + size + ", " + url + ", " + path + ">";
	}

}
