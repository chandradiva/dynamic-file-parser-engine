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
import com.optima.oraclejdbc.model.CustomerAddress;
import com.optima.oraclejdbc.utils.CommonUtils;

@Component
public class CustomerAddressDao extends ParentDao<CustomerAddress>{

	public List<CustomerAddress> getCustomerAddressByCif(String cifKey) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
		.withSchemaName(CommonCons.DB_SCHEMA)
		.withProcedureName("SP_GET_CUST_ADDR_CIF")
		.declareParameters(				
				new SqlParameter("P_CIFKEY", OracleTypes.VARCHAR),
				new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR));

		Map<String, Object> mValues = caller.execute(CommonUtils.normalizeCifKey(cifKey));

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>)mValues.get("PO_CURSOR");

		ArrayList<CustomerAddress> res = new ArrayList<CustomerAddress>();
		for (Map<String, Object> row : rows) {
			CustomerAddress resp = serialize(CustomerAddress.class, row);
			res.add(resp);
		}

		return res;
	}
	
	public void setCustomerDefaultAddress(String cifKey, String seqNum){
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
		.withSchemaName(CommonCons.DB_SCHEMA)
		.withProcedureName("SP_SET_CUST_DEF_ADDR")
		.declareParameters(				
				new SqlParameter("P_CIFKEY", OracleTypes.VARCHAR),
				new SqlParameter("P_SEQNUM", OracleTypes.VARCHAR));

		caller.execute(CommonUtils.normalizeCifKey(cifKey), seqNum);
	}
	
	public void resetCustomerDefaultAddress(String cifKey){
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
		.withSchemaName(CommonCons.DB_SCHEMA)
		.withProcedureName("SP_RESET_CUST_DEF_ADDR")
		.declareParameters(				
				new SqlParameter("P_CIFKEY", OracleTypes.VARCHAR));

		caller.execute(CommonUtils.normalizeCifKey(cifKey));
	}
	
	public CustomerAddress getCustomerCustomAddress(String cifKey) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
		.withSchemaName(CommonCons.DB_SCHEMA)
		.withProcedureName("SP_GET_CUST_CUSTOM_ADDR")
		.declareParameters(				
				new SqlParameter("P_CIFKEY", OracleTypes.VARCHAR),
				new SqlOutParameter("PO_CURSOR", OracleTypes.CURSOR));

		Map<String, Object> mValues = caller.execute(CommonUtils.normalizeCifKey(cifKey));

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>)mValues.get("PO_CURSOR");

		if( rows.size() > 0 ){
			CustomerAddress addr = serialize(CustomerAddress.class, rows.get(0));
			return addr;
		}
		return null;
	}
}
