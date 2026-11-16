package com.optima.oraclejdbc.response.creditcard;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.optima.oraclejdbc.annotations.NISPEntitySerialize;

public class AccountHeaderResponse implements Serializable {
	private static final long serialVersionUID = 7734325645978176249L;

	@NISPEntitySerialize(field="ID")
	private Long id;
	
	@NISPEntitySerialize(field="CH_ID")
	private Long chId;
	
	@NISPEntitySerialize(field="CARDHOLDER_NUMBER")
	private String customerNumber;
	
	@JsonFormat(pattern="dd-MM-yyyy", timezone="Asia/Jakarta")
	@NISPEntitySerialize(field="STATEMENT_DATE")
	private Date statementDate;
	
	@JsonFormat(pattern="dd-MM-yyyy", timezone="Asia/Jakarta")
	@NISPEntitySerialize(field="PAYMENT_DUE_DATE")
	private Date paymentDueDate;
	
	@NISPEntitySerialize(field="TOTAL_OUTSTANDING_BALANCE")
	private String outstandingBalance;
	
	@NISPEntitySerialize(field="MINIMUM_PAYMENT")
	private String minimumPayment;
	
	@NISPEntitySerialize(field = "CREDIT_QUALITY")
	private String creditQuality;

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public Date getStatementDate() {
		return statementDate;
	}

	public void setStatementDate(Date statementDate) {
		this.statementDate = statementDate;
	}

	public Date getPaymentDueDate() {
		return paymentDueDate;
	}

	public void setPaymentDueDate(Date paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getChId() {
		return chId;
	}

	public void setChId(Long chId) {
		this.chId = chId;
	}

	public String getOutstandingBalance() {
		return outstandingBalance;
	}

	public void setOutstandingBalance(String outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}

	public String getMinimumPayment() {
		return minimumPayment;
	}

	public void setMinimumPayment(String minimumPayment) {
		this.minimumPayment = minimumPayment;
	}

	public String getCreditQuality() {
		return creditQuality;
	}

	public void setCreditQuality(String creditQuality) {
		this.creditQuality = creditQuality;
	}
}
