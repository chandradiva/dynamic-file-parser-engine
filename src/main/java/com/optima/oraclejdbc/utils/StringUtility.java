package com.optima.oraclejdbc.utils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtility {

	public static String formatNumber(Double param) {
		String result = "";
		try {
			NumberFormat nf = NumberFormat.getInstance();
			result = nf.format(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static String checkStr(String param) {
		String result = "";
		
		try {
			if (param != null) {
				result = param;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static List<String> getAlamats(String alamat) {
		if(alamat!=null && !alamat.isEmpty()){
			String data = alamat;
			int div = data.length() / 40;
			int mod = data.length() % 40;
			
			if (div >= 4) {
				div = 4;
				mod = 0;
			}
			
			int i = 0;
			List<String> alamats = new ArrayList<String>();
			for (i = 0; i < div; i++) {
				int start = i * 40;
				int end = (i + 1) * 40;
				
				alamats.add(data.substring(start, end));
			}
			
			if (mod > 0) {
				alamats.add(data.substring(div * 40));
			}
			
			return alamats;
		}
		
		return Arrays.asList("");
	}
	
	public static String checkRemark(String param) {
		String result = "";
		
		try {
			if (param != null) {
				result = " - " + param;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static String checkTransCode(String param) {
		String result = "";
		
		try {
			if (param != null) {
				result = param + " - ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
