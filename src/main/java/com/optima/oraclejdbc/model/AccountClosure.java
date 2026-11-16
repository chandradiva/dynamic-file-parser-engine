package com.optima.oraclejdbc.model;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName="ACCOUNT_CLOSURE")
public class AccountClosure implements Serializable{

	private static final long serialVersionUID = -7444175569153415858L;

	@Position(start = 0, length = 30)
	@NISPEntityId
	@NISPEntity(columnName="BV_ORG_ID")
	private String bvOrgId;
	
	@Position(start = 30, length = 19)
	@NISPEntity(columnName="ACCOUNT_NUMBER")
	private String accountNumber;
	
	@Position(start = 49, length = 1)
	@NISPEntity(columnName="ACCOUNT_TYPE")
	private String accountType;
	
	@Position(start = 50, length = 3)
	@NISPEntity(columnName="CURRENCY_CODE")
	private String currencyCode;

	public String getBvOrgId() {
		return bvOrgId;
	}

	public void setBvOrgId(String bvOrgId) {
		this.bvOrgId = bvOrgId;
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

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
