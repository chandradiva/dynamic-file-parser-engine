package com.optima.oraclejdbc.daoimpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oracle.jdbc.internal.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.estatement.ESMultiCurrency;
import com.optima.oraclejdbc.utils.CommonUtils;

@Component
public class ESMultiCurrencyDao extends ParentDao<ESMultiCurrency> {

	@Override
	public void createBatch(Object[] dataArray) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (dataArray.length > 0) {
			List<ESMultiCurrency> listData = new ArrayList<ESMultiCurrency>();
			
			for (int i=0;i<dataArray.length;i++) {
				listData.add((ESMultiCurrency) dataArray[i]); 
			}
			
			super.createBatch(listData.toArray());
		}
	}

	public boolean exists(String accountNumber, String periode){
		if( accountNumber == null || accountNumber.length() == 0 )
			return false;
		
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
				.withSchemaName(CommonCons.DB_SCHEMA)
				.withProcedureName("CHECK_MCY_STATEMENT_EXISTS")
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
}
