package fr.trxyy.launcherlib.accounts;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import fr.trxyy.launcherlib.utils.Logger;

public class AuthSystem {
	
	public static String authUrl = "http://localhost/auth/auth.php?";
	
	public static boolean login(String usr, String pwd) {
		String result = null;
		try {
			String parameters = "username=" + URLEncoder.encode(usr, "UTF-8") + "&password=" + URLEncoder.encode(pwd, "UTF-8");
			URL customUrl = new URL(getAuthUrl() + parameters);
			URLConnection yc = customUrl.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String line;
			while ((line = in.readLine()) != null)
				result = line;	
			in.close();
			if (result == null) {
				return false;
			}
			if (!result.contains(":")) {
				if (result.trim().equals("Bad Login")) {
					Logger.write("Identifiants incorrects.");
				} else if (result.trim().equals("Old version")) {
					Logger.write("Maintenance en cours.");
				} else {
					Logger.write("Connexion reussie.");
				}
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			;
		}
	    String[] values = result.split(":"); 
	    Account.setUsername(values[0].trim());
	    Account.setAccessToken(values[1].trim());
	    Account.setUuID(values[1].trim());
	    
	    Logger.write("Username: " + values[0]);
	    Logger.write("sessionId: " + values[1]);
		return true;
	}
	
	public static String getAuthUrl() {
		return authUrl;
	}
	
	public static void setAuthUrl(String custom) {
		authUrl = custom;
	}
	
}
