package com.optima.oraclejdbc.daoimpl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oracle.jdbc.internal.OracleTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.CustomerInfo;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.RsaUtil;

import oracle.jdbc.internal.OracleTypes;


@Component
public class CustomerInfoDao extends ParentDao<CustomerInfo>{

	@Autowired
	RsaUtil rsaUtil;
	
	public Long insertOrUpdate(CustomerInfo data) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String sql = "SELECT ID FROM "+CommonCons.DB_SCHEMA+".TBL_CUSTOMER_INFO WHERE CIF_KEY=?";
		List<Object> args = new ArrayList<Object>();
		args.add(data.getCifKey());
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if( rows.size() == 0 ){
			data.setStatementPassword(rsaUtil.rsaEncrypt("7777777"));
			return create(data);
		}
		else{
			update(data);
			return ((BigDecimal)rows.get(0).get("ID")).longValue();
		}
	}
	
	public CustomerInfo getUserByUserId(String userId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		String sql = "SELECT * FROM TBL_CUSTOMER_INFO "
				+ "WHERE USER_ID=?";
		List<Object> args = new ArrayList<Object>();
		args.add(userId);
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if( rows.size() == 0 )
			return null;
		else{
			return serialize(CustomerInfo.class, rows.get(0));
		}
	}
	
	public CustomerInfo getUserByCIFKey(String cifKey) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		String sql = "SELECT * FROM TBL_CUSTOMER_INFO "
				+ "WHERE CIF_KEY=?";
		List<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeCifKey(cifKey));
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if( rows.size() == 0 )
			return null;
		else{
			return serialize(CustomerInfo.class, rows.get(0));
		}
	}
	
	public List<CustomerInfo> get(Long min, Long max){
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_GET_CUSTOMER_INFO");
		caller.declareParameters(
				new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR),
				new SqlParameter("P_MIN", OracleTypes.NUMBER),
				new SqlParameter("P_MAX", OracleTypes.NUMBER));
		
		Map<String, Object> mValues = caller.execute(min, max);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("PO_CURSOR");
		List<CustomerInfo> datas = new ArrayList<CustomerInfo>();
		for (Map<String, Object> row : rows) {
			CustomerInfo ci = new CustomerInfo();
			ci.setCifKey((String)row.get("CIF_KEY"));
			ci.setId(((BigDecimal)row.get("ID")).longValue());
			datas.add(ci);
		}
		return datas;
	}
}
