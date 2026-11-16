package com.optima.oraclejdbc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.daoimpl.ApplicationParameterDao;

@Service
public class ApplicationParameterService {
	@Autowired
	ApplicationParameterDao dao;
	
	public String getParameterValue(String key){
		return dao.getParameterValue(key);
	}
	
	public Map<String, String> getParameterValues(List<String> keys){
		return dao.getParameterValues(keys);
	}
	
	public List<Map<String, Object>> getParameterValues(){
		return dao.getParameterValues();
	}
	
	public Map<String, String> getAllParameters(){
		List<Map<String, Object>> rows = dao.getParameterValues();
		Map<String, String> res = new HashMap<String, String>();
		for (Map<String, Object> row : rows) {
			res.put((String)row.get("KEY"), (String)row.get("VALUE"));
		}
		return res;
	}
}
