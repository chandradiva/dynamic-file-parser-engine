package com.optima.oraclejdbc.model.backoffice;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.cons.CommonCons;

@NISPEntity(tableName = "BO_TBL_BLOCK_HC", dbSchema = CommonCons.DB_SCHEMA)
public class BOBlockHardcopy implements Serializable {

	private static final long serialVersionUID = -4803166606299016314L;

	@NISPEntityId
	@NISPEntity(columnName = "ID")
	private Long id;
	
	@NISPEntity(columnName = "NAME")
	private String name;
	
	@NISPEntity(columnName = "ACCOUNT_NUMBER")
	private String accountNumber;
	
	@NISPEntity(columnName = "STATUS")
	private Integer status;
	
	@NISPEntity(columnName = "TYPE")
	private Integer type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
