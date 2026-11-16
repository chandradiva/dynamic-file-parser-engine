package com.optima.oraclejdbc.elixirprop;

import java.util.Properties;

import com.optima.oraclejdbc.model.CCAccountBalance;
import com.optima.oraclejdbc.model.CCAccountHeader;
import com.optima.oraclejdbc.model.CCCustomerHeader;
import com.optima.oraclejdbc.model.CCRateInfo;
import com.optima.oraclejdbc.model.CCRewardSummary;
import com.optima.oraclejdbc.response.creditcard.AccountBalanceResponse;
import com.optima.oraclejdbc.response.creditcard.AccountHeaderResponse;
import com.optima.oraclejdbc.response.creditcard.CustomerHeaderResponse;
import com.optima.oraclejdbc.response.creditcard.RateInfoResponse;
import com.optima.oraclejdbc.response.creditcard.RewardSummaryResponse;
import com.optima.oraclejdbc.utils.DateUtility;
import com.optima.oraclejdbc.utils.StringUtility;

public class CreditCardProp {

	public static Properties getCCProp(String ccNumber, String ccName, String providerUrl) {
		Properties prop = new Properties();
		
		prop.put("P_CC_NUMBER", ccNumber);
		prop.put("CC_NAME", ccName);
		prop.put("PROVIDER_URL", providerUrl);
		
		return prop;
	}
	
	public static Properties getBillingStatementProp(CustomerHeaderResponse ccCh, AccountHeaderResponse ccAh, 
			AccountBalanceResponse ccAb, RewardSummaryResponse ccBs, RateInfoResponse ccRi, String logo, String message1, 
			String message2, String bankNotes, int isReprint, String kolek, String providerUrl) {
		
		Properties prop = new Properties();
		
		prop.put("P_AH_ID", String.valueOf(ccAh.getId()));
		prop.put("CH_FULLNAME", StringUtility.checkStr(ccCh.getFullname()));
		prop.put("CH_ADDRESS_1", StringUtility.checkStr(ccCh.getAddress1()));
		prop.put("CH_ADDRESS_2", StringUtility.checkStr(ccCh.getAddress2()));
		prop.put("CH_ADDRESS_3", StringUtility.checkStr(ccCh.getAddress3()));
		prop.put("CH_ADDRESS_4", StringUtility.checkStr(ccCh.getAddress4()));
		prop.put("CH_KODE_POS", StringUtility.checkStr(ccCh.getPostalCode()));
		prop.put("AH_NO_CUST", StringUtility.checkStr(ccAh.getCustomerNumber()));
		prop.put("AH_TGL_CETAK", StringUtility.checkStr(DateUtility.formatDate3(ccAh.getStatementDate())));
		prop.put("AH_TGL_TEMPO", StringUtility.checkStr(DateUtility.formatDate3(ccAh.getPaymentDueDate())));
		prop.put("AH_TAGIHAN_BULAN", StringUtility.checkStr(ccAh.getOutstandingBalance()));
		prop.put("AH_PEMBAYARAN_MINIMUM", StringUtility.checkStr(ccAh.getMinimumPayment()));
		prop.put("AH_LIMIT_GABUNGAN", StringUtility.checkStr(ccRi.getCreditLimit()));
		prop.put("AH_SISA_KREDIT", StringUtility.checkStr(ccRi.getAvailableLimit()));
		prop.put("AB_TAGIHAN_SEBELUM", StringUtility.checkStr(ccAb.getBalanceBefore()) + getSign(ccAb.getBalanceBeforeSign()));
		prop.put("AB_PEMBELANJAAN", StringUtility.checkStr(ccAb.getRetailTransaction()) + getSign(ccAb.getRetailTransactionSign()));
		prop.put("AB_BUNGA_BIAYA", StringUtility.checkStr(ccAb.getInterestAmount()) + getSign(ccAb.getInterestSign()));
		prop.put("AB_PEMBAYARAN", StringUtility.checkStr(ccAb.getPayment()) + getSign(ccAb.getPaymentSign()));
		prop.put("AB_TOTAL", StringUtility.checkStr(ccAb.getTotal()) + getSign(ccAb.getTotalSign()));
		prop.put("BS_NO_KARTU", StringUtility.checkStr(ccBs.getCardNumber()));
		prop.put("BS_POIN_BULAN", StringUtility.checkStr(ccBs.getThisMonthPoints()) + getSign(ccBs.getThisMonthSign()));
		prop.put("BS_POIN_SEBELUM", StringUtility.checkStr(ccBs.getTotalPreviousPoints()) + getSign(ccBs.getPreviousSign()));
		prop.put("BS_POIN_TERPAKAI", StringUtility.checkStr(ccBs.getUsedPoints()) + getSign(ccBs.getUsedSign()));
		prop.put("BS_TOTAL_POIN_1", StringUtility.checkStr(ccBs.getAvailable()) + getSign(ccBs.getAvailableSign()));
		prop.put("BS_TOTAL_POIN_2", StringUtility.checkStr(ccBs.getExtended()));
		prop.put("BS_KADALUWARSA_1", StringUtility.checkStr(DateUtility.formatDate3(ccBs.getAvailableExpire())));
		prop.put("BS_KADALUWARSA_2", StringUtility.checkStr(DateUtility.formatDate3(ccBs.getExtendedExpire())));
		prop.put("RI_BUNGA_TAHUN", StringUtility.checkStr(ccRi.getYearlyRetail()));
		prop.put("RI_BUNGA_BULAN", StringUtility.checkStr(ccRi.getMonthlyRetail()));
		prop.put("RI_TUNAI_TAHUN", StringUtility.checkStr(ccRi.getYearlyAdvanced()));
		prop.put("RI_TUNAI_BULAN", StringUtility.checkStr(ccRi.getMonthlyAdvanced()));
		prop.put("LOGO", logo);
		prop.put("KOLEK", kolek);
		prop.put("MESSAGE_1", message1);
		prop.put("MESSAGE_2", message2);
		prop.put("BANK_NOTES", bankNotes);
		prop.put("RE_PRINT", String.valueOf(isReprint));
		prop.put("PROVIDER_URL", providerUrl);
		
		return prop;
	}
	
