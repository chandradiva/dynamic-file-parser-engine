package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;

@NISPEntity(tableName="TBL_SOAP_MESSAGE_LOG")
public class SoapMessageLog implements Serializable{
	private static final long serialVersionUID = 3282499986814428073L;

	@NISPEntityId
	@NISPEntity(columnName="ID")
	private Long id;
	
	@NISPEntity(columnName="XML_REQUEST")
	private String xmlRequest;
	
	@NISPEntity(columnName="XML_RESPONSE")
	private String xmlResponse;
	
	@NISPEntity(columnName="REQUEST_DATE")
	private Date requestDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getXmlRequest() {
		return xmlRequest;
	}

	public void setXmlRequest(String xmlRequest) {
		this.xmlRequest = xmlRequest;
	}

	public String getXmlResponse() {
		return xmlResponse;
	}

	public void setXmlResponse(String xmlResponse) {
		this.xmlResponse = xmlResponse;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	
}
