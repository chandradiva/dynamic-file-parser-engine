package com.optima.oraclejdbc.model.estatement;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntity;

@NISPEntity(tableName = "ES_GIRO_HISTORY")
public class ESGiroHistory extends ESGiro implements Serializable {

	private static final long serialVersionUID = 5667977051831646109L;

//	@NISPEntityId
//	@NISPEntity(columnName = "ID")
//	private Long id; //DDI236TXTS
//	
//	@NISPEntity(columnName = "NO_REKENING")
//	private String noRekening;
//	
//	@NISPEntity(columnName = "KODE_CABANG_AMBIL")
//	private String kodeCabangAmbil;
//	
//	@NISPEntity(columnName = "NAMA_REKENING")
//	private String namaRekening;
//	
//	@NISPEntity(columnName = "ALAMAT_1")
//	private String alamat1;
//	
//	@NISPEntity(columnName = "ALAMAT_2")
//	private String alamat2;
//	
//	@NISPEntity(columnName = "ALAMAT_3")
//	private String alamat3;
//	
//	@NISPEntity(columnName = "ALAMAT_4")
//	private String alamat4;
//	
//	@NISPEntity(columnName = "TANGGAL_CETAK")
//	private Date tanggalCetak;
//	
//	@NISPEntity(columnName = "KODE_CABANG_REKENING")
//	private String kodeCabangRekening;
//	
//	@NISPEntity(columnName = "NAMA_CABANG_REKENING")
//	private String namaCabangRekening;
//	
//	@NISPEntity(columnName = "PRODUCT_TYPE_DESC")
//	private String productTypeDesc;
//	
//	@NISPEntity(columnName = "MATA_UANG")
//	private String mataUang;
//	
//	@NISPEntity(columnName = "HALAMAN")
//	private String halaman;
//	
//	@NISPEntity(columnName = "TANGGAL")
//	private Date tanggal;
//	
//	@NISPEntity(columnName = "TANGGAL_VALUTA")
//	private Date tanggalValuta;
//	
//	@NISPEntity(columnName = "URAIAN")
//	private String uraian;
//	
//	@NISPEntity(columnName = "DEBET")
//	private Double debet;
//	
//	@NISPEntity(columnName = "KREDIT")
//	private Double kredit;
//	
//	@NISPEntity(columnName = "SALDO")
//	private Double saldo;
//	
//	@NISPEntity(columnName = "SALDO_AWAL")
//	private Double saldoAwal;
//	
//	@NISPEntity(columnName = "TOTAL_TRANS_DR_BULAN")
//	private Double totalTransDrBulan;
//	
//	@NISPEntity(columnName = "TOTAL_TRANS_CR_BULAN")
//	private Double totalTransCrBulan;
//	
//	@NISPEntity(columnName = "STATEMENT_MESSAGE_1")
//	private String statementMessage1;
//	
//	@NISPEntity(columnName = "STATEMENT_MESSAGE_2")
//	private String statementMessage2;
//	
//	@NISPEntity(columnName = "STATEMENT_MESSAGE_3")
//	private String statementMessage3;
//	
//	@NISPEntity(columnName = "STATEMENT_MESSAGE_4")
//	private String statementMessage4;
//	
//	@NISPEntity(columnName = "STATEMENT_MESSAGE_5")
//	private String statementMessage5;
//	
//	@NISPEntity(columnName = "STATEMENT_MESSAGE_6")
//	private String statementMessage6;
//	
//	@NISPEntity(columnName = "STATEMENT_MESSAGE_7")
//	private String statementMessage7;
//	
//	@NISPEntity(columnName = "STATEMENT_MESSAGE_8")
//	private String statementMessage8;
//	
//	@NISPEntity(columnName = "TANGGAL_TRANSAKSI_OD")
//	private Date tanggalTransaksiOd;
//	
//	@NISPEntity(columnName = "NO_FASILITAS")
//	private String noFasilitas;
//	
//	@NISPEntity(columnName = "PERSEN_BUNGA")
//	private Double persenBunga;
//	
//	@NISPEntity(columnName = "TANGGAL_PERJANJIAN_OD")
//	private Date tanggalPerjanjianOd;
//	
//	@NISPEntity(columnName = "TANGGAL_AWAL_OD")
//	private Date tanggalAwalOd;
//	
//	@NISPEntity(columnName = "TANGGAL_AKHIR_OD")
//	private Date tanggalAkhirOd;
//	
//	@NISPEntity(columnName = "PLAFOND")
//	private Double plafond;
//	
//	@NISPEntity(columnName = "KELONGGARAN_TARIK")
//	private Double kelonggaranTarik;
//	
//	@NISPEntity(columnName = "POINT_BALANCE")
//	private Double pointBalance;
//	
//	@NISPEntity(columnName = "TUNGGAKAN_BUNGA")
//	private Double tunggakanBunga;
//	
//	@NISPEntity(columnName = "TUNGGAKAN_DENDA")
//	private Double tunggakanDenda;
//	
//	@NISPEntity(columnName = "TUNGGAKAN_BIAYA_LAIN")
//	private Double tunggakanBiayaLain;
//	
//	@NISPEntity(columnName = "TOTAL_TUNGGAKAN")
//	@Position(start = 1081, length = 19)
//	private Double totalTunggakan;
//	
//	@NISPEntity(columnName = "ELEC_ADDR_TYPE_DESC")
//	private String elecAddrTypeDesc; //email
//	
//	@NISPEntity(columnName = "ELEC_ADDR_CODE")
//	private String elecAddrCode;
	
