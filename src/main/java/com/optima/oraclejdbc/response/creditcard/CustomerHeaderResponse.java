package com.optima.oraclejdbc.response.creditcard;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntitySerialize;

public class CustomerHeaderResponse implements Serializable{
	private static final long serialVersionUID = 513762021634324133L;

	@NISPEntitySerialize(field="ID")
	private Long id;
	
	@NISPEntitySerialize(field="CUSTOMER_FULLNAME")
	private String fullname;
	
	@NISPEntitySerialize(field="MAILING_ADDRESS_LINE_1")
	private String address1;
	
	@NISPEntitySerialize(field="MAILING_ADDRESS_LINE_2")
	private String address2;
	
	@NISPEntitySerialize(field="MAILING_ADDRESS_LINE_3")
	private String address3;
	
	@NISPEntitySerialize(field="MAILING_ADDRESS_LINE_4")
	private String address4;
	
	@NISPEntitySerialize(field="MAILING_ADDRESS_POSTAL_CODE")
	private String postalCode;

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
