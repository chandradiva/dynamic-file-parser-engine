package com.optima.oraclejdbc.cons;

import java.io.File;

public class CommonCons {
	// set this to false in real server
	public static final boolean dummy = true;
	public static final String X_WWW_FORM_URLECODED = "application/x-www-form-urlencoded";
	
//	public static final String DB_SCHEMA = "C##WEBSTATEMENT";
//	public static final String FE_DB_SCHEMA = "C##ESTATEMENT";
	
	public static final String DB_SCHEMA = "WEBSTATEMENT";
	public static final String FE_DB_SCHEMA = "ESTATEMENT";
	
//	public static String WORK_DIR = "D:\\WORK_DIR" + SEPARATOR;
//	public static String WORK_DIR = "F:\\WORK_DIR\\NISP" + SEPARATOR;
	public static String SEPARATOR = File.separator;
	public static String FTP_DIR = "";
	
	public static String DEFAULT_STATEMENT_PASSWORD = "7777777";
	
	public static String DAILY_STATEMENT_DIR = FTP_DIR + "DS" + SEPARATOR;
	public static String DAILY_STATEMENT_INPUT_DIR = DAILY_STATEMENT_DIR + "input" + SEPARATOR;
	public static String DAILY_STATEMENT_PENDING_DIR = DAILY_STATEMENT_DIR + "process" + SEPARATOR;
	public static String DAILY_STATEMENT_SUCCESS_DIR = DAILY_STATEMENT_DIR + "success" + SEPARATOR;
	public static String DAILY_STATEMENT_FAILED_DIR = DAILY_STATEMENT_DIR + "failed" + SEPARATOR;
	
	public static String CREDIT_CARD_DIR = FTP_DIR + "CC" + SEPARATOR;
	public static String CREDIT_CARD_INPUT_DIR = CREDIT_CARD_DIR + "input" + SEPARATOR;
	public static String CREDIT_CARD_PENDING_DIR = CREDIT_CARD_DIR + "process" + SEPARATOR;
	public static String CREDIT_CARD_SUCCESS_DIR = CREDIT_CARD_DIR + "success" + SEPARATOR;
	public static String CREDIT_CARD_FAILED_DIR = CREDIT_CARD_DIR + "failed" + SEPARATOR;
	
	public static String BANCASS_DIR = FTP_DIR + "BANCA" + SEPARATOR;
	public static String BANCASS_INPUT_DIR = BANCASS_DIR + "input" + SEPARATOR;
	public static String BANCASS_PENDING_DIR = BANCASS_DIR + "process" + SEPARATOR;
	public static String BANCASS_SUCCESS_DIR = BANCASS_DIR + "success" + SEPARATOR;
	public static String BANCASS_FAILED_DIR = BANCASS_DIR + "failed" + SEPARATOR;
	
	public static String UNIT_TRUST_DIR = FTP_DIR + "UT" + SEPARATOR;
	public static String UNIT_TRUST_INPUT_DIR = UNIT_TRUST_DIR + "input" + SEPARATOR;
	public static String UNIT_TRUST_PENDING_DIR = UNIT_TRUST_DIR + "process" + SEPARATOR;
	public static String UNIT_TRUST_SUCCESS_DIR = UNIT_TRUST_DIR + "success" + SEPARATOR;
	public static String UNIT_TRUST_FAILED_DIR = UNIT_TRUST_DIR + "failed" + SEPARATOR;
	
	public static String DDI_DIR = FTP_DIR + "EStatement" + SEPARATOR;
	
//	public static String FTP_INPUT = FTP_DIR + "input" + SEPARATOR;
//	public static String FTP_PENDING = FTP_DIR + "process" + SEPARATOR;
//	public static String FTP_SUCCESS = FTP_DIR + "success" + SEPARATOR;
//	public static String FTP_FAILED = FTP_DIR + "failed" + SEPARATOR;
	
	public static int ES_BEGIN_IDX_ACC_NO = 0;
	public static int ES_END_IDX_ACC_NO = 20;
}