	@NISPEntity(columnName = "PERIODE")
	private String periode;
	
	@NISPEntity(columnName = "TYPE")
	private int type;
	
	@NISPEntity(columnName = "TGL_CETAK_STR")
	private String tglCetakStr;

//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getNoRekening() {
//		return noRekening;
//	}
//
//	public void setNoRekening(String noRekening) {
//		this.noRekening = noRekening;
//	}
//
//	public String getKodeCabangAmbil() {
//		return kodeCabangAmbil;
//	}
//
//	public void setKodeCabangAmbil(String kodeCabangAmbil) {
//		this.kodeCabangAmbil = kodeCabangAmbil;
//	}
//
//	public String getNamaRekening() {
//		return namaRekening;
//	}
//
//	public void setNamaRekening(String namaRekening) {
//		this.namaRekening = namaRekening;
//	}
//
//	public String getAlamat1() {
//		return alamat1;
//	}
//
//	public void setAlamat1(String alamat1) {
//		this.alamat1 = alamat1;
//	}
//
//	public String getAlamat2() {
//		return alamat2;
//	}
//
//	public void setAlamat2(String alamat2) {
//		this.alamat2 = alamat2;
//	}
//
//	public String getAlamat3() {
//		return alamat3;
//	}
//
//	public void setAlamat3(String alamat3) {
//		this.alamat3 = alamat3;
//	}
//
//	public String getAlamat4() {
//		return alamat4;
//	}
//
//	public void setAlamat4(String alamat4) {
//		this.alamat4 = alamat4;
//	}
//
//	public Date getTanggalCetak() {
//		return tanggalCetak;
//	}
//
//	public void setTanggalCetak(Date tanggalCetak) {
//		this.tanggalCetak = tanggalCetak;
//	}
//
//	public String getKodeCabangRekening() {
//		return kodeCabangRekening;
//	}
//
//	public void setKodeCabangRekening(String kodeCabangRekening) {
//		this.kodeCabangRekening = kodeCabangRekening;
//	}
//
//	public String getNamaCabangRekening() {
//		return namaCabangRekening;
//	}
//
//	public void setNamaCabangRekening(String namaCabangRekening) {
//		this.namaCabangRekening = namaCabangRekening;
//	}
//
//	public String getProductTypeDesc() {
//		return productTypeDesc;
//	}
//
//	public void setProductTypeDesc(String productTypeDesc) {
//		this.productTypeDesc = productTypeDesc;
//	}
//
//	public String getMataUang() {
//		return mataUang;
//	}
//
//	public void setMataUang(String mataUang) {
//		this.mataUang = mataUang;
//	}
//
//	public String getHalaman() {
//		return halaman;
//	}
//
//	public void setHalaman(String halaman) {
//		this.halaman = halaman;
//	}
//
//	public Date getTanggal() {
//		return tanggal;
//	}
//
//	public void setTanggal(Date tanggal) {
//		this.tanggal = tanggal;
//	}
//
//	public Date getTanggalValuta() {
//		return tanggalValuta;
//	}
//
//	public void setTanggalValuta(Date tanggalValuta) {
//		this.tanggalValuta = tanggalValuta;
//	}
//
//	public String getUraian() {
//		return uraian;
//	}
//
//	public void setUraian(String uraian) {
//		this.uraian = uraian;
//	}
//
//	public Double getDebet() {
//		return debet;
//	}
//
//	public void setDebet(Double debet) {
//		this.debet = debet;
//	}
//
//	public Double getKredit() {
//		return kredit;
//	}
//
//	public void setKredit(Double kredit) {
//		this.kredit = kredit;
//	}
//
//	public Double getSaldo() {
//		return saldo;
//	}
//
//	public void setSaldo(Double saldo) {
//		this.saldo = saldo;
//	}
//
//	public Double getSaldoAwal() {
//		return saldoAwal;
//	}
//
//	public void setSaldoAwal(Double saldoAwal) {
//		this.saldoAwal = saldoAwal;
//	}
//
//	public Double getTotalTransDrBulan() {
//		return totalTransDrBulan;
//	}
//
//	public void setTotalTransDrBulan(Double totalTransDrBulan) {
//		this.totalTransDrBulan = totalTransDrBulan;
//	}
//
//	public Double getTotalTransCrBulan() {
//		return totalTransCrBulan;
//	}
//
//	public void setTotalTransCrBulan(Double totalTransCrBulan) {
//		this.totalTransCrBulan = totalTransCrBulan;
//	}
//
//	public String getStatementMessage1() {
//		return statementMessage1;
//	}
//
//	public void setStatementMessage1(String statementMessage1) {
//		this.statementMessage1 = statementMessage1;
//	}
//
//	public String getStatementMessage2() {
//		return statementMessage2;
//	}
//
//	public void setStatementMessage2(String statementMessage2) {
//		this.statementMessage2 = statementMessage2;
//	}
//
//	public String getStatementMessage3() {
//		return statementMessage3;
//	}
//
//	public void setStatementMessage3(String statementMessage3) {
//		this.statementMessage3 = statementMessage3;
//	}
//
//	public String getStatementMessage4() {
//		return statementMessage4;
//	}
//
//	public void setStatementMessage4(String statementMessage4) {
//		this.statementMessage4 = statementMessage4;
//	}
//
//	public String getStatementMessage5() {
//		return statementMessage5;
//	}
//
//	public void setStatementMessage5(String statementMessage5) {
//		this.statementMessage5 = statementMessage5;
//	}
//
//	public String getStatementMessage6() {
//		return statementMessage6;
//	}
//
//	public void setStatementMessage6(String statementMessage6) {
//		this.statementMessage6 = statementMessage6;
//	}
//
//	public String getStatementMessage7() {
//		return statementMessage7;
//	}
//
//	public void setStatementMessage7(String statementMessage7) {
//		this.statementMessage7 = statementMessage7;
//	}
//
//	public String getStatementMessage8() {
//		return statementMessage8;
//	}
//
//	public void setStatementMessage8(String statementMessage8) {
//		this.statementMessage8 = statementMessage8;
//	}
//
//	public Date getTanggalTransaksiOd() {
//		return tanggalTransaksiOd;
//	}
//
//	public void setTanggalTransaksiOd(Date tanggalTransaksiOd) {
//		this.tanggalTransaksiOd = tanggalTransaksiOd;
//	}
//
//	public String getNoFasilitas() {
//		return noFasilitas;
//	}
//
//	public void setNoFasilitas(String noFasilitas) {
//		this.noFasilitas = noFasilitas;
//	}
//
//	public Double getPersenBunga() {
//		return persenBunga;
//	}
//
//	public void setPersenBunga(Double persenBunga) {
//		this.persenBunga = persenBunga;
//	}
//
//	public Date getTanggalPerjanjianOd() {
//		return tanggalPerjanjianOd;
//	}
//
//	public void setTanggalPerjanjianOd(Date tanggalPerjanjianOd) {
//		this.tanggalPerjanjianOd = tanggalPerjanjianOd;
//	}
//
//	public Date getTanggalAwalOd() {
//		return tanggalAwalOd;
//	}
//
//	public void setTanggalAwalOd(Date tanggalAwalOd) {
//		this.tanggalAwalOd = tanggalAwalOd;
//	}
//
//	public Date getTanggalAkhirOd() {
//		return tanggalAkhirOd;
//	}
//
//	public void setTanggalAkhirOd(Date tanggalAkhirOd) {
//		this.tanggalAkhirOd = tanggalAkhirOd;
//	}
//
//	public Double getPlafond() {
//		return plafond;
//	}
//
//	public void setPlafond(Double plafond) {
//		this.plafond = plafond;
//	}
//
//	public Double getKelonggaranTarik() {
//		return kelonggaranTarik;
//	}
//
//	public void setKelonggaranTarik(Double kelonggaranTarik) {
//		this.kelonggaranTarik = kelonggaranTarik;
//	}
//
//	public Double getPointBalance() {
//		return pointBalance;
//	}
//
//	public void setPointBalance(Double pointBalance) {
//		this.pointBalance = pointBalance;
//	}
//
//	public Double getTunggakanBunga() {
//		return tunggakanBunga;
//	}
//
//	public void setTunggakanBunga(Double tunggakanBunga) {
//		this.tunggakanBunga = tunggakanBunga;
//	}
//
//	public Double getTunggakanDenda() {
//		return tunggakanDenda;
//	}
//
//	public void setTunggakanDenda(Double tunggakanDenda) {
//		this.tunggakanDenda = tunggakanDenda;
//	}
//
//	public Double getTunggakanBiayaLain() {
//		return tunggakanBiayaLain;
//	}
//
//	public void setTunggakanBiayaLain(Double tunggakanBiayaLain) {
//		this.tunggakanBiayaLain = tunggakanBiayaLain;
//	}
//
//	public Double getTotalTunggakan() {
//		return totalTunggakan;
//	}
//
//	public void setTotalTunggakan(Double totalTunggakan) {
//		this.totalTunggakan = totalTunggakan;
//	}
//
//	public String getElecAddrTypeDesc() {
//		return elecAddrTypeDesc;
//	}
//
//	public void setElecAddrTypeDesc(String elecAddrTypeDesc) {
//		this.elecAddrTypeDesc = elecAddrTypeDesc;
//	}
//
//	public String getElecAddrCode() {
//		return elecAddrCode;
//	}
//
//	public void setElecAddrCode(String elecAddrCode) {
//		this.elecAddrCode = elecAddrCode;
//	}

