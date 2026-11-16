package com.optima.oraclejdbc.daoimpl;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.ContentParameter;

@Component
public class ContentParameterDao extends ParentDao<ContentParameter> {
	public String getParameterValue(String key){
		String sql = "SELECT VALUE FROM "+CommonCons.DB_SCHEMA+".BO_TBL_CONTENT_PARAM WHERE KEY=?";
		ArrayList<String> args = new ArrayList<String>();
		args.add(key);
		
		String res = template.queryForObject(sql, args.toArray(), String.class);
		return res;
	}
}
