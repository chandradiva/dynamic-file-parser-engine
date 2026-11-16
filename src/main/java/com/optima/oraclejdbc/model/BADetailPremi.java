package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityDateFomat;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.NISPEntityNumberFormat;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName = "BA_DETAIL_PREMI")
public class BADetailPremi extends BAParentModel implements BAChildModel, Serializable {

	private static final long serialVersionUID = 3441904404793204235L;
	
	@NISPEntity(columnName = "ID")
	@NISPEntityId
	private Long id;
	
	@NISPEntity(columnName = "BA_TRANSACTION_SUMMARY_ID")
	private Long baTransactionSummaryId;
	
	@NISPEntity(columnName = "RECORD_TYPE")
	@Position(start = 0, length = 2)
	private String recordType; //DP
	
	@NISPEntity(columnName = "DETAIL_ID")
	@Position(start = 2, length = 5)
	private int detailId;
	
	@NISPEntity(columnName = "PEMBAYARAN_PREMI")
	@Position(start = 7, length = 100)
	private String pembayaranPremi;
	
	@NISPEntity(columnName = "TANGGAL")
	@NISPEntityDateFomat(format = "yyyyMMdd")
	@Position(start = 107, length = 8)
	private Date tanggal;
	
	@NISPEntity(columnName = "JUMLAH_PREMI")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 115, length = 20)
	private double jumlahPremi;
	
	@NISPEntity(columnName = "HARGA_UNIT")
	@NISPEntityNumberFormat(scale = 4)
	@Position(start = 135, length = 20)
	private double hargaUnit;
	
	@NISPEntity(columnName = "TOTAL_UNIT")
	@NISPEntityNumberFormat(scale = 4)
	@Position(start = 155, length = 20)
	private double totalUnit;
	
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

	public Long getBaTransactionSummaryId() {
		return baTransactionSummaryId;
	}

	@Override
	public void setBaTransactionSummaryId(Long baTransactionSummaryId) {
		this.baTransactionSummaryId = baTransactionSummaryId;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public int getDetailId() {
		return detailId;
	}

	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}

	public String getPembayaranPremi() {
		return pembayaranPremi;
	}

	public void setPembayaranPremi(String pembayaranPremi) {
		this.pembayaranPremi = pembayaranPremi;
	}

	public Date getTanggal() {
		return tanggal;
	}

	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}

	public double getJumlahPremi() {
		return jumlahPremi;
	}

	public void setJumlahPremi(double jumlahPremi) {
		this.jumlahPremi = jumlahPremi;
	}

	public double getHargaUnit() {
		return hargaUnit;
	}

	public void setHargaUnit(double hargaUnit) {
		this.hargaUnit = hargaUnit;
	}

	public double getTotalUnit() {
		return totalUnit;
	}

	public void setTotalUnit(double totalUnit) {
		this.totalUnit = totalUnit;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	@Override
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}