	public ESGiroHistory() {
		super();
	}
	
	public ESGiroHistory(ESGiro giro) {
		super();
		this.setNoRekening(giro.getNoRekening());
		this.setKodeCabangAmbil(giro.getKodeCabangAmbil());
		this.setNamaRekening(giro.getNamaRekening());
		this.setAlamat1(giro.getAlamat1());
		this.setAlamat2(giro.getAlamat2());
		this.setAlamat3(giro.getAlamat3());
		this.setAlamat4(giro.getAlamat4());
		this.setTanggalCetak(giro.getTanggalCetak());
		this.setKodeCabangRekening(giro.getKodeCabangRekening());
		this.setNamaCabangRekening(giro.getNamaCabangRekening());
		this.setProductTypeDesc(giro.getProductTypeDesc());
		this.setMataUang(giro.getMataUang());
		this.setHalaman(giro.getHalaman());
		this.setTanggal(giro.getTanggal());
		this.setTanggalValuta(giro.getTanggalValuta());
		this.setUraian(giro.getUraian());
		this.setDebet(giro.getDebet());
		this.setKredit(giro.getKredit());
		this.setSaldo(giro.getSaldo());
		this.setSaldoAwal(giro.getSaldoAwal());
		this.setTotalTransDrBulan(giro.getTotalTransDrBulan());
		this.setTotalTransCrBulan(giro.getTotalTransCrBulan());
		this.setStatementMessage1(giro.getStatementMessage1());
		this.setStatementMessage2(giro.getStatementMessage2());
		this.setStatementMessage3(giro.getStatementMessage3());
		this.setStatementMessage4(giro.getStatementMessage4());
		this.setStatementMessage5(giro.getStatementMessage5());
		this.setStatementMessage6(giro.getStatementMessage6());
		this.setStatementMessage7(giro.getStatementMessage7());
		this.setStatementMessage8(giro.getStatementMessage8());
		this.setTanggalTransaksiOd(giro.getTanggalTransaksiOd());
		this.setNoFasilitas(giro.getNoFasilitas());
		this.setPersenBunga(giro.getPersenBunga());
		this.setTanggalPerjanjianOd(giro.getTanggalPerjanjianOd());
		this.setTanggalAwalOd(giro.getTanggalAwalOd());
		this.setTanggalAkhirOd(giro.getTanggalAkhirOd());
		this.setPlafond(giro.getPlafond());
		this.setKelonggaranTarik(giro.getKelonggaranTarik());
		this.setPointBalance(giro.getPointBalance());
		this.setTunggakanBunga(giro.getTunggakanBunga());
		this.setTunggakanDenda(giro.getTunggakanDenda());
		this.setTunggakanBiayaLain(giro.getTunggakanBiayaLain());
		this.setTotalTunggakan(giro.getTotalTunggakan());
		this.setElecAddrTypeDesc(giro.getElecAddrTypeDesc());
		this.setElecAddrCode(giro.getElecAddrCode());
	}
	
	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTglCetakStr() {
		return tglCetakStr;
	}

	public void setTglCetakStr(String tglCetakStr) {
		this.tglCetakStr = tglCetakStr;
	}
}
