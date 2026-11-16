package com.optima.oraclejdbc.response.estatement;

import java.util.List;

public class ReqHardcopyProcessResponse {

	private String filename;
	private String batchId;
	private List<Long> hcReqIds;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public List<Long> getHcReqIds() {
		return hcReqIds;
	}
	public void setHcReqIds(List<Long> hcReqIds) {
		this.hcReqIds = hcReqIds;
	}
}
