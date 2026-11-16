package com.optima.oraclejdbc.elixirprop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import com.optima.oraclejdbc.model.estatement.ESGiro;
import com.optima.oraclejdbc.model.estatement.ESGiroHistory;
import com.optima.oraclejdbc.model.estatement.ESMCBHistory;
import com.optima.oraclejdbc.model.estatement.ESMCBilingual;
import com.optima.oraclejdbc.model.estatement.ESMultiCurrency;
import com.optima.oraclejdbc.model.estatement.ESMultiCurrencyHistory;
import com.optima.oraclejdbc.model.estatement.ViewESAccountInfo;
import com.optima.oraclejdbc.model.estatement.ViewESGiroFooterHistoryReport;
import com.optima.oraclejdbc.model.estatement.ViewESGiroFooterReport;
import com.optima.oraclejdbc.utils.DateUtility;
import com.optima.oraclejdbc.utils.StringUtility;

public class ESProp {

	public static Properties getEsMcyProperties(ESMultiCurrency data, ViewESAccountInfo info, Date tglCetak, 
			double totalIdr, String providerUrl) {
		
		Properties prop = new Properties();
		String accNo = data.getAccountNumber().substring(data.getAccountNumber().toString().length() - 4);
		
		String accNo12 = data.getAccountNumber().substring(7);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(tglCetak);
		String hd1 = "01/" + String.valueOf(cal.get(Calendar.YEAR)).substring(2);
		
		prop.put("P_ACCOUNT_NO", data.getAccountNumber());
		prop.put("ACCOUNT_NO", accNo12);
		prop.put("KODE_CABANG", data.getKodeCabangRek());
		prop.put("NAMA_CABANG", data.getNamaCabangRek());
		prop.put("NOMOR_REKENING", data.getAccountNumber());
		prop.put("PRODUCT_NAME", data.getProductTypeDesc());
		prop.put("CURRENCY", data.getCurrencyType());
		prop.put("PERIODE", getLongPeriode(info.getPeriode()));
		prop.put("TANGGAL_CETAK", DateUtility.formatDate2(tglCetak));
		prop.put("CIF_NAME", data.getNamaRekening());
		prop.put("ALAMAT_1", StringUtility.checkStr(data.getAlamat1()));
		prop.put("ALAMAT_2", StringUtility.checkStr(data.getAlamat2()));
		prop.put("ALAMAT_3", StringUtility.checkStr(data.getAlamat3()));
		prop.put("ALAMAT_4", StringUtility.checkStr(data.getAlamat4()));
		prop.put("HD_1", hd1);
		prop.put("HD_2", accNo);
		prop.put("TOTAL_IDR", StringUtility.formatNumber(totalIdr));
		prop.put("PROVIDER_URL", providerUrl);
		
		return prop;
	}
	
