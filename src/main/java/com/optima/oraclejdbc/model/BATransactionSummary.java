package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityDateFomat;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.NISPEntityNumberFormat;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName = "BA_TRANSACTION_SUMMARY")
public class BATransactionSummary extends BAParentModel implements Serializable {

	private static final long serialVersionUID = 3016411101404940160L;
	
	@NISPEntity(columnName = "ID")
	@NISPEntityId
	private Long id;
	
	@NISPEntity(columnName = "RECORD_TYPE")
	@Position(start = 0, length = 2)
	private String recordType; //SH
	
	@NISPEntity(columnName = "NO_POLIS")
	@Position(start = 2, length = 30)
	private String noPolis;
	
	@NISPEntity(columnName = "TGL_MULAI_POLIS")
	@NISPEntityDateFomat(format = "yyyyMMdd")
	@Position(start = 32, length = 8)
	private Date tglMulaiPolis;
	
	@NISPEntity(columnName = "MATA_UANG")
	@Position(start = 40, length = 4)
	private String mataUang;
	
	@NISPEntity(columnName = "PRODUK")
	@Position(start = 44, length = 70)
	private String produk;
	
	@NISPEntity(columnName = "NAMA_TERTANGGUNG")
	@Position(start = 114, length = 105)
	private String namaTertanggung;
	
