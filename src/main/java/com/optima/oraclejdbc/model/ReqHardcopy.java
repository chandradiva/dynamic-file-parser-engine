package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;

@NISPEntity(tableName = "TBL_REQ_HARDCOPY")
public class ReqHardcopy implements Serializable {

	private static final long serialVersionUID = 1922383267036136787L;

	@NISPEntityId
	@NISPEntity(columnName = "ID")
	private Long id;
	
	@NISPEntity(columnName = "REQUEST_DATE")
	@JsonFormat(pattern="dd MMM yyyy HH:mm:ss", timezone="Asia/Jakarta")
	private Date requestDate;
	
	@NISPEntity(columnName = "PERIODE")
	private String periode;
	
	@NISPEntity(columnName = "ACCOUNT_NUMBER")
	private String accountNumber;
	
	@NISPEntity(columnName = "CURRENCY")
	private String currency;
	
	@NISPEntity(columnName = "MAILING_ADDRESS")
	private String mailingAddress;
	
	@NISPEntity(columnName = "STATUS")
	private int status;
	
	@NISPEntity(columnName = "BATCH_ID")
	private Long batchId;
	
	@JsonFormat(pattern="dd MMM yyyy HH:mm:ss", timezone="Asia/Jakarta")
	@NISPEntity(columnName = "PROCESS_DATE")
	private Date processDate;
	
	@NISPEntity(columnName = "PERIODE_NUMBER")
	private int periodeNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public int getPeriodeNumber() {
		return periodeNumber;
	}

	public void setPeriodeNumber(int periodeNumber) {
		this.periodeNumber = periodeNumber;
	}
}
