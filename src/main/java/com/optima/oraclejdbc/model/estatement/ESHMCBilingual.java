package com.optima.oraclejdbc.model.estatement;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityDateFomat;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.NISPEntityNumberFormat;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName = "ES_MC_BILINGUAL")
public class ESHMCBilingual extends DDIModel implements Serializable {

	private static final long serialVersionUID = -6019179550041603602L;

	@NISPEntityId
	@NISPEntity(columnName = "ID")
	private Long id; //DDI236MBLH
	
	@NISPEntity(columnName = "NO_REKENING")
	@Position(start = 0, length = 20)
	private String noRekening;
	
	@NISPEntity(columnName = "KODE_CABANG_AMBIL")
	@Position(start = 20, length = 6)
	private String kodeCabangAmbil;
	
	@NISPEntity(columnName = "NAMA_REKENING")
	@Position(start = 26, length = 40)
	private String namaRekening;
	
	@NISPEntity(columnName = "ALAMAT_1")
	@Position(start = 66, length = 40)
	private String alamat1;
	
	@NISPEntity(columnName = "ALAMAT_2")
	@Position(start = 106, length = 40)
	private String alamat2;
	
	@NISPEntity(columnName = "ALAMAT_3")
	@Position(start = 146, length = 40)
	private String alamat3;
	
	@NISPEntity(columnName = "ALAMAT_4")
	@Position(start = 186, length = 40)
	private String alamat4;
	
	@NISPEntity(columnName = "TANGGAL_CETAK")
	@NISPEntityDateFomat(format = "ddMMyy")
	@Position(start = 226, length = 8)
	private Date tanggalCetak;
	
	@NISPEntity(columnName = "KODE_CABANG_REKENING")
	@Position(start = 234, length = 6)
	private String kodeCabangRekening;
	
	@NISPEntity(columnName = "NAMA_CABANG_REKENING")
	@Position(start = 240, length = 30)
	private String namaCabangRekening;
	
	@NISPEntity(columnName = "PRODUCT_TYPE_DESC")
	@Position(start = 270, length = 20)
	private String productTypeDesc;
	
	@NISPEntity(columnName = "MATA_UANG")
	@Position(start = 290, length = 4)
	private String mataUang;
	
	@NISPEntity(columnName = "HALAMAN")
	@Position(start = 294, length = 6)
	private String halaman;
	
	@NISPEntity(columnName = "TANGGAL")
	@NISPEntityDateFomat(format = "ddMMyy")
	@Position(start = 300, length = 8)
	private Date tanggal;
	
	@NISPEntity(columnName = "TANGGAL_VALUTA")
	@NISPEntityDateFomat(format = "ddMMyy")
	@Position(start = 308, length = 8)
	private Date tanggalValuta;
	
	@NISPEntity(columnName = "URAIAN")
	@Position(start = 316, length = 191)
	private String uraian;
	