	@NISPEntity(columnName = "UANG_PERTANGGUNGAN")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 219, length = 17)
	private double uangPertanggungan;
	
	@NISPEntity(columnName = "NAMA_NASABAH")
	@Position(start = 236, length = 105)
	private String namaNasabah;
	
	@NISPEntity(columnName = "ALAMAT_NASABAH_1")
	@Position(start = 341, length = 50)
	private String alamatNasabah1;
	
	@NISPEntity(columnName = "ALAMAT_NASABAH_2")
	@Position(start = 391, length = 50)
	private String alamatNasabah2;
	
	@NISPEntity(columnName = "ALAMAT_NASABAH_3")
	@Position(start = 441, length = 50)
	private String alamatNasabah3;
	
	@NISPEntity(columnName = "ALAMAT_NASABAH_4")
	@Position(start = 491, length = 50)
	private String alamatNasabah4;
	
	@NISPEntity(columnName = "JENIS_DANA_INVESTASI")
	@Position(start = 541, length = 50)
	private String jenisDanaInvestasi;
	
	@NISPEntity(columnName = "TOTAL_UNIT")
	@NISPEntityNumberFormat(scale = 4)
	@Position(start = 591, length = 20)
	private double totalUnit;
	
	@NISPEntity(columnName = "HARGA_UNIT")
	@NISPEntityNumberFormat(scale = 4)
	@Position(start = 611, length = 20)
	private double hargaUnit;
	
	@NISPEntity(columnName = "NILAI_UNIT")
	@NISPEntityNumberFormat(scale = 4)
	@Position(start = 631, length = 20)
	private double nilaiUnit;
	
	@NISPEntity(columnName = "TANGGAL_UNIT")
	@NISPEntityDateFomat(format = "yyyyMMdd")
	@Position(start = 651, length = 8)
	private Date tanggalUnit;
	
	@NISPEntity(columnName = "FREKUENSI_PEMBAYARAN")
	@Position(start = 659, length = 20)
	private String frekuensiPembayaran;
	
	@NISPEntity(columnName = "JUMLAH_PREMI")
	@NISPEntityNumberFormat(scale = 4)
	@Position(start = 679, length = 20)
	private double jumlahPremi;
	
	@NISPEntity(columnName = "ALOKASI")
	@NISPEntityNumberFormat(scale = 4)
	@Position(start = 699, length = 20)
	private double alokasi;
	
	@NISPEntity(columnName = "UNIT_LINK")
	@Position(start = 719, length = 1)
	private int unitLink;
	
	@NISPEntity(columnName = "VENDOR_ID")
	@Position(start = 720, length = 5)
	private int vendorId;
	
	@NISPEntity(columnName = "STATUS_POLIS")
	@Position(start = 725, length = 30)
	private String statusPolis;
	
	@NISPEntity(columnName = "CREATED_DATE", create = true)
	private Date createdDate;
	
	@NISPEntity(columnName = "UPDATED_DATE", update = true)
	private Date updatedDate;
	
	@NISPEntity(columnName = "SOURCE")
	private String source;

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

	public String getNoPolis() {
		return noPolis;
	}

	public void setNoPolis(String noPolis) {
		this.noPolis = noPolis;
	}

	public Date getTglMulaiPolis() {
		return tglMulaiPolis;
	}

	public void setTglMulaiPolis(Date tglMulaiPolis) {
		this.tglMulaiPolis = tglMulaiPolis;
	}

	public String getMataUang() {
		return mataUang;
	}

	public void setMataUang(String mataUang) {
		this.mataUang = mataUang;
	}

	public String getProduk() {
		return produk;
	}

	public void setProduk(String produk) {
		this.produk = produk;
	}

	public String getNamaTertanggung() {
		return namaTertanggung;
	}

	public void setNamaTertanggung(String namaTertanggung) {
		this.namaTertanggung = namaTertanggung;
	}

	public double getUangPertanggungan() {
		return uangPertanggungan;
	}

	public void setUangPertanggungan(double uangPertanggungan) {
		this.uangPertanggungan = uangPertanggungan;
	}

	public String getNamaNasabah() {
		return namaNasabah;
	}

	public void setNamaNasabah(String namaNasabah) {
		this.namaNasabah = namaNasabah;
	}

	public String getAlamatNasabah1() {
		return alamatNasabah1;
	}

	public void setAlamatNasabah1(String alamatNasabah1) {
		this.alamatNasabah1 = alamatNasabah1;
	}

	public String getAlamatNasabah2() {
		return alamatNasabah2;
	}

	public void setAlamatNasabah2(String alamatNasabah2) {
		this.alamatNasabah2 = alamatNasabah2;
	}

	public String getAlamatNasabah3() {
		return alamatNasabah3;
	}

	public void setAlamatNasabah3(String alamatNasabah3) {
		this.alamatNasabah3 = alamatNasabah3;
	}

	public String getAlamatNasabah4() {
		return alamatNasabah4;
	}

	public void setAlamatNasabah4(String alamatNasabah4) {
		this.alamatNasabah4 = alamatNasabah4;
	}

	public String getJenisDanaInvestasi() {
		return jenisDanaInvestasi;
	}

	public void setJenisDanaInvestasi(String jenisDanaInvestasi) {
		this.jenisDanaInvestasi = jenisDanaInvestasi;
	}

	public double getTotalUnit() {
		return totalUnit;
	}

	public void setTotalUnit(double totalUnit) {
		this.totalUnit = totalUnit;
	}

	public double getHargaUnit() {
		return hargaUnit;
	}

	public void setHargaUnit(double hargaUnit) {
		this.hargaUnit = hargaUnit;
	}

	public double getNilaiUnit() {
		return nilaiUnit;
	}

	public void setNilaiUnit(double nilaiUnit) {
		this.nilaiUnit = nilaiUnit;
	}

	public Date getTanggalUnit() {
		return tanggalUnit;
	}

	public void setTanggalUnit(Date tanggalUnit) {
		this.tanggalUnit = tanggalUnit;
	}

	public String getFrekuensiPembayaran() {
		return frekuensiPembayaran;
	}

	public void setFrekuensiPembayaran(String frekuensiPembayaran) {
		this.frekuensiPembayaran = frekuensiPembayaran;
	}

	public double getJumlahPremi() {
		return jumlahPremi;
	}

	public void setJumlahPremi(double jumlahPremi) {
		this.jumlahPremi = jumlahPremi;
	}

	public double getAlokasi() {
		return alokasi;
	}

	public void setAlokasi(double alokasi) {
		this.alokasi = alokasi;
	}

	public int getUnitLink() {
		return unitLink;
	}

	public void setUnitLink(int unitLink) {
		this.unitLink = unitLink;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public String getStatusPolis() {
		return statusPolis;
	}

	public void setStatusPolis(String statusPolis) {
		this.statusPolis = statusPolis;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
