package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityDateFomat;
import com.optima.oraclejdbc.annotations.NISPEntityDefaultValue;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.NISPEntityNumberFormat;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName="BANCASS")
public class Bancass implements Serializable {

	private static final long serialVersionUID = 840774756490435016L;

	@NISPEntityId
	@NISPEntity(columnName="BATCH_ID")
	@Position(start = 0, length = 10)
	private String batchId;
	
	@NISPEntityId
	@NISPEntity(columnName="NO")
	@Position(start = 10, length = 10)
	private String no;
	
	@NISPEntity(columnName="SPAJ_NO")
	@Position(start = 20, length = 40)
	private String spajNo;
	
	@NISPEntity(columnName="DEBIT_CURRENCY")
	@Position(start = 60, length = 3)
	private String debitCurrency;
	
	@NISPEntity(columnName="TRANSACTION_DATE")
	@NISPEntityDateFomat(format="yyyyMMdd")
	@Position(start = 63, length = 8)
	private Date transactionDate;
	
	@NISPEntity(columnName="BILLING_AMOUNT")
	@NISPEntityNumberFormat(scale = 4)
	@Position(start = 71, length = 21)
	private double billingAmount;
	
	@NISPEntity(columnName="BILLING_DESCRIPTION")
	@Position(start = 92, length = 40)
	private String billingDescription;
	
	@NISPEntity(columnName="TRANSACTION_STATUS")
	@NISPEntityDefaultValue(defaultValue = "SG")
	@Position(start = 132, length = 1)
	private String transaction_status;
	
	@NISPEntity(columnName = "CREATED_DATE", create = true)
	private Date createdDate;
	
	@NISPEntity(columnName = "UPDATED_DATE", update = true)
	private Date updatedDate;

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getSpajNo() {
		return spajNo;
	}

	public void setSpajNo(String spajNo) {
		this.spajNo = spajNo;
	}

	public String getDebitCurrency() {
		return debitCurrency;
	}

	public void setDebitCurrency(String debitCurrency) {
		this.debitCurrency = debitCurrency;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getBillingDescription() {
		return billingDescription;
	}

	public void setBillingDescription(String billingDescription) {
		this.billingDescription = billingDescription;
	}

	public String getTransaction_status() {
		return transaction_status;
	}

	public void setTransaction_status(String transaction_status) {
		this.transaction_status = transaction_status;
	}

	public double getBillingAmount() {
		return billingAmount;
	}

	public void setBillingAmount(double billingAmount) {
		this.billingAmount = billingAmount;
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
