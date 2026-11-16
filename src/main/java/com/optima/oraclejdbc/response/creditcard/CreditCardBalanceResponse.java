package com.optima.oraclejdbc.response.creditcard;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ocbc.schemas.soa.creditcard.service.wbst_inquiry_ccbalance.RESConsumerSequence_type1;
import com.optima.oraclejdbc.model.MappingProduct;

public class CreditCardBalanceResponse implements Serializable{
	private static final long serialVersionUID = 6213534129252069164L;
	private MappingProduct product;
	private String creditCardNumber;
	private Double available;
	private Double creditLimit;
	private Double outstandingBalance;
	private Double lastBilled;
	private Double minPayment;
	@JsonFormat(pattern="dd/MM/yyyy", timezone="Asia/Jakarta")
	private Date dueDate;
	
	private Double pointsBalance;
	@JsonFormat(pattern="dd/MM/yyyy", timezone="Asia/Jakarta")
	private Date expiryDate;
	
	public CreditCardBalanceResponse(RESConsumerSequence_type1 seq) {
		creditCardNumber = seq.getCreditCardBalances().getBalanceInquiryKey().getCreditCardNumber();
		available = seq.getCreditCardBalances().getCreditCardBalanceDetails().getAvailableCredit().doubleValue();
		creditLimit = seq.getCreditCardBalances().getCreditCardBalanceDetails().getCreditLimit().doubleValue();
		outstandingBalance = seq.getCreditCardBalances().getCreditCardBalanceDetails().getCurrentBalance().doubleValue();
		lastBilled = seq.getCreditCardBalances().getCreditCardBalanceDetails().getOutstandingBalance().doubleValue();
		minPayment = seq.getCreditCardBalances().getCreditCardBalanceDetails().getMinimumPayment().doubleValue();
		dueDate = seq.getCreditCardBalances().getCreditCardBalanceDetails().getDueDate();
	}
	public CreditCardBalanceResponse(){}
	public MappingProduct getProduct() {
		return product;
	}
	public void setProduct(MappingProduct product) {
		this.product = product;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public Double getAvailable() {
		return available;
	}
	public void setAvailable(Double available) {
		this.available = available;
	}
	public Double getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}
	public Double getOutstandingBalance() {
		return outstandingBalance;
	}
	public void setOutstandingBalance(Double outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}
	public Double getLastBilled() {
		return lastBilled;
	}
	public void setLastBilled(Double lastBilled) {
		this.lastBilled = lastBilled;
	}
	public Double getMinPayment() {
		return minPayment;
	}
	public void setMinPayment(Double minPayment) {
		this.minPayment = minPayment;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Double getPointsBalance() {
		return pointsBalance;
	}
	public void setPointsBalance(Double pointsBalance) {
		this.pointsBalance = pointsBalance;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}	
}
