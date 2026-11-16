package com.optima.oraclejdbc.model.backoffice;

import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.utils.CommonUtils;

@NISPEntity(tableName = "BO_TBL_SEND_EMAIL")
public class BOSendEmail {

	@NISPEntityId
	@NISPEntity(columnName = "ID")
	private Long id;
	
	@NISPEntity(columnName = "CIF_KEY")
	private String cifKey;
	
	@NISPEntity(columnName = "ACCOUNT_NUMBER")
	private String accountNumber;
	
	@NISPEntity(columnName = "TGL_CETAK")
	private Date tglCetak;
	
	@NISPEntity(columnName = "STATUS")
	private int status;
	
	@NISPEntity(columnName = "TGL_KIRIM")
	private Date tglKirim;
	
	@NISPEntity(columnName = "FILENAME")
	private String filename;
	
	@NISPEntity(columnName = "SENDER")
	private int sender;
	
	@NISPEntity(columnName = "PERIODE")
	private String periode;
	
	@NISPEntity(columnName = "CATEGORY")
	private int category;

	@NISPEntity(columnName="NAMA_REKENING")
	private String namaRekening;
	
	@NISPEntity(columnName="PRODUCT_NAME")
	private String productName;
	
	@NISPEntity(columnName="IS_PASSWORD_DEFAULT")
	private Integer isPasswordDefault;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCifKey() {
		return cifKey;
	}

	public void setCifKey(String cifKey) {
		this.cifKey = CommonUtils.normalizeCifKey(cifKey);
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Date getTglCetak() {
		return tglCetak;
	}

	public void setTglCetak(Date tglCetak) {
		this.tglCetak = tglCetak;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getTglKirim() {
		return tglKirim;
	}

	public void setTglKirim(Date tglKirim) {
		this.tglKirim = tglKirim;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getNamaRekening() {
		return namaRekening;
	}

	public void setNamaRekening(String namaRekening) {
		this.namaRekening = namaRekening;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getIsPasswordDefault() {
		return isPasswordDefault;
	}

	public void setIsPasswordDefault(Integer isPasswordDefault) {
		this.isPasswordDefault = isPasswordDefault;
	}
	
	
}
