package com.optima.oraclejdbc.response.creditcard;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UnbilledTransactionDetailResponse implements Serializable{
	private static final long serialVersionUID = -401586351338271408L;
	@JsonFormat(pattern="dd/MM/yyyy", timezone="Asia/Jakarta")
	private Date transactionDate;
	private String description;
	private Double amount;
	public UnbilledTransactionDetailResponse(
			com.ocbc.schemas.soa.creditcard.service.wbst_inquiry_ccunbilledactivity.RESConsumerSequence_type1 seq) {
		transactionDate = seq.getCreditCardTransactionDetail().getTransactionDate();
		description = seq.getCreditCardTransactionDetail().getTransactionDescription();
		amount = seq.getCreditCardTransactionDetail().getTransactionAmount().doubleValue();
	}
	public UnbilledTransactionDetailResponse(){
	}
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
}
