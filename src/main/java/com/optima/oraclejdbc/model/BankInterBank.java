package com.optima.oraclejdbc.model;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName="BANK_INTERBANK")
public class BankInterBank implements Serializable{

	private static final long serialVersionUID = 687323949946273307L;

	@Position(start = 0, length = 40)
	@NISPEntityId
	@NISPEntity(columnName="RECORD_ID")
	private String recordId;
	
	@Position(start = 40, length = 4)
	@NISPEntity(columnName="BANK_ID")
	private String bankId;
	
	@Position(start = 44, length = 50)
	@NISPEntity(columnName="BANK_NAME")
	private String bankName;
	
	@Position(start = 94, length = 50)
	@NISPEntity(columnName="BRANCH_NAME")
	private String branchName;
	
	@Position(start = 144, length = 60)
	@NISPEntity(columnName="ADDRESS")
	private String address;
	
	@Position(start = 204, length = 4)
	@NISPEntity(columnName="CITY_ID")
	private String cityId;
	
	@Position(start = 208, length = 4)
	@NISPEntity(columnName="PROVINCE_ID")
	private String provinceId;
	
	@Position(start = 212, length = 16)
	@NISPEntity(columnName="CLEARING_NETWORK_ID")
	private String clearingNetworkId;
	
	@Position(start = 228, length = 12)
	@NISPEntity(columnName="RTGS_MEMBER_CODE")
	private String rtgsMemberCode;
	
	@Position(start = 240, length = 50)
	@NISPEntity(columnName="BLK_BANK_NAME")
	private String blkBankName;
	
	@Position(start = 290, length = 50)
	@NISPEntity(columnName="BLK_BANK_CITY")
	private String blkBankCity;
	
	@Position(start = 340, length = 12)
	@NISPEntity(columnName="BLK_BANK_RTGS_CODE")
	private String blkBanRtgsCode;
	
	@Position(start = 352, length = 1)
	@NISPEntity(columnName="MAINTENANCE_FLAG")
	private String maintenanceFlag;

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getClearingNetworkId() {
		return clearingNetworkId;
	}

	public void setClearingNetworkId(String clearingNetworkId) {
		this.clearingNetworkId = clearingNetworkId;
	}

	public String getRtgsMemberCode() {
		return rtgsMemberCode;
	}

	public void setRtgsMemberCode(String rtgsMemberCode) {
		this.rtgsMemberCode = rtgsMemberCode;
	}

	public String getBlkBankName() {
		return blkBankName;
	}

	public void setBlkBankName(String blkBankName) {
		this.blkBankName = blkBankName;
	}

	public String getBlkBankCity() {
		return blkBankCity;
	}

	public void setBlkBankCity(String blkBankCity) {
		this.blkBankCity = blkBankCity;
	}

	public String getBlkBanRtgsCode() {
		return blkBanRtgsCode;
	}

	public void setBlkBanRtgsCode(String blkBanRtgsCode) {
		this.blkBanRtgsCode = blkBanRtgsCode;
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
