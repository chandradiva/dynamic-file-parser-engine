package com.optima.oraclejdbc.model.estatement;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;

@NISPEntity(tableName = "ES_MC_BILINGUAL_HISTORY")
public class ESMCBilingualHistory extends DDIModel implements Serializable {

	private static final long serialVersionUID = 9116751999698186869L;

	@NISPEntityId
	@NISPEntity(columnName = "ID")
	private Long id; //DDI236MBLS
	
	@NISPEntity(columnName = "NO_REKENING")
	private String noRekening;
	
	@NISPEntity(columnName = "KODE_CABANG_AMBIL")
	private String kodeCabangAmbil;
	
	@NISPEntity(columnName = "NAMA_REKENING")
	private String namaRekening;
	
	@NISPEntity(columnName = "ALAMAT_1")
	private String alamat1;
	
	@NISPEntity(columnName = "ALAMAT_2")
	private String alamat2;
	
	@NISPEntity(columnName = "ALAMAT_3")
	private String alamat3;
	
	@NISPEntity(columnName = "ALAMAT_4")
	private String alamat4;
	
	@NISPEntity(columnName = "TANGGAL_CETAK")
	private Date tanggalCetak;
	
	@NISPEntity(columnName = "KODE_CABANG_REKENING")
	private String kodeCabangRekening;
	
	@NISPEntity(columnName = "NAMA_CABANG_REKENING")
	private String namaCabangRekening;
	
	@NISPEntity(columnName = "PRODUCT_TYPE_DESC")
	private String productTypeDesc;
	
	@NISPEntity(columnName = "MATA_UANG")
	private String mataUang;
	
	@NISPEntity(columnName = "HALAMAN")
	private String halaman;
	
	@NISPEntity(columnName = "TANGGAL")
	private Date tanggal;
	
	@NISPEntity(columnName = "TANGGAL_VALUTA")
	private Date tanggalValuta;
	
	@NISPEntity(columnName = "URAIAN")
	private String uraian;
	
	@NISPEntity(columnName = "DEBET")
	private Double debet;
	
	@NISPEntity(columnName = "KREDIT")
	private Double kredit;
	
	@NISPEntity(columnName = "SALDO")
	private Double saldo;
	
	@NISPEntity(columnName = "SALDO_AWAL")
	private Double saldoAwal;
	
	@NISPEntity(columnName = "TOTAL_TRANS_DR_BULAN")
	private Double totalTransDrBulan;
	
	@NISPEntity(columnName = "TOTAL_TRANS_CR_BULAN")
	private Double totalTransCrBulan;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_1")
	private String statementMessage1;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_2")
	private String statementMessage2;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_3")
	private String statementMessage3;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_4")
	private String statementMessage4;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_5")
	private String statementMessage5;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_6")
	private String statementMessage6;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_7")
	private String statementMessage7;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_8")
	private String statementMessage8;
	
	@NISPEntity(columnName = "NOTE")
	private String note;
	
	@NISPEntity(columnName = "MATA_UANG_RINGKASAN")
	private String mataUangRingkasan;
	
	@NISPEntity(columnName = "KURS_VALAS_IDR")
	private Double kursValasIDR;
	
	@NISPEntity(columnName = "SALDO_RINGKASAN")
	private Double saldoRingkasan;
	
	@NISPEntity(columnName = "SALDO_IDR")
	private Double saldoIDR;
	
	@NISPEntity(columnName = "TOTAL_SALDO_IDR")
	private Double totalSaldoIDR;
	
	@NISPEntity(columnName = "CATATAN")
	private String catatan;
	
	@NISPEntity(columnName = "TANGGAL_TRANSAKSI_OD")
	private Date tanggalTransaksiOd;
	
	@NISPEntity(columnName = "NO_FASILITAS")
	private String noFasilitas;
	
	@NISPEntity(columnName = "PERSEN_BUNGA")
	private Double persenBunga;
	
	@NISPEntity(columnName = "TANGGAL_PERJANJIAN_OD")
	private Date tanggalPerjanjianOd;
	
	@NISPEntity(columnName = "TANGGAL_AWAL_OD")
	private Date tanggalAwalOd;
	
	@NISPEntity(columnName = "TANGGAL_AKHIR_OD")
	private Date tanggalAkhirOd;
	
	@NISPEntity(columnName = "PLAFOND")
	private Double plafond;
	
	@NISPEntity(columnName = "KELONGGARAN_TARIK")
	private Double kelonggaranTarik;
	
	@NISPEntity(columnName = "POINT_BALANCE")
	private Double pointBalance;
	
	@NISPEntity(columnName = "TUNGGAKAN_BUNGA")
	private Double tunggakanBunga;
	
	@NISPEntity(columnName = "TUNGGAKAN_DENDA")
	private Double tunggakanDenda;
	
