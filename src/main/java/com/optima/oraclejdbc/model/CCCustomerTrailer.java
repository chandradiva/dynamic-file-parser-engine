package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityDefaultValue;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName="CC_CUSTOMER_TRAILER")
public class CCCustomerTrailer extends CCParentModel implements CCChildModel, CCCHChildModel, Serializable{

	private static final long serialVersionUID = -7196766309077009120L;
	
	@NISPEntity(columnName = "ID")
	@NISPEntityId
	private Long id;
	
	@NISPEntity(columnName = "CC_STATEMENT_ID")
	private Long ccStatementId;
	
	@NISPEntity(columnName = "RECORD_TYPE")
	@Position(start = 0, length = 2)
	private String recordType; //CT
	
	@NISPEntity(columnName = "TOTAL_NO_OF_ACCOUNTS")
	@Position(start = 2, length = 9)
	private String totalNoOfAcounts;
	
	@NISPEntity(columnName = "TOTAL_NO_OF_TRANS_PERFORMED")
	@Position(start = 11, length = 9)
	private String totalNoOfTransPerformed;
	
	@NISPEntity(columnName = "NO_OF_STATEMENT_MESSAGES")
	@Position(start = 20, length = 2)
	private String noOfStatementMessages;
	
	@NISPEntity(columnName = "STATEMENT_HOPPER_1")
	@NISPEntityDefaultValue(defaultValue = "YN")
	@Position(start = 22, length = 1)
	private String statementHopper1;
	
	@NISPEntity(columnName = "STATEMENT_HOPPER_2")
	@NISPEntityDefaultValue(defaultValue = "YN")
	@Position(start = 23, length = 1)
	private String statementHopper2;
	
	@NISPEntity(columnName = "STATEMENT_HOPPER_3")
	@NISPEntityDefaultValue(defaultValue = "YN")
	@Position(start = 24, length = 1)
	private String statementHopper3;
	
	@NISPEntity(columnName = "STATEMENT_HOPPER_4")
	@NISPEntityDefaultValue(defaultValue = "YN")
	@Position(start = 25, length = 1)
	private String statementHopper4;
	
	@NISPEntity(columnName = "STATEMENT_HOPPER_5")
	@NISPEntityDefaultValue(defaultValue = "YN")
	@Position(start = 26, length = 1)
	private String statementHopper5;
	
	@NISPEntity(columnName = "STATEMENT_HOPPER_6")
	@NISPEntityDefaultValue(defaultValue = "YN")
	@Position(start = 27, length = 1)
	private String statementHopper6;
	
//	@NISPEntity(columnName = "FOOTER_MESSAGE_INDICATOR")
//	@Position(start = 28, length = 2)
	private String footerMessageIndicator;
	
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

	public String getTotalNoOfAcounts() {
		return totalNoOfAcounts;
	}

	public void setTotalNoOfAcounts(String totalNoOfAcounts) {
		this.totalNoOfAcounts = totalNoOfAcounts;
	}

	public String getTotalNoOfTransPerformed() {
		return totalNoOfTransPerformed;
	}

	public void setTotalNoOfTransPerformed(String totalNoOfTransPerformed) {
		this.totalNoOfTransPerformed = totalNoOfTransPerformed;
	}

	public String getNoOfStatementMessages() {
		return noOfStatementMessages;
	}

	public void setNoOfStatementMessages(String noOfStatementMessages) {
		this.noOfStatementMessages = noOfStatementMessages;
	}

	public String getStatementHopper1() {
		return statementHopper1;
	}

	public void setStatementHopper1(String statementHopper1) {
		this.statementHopper1 = statementHopper1;
	}

	public String getStatementHopper2() {
		return statementHopper2;
	}

	public void setStatementHopper2(String statementHopper2) {
		this.statementHopper2 = statementHopper2;
	}

	public String getStatementHopper3() {
		return statementHopper3;
	}

	public void setStatementHopper3(String statementHopper3) {
		this.statementHopper3 = statementHopper3;
	}

	public String getStatementHopper4() {
		return statementHopper4;
	}

	public void setStatementHopper4(String statementHopper4) {
		this.statementHopper4 = statementHopper4;
	}

	public String getStatementHopper5() {
		return statementHopper5;
	}

	public void setStatementHopper5(String statementHopper5) {
		this.statementHopper5 = statementHopper5;
	}

	public String getStatementHopper6() {
		return statementHopper6;
	}

	public void setStatementHopper6(String statementHopper6) {
		this.statementHopper6 = statementHopper6;
	}

	public String getFooterMessageIndicator() {
		return footerMessageIndicator;
	}

	public void setFooterMessageIndicator(String footerMessageIndicator) {
		this.footerMessageIndicator = footerMessageIndicator;
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
