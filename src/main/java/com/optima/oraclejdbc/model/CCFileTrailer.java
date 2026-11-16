package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityDefaultValue;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName = "CC_FILE_TRAILER")
public class CCFileTrailer extends CCParentModel implements CCChildModel, CCCHChildModel, Serializable {

	private static final long serialVersionUID = 7031551956681055515L;
	
	@NISPEntity(columnName = "ID")
	@NISPEntityId
	private Long id;
	
	@NISPEntity(columnName = "CC_STATEMENT_ID")
	private Long ccStatementId;
	
	@NISPEntity(columnName = "RECORD_TYPE")
	@Position(start = 0, length = 2)
	private String recordType; //FT
	
	@NISPEntity(columnName = "NUMBER_OF_CUSTOMERS")
	@Position(start = 2, length = 9)
	private String numberOfCustomers;
	
	@NISPEntity(columnName = "NUMBER_OF_ACCOUNTS")
	@Position(start = 11, length = 9)
	private String numberOfAccounts;
	
	@NISPEntity(columnName = "NUMBER_OF_TRANSACTIONS")
	@Position(start = 20, length = 10)
	private String numberOfTransactions;
	
	@NISPEntity(columnName = "HASH_TOTAL")
	@Position(start = 30, length = 30)
	private String hashTotal;
	
	@NISPEntity(columnName = "HASH_TOTAL_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 60, length = 1)
	private String hashTotalSign;
	
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

	public String getNumberOfCustomers() {
		return numberOfCustomers;
	}

	public void setNumberOfCustomers(String numberOfCustomers) {
		this.numberOfCustomers = numberOfCustomers;
	}

	public String getNumberOfAccounts() {
		return numberOfAccounts;
	}

	public void setNumberOfAccounts(String numberOfAccounts) {
		this.numberOfAccounts = numberOfAccounts;
	}

	public String getNumberOfTransactions() {
		return numberOfTransactions;
	}

	public void setNumberOfTransactions(String numberOfTransactions) {
		this.numberOfTransactions = numberOfTransactions;
	}

	public String getHashTotal() {
		return hashTotal;
	}

	public void setHashTotal(String hashTotal) {
		this.hashTotal = hashTotal;
	}

	public String getHashTotalSign() {
		return hashTotalSign;
	}

	public void setHashTotalSign(String hashTotalSign) {
		this.hashTotalSign = hashTotalSign;
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
