package com.optima.oraclejdbc.daoimpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.backoffice.BOCCRegex;

import oracle.jdbc.OracleTypes;

@Component
public class CCRegexDao extends ParentDao<BOCCRegex>{

	public List<BOCCRegex> getAllCCRegex() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_GET_ALL_CC_REGEX");
		caller.declareParameters(new SqlOutParameter("C1", OracleTypes.CURSOR));
		
		Map<String, Object> values = caller.execute();
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) values.get("C1");
		List<BOCCRegex> datas = new ArrayList<BOCCRegex>();
		for (Map<String, Object> row : rows) {
			BOCCRegex regex = serialize(BOCCRegex.class, row);
			
			datas.add(regex);
		}
		
		return datas;
	}
}
