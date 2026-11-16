package com.optima.oraclejdbc.daoimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jdbc.support.oracle.BeanPropertyStructMapper;
import org.springframework.data.jdbc.support.oracle.SqlArrayValue;
import org.springframework.data.jdbc.support.oracle.SqlStructArrayValue;
import org.springframework.data.jdbc.support.oracle.StructMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.db.SqlStringArray;
import com.optima.oraclejdbc.model.ApplicationParameter;
import com.optima.oraclejdbc.model.KursInfo;

import oracle.jdbc.internal.OracleTypes;

@Component
public class ApplicationParameterDao extends ParentDao<ApplicationParameter>{
	public String getParameterValue(String key){
		String sql = "SELECT VALUE FROM "+CommonCons.FE_DB_SCHEMA+".BO_TBL_APP_PARAM WHERE KEY=?";
		ArrayList<String> args = new ArrayList<String>();
		args.add(key);
		
		String res = template.queryForObject(sql, args.toArray(), String.class);
		return res;
	}
	
	public Map<String, String> getParameterValues(List<String> keys){
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
				.withSchemaName(CommonCons.FE_DB_SCHEMA)
				.withProcedureName("SP_GET_APP_PARAMS")
				.declareParameters(
						new SqlOutParameter("C1", OracleTypes.CURSOR),
						new SqlParameter("P_KEYS", OracleTypes.ARRAY, CommonCons.FE_DB_SCHEMA+".T_STRING") 
				);
		
		
		String[] aKeys = keys.toArray(new String[0]);//Arrays.copyOf(keys.toArray(), keys.size(), String[].class);
		SqlArrayValue<String> data = new SqlArrayValue<String>(aKeys);
		Map<String, Object> mValues = caller.execute(data);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>)mValues.get("C1");		
		Map<String, String> res = new HashMap<String, String>();
		for (Map<String, Object> row : rows) {
			res.put((String)row.get("KEY"), (String)row.get("VALUE"));
		}
		return res;
	}
	
	public List<Map<String, Object>> getParameterValues(){
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
				.withSchemaName(CommonCons.FE_DB_SCHEMA)
				.withProcedureName("GET_ALL_PARAMETER_VALUES")
				.declareParameters(new SqlOutParameter("res_cursor", OracleTypes.CURSOR));
				
		Map<String, Object> mValues = caller.execute();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>)mValues.get("res_cursor");		
		return rows;
				
	}
}