	@NISPEntity(columnName = "TUNGGAKAN_BIAYA_LAIN")
	private Double tunggakanBiayaLain;
	
	@NISPEntity(columnName = "TOTAL_TUNGGAKAN")
	private Double totalTunggakan;
	
	@NISPEntity(columnName = "ELECTRONIC_ADDRESS_DESC")
	private String electronicAddressDesc;
	
	@NISPEntity(columnName = "ELECTRONIC_ADDRESS_CODE")
	private String electronicAddressCode;
	
	@NISPEntity(columnName = "PERIODE")
	private String periode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNoRekening() {
		return noRekening;
	}

	public void setNoRekening(String noRekening) {
		this.noRekening = noRekening;
	}

	public String getKodeCabangAmbil() {
		return kodeCabangAmbil;
	}

	public void setKodeCabangAmbil(String kodeCabangAmbil) {
		this.kodeCabangAmbil = kodeCabangAmbil;
	}

	public String getNamaRekening() {
		return namaRekening;
	}

	public void setNamaRekening(String namaRekening) {
		this.namaRekening = namaRekening;
	}

	public String getAlamat1() {
		return alamat1;
	}

	public void setAlamat1(String alamat1) {
		this.alamat1 = alamat1;
	}

	public String getAlamat2() {
		return alamat2;
	}

	public void setAlamat2(String alamat2) {
		this.alamat2 = alamat2;
	}

	public String getAlamat3() {
		return alamat3;
	}

	public void setAlamat3(String alamat3) {
		this.alamat3 = alamat3;
	}

	public String getAlamat4() {
		return alamat4;
	}

	public void setAlamat4(String alamat4) {
		this.alamat4 = alamat4;
	}

	public Date getTanggalCetak() {
		return tanggalCetak;
	}

	public void setTanggalCetak(Date tanggalCetak) {
		this.tanggalCetak = tanggalCetak;
	}

	public String getKodeCabangRekening() {
		return kodeCabangRekening;
	}

	public void setKodeCabangRekening(String kodeCabangRekening) {
		this.kodeCabangRekening = kodeCabangRekening;
	}

	public String getNamaCabangRekening() {
		return namaCabangRekening;
	}

	public void setNamaCabangRekening(String namaCabangRekening) {
		this.namaCabangRekening = namaCabangRekening;
	}

	public String getProductTypeDesc() {
		return productTypeDesc;
	}

	public void setProductTypeDesc(String productTypeDesc) {
		this.productTypeDesc = productTypeDesc;
	}

	public String getMataUang() {
		return mataUang;
	}

	public void setMataUang(String mataUang) {
		this.mataUang = mataUang;
	}

	public String getHalaman() {
		return halaman;
	}

	public void setHalaman(String halaman) {
		this.halaman = halaman;
	}

