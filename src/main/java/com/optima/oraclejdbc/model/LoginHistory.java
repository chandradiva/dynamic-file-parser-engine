package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.cons.CommonCons;

@NISPEntity(tableName="NISP_LOGIN_HISTORY", dbSchema=CommonCons.FE_DB_SCHEMA)
public class LoginHistory implements Serializable{
	
	private static final long serialVersionUID = -1583014775025938173L;
	
	public static final int FAILED = 0;
	public static final int SUCCESS = 1;

	@NISPEntityId
	@NISPEntity(columnName="ID")
	private Long id;
	
	@NISPEntity(columnName="USERNAME")
	private String username;
	
	@NISPEntity(columnName="LOGIN_TIME")
	private Date loginTime;
	
	@NISPEntity(columnName="IS_SUCCESS")
	private Integer isSuccess;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}

	public LoginHistory(String username) {
		super();
		this.username = username;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
