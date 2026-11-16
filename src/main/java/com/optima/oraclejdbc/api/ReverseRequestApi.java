package com.optima.oraclejdbc.api;

import java.util.List;

import com.optima.oraclejdbc.utils.CommonUtils;

public class ReverseRequestApi {

	private String cifNo;
	private String alamat;
	private List<ReverseRequestDetailApi> periods;
	private int perMonth;
	
	public String getCifNo() {
		return cifNo;
	}
	public void setCifNo(String cifNo) {
		this.cifNo = CommonUtils.normalizeCifKey(cifNo);
	}
	public String getAlamat() {
		return alamat;
	}
	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
	public List<ReverseRequestDetailApi> getPeriods() {
		return periods;
	}
	public void setPeriods(List<ReverseRequestDetailApi> periods) {
		this.periods = periods;
	}
	public int getPerMonth() {
		return perMonth;
	}
	public void setPerMonth(int perMonth) {
		this.perMonth = perMonth;
	}
}