	public static Properties getEsGiroProperties(ESGiro data, ViewESAccountInfo info, Date tglCetak, 
			ViewESGiroFooterReport footer, int type, String providerUrl) {
		
		Properties prop = new Properties();
		String accNo = data.getNoRekening().substring(data.getNoRekening().toString().length() - 4);
		
		String accNo12 = data.getNoRekening().substring(7);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(tglCetak);
		String hd1 = "01/" + String.valueOf(cal.get(Calendar.YEAR)).substring(2);
		
		prop.put("P_ACCOUNT_NO", data.getNoRekening());
		prop.put("ACCOUNT_NO", accNo12);
		prop.put("TYPE", String.valueOf(type));
		prop.put("KODE_CABANG", data.getKodeCabangRekening());
		prop.put("NAMA_CABANG", data.getNamaCabangRekening());
		prop.put("NOMOR_REKENING", data.getNoRekening());
		prop.put("PRODUCT_NAME", data.getProductTypeDesc());
		prop.put("CURRENCY", data.getMataUang());		
		prop.put("PERIODE", getLongPeriode(info.getPeriode()));
		prop.put("TANGGAL_CETAK", DateUtility.formatDate2(tglCetak));
		prop.put("CIF_NAME", data.getNamaRekening());
		prop.put("ALAMAT_1", StringUtility.checkStr(data.getAlamat1()));
		prop.put("ALAMAT_2", StringUtility.checkStr(data.getAlamat2()));
		prop.put("ALAMAT_3", StringUtility.checkStr(data.getAlamat3()));
		prop.put("ALAMAT_4", StringUtility.checkStr(data.getAlamat4()));
		prop.put("HD_1", hd1);
		prop.put("HD_2", accNo);
		prop.put("TOTAL_DEBET", StringUtility.formatNumber(footer.getDebet()));
		prop.put("TOTAL_CREDIT", StringUtility.formatNumber(footer.getCredit()));
		prop.put("SALDO_AKHIR", StringUtility.formatNumber(footer.getSaldo()));
		prop.put("PROVIDER_URL", providerUrl);
		
		if (type == 2) {
			prop.put("TUNGGAKAN_BUNGA", StringUtility.formatNumber(footer.getTunggakanBunga()));
			prop.put("TUNGGAKAN_DENDA", StringUtility.formatNumber(footer.getTunggakanDenda()));
			prop.put("TUNGGAKAN_BIAYA_LAIN", StringUtility.formatNumber(footer.getTunggakanBiayaLain()));
			prop.put("TOTAL_TUNGGAKAN", StringUtility.formatNumber(footer.getTotalTunggakan()));
		} else if (type == 3) {
			prop.put("NO_FASILITAS", footer.getNoFasilitas());
			prop.put("PLATFORM", StringUtility.formatNumber(footer.getPlafond()));
			prop.put("PERSEN_BUNGA", StringUtility.formatNumber(footer.getPersenBunga()));
			prop.put("MASA_KREDIT_1", footer.getTanggalAwal());
			prop.put("MASA_KREDIT_2", footer.getTanggalAkhir());
			prop.put("TUNGGAKAN_BUNGA", StringUtility.formatNumber(footer.getTunggakanBunga()));
			prop.put("TUNGGAKAN_DENDA", StringUtility.formatNumber(footer.getTunggakanDenda()));
			prop.put("TUNGGAKAN_BIAYA_LAIN", StringUtility.formatNumber(footer.getTunggakanBiayaLain()));
			prop.put("TOTAL_TUNGGAKAN", StringUtility.formatNumber(footer.getTotalTunggakan()));
		}
		
		return prop;
	}
	
	public static Properties getEsMcbProperties(ESMCBilingual data, ViewESAccountInfo info, Date tglCetak, 
			double totalIdr, String providerUrl) {
		
		Properties prop = new Properties();
		String accNo = data.getNoRekening().substring(data.getNoRekening().toString().length() - 4);
		
		String accNo12 = data.getNoRekening().substring(7);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(tglCetak);
		String hd1 = "01/" + String.valueOf(cal.get(Calendar.YEAR)).substring(2);
		
		prop.put("P_ACCOUNT_NO", data.getNoRekening());
		prop.put("ACCOUNT_NO", accNo12);
		prop.put("KODE_CABANG", data.getKodeCabangRekening());
		prop.put("NAMA_CABANG", data.getNamaCabangRekening());
		prop.put("NOMOR_REKENING", data.getNoRekening());
		prop.put("PRODUCT_NAME", data.getProductTypeDesc());
		prop.put("CURRENCY", data.getMataUang());
		prop.put("PERIODE", getLongPeriode(info.getPeriode()));
		prop.put("TANGGAL_CETAK", DateUtility.formatDate2(tglCetak));
		prop.put("CIF_NAME", data.getNamaRekening());
		prop.put("ALAMAT_1", StringUtility.checkStr(data.getAlamat1()));
		prop.put("ALAMAT_2", StringUtility.checkStr(data.getAlamat2()));
		prop.put("ALAMAT_3", StringUtility.checkStr(data.getAlamat3()));
		prop.put("ALAMAT_4", StringUtility.checkStr(data.getAlamat4()));
		prop.put("HD_1", hd1);
		prop.put("HD_2", accNo);
		prop.put("TOTAL_IDR", StringUtility.formatNumber(totalIdr));
		prop.put("PROVIDER_URL", providerUrl);
		
		return prop;
	}
	
