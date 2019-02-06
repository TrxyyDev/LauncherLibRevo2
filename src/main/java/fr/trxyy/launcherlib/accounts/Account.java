package fr.trxyy.launcherlib.accounts;

public class Account {
	
	public static String username = "NO_ACCOUNT_SELECTED";
	public static String password = "";
	public static String uuId = "0";
	public static String accessToken = "0";
	public static String clientToken = "0";
	private static String userType = "legacy";
	public static String secretKey = "0";
	private static String ram = "1G";

	public Account(String user, String uuid, String acctoken, String cToken) {
		this.username = user;
		this.uuId = uuid;
		this.accessToken = acctoken;
		this.clientToken = cToken;
	}

	public static String getSecretKey() {
		return secretKey;
	}

	public static void setSecretKey(String key) {
		secretKey = key;
	}

	public static String getClientToken() {
		return clientToken;
	}

	public static void setClientToken(String clientToken_) {
		clientToken = clientToken_;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username_) {
		username = username_;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password_) {
		password = password_;
	}

	public static String getUuID() {
		return uuId;
	}

	public static void setUuID(String uuID_) {
		uuId = uuID_;
	}

	public static String getAccessToken() {
		return accessToken;
	}

	public static void setAccessToken(String accessToken_) {
		accessToken = accessToken_;
	}

	public static String getUserType() {
		return userType;
	}

	public static void setUserType(String userType_) {
		userType = userType_;
	}

	public static void setRam(String rr) {
		ram = rr;
	}
	
	public static String getRam() {
		return ram;
	}
}
