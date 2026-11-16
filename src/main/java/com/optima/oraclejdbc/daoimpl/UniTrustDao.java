package com.optima.oraclejdbc.daoimpl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.UnitTrust;
import com.optima.oraclejdbc.response.unitrust.UniTrustTrxHistoryResponse;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.DateUtility;

@Component
public class UniTrustDao extends ParentDao<UnitTrust> {
	
	public List<UniTrustTrxHistoryResponse> getMiniStatement(String accountNumber, Date startDate, Date endDate, 
			Integer start, Integer end) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		return getByDateStatement(accountNumber, startDate, endDate, start, end, "TRANSACTION_DATE", "ASC", null);
	}

	public List<UniTrustTrxHistoryResponse> getByDateStatement(String accountNumber, Date startDate, Date endDate,
			Integer start, Integer end, String sortBy, String sortType, String searchKeyWord) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		if( accountNumber == null || accountNumber.length() == 0 )
			return null;
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(accountNumber);
		String sql = "SELECT * FROM "
				+ "(SELECT A.*, ROWNUM RNUM FROM "
				+ "(SELECT REFERENCE_NO, TRANSACTION_DATE, EFFECTIVE_DATE, TRANSACTION_TYPE, CURRENCY_CODE, "
				+ "AMOUNT, NAV, FEE_AMOUNT, UNIT, STATUS, TRANSACTION_SOURCE "
				+ "FROM UNIT_TRUST "
				+ "WHERE ACCOUNT_NO = ?";
		
		if( searchKeyWord != null ){
			sql+=" AND "+generateSearchSql(searchKeyWord, args);
		}
		
		if( startDate != null ){
			sql += " AND TRANSACTION_DATE >= ?";
			args.add(startDate);
		}
		
		if( endDate != null ){
			sql += " AND TRANSACTION_DATE <= ?";
			args.add(endDate);
		}
		sql+=" ORDER BY "+sortBy+" "+sortType+") A ";
		
		if( start == null ){
			start = 1; 						
		}
		
		if( end == null )
			end = 10;
		
		sql+="WHERE ROWNUM<=?) "
				+ "WHERE RNUM>=?";
		args.add(end);
		args.add(start);
		
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		
		ArrayList<UniTrustTrxHistoryResponse> res = new ArrayList<UniTrustTrxHistoryResponse>();
		for (Map<String, Object> row : rows) {
			UniTrustTrxHistoryResponse resp = serialize(UniTrustTrxHistoryResponse.class, row); 					
			res.add(resp);
		}
		
		return res;
	}

	public List<UniTrustTrxHistoryResponse> getByRecentTransactionsStatement(String accountNumber, int count,
			Integer page, Integer countPerPage, String sortBy, String sortType, String searchKeyWord) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		if( accountNumber == null || accountNumber.length() == 0 )
			return null;
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(accountNumber);
		String sql = "SELECT * FROM "
				+ "(SELECT A.*, ROWNUM RNUM FROM "
				+ "(SELECT REFERENCE_NO, TRANSACTION_DATE, EFFECTIVE_DATE, TRANSACTION_TYPE, CURRENCY_CODE, "
				+ "AMOUNT, NAV, FEE_AMOUNT, UNIT, STATUS, TRANSACTION_SOURCE "
				+ "FROM UNIT_TRUST "
				+ "WHERE ACCOUNT_NO = ? ";
		
		if( searchKeyWord != null ){
			sql += " AND "+generateSearchSql(searchKeyWord, args);
		}
		sql += "ORDER BY TRANSACTION_DATE DESC) A ";
		
		
		if( countPerPage == null )
			countPerPage = 10;
		
		int startRecord = 1;
		if( page != null ){
			startRecord = (page-1)*countPerPage+1; 						
		}
		sql+="WHERE ROWNUM<=? ";
		sql+="ORDER BY "+sortBy+" "+sortType+") ";
		args.add(startRecord+countPerPage-1);
		sql += "WHERE RNUM>=?";
		args.add(startRecord);
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		
		ArrayList<UniTrustTrxHistoryResponse> res = new ArrayList<UniTrustTrxHistoryResponse>();
		for (Map<String, Object> row : rows) {
			UniTrustTrxHistoryResponse resp = serialize(UniTrustTrxHistoryResponse.class, row); 					
			res.add(resp);
		}
		
		return res;
	}
	
	private String generateSearchSql(String searchKeyWord, List<Object> args) {
		searchKeyWord = searchKeyWord.toUpperCase();
		String res = "(UPPER(REFERENCE_NO) LIKE ? ";
		args.add("%"+searchKeyWord+"%");
		
		if( searchKeyWord.equals("SUBSCRIPTION") ){
			res += "OR TRANSACTION_TYPE=1 ";
		}else if( searchKeyWord.equals("REDEMPTION") ){
			res += "OR TRANSACTION_TYPE=2 ";
		}else if( searchKeyWord.equals("SWITCHING IN") ){
			res += "OR TRANSACTION_TYPE=3 ";
		}else if( searchKeyWord.equals("SWITCHING OUT") ){
			res += "OR TRANSACTION_TYPE=4 ";
		}
		
		if( searchKeyWord.equals("MOBILE BANKING") ){
			res += "OR UPPER(TRANSACTION_SOURCE)='M' ";
		}else if( searchKeyWord.equals("INTERNET BANKING") ){
			res += "OR UPPER(TRANSACTION_SOURCE)='I' ";
		}else if( searchKeyWord.equals("BRANCH") ){
			res += "OR UPPER(TRANSACTION_SOURCE)='B' ";
		}else if( searchKeyWord.equals("PHONE") ){
			res += "OR UPPER(TRANSACTION_SOURCE)='P' ";
		}
		
		if( searchKeyWord.equals("IN PROGRESS") ){
			res += "OR STATUS=0 ";
		}else if( searchKeyWord.equals("SUCCESSFUL") ){
			res += "OR STATUS=1 ";
		}else if( searchKeyWord.equals("REJECTED") ){
			res += "OR STATUS=2 ";
		}else if( searchKeyWord.equals("CANCELLED") ){
			res += "OR STATUS=3 OR STATUS=4 ";
		}
		
		Scanner scanner = new Scanner(searchKeyWord);
		if( scanner.hasNextBigDecimal() ){
			scanner.next();
			if( !scanner.hasNext() ){
				char thousandSep = CommonUtils.getThousandSeparator(Locale.US);
				String strAmount = searchKeyWord.replaceAll(""+thousandSep, "");
				BigDecimal amount = new BigDecimal(strAmount);
				
				res += "OR AMOUNT=? "
						+ "OR NAV=? "
						+ "OR FEE_AMOUNT=? "
						+ "OR UNIT=? ";
				args.add(amount);
				args.add(amount);
				args.add(amount);
				args.add(amount);
			}			
		}
		scanner.close();
		
		Date searchDate = DateUtility.parse(searchKeyWord, "dd/MM/yyyy");
		if( searchDate != null ){
			res += "OR TRANSACTION_DATE=? "
					+ "OR EFFECTIVE_DATE=? ";
			args.add(searchDate);
			args.add(searchDate);
		}
		res+=") ";
		return res;
	}

	public int getTotalCountByDate(String accountNumber, Date startDate, Date endDate, String searchKeyWord){
		String sql = "SELECT COUNT(*) "
				+ "FROM UNIT_TRUST "
				+ "WHERE ACCOUNT_NO=?";
		List<Object> args = new ArrayList<Object>();
		args.add(accountNumber);
		
		if( searchKeyWord != null )
			sql += " AND "+generateSearchSql(searchKeyWord, args);
		
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
	
	public int getTotalCountByRecent(String accountNumber, int count, String searchKeyWord){
		String sql = "SELECT COUNT(*) "
				+ "FROM UNIT_TRUST "
				+ "WHERE ACCOUNT_NO=?";
		List<Object> args = new ArrayList<Object>();
		args.add(accountNumber);
		
		if( searchKeyWord != null )
			sql += " AND "+generateSearchSql(searchKeyWord, args);
		int res = template.queryForObject(sql, args.toArray(), Integer.class);
		if( res > count )
			return count;
		return res;
	}
	
	public List<UnitTrust> getListDataByAccNo(String accountNo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT * FROM UNIT_TRUST WHERE ACCOUNT_NO = ?";
		List<Object> args = new ArrayList<Object>();
		args.add(accountNo);
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		List<UnitTrust> res = new ArrayList<UnitTrust>();
		for (Map<String, Object> row : rows) {
			UnitTrust resp = serialize(UnitTrust.class, row); 					
			res.add(resp);
		}
		
		return res;
	}
	
	public void deleteUTTemp(String accountNo) {
		String sql = "DELETE FROM " + CommonCons.DB_SCHEMA + ".UNIT_TRUST_TEMP WHERE ACCOUNT_NO = ?";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(accountNo);
		
		template.update(sql, args.toArray());
	}
}
