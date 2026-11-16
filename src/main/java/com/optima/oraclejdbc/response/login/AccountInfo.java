package com.optima.oraclejdbc.response.login;

import java.io.Serializable;

import com.ocbc.schemas.soa.accounts.service.wbst_admin_loginchannelbanking.AccountsInfo_type0;
import com.ocbc.schemas.soa.emf.account.aggregates.AlternateAccountKeyDetail_type0;

public class AccountInfo implements Serializable {

	private static final long serialVersionUID = -8632049310434974412L;
	
	private String accountNumber;
	private String accountType;
	private String currencyType;
	private String branchCode;
	private String productCode;
	
	public AccountInfo(AlternateAccountKeyDetail_type0 data, AccountsInfo_type0 data2){
		accountNumber = data.getAccountNo1();
		accountType = data.getAccountType();
		currencyType = data.getCurrencyType();
		branchCode = data.getBranchCode();
		productCode = data2.getProductCode();
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
}
