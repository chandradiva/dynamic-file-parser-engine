package com.optima.oraclejdbc.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.optima.oraclejdbc.model.backoffice.BOCCRegex;

public class CCRegexUtils {

	public static BOCCRegex validateRegex(List<BOCCRegex> regexs, String cardNumber) {
		BOCCRegex ccRegex =  null;
		
		Pattern pattern;
		Matcher matcher;
		
		try {
			for (BOCCRegex item : regexs) {
				pattern = Pattern.compile(item.getRegex());
				matcher = pattern.matcher(cardNumber);
				
				if (matcher.matches()) {
					ccRegex = item;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ccRegex;
	}
	
	public static BOCCRegex validatePrefix(List<BOCCRegex> regexs, String cardNumber) {
		BOCCRegex ccRegex =  null;
		
		try {
			for (BOCCRegex item : regexs) {
				if (item.getStartsWith() != null) {
					if (cardNumber.startsWith(item.getStartsWith())) {
						ccRegex = item;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ccRegex;
	}
}