	public static Properties getEsGiroHistoryProperties(ESGiroHistory data, ViewESGiroFooterHistoryReport footer, 
			int isReprint, String providerUrl, String tglCetakStr) {
		
		Properties prop = new Properties();
		String accNo = data.getNoRekening().substring(data.getNoRekening().toString().length() - 4);
		String accNo12 = data.getNoRekening().substring(7);
		String hd1 = "01/" + data.getTglCetakStr().substring(data.getTglCetakStr().length() - 2);
		
		String tglCetak = "";
		if (!tglCetakStr.equals("")) {
			tglCetak = tglCetakStr;
		} else {
			tglCetak = data.getTglCetakStr();
		}
		
		prop.put("P_ACCOUNT_NO", data.getNoRekening());
		prop.put("P_PERIODE", data.getPeriode());
		prop.put("ACCOUNT_NO", accNo12);
		prop.put("TYPE", String.valueOf(data.getType()));
		prop.put("KODE_CABANG", data.getKodeCabangRekening());
		prop.put("NAMA_CABANG", data.getNamaCabangRekening());
		prop.put("NOMOR_REKENING", data.getNoRekening());
		prop.put("PRODUCT_NAME", data.getProductTypeDesc());
		prop.put("CURRENCY", data.getMataUang());		
		prop.put("PERIODE", getLongPeriode(data.getPeriode()));
		prop.put("TANGGAL_CETAK", tglCetak);
		prop.put("CIF_NAME", data.getNamaRekening());
		prop.put("ALAMAT_1", StringUtility.checkStr(data.getAlamat1()));
		prop.put("ALAMAT_2", StringUtility.checkStr(data.getAlamat2()));
		prop.put("ALAMAT_3", StringUtility.checkStr(data.getAlamat3()));
		prop.put("ALAMAT_4", StringUtility.checkStr(data.getAlamat4()));
		prop.put("HD_1", hd1);
		prop.put("HD_2", accNo);
		prop.put("TOTAL_DEBET", StringUtility.formatNumber(footer.getDebet()));
		prop.put("TOTAL_CREDIT", StringUtility.formatNumber(footer.getCredit()));
		prop.put("SALDO_AKHIR", StringUtility.formatNumber(footer.getSaldo()));
		prop.put("RE_PRINT", String.valueOf(isReprint));
		prop.put("PROVIDER_URL", providerUrl);
		
		if (data.getType() == 2) {
			prop.put("TUNGGAKAN_BUNGA", StringUtility.formatNumber(footer.getTunggakanBunga()));
			prop.put("TUNGGAKAN_DENDA", StringUtility.formatNumber(footer.getTunggakanDenda()));
			prop.put("TUNGGAKAN_BIAYA_LAIN", StringUtility.formatNumber(footer.getTunggakanBiayaLain()));
			prop.put("TOTAL_TUNGGAKAN", StringUtility.formatNumber(footer.getTotalTunggakan()));
		} else if (data.getType() == 3) {
			prop.put("NO_FASILITAS", footer.getNoFasilitas());
			prop.put("PLATFORM", StringUtility.formatNumber(footer.getPlafond()));
			prop.put("PERSEN_BUNGA", StringUtility.formatNumber(footer.getPersenBunga()));
			prop.put("MASA_KREDIT_1", footer.getTanggalAwal());
			prop.put("MASA_KREDIT_2", footer.getTanggalAkhir());
			prop.put("TUNGGAKAN_BUNGA", StringUtility.formatNumber(footer.getTunggakanBunga()));
			prop.put("TUNGGAKAN_DENDA", StringUtility.formatNumber(footer.getTunggakanDenda()));
			prop.put("TUNGGAKAN_BIAYA_LAIN", StringUtility.formatNumber(footer.getTunggakanBiayaLain()));
			prop.put("TOTAL_TUNGGAKAN", StringUtility.formatNumber(footer.getTotalTunggakan()));
		}
		
		return prop;
	}
	
