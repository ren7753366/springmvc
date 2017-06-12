package com.renms.common.constant;

public class ConfigurationInfo {

	private static String clientId;
	private static String clientSecret;
	
	public static String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		ConfigurationInfo.clientId = clientId;
	}

	public static String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		ConfigurationInfo.clientSecret = clientSecret;
	}
}