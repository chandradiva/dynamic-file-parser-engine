package com.optima.oraclejdbc.response.exchangerate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.optima.oraclejdbc.model.KursInfo;

public class ExchangeRateResponse implements Serializable{

	private static final long serialVersionUID = 604044349307296135L;

	@JsonFormat(pattern="EEEE, MMMM dd, yyyy - hh:mm:ss aa", timezone="Asia/Jakarta")
	private Date lastUpdate;
	
	private List<KursInfo> data;
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public List<KursInfo> getData() {
		return data;
	}
	public void setData(List<KursInfo> data) {
		this.data = data;
	}
	
	
}
