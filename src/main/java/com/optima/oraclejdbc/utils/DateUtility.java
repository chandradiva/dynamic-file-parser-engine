package com.optima.oraclejdbc.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {

	/** 
	 * Format Date: yyyyMMdd
	 */
	public static Date parseDate(String param) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date result = null;
		try {
			result = df.parse(param);
			
			return result;
		} catch (Exception e) {
			return null;
		}
	}
	
	/** 
	 * Format Date: yyyy-MM-dd
	 */
	public static Date parseDate2(String param) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date result = null;
		try {
			result = df.parse(param);
			
			return result;
		} catch (Exception e) {
			return null;
		}
	}
	
	/** 
	 * Format Date: dd/MM/yyyy
	 */
	public static Date parseDate3(String param) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date result = null;
		try {
			result = df.parse(param);
			
			return result;
		} catch (Exception e) {
			return null;
		}
	}
	
	/** 
	 * Format Date: dd/MM/yyyy
	 */
	public static String formatDate(Date param) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String result = "";
		
		try {
			result = df.format(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static Date parse(String strDate, String strDateFormat){
		SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
		Date result = null;
		try{
			result = sdf.parse(strDate);
		}catch(ParseException e){
			result = null;
		}
		
		return result;
	}
	
	/** 
	 * Format Date: dd MMMM yyyy - dd MMMM yyyy
	 */
	public static String formatDatePeriode(Date param, Date param2) {
		DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
		String result = "";
		
		try {
			String str1 = df.format(param);
			String str2 = df.format(param2);
			result = str1 + " - " + str2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/** 
	 * Format Date: dd MMMM yyyy
	 */
	public static String formatDate2(Date param) {
		DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
		String result = "";
		
		try {
			result = df.format(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/** 
	 * Format Date: dd-MM-yyyy
	 */
	public static String formatDate3(Date param) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String result = "";
		
		try {
			if (param != null) {
				result = df.format(param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/** 
	 * Format Date: ddMMMyyyy
	 */
	public static String formatDate4(Date param) {
		DateFormat df = new SimpleDateFormat("ddMMMyyyy");
		String result = "";
		
		try {
			if (param != null) {
				result = df.format(param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/** 
	 * Format Date: ddMMyy
	 */
	public static String formatDate5(Date param) {
		DateFormat df = new SimpleDateFormat("ddMMyy");
		String result = "";
		
		try {
			result = df.format(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/** 
	 * Format Date: ddMMyy
	 */
	public static String formatDate6(Date param) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String result = "";
		
		try {
			result = df.format(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/** 
	 * Format Date: dd/MM/yyyy HH:mm:ss
	 */
	public static String formatDate7(Date param) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String result = "";
		
		try {
			result = df.format(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
