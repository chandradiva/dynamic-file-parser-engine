package com.optima.oraclejdbc.cons;

import java.util.LinkedHashMap;
import java.util.Map;

public class ErrorCodeCons {

	public static Map<String, String> ERROR_CODE_ENG = new LinkedHashMap<String, String>();
	
	static {
		
		ERROR_CODE_ENG.put("00000001", "Internal System Error"); // no such method exception
		ERROR_CODE_ENG.put("00000002", "Invalid Number");
		ERROR_CODE_ENG.put("00000003", "Internal System Error"); // invocation target exception
		ERROR_CODE_ENG.put("00000004", "Internal System Error"); // illegal access exception
		ERROR_CODE_ENG.put("00000005", "Invalid Date");
		ERROR_CODE_ENG.put("00000006", "Internal System Error"); // instantiation exception
		ERROR_CODE_ENG.put("00000007", "Value Not Valid");
		ERROR_CODE_ENG.put("00000008", "Value Length Not Match");
	}
}
