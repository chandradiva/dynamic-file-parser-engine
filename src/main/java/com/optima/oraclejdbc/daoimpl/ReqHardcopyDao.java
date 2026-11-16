package com.optima.oraclejdbc.daoimpl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.springframework.data.jdbc.support.oracle.SqlArrayValue;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.ReqHardcopy;
import com.optima.oraclejdbc.utils.CommonUtils;

@Component
public class ReqHardcopyDao extends ParentDao<ReqHardcopy>{

	public int insertRequest(String periode, String accountNo, String currency, String address) {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_INS_REQ_HARDCOPY");
		caller.declareParameters(
				new SqlParameter("P_PERIODE", OracleTypes.VARCHAR),
				new SqlParameter("P_ACCOUNT_NO", OracleTypes.VARCHAR),
				new SqlParameter("P_CURRENCY", OracleTypes.VARCHAR),
				new SqlParameter("P_ADDRESS", OracleTypes.VARCHAR),
				new SqlOutParameter("RS", OracleTypes.INTEGER));
		
		Map<String, Object> mValues = caller.execute(periode, CommonUtils.normalizeAccNo(accountNo), currency, address);
		
		return (Integer) mValues.get("RS");
	}
	
	public List<ReqHardcopy> getListDataByParam(String cif, String accountNo, Date startDate, Date endDate, 
			Integer periodeStart, Integer periodeEnd, int start, int length) {
		
		List<ReqHardcopy> listData = new ArrayList<ReqHardcopy>();
		
		try {
			SimpleJdbcCall caller = new SimpleJdbcCall(template);
			caller.withSchemaName(CommonCons.DB_SCHEMA);
			caller.withProcedureName("SP_GET_REQ_HC_BY_PARAM");
			caller.declareParameters(
					new SqlParameter("P_CIF", OracleTypes.VARCHAR),
					new SqlParameter("P_ACCOUNT_NO", OracleTypes.VARCHAR),
					new SqlParameter("P_START", OracleTypes.DATE),
					new SqlParameter("P_END", OracleTypes.DATE),
					new SqlParameter("P_PERIODE_START", OracleTypes.NUMBER),
					new SqlParameter("P_PERIODE_END", OracleTypes.NUMBER),
					new SqlParameter("P_RNUM_START", OracleTypes.NUMBER),
					new SqlParameter("P_RNUM_LENGTH", OracleTypes.NUMBER),
					new SqlOutParameter("REF_CURSOR", OracleTypes.CURSOR));
			
			Map<String, Object> mValues = caller.execute(
					CommonUtils.normalizeCifKey(cif),
					accountNo, 
					startDate, 
					endDate, 
					periodeStart, 
					periodeEnd, 
					start, 
					length);
			
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("REF_CURSOR");
			for (Map<String, Object> row : rows) {
				ReqHardcopy req = serialize(ReqHardcopy.class, row);
				
				DateFormat format = new SimpleDateFormat("yyyyMM");
				Date date = format.parse(req.getPeriode());
				req.setPeriode(CommonUtils.getStrDate(date, "MMMM yyyy", Locale.forLanguageTag("id-ID")));
				listData.add(req);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
		
		return listData;
	}
	
	public int getTotalRow(String cif, String accountNo, Date startDate, Date endDate, Integer periodeStart, Integer periodeEnd) {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_GET_TOTAL_ROW_REQ_HC");
		caller.declareParameters(
				new SqlParameter("P_CIF", OracleTypes.VARCHAR),
				new SqlParameter("P_ACCOUNT_NO", OracleTypes.VARCHAR),
				new SqlParameter("P_START", OracleTypes.DATE),
				new SqlParameter("P_END", OracleTypes.DATE),
				new SqlParameter("P_PERIODE_START", OracleTypes.NUMBER),
				new SqlParameter("P_PERIODE_END", OracleTypes.NUMBER),
				new SqlOutParameter("TOTAL_ROW", OracleTypes.INTEGER));
		
		Map<String, Object> mValues = caller.execute(
				CommonUtils.normalizeCifKey(cif),
				CommonUtils.normalizeAccNo(accountNo), 
				startDate, 
				endDate, 
				periodeStart, 
				periodeEnd);
		
		return (Integer) mValues.get("TOTAL_ROW");
	}
	
	public Long cancelReqHardcopy(List<Long> hcIds) {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_CANCEL_REQ_HC");
		caller.declareParameters(
				new SqlParameter("P_HC_ID", OracleTypes.ARRAY, CommonCons.DB_SCHEMA + ".T_NUMBER"),
				new SqlOutParameter("B_ID_OUT", OracleTypes.NUMBER));
		
		Long[] ids = hcIds.toArray(new Long[0]);
		SqlArrayValue<Long> data = new SqlArrayValue<Long>(ids);
		
		Map<String, Object> mValues = caller.execute(data);
		
		BigDecimal mValuess = (BigDecimal) mValues.get("B_ID_OUT");
		
		return mValuess.longValue();
	}
	
	public void updateReqHardcopy(Long id, Integer status){
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_SET_REQ_HARDCOPY");
		caller.declareParameters(
				new SqlParameter("P_ID", OracleTypes.NUMBER),
				new SqlParameter("P_STATUS", OracleTypes.INTEGER));
		
		caller.execute(id, status);
	}
	
	public Long processReqHardcopy(List<Long> hcIds) {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_PROCESS_REQ_HC");
		caller.declareParameters(
				new SqlParameter("P_HC_ID", OracleTypes.ARRAY, CommonCons.DB_SCHEMA + ".T_NUMBER"),
				new SqlOutParameter("B_ID_OUT", OracleTypes.NUMBER));
		
		Long[] ids = hcIds.toArray(new Long[0]);
		SqlArrayValue<Long> data = new SqlArrayValue<Long>(ids);
		
		Map<String, Object> mValues = caller.execute(data);
		
		BigDecimal mValuess = (BigDecimal) mValues.get("B_ID_OUT");
		
		return mValuess.longValue();
	}
}
