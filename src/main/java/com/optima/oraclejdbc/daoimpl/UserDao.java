package com.optima.oraclejdbc.daoimpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.User;

@Component
public class UserDao extends ParentDao<User>{
	
	public void insertOrUpdate(User data) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String sql = "SELECT USERNAME FROM "+CommonCons.FE_DB_SCHEMA+".NISP_USER WHERE USERNAME=?";
		List<Object> args = new ArrayList<Object>();
		args.add(data.getUsername());
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if( rows.size() == 0 )
			create(data);
		else{
			update(data);
		}
	}
	
	public User getUserByUsername(String username) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		String sql = "SELECT * FROM "+CommonCons.FE_DB_SCHEMA+".NISP_USER "
				+ "WHERE USERNAME=?";
		List<Object> args = new ArrayList<Object>();
		args.add(username);
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if( rows.size() == 0 )
			return null;
		else{
			return serialize(User.class, rows.get(0));
		}
	}
}
