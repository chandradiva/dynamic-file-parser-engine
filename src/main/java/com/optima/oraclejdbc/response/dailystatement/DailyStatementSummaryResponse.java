package com.optima.oraclejdbc.response.dailystatement;

import java.io.Serializable;

import com.optima.oraclejdbc.model.MappingProduct;

public class DailyStatementSummaryResponse implements Serializable{

	private static final long serialVersionUID = 6954033283975204431L;
	private MappingProduct product;
	private String currency;
	private String accountNumber;
	private Double balance;
	private Double available;
	private Double hold;
	private Double overdraft;
	public MappingProduct getProduct() {
		return product;
	}
	public void setProduct(MappingProduct product) {
		this.product = product;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getAvailable() {
		return available;
	}
	public void setAvailable(Double available) {
		this.available = available;
	}
	public Double getHold() {
		return hold;
	}
	public void setHold(Double hold) {
		this.hold = hold;
	}
	public Double getOverdraft() {
		return overdraft;
	}
	public void setOverdraft(Double overdraft) {
		this.overdraft = overdraft;
	}
	
}