	@NISPEntity(columnName = "DEBET")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 507, length = 19)
	private Double debet;
	
	@NISPEntity(columnName = "KREDIT")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 526, length = 19)
	private Double kredit;
	
	@NISPEntity(columnName = "SALDO")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 545, length = 21)
	private Double saldo;
	
	@NISPEntity(columnName = "SALDO_AWAL")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 566, length = 21)
	private Double saldoAwal;
	
	@NISPEntity(columnName = "TOTAL_TRANS_DR_BULAN")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 587, length = 21)
	private Double totalTransDrBulan;
	
	@NISPEntity(columnName = "TOTAL_TRANS_CR_BULAN")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 608, length = 21)
	private Double totalTransCrBulan;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_1")
	@Position(start = 629, length = 40)
	private String statementMessage1;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_2")
	@Position(start = 669, length = 40)
	private String statementMessage2;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_3")
	@Position(start = 709, length = 40)
	private String statementMessage3;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_4")
	@Position(start = 749, length = 40)
	private String statementMessage4;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_5")
	@Position(start = 789, length = 40)
	private String statementMessage5;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_6")
	@Position(start = 829, length = 40)
	private String statementMessage6;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_7")
	@Position(start = 869, length = 40)
	private String statementMessage7;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_8")
	@Position(start = 909, length = 40)
	private String statementMessage8;
	
	@NISPEntity(columnName = "NOTE")
	@Position(start = 949, length = 50)
	private String note;
	
	@NISPEntity(columnName = "MATA_UANG_RINGKASAN")
	@Position(start = 999, length = 4)
	private String mataUangRingkasan;
	
	@NISPEntity(columnName = "KURS_VALAS_IDR")
	@NISPEntityNumberFormat(scale = 7)
	@Position(start = 1003, length = 15)
	private Double kursValasIDR;
	
	@NISPEntity(columnName = "SALDO_RINGKASAN")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 1018, length = 19)
	private Double saldoRingkasan;
	
	@NISPEntity(columnName = "SALDO_IDR")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 1037, length = 19)
	private Double saldoIDR;
	
	@NISPEntity(columnName = "TOTAL_SALDO_IDR")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 1056, length = 19)
	private Double totalSaldoIDR;
	
	@NISPEntity(columnName = "CATATAN")
	@Position(start = 1075, length = 150)
	private String catatan;
	
	@NISPEntity(columnName = "TANGGAL_TRANSAKSI_OD")
	@NISPEntityDateFomat(format = "ddMMyy")
	@Position(start = 1225, length = 8)
	private Date tanggalTransaksiOd;
	
	@NISPEntity(columnName = "NO_FASILITAS")
	@Position(start = 1233, length = 20)
	private String noFasilitas;
	
	@NISPEntity(columnName = "PERSEN_BUNGA")
	@NISPEntityNumberFormat(scale = 7)
	@Position(start = 1253, length = 13)
	private Double persenBunga;
	
	@NISPEntity(columnName = "TANGGAL_PERJANJIAN_OD")
	@NISPEntityDateFomat(format = "ddMMyy")
	@Position(start = 1266, length = 8)
	private Date tanggalPerjanjianOd;
	
	@NISPEntity(columnName = "TANGGAL_AWAL_OD")
	@NISPEntityDateFomat(format = "ddMMyy")
	@Position(start = 1274, length = 8)
	private Date tanggalAwalOd;
	
	@NISPEntity(columnName = "TANGGAL_AKHIR_OD")
	@NISPEntityDateFomat(format = "ddMMyy")
	@Position(start = 1282, length = 8)
	private Date tanggalAkhirOd;
	
	@NISPEntity(columnName = "PLAFOND")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 1290, length = 19)
	private Double plafond;
	
	@NISPEntity(columnName = "KELONGGARAN_TARIK")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 1309, length = 19)
	private Double kelonggaranTarik;
	
	@NISPEntity(columnName = "POINT_BALANCE")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 1328, length = 13)
	private Double pointBalance;
	
	@NISPEntity(columnName = "TUNGGAKAN_BUNGA")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 1341, length = 19)
	private Double tunggakanBunga;
	
	@NISPEntity(columnName = "TUNGGAKAN_DENDA")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 1360, length = 19)
	private Double tunggakanDenda;
	
	@NISPEntity(columnName = "TUNGGAKAN_BIAYA_LAIN")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 1379, length = 19)
	private Double tunggakanBiayaLain;
	
	@NISPEntity(columnName = "TOTAL_TUNGGAKAN")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 1398, length = 19)
	private Double totalTunggakan;
	
	@NISPEntity(columnName = "BLOCK_HC")
	private Integer blockHc;
	
//	@NISPEntity(columnName = "ELECTRONIC_ADDRESS_DESC")
//	@Position(start = 1417, length = 40)
//	private String electronicAddressDesc;
//	
//	@NISPEntity(columnName = "ELECTRONIC_ADDRESS_CODE")
//	@Position(start = 1457, length = 2)
//	private String electronicAddressCode;

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

	public Integer getBlockHc() {
		return blockHc;
	}

	public void setBlockHc(Integer blockHc) {
		this.blockHc = blockHc;
	}

//	public String getElectronicAddressDesc() {
//		return electronicAddressDesc;
//	}
//
//	public void setElectronicAddressDesc(String electronicAddressDesc) {
//		this.electronicAddressDesc = electronicAddressDesc;
//	}
//
//	public String getElectronicAddressCode() {
//		return electronicAddressCode;
//	}
//
//	public void setElectronicAddressCode(String electronicAddressCode) {
//		this.electronicAddressCode = electronicAddressCode;
//	}
}
