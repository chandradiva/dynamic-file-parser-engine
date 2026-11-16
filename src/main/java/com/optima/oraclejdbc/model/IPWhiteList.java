package com.optima.oraclejdbc.model;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntitySerialize;

public class IPWhiteList implements Serializable{
	private static final long serialVersionUID = -3325647222190201821L;
	@NISPEntitySerialize(field="IP")
	private String ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
