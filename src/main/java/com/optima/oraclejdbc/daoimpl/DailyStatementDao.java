package com.optima.oraclejdbc.daoimpl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.DailyStatement;
import com.optima.oraclejdbc.response.dailystatement.DailyStatementResponse;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.DateUtility;

import oracle.jdbc.internal.OracleTypes;

@Component
public class DailyStatementDao extends ParentDao<DailyStatement>{

	public List<DailyStatementResponse> getMiniStatement(String accountNumber, String currency, Date startDate, Date endDate, 
			Integer start, Integer end) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		return getByDateStatement(accountNumber, currency, startDate, endDate, start, end, "RECORD_ID", "DESC", null);
	}

	public List<DailyStatementResponse> getByDateStatement(String accountNumber, String currency, Date startDate, Date endDate, Integer start,
			Integer end, String sortBy, String sortType, String searchKeyWord) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		if( accountNumber == null || accountNumber.length() == 0 )
			return null;
		
		if( sortBy.equalsIgnoreCase("TRANSACTION_DATE") )
			sortBy = "RECORD_ID";
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
				.withSchemaName(CommonCons.DB_SCHEMA)
				.withProcedureName("GET_DS_BY_DATE")
				.declareParameters(
						new SqlParameter("ACCOUNT_NUMBER", OracleTypes.NVARCHAR),
						new SqlParameter("CURRENCY_CODE", OracleTypes.NVARCHAR),
						new SqlParameter("START_DATE", OracleTypes.DATE),
						new SqlParameter("END_DATE", OracleTypes.DATE),
						new SqlParameter("STR_SEARCH_KEY_WORD", OracleTypes.NVARCHAR),
						new SqlParameter("NUM_SEARCH_KEY_WORD", OracleTypes.NUMBER),
						new SqlParameter("DATE_SEARCH_KEY_WORD", OracleTypes.DATE),
						new SqlParameter("SORT_BY", OracleTypes.NVARCHAR),
						new SqlParameter("SORT_TYPE", OracleTypes.NVARCHAR),
						new SqlParameter("POS_START", OracleTypes.INTEGER),
						new SqlParameter("POS_END", OracleTypes.INTEGER),
						new SqlOutParameter("REF_CURSOR", OracleTypes.CURSOR));
		
		BigDecimal amount = null;
		Date searchDate = null;
		if( searchKeyWord != null ){
			searchKeyWord = searchKeyWord.replaceAll("'", "''");
			Scanner scanner = new Scanner(searchKeyWord);
			if(scanner.hasNextBigDecimal()){
				scanner.next();
				if( !scanner.hasNext() ){
					String strDec = searchKeyWord.replaceAll(""+CommonUtils.getThousandSeparator(Locale.US), "");
					amount = new BigDecimal(strDec);
				}			
			}
			scanner.close();
			
			searchDate = DateUtility.parse(searchKeyWord,"dd/MM/yyyy");
		}
		
		Map<String, Object> mValues = caller.execute(
				CommonUtils.normalizeAccNo(accountNumber),
				currency,
				startDate,
				endDate,
				searchKeyWord,
				amount,
				searchDate,
				sortBy,
				sortType,
				start,
				end
		);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>)mValues.get("REF_CURSOR");
		
		ArrayList<DailyStatementResponse> res = new ArrayList<DailyStatementResponse>();
		for (Map<String, Object> row : rows) {
			DailyStatementResponse resp = serialize(DailyStatementResponse.class, row);
			String tranCode = resp.getTranCode();
			String desc = "";
			if( tranCode != null ){
//				for(int i=tranCode.length();i<3;i++)
//					tranCode = "0"+tranCode;
//				desc = tranCode+" - ";
				resp.setTranCode(null);
			}
			String transDesc = resp.getTransDescription();
			if( transDesc == null ){
				transDesc = "";
			}
			desc += transDesc;
			String trxDesc = resp.getTrxDescription();
			if( trxDesc != null && !transDesc.trim().equalsIgnoreCase(trxDesc.trim()) ){
				if( transDesc.length() > 0 )
					trxDesc = " ("+trxDesc+")"; 
				desc += trxDesc;
			}
			resp.setTrxDescription(null);
			
			String remark = resp.getRemark1();
			if( remark != null && remark.length() > 0 ){
				desc += "<br /><b>"+remark+"</b>";
				resp.setRemark1(null);
			}
			
			remark = resp.getRemark2();
			if( remark != null && remark.length() > 0 ){
				desc += "<br /><b>"+remark+"</b>";
				resp.setRemark2(null);
			}
			
			remark = resp.getRemark3();
			if( remark != null && remark.length() > 0 ){
				desc += "<br /><b>"+remark+"</b>";
				resp.setRemark3(null);
			}
			
			remark = resp.getRemark4();
			if( remark != null && remark.length() > 0 ){
				desc += "<br /><b>"+remark+"</b>";
				resp.setRemark4(null);
			}
			
			remark = resp.getChequeNo();
			if( remark != null && remark.length() > 0 ){
				desc += "<br /><b>"+remark+"</b>";
				resp.setChequeNo(null);
			}
			resp.setTransDescription(desc);
			res.add(resp);
		}
		
		return res;
	}

	public List<DailyStatementResponse> getByRecentTransactionsStatement(String accountNumber, String currency, int count, Integer start, Integer end,
			String sortBy, String sortType, String searchKeyWord) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {		
		if( accountNumber == null || accountNumber.length() == 0 )
			return null;
		
		if( sortBy.equalsIgnoreCase("TRANSACTION_DATE") )
			sortBy = "RECORD_ID";
		
		if( end == null )
			end = Integer.MAX_VALUE;
		
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
				.withSchemaName(CommonCons.DB_SCHEMA)
				.withProcedureName("GET_DS_BY_RECENT")
				.declareParameters(
						new SqlParameter("ACCOUNT_NUMBER", OracleTypes.NVARCHAR),
						new SqlParameter("CURRENCY_CODE", OracleTypes.NVARCHAR),
						new SqlParameter("STR_SEARCH_KEY_WORD", OracleTypes.NVARCHAR),
						new SqlParameter("NUM_SEARCH_KEY_WORD", OracleTypes.NUMBER),
						new SqlParameter("DATE_SEARCH_KEY_WORD", OracleTypes.DATE),
						new SqlParameter("SORT_BY", OracleTypes.NVARCHAR),
						new SqlParameter("SORT_TYPE", OracleTypes.NVARCHAR),
						new SqlParameter("POS_COUNT", OracleTypes.INTEGER),
						new SqlParameter("POS_START", OracleTypes.INTEGER),
						new SqlParameter("POS_END", OracleTypes.INTEGER),
						new SqlOutParameter("REF_CURSOR", OracleTypes.CURSOR));
		
		BigDecimal amount = null;
		Date searchDate = null;
		if( searchKeyWord != null ){
			searchKeyWord = searchKeyWord.replaceAll("'", "''");
			Scanner scanner = new Scanner(searchKeyWord);
			if(scanner.hasNextBigDecimal()){
				scanner.next();
				if( !scanner.hasNext() ){
					String strDec = searchKeyWord.replaceAll(""+CommonUtils.getThousandSeparator(Locale.US), "");
					amount = new BigDecimal(strDec);
				}				
			}
			scanner.close();
			
			searchDate = DateUtility.parse(searchKeyWord,"dd/MM/yyyy");
		}
		
		Map<String, Object> mValues = caller.execute(
				CommonUtils.normalizeAccNo(accountNumber),
				currency,
				searchKeyWord,
				amount,
				searchDate,
				sortBy,
				sortType,
				count,
				start,
				end
		);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>)mValues.get("REF_CURSOR");
		
		ArrayList<DailyStatementResponse> res = new ArrayList<DailyStatementResponse>();
		for (Map<String, Object> row : rows) {
			DailyStatementResponse resp = serialize(DailyStatementResponse.class, row);
			String tranCode = resp.getTranCode();
			String desc = "";
			if( tranCode != null ){
//				for(int i=tranCode.length();i<3;i++)
//					tranCode = "0"+tranCode;
//				desc = tranCode+" - ";
				resp.setTranCode(null);
			}
			String transDesc = resp.getTransDescription();
			if( transDesc == null ){
				transDesc = "";
			}
			desc += transDesc;
			String trxDesc = resp.getTrxDescription();
			if( trxDesc != null && !transDesc.trim().equalsIgnoreCase(trxDesc.trim()) ){
				if( transDesc.length() > 0 )
					trxDesc = " ("+trxDesc+")"; 
				desc += trxDesc;
			}
			resp.setTrxDescription(null);
			
			String remark = resp.getRemark1();
			if( remark != null && remark.length() > 0 ){
				desc += "<br /><b>"+remark+"</b>";
				resp.setRemark1(null);
			}
			
			remark = resp.getRemark2();
			if( remark != null && remark.length() > 0 ){
				desc += "<br /><b>"+remark+"</b>";
				resp.setRemark2(null);
			}
			
			remark = resp.getRemark3();
			if( remark != null && remark.length() > 0 ){
				desc += "<br /><b>"+remark+"</b>";
				resp.setRemark3(null);
			}
			
			remark = resp.getRemark4();
			if( remark != null && remark.length() > 0 ){
				desc += "<br /><b>"+remark+"</b>";
				resp.setRemark4(null);
			}
			
			remark = resp.getChequeNo();
			if( remark != null && remark.length() > 0 ){
				desc += "<br /><b>"+remark+"</b>";
				resp.setChequeNo(null);
			}
			resp.setTransDescription(desc);
			res.add(resp);
		}
		
		return res;
	}
	
	public int getTotalCountByDate(String accountNumber, String currency, Date startDate, Date endDate, String searchKeyWord){
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
				.withSchemaName(CommonCons.DB_SCHEMA)
				.withProcedureName("GET_TOTAL_DS_COUNT_BY_DATE")
				.declareParameters(
						new SqlParameter("ACCOUNT_NUMBER", OracleTypes.NVARCHAR),
						new SqlParameter("CURRENCY_CODE", OracleTypes.NVARCHAR),
						new SqlParameter("START_DATE", OracleTypes.DATE),
						new SqlParameter("END_DATE", OracleTypes.DATE),
						new SqlParameter("STR_SEARCH_KEY_WORD", OracleTypes.NVARCHAR),
						new SqlParameter("NUM_SEARCH_KEY_WORD", OracleTypes.NUMBER),
						new SqlParameter("DATE_SEARCH_KEY_WORD", OracleTypes.DATE),
						new SqlOutParameter("TOTAL_COUNT", OracleTypes.INTEGER));
		
		BigDecimal amount = null;
		Date searchDate = null;
		if( searchKeyWord != null ){
			searchKeyWord = searchKeyWord.replaceAll("'", "''");
			Scanner scanner = new Scanner(searchKeyWord);
			if(scanner.hasNextBigDecimal()){
				scanner.next();
				if( !scanner.hasNext() ){
					String strDec = searchKeyWord.replaceAll(""+CommonUtils.getThousandSeparator(Locale.US), "");
					amount = new BigDecimal(strDec);
				}						
			}
			scanner.close();
			
			searchDate = DateUtility.parse(searchKeyWord,"dd/MM/yyyy");
		}
		
		Map<String, Object> mValues = caller.execute(
				CommonUtils.normalizeAccNo(accountNumber),
				currency,
				startDate,
				endDate,
				searchKeyWord,
				amount,
				searchDate				
		);
		
		return (Integer)mValues.get("TOTAL_COUNT");		
	}
	
	public int getTotalCountByRecent(String accountNumber, String currency, int count, String searchKeyWord){
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
				.withSchemaName(CommonCons.DB_SCHEMA)
				.withProcedureName("GET_TOTAL_DS_COUNT_BY_RECENT")
				.declareParameters(
						new SqlParameter("ACCOUNT_NUMBER", OracleTypes.NVARCHAR),
						new SqlParameter("CURRENCY_CODE", OracleTypes.NVARCHAR),
						new SqlParameter("STR_SEARCH_KEY_WORD", OracleTypes.NVARCHAR),
						new SqlParameter("NUM_SEARCH_KEY_WORD", OracleTypes.NUMBER),
						new SqlParameter("DATE_SEARCH_KEY_WORD", OracleTypes.DATE),
						new SqlOutParameter("TOTAL_COUNT", OracleTypes.INTEGER));
		
		BigDecimal amount = null;
		Date searchDate = null;
		if( searchKeyWord != null ){
			searchKeyWord = searchKeyWord.replaceAll("'", "''");
			Scanner scanner = new Scanner(searchKeyWord);
			if(scanner.hasNextBigDecimal()){
				scanner.next();
				if( !scanner.hasNext() ){
					String strDec = searchKeyWord.replaceAll(""+CommonUtils.getThousandSeparator(Locale.US), "");
					amount = new BigDecimal(strDec);
				}				
			}
			scanner.close();
			
			searchDate = DateUtility.parse(searchKeyWord,"dd/MM/yyyy");
		}
		
		Map<String, Object> mValues = caller.execute(
				CommonUtils.normalizeAccNo(accountNumber),
				currency,
				searchKeyWord,
				amount,
				searchDate				
		);
		
		int res = (Integer)mValues.get("TOTAL_COUNT");	
		
		if( res > count )
			return count;
		return res;
	}

	public Double getLastBalance(String accountNumber, String currency) {
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
				.withSchemaName(CommonCons.DB_SCHEMA)
				.withProcedureName("SP_DS_GET_LAST_BALANCE")
				.declareParameters(
						new SqlOutParameter("O_LAST_BALANCE", OracleTypes.NUMBER),
						new SqlParameter("P_ACCOUNT_NUMBER", OracleTypes.NVARCHAR),
						new SqlParameter("CURRENCY_CODE", OracleTypes.NVARCHAR)	
				);
						
		
		Map<String, Object> mValues = caller.execute(
				CommonUtils.normalizeAccNo(accountNumber),
				currency				
		);
		
		return ((BigDecimal)mValues.get("O_LAST_BALANCE")).doubleValue();			
	}
	
	public boolean isYesterdaySubmitted(String accountNumber, String currency) {
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
				.withSchemaName(CommonCons.DB_SCHEMA)
				.withProcedureName("SP_DS_YESTERDAY_SUBMITTED")
				.declareParameters(
						new SqlOutParameter("PO_EXISTS", OracleTypes.NUMBER),
						new SqlParameter("P_ACCOUNT_NUMBER", OracleTypes.NVARCHAR),
						new SqlParameter("P_CURRENCY", OracleTypes.NVARCHAR),
						new SqlParameter("P_YESTERDAY", OracleTypes.DATE)
				);
						
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		
		Map<String, Object> mValues = caller.execute(
				CommonUtils.normalizeAccNo(accountNumber),				
				currency,
				cal.getTime()
		);
		
		return ((BigDecimal)mValues.get("PO_EXISTS")).intValue() > 0;			
	}
	
	public void deleteTemp(String accountNumber) {
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
				.withSchemaName(CommonCons.DB_SCHEMA)
				.withProcedureName("SP_DS_DELETE_TEMP")
				.declareParameters(
						new SqlParameter("P_ACCOUNT_NUMBER", OracleTypes.NVARCHAR)							
				);
						
		
		caller.execute(
				CommonUtils.normalizeAccNo(accountNumber)
		);
	}
}
