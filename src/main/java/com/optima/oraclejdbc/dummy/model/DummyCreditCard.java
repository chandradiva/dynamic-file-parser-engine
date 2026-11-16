package com.optima.oraclejdbc.dummy.model;

public class DummyCreditCard {

	private String productName;
	private String currency;
	private String accountNo;
	private String points;
	private String expireDate;
	private String availableLimit;
	private String creditLimit;
	private String outstandingBalance;
	private String lastBilled;
	private String minimumPayment;
	private String dueDate;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public String getAvailableLimit() {
		return availableLimit;
	}
	public void setAvailableLimit(String availableLimit) {
		this.availableLimit = availableLimit;
	}
	public String getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}
	public String getOutstandingBalance() {
		return outstandingBalance;
	}
	public void setOutstandingBalance(String outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}
	public String getLastBilled() {
		return lastBilled;
	}
	public void setLastBilled(String lastBilled) {
		this.lastBilled = lastBilled;
	}
	public String getMinimumPayment() {
		return minimumPayment;
	}
	public void setMinimumPayment(String minimumPayment) {
		this.minimumPayment = minimumPayment;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
}
