package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;

@NISPEntity(tableName = "UNIT_TRUST_TEMP")
public class UnitTrustTemp implements Serializable {

	private static final long serialVersionUID = -7993951444612150580L;

	@NISPEntity(columnName = "ID")
	private Long id;
	
	@NISPEntity(columnName = "RECORD_ID")
	private String recordId;
	
	@NISPEntity(columnName = "ACCOUNT_NO")
	private String accountNo;
	
	@NISPEntity(columnName = "CURRENCY_CODE")
	private String currencyCode;
	
	@NISPEntity(columnName = "TRANSACTION_DATE")
	private Date transactionDate;
	
	@NISPEntity(columnName = "EFFECTIVE_DATE")
	private Date effectiveDate;
	
	@NISPEntity(columnName = "REFERENCE_NO")
	private String referenceNo;
	
	@NISPEntity(columnName = "TRANSACTION_TYPE")
	private String transactionType;
	
	@NISPEntity(columnName = "TRANSACTION_TYPE_STR")
	private String transactionTypeStr;
	
	@NISPEntity(columnName = "AMOUNT")
	private Double amount;
	
	@NISPEntity(columnName = "NAV")
	private Double nav;
	
	@NISPEntity(columnName = "FEE_AMOUNT")
	private Double feeAmount;
	
	@NISPEntity(columnName = "UNIT")
	private Double unit;
	
	@NISPEntity(columnName = "STATUS")
	private String status;
	
	@NISPEntity(columnName = "STATUS_STR")
	private String statusStr;
	
	@NISPEntity(columnName = "TRANSACTION_SOURCE")
	private String transactionSource;
	
	@NISPEntity(columnName = "PERIODE")
	private String periode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionTypeStr() {
		return transactionTypeStr;
	}

	public void setTransactionTypeStr(String transactionTypeStr) {
		this.transactionTypeStr = transactionTypeStr;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getNav() {
		return nav;
	}

	public void setNav(Double nav) {
		this.nav = nav;
	}

	public Double getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(Double feeAmount) {
		this.feeAmount = feeAmount;
	}

	public Double getUnit() {
		return unit;
	}

	public void setUnit(Double unit) {
		this.unit = unit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getTransactionSource() {
		return transactionSource;
	}

	public void setTransactionSource(String transactionSource) {
		this.transactionSource = transactionSource;
	}

	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
	}
}
