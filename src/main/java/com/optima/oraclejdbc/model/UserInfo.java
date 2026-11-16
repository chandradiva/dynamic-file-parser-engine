package com.optima.oraclejdbc.model;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_customerinfo.AddressSubDetail_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_customerinfo.RESConsumer_type0;
import com.optima.oraclejdbc.utils.CommonUtils;

public class UserInfo implements Serializable{
	private static final long serialVersionUID = 7303329764426016314L;
	private String cifKey;
	private Date dob;
	private String fullname;
	private String shortName;
	private String email;
	private List<CustomerAddress> customerAddresses;
	private Integer requestPerMonth;
	
	public UserInfo(RESConsumer_type0 resConsumer_type0){
		cifKey = resConsumer_type0.getCIFNumber();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			dob = sdf.parse(resConsumer_type0.getDOB());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fullname = resConsumer_type0.getFullName();
		shortName = resConsumer_type0.getShortName();
		email = resConsumer_type0.getEmailAddr();
		
		customerAddresses = new ArrayList<CustomerAddress>();
		for(AddressSubDetail_type0 address : resConsumer_type0.getAddressDetail().getAddressSubDetail()){
			CustomerAddress ca = new CustomerAddress(address);
			customerAddresses.add(ca);
		}
		
	}
	
	public String getCifKey() {
		return cifKey;
	}
	public void setCifKey(String cifKey) {
		this.cifKey = CommonUtils.normalizeCifKey(cifKey);
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public List<CustomerAddress> getCustomerAddresses() {
		return customerAddresses;
	}

	public void setCustomerAddresses(List<CustomerAddress> customerAddresses) {
		this.customerAddresses = customerAddresses;
	}

	public Integer getRequestPerMonth() {
		return requestPerMonth;
	}

	public void setRequestPerMonth(Integer requestPerMonth) {
		this.requestPerMonth = requestPerMonth;
	}
	
	
}
