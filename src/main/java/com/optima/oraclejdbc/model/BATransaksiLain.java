package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityDateFomat;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.NISPEntityNumberFormat;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName = "BA_TRANSAKSI_LAIN")
public class BATransaksiLain extends BAParentModel implements BAChildModel, Serializable {

	private static final long serialVersionUID = 2396597464068166234L;

	@NISPEntity(columnName = "ID")
	@NISPEntityId
	private Long id;
	
	@NISPEntity(columnName = "BA_TRANSACTION_SUMMARY_ID")
	private Long baTransactionSummaryId;
	
	@NISPEntity(columnName = "RECORD_TYPE")
	@Position(start = 0, length = 2)
	private String recordType; //TL
	
	@NISPEntity(columnName = "DETAIL_ID")
	@Position(start = 2, length = 5)
	private int detailId;
	
	@NISPEntity(columnName = "TRANSAKSI_LAIN")
	@Position(start = 7, length = 100)
	private String transaksiLain;
	
	@NISPEntity(columnName = "TANGGAL")
	@NISPEntityDateFomat(format = "yyyyMMdd")
	@Position(start = 107, length = 8)
	private Date tanggal;
	
	@NISPEntity(columnName = "JUMLAH_DANA")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 115, length = 20)
	private double jumlahDana;
	
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

	public String getTransaksiLain() {
		return transaksiLain;
	}

	public void setTransaksiLain(String transaksiLain) {
		this.transaksiLain = transaksiLain;
	}

	public Date getTanggal() {
		return tanggal;
	}

	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}

	public double getJumlahDana() {
		return jumlahDana;
	}

	public void setJumlahDana(double jumlahDana) {
		this.jumlahDana = jumlahDana;
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
