package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityDateFomat;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName="CC_STATEMENT")
public class CCStatement extends CCParentModel implements Serializable {

	private static final long serialVersionUID = 4691910375163855614L;

	@NISPEntity(columnName="ID")
	@NISPEntityId
	private Long id;
	
	@NISPEntity(columnName = "RECORD_TYPE")
	@Position(start = 0, length = 2)
	private String recordType;
	
	@NISPEntity(columnName = "FILE_NAME")
	@Position(start = 2, length = 10)
	private String fileName;
	
	@NISPEntity(columnName = "GENERATION_DATE")
	@NISPEntityDateFomat(format="ddMMyyyy")
	@Position(start = 12, length = 8)
	private Date generationDate;
	
	@NISPEntity(columnName = "PROCESSING_DATE")
	@NISPEntityDateFomat(format="ddMMyyyy")
	@Position(start = 20, length = 8)
	private Date processingDate;
	
	@NISPEntity(columnName = "PRODUCT_CODE")
	@Position(start = 28, length = 6)
	private String productCode;
	
	@NISPEntity(columnName = "GENERATION_TIME")
	@Position(start = 34, length = 6)
	private String generationTime;
	
	@NISPEntity(columnName = "CREATED_DATE", create = true)
	private Date createdDate;
	
	@NISPEntity(columnName = "UPDATED_DATE", update = true)
	private Date updatedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getGenerationDate() {
		return generationDate;
	}

	public void setGenerationDate(Date generationDate) {
		this.generationDate = generationDate;
	}

	public Date getProcessingDate() {
		return processingDate;
	}

	public void setProcessingDate(Date processingDate) {
		this.processingDate = processingDate;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getGenerationTime() {
		return generationTime;
	}

	public void setGenerationTime(String generationTime) {
		this.generationTime = generationTime;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}
