package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;

@NISPEntity(tableName = "DAILY_STATEMENT_TEMP")
public class DailyStatementTemp implements Serializable {

	private static final long serialVersionUID = -1795865742436911634L;

	@NISPEntityId
	@NISPEntity(columnName="ID")
	private Long id;
	
	@NISPEntity(columnName="TRANSACTION_DATE")
	private Date transactionDate;
	
	@NISPEntity(columnName="VALUE_DATE")
	private Date valueDate;
	
	@NISPEntity(columnName="TRANS_DESCRIPTION")
	private String transDescription;
	
	@NISPEntity(columnName="TRX_DESCRIPTION")
	private String trxDescription;
	
	@NISPEntity(columnName="SIGN")
	private String sign;
	
	@NISPEntity(columnName="AMOUNT")
	private Double amount;
	
	@NISPEntity(columnName="BALANCE")
	private Double balance;
	
	@NISPEntity(columnName="ACCOUNT_NUMBER")
	private String accountNumber;
	
	@NISPEntity(columnName = "REMARK_1")
	private String remark1;
	
	@NISPEntity(columnName = "REMARK_2")
	private String remark2;
	
	@NISPEntity(columnName = "REMARK_3")
	private String remark3;
	
	@NISPEntity(columnName = "REMARK_4")
	private String remark4;
	
	@NISPEntity(columnName = "TRANS_CODE")
	private String transCode;
	
	public DailyStatementTemp() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public String getTransDescription() {
		return transDescription;
	}

	public void setTransDescription(String transDescription) {
		this.transDescription = transDescription;
	}

	public String getTrxDescription() {
		return trxDescription;
	}

	public void setTrxDescription(String trxDescription) {
		this.trxDescription = trxDescription;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public String getRemark4() {
		return remark4;
	}

	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
}
