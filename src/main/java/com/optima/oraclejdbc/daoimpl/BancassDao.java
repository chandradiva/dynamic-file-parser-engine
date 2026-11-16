package com.optima.oraclejdbc.daoimpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.BADetailBiaya;
import com.optima.oraclejdbc.model.BADetailPremi;
import com.optima.oraclejdbc.model.BADisclaimer;
import com.optima.oraclejdbc.model.BAParentModel;
import com.optima.oraclejdbc.model.BATransactionSummary;
import com.optima.oraclejdbc.model.BATransaksiLain;
import com.optima.oraclejdbc.response.bancass.BancassMiniResponse;
import com.optima.oraclejdbc.response.bancass.DetailBiayaResponse;
import com.optima.oraclejdbc.response.bancass.DetailPremiResponse;
import com.optima.oraclejdbc.response.bancass.DisclaimerResponse;
import com.optima.oraclejdbc.response.bancass.TransactionSummaryResponse;
import com.optima.oraclejdbc.response.bancass.TransaksiLainResponse;

@Component
public class BancassDao extends ParentDao<BAParentModel> {

	public List<BancassMiniResponse> getMiniStatement(String spajNo, Date startDate, Date endDate, 
			Integer page, Integer countRecord) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		
		return getByDateBySpajNoStatement(spajNo, startDate, endDate, page, countRecord, "TO_NUMBER(NO)", "DESC");
	}

	private List<BancassMiniResponse> getByDateBySpajNoStatement(String spajNo, Date startDate, Date endDate,
			Integer page, Integer countPerPage, String sortBy, String sortType) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		if( spajNo == null || spajNo.length() == 0 )
			return null;
		
		String sql = "SELECT BATCH_ID FROM BANCASS "
				+ "WHERE SPAJ_NO=?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(spajNo);
		List<Map<String, Object> > rows = template.queryForList(sql, args.toArray());
		if( rows.size() > 0 ){
			return getByDateStatement((String)rows.get(0).get("BATCH_ID"), startDate, endDate, page, countPerPage, sortBy, sortType);
		}else
			return new ArrayList<BancassMiniResponse>();
	}
	
	public List<BancassMiniResponse> getByDateStatement(String batchId, Date startDate, Date endDate,
			Integer page, Integer countPerPage, String sortBy, String sortType) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		if( batchId == null || batchId.length() == 0 )
			return null;
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(batchId);
		String sql = "SELECT * FROM "
				+ "(SELECT A.*, ROWNUM RNUM FROM "
				+ "(SELECT TRANSACTION_DATE, BILLING_DESCRIPTION, BILLING_AMOUNT, DEBIT_CURRENCY, TRANSACTION_STATUS "
				+ "FROM BANCASS "
				+ "WHERE BATCH_ID = ?";
		
		if( startDate != null ){
			sql += " AND TRANSACTION_DATE >= ?";
			args.add(startDate);
		}
		
		if( endDate != null ){
			sql += " AND TRANSACTION_DATE <= ?";
			args.add(endDate);
		}
		sql+=" ORDER BY "+sortBy+" "+sortType+") A ";
		if( countPerPage == null )
			countPerPage = 10;
		
		int startRecord = 1;
		if( page != null ){
			startRecord = (page-1)*countPerPage+1; 						
		}
		
		sql+="WHERE ROWNUM<=?) "
				+ "WHERE RNUM>=?";
		args.add(startRecord+countPerPage-1);
		args.add(startRecord);
		
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		
		ArrayList<BancassMiniResponse> res = new ArrayList<BancassMiniResponse>();
		for (Map<String, Object> row : rows) {
			BancassMiniResponse resp = serialize(BancassMiniResponse.class, row); 					
			res.add(resp);
		}
		
		return res;
	}

	public List<BancassMiniResponse> getByRecentTransactionsStatement(String batchId, int count, Integer page,
			Integer countPerPage, String sortBy, String sortType) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		if( batchId == null || batchId.length() == 0 )
			return null;
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(batchId);
		String sql = "SELECT * FROM "
				+ "(SELECT A.*, ROWNUM RNUM FROM "
				+ "(SELECT TRANSACTION_DATE, BILLING_DESCRIPTION, BILLING_AMOUNT, DEBIT_CURRENCY, TRANSACTION_STATUS "
				+ "FROM BANCASS "
				+ "WHERE BATCH_ID = ? AND ROWNUM <=? "
				+ "ORDER BY TO_NUMBER(NO) DESC) A ";
		
		args.add(count);
		if( countPerPage == null )
			countPerPage = 10;
		
		int startRecord = 1;
		if( page != null ){
			startRecord = (page-1)*countPerPage+1; 						
		}
		sql+="WHERE ROWNUM<=? ";
		if( !sortBy.equals("TO_NUMBER(NO)") )
			sql+="ORDER BY "+sortBy+" "+sortType;
		sql += ") ";
		args.add(startRecord+countPerPage-1);
		sql += "WHERE RNUM>=?";
		args.add(startRecord);
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		
		ArrayList<BancassMiniResponse> res = new ArrayList<BancassMiniResponse>();
		for (Map<String, Object> row : rows) {
			BancassMiniResponse resp = serialize(BancassMiniResponse.class, row); 					
			res.add(resp);
		}
		
		return res;
	}
	
	public int getTotalCountByDate(String batchId, Date startDate, Date endDate){
		String sql = "SELECT COUNT(*) "
				+ "FROM BANCASS "
				+ "WHERE BATCH_ID=?";
		List<Object> args = new ArrayList<Object>();
		args.add(batchId);
		
		if( startDate != null ){
			sql += " AND TRANSACTION_DATE >= ?";
			args.add(startDate);
		}
		
		if( endDate != null ){
			sql += " AND TRANSACTION_DATE <= ?";
			args.add(endDate);
		}
		return template.queryForObject(sql, args.toArray(), Integer.class);
	}
	
	public int getTotalCountByRecent(String batchId, int count){
		String sql = "SELECT COUNT(*) "
				+ "FROM BANCASS "
				+ "WHERE BATCH_ID=?";
		List<Object> args = new ArrayList<Object>();
		args.add(batchId);
		
		int res = template.queryForObject(sql, args.toArray(), Integer.class);
		if( res > count )
			return count;
		return res;
	}
	
	public Long getNextId() {
		String sql = "SELECT BA_TRANSACTION_SUMMARY_SEQ.NEXTVAL FROM DUAL";
		return template.queryForObject(sql, Long.class);		
	}

	@Override
	public void createBatch(Object[] dataArray) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (dataArray.length > 0) {
			List<BATransactionSummary> baTransSummary = new ArrayList<BATransactionSummary>();
			List<BADetailPremi> baDetailPremi = new ArrayList<BADetailPremi>();
			List<BADetailBiaya> baDetailBiaya = new ArrayList<BADetailBiaya>();
			List<BATransaksiLain> baTransLain = new ArrayList<BATransaksiLain>();
			List<BADisclaimer> baDisclaimer = new ArrayList<BADisclaimer>();
			
			for (int i=0;i<dataArray.length;i++) {
				if (dataArray[i] instanceof BATransactionSummary) {
					baTransSummary.add((BATransactionSummary) dataArray[i]);
				} else if (dataArray[i] instanceof BADetailPremi) {
					baDetailPremi.add((BADetailPremi) dataArray[i]);
				} else if (dataArray[i] instanceof BADetailBiaya) {
					baDetailBiaya.add((BADetailBiaya) dataArray[i]);
				} else if (dataArray[i] instanceof BATransaksiLain) {
					baTransLain.add((BATransaksiLain) dataArray[i]);
				} else if (dataArray[i] instanceof BADisclaimer) {
					baDisclaimer.add((BADisclaimer) dataArray[i]);
				}
			}
			
			super.createBatch(baTransSummary.toArray());
			super.createBatch(baDetailPremi.toArray());
			super.createBatch(baDetailBiaya.toArray());
			super.createBatch(baTransLain.toArray());
			super.createBatch(baDisclaimer.toArray());
		}
	}
	
	public TransactionSummaryResponse getTransactionSummaryByPolicyNo(String policyNo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT * FROM (SELECT ID, NO_POLIS, NAMA_NASABAH, ALAMAT_NASABAH_1, ALAMAT_NASABAH_2, ALAMAT_NASABAH_3, ALAMAT_NASABAH_4, "
				+ "TO_CHAR(TGL_MULAI_POLIS, 'DD MONTH YYYY') AS TGL_MULAI_POLIS, MATA_UANG, PRODUK, JUMLAH_PREMI, "
				+ "FREKUENSI_PEMBAYARAN, NAMA_TERTANGGUNG, UANG_PERTANGGUNGAN, STATUS_POLIS, JENIS_DANA_INVESTASI, "
				+ "ALOKASI, TOTAL_UNIT, HARGA_UNIT, TO_CHAR(TANGGAL_UNIT, 'DD MONTH YYYY') AS TANGGAL_UNIT, NILAI_UNIT "
				+ "FROM BA_TRANSACTION_SUMMARY "
				+ "WHERE NO_POLIS = ? ORDER BY ID DESC) WHERE ROWNUM = 1";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(policyNo);
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if (rows.size() > 0) {
			TransactionSummaryResponse response = serialize(TransactionSummaryResponse.class, rows.get(0));
			return response;
		}
		
		return null;
	}
	
	public List<DetailPremiResponse> getListDetailPremiByPolicyNo(Long shId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT ID, DETAIL_ID, PEMBAYARAN_PREMI, "
				+ "CASE WHEN TANGGAL IS NOT NULL THEN TO_CHAR(TANGGAL, 'DD MONTH YYYY') ELSE ' ' END AS TANGGAL, "
				+ "JUMLAH_PREMI, HARGA_UNIT, TOTAL_UNIT "
				+ "FROM BA_DETAIL_PREMI "
				+ "WHERE BA_TRANSACTION_SUMMARY_ID = ? "
				+ "ORDER BY ID ASC";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(shId);
		
		List<Map<String, Object>> trans = template.queryForList(sql, args.toArray());
		List<DetailPremiResponse> records = new ArrayList<DetailPremiResponse>();
		for (Map<String, Object> item : trans) {
			DetailPremiResponse detail = serialize(DetailPremiResponse.class, item);
			
			records.add(detail);
		}
		
		return records;
	}
	
	public List<DetailBiayaResponse> getListDetailBiayaByPolicyNo(Long shId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT ID, DETAIL_ID, BIAYA, "
				+ "CASE WHEN TANGGAL IS NOT NULL THEN TO_CHAR(TANGGAL, 'DD MONTH YYYY') ELSE ' ' END AS TANGGAL, "
				+ "JUMLAH_BIAYA, HARGA_UNIT, TOTAL_UNIT "
				+ "FROM BA_DETAIL_BIAYA "
				+ "WHERE BA_TRANSACTION_SUMMARY_ID = ? "
				+ "ORDER BY ID ASC";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(shId);
		
		List<Map<String, Object>> trans = template.queryForList(sql, args.toArray());
		List<DetailBiayaResponse> records = new ArrayList<DetailBiayaResponse>();
		for (Map<String, Object> item : trans) {
			DetailBiayaResponse detail = serialize(DetailBiayaResponse.class, item);
			
			records.add(detail);
		}
		
		return records;
	}
	
	public List<TransaksiLainResponse> getListTransaksiLainByPolicyNo(Long shId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT ID, DETAIL_ID, TRANSAKSI_LAIN, "
				+ "CASE WHEN TANGGAL IS NOT NULL THEN TO_CHAR(TANGGAL, 'DD MONTH YYYY') ELSE ' ' END AS TANGGAL, "
				+ "JUMLAH_DANA, HARGA_UNIT, TOTAL_UNIT "
				+ "FROM BA_TRANSAKSI_LAIN "
				+ "WHERE BA_TRANSACTION_SUMMARY_ID = ? "
				+ "ORDER BY ID ASC";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(shId);
		
		List<Map<String, Object>> trans = template.queryForList(sql, args.toArray());
		List<TransaksiLainResponse> records = new ArrayList<TransaksiLainResponse>();
		for (Map<String, Object> item : trans) {
			TransaksiLainResponse detail = serialize(TransaksiLainResponse.class, item);
			
			records.add(detail);
		}
		
		return records;
	}
	
	public List<DisclaimerResponse> getListDisclaimerByPolicyNo(Long shId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT ID, DISCLAIMER_NO, DISCLAIMER_DESCRIPTION "
				+ "FROM BA_DISCLAIMER WHERE "
				+ "BA_TRANSACTION_SUMMARY_ID = ? "
				+ "ORDER BY ID ASC";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(shId);
		
		List<Map<String, Object>> trans = template.queryForList(sql, args.toArray());
		List<DisclaimerResponse> records = new ArrayList<DisclaimerResponse>();
		for (Map<String, Object> item : trans) {
			DisclaimerResponse detail = serialize(DisclaimerResponse.class, item);
			
			records.add(detail);
		}
		
		return records;
	}
	
	public boolean isExists(String policyNo){
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_CHECK_BA_EXISTS");
		caller.declareParameters(
				new SqlOutParameter("PO_EXISTS", OracleTypes.INTEGER),
				new SqlParameter("P_NO_POLIST", OracleTypes.VARCHAR));
		
		Map<String, Object> mValues = caller.execute(policyNo);
		return ((Integer)mValues.get("PO_EXISTS")) > 0;
	}
	
	public void deleteBancass(String sources) {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_DELETE_BANCASS");
		caller.declareParameters(new SqlParameter("P_SOURCE", OracleTypes.VARCHAR));
		
		caller.execute(sources);
	}
}
