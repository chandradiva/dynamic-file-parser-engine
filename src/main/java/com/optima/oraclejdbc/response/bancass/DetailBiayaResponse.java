package com.optima.oraclejdbc.response.bancass;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.optima.oraclejdbc.annotations.NISPEntitySerialize;

public class DetailBiayaResponse implements Serializable {

	private static final long serialVersionUID = 9084964867830877455L;

	@NISPEntitySerialize(field = "ID")
	private Long id;
	
	@NISPEntitySerialize(field = "DETAIL_ID")
	private int detailId;
	
	@NISPEntitySerialize(field = "BIAYA")
	private String biaya;
	
	@NISPEntitySerialize(field = "TANGGAL")
	private String tanggal;
	
	@NISPEntitySerialize(field = "JUMLAH_BIAYA")
	private double jumlahBiaya;
	
	@NISPEntitySerialize(field = "HARGA_UNIT")
	private double hargaUnit;
	
	@NISPEntitySerialize(field = "TOTAL_UNIT")
	private double totalUnit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getDetailId() {
		return detailId;
	}

	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}

	public String getBiaya() {
		return biaya;
	}

	public void setBiaya(String biaya) {
		this.biaya = biaya;
	}

	public String getTanggal() {
		return tanggal;
	}

	public void setTanggal(String tanggal) {
		this.tanggal = tanggal;
	}

	public double getJumlahBiaya() {
		return jumlahBiaya;
	}

	public void setJumlahBiaya(double jumlahBiaya) {
		this.jumlahBiaya = jumlahBiaya;
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
}
