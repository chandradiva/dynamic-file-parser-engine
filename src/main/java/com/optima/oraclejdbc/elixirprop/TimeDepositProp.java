package com.optima.oraclejdbc.elixirprop;

import java.util.Properties;

import com.optima.oraclejdbc.response.dailystatement.TDAResponse;

public class TimeDepositProp {

	public static Properties tdaProp(TDAResponse tda) {
		Properties prop = new Properties();
		prop.put("ACCOUNT_NO", tda.getAccountNo());
		prop.put("TDA_DATE", tda.getTdaDate());
		prop.put("TDA_NAME", tda.getTdaName());
		prop.put("TDA_LOCATION", tda.getTdaLocation());
		prop.put("TDA_ADDRESS", tda.getTdaAddress());
		prop.put("TDA_TENOR", tda.getTenor());
		prop.put("VALUTA_DATE", tda.getValutaDate());
		prop.put("MATURITY_DATE", tda.getMaturityDate());
		prop.put("CURRENCY", tda.getCurrency());
		prop.put("PRINCIPAL_AMOUNT", tda.getPrincipalAmount());
		prop.put("INTEREST_RATE", tda.getInterestAmount());
		prop.put("WORDS", tda.getWords());
		prop.put("MATURITY", tda.getMaturity());
		prop.put("INTEREST_AMOUNT", tda.getInterestAmount());
		prop.put("TAXES", tda.getTaxes());
		prop.put("NET_INTEREST", tda.getNetInterest());
//		prop.put("LOGO", elResources + "/img/" + tda.getLogo());
		
		return prop;
	}
}
