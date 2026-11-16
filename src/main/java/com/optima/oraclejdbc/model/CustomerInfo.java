package com.optima.oraclejdbc.model;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.utils.CommonUtils;

@NISPEntity(tableName="TBL_CUSTOMER_INFO")
public class CustomerInfo implements Serializable
{
	private static final long serialVersionUID = 8201826193207633496L;

	@NISPEntityId
	@NISPEntity(columnName="ID")
	private Long id;
	
	@NISPEntity(columnName="CIF_KEY")
	private String cifKey;
	
	@NISPEntity(columnName="USER_ID")
	private String userId;
	
	@NISPEntity(columnName="STATEMENT_PASSWORD")
	private String statementPassword;
	
	@NISPEntity(columnName = "IS_MONTHLY")
	private int isMonthly;
	
	@NISPEntity(columnName = "IS_PASSWORD_DEFAULT")
	private int isPasswordDefault;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCifKey() {
		return cifKey;
	}

	public void setCifKey(String cifKey) {
		this.cifKey = CommonUtils.normalizeCifKey(cifKey);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatementPassword() {
		return statementPassword;
	}

	public void setStatementPassword(String statementPassword) {
		this.statementPassword = statementPassword;
	}

	public int getIsMonthly() {
		return isMonthly;
	}

	public void setIsMonthly(int isMonthly) {
		this.isMonthly = isMonthly;
	}

	public int getIsPasswordDefault() {
		return isPasswordDefault;
	}

	public void setIsPasswordDefault(int isPasswordDefault) {
		this.isPasswordDefault = isPasswordDefault;
	}
}
