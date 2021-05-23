package com.deviget.minesweeper.util;

/**
 * Common Constants
 * @author Erneski Coronado
 *
 */
public class Constants {

	private Constants() {
		//default
	}
	
	public static final String AUTHORIZATION_STR = "authorization";
	public static final String UNAUTHORIZED_STR = "Unauthorized";
	public static final String BEARER_STR = "Bearer";
	public static final String API_KEY = "Api-Key";
	
	
	public static final String INFO_GENERATED_CRED = "Credentials for user {} with password {}, is {}";
	
	public static final String ERROR_MSG_GET_TOKEN = "Unable to get JWT Token";
	public static final String ERROR_MSG_EXP_TOKEN = "JWT Token has expired";
	public static final String ERROR_USER_DISABLED = "USER_DISABLED %s";
	public static final String ERROR_INVALID_CREDENTIALS = "INVALID_CREDENTIALS %s";
	
	
	
	
}
