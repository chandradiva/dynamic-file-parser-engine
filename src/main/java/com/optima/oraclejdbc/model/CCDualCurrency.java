package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityDefaultValue;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName="CC_DUAL_CURRENCY")
public class CCDualCurrency extends CCParentModel implements CCChildModel, CCCHChildModel, Serializable {

	private static final long serialVersionUID = 1891577960975151683L;
	
	@NISPEntity(columnName = "ID")
	@NISPEntityId
	private Long id;
	
	@NISPEntity(columnName = "CC_STATEMENT_ID")
	private Long ccStatementId;
	
	@NISPEntity(columnName = "RECORD_TYPE")
	@Position(start = 0, length = 2)
	private String recordType; //DC
	
	@NISPEntity(columnName = "DESCRIPTION")
	@Position(start = 2, length = 365)
	private String description;
	
	@NISPEntity(columnName = "TOTAL_OUTSTANDING_BALANCE_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 367, length = 1)
	private String totalOutstandingBalanceSign;
	
	@NISPEntity(columnName = "TOTAL_OUTSTANDING_BALANCE")
	@Position(start = 368, length = 14)
	private String totalOutstandingBalance;
	
	@NISPEntity(columnName = "MINIMUM_PAYMENT")
	@Position(start = 382, length = 14)
	private String minimumPayment;
	
	@NISPEntity(columnName = "DUAL_CURRENCY_TRANSACTION_1")
	@Position(start = 396, length = 20)
	private String dualCurrencyTransaction1;
	
	@NISPEntity(columnName = "DUAL_CURRENCY_TRANSACTION_2")
	@Position(start = 416, length = 20)
	private String dualCurrencyTransaction2;
	
	@NISPEntity(columnName = "MESSAGE_DATA")
	@Position(start = 436, length = 120)
	private String messageData;
	
	@NISPEntity(columnName = "CREATED_DATE", create = true)
	private Date createdDate;
	
	@NISPEntity(columnName = "UPDATED_DATE", update = true)
	private Date updatedDate;
	
	@NISPEntity(columnName = "CH_ID")
	private Long chId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCcStatementId() {
		return ccStatementId;
	}

	@Override
	public void setCcStatementId(Long ccStatementId) {
		this.ccStatementId = ccStatementId;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTotalOutstandingBalanceSign() {
		return totalOutstandingBalanceSign;
	}

	public void setTotalOutstandingBalanceSign(String totalOutstandingBalanceSign) {
		this.totalOutstandingBalanceSign = totalOutstandingBalanceSign;
	}

	public String getTotalOutstandingBalance() {
		return totalOutstandingBalance;
	}

	public void setTotalOutstandingBalance(String totalOutstandingBalance) {
		this.totalOutstandingBalance = totalOutstandingBalance;
	}

	public String getMinimumPayment() {
		return minimumPayment;
	}

	public void setMinimumPayment(String minimumPayment) {
		this.minimumPayment = minimumPayment;
	}

	public String getDualCurrencyTransaction1() {
		return dualCurrencyTransaction1;
	}

	public void setDualCurrencyTransaction1(String dualCurrencyTransaction1) {
		this.dualCurrencyTransaction1 = dualCurrencyTransaction1;
	}

	public String getDualCurrencyTransaction2() {
		return dualCurrencyTransaction2;
	}

	public void setDualCurrencyTransaction2(String dualCurrencyTransaction2) {
		this.dualCurrencyTransaction2 = dualCurrencyTransaction2;
	}

	public String getMessageData() {
		return messageData;
	}

	public void setMessageData(String messageData) {
		this.messageData = messageData;
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

	public Long getChId() {
		return chId;
	}

	public void setChId(Long chId) {
		this.chId = chId;
	}
}
