package com.optima.oraclejdbc.model.estatement;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.utils.CommonUtils;

@NISPEntity(tableName = "ES_ACCOUNT_INFO")
public class ESAccountInfo extends DDIModel implements Serializable{

	private static final long serialVersionUID = -2203062621183367879L;

	@NISPEntityId
	@NISPEntity(columnName = "CIF_NO")
	private String cifNo;
	
	@NISPEntity(columnName = "EMAIL")
	private String email;
	
	@NISPEntity(columnName = "DOB")
	private Date dob;

	public String getCifNo() {
		return cifNo;
	}

	public void setCifNo(String cifNo) {
		this.cifNo = CommonUtils.normalizeCifKey(cifNo);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}
}
