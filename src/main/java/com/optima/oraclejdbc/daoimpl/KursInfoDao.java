package com.optima.oraclejdbc.daoimpl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.optima.oraclejdbc.model.KursInfo;

import oracle.jdbc.internal.OracleTypes;

@Component
public class KursInfoDao extends ParentDao<KursInfo>{	
	public void save(List<KursInfo> models){		
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_SAVE_KURS_INFO");
		caller.declareParameters(
				new SqlParameter("PA_MODEL", OracleTypes.ARRAY, "TABLE_KURS_INFO_TYPE")			
		);
		StructMapper<KursInfo> mapper = new BeanPropertyStructMapper<KursInfo>(KursInfo.class);
		KursInfo[] aKursInfo = Arrays.copyOf(models.toArray(), models.size(), KursInfo[].class);
		SqlStructArrayValue<KursInfo> data = new SqlStructArrayValue<KursInfo>(aKursInfo, mapper, "TBL_KURS_INFO_TYPE", "TABLE_KURS_INFO_TYPE");
		caller.execute(data);		
	}
	
	public List<KursInfo> getAll() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{		
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_GET_KURS_INFO");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR)			
		);
		Map<String, Object> mValues = caller.execute();
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>)mValues.get("C1");
		ArrayList<KursInfo> res = new ArrayList<KursInfo>();
		for (Map<String, Object> row : rows) {
			KursInfo kursInfo = serialize(KursInfo.class, row);
			res.add(kursInfo);
		}
		return res;
	}
	
	public Date getKursInfoUpdatedAt(){
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_GET_KURS_INFO_UPDATED_AT");
		caller.declareParameters(
				new SqlOutParameter("UPDATED_AT", OracleTypes.TIMESTAMP)			
		);
		Map<String, Object> mValues = caller.execute(); 
		return (Date)mValues.get("UPDATED_AT");
	}
}
