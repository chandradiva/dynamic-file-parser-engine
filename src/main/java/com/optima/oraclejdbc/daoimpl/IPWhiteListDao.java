package com.optima.oraclejdbc.daoimpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.IPWhiteList;

import oracle.jdbc.internal.OracleTypes;

@Component
public class IPWhiteListDao extends ParentDao<IPWhiteList>{
	public List<IPWhiteList> getAlloweds() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_GET_IP_WHITE_LIST");
		caller.declareParameters(
				new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR));
		
		Map<String, Object> mValues = caller.execute();
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("PO_CURSOR");
		List<IPWhiteList> datas = new ArrayList<IPWhiteList>();
		for (Map<String, Object> row : rows) {
			IPWhiteList ip = serialize(IPWhiteList.class, row);
			
			datas.add(ip);
		}
		
		return datas;	
	}
}
