package com.optima.oraclejdbc.response.creditcard;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RowTransactionDetailResponse implements Serializable{

	private static final long serialVersionUID = -1613934041326830246L;

	@JsonFormat(pattern="dd/MM")
	private Date transactionDate;
	
	@JsonFormat(pattern="dd/MM")
	private Date postingDate;
	
	private String description;
	private Character sign;
	
	private String amount;

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Date getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Character getSign() {
		return sign;
	}

	public void setSign(Character sign) {
		this.sign = sign;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
}
