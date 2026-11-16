package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.optima.oraclejdbc.annotations.NISPEntitySerialize;


public class ApplicationParameter implements Serializable{

	private static final long serialVersionUID = 6757005319590654878L;
	@NISPEntitySerialize(field="KEY")
	private String key;
	@NISPEntitySerialize(field="KEY_DESC")
	private String keyDesc;
	@NISPEntitySerialize(field="VALUE")
	private String value;
	@JsonFormat(pattern="dd/MM/yyyy", timezone="Asia/Jakarta")
	private Date createdAt;
	@JsonFormat(pattern="dd/MM/yyyy", timezone="Asia/Jakarta")
	private Date updateAt;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getKeyDesc() {
		return keyDesc;
	}
	public void setKeyDesc(String keyDesc) {
		this.keyDesc = keyDesc;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	
}
