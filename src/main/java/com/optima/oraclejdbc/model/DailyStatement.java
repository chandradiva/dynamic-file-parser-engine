package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityDateFomat;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.NISPEntityNumberFormat;
import com.optima.oraclejdbc.annotations.Position;
import com.optima.oraclejdbc.utils.CommonUtils;

@NISPEntity(tableName="DAILY_STATEMENT")
public class DailyStatement implements Serializable {

	private static final long serialVersionUID = 4703744241819297702L;

	@NISPEntityId
	@NISPEntity(columnName="RECORD_ID")
	@Position(start = 0, length = 10)
	private String recordId;
	
	@NISPEntity(columnName="ACCOUNT_NUMBER")
	@Position(start = 10, length = 19)
	private String accountNumber;
	
	@NISPEntity(columnName="CURRENCY_CODE")
	@Position(start = 29, length = 3)
	private String currencyCode;
	
	@NISPEntityDateFomat(format="yyyyMMdd")
	@NISPEntity(columnName="TRANSACTION_DATE")
	@Position(start = 32, length = 8)
	private Date transactionDate;
	
	@NISPEntityDateFomat(format="yyyyMMdd")
	@NISPEntity(columnName="VALUE_DATE")
	@Position(start = 40, length = 8)
	private Date valueDate;
	
	@NISPEntity(columnName="DEBIT_OR_CREDIT")
	@Position(start = 48, length = 1)
	private String debitOrCredit;
	
	@NISPEntity(columnName="AMOUNT")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 49, length = 17)
	private double amount;
	
	@NISPEntity(columnName="BALANCE")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 66, length = 17)
	private double balance;
	
	@NISPEntity(columnName="TRAN_CODE")
	@Position(start = 83, length = 4)
	private String tranCode;
	
	@NISPEntity(columnName="TRAN_DESC")
	@Position(start = 87, length = 50)
	private String tranDesc;
	
	@NISPEntity(columnName="TRX_DESC")
	@Position(start = 137, length = 50)
	private String trxDesc;
	
	@NISPEntity(columnName="REMARK_1")
	@Position(start = 187, length = 60)
	private String remark1;
	
	@NISPEntity(columnName="REMARK_2")
	@Position(start = 247, length = 60)
	private String remark2;
	
	@NISPEntity(columnName="REMARK_3")
	@Position(start = 307, length = 60)
	private String remark3;
	
	@NISPEntity(columnName="REMARK_4")
	@Position(start = 367, length = 60)
	private String remark4;
	
	@NISPEntity(columnName="CHEQUE_NO")
	@Position(start = 427, length = 15) //old length 10
	private String chequeNo;
	
	@NISPEntity(columnName="TRREFN")	
	@Position(start = 442, length = 20) //old length 16
	private String trrefn;
	
	@NISPEntity(columnName = "CREATED_DATE", create = true)
	private Date createdDate;
	
	@NISPEntity(columnName = "UPDATED_DATE", update = true)
	private Date updatedDate;

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = CommonUtils.normalizeAccNo(accountNumber);
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

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public String getDebitOrCredit() {
		return debitOrCredit;
	}

	public void setDebitOrCredit(String debitOrCredit) {
		this.debitOrCredit = debitOrCredit;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getTranDesc() {
		return tranDesc;
	}

	public void setTranDesc(String tranDesc) {
		this.tranDesc = tranDesc;
	}

	public String getTrxDesc() {
		return trxDesc;
	}

	public void setTrxDesc(String trxDesc) {
		this.trxDesc = trxDesc;
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

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getTrrefn() {
		return trrefn;
	}

	public void setTrrefn(String trrefn) {
		this.trrefn = trrefn;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}
