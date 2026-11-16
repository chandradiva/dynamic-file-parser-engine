package com.optima.oraclejdbc.response.creditcard;

import java.io.Serializable;
import java.util.ArrayList;

public class TransactionDetailResponse implements Serializable{

	private static final long serialVersionUID = -8386443881800415213L;
	private String fullname;
	private String customerNumber;
	
	private String lastMonthLabel;
	private Character lastMonthSign;
	private String lastMonthBalance;
	
	private ArrayList<RowTransactionDetailResponse> transactionDetails;
	
	private String subTotalLabel;
	private Character subTotalSign;
	private String subTotal;
	
	private String totalLabel;
	private Character totalSign;
	private String total;
	
	private String sign;
	
	private String currency;

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}


	public ArrayList<RowTransactionDetailResponse> getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(ArrayList<RowTransactionDetailResponse> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	public String getLastMonthBalance() {
		return lastMonthBalance;
	}

	public void setLastMonthBalance(String lastMonthBalance) {
		this.lastMonthBalance = lastMonthBalance;
	}

	public String getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getLastMonthLabel() {
		return lastMonthLabel;
	}

	public void setLastMonthLabel(String lastMonthLabel) {
		this.lastMonthLabel = lastMonthLabel;
	}

	public Character getLastMonthSign() {
		return lastMonthSign;
	}

	public void setLastMonthSign(Character lastMonthSign) {
		this.lastMonthSign = lastMonthSign;
	}

	public String getSubTotalLabel() {
		return subTotalLabel;
	}

	public void setSubTotalLabel(String subTotalLabel) {
		this.subTotalLabel = subTotalLabel;
	}

	public Character getSubTotalSign() {
		return subTotalSign;
	}

	public void setSubTotalSign(Character subTotalSign) {
		this.subTotalSign = subTotalSign;
	}

	public String getTotalLabel() {
		return totalLabel;
	}

	public void setTotalLabel(String totalLabel) {
		this.totalLabel = totalLabel;
	}

	public Character getTotalSign() {
		return totalSign;
	}

	public void setTotalSign(Character totalSign) {
		this.totalSign = totalSign;
	}
	
	
}
