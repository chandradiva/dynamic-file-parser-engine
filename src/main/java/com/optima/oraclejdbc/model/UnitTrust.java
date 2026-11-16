package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityDateFomat;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.NISPEntityNumberFormat;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName = "UNIT_TRUST")
public class UnitTrust implements Serializable {

	private static final long serialVersionUID = 6498752671563691901L;

	@NISPEntityId
	@NISPEntity(columnName = "RECORD_ID")
	@Position(start = 0, length = 10)
	private String recordId;
	
	@NISPEntityId
	@NISPEntity(columnName = "ACCOUNT_NO")
	@Position(start = 10, length = 19) //length 12
	private String accountNo;
	
	@NISPEntity(columnName = "CURRENCY_CODE")
	@Position(start = 29, length = 3)
	private String currencyCode;
	
	@NISPEntityDateFomat(format="yyyyMMdd")
	@NISPEntity(columnName = "TRANSACTION_DATE")
	@Position(start = 32, length = 8)
	private Date transactionDate;
	
	@NISPEntityDateFomat(format="yyyyMMdd")
	@NISPEntity(columnName = "EFFECTIVE_DATE")
	@Position(start = 40, length = 8)
	private Date effectiveDate;
	
	@NISPEntity(columnName = "REFERENCE_NO")
	@Position(start = 48, length = 20)
	private String referenceNo;
	
	@NISPEntity(columnName = "TRANSACTION_TYPE")
	@Position(start = 68, length = 1)
	private String transactionType;
	
	@NISPEntity(columnName = "AMOUNT")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 69, length = 17)
	private double amount;
	
	@NISPEntity(columnName = "NAV")
	@NISPEntityNumberFormat(scale = 4)
	@Position(start = 86, length = 19)
	private double nav;
	
	@NISPEntity(columnName = "FEE_AMOUNT")
	@NISPEntityNumberFormat(scale = 4)
	@Position(start = 105, length = 19)
	private double feeAmount;
	
	@NISPEntity(columnName = "UNIT")
	@NISPEntityNumberFormat(scale = 4)
	@Position(start = 124, length = 19)
	private double unit;
	
	@NISPEntity(columnName = "STATUS")
	@Position(start = 143, length = 1)
	private String status;
	
	@NISPEntity(columnName = "TRANSACTION_SOURCE")
	@Position(start = 144, length = 10)
	private String transactionSource;
	
//	@NISPEntity(columnName = "CHANNEL")
//	@Position(start = 154, length = 50)
	private String channel;
	
//	@NISPEntityDateFomat(format="yyyyMMdd")
//	@NISPEntity(columnName = "PROCESS_DATE")
//	@Position(start = 204, length = 8)
	private Date processDate;
	
//	@NISPEntity(columnName = "TRAN_CODE")
//	@Position(start = 212, length = 20)
	private String tranCode;
	
//	@NISPEntity(columnName = "TRAN_TYPE")
//	@Position(start = 232, length = 4)
	private int tranType;
	
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getNav() {
		return nav;
	}

	public void setNav(double nav) {
		this.nav = nav;
	}

	public double getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(double feeAmount) {
		this.feeAmount = feeAmount;
	}

	public double getUnit() {
		return unit;
	}

	public void setUnit(double unit) {
		this.unit = unit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransactionSource() {
		return transactionSource;
	}

	public void setTransactionSource(String transactionSource) {
		this.transactionSource = transactionSource;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public int getTranType() {
		return tranType;
	}

	public void setTranType(int tranType) {
		this.tranType = tranType;
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
