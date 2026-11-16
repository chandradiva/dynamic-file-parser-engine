package com.optima.oraclejdbc.response.creditcard;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UnbilledTransactionResponse implements Serializable{
	private static final long serialVersionUID = 4082946708643014329L;
	@JsonFormat(pattern="dd MMM yyyy", timezone="Asia/Jakarta")
	private Date fromDate;
	@JsonFormat(pattern="dd MMM yyyy", timezone="Asia/Jakarta")
	private Date toDate;
	private Integer countTransaction;
	private List<UnbilledTransactionDetailResponse> transactionDetails;
	
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Integer getCountTransaction() {
		return countTransaction;
	}
	public void setCountTransaction(Integer countTransaction) {
		this.countTransaction = countTransaction;
	}
	public List<UnbilledTransactionDetailResponse> getTransactionDetails() {
		return transactionDetails;
	}
	public void setTransactionDetails(List<UnbilledTransactionDetailResponse> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}
	
}