package com.optima.oraclejdbc.model;

import java.io.Serializable;

import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_customerinfo.AddressSubDetail_type0;
import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.cons.CommonCons;

@NISPEntity(tableName="TBL_CUSTOMER_ADDRESS", dbSchema=CommonCons.DB_SCHEMA)
public class CustomerAddress implements Serializable{
	
	private static final long serialVersionUID = -8612277000460671142L;

	@NISPEntity(columnName="ID")
	private Long id;
	
	@NISPEntityId
	@NISPEntity(columnName="CUSTOMER_INFO_ID")
	private Long customerInfoId;
	
	@NISPEntity(columnName="ADDRESS_1")
	private String address1;
	
	@NISPEntity(columnName="ADDRESS_2")
	private String address2;
	
	@NISPEntity(columnName="ADDRESS_3")
	private String address3;
	
	@NISPEntity(columnName="ADDRESS_4")
	private String address4;
	
	@NISPEntity(columnName="SOURCE")
	private Integer source;
	
	@NISPEntity(columnName="IS_DEFAULT")
	private Integer isDefault;
	
	@NISPEntityId
	@NISPEntity(columnName="SEQ_NUM")
	private String seqNum;

	public CustomerAddress(){}
	
	public CustomerAddress(AddressSubDetail_type0 address) {
		// from tibco
		source = 0;
		address1 = address.getAddressline1();
		address2 = address.getAddressline2();
		address3 = address.getAddressline3();
		address4 = address.getAddressline4();
		seqNum = address.getAddressSeqNo();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerInfoId() {
		return customerInfoId;
	}

	public void setCustomerInfoId(Long customerInfoId) {
		this.customerInfoId = customerInfoId;
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

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
}