	public static Properties getCCStatamentProp(CCCustomerHeader ch, CCAccountHeader ah, CCAccountBalance ab, 
			CCRewardSummary bs, CCRateInfo ri, String logo, String message1, String message2, String bankNotes, 
			int isReprint, String kolek, String providerUrl) {
		
		Properties prop = new Properties();
		
		prop.put("P_AH_ID", String.valueOf(ah.getId()));
		prop.put("CH_FULLNAME", StringUtility.checkStr(ch.getCustomerFullname()));
		prop.put("CH_ADDRESS_1", StringUtility.checkStr(ch.getMailingAddressLine1()));
		prop.put("CH_ADDRESS_2", StringUtility.checkStr(ch.getMailingAddressLine2()));
		prop.put("CH_ADDRESS_3", StringUtility.checkStr(ch.getMailingAddressLine3()));
		prop.put("CH_ADDRESS_4", StringUtility.checkStr(ch.getMailingAddressLine4()));
		prop.put("CH_KODE_POS", StringUtility.checkStr(ch.getMailingAddressPostalCode()));
		prop.put("AH_NO_CUST", StringUtility.checkStr(ah.getCardholderNumber()));
		prop.put("AH_TGL_CETAK", StringUtility.checkStr(DateUtility.formatDate3(ah.getStatementDate())));
		prop.put("AH_TGL_TEMPO", StringUtility.checkStr(DateUtility.formatDate3(ah.getPaymentDueDate())));
		prop.put("AH_TAGIHAN_BULAN", StringUtility.checkStr(ah.getTotalOutstandingBalance()));
		prop.put("AH_PEMBAYARAN_MINIMUM", StringUtility.checkStr(ah.getMinimumPayment()));
		prop.put("AH_LIMIT_GABUNGAN", StringUtility.checkStr(ah.getCreditLimit()));
		prop.put("AH_SISA_KREDIT", StringUtility.checkStr(ah.getAvailableLimit()));
		prop.put("AB_TAGIHAN_SEBELUM", StringUtility.checkStr(ab.getOpenBalanceBefore()) + getSign(ab.getOpeningBalanceSign()));
		prop.put("AB_PEMBELANJAAN", StringUtility.checkStr(ab.getRetailTransactionAmount()) + getSign(ab.getRetailTransactionSign()));
		prop.put("AB_BUNGA_BIAYA", StringUtility.checkStr(ab.getInterestChargesAmount()) + getSign(ab.getInterestChargesSign()));
		prop.put("AB_PEMBAYARAN", StringUtility.checkStr(ab.getPaymentAmount()) + getSign(ab.getPaymentSign()));
		prop.put("AB_TOTAL", StringUtility.checkStr(ab.getTotal()) + getSign(ab.getTotalSign()));
		prop.put("BS_NO_KARTU", StringUtility.checkStr(bs.getCardHolderNo()));
		prop.put("BS_POIN_BULAN", StringUtility.checkStr(bs.getEarnedThisMonth()) + getSign(bs.getEarnedThisMonthSign()));
		prop.put("BS_POIN_SEBELUM", StringUtility.checkStr(bs.getPreviousMonth()) + getSign(bs.getPreviousMonthSign()));
		prop.put("BS_POIN_TERPAKAI", StringUtility.checkStr(bs.getUsed()) + getSign(bs.getUsedSign()));
		prop.put("BS_TOTAL_POIN_1", StringUtility.checkStr(bs.getAvailable()) + getSign(bs.getAvailableSign()));
		prop.put("BS_TOTAL_POIN_2", StringUtility.checkStr(bs.getExtended()));
		prop.put("BS_KADALUWARSA_1", StringUtility.checkStr(DateUtility.formatDate3(bs.getAvailableExpireDate())));
		prop.put("BS_KADALUWARSA_2", StringUtility.checkStr(DateUtility.formatDate3(bs.getExtendedExpireDate())));
		prop.put("RI_BUNGA_TAHUN", StringUtility.checkStr(ri.getYearlyRetailPercentage()));
		prop.put("RI_BUNGA_BULAN", StringUtility.checkStr(ri.getMonthlyRetailPercentage()));
		prop.put("RI_TUNAI_TAHUN", StringUtility.checkStr(ri.getYearlyCashAdvance()));
		prop.put("RI_TUNAI_BULAN", StringUtility.checkStr(ri.getMonthlyCashAdvance()));
		prop.put("LOGO", logo);
		prop.put("KOLEK", kolek);
		prop.put("MESSAGE_1", message1);
		prop.put("MESSAGE_2", message2);
		prop.put("BANK_NOTES", bankNotes);
		prop.put("RE_PRINT", String.valueOf(isReprint));
		prop.put("PROVIDER_URL", providerUrl);
		
		return prop;
	}
	
	private static String getSign(Character param) {
		String sign = "";
		if (param == '-' && !param.equals(" ")) {
			sign = " CR";
		}
		
		return sign;
	}
	
	private static String getSign(String param) {
		String sign = "";
		if (param.equals("-") && !param.equals(" ")) {
			sign = " CR";
		}
		
		return sign;
	}
}
