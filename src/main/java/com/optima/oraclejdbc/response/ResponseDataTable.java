package com.optima.oraclejdbc.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_NULL)
public class ResponseDataTable implements Serializable {

	private static final long serialVersionUID = -2118693136965059644L;
	private Integer resultCode;
	private String message;
	private Object data;
	private Integer recordsFiltered;
	private Integer recordsTotal;
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
	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public Integer getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public List<String> getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(List<String> errMsg) {
		this.errMsg = errMsg;
	}
	public Integer getTotalAllData() {
		return totalAllData;
	}
	public void setTotalAllData(Integer totalAllData) {
		this.totalAllData = totalAllData;
	}
}
