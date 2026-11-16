package com.optima.oraclejdbc.daoimpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.HousekeepingLog;

import oracle.jdbc.OracleTypes;

@Component
public class HousekeepingLogDao extends ParentDao<HousekeepingLog> {

	public void insertLog(Date startDate, Date endDate, int status, String remarks) {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_INS_HOUSEKEEPING_LOG");
		caller.declareParameters(
				new SqlParameter("P_START", OracleTypes.TIMESTAMP),
				new SqlParameter("P_END", OracleTypes.TIMESTAMP),
				new SqlParameter("P_STATUS", OracleTypes.INTEGER),
				new SqlParameter("P_REMARKS", OracleTypes.VARCHAR));
		
		caller.execute(startDate, endDate, status, remarks);
	}
	
	public List<HousekeepingLog> getLogs(Date startDate, Date endDate, int status, String remarks, Integer start, Integer length) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_GET_HOUSEKEEPING_LOG");
		caller.declareParameters(
				new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR),
				new SqlParameter("P_START", OracleTypes.TIMESTAMP),
				new SqlParameter("P_END", OracleTypes.TIMESTAMP),
				new SqlParameter("P_STATUS", OracleTypes.INTEGER),
				new SqlParameter("P_REMARKS", OracleTypes.VARCHAR),
				new SqlParameter("P_START_POS", OracleTypes.INTEGER),
				new SqlParameter("P_LENGTH", OracleTypes.INTEGER)
		);
		
		Map<String, Object> mValues = caller.execute(startDate, endDate, status, remarks, start, length);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>)mValues.get("PO_CURSOR");
		
		ArrayList<HousekeepingLog> res = new ArrayList<HousekeepingLog>();
		for (Map<String, Object> row : rows) {
			res.add(serialize(HousekeepingLog.class, row));
		}
		return res;
	}
}
