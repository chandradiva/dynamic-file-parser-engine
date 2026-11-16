package com.optima.oraclejdbc.model;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;

@NISPEntity(tableName="TBL_MAPPING_PRODUCT")
public class MappingProduct implements Serializable{

	private static final long serialVersionUID = -7895751852595525L;

	@NISPEntityId
	@NISPEntity(columnName="ID")
	private Long id;
	
	@NISPEntity(columnName="PRODUCT_CODE")
	private String productCode;
	
	@NISPEntity(columnName="PRODUCT_NAME")
	private String productName;
	
	@NISPEntity(columnName="PRODUCT_TYPE")
	private Integer productType;
	
	@NISPEntity(columnName="PRODUCT_GROUP")
	private Integer productGroup;
	
	@NISPEntity(columnName="PRODUCT_CATEGORY")
	private Integer productCategory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Integer getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(Integer productGroup) {
		this.productGroup = productGroup;
	}

	public Integer getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(Integer productCategory) {
		this.productCategory = productCategory;
	}
	
	
}