	public Date getTanggal() {
		return tanggal;
	}

	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}

	public Date getTanggalValuta() {
		return tanggalValuta;
	}

	public void setTanggalValuta(Date tanggalValuta) {
		this.tanggalValuta = tanggalValuta;
	}

	public String getUraian() {
		return uraian;
	}

	public void setUraian(String uraian) {
		this.uraian = uraian;
	}

	public Double getDebet() {
		return debet;
	}

	public void setDebet(Double debet) {
		this.debet = debet;
	}

	public Double getKredit() {
		return kredit;
	}

	public void setKredit(Double kredit) {
		this.kredit = kredit;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Double getSaldoAwal() {
		return saldoAwal;
	}

	public void setSaldoAwal(Double saldoAwal) {
		this.saldoAwal = saldoAwal;
	}

	public Double getTotalTransDrBulan() {
		return totalTransDrBulan;
	}

	public void setTotalTransDrBulan(Double totalTransDrBulan) {
		this.totalTransDrBulan = totalTransDrBulan;
	}

	public Double getTotalTransCrBulan() {
		return totalTransCrBulan;
	}

	public void setTotalTransCrBulan(Double totalTransCrBulan) {
		this.totalTransCrBulan = totalTransCrBulan;
	}

	public String getStatementMessage1() {
		return statementMessage1;
	}

	public void setStatementMessage1(String statementMessage1) {
		this.statementMessage1 = statementMessage1;
	}

	public String getStatementMessage2() {
		return statementMessage2;
	}

	public void setStatementMessage2(String statementMessage2) {
		this.statementMessage2 = statementMessage2;
	}

	public String getStatementMessage3() {
		return statementMessage3;
	}

	public void setStatementMessage3(String statementMessage3) {
		this.statementMessage3 = statementMessage3;
	}

	public String getStatementMessage4() {
		return statementMessage4;
	}

	public void setStatementMessage4(String statementMessage4) {
		this.statementMessage4 = statementMessage4;
	}

	public String getStatementMessage5() {
		return statementMessage5;
	}

	public void setStatementMessage5(String statementMessage5) {
		this.statementMessage5 = statementMessage5;
	}

	public String getStatementMessage6() {
		return statementMessage6;
	}

	public void setStatementMessage6(String statementMessage6) {
		this.statementMessage6 = statementMessage6;
	}

	public String getStatementMessage7() {
		return statementMessage7;
	}

	public void setStatementMessage7(String statementMessage7) {
		this.statementMessage7 = statementMessage7;
	}

	public String getStatementMessage8() {
		return statementMessage8;
	}

	public void setStatementMessage8(String statementMessage8) {
		this.statementMessage8 = statementMessage8;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getMataUangRingkasan() {
		return mataUangRingkasan;
	}

	public void setMataUangRingkasan(String mataUangRingkasan) {
		this.mataUangRingkasan = mataUangRingkasan;
	}

	public Double getKursValasIDR() {
		return kursValasIDR;
	}

	public void setKursValasIDR(Double kursValasIDR) {
		this.kursValasIDR = kursValasIDR;
	}

	public Double getSaldoRingkasan() {
		return saldoRingkasan;
	}

	public void setSaldoRingkasan(Double saldoRingkasan) {
		this.saldoRingkasan = saldoRingkasan;
	}

	public Double getSaldoIDR() {
		return saldoIDR;
	}

	public void setSaldoIDR(Double saldoIDR) {
		this.saldoIDR = saldoIDR;
	}

	public Double getTotalSaldoIDR() {
		return totalSaldoIDR;
	}

	public void setTotalSaldoIDR(Double totalSaldoIDR) {
		this.totalSaldoIDR = totalSaldoIDR;
	}

	public String getCatatan() {
		return catatan;
	}

	public void setCatatan(String catatan) {
		this.catatan = catatan;
	}

	public Date getTanggalTransaksiOd() {
		return tanggalTransaksiOd;
	}

	public void setTanggalTransaksiOd(Date tanggalTransaksiOd) {
		this.tanggalTransaksiOd = tanggalTransaksiOd;
	}

	public String getNoFasilitas() {
		return noFasilitas;
	}

	public void setNoFasilitas(String noFasilitas) {
		this.noFasilitas = noFasilitas;
	}

	public Double getPersenBunga() {
		return persenBunga;
	}

	public void setPersenBunga(Double persenBunga) {
		this.persenBunga = persenBunga;
	}

	public Date getTanggalPerjanjianOd() {
		return tanggalPerjanjianOd;
	}

	public void setTanggalPerjanjianOd(Date tanggalPerjanjianOd) {
		this.tanggalPerjanjianOd = tanggalPerjanjianOd;
	}

	public Date getTanggalAwalOd() {
		return tanggalAwalOd;
	}

	public void setTanggalAwalOd(Date tanggalAwalOd) {
		this.tanggalAwalOd = tanggalAwalOd;
	}

	public Date getTanggalAkhirOd() {
		return tanggalAkhirOd;
	}

	public void setTanggalAkhirOd(Date tanggalAkhirOd) {
		this.tanggalAkhirOd = tanggalAkhirOd;
	}

	public Double getPlafond() {
		return plafond;
	}

	public void setPlafond(Double plafond) {
		this.plafond = plafond;
	}

	public Double getKelonggaranTarik() {
		return kelonggaranTarik;
	}

	public void setKelonggaranTarik(Double kelonggaranTarik) {
		this.kelonggaranTarik = kelonggaranTarik;
	}

	public Double getPointBalance() {
		return pointBalance;
	}

	public void setPointBalance(Double pointBalance) {
		this.pointBalance = pointBalance;
	}

	public Double getTunggakanBunga() {
		return tunggakanBunga;
	}

	public void setTunggakanBunga(Double tunggakanBunga) {
		this.tunggakanBunga = tunggakanBunga;
	}

	public Double getTunggakanDenda() {
		return tunggakanDenda;
	}

	public void setTunggakanDenda(Double tunggakanDenda) {
		this.tunggakanDenda = tunggakanDenda;
	}

	public Double getTunggakanBiayaLain() {
		return tunggakanBiayaLain;
	}

	public void setTunggakanBiayaLain(Double tunggakanBiayaLain) {
		this.tunggakanBiayaLain = tunggakanBiayaLain;
	}

	public Double getTotalTunggakan() {
		return totalTunggakan;
	}

	public void setTotalTunggakan(Double totalTunggakan) {
		this.totalTunggakan = totalTunggakan;
	}

	public String getElectronicAddressDesc() {
		return electronicAddressDesc;
	}

	public void setElectronicAddressDesc(String electronicAddressDesc) {
		this.electronicAddressDesc = electronicAddressDesc;
	}

	public String getElectronicAddressCode() {
		return electronicAddressCode;
	}

	public void setElectronicAddressCode(String electronicAddressCode) {
		this.electronicAddressCode = electronicAddressCode;
	}

	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
	}
}
