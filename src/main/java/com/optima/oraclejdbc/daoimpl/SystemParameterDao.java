package com.optima.oraclejdbc.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.SystemParameter;

import oracle.jdbc.internal.OracleTypes;

@Component
public class SystemParameterDao extends ParentDao<SystemParameter>{
	public String getParameterValue(String key){
		String sql = "SELECT VALUE FROM "+CommonCons.DB_SCHEMA+".BO_TBL_SYS_PARAM WHERE KEY=?";
		ArrayList<String> args = new ArrayList<String>();
		args.add(key);
		
		String res = null;
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if( rows.size() > 0 )
			res = (String)rows.get(0).get("VALUE");
		return res;
	}
	
	public List<String> getParameterValues(List<String> keys){
		ArrayList<String> res = new ArrayList<String>();
		if( keys.size() == 0 )
			return res;
		String sql = "SELECT VALUE FROM "+CommonCons.DB_SCHEMA+".BO_TBL_SYS_PARAM WHERE KEY IN (";
		boolean first = true;
		for (int i=0;i<keys.size();i++) {
			if( !first ){
				sql+=",";				
			}else
				first=false;
			sql+="?";
		}
		sql += ")";
		
		List<Map<String, Object>> rows = template.queryForList(sql, keys.toArray());
		
		for (Map<String, Object> map : rows) {
			res.add((String)map.get("VALUE"));
		}
		return res;
	}
	
	public List<Map<String, Object>> getParameterValues(){
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
				.withSchemaName(CommonCons.DB_SCHEMA)
				.withProcedureName("GET_ALL_PARAMETER_VALUES")
				.declareParameters(new SqlOutParameter("res_cursor", OracleTypes.CURSOR));
				
		Map<String, Object> mValues = caller.execute();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>)mValues.get("res_cursor");		
		return rows;
				
	}
}
