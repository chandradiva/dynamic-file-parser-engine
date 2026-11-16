package com.optima.oraclejdbc.daoimpl;

import oracle.jdbc.internal.OracleTypes;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.HouseKeeping;

@Component
public class HouseKeepingDao extends ParentDao<HouseKeeping> {
	public void houseKeeping(String hostname){
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
			.withSchemaName(CommonCons.DB_SCHEMA)
			.withProcedureName("SP_HOUSEKEEPING")
			.declareParameters(new SqlParameter("P_HOSTNAME", OracleTypes.NVARCHAR));

		caller.execute(hostname);
	}
}
