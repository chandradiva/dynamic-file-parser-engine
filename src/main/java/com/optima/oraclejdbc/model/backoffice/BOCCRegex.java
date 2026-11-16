package com.optima.oraclejdbc.model.backoffice;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;

@NISPEntity(tableName = "BO_TBL_CC_REGEX")
public class BOCCRegex implements Serializable {

	private static final long serialVersionUID = 5568539988037212601L;

	@NISPEntityId
	@NISPEntity(columnName = "ID")
	private Long id;
	
	@NISPEntity(columnName = "REGEX")
	private String regex;
	
	@NISPEntity(columnName = "PRODUCT")
	private String product;
	
	@NISPEntity(columnName = "IMAGE_URL")
	private String imageUrl;
	
	@NISPEntity(columnName = "STARTS_WITH")
	private String startsWith;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getStartsWith() {
		return startsWith;
	}

	public void setStartsWith(String startsWith) {
		this.startsWith = startsWith;
	}
}
