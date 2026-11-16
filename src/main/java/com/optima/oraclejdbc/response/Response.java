package com.optima.oraclejdbc.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_NULL)
public class Response implements Serializable{
	private static final long serialVersionUID = 4072378572001350955L;
	
	private Integer resultCode;
	private String message;
	private Object data;
	private List<String> errMsg;
	private Integer totalAllData;
	
	public Integer getResultCode() {
		return resultCode;
	}
	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Integer getTotalAllData() {
		return totalAllData;
	}
	public void setTotalAllData(Integer totalAllData) {
		this.totalAllData = totalAllData;
	}
	public List<String> getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(List<String> errMsg) {
		this.errMsg = errMsg;
	}		
}
