package com.optima.oraclejdbc.response.bancass;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.optima.oraclejdbc.annotations.NISPEntitySerialize;

public class DetailPremiResponse implements Serializable{

	private static final long serialVersionUID = 1022005409687595071L;

	@NISPEntitySerialize(field = "ID")
	private Long id;
	
	@NISPEntitySerialize(field = "DETAIL_ID")
	private int detailId;
	
	@NISPEntitySerialize(field = "PEMBAYARAN_PREMI")
	private String pembayaranPremi;
	
	@NISPEntitySerialize(field = "TANGGAL")
	private String tanggal;
	
	@NISPEntitySerialize(field = "JUMLAH_PREMI")
	private double jumlahPremi;
	
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

	public String getPembayaranPremi() {
		return pembayaranPremi;
	}

	public void setPembayaranPremi(String pembayaranPremi) {
		this.pembayaranPremi = pembayaranPremi;
	}

	public String getTanggal() {
		return tanggal;
	}

	public void setTanggal(String tanggal) {
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
}
