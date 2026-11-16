package com.optima.oraclejdbc.daoimpl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.backoffice.BOSendEmail;

import oracle.jdbc.internal.OracleTypes;

@Component
public class BOSendEmailDao extends ParentDao<BOSendEmail> {

	public BOSendEmail getEmailById(long id) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_GET_SEND_EMAIL_BY_ID");
		caller.declareParameters(
				new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR),
				new SqlParameter("P_EMAIL_ID", OracleTypes.NUMBER));
		
		Map<String, Object> mValues = caller.execute(id);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("PO_CURSOR");
		if( rows.size() > 0 ){
			return serialize(BOSendEmail.class, rows.get(0));
		}
		return null;
	}
	
	public void startEmailRetention() {
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
			.withSchemaName(CommonCons.DB_SCHEMA)
			.withProcedureName("SP_EMAIL_RETENTION")
			.declareParameters();
		
		caller.execute();
	}
}
