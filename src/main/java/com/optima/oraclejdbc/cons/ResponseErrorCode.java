package com.optima.oraclejdbc.cons;

public class ResponseErrorCode {

	public static final int SUCCESS = 1000;
	public static final int NO_RECORD_FOUND = 1001;
	
	public static final int INVALID_REQUEST_INPUT = 2001;
	public static final int SAME_OLD_PASSWORD = 2002;
	public static final int INTERNAL_SERVER_ERROR = 5001;
	public static final int TIBCO_CONNECTION_FAILED = 5002;
	
	
	public static final String MSG_SUCCESS = "Success";
	public static final String MSG_NO_RECORD_FOUND = "No Record Found";
	public static final String MSG_INVALID_REQUEST_INPUT = "Invalid Request Input";
	public static final String MSG_SAME_OLD_PASSWORD = "New password same with old password";
	public static final String MSG_INTERNAL_SERVER_ERROR = "Internal Server Error";
	public static final String MSG_TIBCO_CONNECTION_FAILED = "Error occured while connecting to TIBCO Server";
	
	public static final String MSG_CUST_NOT_EXIST = "Customer is not exist";
	public static final String MSG_RESET_PASSWORD_SUCCESS = "Reset statement password success";
	public static final String MSG_CHANGE_PASSWORD_SUCCESS = "Change statement password success";
	public static final String MSG_PASSWORD_NOT_MATCH = "Password and confirm password is not match";
}
