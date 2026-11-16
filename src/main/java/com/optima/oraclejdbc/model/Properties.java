package com.optima.oraclejdbc.model;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;

@NISPEntity(tableName="TBL_PROPERTIES")
public class Properties implements Serializable{

	private static final long serialVersionUID = -8839860943549294628L;

	@NISPEntityId
	@NISPEntity(columnName="ID")	
	private Long id;
		
	@NISPEntity(columnName="KEY")
	private String key;
	
	@NISPEntity(columnName="VALUE")
	private String value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
