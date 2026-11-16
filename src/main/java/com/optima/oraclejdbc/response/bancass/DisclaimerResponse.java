package com.optima.oraclejdbc.response.bancass;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntitySerialize;

public class DisclaimerResponse implements Serializable {

	private static final long serialVersionUID = -3064373707758327603L;

	@NISPEntitySerialize(field = "ID")
	private Long id;
	
	@NISPEntitySerialize(field = "DISCLAIMER_NO")
	private String disclaimerNo;
	
	@NISPEntitySerialize(field = "DISCLAIMER_DESCRIPTION")
	private String disclaimerDescription;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDisclaimerNo() {
		return disclaimerNo;
	}

	public void setDisclaimerNo(String disclaimerNo) {
		this.disclaimerNo = disclaimerNo;
	}

	public String getDisclaimerDescription() {
		return disclaimerDescription;
	}

	public void setDisclaimerDescription(String disclaimerDescription) {
		this.disclaimerDescription = disclaimerDescription;
	}
}
