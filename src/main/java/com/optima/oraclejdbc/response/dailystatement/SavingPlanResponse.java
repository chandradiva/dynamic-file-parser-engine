package com.optima.oraclejdbc.response.dailystatement;


import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailsavingsplan.SPDetailInfo_type0;
import com.optima.oraclejdbc.model.MappingProduct;

public class SavingPlanResponse implements Serializable{

	private static final long serialVersionUID = 2195045121306143949L;
	private MappingProduct product;
	private String currency;
	private String accountNo;
	private Double balance;
	private Double targetAmount;
	private Double paymentAmount;
	@JsonFormat(pattern="dd/MM/yyyy", timezone="Asia/Jakarta")
	private Date dueDate;
	@JsonFormat(pattern="dd/MM/yyyy", timezone="Asia/Jakarta")
	private Date effectiveDate;
	private Byte tenorMonth;
	private Double interest;
	
	public SavingPlanResponse(SPDetailInfo_type0 detailInfo) {
		currency = detailInfo.getCurrencyType();
		accountNo = detailInfo.getAccountNo1();
		balance = detailInfo.getCurrentBalance().doubleValue();
		targetAmount = detailInfo.getTargetBalance().doubleValue();
		paymentAmount = detailInfo.getInstallmentAmount().doubleValue();
		dueDate = detailInfo.getMaturityDate();
		effectiveDate = detailInfo.getEffectiveDate();
		tenorMonth = detailInfo.getSPTenor();
		interest = detailInfo.getInterestRate().doubleValue()*100;
	}

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

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(Double targetAmount) {
		this.targetAmount = targetAmount;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Byte getTenorMonth() {
		return tenorMonth;
	}

	public void setTenorMonth(Byte tenorMonth) {
		this.tenorMonth = tenorMonth;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}
	
}