	public static Properties getEsMcyHistoryProperties(ESMultiCurrencyHistory data, double totalIdr, 
			int isReprint, String providerUrl, String tglCetakStr) {
		
		Properties prop = new Properties();
		String accNo = data.getAccountNumber().substring(data.getAccountNumber().toString().length() - 4);
		String accNo12 = data.getAccountNumber().substring(7);
		String hd1 = "01/" + data.getTglCetakStr().substring(data.getTglCetakStr().length() - 2);
		
		String tglCetak = "";
		if (!tglCetakStr.equals("")) {
			tglCetak = tglCetakStr;
		} else {
			tglCetak = data.getTglCetakStr();
		}
		
		prop.put("P_ACCOUNT_NO", data.getAccountNumber());
		prop.put("P_PERIODE", data.getPeriode());
		prop.put("ACCOUNT_NO", accNo12);
		prop.put("KODE_CABANG", data.getKodeCabangRek());
		prop.put("NAMA_CABANG", data.getNamaCabangRek());
		prop.put("NOMOR_REKENING", data.getAccountNumber());
		prop.put("PRODUCT_NAME", data.getProductTypeDesc());
		prop.put("CURRENCY", data.getCurrencyType());
		prop.put("PERIODE", getLongPeriode(data.getPeriode()));
		prop.put("TANGGAL_CETAK", tglCetak);
		prop.put("CIF_NAME", data.getNamaRekening());
		prop.put("ALAMAT_1", StringUtility.checkStr(data.getAlamat1()));
		prop.put("ALAMAT_2", StringUtility.checkStr(data.getAlamat2()));
		prop.put("ALAMAT_3", StringUtility.checkStr(data.getAlamat3()));
		prop.put("ALAMAT_4", StringUtility.checkStr(data.getAlamat4()));
		prop.put("HD_1", hd1);
		prop.put("HD_2", accNo);
		prop.put("TOTAL_IDR", StringUtility.formatNumber(totalIdr));
		prop.put("RE_PRINT", String.valueOf(isReprint));
		prop.put("PROVIDER_URL", providerUrl);
		
		return prop;
	}
	
	public static Properties getEsMcbHistoryProperties(ESMCBHistory data, double totalIdr, 
			int isReprint, String providerUrl, String tglCetakStr) {
		
		Properties prop = new Properties();
		String accNo = data.getNoRekening().substring(data.getNoRekening().toString().length() - 4);
		String accNo12 = data.getNoRekening().substring(7);
		String hd1 = "01/" + data.getTglCetakStr().substring(data.getTglCetakStr().length() - 2);
		
		String tglCetak = "";
		if (!tglCetakStr.equals("")) {
			tglCetak = tglCetakStr;
		} else {
			tglCetak = data.getTglCetakStr();
		}
		
		prop.put("P_ACCOUNT_NO", data.getNoRekening());
		prop.put("P_PERIODE", data.getPeriode());
		prop.put("ACCOUNT_NO", accNo12);
		prop.put("KODE_CABANG", data.getKodeCabangRekening());
		prop.put("NAMA_CABANG", data.getNamaCabangRekening());
		prop.put("NOMOR_REKENING", data.getNoRekening());
		prop.put("PRODUCT_NAME", data.getProductTypeDesc());
		prop.put("CURRENCY", data.getMataUang());
		prop.put("PERIODE", getLongPeriode(data.getPeriode()));
		prop.put("TANGGAL_CETAK", tglCetak);
		prop.put("CIF_NAME", data.getNamaRekening());
		prop.put("ALAMAT_1", StringUtility.checkStr(data.getAlamat1()));
		prop.put("ALAMAT_2", StringUtility.checkStr(data.getAlamat2()));
		prop.put("ALAMAT_3", StringUtility.checkStr(data.getAlamat3()));
		prop.put("ALAMAT_4", StringUtility.checkStr(data.getAlamat4()));
		prop.put("HD_1", hd1);
		prop.put("HD_2", accNo);
		prop.put("TOTAL_IDR", StringUtility.formatNumber(totalIdr));
		prop.put("RE_PRINT", String.valueOf(isReprint));
		prop.put("PROVIDER_URL", providerUrl);
		
		return prop;
	}
	
	private static String getLongPeriode(String strPeriode){
		
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			Date ori = sdf.parse(strPeriode);
			cal.setTime(ori);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			
			SimpleDateFormat out = new SimpleDateFormat("dd MMMM yyyy");
			String res = out.format(cal.getTime());
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			res+=" - "+out.format(cal.getTime());
			return res;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}