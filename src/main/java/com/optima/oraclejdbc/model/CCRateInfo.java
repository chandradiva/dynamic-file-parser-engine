package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName="CC_RATE_INFO")
public class CCRateInfo extends CCParentModel implements CCChildModel, CCCHChildModel, Serializable {

	private static final long serialVersionUID = -8267222344041698913L;

	@NISPEntity(columnName="ID")
	@NISPEntityId
	private Long id;
	
	@NISPEntity(columnName="CC_STATEMENT_ID")
	private Long ccStatementId;
	
	@NISPEntity(columnName="RECORD_TYPE")
	@Position(start = 0, length = 2)
	private String recordType;
	
	@NISPEntity(columnName="CARD_NO")
	@Position(start = 2, length = 23)
	private String cardNo;
	
	@NISPEntity(columnName="YEARLY_RETAIL_PERCENTAGE")
	@Position(start = 25, length = 6)
	private String yearlyRetailPercentage;
	
	@NISPEntity(columnName="MONTHLY_RETAIL_PERCENTAGE")
	@Position(start = 31, length = 6)
	private String monthlyRetailPercentage;
	
	@NISPEntity(columnName="YEARLY_CASH_ADVANCE")
	@Position(start = 37, length = 6)
	private String yearlyCashAdvance;
	
	@NISPEntity(columnName="MONTHLY_CASH_ADVENCE")
	@Position(start = 43, length = 6)
	private String monthlyCashAdvance;
	
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

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getYearlyRetailPercentage() {
		return yearlyRetailPercentage;
	}

	public void setYearlyRetailPercentage(String yearlyRetailPercentage) {
		this.yearlyRetailPercentage = yearlyRetailPercentage;
	}

	public String getMonthlyRetailPercentage() {
		return monthlyRetailPercentage;
	}

	public void setMonthlyRetailPercentage(String monthlyRetailPercentage) {
		this.monthlyRetailPercentage = monthlyRetailPercentage;
	}

	public String getYearlyCashAdvance() {
		return yearlyCashAdvance;
	}

	public void setYearlyCashAdvance(String yearlyCashAdvance) {
		this.yearlyCashAdvance = yearlyCashAdvance;
	}

	public String getMonthlyCashAdvance() {
		return monthlyCashAdvance;
	}

	public void setMonthlyCashAdvance(String monthlyCashAdvance) {
		this.monthlyCashAdvance = monthlyCashAdvance;
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

	public void setChId(Long chId) {
		this.chId = chId;
	}
}
