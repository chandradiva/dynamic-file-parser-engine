package com.optima.oraclejdbc.elixirprop;

import java.util.Date;
import java.util.Properties;

import com.optima.oraclejdbc.response.bancass.TransactionSummaryResponse;
import com.optima.oraclejdbc.utils.DateUtility;

public class BancassProp {

	public static Properties pernyataanTransaksi(TransactionSummaryResponse sh, Date tglCetak, String providerUrl) {
		Properties prop = new Properties();
		prop.put("P_SH_ID", String.valueOf(sh.getId()));
		prop.put("TGL_CETAK", DateUtility.formatDate2(tglCetak));
		prop.put("SH_NAMA_NASABAH", sh.getNamaNasabah() != null ? sh.getNamaNasabah() : "");
		prop.put("SH_ALAMAT_1", sh.getAlamatNasabah1() != null ? sh.getAlamatNasabah1() : "");
		prop.put("SH_ALAMAT_2", sh.getAlamatNasabah2() != null ? sh.getAlamatNasabah2() : "");
		prop.put("SH_ALAMAT_3", sh.getAlamatNasabah3() != null ? sh.getAlamatNasabah3() : "");
		prop.put("SH_ALAMAT_4", sh.getAlamatNasabah4() != null ? sh.getAlamatNasabah4() : "");
		prop.put("SH_NO_POLIS", sh.getNoPolis());
		prop.put("SH_TGL_MULAI_POLIS", sh.getTglMulaiPolis());
		prop.put("SH_CURRENCY", sh.getMataUang());
		prop.put("SH_PRODUK", sh.getProduk());
		prop.put("SH_JUMLAH_PREMI", String.valueOf(sh.getJumlahPremi()));
		prop.put("SH_FREKUENSI_BAYAR", sh.getFrekuensiPembayaran());
		prop.put("SH_TERTANGGUNG", sh.getNamaTertanggung());
		prop.put("SH_UANG_PERTANGGUNGAN", String.valueOf(sh.getUangPertanggungan()));
		prop.put("SH_STATUS", sh.getStatusPolis());
		prop.put("SH_JENIS_DANA", sh.getJenisDanaInvestasi());
		prop.put("SH_ALOKASI", String.valueOf(sh.getAlokasi()));
		prop.put("SH_TOTAL_UNIT", String.valueOf(sh.getTotalUnit()));
		prop.put("SH_HARGA_UNIT", String.valueOf(sh.getHargaUnit()));
		prop.put("SH_TGL_UNIT", sh.getTanggalUnit());
		prop.put("SH_NILAI_UNIT", String.valueOf(sh.getNilaiUnit()));
		prop.put("PROVIDER_URL", providerUrl);
		
		return prop;
	}
}
