package com.optima.oraclejdbc.utils;

import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.model.ElixirModel;
import com.optima.oraclejdbc.response.Response;

public class CommonUtils {
	private static int CIF_KEY_LENGTH = 20;
	private static int ACC_NO_LENGTH = 19;
	
	public static boolean isPositiveInteger(String s){
		return isPositiveInteger(s, 10);
	}
	
	public static boolean isPositiveInteger(String s, int radix){
		if(s==null || s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
	
	public static boolean isNumber(String s){
		if(s==null || s.isEmpty()) return false;
		boolean dotFound = false;
		if( s.length() == 1 && (s.charAt(0) == '-' || s.charAt(0) == '.'))
			return false;
		for(int i=0; i< s.length(); i++ ){
			char c = s.charAt(i);
			if( i == 0 && c == '.' )
				return false;
			
			if( c == '-' ){
				if( i != 0 )
					return false;		
			}else if( c == '.' ){
				if( dotFound )
					return false;
				dotFound = true;
			}else if(Character.digit(s.charAt(i),10) < 0)
				return false;
		}
		return true;
	}
	
	public static boolean isNumber(String s, int precious, int scale){
		if(s==null || s.isEmpty()) return false;
		boolean dotFound = false;
		if( s.length() == 1 && (s.charAt(0) == '-' || s.charAt(0) == '.'))
			return false;
		for(int i=0; i< s.length(); i++ ){
			char c = s.charAt(i);
			if( i == 0 && c == '.' )
				return false;
			
			if( c == '-' ){
				if( i != 0 )
					return false;		
			}else if( c == '.' ){
				if( dotFound )
					return false;
				dotFound = true;
				if( i > precious - scale )
					return false;
				
				if( s.substring(i).length() > scale+1 )
					return false;
			}else if(Character.digit(s.charAt(i),10) < 0)
				return false;
		}
		return true;
	}
	
	public static boolean validateInput(BindingResult bindingResult, List<String>messages){
		if( bindingResult.hasErrors() ){
			for (FieldError fe : bindingResult.getFieldErrors()) {
				String field = fe.getField();
				messages.add(field+":"+fe.getDefaultMessage());
			}
			return false;
		}
		return true;
	}
	
	public static boolean checkPositiveInteger(String fieldName, String s, List<String>messages){
		if( !isPositiveInteger(s) ){
			messages.add(fieldName+" must be a positive integer");
			return false;
		}
		return true;
	}

	public static String getContextPath(Properties properties) {
		String server = properties.getProperty("stub_server");
		String protocol = properties.getProperty("stub_protocol");
		String port = properties.getProperty("stub_port");
		String contextPath = properties.getProperty("stub_context_path");
		if( port.equals("80") ){
			port = "";
		}else
			port = ":"+port;
		return protocol+"://"+server+port+contextPath;
	}	
	
	public static int getStubTimeout(Properties properties) {
		String stubTimeout = properties.getProperty("stub_timeout");
		if( stubTimeout != null )
			return Integer.parseInt(stubTimeout);
		return 30000;
	}
	
	public static String getFtpWorkDir(Properties properties) {
		String ftpDir = properties.getProperty("ftp_work_dir");
		
		if( ftpDir == null )
			return "";
		return ftpDir;
	}
	
	public static String getTempDir(Properties properties) {
		String tempDir = properties.getProperty("temp_dir");
		
		if( tempDir == null )
			return "";
		return tempDir;
	}
	
	public static String getMonthlyStatementDir(Properties properties) {
		String msDir = properties.getProperty("ms_dir");
		
		if( msDir == null )
			return "";
		return msDir;
	}
	
	public static String getMonthlyStatementHardcopyDir(Properties properties) {
		String msDir = properties.getProperty("ms_hc_dir");
		
		if( msDir == null )
			return "";
		return msDir;
	}
	
	public static ElixirModel getElixirModel(Properties properties) {
		
		ElixirModel model = new ElixirModel();
		model.setHostname(properties.getProperty("elixir_hostname"));
		model.setPort(Integer.parseInt(properties.getProperty("elixir_port")));
		model.setUsername(properties.getProperty("elixir_username"));
		model.setPassword(properties.getProperty("elixir_password"));
		model.setWorkspace(properties.getProperty("elixir_workspace"));
		model.setResources(properties.getProperty("elixir_resources"));
		model.setProviderUrl(properties.getProperty("elixir_provider_url"));
		
		return model;
	}
	
	public static String getReverseWorkDir(Properties properties) {
		String reverseDir = properties.getProperty("reverse_work_dir");
		
		if (reverseDir == null) {
			return "";
		}
		
		return reverseDir;
	}
	
	/*
	 * Get thousand separator
	 * Using default jvm locale
	 * 
	 * @return default thousand separator character
	 * 
	 */
	public static char getThousandSeparator(){
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
		return symbols.getGroupingSeparator();
	}
	
	/*
	 * Return thousand separtor
	 * 
	 * @param locale desired locale
	 * 
	 * @return thousand separator in locale <tt>locale</tt>
	 * 
	 */
	
	public static char getThousandSeparator(Locale locale){
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale);
		return symbols.getGroupingSeparator();
	}
	
	public static String getElixirHostname(Properties prop){
		String strHostname = prop.getProperty("elixir_hostname");
		
		return strHostname;
	}
	
	public static int getElixirPort(Properties prop){
		int port =  Integer.parseInt(prop.get("elixir_port").toString());
		
		return port;
	}
	
	public static String getElixirUsername(Properties prop){
		String username = prop.getProperty("elixir_username");
		
		return username;
	}
	
	public static String getElixirPassword(Properties prop){
		String password = prop.getProperty("elixir_password");
		
		return password;
	}
	
	public static String getElixirWorkspace(Properties prop){
		String workspace = prop.getProperty("elixir_workspace");
		
		return workspace;
	}
	
	public static String getElixirResources(Properties prop){
		String resources = prop.getProperty("elixir_resources");
		
		return resources;
	}
	
	public static String getElixirProviderURL(Properties prop){
		String strHostname = prop.getProperty("elixir_provider_url");
		
		return strHostname;
	}
	
	public static String getPeriodeBefore() {
		String result = "";
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		month++;
		String strMonth = "";
		if (month < 10) {
			strMonth = "0" + month;
		} else {
			strMonth = "" + month;
		}
		
		result = year + strMonth;
		
		return result;
	}
	
	public static int getErrt(List<Map<String, Object>> params){
		int errt = 300;
		for (Map<String, Object> map : params) {
			if( ((String)map.get("KEY")).equalsIgnoreCase("errt") )
				errt = Integer.parseInt((String)map.get("VALUE"));			
		}
		return errt;
	}
	
	public static int getErcp(List<Map<String, Object>> params){
		int ercp = 300;
		for (Map<String, Object> map : params) {
			if( ((String)map.get("KEY")).equalsIgnoreCase("ercp") )
				ercp = Integer.parseInt((String)map.get("VALUE"));			
		}
		return ercp;
	}
	
	public static String getCsvDelimiter(List<Map<String, Object>> params) {
		String separator = ",";
		
		for (Map<String, Object> map : params) {
			if (((String) map.get("KEY")).equalsIgnoreCase("mcdp")) {
				separator = (String)map.get("VALUE");
			}
		}
		
		return separator;
	}
	
	public static String getStrDate(Date date, String pattern, Locale local) {
		String strDate = "";
		SimpleDateFormat formatter = null;
		if (local == null) {
			formatter = new SimpleDateFormat(pattern);
		} else {
			formatter = new SimpleDateFormat(pattern, local);
		}
 
		strDate = formatter.format(date);
		return strDate;
    }
	
	public static String getMailFrom(Properties properties) {
		String mailFrom = properties.getProperty("mail_from");
		
		return mailFrom;
	}
	
	public static String normalizeCifKey(String cifKey){
		if( cifKey == null )
			return null;
		if( cifKey.length() > CIF_KEY_LENGTH )
			return cifKey.substring(cifKey.length()-CIF_KEY_LENGTH, CIF_KEY_LENGTH);
		
		String tCifKey = cifKey;
		for(int i=cifKey.length();i<CIF_KEY_LENGTH;i++)
			tCifKey = "0"+tCifKey;
		return tCifKey;
	}
	
	public static String normalizeAccNo(String accNo){
		if( accNo == null )
			return null;
		if( accNo.length() == 0 )
			return accNo;
		if( accNo.length() > ACC_NO_LENGTH )
			return accNo.substring(accNo.length()-ACC_NO_LENGTH, ACC_NO_LENGTH);
		
		String taccNo = accNo;
		for(int i=accNo.length();i<ACC_NO_LENGTH;i++)
			taccNo = "0"+taccNo;
		return taccNo;
	}
	
	public static Response responseException(Exception e){
		Response res = new Response();
		res.setResultCode(ResponseErrorCode.INTERNAL_SERVER_ERROR);
		res.setMessage(ResponseErrorCode.MSG_INTERNAL_SERVER_ERROR);
		List<String> errMsg = new ArrayList<String>();
		errMsg.add(ExceptionUtils.getStackTrace(e));
		res.setErrMsg(errMsg);
		return res;
	}
}
