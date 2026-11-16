package com.optima.oraclejdbc.daoimpl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.AccountList;
import com.optima.oraclejdbc.model.MappingProduct;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.RsaUtil;

import oracle.jdbc.internal.OracleTypes;

@Component
public class AccountListDao extends ParentDao<AccountList>{
	
	@Autowired
	MappingProductDao productDao;
	
	@Autowired
	RsaUtil rsaUtil;
	
	public void insertOrUpdate(AccountList data) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		String currency = data.getCurrency();
		String sql = "SELECT ID FROM "+CommonCons.DB_SCHEMA+".TBL_ACCOUNT_LIST WHERE ACCOUNT_NO=?";
		List<Object> args = new ArrayList<Object>();
		args.add(data.getAccountNumber());
		if( currency != null ){
			sql += " AND CURRENCY=?";
			args.add(currency);
		}else{
			sql += " AND CURRENCY IS NULL";
		}
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if( rows.size() == 0 )
			create(data);
		else{
			data.setId(((BigDecimal)rows.get(0).get("ID")).longValue());
			update(data);
		}
	}
	
	public List<AccountList> getAllAccountListByAccountNumber(String accountNumber) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		String sql = "SELECT * FROM TBL_ACCOUNT_LIST "
				+ "WHERE ACCOUNT_NO=?";
		List<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeAccNo(accountNumber));
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		ArrayList<AccountList> res = new ArrayList<AccountList>();
		if( rows.size() == 0 )
			return null;
		else{
			for (Map<String, Object> row : rows) {
				AccountList acc = serialize(AccountList.class, row);
				MappingProduct product = productDao.getProductById(acc.getProductId());
				acc.setProduct(product);
				res.add(acc);
			}			
		}
		return res;
	}
	
	public AccountList getAccountListByAccountNumber(String accountNumber) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		List<AccountList> res = getAllAccountListByAccountNumber(CommonUtils.normalizeAccNo(accountNumber));
		if( res == null )
			return null;
		return res.get(0);
	}
	
	public AccountList getAccountListByAccountNumber(String accountNumber, String currency) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		List<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeAccNo(accountNumber).trim());
		String sql = "SELECT * FROM TBL_ACCOUNT_LIST "
				+ "WHERE ACCOUNT_NO=? AND ";
		if( currency.equalsIgnoreCase("POINT") ){
			sql += "CURRENCY IS NULL"; 
		}else{
			sql += "CURRENCY=?";
			args.add(currency.trim());
		}
			
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if( rows.size() == 0 )
			return null;
		else{
			AccountList acc = serialize(AccountList.class, rows.get(0));
			MappingProduct product = productDao.getProductById(acc.getProductId());
			acc.setProduct(product);
			return acc;
		}
	}

	public AccountList getAccountListByProducCode(String cifKey,
			String productType) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT A.* FROM TBL_ACCOUNT_LIST A "
				+ "LEFT JOIN TBL_CUSTOMER_INFO B ON A.CUSTOMER_INFO_ID = B.ID "
				+ "LEFT JOIN TBL_MAPPING_PRODUCT C ON A.PRODUCT_ID = C.ID "
				+ "WHERE B.CIF_KEY = ? AND C.PRODUCT_CODE = ? "
				+ "ORDER BY A.ID DESC";
		ArrayList<String> args = new ArrayList<String>();
		args.add(CommonUtils.normalizeCifKey(cifKey));
		args.add(productType);
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if( rows.size() == 0 )
			return null;
		else{
			return serialize(AccountList.class, rows.get(0));
		}
	}
	
	public String getStatementPassword(String accountNumber, int type){
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
				.withSchemaName(CommonCons.DB_SCHEMA)
				.withProcedureName("GET_STATEMENT_PASSWORD")
				.declareParameters(
						new SqlParameter("ACCOUNT_NUMBER", OracleTypes.NVARCHAR),
						new SqlOutParameter("RES", OracleTypes.NVARCHAR));
		
		// 0: CASA
		// 1: CC
		if( type == 0 )
			accountNumber = CommonUtils.normalizeAccNo(accountNumber);
		Map<String, Object> mValues = caller.execute(
				accountNumber				
		);
		
		String res = (String)mValues.get("RES");
		if( res != null && res.length() > 0 ){
			return res;
		}
		return null;
	}
	
	public String getStatementPasswordByCifKey(String cifKey){
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
				.withSchemaName(CommonCons.DB_SCHEMA)
				.withProcedureName("SP_GET_PASSWORD_BY_CIFKEY")
				.declareParameters(
						new SqlParameter("CIFKEY", OracleTypes.NVARCHAR),
						new SqlOutParameter("RES", OracleTypes.NVARCHAR));
		
		Map<String, Object> mValues = caller.execute(
				CommonUtils.normalizeCifKey(cifKey)
		);
		
		String res = (String)mValues.get("RES");
		if( res != null && res.length() > 0 ){
			return res;
		}
		return null;
	}
	
	public Integer getAccountCategory(String accountNumber){
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
				.withSchemaName(CommonCons.DB_SCHEMA)
				.withProcedureName("GET_ACCOUNT_CATEGORY")
				.declareParameters(
						new SqlParameter("ACCOUNT_NUMBER", OracleTypes.NVARCHAR),
						new SqlOutParameter("RES", OracleTypes.INTEGER));
		
		Map<String, Object> mValues = caller.execute(
				CommonUtils.normalizeAccNo(accountNumber)				
		);
		
		return (Integer)mValues.get("RES");		
	}
	
	public void removeFromAccountList(String accountNumber, String currency){
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
		.withSchemaName(CommonCons.DB_SCHEMA)
		.withProcedureName("SP_DEL_ACCOUNT_LIST")
		.declareParameters(
				new SqlParameter("P_ACCOUNT_NUMBER", OracleTypes.VARCHAR),
				new SqlParameter("P_CURRENCY", OracleTypes.VARCHAR)
				);

		caller.execute(CommonUtils.normalizeAccNo(accountNumber), currency);
	}
	
	public void removeFromAccountList(long customerInfoId){
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
		.withSchemaName(CommonCons.DB_SCHEMA)
		.withProcedureName("SP_DEL_ACC_LIST_BY_CUST_ID")
		.declareParameters(
				new SqlParameter("P_CUST_ID", OracleTypes.NUMBER)
				);

		caller.execute(customerInfoId);
	}
	
	public List<String> getAccountStatement(String cifKey){
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
		.withSchemaName(CommonCons.DB_SCHEMA)
		.withProcedureName("SP_GET_ACC_STATEMENT")
		.declareParameters(
				new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR),
				new SqlParameter("P_CIF_KEY", OracleTypes.NVARCHAR));

		Map<String, Object> mValues = caller.execute(
				CommonUtils.normalizeCifKey(cifKey)
		);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>)mValues.get("PO_CURSOR");
		
		ArrayList<String> res = new ArrayList<String>();
		for (Map<String, Object> row : rows) {
			res.add((String)row.get("ACCOUNT_NO"));
		}
		return res;
	}
}
