package com.optima.oraclejdbc.daoimpl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.CreditCardInfo;

@Component
public class CreditCardInfoDao extends ParentDao<CreditCardInfo>{
	public void insertOrUpdate(CreditCardInfo data) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		String sql = "SELECT ID FROM "+CommonCons.DB_SCHEMA+".TBL_CREDIT_CARD_INFO WHERE CREDIT_CARD_NUMBER=? ";
		List<Object> args = new ArrayList<Object>();
		args.add(data.getCreditCardNumber());
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if( rows.size() == 0 )
			create(data);
		else{
			data.setId(((BigDecimal)rows.get(0).get("ID")).longValue());
			update(data);
		}
	}
	
	public CreditCardInfo getInfo(String ccNumber) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT ID, CUSTOMER_INFO_ID, CREDIT_CARD_NUMBER, PRODUCT_ID, ACCOUNT_NUMBER "
				+ " FROM "+CommonCons.DB_SCHEMA+".TBL_CREDIT_CARD_INFO WHERE CREDIT_CARD_NUMBER=? ";
		List<Object> args = new ArrayList<Object>();
		args.add(ccNumber);
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if (rows.size() > 0) {
			CreditCardInfo info = serialize(CreditCardInfo.class, rows.get(0));
			
			return info;
		} else {
			return null;
		}
	}
}
