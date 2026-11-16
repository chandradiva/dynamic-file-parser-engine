package com.optima.oraclejdbc.model;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName = "BANK_SWIFT")
public class BankSwift implements Serializable {

	private static final long serialVersionUID = 4989552287391047323L;

	@Position(start = 282, length = 12)
	@NISPEntity(columnName = "SWIFT_CODE")
	@NISPEntityId
	private String swiftCode;
	
	@Position(start = 0, length = 50)
	@NISPEntity(columnName = "BANK_NAME")
	private String bankName;
	
	@Position(start = 50, length = 60)
	@NISPEntity(columnName = "ADDRESS_1")
	private String address1;
	
	@Position(start = 110, length = 60)
	@NISPEntity(columnName = "ADDRESS_2")
	private String address2;
	
	@Position(start = 170, length = 60)
	@NISPEntity(columnName = "ADDRESS_3")
	private String address3;
	
	@Position(start = 230, length = 2)
	@NISPEntity(columnName = "COUNTRY_CODE")
	private String countryCode;
	
	@Position(start = 232, length = 50)
	@NISPEntity(columnName = "CITY_NAME")
	private String cityName;
	
	@Position(start = 294, length = 1)
	@NISPEntity(columnName = "MAINTENANCE_FLAG")
	private String maintenanceFlag;

	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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
