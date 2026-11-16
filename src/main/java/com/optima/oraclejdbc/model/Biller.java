package com.optima.oraclejdbc.model;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName = "BILLER")
public class Biller implements Serializable {

	private static final long serialVersionUID = 6315704764964626090L;

	@Position(start = 0, length = 40)
	@NISPEntity(columnName = "RECORD_ID")
	@NISPEntityId
	private String recordId;
	
	@Position(start = 40, length = 3)
	@NISPEntity(columnName = "GROUP_SEQUENCE")
	private String groupSequence;
	
	@Position(start = 43, length = 20)
	@NISPEntity(columnName = "GROUP_NAME_EN")
	private String groupNameEn;
	
	@Position(start = 63, length = 20)
	@NISPEntity(columnName = "GROUP_NAME_ID")
	private String groupNameId;
	
	@Position(start = 83, length = 3)
	@NISPEntity(columnName = "BILLER_SEQUENCE")
	private String billerSequence;
	
	@Position(start = 86, length = 15)
	@NISPEntity(columnName = "BILLER_CODE")
	private String billerCode;
	
	@Position(start = 101, length = 50)
	@NISPEntity(columnName = "BILLER_NAME_EN")
	private String billerNameEn;
	
	@Position(start = 151, length = 50)
	@NISPEntity(columnName = "BILLER_NAME_ID")
	private String billerNameId;
	
	@Position(start = 201, length = 10)
	@NISPEntity(columnName = "BILLER_TYPE")
	private String billerType;
	
	@Position(start = 211, length = 1)
	@NISPEntity(columnName = "FLOW_TYPE")
	private String flowType;
	
	@Position(start = 212, length = 1)
	@NISPEntity(columnName = "SOA_TYPE")
	private String soaType;
	
	@Position(start = 213, length = 1)
	@NISPEntity(columnName = "RECURRING_BIT")
	private String recurringBit;
	
	@Position(start = 214, length = 1)
	@NISPEntity(columnName = "FUTURE_BIT")
	private String futureBit;
	
	@Position(start = 215, length = 1)
	@NISPEntity(columnName = "MAINTENANCE_FLAG")
	private String maintenanceFlag;

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getGroupSequence() {
		return groupSequence;
	}

	public void setGroupSequence(String groupSequence) {
		this.groupSequence = groupSequence;
	}

	public String getGroupNameEn() {
		return groupNameEn;
	}

	public void setGroupNameEn(String groupNameEn) {
		this.groupNameEn = groupNameEn;
	}

	public String getGroupNameId() {
		return groupNameId;
	}

	public void setGroupNameId(String groupNameId) {
		this.groupNameId = groupNameId;
	}

	public String getBillerSequence() {
		return billerSequence;
	}

	public void setBillerSequence(String billerSequence) {
		this.billerSequence = billerSequence;
	}

	public String getBillerCode() {
		return billerCode;
	}

	public void setBillerCode(String billerCode) {
		this.billerCode = billerCode;
	}

	public String getBillerNameEn() {
		return billerNameEn;
	}

	public void setBillerNameEn(String billerNameEn) {
		this.billerNameEn = billerNameEn;
	}

	public String getBillerNameId() {
		return billerNameId;
	}

	public void setBillerNameId(String billerNameId) {
		this.billerNameId = billerNameId;
	}

	public String getBillerType() {
		return billerType;
	}

	public void setBillerType(String billerType) {
		this.billerType = billerType;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getSoaType() {
		return soaType;
	}

	public void setSoaType(String soaType) {
		this.soaType = soaType;
	}

	public String getRecurringBit() {
		return recurringBit;
	}

	public void setRecurringBit(String recurringBit) {
		this.recurringBit = recurringBit;
	}

	public String getFutureBit() {
		return futureBit;
	}

	public void setFutureBit(String futureBit) {
		this.futureBit = futureBit;
	}

	public String getMaintenanceFlag() {
		return maintenanceFlag;
	}

	public void setMaintenanceFlag(String maintenanceFlag) {
		this.maintenanceFlag = maintenanceFlag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
