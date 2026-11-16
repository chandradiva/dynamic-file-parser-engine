package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityDefaultValue;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName="CC_STATEMENT_MESSAGE")
public class CCStatementMessage extends CCParentModel implements CCChildModel, CCCHChildModel, Serializable {

	private static final long serialVersionUID = 6940817661851406657L;

	@NISPEntity(columnName="ID")
	@NISPEntityId
	private Long id;
	
	@NISPEntity(columnName="CC_STATEMENT_ID")
	private Long ccStatementId;
	
	@NISPEntity(columnName="RECORD_TYPE")
	@Position(start = 0, length = 2)
	private String recordType; //MS
	
	@NISPEntity(columnName="MESSAGE_TYPE")
	@NISPEntityDefaultValue(defaultValue = "01020304050607")
	@Position(start = 2, length = 2)
	private String messageType;
	
	@NISPEntity(columnName="MESSAGE_SEQUENCE")
	@Position(start = 4, length = 2)
	private String messageSequence;
	
	@NISPEntity(columnName="PROCESSING_MESSAGE_INDICATOR")
	@Position(start = 6, length = 10)
	private String processingMessageIndicator;
	
	@NISPEntity(columnName="MESSAGE_HEADING")
	@Position(start = 16, length = 100)
	private String messageHeading;
	
	@NISPEntity(columnName="MESSAGE_VARIABLE")
	@Position(start = 116, length = 500)
	private String messageVariable;
	
	@NISPEntity(columnName="MESSAGE_DATA")
	@Position(start = 616, length = 800)
	private String messageData;
	
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

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageSequence() {
		return messageSequence;
	}

	public void setMessageSequence(String messageSequence) {
		this.messageSequence = messageSequence;
	}

	public String getProcessingMessageIndicator() {
		return processingMessageIndicator;
	}

	public void setProcessingMessageIndicator(String processingMessageIndicator) {
		this.processingMessageIndicator = processingMessageIndicator;
	}

	public String getMessageHeading() {
		return messageHeading;
	}

	public void setMessageHeading(String messageHeading) {
		this.messageHeading = messageHeading;
	}

	public String getMessageVariable() {
		return messageVariable;
	}

	public void setMessageVariable(String messageVariable) {
		this.messageVariable = messageVariable;
	}

	public String getMessageData() {
		return messageData;
	}

	public void setMessageData(String messageData) {
		this.messageData = messageData;
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
