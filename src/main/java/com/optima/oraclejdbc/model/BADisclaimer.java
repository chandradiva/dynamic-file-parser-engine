package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName = "BA_DISCLAIMER")
public class BADisclaimer extends BAParentModel implements BAChildModel, Serializable {

	private static final long serialVersionUID = -4900580236814619390L;

	@NISPEntity(columnName = "ID")
	@NISPEntityId
	private Long id;
	
	@NISPEntity(columnName = "BA_TRANSACTION_SUMMARY_ID")
	private Long baTransactionSummaryId;
	
	@NISPEntity(columnName = "RECORD_TYPE")
	@Position(start = 0, length = 2)
	private String recordType; //DM
	
	@NISPEntity(columnName = "DISCLAIMER_NO")
	@Position(start = 2, length = 3)
	private String disclaimerNo;
	
	@NISPEntity(columnName = "DISCLAIMER_DESCRIPTION")
	@Position(start = 5, length = 1000)
	private String disclaimerDescription;
	
	@NISPEntity(columnName = "CREATED_DATE", create = true)
	private Date createdDate;
	
	@NISPEntity(columnName = "UPDATED_DATE", update = true)
	private Date updatedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBaTransactionSummaryId() {
		return baTransactionSummaryId;
	}

	@Override
	public void setBaTransactionSummaryId(Long baTransactionSummaryId) {
		this.baTransactionSummaryId = baTransactionSummaryId;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getDisclaimerNo() {
		return disclaimerNo;
	}

	public void setDisclaimerNo(String disclaimerNo) {
		this.disclaimerNo = disclaimerNo;
	}

	public String getDisclaimerDescription() {
		return disclaimerDescription;
	}

	public void setDisclaimerDescription(String disclaimerDescription) {
		this.disclaimerDescription = disclaimerDescription;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	@Override
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}
