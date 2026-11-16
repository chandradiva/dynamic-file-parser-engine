package com.optima.oraclejdbc.daoimpl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import oracle.jdbc.internal.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.estatement.ESGiro;
import com.optima.oraclejdbc.model.estatement.ESGiroHistory;
import com.optima.oraclejdbc.response.dailystatement.DailyStatementResponse;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.DateUtility;

@Component
public class ESGiroDao extends ParentDao<ESGiro> {

	@Override
	public void createBatch(Object[] dataArray) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (dataArray.length > 0) {
			List<ESGiro> listData = new ArrayList<ESGiro>();
			
			for (int i=0;i<dataArray.length;i++) {
				listData.add((ESGiro) dataArray[i]); 
			}
			
			super.createBatch(listData.toArray());
		}
	}
	
	public boolean exists(String accountNumber, String periode){
		if( accountNumber == null || accountNumber.length() == 0 )
			return false;
		
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
				.withSchemaName(CommonCons.DB_SCHEMA)
				.withProcedureName("CHECK_GIRO_STATEMENT_EXISTS")
				.declareParameters(
						new SqlOutParameter("RES", OracleTypes.INTEGER),
						new SqlParameter("ACCOUNT_NUMBER", OracleTypes.VARCHAR),
						new SqlParameter("PERIODE", OracleTypes.VARCHAR)
				);
						
		
		Map<String, Object> mValues = caller.execute(
				CommonUtils.normalizeAccNo(accountNumber),
				periode
		);
		
		Integer res = (Integer)mValues.get("RES");
		
		return res > 0;
	}
	
	public List<ESGiroHistory> getListGiroHistoryByAccNo(String accountNo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT * FROM ES_GIRO_HISTORY WHERE NO_REKENING = ?";
		List<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeAccNo(accountNo));
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		List<ESGiroHistory> res = new ArrayList<ESGiroHistory>();
		for (Map<String, Object> row : rows) {
			ESGiroHistory giro = serialize(ESGiroHistory.class, row);
			res.add(giro);
		}
		
		return res;
	}
}
