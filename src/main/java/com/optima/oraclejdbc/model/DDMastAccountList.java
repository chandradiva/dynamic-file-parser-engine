package com.optima.oraclejdbc.model;

import java.io.Serializable;

import com.optima.oraclejdbc.utils.CommonUtils;

public class DDMastAccountList implements Serializable {

	private static final long serialVersionUID = -66330691735647560L;
	private String cifKey;
	private String accountNumber;
	public String getCifKey() {
		return cifKey;
	}
	public void setCifKey(String cifKey) {
		this.cifKey = CommonUtils.normalizeCifKey(cifKey);
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	
}
