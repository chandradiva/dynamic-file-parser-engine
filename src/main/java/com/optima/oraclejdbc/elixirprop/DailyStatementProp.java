package com.optima.oraclejdbc.elixirprop;

import java.util.Date;
import java.util.Properties;

import com.optima.oraclejdbc.model.AccountList;
import com.optima.oraclejdbc.model.UserInfo;
import com.optima.oraclejdbc.requestinput.ByDateHistoryInput;
import com.optima.oraclejdbc.requestinput.ByRecentTransactionsInput;
import com.optima.oraclejdbc.utils.DateUtility;

public class DailyStatementProp {

	public static Properties byLastTrans(ByRecentTransactionsInput input, UserInfo userInfo, Date tglCetak, AccountList acc) {
		String accNo = input.getAccountNumber().substring(input.getAccountNumber().toString().length() - 4);
		
		int count = Integer.parseInt(input.getCountLastTransactions());
		
		return generateDataProperties(input.getAccountNumber(),
				acc.getBranchCode(),
				"OCBC NISP",
				acc.getProduct().getProductName(),
				input.getCurrency(),
				"Last " + count + " Transactions",
				DateUtility.formatDate(tglCetak),
				userInfo.getFullname(),
				"",
				"",
				"");
	}
	
	public static Properties byDateRange(ByDateHistoryInput input, UserInfo userInfo, Date tglCetak, Date startDate, 
			Date endDate, AccountList acc) {
		
		return generateDataProperties(input.getAccountNumber(),
		acc.getBranchCode(),
		"OCBC NISP",
		acc.getProduct().getProductName(),
		input.getCurrency(),
		DateUtility.formatDatePeriode(startDate, endDate),
		DateUtility.formatDate(tglCetak),
		userInfo.getFullname(),
		"",
		"",
		"");				
	}
	
	private static Properties generateDataProperties(String accountNumber, String kodeCabang, String namaCabang,
			String productName, String currency, String periode, String tanggalCetak, String fullname,
			String alamat1, String alamat2, String alamat3){
		String accNo = accountNumber.substring(accountNumber.toString().length() - 4);

		Properties prop = new Properties();
		prop.put("P_ACCOUNT_NO", accountNumber);
		prop.put("KODE_CABANG", kodeCabang);
		prop.put("NAMA_CABANG", "OCBC NISP");
		prop.put("NOMOR_REKENING", accountNumber);
		prop.put("PRODUCT_NAME", productName);
		prop.put("CURRENCY", currency);
		prop.put("PERIODE", periode);
		prop.put("TANGGAL_CETAK", tanggalCetak);
		prop.put("CIF_NAME", fullname);
		prop.put("ALAMAT_1", alamat1);
		prop.put("ALAMAT_2", alamat2);
		prop.put("ALAMAT_3", alamat3);
		prop.put("HD_1", kodeCabang);
		prop.put("HD_2", accNo);
		
		return prop;
	}
	
	public static Properties getMutasiHistoryProperties(String accountNumber, String currency, UserInfo userInfo, 
			Date startDate, Date endDate, String productName, String searchBy, String providerUrl) {
		Properties prop = new Properties();
		prop.put("P_ACCOUNT_NO", accountNumber);
		prop.put("PRODUCT_NAME", productName);
		prop.put("CURRENCY", currency);
		prop.put("PERIODE", DateUtility.formatDatePeriode(startDate, endDate));
		prop.put("SEARCH_BY", searchBy==null?"":searchBy);
		prop.put("PROVIDER_URL", providerUrl);
		
		return prop;
	}
	
	public static Properties getMutasiRecentProperties(String accountNumber, String currency, int count, UserInfo userInfo, 
			String productName, String searchBy, String providerUrl) {
		Properties prop = new Properties();
		prop.put("P_ACCOUNT_NO", accountNumber);
		prop.put("PRODUCT_NAME", productName);
		prop.put("CURRENCY", currency);
		prop.put("PERIODE", "Last " + count + " Transactions");
		prop.put("SEARCH_BY", searchBy==null?"":searchBy);
		prop.put("PROVIDER_URL", providerUrl);
		
		return prop;
	}
}
