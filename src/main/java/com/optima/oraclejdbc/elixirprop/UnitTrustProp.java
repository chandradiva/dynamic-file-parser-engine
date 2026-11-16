package com.optima.oraclejdbc.elixirprop;

import java.util.Properties;

import com.optima.oraclejdbc.model.AccountList;
import com.optima.oraclejdbc.model.UserInfo;
import com.optima.oraclejdbc.requestinput.ByDateHistoryInput;
import com.optima.oraclejdbc.requestinput.ByRecentTransactionsInput;
import com.optima.oraclejdbc.utils.StringUtility;

public class UnitTrustProp {

	public static Properties getUTStatementHistoryProp(ByDateHistoryInput input, UserInfo info, AccountList acc, 
			String productName, String providerUrl) {
		
		Properties prop = new Properties();
		
		prop.put("P_ACCOUNT_NO", input.getAccountNumber());
		prop.put("UT_CURRENCY", input.getCurrency());
		prop.put("PROVIDER_URL", providerUrl);
		if (acc != null) {
			prop.put("UT_NAME", StringUtility.checkStr(acc.getProduct().getProductName()));
		} else {
			prop.put("UT_NAME", productName);
		}
		
		return prop;
	}
	
	public static Properties getUTStatementRecentProp(ByRecentTransactionsInput input, UserInfo info, AccountList acc, 
			String productName, String providerUrl) {
		
		Properties prop = new Properties();
		
		prop.put("P_ACCOUNT_NO", input.getAccountNumber());
		prop.put("UT_CURRENCY", input.getCurrency());
		prop.put("PROVIDER_URL", providerUrl);
		if (acc != null) {
			prop.put("UT_NAME", StringUtility.checkStr(acc.getProduct().getProductName()));
		} else {
			prop.put("UT_NAME", productName);
		}
		
		return prop;
	}
}
