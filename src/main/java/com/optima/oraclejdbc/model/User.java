package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.cons.CommonCons;

@NISPEntity(tableName="NISP_USER", dbSchema=CommonCons.FE_DB_SCHEMA)
public class User implements Serializable{
	
	private static final long serialVersionUID = -1583014775025938173L;
	public static final int IS_UNREAD_AGGREMENT = 0;
	public static final int IS_READ_AGGREMENT = 1;
	
	@NISPEntityId
	@NISPEntity(columnName="USERNAME")
	private String username;
	
	@NISPEntity(columnName="LAST_LOGIN")
	private Date lastLogin;
	
	@NISPEntity(columnName="IS_READ_AGREEMENT")
	private Integer readAgreement;
	
	@NISPEntity(columnName="FAILED_LOGIN_COUNT")
	private Integer failedLoginCount;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Integer getFailedLoginCount() {
		return failedLoginCount;
	}

	public void setFailedLoginCount(Integer failedLoginCount) {
		this.failedLoginCount = failedLoginCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getReadAgreement() {
		return readAgreement;
	}

	public void setReadAgreement(Integer readAgreement) {
		this.readAgreement = readAgreement;
	}
}
