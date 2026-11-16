package com.optima.oraclejdbc.response.bancass;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.optima.oraclejdbc.annotations.NISPEntitySerialize;
import com.optima.oraclejdbc.jsonserializer.DoubleSerializer;

public class BancassMiniResponse implements Serializable {

	private static final long serialVersionUID = -4845611684062271852L;

	@JsonFormat(pattern="dd/MM/yyyy", timezone="Asia/Jakarta")
	@NISPEntitySerialize(field="TRANSACTION_DATE")
	private Date transactionDate;
	
	@NISPEntitySerialize(field="BILLING_DESCRIPTION")
	private String description;
	
	@JsonSerialize(using=DoubleSerializer.class)
	@NISPEntitySerialize(field="BILLING_AMOUNT")
	private Double amount;
	
	@NISPEntitySerialize(field="DEBIT_CURRENCY")
	private String currency;
	
	@NISPEntitySerialize(field="TRANSACTION_STATUS")
	private Character status;

	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
	
	
}
