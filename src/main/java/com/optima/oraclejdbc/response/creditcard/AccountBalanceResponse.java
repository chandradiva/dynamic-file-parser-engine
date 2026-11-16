package com.optima.oraclejdbc.response.creditcard;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntitySerialize;

public class AccountBalanceResponse implements Serializable{

	private static final long serialVersionUID = 7894959384942950522L;

	@NISPEntitySerialize(field="OPENING_BALANCE_SIGN")
	private Character balanceBeforeSign;
	
	@NISPEntitySerialize(field="OPEN_BALANCE_BEFORE")
	private String balanceBefore;
	
	@NISPEntitySerialize(field="RETAIL_TRANSACTION_SIGN")
	private Character retailTransactionSign;
	
	@NISPEntitySerialize(field="RETAIL_TRANSACTION_AMOUNT")
	private String retailTransaction;
	
	@NISPEntitySerialize(field="INTEREST_CHARGES_SIGN")
	private Character interestSign;
	
	@NISPEntitySerialize(field="INTEREST_CHARGES_AMOUNT")
	private String interestAmount;
	
	@NISPEntitySerialize(field="PAYMENT_SIGN")
	private Character paymentSign;
	
	@NISPEntitySerialize(field="PAYMENT_AMOUNT")
	private String payment;
	
	@NISPEntitySerialize(field="TOTAL_SIGN")
	private Character totalSign;
	
	@NISPEntitySerialize(field="TOTAL")
	private String total;

	public Character getBalanceBeforeSign() {
		return balanceBeforeSign;
	}

	public void setBalanceBeforeSign(Character balanceBeforeSign) {
		this.balanceBeforeSign = balanceBeforeSign;
	}

	public String getBalanceBefore() {
		return balanceBefore;
	}

	public void setBalanceBefore(String balanceBefore) {
		this.balanceBefore = balanceBefore;
	}

	public Character getRetailTransactionSign() {
		return retailTransactionSign;
	}

	public void setRetailTransactionSign(Character retailTransactionSign) {
		this.retailTransactionSign = retailTransactionSign;
	}

	public String getRetailTransaction() {
		return retailTransaction;
	}

	public void setRetailTransaction(String retailTransaction) {
		this.retailTransaction = retailTransaction;
	}

	public Character getInterestSign() {
		return interestSign;
	}

	public void setInterestSign(Character interestSign) {
		this.interestSign = interestSign;
	}

	public String getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(String interestAmount) {
		this.interestAmount = interestAmount;
	}

	public Character getPaymentSign() {
		return paymentSign;
	}

	public void setPaymentSign(Character paymentSign) {
		this.paymentSign = paymentSign;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public Character getTotalSign() {
		return totalSign;
	}

	public void setTotalSign(Character totalSign) {
		this.totalSign = totalSign;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	
}
