package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityDefaultValue;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName="CC_ACCOUNT_BALANCE")
public class CCAccountBalance extends CCParentModel implements CCChildModel, CCCHChildModel, Serializable {

	private static final long serialVersionUID = 1756071541654138059L;

	@NISPEntity(columnName="ID")
	@NISPEntityId
	private Long id;
	
	@NISPEntity(columnName="CC_STATEMENT_ID")
	private Long ccStatementId;
	
	@NISPEntity(columnName="RECORD_TYPE")
	@Position(start = 0, length = 2)
	private String recordType;
	
	@NISPEntity(columnName="OPENING_BALANCE_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 2, length = 1)
	private String openingBalanceSign;
	
	@NISPEntity(columnName="OPEN_BALANCE_BEFORE")
	@Position(start = 3, length = 14)
	private String openBalanceBefore;
	
	@NISPEntity(columnName="RETAIL_TRANSACTION_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 17, length = 1)
	private String retailTransactionSign;
	
	@NISPEntity(columnName="RETAIL_TRANSACTION_AMOUNT")
	@Position(start = 18, length = 14)
	private String retailTransactionAmount;
	
	@NISPEntity(columnName="CASH_ADVANCE_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 32, length = 1)
	private String cashAdvanceSign;
	
	@NISPEntity(columnName="CASH_ADVANCE_AMOUNT")
	@Position(start = 33, length = 14)
	private String cashAdvanceAmount;
	
	@NISPEntity(columnName="CREDIT_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 47, length = 1)
	private String creditSign;
	
	@NISPEntity(columnName="CREDIT_AMOUNT")
	@Position(start = 48, length = 14)
	private String creditAmount;
	
	@NISPEntity(columnName="INTEREST_CHARGES_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 62, length = 1)
	private String interestChargesSign;
	
	@NISPEntity(columnName="INTEREST_CHARGES_AMOUNT")
	@Position(start = 63, length = 14)
	private String interestChargesAmount;
	
	@NISPEntity(columnName="PAYMENT_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 77, length = 1)
	private String paymentSign;
	
	@NISPEntity(columnName="PAYMENT_AMOUNT")
	@Position(start = 78, length = 14)
	private String paymentAmount;
	
	@NISPEntity(columnName="TOTAL_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 92, length = 1)
	private String totalSign;
	
	@NISPEntity(columnName="TOTAL")
	@Position(start = 93, length = 14)
	private String total;
	
	private String filler;
	
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

	public String getOpeningBalanceSign() {
		return openingBalanceSign;
	}

	public void setOpeningBalanceSign(String openingBalanceSign) {
		this.openingBalanceSign = openingBalanceSign;
	}

	public String getOpenBalanceBefore() {
		return openBalanceBefore;
	}

	public void setOpenBalanceBefore(String openBalanceBefore) {
		this.openBalanceBefore = openBalanceBefore;
	}

	public String getRetailTransactionSign() {
		return retailTransactionSign;
	}

	public void setRetailTransactionSign(String retailTransactionSign) {
		this.retailTransactionSign = retailTransactionSign;
	}

	public String getRetailTransactionAmount() {
		return retailTransactionAmount;
	}

	public void setRetailTransactionAmount(String retailTransactionAmount) {
		this.retailTransactionAmount = retailTransactionAmount;
	}

	public String getCashAdvanceSign() {
		return cashAdvanceSign;
	}

	public void setCashAdvanceSign(String cashAdvanceSign) {
		this.cashAdvanceSign = cashAdvanceSign;
	}

	public String getCashAdvanceAmount() {
		return cashAdvanceAmount;
	}

	public void setCashAdvanceAmount(String cashAdvanceAmount) {
		this.cashAdvanceAmount = cashAdvanceAmount;
	}

	public String getCreditSign() {
		return creditSign;
	}

	public void setCreditSign(String creditSign) {
		this.creditSign = creditSign;
	}

	public String getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}

	public String getInterestChargesSign() {
		return interestChargesSign;
	}

	public void setInterestChargesSign(String interestChargesSign) {
		this.interestChargesSign = interestChargesSign;
	}

	public String getInterestChargesAmount() {
		return interestChargesAmount;
	}

	public void setInterestChargesAmount(String interestChargesAmount) {
		this.interestChargesAmount = interestChargesAmount;
	}

	public String getPaymentSign() {
		return paymentSign;
	}

	public void setPaymentSign(String paymentSign) {
		this.paymentSign = paymentSign;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getTotalSign() {
		return totalSign;
	}

	public void setTotalSign(String totalSign) {
		this.totalSign = totalSign;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getFiller() {
		return filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
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

	@Override
	public void setChId(Long chId) {
		this.chId = chId;
	}
}
