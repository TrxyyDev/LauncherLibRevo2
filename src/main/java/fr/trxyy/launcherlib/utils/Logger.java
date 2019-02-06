package fr.trxyy.launcherlib.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {

	public static void write(String s) {
		System.out.println("[" + getTime() + "] " + s);
	}
	
	public static void err(String s) {
		System.err.println("[" + getTime() + "] " + s);
	}

	public static String getTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm:ss");
		return sdf.format(cal.getTime());
	}

}
