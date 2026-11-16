package com.optima.oraclejdbc.response.unitrust;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ocbc.schemas.soa.emf.investment.aggregates.UTTransactionDetails_type0;
import com.optima.oraclejdbc.annotations.NISPEntitySerialize;
import com.optima.oraclejdbc.jsonserializer.DoubleSerializer;

public class UniTrustTrxHistoryResponse implements Serializable{

	private static final long serialVersionUID = 8404784562377573802L;

	@NISPEntitySerialize(field="REFERENCE_NO")
	private String referenceNo;
	
	@JsonFormat(pattern="dd/MM/yyyy", timezone="Asia/Jakarta")
	@NISPEntitySerialize(field="TRANSACTION_DATE")
	private Date transactionDate;
	
	@JsonFormat(pattern="dd/MM/yyyy", timezone="Asia/Jakarta")
	@NISPEntitySerialize(field="EFFECTIVE_DATE")
	private Date effectiveDate;
	
	@NISPEntitySerialize(field="TRANSACTION_TYPE")
	private Character transactionType;
	
	@NISPEntitySerialize(field="CURRENCY_CODE")
	private String currencyCode;
	
	@NISPEntitySerialize(field="AMOUNT")
	@JsonSerialize(using=DoubleSerializer.class)
	private Double amount;
	
	@NISPEntitySerialize(field="NAV")
	@JsonSerialize(using=DoubleSerializer.class)
	private Double nav;
	
	@NISPEntitySerialize(field="FEE_AMOUNT")
	@JsonSerialize(using=DoubleSerializer.class)
	private Double feeAmount;
	
	@NISPEntitySerialize(field="UNIT")
	@JsonSerialize(using=DoubleSerializer.class)
	private Double unit;
	
	@NISPEntitySerialize(field="STATUS")
	private Character status;
	
	@NISPEntitySerialize(field="TRANSACTION_SOURCE")
	private String transactionSource;
	
	public UniTrustTrxHistoryResponse(){}
	public UniTrustTrxHistoryResponse(UTTransactionDetails_type0 detail) {
		referenceNo = detail.getReferenceNo();
		transactionDate = detail.getTransactionDate();
		effectiveDate = detail.getPostDate();
		if( detail.getTransactionType().length() > 0 )
			transactionType = detail.getTransactionType().charAt(0);
		
		amount = detail.getAmount().doubleValue();
		nav = detail.getNAV().doubleValue();
		feeAmount = detail.getFeeAmount().doubleValue();
		unit = detail.getUnit().doubleValue();
		if( detail.getStatus().length() > 0)
			status = detail.getStatus().charAt(0);
		transactionSource = detail.getTransactionSource();
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
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
	public Character getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(Character transactionType) {
		this.transactionType = transactionType;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
	public String getTransactionSource() {
		return transactionSource;
	}
	public void setTransactionSource(String transactionSource) {
		this.transactionSource = transactionSource;
	}
}
