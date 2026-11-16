package com.optima.oraclejdbc.model;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.utils.CommonUtils;

@NISPEntity(tableName="TBL_ACCOUNT_LIST")
public class AccountList implements Serializable{
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	private static final long serialVersionUID = -1583014775025938173L;

	@NISPEntityId
	@NISPEntity(columnName="ID")
	private Long id;
	
	@NISPEntity(columnName="CUSTOMER_INFO_ID")
	private Long customerInfoId;
	
	@NISPEntity(columnName="ACCOUNT_NO")
	private String accountNumber;
	
	@NISPEntity(columnName="ACCOUNT_TYPE")
	private Character accountType;
	
	@NISPEntity(columnName="CURRENCY")
	private String currency;
	
	@NISPEntity(columnName="BRANCH_CODE")
	private String branchCode;
	
	@NISPEntity(columnName="MCBIT")
	private Integer mcBit;
	
	@NISPEntity(columnName="PRODUCT_ID")
	private Long productId;
	
	private MappingProduct product;
	
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

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Character getAccountType() {
		return accountType;
	}

	public void setAccountType(Character accountType) {
		this.accountType = accountType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public MappingProduct getProduct() {
		return product;
	}

	public void setProduct(MappingProduct product) {
		this.product = product;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Integer getMcBit() {
		return mcBit;
	}

	public void setMcBit(Integer mcBit) {
		this.mcBit = mcBit;
	}	
}
