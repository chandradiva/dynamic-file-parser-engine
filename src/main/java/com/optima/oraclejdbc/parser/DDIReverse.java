package com.optima.oraclejdbc.parser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.model.estatement.DDIModel;
import com.optima.oraclejdbc.model.estatement.ESGiroHistory;
import com.optima.oraclejdbc.model.estatement.ESMCBilingualHistory;
import com.optima.oraclejdbc.model.estatement.ESMultiCurrencyHistory;

@Service
public class DDIReverse extends NISPReverse<DDIModel>{

	@Override
	public List<String> reverse(List<DDIModel> datas, String filename) {
		List<String> rd = new ArrayList<String>();
		
		try {
			for (DDIModel item : datas) {
				String ddi = "";
				
				if (item instanceof ESGiroHistory) {
					ddi = reverse(item, ESGiroHistory.class);
				} else if (item instanceof ESMultiCurrencyHistory) {
					ddi = reverse(item, ESMultiCurrencyHistory.class);
				} else if (item instanceof ESMCBilingualHistory) {
					ddi = reverse(item, ESMCBilingualHistory.class);
				}
				
				rd.add(ddi);
			}
			
			return rd;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<String> reverseObj(Object[] datas, List<String> alamats, String filename) {
		List<String> rd = new ArrayList<String>();
		
		try {
			for (int i = 0; i < datas.length; i++) {
				String ddi = "";
				
				if (datas[i] instanceof ESGiroHistory) {
					((ESGiroHistory) datas[i]).setAlamat1(alamats.get(0));
					((ESGiroHistory) datas[i]).setAlamat2(alamats.get(1));
					((ESGiroHistory) datas[i]).setAlamat3(alamats.get(2));
					((ESGiroHistory) datas[i]).setAlamat4(alamats.get(3));
					
					ddi = reverseObj(datas[i], ESGiroHistory.class);
				} else if (datas[i] instanceof ESMultiCurrencyHistory) {
					String accountNumber = ((ESMultiCurrencyHistory) datas[i]).getAccountNumber();
					String currency = ((ESMultiCurrencyHistory) datas[i]).getCurrencyType();
					String code = this.getCurrencyCode(currency);
					
					((ESMultiCurrencyHistory) datas[i]).setAccountNumber(code + accountNumber);
					
					((ESMultiCurrencyHistory) datas[i]).setAlamat1(alamats.get(0));
					((ESMultiCurrencyHistory) datas[i]).setAlamat2(alamats.get(1));
					((ESMultiCurrencyHistory) datas[i]).setAlamat3(alamats.get(2));
					((ESMultiCurrencyHistory) datas[i]).setAlamat4(alamats.get(3));
					
					ddi = reverseObj(datas[i], ESMultiCurrencyHistory.class);
				} else if (datas[i] instanceof ESMCBilingualHistory) {
					((ESMCBilingualHistory) datas[i]).setAlamat1(alamats.get(0));
					((ESMCBilingualHistory) datas[i]).setAlamat2(alamats.get(1));
					((ESMCBilingualHistory) datas[i]).setAlamat3(alamats.get(2));
					((ESMCBilingualHistory) datas[i]).setAlamat4(alamats.get(3));
					
					ddi = reverseObj(datas[i], ESMCBilingualHistory.class);
				}
				
				rd.add(ddi);
			}
			
			return rd;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String getCurrencyCode(String currency) {
		String code = "";
		
		if (currency.toLowerCase().equals("usd")) {
			code = "1";
		} else if (currency.toLowerCase().equals("sgd")) {
			code = "2";
		} else if (currency.toLowerCase().equals("jpy")) {
			code = "3";
		} else if (currency.toLowerCase().equals("eur")) {
			code = "4";
		} else if (currency.toLowerCase().equals("aud")) {
			code = "5";
		} else if (currency.toLowerCase().equals("hkd")) {
			code = "6";
		} else if (currency.toLowerCase().equals("gbp")) {
			code = "7";
		} else if (currency.toLowerCase().equals("cad")) {
			code = "8";
		} else if (currency.toLowerCase().equals("chf")) {
			code = "9";
		} else if (currency.toLowerCase().equals("nzd")) {
			code = "10";
		}
		
		return code;
	}
}
