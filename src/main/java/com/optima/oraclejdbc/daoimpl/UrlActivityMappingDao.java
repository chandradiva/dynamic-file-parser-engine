package com.optima.oraclejdbc.daoimpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.UrlActivityMapping;

@Component
public class UrlActivityMappingDao extends ParentDao<UrlActivityMapping> {

	public UrlActivityMapping getByUrl(String urlPath) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT * FROM "+CommonCons.DB_SCHEMA+".TBL_URL_ACTIVITY_MAPPING WHERE ? LIKE URL_PATH||'%'";
		List<Object> args = new ArrayList<Object>();
		args.add(urlPath);
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if( rows.size() == 0 )
			return null;
		return serialize(UrlActivityMapping.class, rows.get(0));		
	}
}
