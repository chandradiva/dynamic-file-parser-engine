package com.optima.oraclejdbc.api;

import java.util.List;

public class ReqHardcopyApi {

	private String password;
	private List<ReqHardcopyDetailsApi> details;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<ReqHardcopyDetailsApi> getDetails() {
		return details;
	}

	public void setDetails(List<ReqHardcopyDetailsApi> details) {
		this.details = details;
	}
}
