package com.optima.oraclejdbc.response.loan;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailloan.LoanDetailInfo_type0;
import com.optima.oraclejdbc.model.MappingProduct;

public class LoanResponse implements Serializable{

	private static final long serialVersionUID = -4988858603026394661L;
	private MappingProduct product;
	private String currency;
	private String accountNo;
	private Double outstandingAmount;
	private Double monthlyPayment;
	@JsonFormat(pattern="dd/MM/yyyy", timezone="Asia/Jakarta")
	private Date dueDate;
	private Double overdueAmount;
	
	public LoanResponse(LoanDetailInfo_type0 loanDetail) {
		currency = loanDetail.getCurrencyType();
		accountNo = loanDetail.getAccountNo1();
		outstandingAmount = loanDetail.getOutstandingAmount().doubleValue();
		monthlyPayment = loanDetail.getMonthlyPayment().doubleValue();
		dueDate = loanDetail.getDueDate();
		overdueAmount = loanDetail.getOverdueAmount().doubleValue();
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

	public Double getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(Double outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public Double getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(Double monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Double getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(Double overdueAmount) {
		this.overdueAmount = overdueAmount;
	}
	
}
